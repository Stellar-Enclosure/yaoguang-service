package top.egon.cola.component.yaoguang.admin.knowledge.domain.dto;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.FieldPermissions;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionGroup;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.enums.KnowledgeEgressPolicyEnum;
import top.egon.cola.component.yaoguang.admin.shared.domain.validation.CreateGroup;
import top.egon.cola.component.yaoguang.admin.shared.domain.validation.UpdateGroup;

/**
 * 中文说明：本 HTTP 操作投影使用知识 write SERVICE Scope，保持原 JSON 形状并与内部持久化载体分离。
 * English summary: Uses the operation's knowledge write SERVICE scope without changing the JSON shape.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class KnowledgeBaseCreateCommandDTO {

    /** 展示名称，trim 后 1–128 字符 / trimmed display name. */
    @NotBlank(groups = {Default.class, CreateGroup.class, UpdateGroup.class})
    @Size(max = 128, groups = {Default.class, CreateGroup.class, UpdateGroup.class})
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreateCommandDTO.name", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.name",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private String name;

    /** 描述，0–2000 字符，可空不可 null / description, empty allowed but never null. */
    @NotNull(groups = {Default.class, CreateGroup.class, UpdateGroup.class})
    @Size(max = 2_000, groups = {Default.class, CreateGroup.class, UpdateGroup.class})
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreateCommandDTO.description", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.description",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private String description;

    /** 生成上下文出域策略，embedding 始终 LOCAL / egress policy for generated context. */
    @NotNull(groups = {Default.class, CreateGroup.class, UpdateGroup.class})
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreateCommandDTO.egressPolicy", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.egressPolicy",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private KnowledgeEgressPolicyEnum egressPolicy;

    /** 允许的 CHAT alias，命令时服务端复核 / allowed CHAT alias, revalidated on command. */
    @NotBlank(groups = {Default.class, CreateGroup.class, UpdateGroup.class})
    @Size(max = 64, groups = {Default.class, CreateGroup.class, UpdateGroup.class})
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreateCommandDTO.chatModel", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.chatModel",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private String chatModel;

    /** EMBEDDING alias，已有索引时不得原地改变空间 / embedding alias, immutable space once indexed. */
    @NotBlank(groups = {Default.class, CreateGroup.class, UpdateGroup.class})
    @Size(max = 64, groups = {Default.class, CreateGroup.class, UpdateGroup.class})
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreateCommandDTO.embeddingModel", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.embeddingModel",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private String embeddingModel;

    /** 创建不传；替换须等于当前 revision，正整数 / absent on create, current revision on replace. */
    @Min(value = 0, groups = {Default.class, UpdateGroup.class})
    @NotNull(groups = UpdateGroup.class)
    @PermissionResource(key = "yaoguang.field.KnowledgeBaseCreateCommandDTO.expectedRevision", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.expectedRevision",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private Long expectedRevision;
}
