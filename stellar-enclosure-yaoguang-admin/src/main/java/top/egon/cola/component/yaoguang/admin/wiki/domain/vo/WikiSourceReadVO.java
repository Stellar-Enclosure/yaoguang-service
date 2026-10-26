package top.egon.cola.component.yaoguang.admin.wiki.domain.vo;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.FieldPermissions;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionGroup;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 中文说明：本 HTTP 操作投影使用知识 read SERVICE Scope，保持原 JSON 形状并与内部持久化载体分离。
 * English summary: Uses the operation's knowledge read SERVICE scope without changing the JSON shape.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class WikiSourceReadVO {

    /** 冻结资料版本 ID / frozen document revision id. */
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    @PermissionResource(key = "yaoguang.field.WikiSourceReadVO.documentRevisionId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.documentRevisionId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String documentRevisionId;

    /** 稳定分块 ID，不使用查询列表下标 / stable chunk id, never a result-list index. */
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    @PermissionResource(key = "yaoguang.field.WikiSourceReadVO.chunkId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.chunkId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String chunkId;

    /** 冻结来源 hash，用于 stale 检查 / frozen source hash used for staleness checks. */
    @NotBlank
    @Pattern(regexp = "^[0-9a-f]{64}$")
    @PermissionResource(key = "yaoguang.field.WikiSourceReadVO.sourceHash", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.sourceHash",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String sourceHash;
}
