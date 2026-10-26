package top.egon.cola.component.yaoguang.admin.permission;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import top.egon.cola.component.common.desensitize.strategy.SensitiveStrategyRegistry;
import top.egon.cola.component.yaoguang.admin.auth.controller.GatewayAuthBootstrapController;
import top.egon.cola.component.yaoguang.admin.knowledge.controller.*;
import top.egon.cola.component.yaoguang.admin.knowledge.converter.KnowledgeUploadReceiptConverter;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.dto.*;
import top.egon.cola.component.yaoguang.admin.knowledge.service.KnowledgeService;
import top.egon.cola.component.yaoguang.admin.shared.converter.KnowledgePermissionProjectionConverter;
import top.egon.cola.component.yaoguang.admin.wiki.controller.WikiController;
import top.egon.cola.platform.tianquan.jianshen.contract.authorization.*;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.*;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;
import top.egon.cola.platform.tianquan.jianshen.starter.authorization.AuthorizationService;
import top.egon.cola.platform.tianquan.jianshen.starter.cache.SingleFlightSnapshotLoader;
import top.egon.cola.platform.tianquan.jianshen.starter.field.*;
import top.egon.cola.platform.tianquan.jianshen.starter.permission.*;
import top.egon.cola.platform.tianquan.jianshen.starter.security.*;
import top.egon.cola.platform.tianquan.shoubing.contract.AuthenticationContext;
import top.egon.cola.platform.tianquan.shoubing.contract.IdentityPrincipal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Runs the real shared executors against compiler policies without a server or an authorization database. */
@EnabledIfSystemProperty(named = "permission.producer.enabled", matches = "true")
class YaoguangManagedEntryPointTest {
    private static final Instant NOW = Instant.parse("2026-10-25T00:00:00Z");
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);
    private static final String ARTIFACT = "a".repeat(64);
    private static final CatalogScope SCOPE = new CatalogScope("10", "20", "xingyuan", "yaoguang", "default", "test");
    private static final IdentityPrincipal USER = new IdentityPrincipal("30", "20", "40", Set.of("https://api.example/yaoguang"),
            NOW.minusSeconds(10), NOW.plusSeconds(300), AuthenticationContext.of("PASSWORD", NOW.minusSeconds(20)));

    @AfterEach void clear() {
        SecurityContextHolder.clearContext();
        org.springframework.web.context.request.RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void fieldCatalogReportsActualAdapterInputsAndRejectsUnverifiedOrForeignQueries() throws Exception {
        var manifest = manifest();
        authenticate(manifest, Set.of());
        var registry = registry(manifest);
        var view = new top.egon.cola.platform.tianquan.jianshen.starter.permission.CurrentEffectivePermissionView(new CurrentRbac3User(), registry, CLOCK);
        var fields = new top.egon.cola.platform.tianquan.jianshen.starter.permission.PermissionFieldCatalog(registry, view, new CurrentRbac3User());
        var adapter = new org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter();
        adapter.setMessageConverters(List.of(new org.springframework.http.converter.json.MappingJackson2HttpMessageConverter(new ObjectMapper().findAndRegisterModules())));
        var routes = new org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping();
        var beans = new org.springframework.beans.factory.support.StaticListableBeanFactory();
        beans.addBean("adapter", adapter);
        beans.addBean("routes", routes);
        var controller = new top.egon.cola.component.yaoguang.admin.auth.controller.GatewayFieldCatalogController(fields,
                beans.getBeanProvider(org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.class),
                beans.getBeanProvider(org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping.class));
        var request = new org.springframework.mock.web.MockHttpServletRequest("GET", "/api/v1/authorization/field-catalog");
        request.addParameter("keys", "yaoguang.field.KnowledgeUploadCommandDTO.content", "yaoguang.field.KnowledgeUploadReceiptVO.documentId");
        assertThatThrownBy(() -> controller.current(request)).isInstanceOf(AuthenticationCredentialsNotFoundException.class);
        top.egon.cola.platform.tianquan.shoubing.starter.security.VerifiedUserTokenCarrier.set(request, "verified-user-fixture");
        var response = controller.current(request);
        assertThat(response.getHeaders().getCacheControl()).isEqualTo("no-store");
        assertThat(response.getBody().data().catalogScope()).isEqualTo(SCOPE);
        assertThat(response.getBody().data().fields().get("yaoguang.field.KnowledgeUploadCommandDTO.content").inputBinding()).isTrue();
        assertThat(response.getBody().data().fields().get("yaoguang.field.KnowledgeUploadReceiptVO.documentId").inputBinding()).isFalse();
        request.addParameter("tenantId", "other");
        assertThatThrownBy(() -> controller.current(request)).isInstanceOf(IllegalArgumentException.class);
        request.removeParameter("tenantId");
        SecurityContextHolder.clearContext();
        assertThatThrownBy(() -> controller.current(request)).isInstanceOf(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    void everyDirectControllerCallRejectsMissingIdentityBeforeEnteringItsBody() throws Exception {
        var manifest = manifest();
        var registry = registry(manifest);
        var loader = mock(SingleFlightSnapshotLoader.class);
        var manager = new Rbac3MethodAuthorizationManager(mock(AuthorizationService.class),
                new PermissionExecutionGuard(registry, new CurrentRbac3User(), loader));
        var interceptor = new AuthorizationManagerBeforeMethodInterceptor(new StaticMethodMatcherPointcut() {
            @Override public boolean matches(Method method, Class<?> targetClass) { return manager.supports(method, targetClass); }
        }, manager);
        int count = 0;
        for (Class<?> type : List.of(KnowledgeController.class, KnowledgeDocumentController.class,
                KnowledgeJobController.class, WikiController.class, GatewayAuthBootstrapController.class,
                top.egon.cola.component.yaoguang.admin.auth.controller.GatewayCurrentPermissionController.class)) {
            Object target = mock(type, CALLS_REAL_METHODS);
            var factory = new ProxyFactory(target);
            factory.setProxyTargetClass(true);
            factory.addAdvisor(interceptor);
            Object proxy = factory.getProxy();
            for (Method method : type.getDeclaredMethods()) {
                if (method.getAnnotation(PermissionResource.class) == null) continue;
                Object[] arguments = Arrays.stream(method.getParameterTypes()).map(parameter -> {
                    if (parameter == int.class) return (Object) 1;
                    if (parameter == long.class) return (Object) 1L;
                    if (parameter == boolean.class) return (Object) false;
                    return null;
                }).toArray();
                assertThatThrownBy(() -> method.invoke(proxy, arguments)).as(method.toString())
                        .isInstanceOf(InvocationTargetException.class)
                        .hasCauseInstanceOf(AuthenticationCredentialsNotFoundException.class);
                count++;
            }
        }
        assertThat(count).isEqualTo(24);
        verifyNoInteractions(loader);
    }

    @Test
    void multipartReadOnlyUserIsRejectedBeforeFileBytesAndBusinessCalls() throws Exception {
        var manifest = manifest();
        authenticate(manifest, Set.of(YaoguangPermissionCodes.READ));
        var fields = new PermissionFieldBindingGuard(registry(manifest), new CurrentRbac3User());
        var service = mock(KnowledgeService.class);
        var controller = new KnowledgeDocumentController(new KnowledgePermissionProjectionConverter(), service,
                new KnowledgeUploadReceiptConverter(), fields);
        var request = new MockMultipartHttpServletRequest();
        request.addFile(new MockMultipartFile("file", "test.txt", "text/plain", new byte[] {1}));
        MultipartFile file = mock(MultipartFile.class);
        assertThatThrownBy(() -> controller.uploadKnowledgeDocument("1", file, null, null, null, "valid-intent-key-1", request))
                .isInstanceOf(AuthorizationDeniedException.class);
        verifyNoInteractions(file, service);
    }

    @Test
    void multipartUnknownPartsAreRejectedBeforeBindingOrWrites() {
        var fields = mock(PermissionFieldBindingGuard.class);
        var service = mock(KnowledgeService.class);
        var controller = new KnowledgeDocumentController(new KnowledgePermissionProjectionConverter(), service,
                new KnowledgeUploadReceiptConverter(), fields);
        var request = new MockMultipartHttpServletRequest();
        request.addFile(new MockMultipartFile("file", new byte[] {1}));
        request.addPart(new org.springframework.mock.web.MockPart("ownerActorId", "untrusted".getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        MultipartFile file = mock(MultipartFile.class);
        assertThatThrownBy(() -> controller.uploadKnowledgeDocument("1", file, null, null, null, "valid-intent-key-1", request))
                .isInstanceOf(AuthorizationDeniedException.class).hasMessage("FIELD_BINDING_UNDECLARED");
        verifyNoInteractions(file, fields, service);
    }

    @Test
    void jsonInputChecksExplicitNullNestedFieldsAndReadonlyOutput() throws Exception {
        var manifest = manifest();
        authenticate(manifest, Set.of(YaoguangPermissionCodes.WRITE));
        var registry = registry(manifest);
        var fields = new PermissionFieldBindingGuard(registry, new CurrentRbac3User(), () -> null, userScopePolicy());
        var module = new PermissionJsonMapperFactory(new CurrentRbac3User(), SensitiveStrategyRegistry.defaults(), registry, fields);
        var converter = new org.springframework.http.converter.json.MappingJackson2HttpMessageConverter(new ObjectMapper().findAndRegisterModules());
        new top.egon.cola.component.yaoguang.admin.config.GatewayAiJsonConfiguration(module)
                .extendMessageConverters(List.of(converter));
        var mapper = converter.getObjectMappersForType(KnowledgeBaseCreateCommandDTO.class).get(org.springframework.http.MediaType.ALL);
        assertThat(converter.getObjectMapper().getRegisteredModuleIds()).contains("Rbac3FieldJacksonModule");
        assertThat(mapper.readValue("{\"name\":null}", KnowledgeBaseCreateCommandDTO.class).getName()).isNull();
        assertThatThrownBy(() -> mapper.readValue("{\"name\":null}", KnowledgeBaseCommandDTO.class))
                .hasStackTraceContaining("FIELD_ACCESS_DENIED");
        assertThatThrownBy(() -> mapper.readValue("{\"members\":[{\"actorId\":\"1\",\"role\":\"OWNER\"}]}", KnowledgeMembersReplaceCommandDTO.class))
                .hasStackTraceContaining("FIELD_ACCESS_DENIED");
        assertThatThrownBy(() -> mapper.readValue("{\"ownerActorId\":\"1\"}", KnowledgeBaseCreateCommandDTO.class))
                .hasStackTraceContaining("FIELD_BINDING_UNKNOWN_OR_DUPLICATE");
        assertThatThrownBy(() -> mapper.readValue("{\"id\":\"1\"}",
                top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeBaseCreatedVO.class))
                .hasStackTraceContaining("FIELD_ACCESS_DENIED");
    }

    @Test
    void bootMvcFirstReaderAndWriterRetainProtectedPerTypeCopiesAndRawTransportOrder() throws Exception {
        var manifest = manifest();
        authenticate(manifest, Set.of(YaoguangPermissionCodes.WRITE));
        var registry = registry(manifest);
        var fields = new PermissionFieldBindingGuard(registry, new CurrentRbac3User(), () -> null, userScopePolicy());
        var factory = new PermissionJsonMapperFactory(new CurrentRbac3User(), SensitiveStrategyRegistry.defaults(), registry, fields);
        var properties = new org.springframework.boot.env.YamlPropertySourceLoader().load("application",
                new org.springframework.core.io.ClassPathResource("application.yml"));
        String preferred = properties.stream().map(value -> value.getProperty("spring.http.converters.preferred-json-mapper"))
                .filter(Objects::nonNull).map(Object::toString).findFirst().orElseThrow();
        String codecsPreferred = properties.stream().map(value -> value.getProperty("spring.http.codecs.preferred-json-mapper"))
                .filter(Objects::nonNull).map(Object::toString).findFirst().orElseThrow();
        assertThat(preferred).isEqualTo("jackson2");
        assertThat(codecsPreferred).isEqualTo("jackson2");
        new org.springframework.boot.test.context.runner.WebApplicationContextRunner()
                .withConfiguration(org.springframework.boot.autoconfigure.AutoConfigurations.of(
                        org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration.class,
                        org.springframework.boot.jackson2.autoconfigure.Jackson2AutoConfiguration.class,
                        org.springframework.boot.http.converter.autoconfigure.HttpMessageConvertersAutoConfiguration.class,
                        org.springframework.boot.webmvc.autoconfigure.WebMvcAutoConfiguration.class))
                .withPropertyValues("spring.http.converters.preferred-json-mapper=" + preferred,
                        "spring.http.codecs.preferred-json-mapper=" + codecsPreferred)
                .withBean(PermissionJsonMapperFactory.class, () -> factory)
                .withUserConfiguration(top.egon.cola.component.yaoguang.admin.config.GatewayAiJsonConfiguration.class)
                .run(context -> {
                    assertThat(context).hasNotFailed();
                    assertThat(context.getServletContext()).isInstanceOf(org.springframework.mock.web.MockServletContext.class);
                    var converters = context.getBean(org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.class)
                            .getMessageConverters();
                    var normal = firstJsonReader(converters, KnowledgeReindexCommandDTO.class);
                    var strict = firstJsonReader(converters, KnowledgeBaseCreateCommandDTO.class);
                    assertThat(strict).isSameAs(normal);
                    var writer = converters.stream().filter(value -> value.canWrite(
                            top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeBaseCreatedVO.class, MediaType.APPLICATION_JSON))
                            .findFirst().orElseThrow();
                    assertThat(writer).isSameAs(normal);
                    var shared = context.getBean(ObjectMapper.class);
                    assertThat(normal.getObjectMapper()).isNotSameAs(shared);
                    assertThat(normal.getObjectMapper().getRegisteredModuleIds()).contains("Rbac3FieldJacksonModule");
                    assertThat(shared.getRegisteredModuleIds()).doesNotContain("Rbac3FieldJacksonModule");
                    var perType = strict.getObjectMappersForType(KnowledgeBaseCreateCommandDTO.class).get(MediaType.ALL);
                    assertThat(perType).isNotSameAs(normal.getObjectMapper());
                    assertThat(perType.getRegisteredModuleIds()).contains("Rbac3FieldJacksonModule");
                    assertThat(perType.isEnabled(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)).isTrue();
                    assertThat(((KnowledgeReindexCommandDTO) normal.read(KnowledgeReindexCommandDTO.class,
                            jsonInput("{\"expectedRevision\":null}"))).getExpectedRevision()).isNull();
                    assertThat(((KnowledgeBaseCreateCommandDTO) strict.read(KnowledgeBaseCreateCommandDTO.class,
                            jsonInput("{\"name\":null}"))).getName()).isNull();
                    assertThatThrownBy(() -> strict.read(KnowledgeBaseCommandDTO.class, jsonInput("{\"name\":null}")))
                            .hasStackTraceContaining("FIELD_ACCESS_DENIED");
                    assertThatThrownBy(() -> strict.read(KnowledgeBaseCreateCommandDTO.class, jsonInput("{\"ownerActorId\":\"1\"}")))
                            .hasStackTraceContaining("FIELD_BINDING_UNKNOWN_OR_DUPLICATE");
                    var output = new org.springframework.mock.http.MockHttpOutputMessage();
                    normal.write(new top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeBaseCreatedVO().setId("42"),
                            MediaType.APPLICATION_JSON, output);
                    assertThat(shared.readTree(output.getBodyAsBytes()).has("id")).isFalse();
                    assertRawTransportOrder(converters, normal);
                });
    }

    @Test
    void userWriteScopeCannotRevealReadFieldsFromTheSameResponse() throws Exception {
        var manifest = manifest();
        authenticate(manifest, Set.of(YaoguangPermissionCodes.READ, YaoguangPermissionCodes.WRITE));
        var user = (Rbac3UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(new Rbac3AuthenticationToken(user,
                List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("SCOPE_" + YaoguangPermissionCodes.WRITE))));
        var registry = registry(manifest);
        var fields = new PermissionFieldBindingGuard(registry, new CurrentRbac3User(), () -> null, userScopePolicy());
        var mapper = new PermissionJsonMapperFactory(new CurrentRbac3User(), SensitiveStrategyRegistry.defaults(), registry, fields)
                .forTransport(new ObjectMapper().findAndRegisterModules());
        assertThat(mapper.readValue("{\"name\":\"Created\"}", KnowledgeBaseCreateCommandDTO.class).getName()).isEqualTo("Created");
        var response = new top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeBaseCreatedVO().setId("42");
        assertThat(mapper.readTree(mapper.writeValueAsString(response)).has("id")).isFalse();
        authenticate(manifest, Set.of(YaoguangPermissionCodes.READ, YaoguangPermissionCodes.WRITE));
        assertThat(mapper.readTree(mapper.writeValueAsString(response)).get("id").asText()).isEqualTo("42");
    }

    @Test
    void currentPermissionViewRequiresTheVerifiedHostUserAndNoTargetQuery() throws Exception {
        var manifest = manifest();
        authenticate(manifest, Set.of());
        var view = new CurrentEffectivePermissionView(new CurrentRbac3User(), registry(manifest), CLOCK);
        var controller = new top.egon.cola.component.yaoguang.admin.auth.controller.GatewayCurrentPermissionController(view);
        var request = new org.springframework.mock.web.MockHttpServletRequest("GET", "/api/v1/authorization/effective-permissions");
        assertThatThrownBy(() -> controller.current(request)).isInstanceOf(AuthenticationCredentialsNotFoundException.class);
        top.egon.cola.platform.tianquan.shoubing.starter.security.VerifiedUserTokenCarrier.set(request, "verified-user-fixture");
        var response = controller.current(request);
        assertThat(response.getHeaders().getCacheControl()).isEqualTo("no-store");
        assertThat(response.getBody().data().catalogScope()).isEqualTo(SCOPE);
        assertThat(response.getBody().data().permissionCodes()).isEmpty();
        request.addParameter("tenantId", "21");
        assertThatThrownBy(() -> controller.current(request)).isInstanceOf(IllegalArgumentException.class);
        request.removeAllParameters();
        authenticateService(YaoguangPermissionCodes.READ);
        assertThatThrownBy(() -> controller.current(request)).isInstanceOf(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    void serviceOperationProjectionsKeepCreateAndAdminScopesIndependent() throws Exception {
        var manifest = manifest();
        var registry = registry(manifest);
        var policyClient = serviceClient(manifest, ARTIFACT);
        var servicePolicies = new PermissionServicePolicyGuard(registry, policyClient, "yaoguang", CLOCK);
        var fields = new PermissionFieldBindingGuard(registry, new CurrentRbac3User(), servicePolicies);
        var mapper = new ObjectMapper().findAndRegisterModules().registerModule(new Rbac3FieldJacksonModule(
                new CurrentRbac3User(), SensitiveStrategyRegistry.defaults(), registry, fields));
        authenticateService(YaoguangPermissionCodes.WRITE);
        selectHandler(KnowledgeController.class, "createKnowledgeBase", new org.springframework.mock.web.MockHttpServletRequest());
        assertThat(mapper.readValue("{\"name\":null}", KnowledgeBaseCreateCommandDTO.class).getName()).isNull();
        var created = new top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeBaseCreatedVO().setName("created");
        assertThat(mapper.readTree(mapper.writeValueAsString(created)).get("name").asText()).isEqualTo("created");
        assertThatThrownBy(() -> mapper.readValue("{\"name\":null}", KnowledgeBaseCommandDTO.class))
                .hasStackTraceContaining("FIELD_SERVICE_SCOPE_DENIED");
        assertThatThrownBy(() -> mapper.readValue("{\"id\":\"1\"}", created.getClass()))
                .hasStackTraceContaining("FIELD_ACCESS_DENIED");
        authenticateService(YaoguangPermissionCodes.ADMIN);
        selectHandler(KnowledgeController.class, "replaceKnowledgeBase", new org.springframework.mock.web.MockHttpServletRequest());
        assertThat(mapper.readValue("{\"name\":null}", KnowledgeBaseCommandDTO.class).getName()).isNull();
        var managed = new top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeBaseManagedVO().setName("managed");
        assertThat(mapper.readTree(mapper.writeValueAsString(managed)).get("name").asText()).isEqualTo("managed");
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .isInstanceOf(top.egon.cola.platform.tianquan.shoubing.contract.ServiceIdentityPrincipal.class);
    }

    @Test
    void serviceMultipartRequiresWriteScopeAndReturnsOnlyTheWriteResponseProjection() throws Exception {
        var manifest = manifest();
        var registry = registry(manifest);
        var fields = new PermissionFieldBindingGuard(registry, new CurrentRbac3User(),
                new PermissionServicePolicyGuard(registry, serviceClient(manifest, ARTIFACT), "yaoguang", CLOCK));
        var service = mock(KnowledgeService.class);
        var controller = new KnowledgeDocumentController(new KnowledgePermissionProjectionConverter(), service,
                new KnowledgeUploadReceiptConverter(), fields);
        var request = new MockMultipartHttpServletRequest();
        request.addFile(new MockMultipartFile("file", "test.txt", "text/plain", new byte[] {1}));
        selectHandler(KnowledgeDocumentController.class, "uploadKnowledgeDocument", request);
        MultipartFile file = mock(MultipartFile.class);
        authenticateService(YaoguangPermissionCodes.READ);
        assertThatThrownBy(() -> controller.uploadKnowledgeDocument("1", file, null, null, null, "valid-intent-key-1", request))
                .isInstanceOf(AuthorizationDeniedException.class).hasMessage("FIELD_SERVICE_SCOPE_DENIED");
        verifyNoInteractions(file, service);
        authenticateService(YaoguangPermissionCodes.WRITE);
        when(file.getOriginalFilename()).thenReturn("test.txt");
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getBytes()).thenReturn(new byte[] {1});
        when(service.uploadDocument(isNull(), eq("1"), any(), eq("valid-intent-key-1"))).thenReturn(
                new top.egon.cola.component.yaoguang.admin.knowledge.domain.bo.KnowledgeUploadReceiptBO()
                        .setDocumentId("1").setRevisionId("2").setJobId("3")
                        .setStatus(top.egon.cola.component.yaoguang.admin.knowledge.domain.enums.KnowledgeJobStatusEnum.QUEUED));
        var response = controller.uploadKnowledgeDocument("1", file, null, null, null, "valid-intent-key-1", request);
        assertThat(response.getStatusCode().value()).isEqualTo(202);
        var mapper = new ObjectMapper().findAndRegisterModules().registerModule(new Rbac3FieldJacksonModule(
                new CurrentRbac3User(), SensitiveStrategyRegistry.defaults(), registry, fields));
        assertThat(mapper.readTree(mapper.writeValueAsString(response.getBody())).get("documentId").asText()).isEqualTo("1");
        verify(service).uploadDocument(isNull(), eq("1"), any(), eq("valid-intent-key-1"));
    }

    @Test
    void serviceFieldCannotUseAnotherBuildsOtherwisePermittedPolicy() throws Exception {
        var manifest = manifest();
        var registry = registry(manifest);
        var fields = new PermissionFieldBindingGuard(registry, new CurrentRbac3User(),
                new PermissionServicePolicyGuard(registry, serviceClient(manifest, "b".repeat(64)), "yaoguang", CLOCK));
        authenticateService(YaoguangPermissionCodes.WRITE);
        selectHandler(KnowledgeController.class, "createKnowledgeBase", new org.springframework.mock.web.MockHttpServletRequest());
        assertThatThrownBy(() -> fields.require(KnowledgeBaseCreateCommandDTO.class, new SubmittedFieldSet(Set.of("name")), Set.of("name")))
                .isInstanceOf(org.springframework.security.authorization.AuthorizationDeniedException.class);
    }

    @Test
    void persistenceCarriersRemainIndependentOfRequestFieldPermissions() throws Exception {
        var manifest = manifest();
        var registry = registry(manifest);
        var mapper = new ObjectMapper().findAndRegisterModules().registerModule(new Rbac3FieldJacksonModule(
                new CurrentRbac3User(), SensitiveStrategyRegistry.defaults(), registry,
                new PermissionFieldBindingGuard(registry, new CurrentRbac3User())));
        SecurityContextHolder.clearContext();
        assertThat(mapper.readTree(mapper.writeValueAsString(new KnowledgeMemberDTO().setActorId("30")
                .setRole(top.egon.cola.component.yaoguang.admin.knowledge.domain.enums.KnowledgeMemberRoleEnum.OWNER)))
                .get("actorId").asText()).isEqualTo("30");
        assertThat(mapper.readTree(mapper.writeValueAsString(new top.egon.cola.component.yaoguang.admin.wiki.domain.dto.WikiSourceDTO()
                .setDocumentRevisionId("1").setChunkId("2").setSourceHash("a".repeat(64))))
                .get("documentRevisionId").asText()).isEqualTo("1");
    }

    private top.egon.cola.platform.tianquan.jianshen.starter.client.Rbac3AuthorizationClient serviceClient(
            PermissionManifest manifest, String artifact) throws Exception {
        var client = mock(top.egon.cola.platform.tianquan.jianshen.starter.client.Rbac3AuthorizationClient.class);
        var digests = new PermissionManifestCanonicalizer().digests(manifest);
        var policy = new ServicePolicySnapshot(SCOPE, "1", "2",
                new RuntimePolicyBinding("yaoguang-java", "1", artifact, digests.policyDigest(), digests.manifestDigest()),
                manifest.resources().stream().map(PermissionManifest.ResourceDefinition::key).collect(java.util.stream.Collectors.toSet()),
                NOW.plusSeconds(30));
        when(client.fetchPolicy(eq("yaoguang"), any())).thenReturn(policy);
        return client;
    }

    private void authenticateService(String scope) {
        var principal = new top.egon.cola.platform.tianquan.shoubing.contract.ServiceIdentityPrincipal("publisher", "20", "publisher", "token-1",
                java.net.URI.create("https://api.example/yaoguang"), 1, Set.of(scope), "xingyuan", "publisher", "test", "credential-1",
                NOW.minusSeconds(10), NOW.plusSeconds(60), "publisher", top.egon.cola.platform.tianquan.shoubing.contract.ServiceTokenContext.TENANT);
        SecurityContextHolder.getContext().setAuthentication(new top.egon.cola.platform.tianquan.shoubing.starter.security.IdpAuthenticationToken(
                principal, List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("SCOPE_" + scope)),
                java.net.URI.create("https://idp.example/tenants/20")));
    }

    private void selectHandler(Class<?> controller, String name, org.springframework.mock.web.MockHttpServletRequest request) {
        Method method = Arrays.stream(controller.getDeclaredMethods()).filter(value -> value.getName().equals(name)).findFirst().orElseThrow();
        request.setAttribute(org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE,
                new org.springframework.web.method.HandlerMethod(mock(controller), method));
        org.springframework.web.context.request.RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));
    }

    private top.egon.cola.platform.tianquan.jianshen.starter.autoconfigure.Rbac3StarterProperties.UserScopePolicyProperties userScopePolicy() {
        var policy = new top.egon.cola.platform.tianquan.jianshen.starter.autoconfigure.Rbac3StarterProperties.UserScopePolicyProperties();
        policy.setEnabled(true);
        policy.setPermissionScopes(Map.of(YaoguangPermissionCodes.READ, Set.of(YaoguangPermissionCodes.READ),
                YaoguangPermissionCodes.WRITE, Set.of(YaoguangPermissionCodes.WRITE),
                YaoguangPermissionCodes.ADMIN, Set.of(YaoguangPermissionCodes.ADMIN)));
        return policy;
    }

    private PermissionPolicyRegistry registry(PermissionManifest manifest) {
        return new PermissionPolicyRegistry(manifest, ARTIFACT, CLOCK, List.of(SCOPE));
    }

    private MappingJackson2HttpMessageConverter firstJsonReader(List<HttpMessageConverter<?>> converters, Class<?> type) {
        var reader = converters.stream().filter(value -> value.canRead(type, MediaType.APPLICATION_JSON)).findFirst().orElseThrow();
        assertThat(reader).isInstanceOf(MappingJackson2HttpMessageConverter.class);
        return (MappingJackson2HttpMessageConverter) reader;
    }

    private MockHttpInputMessage jsonInput(String body) {
        var input = new MockHttpInputMessage(body.getBytes(StandardCharsets.UTF_8));
        input.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return input;
    }

    private void assertRawTransportOrder(List<HttpMessageConverter<?>> converters, MappingJackson2HttpMessageConverter json) throws Exception {
        var string = converters.stream().filter(value -> value.canWrite(String.class, MediaType.APPLICATION_JSON)).findFirst().orElseThrow();
        var bytes = converters.stream().filter(value -> value.canWrite(byte[].class, MediaType.APPLICATION_OCTET_STREAM)).findFirst().orElseThrow();
        var stream = converters.stream().filter(value -> value.canWrite(org.springframework.core.io.InputStreamResource.class,
                MediaType.APPLICATION_OCTET_STREAM)).findFirst().orElseThrow();
        assertThat(string).isInstanceOf(org.springframework.http.converter.StringHttpMessageConverter.class);
        assertThat(bytes).isInstanceOf(org.springframework.http.converter.ByteArrayHttpMessageConverter.class);
        assertThat(stream).isInstanceOf(org.springframework.http.converter.ResourceHttpMessageConverter.class);
        assertThat(converters.indexOf(bytes)).isLessThan(converters.indexOf(string));
        assertThat(converters.indexOf(string)).isLessThan(converters.indexOf(stream));
        assertThat(converters.indexOf(stream)).isLessThan(converters.indexOf(json));
        var raw = new org.springframework.mock.http.MockHttpOutputMessage();
        ((org.springframework.http.converter.StringHttpMessageConverter) string).write("{\"ok\":true}", MediaType.APPLICATION_JSON, raw);
        assertThat(raw.getBodyAsString()).isEqualTo("{\"ok\":true}");
        var binary = new org.springframework.mock.http.MockHttpOutputMessage();
        ((org.springframework.http.converter.ByteArrayHttpMessageConverter) bytes).write(new byte[] {1, 2, 3}, MediaType.APPLICATION_OCTET_STREAM, binary);
        assertThat(binary.getBodyAsBytes()).containsExactly(1, 2, 3);
        var streamed = new org.springframework.mock.http.MockHttpOutputMessage();
        ((org.springframework.http.converter.ResourceHttpMessageConverter) stream).write(new org.springframework.core.io.InputStreamResource(
                new java.io.ByteArrayInputStream(new byte[] {4, 5, 6})), MediaType.APPLICATION_OCTET_STREAM, streamed);
        assertThat(streamed.getBodyAsBytes()).containsExactly(4, 5, 6);
    }

    private PermissionManifest manifest() throws Exception {
        var json = new ObjectMapper();
        var fragment = new PermissionManifestReader().read(json.readValue(Files.readString(
                Path.of("target/classes/META-INF/permissions/fragment-v1.json")), new TypeReference<Map<String, Object>>() { }));
        var producer = fragment.producer();
        // Test-local completion enables executor tests; deployable FULL assembly is verified separately from packaged JARs.
        return new PermissionManifest(fragment.schemaVersion(), fragment.application(),
                new PermissionManifest.Producer(producer.id(), producer.kind(), PermissionManifest.SnapshotMode.FULL,
                        producer.ownedKeyPrefixes(), producer.expectedModules(), producer.scannedModules(), false),
                fragment.artifact(), fragment.permissionDefinitions(), fragment.permissionReferences(), fragment.resources(),
                fragment.resourceReferences(), fragment.messages());
    }

    private void authenticate(PermissionManifest manifest, Set<String> permissions) {
        var digests = new PermissionManifestCanonicalizer().digests(manifest);
        var view = new EffectivePermissionSnapshot("https://idp.example/tenants/20", USER.subject(), SCOPE, "1", "2", "3",
                new EffectivePermissionSnapshot.ActiveRoleContext("yaoguang", List.of("50"), List.of("50")),
                permissions.stream().sorted().toList(), NOW.plusSeconds(30));
        var snapshot = new SystemAuthorizationSnapshot("20", USER.subject(), "60", "yaoguang", 2, 3, List.of("50"), null, null,
                permissions, manifest.resources().stream().map(PermissionManifest.ResourceDefinition::key).collect(java.util.stream.Collectors.toSet()),
                Map.of(), Map.of(), "checksum", NOW, view.snapshotExpiresAt(), view,
                new RuntimePolicyBinding("yaoguang-java", "1", ARTIFACT, digests.policyDigest(), digests.manifestDigest()));
        SecurityContextHolder.getContext().setAuthentication(new Rbac3AuthenticationToken(new Rbac3UserDetails(USER, snapshot),
                permissions.stream().map(value -> new org.springframework.security.core.authority.SimpleGrantedAuthority("SCOPE_" + value)).toList()));
    }
}
