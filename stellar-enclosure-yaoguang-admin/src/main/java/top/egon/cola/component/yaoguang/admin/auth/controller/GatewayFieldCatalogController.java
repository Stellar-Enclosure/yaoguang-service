package top.egon.cola.component.yaoguang.admin.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import top.egon.cola.component.yuheng.openapi.annotation.EgonApiCatalog;
import top.egon.cola.component.yuheng.openapi.annotation.EgonGatewayPolicy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import top.egon.cola.component.common.core.pojo.ResultRecord;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.FieldCatalogSnapshot;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceAccess;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;
import top.egon.cola.platform.tianquan.jianshen.starter.permission.PermissionFieldCatalog;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.dto.KnowledgeUploadCommandDTO;

import java.util.Set;

/** Projects active FIELD policies with the host's actual JSON and explicit adapter input bindings. */
@RestController
@EgonApiCatalog(businessDomainCode = "xingyuan", businessDomainName = "平台治理域",
        entityDomainCode = "yaoguang-admin", entityDomainName = "yaoguang 管理实体域", interfaceGroupCode = "yaoguang-admin")
public class GatewayFieldCatalogController {
    private static final String KEY = "yaoguang.api.authorization.fields";
    private final PermissionFieldCatalog fields;
    private final ObjectProvider<RequestMappingHandlerAdapter> adapters;
    private final ObjectProvider<RequestMappingHandlerMapping> routes;

    public GatewayFieldCatalogController(PermissionFieldCatalog fields,
            @Qualifier("requestMappingHandlerAdapter") ObjectProvider<RequestMappingHandlerAdapter> adapters,
            @Qualifier("requestMappingHandlerMapping") ObjectProvider<RequestMappingHandlerMapping> routes) {
        this.fields = fields;
        this.adapters = adapters;
        this.routes = routes;
    }

    @PermissionResource(key = KEY, type = ResourceType.API, labelPage = "yaoguang.permissions",
            labelKey = "api.authorization.fields", access = ResourceAccess.PUBLIC,
            allowedPrincipals = PermissionManifest.PrincipalType.USER)
    @Operation(operationId = "yaoguangGetFieldCatalog", summary = "Read current verified field policy and input binding view")
    @EgonGatewayPolicy(exposure = EgonGatewayPolicy.Exposure.EXTERNAL)
    @GetMapping(path = "/api/v1/authorization/field-catalog", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultRecord<FieldCatalogSnapshot>> current(HttpServletRequest request) {
        return ResponseEntity.ok().header(HttpHeaders.CACHE_CONTROL, "no-store")
                .body(ResultRecord.success(fields.currentForHttp(request, KEY,
                        adapters.getObject().getMessageConverters(), routes.getObject().getHandlerMethods().values(),
                        Set.of("top.egon.cola.component.yaoguang."), Set.of(KnowledgeUploadCommandDTO.class))));
    }
}
