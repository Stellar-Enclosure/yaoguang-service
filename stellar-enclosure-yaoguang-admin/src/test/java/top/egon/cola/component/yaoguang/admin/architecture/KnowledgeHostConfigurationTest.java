package top.egon.cola.component.yaoguang.admin.architecture;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import top.egon.cola.component.yaoguang.admin.bootstrap.YaoguangAdminConfiguration;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class KnowledgeHostConfigurationTest {
    @Test
    void compiledUserPoliciesRetainTheExplicitOAuthScopeUpperBound() {
        var loader = new org.springframework.beans.factory.config.YamlPropertiesFactoryBean();
        loader.setResources(new org.springframework.core.io.ClassPathResource("application.yml"));
        var properties = loader.getObject();
        assertThat(properties.getProperty("egon.cola.platform.tianquan.jianshen.system-code")).isEqualTo("yaoguang");
        assertThat(properties.getProperty("egon.cola.platform.tianquan.jianshen.runtime.catalog-scopes[0].application-code"))
                .isEqualTo("yaoguang");
        String prefix = "egon.cola.platform.tianquan.jianshen.user-scope-policy.";
        assertThat(properties.getProperty(prefix + "enabled")).isEqualTo("true");
        for (String action : java.util.List.of("read", "write", "admin")) {
            String code = "yuheng:knowledge:" + action;
            assertThat(properties.getProperty(prefix + "permission-scopes[" + code + "][0]")).isEqualTo(code);
        }
    }

    @Test
    void independentHostEnablesWorkersWithoutAnAuthorizationRedisConnection() {
        new ApplicationContextRunner().withUserConfiguration(YaoguangAdminConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(ScheduledAnnotationBeanPostProcessor.class);
                    assertThat(context).doesNotHaveBean(RedissonClient.class);
                    assertThat(context.getBean("jacksonObjectMapper", ObjectMapper.class)
                            .writeValueAsString(Instant.parse("2026-10-20T00:00:00Z")))
                            .isEqualTo("\"2026-10-20T00:00:00Z\"");
                });
    }
}
