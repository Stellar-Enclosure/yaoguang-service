package top.egon.cola.component.yaoguang.admin.knowledge.domain.vo;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.FieldPermissions;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionGroup;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeMemberReadVO;

/**
 * 中文说明：本 HTTP 操作投影使用知识 read SERVICE Scope，保持原 JSON 形状并与内部持久化载体分离。
 * English summary: Uses the operation's knowledge read SERVICE scope without changing the JSON shape.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class KnowledgeMembersReadVO {

    @NotNull
    @Size(max = 100)
    @Valid
    @PermissionResource(key = "yaoguang.field.KnowledgeMembersReadVO.members", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.members",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.ARRAY,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private List<KnowledgeMemberReadVO> members;

    @NotNull
    @Min(1)
    @Schema(minimum = "1")
    @PermissionResource(key = "yaoguang.field.KnowledgeMembersReadVO.revision", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.revision",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private Long revision;

    public List<KnowledgeMemberReadVO> getMembers() {
        return members == null ? null : Collections.unmodifiableList(new ArrayList<>(members));
    }

    public KnowledgeMembersReadVO setMembers(List<KnowledgeMemberReadVO> members) {
        this.members = members == null ? null : new ArrayList<>(members);
        return this;
    }
}
