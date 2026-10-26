package top.egon.cola.component.yaoguang.admin.permission;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import top.egon.cola.component.yaoguang.admin.config.GatewayAdminServiceAuthorityFilter;
import top.egon.cola.platform.tianquan.shoubing.contract.ServiceIdentityPrincipal;
import top.egon.cola.platform.tianquan.shoubing.contract.ServiceTokenContext;
import top.egon.cola.platform.tianquan.shoubing.starter.security.IdpAuthenticationToken;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class YaoguangServiceIdentityAdapterTest {
    @AfterEach void clear() { SecurityContextHolder.clearContext(); }

    @Test
    void adapterPreservesVerifiedIssuerAndOnlyProjectsItsExactScopes() throws Exception {
        Instant now = Instant.parse("2026-10-25T00:00:00Z");
        URI issuer = URI.create("https://idp.example/tenants/20");
        var service = new ServiceIdentityPrincipal("publisher", "20", "publisher", "token-1",
                URI.create("https://api.example/yaoguang"), 1, Set.of("yuheng:knowledge:read", "unrelated:scope"),
                "xingyuan", "publisher", "test", "credential-1", now, now.plusSeconds(60), "publisher", ServiceTokenContext.TENANT);
        var original = new IdpAuthenticationToken(service, List.of(new SimpleGrantedAuthority("SCOPE_yuheng:knowledge:read"),
                new SimpleGrantedAuthority("CAP_*")), issuer);
        Object auditDetails = new Object();
        original.setDetails(auditDetails);
        SecurityContextHolder.getContext().setAuthentication(original);
        new GatewayAdminServiceAuthorityFilter().doFilter(new MockHttpServletRequest(), new MockHttpServletResponse(), (request, response) -> {
            var adapted = SecurityContextHolder.getContext().getAuthentication();
            assertThat(adapted).isInstanceOf(IdpAuthenticationToken.class);
            assertThat(((IdpAuthenticationToken) adapted).verifiedIssuer()).isEqualTo(issuer);
            assertThat(adapted.getPrincipal()).isSameAs(service);
            assertThat(adapted.getDetails()).isSameAs(auditDetails);
            assertThat(adapted.getAuthorities()).extracting(value -> value.getAuthority())
                    .containsExactlyInAnyOrder("SCOPE_yuheng:knowledge:read", "CAP_yuheng:knowledge:read");
        });
    }
}
