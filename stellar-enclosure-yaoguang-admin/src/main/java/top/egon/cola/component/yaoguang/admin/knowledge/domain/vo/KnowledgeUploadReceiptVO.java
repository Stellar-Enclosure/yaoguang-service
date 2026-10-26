package top.egon.cola.component.yaoguang.admin.knowledge.domain.vo;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.FieldPermissions;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionGroup;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.enums.KnowledgeJobStatusEnum;

/**
 * 中文说明：{@code KnowledgeUploadReceiptVO} 固定 API-015 的四字段 202 wire shape。
 * English summary: {@code KnowledgeUploadReceiptVO} fixes the four-field API-015 202 wire shape.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class KnowledgeUploadReceiptVO {

    @PermissionResource(key = "yaoguang.field.KnowledgeUploadReceiptVO.documentId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.documentId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    private String documentId;

    @PermissionResource(key = "yaoguang.field.KnowledgeUploadReceiptVO.revisionId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.revisionId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    private String revisionId;

    @PermissionResource(key = "yaoguang.field.KnowledgeUploadReceiptVO.jobId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.jobId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    private String jobId;

    @PermissionResource(key = "yaoguang.field.KnowledgeUploadReceiptVO.status", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.status",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotNull
    @Schema(implementation = KnowledgeJobStatusEnum.class)
    private KnowledgeJobStatusEnum status;
}
