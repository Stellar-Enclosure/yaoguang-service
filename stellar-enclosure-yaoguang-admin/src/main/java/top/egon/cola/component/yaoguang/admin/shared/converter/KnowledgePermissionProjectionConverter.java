package top.egon.cola.component.yaoguang.admin.shared.converter;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.dto.KnowledgeBaseCommandDTO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.dto.KnowledgeBaseCreateCommandDTO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.dto.KnowledgeMembersCommandDTO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.dto.KnowledgeMembersReplaceCommandDTO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeBaseVO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeBaseCreatedVO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeBaseManagedVO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeJobVO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeJobWrittenVO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeMembersVO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeMembersReadVO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgeMembersManagedVO;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.vo.KnowledgePageVO;
import top.egon.cola.component.yaoguang.admin.wiki.domain.dto.WikiDraftCommandDTO;
import top.egon.cola.component.yaoguang.admin.wiki.domain.dto.WikiDraftWriteCommandDTO;
import top.egon.cola.component.yaoguang.admin.wiki.domain.vo.WikiPageVO;
import top.egon.cola.component.yaoguang.admin.wiki.domain.vo.WikiPageReadVO;
import top.egon.cola.component.yaoguang.admin.wiki.domain.vo.WikiPageWrittenVO;

/**
 * 中文说明：按 HTTP 操作投影权限字段，保留业务载体和 JSON 形状；持久化载体不参与请求字段鉴权。
 * English summary: Projects operation-specific HTTP field policies without changing business carriers or JSON shapes.
 */
@Component("knowledgePermissionProjectionConverter")
public class KnowledgePermissionProjectionConverter {
    private static final KnowledgePermissionProjectionMapping MAPPING = Mappers.getMapper(KnowledgePermissionProjectionMapping.class);

    public KnowledgeBaseCommandDTO baseCommand(KnowledgeBaseCreateCommandDTO source) { return MAPPING.baseCommand(source); }
    public KnowledgeBaseCreatedVO createdBase(KnowledgeBaseVO source) { return MAPPING.createdBase(source); }
    public KnowledgeBaseManagedVO managedBase(KnowledgeBaseVO source) { return MAPPING.managedBase(source); }
    public KnowledgeJobWrittenVO writtenJob(KnowledgeJobVO source) { return MAPPING.writtenJob(source); }
    public KnowledgeMembersCommandDTO membersCommand(KnowledgeMembersReplaceCommandDTO source) { return MAPPING.membersCommand(source); }
    public KnowledgeMembersReadVO readMembers(KnowledgeMembersVO source) { return MAPPING.readMembers(source); }
    public KnowledgeMembersManagedVO managedMembers(KnowledgeMembersVO source) { return MAPPING.managedMembers(source); }
    public WikiDraftCommandDTO draftCommand(WikiDraftWriteCommandDTO source) { return MAPPING.draftCommand(source); }
    public WikiPageReadVO readWiki(WikiPageVO source) { return MAPPING.readWiki(source); }
    public WikiPageWrittenVO writtenWiki(WikiPageVO source) { return MAPPING.writtenWiki(source); }
    public KnowledgePageVO<WikiPageReadVO> readWikiPages(KnowledgePageVO<WikiPageVO> source) { return MAPPING.readWikiPages(source); }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, builder = @Builder(disableBuilder = true))
interface KnowledgePermissionProjectionMapping {
    KnowledgeBaseCommandDTO baseCommand(KnowledgeBaseCreateCommandDTO source);
    KnowledgeBaseCreatedVO createdBase(KnowledgeBaseVO source);
    KnowledgeBaseManagedVO managedBase(KnowledgeBaseVO source);
    KnowledgeJobWrittenVO writtenJob(KnowledgeJobVO source);
    KnowledgeMembersCommandDTO membersCommand(KnowledgeMembersReplaceCommandDTO source);
    KnowledgeMembersReadVO readMembers(KnowledgeMembersVO source);
    KnowledgeMembersManagedVO managedMembers(KnowledgeMembersVO source);
    WikiDraftCommandDTO draftCommand(WikiDraftWriteCommandDTO source);
    WikiPageReadVO readWiki(WikiPageVO source);
    WikiPageWrittenVO writtenWiki(WikiPageVO source);
    KnowledgePageVO<WikiPageReadVO> readWikiPages(KnowledgePageVO<WikiPageVO> source);
}
