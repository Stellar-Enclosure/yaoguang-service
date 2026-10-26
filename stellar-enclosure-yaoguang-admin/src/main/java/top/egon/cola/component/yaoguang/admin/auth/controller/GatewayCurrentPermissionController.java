package top.egon.cola.component.yaoguang.admin.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import top.egon.cola.component.yuheng.openapi.annotation.EgonApiCatalog;
import top.egon.cola.component.yuheng.openapi.annotation.EgonGatewayPolicy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.egon.cola.component.common.core.pojo.ResultRecord;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.EffectivePermissionSnapshot;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceAccess;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;
import top.egon.cola.platform.tianquan.jianshen.starter.permission.CurrentEffectivePermissionView;

/** Exposes only the current verified USER view for this host's fixed catalog and deployed artifact. */
@RestController
@EgonApiCatalog(businessDomainCode = "xingyuan", businessDomainName = "平台治理域",
        entityDomainCode = "yaoguang-admin", entityDomainName = "yaoguang 管理实体域", interfaceGroupCode = "yaoguang-admin")
@lombok.RequiredArgsConstructor
public class GatewayCurrentPermissionController {
    private final CurrentEffectivePermissionView permissions;

    @PermissionResource(key = "yaoguang.api.authorization.current", type = ResourceType.API,
            labelPage = "yaoguang.permissions", labelKey = "api.authorization.current", access = ResourceAccess.PUBLIC,
            allowedPrincipals = PermissionManifest.PrincipalType.USER)
    @Operation(operationId = "yaoguangGetCurrentPermissions", summary = "Read current verified permission view")
    @EgonGatewayPolicy(exposure = EgonGatewayPolicy.Exposure.EXTERNAL)
    @GetMapping(path = "/api/v1/authorization/effective-permissions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultRecord<EffectivePermissionSnapshot>> current(HttpServletRequest request) {
        return ResponseEntity.ok().header(HttpHeaders.CACHE_CONTROL, "no-store")
                .body(ResultRecord.success(permissions.current(request, "yaoguang.api.authorization.current")));
    }
}
