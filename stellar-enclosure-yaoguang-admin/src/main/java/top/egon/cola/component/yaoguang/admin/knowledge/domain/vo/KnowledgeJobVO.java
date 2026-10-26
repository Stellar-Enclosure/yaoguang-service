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
 * 中文说明：{@code KnowledgeJobVO} 是原 API-018/019/020/021/024 共享的持久任务投影；
 * 按合同不暴露 leaseToken、leaseOwner、payload、result 或任何上游原始错误正文。
 * English summary: {@code KnowledgeJobVO} is the shared job projection of API-018/019/020/021/024; lease token,
 * lease owner, frozen payload, result and raw upstream error text are deliberately not exposed.
 *
 * 用法 / Usage: stage 是离散业务阶段而非伪百分比；errorCode 只在失败态出现，
 * 202 之后按 status 轮询，终态停止。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class KnowledgeJobVO {

    /** 持久任务 ID / persistent job id. */
    @PermissionResource(key = "yaoguang.field.KnowledgeJobVO.id", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.id",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    private String id;

    /** 所属知识库 ID / owning KB id. */
    @PermissionResource(key = "yaoguang.field.KnowledgeJobVO.kbId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.kbId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    private String kbId;

    /** DOCUMENT_INGEST/WIKI_GENERATE / job type. */
    @PermissionResource(key = "yaoguang.field.KnowledgeJobVO.type", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.type",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotNull
    private KnowledgeJobTypeEnum type;

    /** 任务关联 document/page/KB 稳定 ID / related document, page or KB id. */
    @PermissionResource(key = "yaoguang.field.KnowledgeJobVO.resourceId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.resourceId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    private String resourceId;

    /** §7 完整任务状态枚举 / full job lifecycle status of §7. */
    @PermissionResource(key = "yaoguang.field.KnowledgeJobVO.status", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.status",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotNull
    private KnowledgeJobStatusEnum status;

    /** QUEUED/PARSE/EMBED/GENERATE/PUBLISH/DONE / discrete business stage. */
    @PermissionResource(key = "yaoguang.field.KnowledgeJobVO.stage", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.stage",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotNull
    private KnowledgeJobStageEnum stage;

    /** 当前任务乐观版本，retry 的 expectedRevision 引用它 / task optimistic revision used by retry. */
    @PermissionResource(key = "yaoguang.field.KnowledgeJobVO.revision", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.revision",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotNull
    @Min(1)
    private Long revision;

    /** 已开始尝试次数 0–3 / started attempts, 0–3. */
    @PermissionResource(key = "yaoguang.field.KnowledgeJobVO.attempt", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.attempt",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @NotNull
    @Min(0)
    @Max(3)
    private Integer attempt;

    /** 安全稳定失败码，非失败时 null，不含模型原始错误正文 / safe failure code, null unless failed. */
    @PermissionResource(key = "yaoguang.field.KnowledgeJobVO.errorCode", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.errorCode",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @Schema(nullable = true)
    @Size(max = 128)
    private String errorCode;

    /** 服务端创建时刻，UTC / server-side UTC creation instant. */
    @PermissionResource(key = "yaoguang.field.KnowledgeJobVO.createdAt", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.createdAt",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING)
    @NotNull
    private Instant createdAt;

    /** 最近服务端更新时间，UTC / last server-side update instant. */
    @PermissionResource(key = "yaoguang.field.KnowledgeJobVO.updatedAt", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.updatedAt",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING)
    @NotNull
    private Instant updatedAt;
}
