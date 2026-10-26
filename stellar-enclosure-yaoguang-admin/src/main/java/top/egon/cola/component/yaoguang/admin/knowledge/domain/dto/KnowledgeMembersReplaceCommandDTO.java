package top.egon.cola.component.yaoguang.admin.knowledge.domain.dto;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.FieldPermissions;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionGroup;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 中文说明：本 HTTP 操作投影使用知识 admin SERVICE Scope，保持原 JSON 形状并与内部持久化载体分离。
 * English summary: Uses the operation's knowledge admin SERVICE scope without changing the JSON shape.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class KnowledgeMembersReplaceCommandDTO {

    /** 完整成员集合，1–200 条，缺少字段与空数组都被拒绝 / complete member set, 1–200 records; both a missing field and an empty array are rejected. */
    @NotEmpty
    @Size(max = 200)
    @Valid
    @PermissionResource(key = "yaoguang.field.KnowledgeMembersReplaceCommandDTO.members", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.members",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:admin")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.ARRAY,
            write = @PermissionGroup(codes = "yuheng:knowledge:admin"))
    private List<KnowledgeMemberWriteDTO> members;

    /** 必须等于库中当前 revision 的正整数，不符返回 409 / must equal the stored revision, a positive integer, and a mismatch yields 409. */
    @NotNull
    @Min(1)
    @PermissionResource(key = "yaoguang.field.KnowledgeMembersReplaceCommandDTO.expectedRevision", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.expectedRevision",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:admin")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            write = @PermissionGroup(codes = "yuheng:knowledge:admin"))
    private Long expectedRevision;

    /**
     * 中文说明：返回 members 的不可变快照副本，未传时为 null 交由 {@code @NotEmpty} 拒绝。
     * English summary: Returns an immutable snapshot copy of the members, staying null so {@code @NotEmpty} rejects it.
     */
    public List<KnowledgeMemberWriteDTO> getMembers() {
        return members == null ? null : Collections.unmodifiableList(new ArrayList<>(members));
    }

    /**
     * 中文说明：写入时复制 members，阻断校验通过后的调用方原地修改。
     * English summary: Defensively copies the incoming members so the caller cannot mutate a validated command.
     */
    public KnowledgeMembersReplaceCommandDTO setMembers(List<KnowledgeMemberWriteDTO> members) {
        this.members = members == null ? null : new ArrayList<>(members);
        return this;
    }
}
