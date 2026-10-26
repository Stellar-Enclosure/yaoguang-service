package top.egon.cola.component.yaoguang.admin.knowledge.domain.vo;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.FieldPermissions;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionGroup;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.enums.KnowledgeMemberRoleEnum;

/**
 * 中文说明：本 HTTP 操作投影使用知识 read SERVICE Scope，保持原 JSON 形状并与内部持久化载体分离。
 * English summary: Uses the operation's knowledge read SERVICE scope without changing the JSON shape.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class KnowledgeMemberReadVO {

    /** 企业身份 subject，配置时校验稳定语法 / verified enterprise identity subject. */
    @NotBlank
    @Size(max = 128)
    @PermissionResource(key = "yaoguang.field.KnowledgeMemberReadVO.actorId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.actorId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String actorId;

    /** READER/EDITOR/OWNER，本版不支持 owner 转移 / member role; owner transfer is out of scope. */
    @NotNull
    @PermissionResource(key = "yaoguang.field.KnowledgeMemberReadVO.role", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.role",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private KnowledgeMemberRoleEnum role;
}
