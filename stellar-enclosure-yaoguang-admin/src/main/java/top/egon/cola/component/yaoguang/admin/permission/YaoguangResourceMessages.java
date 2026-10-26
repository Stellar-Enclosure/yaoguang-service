package top.egon.cola.component.yaoguang.admin.permission;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.LocalizedText;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionMessage;

/** Labels for declared Java API and DTO resources. */
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.authorization.fields", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Read active field policies and input bindings"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.authorization.current", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Read current verified permission view"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.gatewayAuthBootstrap.bootstrap", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "gatewayAuthBootstrap: bootstrap"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledge.createKnowledgeAnswer", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledge: createKnowledgeAnswer"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledge.createKnowledgeBase", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledge: createKnowledgeBase"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledge.getKnowledgeBase", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledge: getKnowledgeBase"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledge.listKnowledgeBases", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledge: listKnowledgeBases"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledge.listKnowledgeMembers", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledge: listKnowledgeMembers"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledge.replaceKnowledgeBase", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledge: replaceKnowledgeBase"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledge.replaceKnowledgeMembers", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledge: replaceKnowledgeMembers"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledgeDocument.createKnowledgeReindexJob", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledgeDocument: createKnowledgeReindexJob"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledgeDocument.deleteKnowledgeDocument", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledgeDocument: deleteKnowledgeDocument"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledgeDocument.getKnowledgeDocumentRevision", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledgeDocument: getKnowledgeDocumentRevision"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledgeDocument.listKnowledgeDocuments", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledgeDocument: listKnowledgeDocuments"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledgeDocument.uploadKnowledgeDocument", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledgeDocument: uploadKnowledgeDocument"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledgeJob.getKnowledgeJob", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledgeJob: getKnowledgeJob"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledgeJob.listKnowledgeJobs", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledgeJob: listKnowledgeJobs"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.knowledgeJob.retryKnowledgeJob", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "knowledgeJob: retryKnowledgeJob"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.wiki.createWikiGenerationJob", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "wiki: createWikiGenerationJob"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.wiki.getWikiGraph", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "wiki: getWikiGraph"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.wiki.getWikiPage", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "wiki: getWikiPage"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.wiki.listWikiPages", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "wiki: listWikiPages"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.wiki.publishWikiRevision", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "wiki: publishWikiRevision"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.wiki.replaceWikiDraft", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "wiki: replaceWikiDraft"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "api.wiki.unpublishWikiPage", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "wiki: unpublishWikiPage"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.activeRevisionId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Active revision id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.actorId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Actor id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.answer", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Answer"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.attempt", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Attempt"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.byteCount", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Byte count"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.chatModel", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Chat model"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.chunkId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Chunk id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.citations", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Citations"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.contentRevisionId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Content revision id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.createdAt", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Created at"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.description", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Description"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.dimensions", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Dimensions"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.documentId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Document id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.documentRevisionId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Document revision id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.draftRevisionId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Draft revision id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.edges", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Edges"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.egressPolicy", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Egress policy"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.embeddingModel", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Embedding model"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.embeddingSpaceId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Embedding space id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.errorCode", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Error code"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.excerpt", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Excerpt"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.expectedRevision", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Expected revision"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.fileName", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "File name"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.hash", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Hash"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.id", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.items", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Items"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.jobId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Job id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.kbId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Kb id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.latestJobId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Latest job id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.links", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Links"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.markdown", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Markdown"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.mediaType", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Media type"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.members", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Members"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.model", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Model"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.myRole", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "My role"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.name", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Name"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.nodes", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Nodes"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.outcome", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Outcome"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.ownerActorId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Owner actor id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.page", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Page"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.pageId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Page id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.publicationErrorCode", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Publication error code"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.publicationPolicySnapshot", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Publication policy snapshot"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.publicationStatus", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Publication status"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.publicationVersion", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Publication version"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.publishedRevisionId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Published revision id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.question", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Question"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.resourceId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Resource id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.reviewStatus", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Review status"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.revision", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Revision"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.revisionId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Revision id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.role", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Role"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.score", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Score"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.searchMode", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Search mode"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.size", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Size"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.slug", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Slug"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.source", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Source"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.sourceHash", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Source hash"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.sourceMode", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Source mode"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.sourceRevisionId", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Source revision id"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.sourceRevisionIds", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Source revision ids"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.sources", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Sources"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.stage", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Stage"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.stale", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Stale"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.status", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Status"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.tags", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Tags"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.target", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Target"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.text", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Text"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.title", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Title"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.topK", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Top k"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.total", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Total"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.truncated", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Truncated"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.type", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Type"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.updatedAt", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Updated at"))
@PermissionMessage(pageCode = "yaoguang.permissions", key = "field.content", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "content"))
final class YaoguangResourceMessages {
    private YaoguangResourceMessages() { }
}
