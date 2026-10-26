package top.egon.cola.component.yaoguang.admin.knowledge.domain.vo;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.FieldPermissions;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionGroup;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;

/**
 * 中文说明：{@code KnowledgeDocumentVO} 是原 API-014 的文档与摄取状态投影；{@code status} 为服务端派生
 * READY/PROCESSING/FAILED，本轮批准枚举集合里没有 document 生命周期枚举，因此保持原 wire 字符串。
 * English summary: {@code KnowledgeDocumentVO} is the API-014 document projection whose derived status keeps the
 * original READY/PROCESSING/FAILED wire strings because no document-status enum is in the approved set.
 *
 * 用法 / Usage: activeRevisionId 在首次摄取成功前为 null，不用空字符串代替；
 * 重建失败但仍有 active 时 status 仍为 READY，失败细节只在 latestJobId 对应任务中展示。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class KnowledgeDocumentVO {

    /** 资料稳定 ID / stable document id. */
    @PermissionResource(key = "yaoguang.field.KnowledgeDocumentVO.id", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.id",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    private String id;

    /** 所属知识库 ID，服务端验证关系 / owning KB id, relations validated server-side. */
    @PermissionResource(key = "yaoguang.field.KnowledgeDocumentVO.kbId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.kbId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    private String kbId;

    /** 原始文件显示名，1–255，不作为路径 / original display file name, never a path. */
    @PermissionResource(key = "yaoguang.field.KnowledgeDocumentVO.fileName", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.fileName",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotBlank
    @Size(max = 255)
    private String fileName;

    /** 已可见版本 ID，首次摄取未成功时为 null / active revision id, null until the first successful ingest. */
    @PermissionResource(key = "yaoguang.field.KnowledgeDocumentVO.activeRevisionId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.activeRevisionId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @Schema(nullable = true)
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    private String activeRevisionId;

    /** 最近摄取任务 ID，可独立展示进度 / latest ingest job id for standalone progress polling. */
    @PermissionResource(key = "yaoguang.field.KnowledgeDocumentVO.latestJobId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.latestJobId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    private String latestJobId;

    /** 已提交乐观版本，正整数 / committed optimistic revision, positive. */
    @PermissionResource(key = "yaoguang.field.KnowledgeDocumentVO.revision", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.revision",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotNull
    @Min(1)
    private Long revision;

    /** 服务端派生 READY/PROCESSING/FAILED / server-derived READY, PROCESSING or FAILED. */
    @PermissionResource(key = "yaoguang.field.KnowledgeDocumentVO.status", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.status",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotBlank
    @Pattern(regexp = "^(READY|PROCESSING|FAILED)$")
    private String status;

    /** 服务端创建时刻，UTC / server-side UTC creation instant. */
    @PermissionResource(key = "yaoguang.field.KnowledgeDocumentVO.createdAt", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.createdAt",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING)
    @NotNull
    private Instant createdAt;
}
