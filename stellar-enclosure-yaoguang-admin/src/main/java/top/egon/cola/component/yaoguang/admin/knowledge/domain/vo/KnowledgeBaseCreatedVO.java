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
import top.egon.cola.component.yaoguang.admin.knowledge.domain.enums.KnowledgeEgressPolicyEnum;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.enums.KnowledgeMemberRoleEnum;

/**
 * 中文说明：本 HTTP 操作投影使用知识 write SERVICE Scope，保持原 JSON 形状并与内部持久化载体分离。
 * English summary: Uses the operation's knowledge write SERVICE scope without changing the JSON shape.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class KnowledgeBaseCreatedVO {

    /** 服务端 Snowflake 十进制字符串 / server-assigned decimal string id. */
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreatedVO.id", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.id",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String id;

    /** 展示名称 / trimmed display name. */
    @NotBlank
    @Size(max = 128)
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreatedVO.name", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.name",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String name;

    /** 描述，0–2000 字符，可空字符串但不可 null / description, empty allowed but never null. */
    @NotNull
    @Size(max = 2_000)
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreatedVO.description", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.description",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String description;

    /** 创建时可信身份派生的 owner，不接受 body 自报 / owner derived from the trusted identity at creation. */
    @NotBlank
    @Size(max = 128)
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreatedVO.ownerActorId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.ownerActorId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String ownerActorId;

    /** 当前主体在 KB 中的角色 / current subject role inside this KB. */
    @NotNull
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreatedVO.myRole", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.myRole",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private KnowledgeMemberRoleEnum myRole;

    /** 生成上下文出域策略 / egress policy for generated context. */
    @NotNull
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreatedVO.egressPolicy", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.egressPolicy",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private KnowledgeEgressPolicyEnum egressPolicy;

    /** 允许的 CHAT alias / allowed CHAT alias. */
    @NotBlank
    @Size(max = 64)
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreatedVO.chatModel", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.chatModel",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String chatModel;

    /** EMBEDDING alias / embedding alias. */
    @NotBlank
    @Size(max = 64)
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreatedVO.embeddingModel", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.embeddingModel",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String embeddingModel;

    /** 创建时冻结的不可变嵌入空间标识 / embedding space identity frozen at creation. */
    @NotBlank
    @Size(max = 128)
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreatedVO.embeddingSpaceId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.embeddingSpaceId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String embeddingSpaceId;

    /** 冻结的正维度 D，不随模型默认变化 / frozen positive dimension. */
    @NotNull
    @Min(1)
    @Max(16_000)
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreatedVO.dimensions", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.dimensions",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private Integer dimensions;

    /** 已提交乐观版本，正整数 / committed optimistic revision, positive. */
    @NotNull
    @Min(1)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreatedVO.revision", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.revision",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private Long revision;
}
