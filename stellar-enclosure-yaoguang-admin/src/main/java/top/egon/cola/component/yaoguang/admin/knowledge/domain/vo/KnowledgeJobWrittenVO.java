package top.egon.cola.component.yaoguang.admin.knowledge.domain.vo;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.FieldPermissions;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionGroup;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
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
import top.egon.cola.component.yaoguang.admin.knowledge.domain.enums.KnowledgeJobStageEnum;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.enums.KnowledgeJobStatusEnum;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.enums.KnowledgeJobTypeEnum;

import java.time.Instant;

/**
 * 中文说明：本 HTTP 操作投影使用知识 write SERVICE Scope，保持原 JSON 形状并与内部持久化载体分离。
 * English summary: Uses the operation's knowledge write SERVICE scope without changing the JSON shape.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class KnowledgeJobWrittenVO {

    /** 持久任务 ID / persistent job id. */
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    @PermissionResource(key = "yaoguang.field.KnowledgeJobWrittenVO.id", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.id",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String id;

    /** 所属知识库 ID / owning KB id. */
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    @PermissionResource(key = "yaoguang.field.KnowledgeJobWrittenVO.kbId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.kbId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String kbId;

    /** DOCUMENT_INGEST/WIKI_GENERATE / job type. */
    @NotNull
    @PermissionResource(key = "yaoguang.field.KnowledgeJobWrittenVO.type", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.type",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private KnowledgeJobTypeEnum type;

    /** 任务关联 document/page/KB 稳定 ID / related document, page or KB id. */
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    @PermissionResource(key = "yaoguang.field.KnowledgeJobWrittenVO.resourceId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.resourceId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String resourceId;

    /** §7 完整任务状态枚举 / full job lifecycle status of §7. */
    @NotNull
    @PermissionResource(key = "yaoguang.field.KnowledgeJobWrittenVO.status", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.status",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private KnowledgeJobStatusEnum status;

    /** QUEUED/PARSE/EMBED/GENERATE/PUBLISH/DONE / discrete business stage. */
    @NotNull
    @PermissionResource(key = "yaoguang.field.KnowledgeJobWrittenVO.stage", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.stage",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private KnowledgeJobStageEnum stage;

    /** 当前任务乐观版本，retry 的 expectedRevision 引用它 / task optimistic revision used by retry. */
    @NotNull
    @Min(1)
    @PermissionResource(key = "yaoguang.field.KnowledgeJobWrittenVO.revision", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.revision",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private Long revision;

    /** 已开始尝试次数 0–3 / started attempts, 0–3. */
    @NotNull
    @Min(0)
    @Max(3)
    @PermissionResource(key = "yaoguang.field.KnowledgeJobWrittenVO.attempt", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.attempt",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private Integer attempt;

    /** 安全稳定失败码，非失败时 null，不含模型原始错误正文 / safe failure code, null unless failed. */
    @Schema(nullable = true)
    @Size(max = 128)
    @PermissionResource(key = "yaoguang.field.KnowledgeJobWrittenVO.errorCode", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.errorCode",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String errorCode;

    /** 服务端创建时刻，UTC / server-side UTC creation instant. */
    @NotNull
    @PermissionResource(key = "yaoguang.field.KnowledgeJobWrittenVO.createdAt", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.createdAt",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING)
    private Instant createdAt;

    /** 最近服务端更新时间，UTC / last server-side update instant. */
    @NotNull
    @PermissionResource(key = "yaoguang.field.KnowledgeJobWrittenVO.updatedAt", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.updatedAt",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING)
    private Instant updatedAt;
}
