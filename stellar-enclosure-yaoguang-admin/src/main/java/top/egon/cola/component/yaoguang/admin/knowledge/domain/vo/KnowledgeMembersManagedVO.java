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
import top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeMemberManagedVO;

/**
 * 中文说明：本 HTTP 操作投影使用知识 admin SERVICE Scope，保持原 JSON 形状并与内部持久化载体分离。
 * English summary: Uses the operation's knowledge admin SERVICE scope without changing the JSON shape.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class KnowledgeMembersManagedVO {

    @NotNull
    @Size(max = 100)
    @Valid
    @PermissionResource(key = "yaoguang.field.KnowledgeMembersManagedVO.members", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.members",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:admin")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.ARRAY,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private List<KnowledgeMemberManagedVO> members;

    @NotNull
    @Min(1)
    @Schema(minimum = "1")
    @PermissionResource(key = "yaoguang.field.KnowledgeMembersManagedVO.revision", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.revision",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:admin")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private Long revision;

    public List<KnowledgeMemberManagedVO> getMembers() {
        return members == null ? null : Collections.unmodifiableList(new ArrayList<>(members));
    }

    public KnowledgeMembersManagedVO setMembers(List<KnowledgeMemberManagedVO> members) {
        this.members = members == null ? null : new ArrayList<>(members);
        return this;
    }
}
