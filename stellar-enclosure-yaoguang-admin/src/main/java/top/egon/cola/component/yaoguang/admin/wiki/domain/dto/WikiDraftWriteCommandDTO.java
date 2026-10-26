package top.egon.cola.component.yaoguang.admin.wiki.domain.dto;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.PermissionManifest;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.ResourceType;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.FieldPermissions;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionGroup;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionResource;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 中文说明：本 HTTP 操作投影使用知识 write SERVICE Scope，保持原 JSON 形状并与内部持久化载体分离。
 * English summary: Uses the operation's knowledge write SERVICE scope without changing the JSON shape.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class WikiDraftWriteCommandDTO {

    /** 页面标题 1–128 / page title, 1–128 characters. */
    @NotBlank
    @Size(max = 128)
    @PermissionResource(key = "yaoguang.field.WikiDraftWriteCommandDTO.title", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.title",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private String title;

    /** 原始 Markdown，合同上限 131072 字节；注解只约束字符数，字节上限由 Service 复核 / raw markdown bounded by 131072 UTF-8 bytes. */
    @NotBlank
    @Size(max = 131_072)
    @PermissionResource(key = "yaoguang.field.WikiDraftWriteCommandDTO.markdown", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.markdown",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.STRING,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private String markdown;

    /** 最多 20 个唯一标签，每个 1–32 字符 / at most 20 unique tags of 1–32 characters. */
    @NotNull
    @Size(max = 20)
    @PermissionResource(key = "yaoguang.field.WikiDraftWriteCommandDTO.tags", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.tags",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.ARRAY,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private List<@NotBlank @Size(min = 1, max = 32) String> tags;

    /** 同 KB 页面稳定 ID，最多 100 / at most 100 page ids inside the same KB. */
    @NotNull
    @Size(max = 100)
    @PermissionResource(key = "yaoguang.field.WikiDraftWriteCommandDTO.links", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.links",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.ARRAY,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private List<@Pattern(regexp = "^[1-9][0-9]{0,19}$") String> links;

    /** 证据列表 1–100，仅来自任务冻结且可读来源 / 1–100 frozen readable sources. */
    @NotNull
    @Size(min = 1, max = 100)
    @Valid
    @PermissionResource(key = "yaoguang.field.WikiDraftWriteCommandDTO.sources", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.sources",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.ARRAY,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private List<WikiSourceWriteDTO> sources;

    /** 须等于页面当前 revision / must equal the current page revision. */
    @NotNull
    @Min(0)
    @PermissionResource(key = "yaoguang.field.WikiDraftWriteCommandDTO.expectedRevision", type = ResourceType.FIELD,
            labelPage = "yaoguang.permissions", labelKey = "field.expectedRevision",
            allowedPrincipals = {PermissionManifest.PrincipalType.USER, PermissionManifest.PrincipalType.SERVICE},
            oauthScopes = "yuheng:knowledge:write")
    @FieldPermissions(valueType = PermissionManifest.JsonFieldType.NUMBER,
            write = @PermissionGroup(codes = "yuheng:knowledge:write"))
    private Long expectedRevision;

    /**
     * 中文说明：返回 tags 的不可变快照副本。
     * English summary: Returns an unmodifiable copy of the tags.
     */
    public List<String> getTags() {
        return tags == null ? null : Collections.unmodifiableList(new ArrayList<>(tags));
    }

    /**
     * 中文说明：写入时复制 tags，避免异步任务期间被外部修改。
     * English summary: Defensively copies the incoming tags.
     */
    public WikiDraftWriteCommandDTO setTags(List<String> tags) {
        this.tags = tags == null ? null : new ArrayList<>(tags);
        return this;
    }

    /**
     * 中文说明：返回 links 的不可变快照副本。
     * English summary: Returns an unmodifiable copy of the link ids.
     */
    public List<String> getLinks() {
        return links == null ? null : Collections.unmodifiableList(new ArrayList<>(links));
    }

    /**
     * 中文说明：写入时复制 links。
     * English summary: Defensively copies the incoming link ids.
     */
    public WikiDraftWriteCommandDTO setLinks(List<String> links) {
        this.links = links == null ? null : new ArrayList<>(links);
        return this;
    }

    /**
     * 中文说明：返回 sources 的不可变快照副本。
     * English summary: Returns an unmodifiable copy of the source list.
     */
    public List<WikiSourceWriteDTO> getSources() {
        return sources == null ? null : Collections.unmodifiableList(new ArrayList<>(sources));
    }

    /**
     * 中文说明：写入时复制 sources；元素仍由 {@code @Valid} 逐个校验。
     * English summary: Defensively copies the incoming sources; elements stay validated by {@code @Valid}.
     */
    public WikiDraftWriteCommandDTO setSources(List<WikiSourceWriteDTO> sources) {
        this.sources = sources == null ? null : new ArrayList<>(sources);
        return this;
    }
}
