package top.egon.cola.component.yaoguang.admin.wiki.domain.vo;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.FieldPermissions;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionGroup;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
import top.egon.cola.component.yaoguang.admin.wiki.domain.vo.WikiSourceReadVO;
import top.egon.cola.component.yaoguang.admin.wiki.domain.enums.WikiPublicationPolicyEnum;
import top.egon.cola.component.yaoguang.admin.wiki.domain.enums.WikiPublicationStatusEnum;
import top.egon.cola.component.yaoguang.admin.wiki.domain.enums.WikiReviewStatusEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 中文说明：本 HTTP 操作投影使用知识 read SERVICE Scope，保持原 JSON 形状并与内部持久化载体分离。
 * English summary: Uses the operation's knowledge read SERVICE scope without changing the JSON shape.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class WikiPageReadVO {

    /** 页面稳定 ID / page id. */
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.id", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.id",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String id;

    /** 所属知识库 ID / owning KB id. */
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.kbId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.kbId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String kbId;

    /** 逻辑地址，不是磁盘路径 / logical slug, never a disk path. */
    @NotBlank
    @Pattern(regexp = "^[a-z0-9][a-z0-9-]{0,63}$")
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.slug", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.slug",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String slug;

    /** 页面标题 1–128 / page title. */
    @NotBlank
    @Size(max = 128)
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.title", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.title",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String title;

    /** 草稿版本 ID，可 null / draft revision id, nullable. */
    @Schema(nullable = true)
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.draftRevisionId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.draftRevisionId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String draftRevisionId;

    /** 已发布版本 ID，null 表示未发布 / published revision id, null when unpublished. */
    @Schema(nullable = true)
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.publishedRevisionId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.publishedRevisionId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String publishedRevisionId;

    /** 所返回 contentRevision 的发布状态；无正文 revision 时 null / publication status of the returned content revision. */
    @Schema(nullable = true)
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.publicationStatus", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.publicationStatus",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private WikiPublicationStatusEnum publicationStatus;

    /** 所返回 contentRevision 的审核状态；无正文 revision 时 null / review status of the returned content revision. */
    @Schema(nullable = true)
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.reviewStatus", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.reviewStatus",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private WikiReviewStatusEnum reviewStatus;

    /** 此 revision 创建时冻结的策略快照 / policy snapshot frozen when this revision was created. */
    @Schema(nullable = true)
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.publicationPolicySnapshot", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.publicationPolicySnapshot",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private WikiPublicationPolicyEnum publicationPolicySnapshot;

    /** 状态 CAS 版本，与 page.revision 不同；无正文 revision 时 null / publication CAS version, distinct from page revision. */
    @Schema(nullable = true)
    @Min(1)
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.publicationVersion", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.publicationVersion",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private Long publicationVersion;

    /** 最近安全发布失败码，无错误为 null，不含上游正文 / latest safe publication failure code, null when none. */
    @Schema(nullable = true)
    @Size(max = 128)
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.publicationErrorCode", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.publicationErrorCode",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String publicationErrorCode;

    /** 当前返回正文对应版本 ID，非 page 乐观计数 / content revision id the returned body belongs to. */
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{0,19}$")
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.contentRevisionId", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.contentRevisionId",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String contentRevisionId;

    /** 原始 Markdown，合同上限 131072 字节 / raw markdown bounded by 131072 UTF-8 bytes. */
    @NotBlank
    @Size(max = 131_072)
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.markdown", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.markdown",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private String markdown;

    /** 最多 20 个唯一标签 / at most 20 unique tags. */
    @NotNull
    @Size(max = 20)
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.tags", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.tags",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.ARRAY,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private List<@NotBlank @Size(min = 1, max = 32) String> tags;

    /** 同 KB 页面稳定 ID，最多 100 / at most 100 same-KB page ids. */
    @NotNull
    @Size(max = 100)
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.links", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.links",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.ARRAY,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private List<@Pattern(regexp = "^[1-9][0-9]{0,19}$") String> links;

    /** 证据列表 1–100 / 1–100 evidence sources. */
    @NotNull
    @Size(min = 1, max = 100)
    @Valid
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.sources", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.sources",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.ARRAY,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private List<WikiSourceReadVO> sources;

    /** 任一来源非当前 active 或被删为 true / true when any source is stale or removed. */
    @NotNull
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.stale", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.stale",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.BOOLEAN,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private Boolean stale;

    /** 已提交乐观版本，正整数 / committed optimistic revision, positive. */
    @NotNull
    @Min(1)
    @PermissionResource(key = "yaoguang.field.WikiPageReadVO.revision", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.revision",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:read")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            plainRead = @PermissionGroup(codes = "yuheng:knowledge:read"))
    private Long revision;

    /**
     * 中文说明：返回 tags 的不可变快照副本，未设置时输出 {@code []}。
     * English summary: Returns an unmodifiable copy of the tags, or an empty list so the wire value is never null.
     */
    public List<String> getTags() {
        return tags == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(tags));
    }

    /**
     * 中文说明：写入时复制 tags。
     * English summary: Defensively copies the incoming tags.
     */
    public WikiPageReadVO setTags(List<String> tags) {
        this.tags = tags == null ? null : new ArrayList<>(tags);
        return this;
    }

    /**
     * 中文说明：返回 links 的不可变快照副本，未设置时输出 {@code []}。
     * English summary: Returns an unmodifiable copy of the link ids, or an empty list.
     */
    public List<String> getLinks() {
        return links == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(links));
    }

    /**
     * 中文说明：写入时复制 links。
     * English summary: Defensively copies the incoming link ids.
     */
    public WikiPageReadVO setLinks(List<String> links) {
        this.links = links == null ? null : new ArrayList<>(links);
        return this;
    }

    /**
     * 中文说明：返回 sources 的不可变快照副本，未设置时输出 {@code []}。
     * English summary: Returns an unmodifiable copy of the sources, or an empty list.
     */
    public List<WikiSourceReadVO> getSources() {
        return sources == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(sources));
    }

    /**
     * 中文说明：写入时复制 sources；元素仍由 {@code @Valid} 逐个校验。
     * English summary: Defensively copies the incoming sources; elements stay validated by {@code @Valid}.
     */
    public WikiPageReadVO setSources(List<WikiSourceReadVO> sources) {
        this.sources = sources == null ? null : new ArrayList<>(sources);
        return this;
    }
}
