# Yaoguang permission producer and runtime boundary

The Java producer owns the existing knowledge/Wiki management API and its transport fields. It preserves the `/api/v1/yuheng/admin` routes and the existing `yuheng:knowledge:read`, `yuheng:knowledge:write` and `yuheng:knowledge:admin` meanings. The bootstrap route remains `/api/v1/yaoguang/auth/bootstrap`.

## Ownership and source inventory

- Producer: `yaoguang-java`; application: `xingyuan` / `yaoguang`.
- Expected module: `stellar-enclosure-yaoguang-admin`; owned prefixes: `yaoguang.api.` and `yaoguang.field.`.
- Current compiler inventory: 24 API resources, 189 FIELD resources, 3 explicit permission definitions and 102 source messages. Every API and FIELD label resolves to a message in `yaoguang.permissions`.
- `PermissionProducerCoverageTest` compares actual Spring request mappings, including HEAD, and the Jackson properties reachable from each method against the compiler fragment. The multipart command is included through its explicit binding adapter.
- `AdminActor` is a server-created identity argument. `AuthorizationBootstrapView` is a shared Jianshen response contract. Neither is a locally owned input DTO. There are no HTTP download/export, RPC provider, MCP, or streaming endpoints in this repository.
- `KnowledgeJobWorker` is an internal scheduled executor over persisted jobs, not a transport that accepts an external caller. It retains its existing tenant/store and lifecycle contracts. No new protocol or business capability is added.

| Resource key | Methods | Paths | Principals | OAuth scopes |
| --- | --- | --- | --- | --- |
| `yaoguang.api.authorization.current` | GET, HEAD | `/api/v1/authorization/effective-permissions` | USER | PUBLIC expression; verified identity still required |
| `yaoguang.api.gatewayAuthBootstrap.bootstrap` | GET, HEAD | `/api/v1/yaoguang/auth/bootstrap` | USER |  |
| `yaoguang.api.knowledge.createKnowledgeAnswer` | POST | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/answers` | SERVICE, USER | `yuheng:knowledge:read` |
| `yaoguang.api.knowledge.createKnowledgeBase` | POST | `/api/v1/yuheng/admin/knowledge-bases` | SERVICE, USER | `yuheng:knowledge:write` |
| `yaoguang.api.knowledge.getKnowledgeBase` | GET, HEAD | `/api/v1/yuheng/admin/knowledge-bases/{kbId}` | SERVICE, USER | `yuheng:knowledge:read` |
| `yaoguang.api.knowledge.listKnowledgeBases` | GET, HEAD | `/api/v1/yuheng/admin/knowledge-bases` | SERVICE, USER | `yuheng:knowledge:read` |
| `yaoguang.api.knowledge.listKnowledgeMembers` | GET, HEAD | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/members` | SERVICE, USER | `yuheng:knowledge:read` |
| `yaoguang.api.knowledge.replaceKnowledgeBase` | PUT | `/api/v1/yuheng/admin/knowledge-bases/{kbId}` | SERVICE, USER | `yuheng:knowledge:admin` |
| `yaoguang.api.knowledge.replaceKnowledgeMembers` | PUT | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/members` | SERVICE, USER | `yuheng:knowledge:admin` |
| `yaoguang.api.knowledgeDocument.createKnowledgeReindexJob` | POST | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/documents/{documentId}/reindex-jobs` | SERVICE, USER | `yuheng:knowledge:write` |
| `yaoguang.api.knowledgeDocument.deleteKnowledgeDocument` | DELETE | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/documents/{documentId}` | SERVICE, USER | `yuheng:knowledge:write` |
| `yaoguang.api.knowledgeDocument.getKnowledgeDocumentRevision` | GET, HEAD | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/documents/{documentId}/revisions/{revisionId}` | SERVICE, USER | `yuheng:knowledge:read` |
| `yaoguang.api.knowledgeDocument.listKnowledgeDocuments` | GET, HEAD | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/documents` | SERVICE, USER | `yuheng:knowledge:read` |
| `yaoguang.api.knowledgeDocument.uploadKnowledgeDocument` | POST | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/documents` | SERVICE, USER | `yuheng:knowledge:write` |
| `yaoguang.api.knowledgeJob.getKnowledgeJob` | GET, HEAD | `/api/v1/yuheng/admin/knowledge-jobs/{jobId}` | SERVICE, USER | `yuheng:knowledge:read` |
| `yaoguang.api.knowledgeJob.listKnowledgeJobs` | GET, HEAD | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/jobs` | SERVICE, USER | `yuheng:knowledge:read` |
| `yaoguang.api.knowledgeJob.retryKnowledgeJob` | POST | `/api/v1/yuheng/admin/knowledge-jobs/{jobId}/retries` | SERVICE, USER | `yuheng:knowledge:write` |
| `yaoguang.api.wiki.createWikiGenerationJob` | POST | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/wiki/generation-jobs` | SERVICE, USER | `yuheng:knowledge:write` |
| `yaoguang.api.wiki.getWikiGraph` | GET, HEAD | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/wiki/graph` | SERVICE, USER | `yuheng:knowledge:read` |
| `yaoguang.api.wiki.getWikiPage` | GET, HEAD | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/wiki/pages/{pageId}` | SERVICE, USER | `yuheng:knowledge:read` |
| `yaoguang.api.wiki.listWikiPages` | GET, HEAD | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/wiki/pages` | SERVICE, USER | `yuheng:knowledge:read` |
| `yaoguang.api.wiki.publishWikiRevision` | POST | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/wiki/pages/{pageId}/publications` | SERVICE, USER | `yuheng:knowledge:write` |
| `yaoguang.api.wiki.replaceWikiDraft` | PUT | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/wiki/pages/{pageId}/draft` | SERVICE, USER | `yuheng:knowledge:write` |
| `yaoguang.api.wiki.unpublishWikiPage` | DELETE | `/api/v1/yuheng/admin/knowledge-bases/{kbId}/wiki/pages/{pageId}/publications/current` | SERVICE, USER | `yuheng:knowledge:write` |

All API declarations are PROTECTED. The existing USER `user-scope-policy.enabled=true` mapping remains in force for all three knowledge permission characters; compiled expressions do not bypass the OAuth Scope upper bound. The original knowledge-base membership checks remain separate from the functional permission and OAuth scope checks. No wildcard permission is accepted by the Yaoguang controller expressions or Wiki publication withdrawal guard.

## Fields and actual execution

`Rbac3StarterAutoConfiguration` supplies the shared compiled-policy registry, method executor, input binder, and response serializer. `GatewayAiJsonConfiguration` explicitly registers the shared module on the actual MVC Jackson converters and their strict input copies. This is required because this host owns a primary ObjectMapper. Write permission does not imply plain read permission.

Each SERVICE field declares SERVICE explicitly, one exact knowledge operation Scope, and its allowed field operation. Inputs and responses that formerly shared a Java DTO across different SERVICE scopes now use concrete operation projections. The projections retain the JSON property names and validation groups; `KnowledgePermissionProjectionConverter` uses the existing MapStruct convention to map them to the unchanged business carriers. Internal `KnowledgeMemberDTO` and `WikiSourceDTO` remain usable by persistence without request identity or HTTP field policy.

Multipart upload is the alternative write path. Before reading the file bytes or calling the business service, the controller rejects unknown multipart names, captures the presence of optional `documentId` and `expectedRevision`, and invokes `PermissionFieldBindingGuard` on `KnowledgeUploadCommandDTO`. Binary `content` has JSON type STRING because Jackson represents `byte[]` as Base64, including when JsonFormat requests ARRAY. Existing multipart size and content checks continue to apply.

The SERVICE authority adapter preserves the verified IdP issuer and audit details, projects only the exact verified knowledge scopes, and never manufactures a USER principal. Shared SERVICE policy execution requires an independently verified scope/build/resource view; it does not borrow user role grants.

## Trusted deployment configuration

The application requires the standard FULL manifest at `META-INF/permissions/manifest-v1.json` and a deployment-supplied final artifact SHA-256. Runtime catalog scope is the complete tuple, supplied through:

- `YAOGUANG_ARTIFACT_DIGEST`
- `YAOGUANG_CATALOG_SCOPE_ID`, `YAOGUANG_CATALOG_TENANT_ID`
- `YAOGUANG_CATALOG_NAMESPACE`, `DEPLOYMENT_ENV`
- fixed application identity `xingyuan` / `yaoguang`

A deployment serving multiple scopes must supply an explicit finite `egon.cola.platform.tianquan.jianshen.runtime.catalog-scopes` list. Requests cannot select authority bindings or change the executing build facts. Unregistered/unactivated builds fail closed on protected execution.

Identity validation and security audit delivery use `egon.cola.platform.tianquan.shoubing.authority`. The configured active tenant/platform issuers each require their own fixed Client Credentials registration and source resource binding. `YAOGUANG_IDENTITY_TENANT_A_REGISTRATION_ID`, `YAOGUANG_IDENTITY_TENANT_B_REGISTRATION_ID`, `YAOGUANG_IDENTITY_PLATFORM_REGISTRATION_ID`, `TIANQUAN_SHOUBING_AUTHORITY_BASE_URI`, and `TIANQUAN_SHOUBING_IDENTITY_RESOURCE_URI` identify those controlled registrations and HTTPS authority destinations. Their credentials and credential versions are supplied through the existing service-client configuration, not this repository.

The old identity Redis revocation/audit spool configuration and the authorization Redis cache bridge are removed. This host does not use a business cache, so its generic component cache is disabled; unrelated Tianshu configuration caching is unchanged. No data, old spool files or Redis keys are deleted.

## Compilation and packaging

Use the `permission-producer` Maven profile with an explicit actual commit and build id. The annotation processor augments the existing Lombok, MapStruct and Spring Boot processors. It emits PARTIAL fragments only. The profile runs the shared `PermissionManifestBuildCli` at `prepare-package`, after successful compilation and tests. It aggregates the complete expected module set and places the FULL output and evidence JSON in the final application package. The CLI and processor remain isolated build-plugin dependencies. The final artifact digest is calculated after packaging and supplied by the deployment control plane, avoiding a self-referential embedded digest.

```bash
./mvnw -B -ntp -Ppermission-producer \
  -Dpermission.commit="$PERMISSION_COMMIT" \
  -Dpermission.buildId="$PERMISSION_BUILD_ID" \
  clean package
```

A normal build without the producer profile is useful for ordinary compile/test work, but is not an activated deployment artifact. Java owns these FIELD keys; the browser producer references them rather than defining duplicate fields.

## Controlled bootstrap and acceptance

In an explicitly authorized environment, B0 establishes the exact catalog scope, producer ownership and finite SERVICE grants for registration/import/runtime policy operations. B1 registers the built-in definitions and grants selected roles only the explicit `yuheng:knowledge:read`, `yuheng:knowledge:write` and `yuheng:knowledge:admin` strings. Ordinary CI neither grants roles nor installs an all-permissions administrator. Startup performs no bootstrap grant or activation mutation.

Source validation includes compiler coverage, all direct controller calls denied without identity through the real method interceptor, multipart rejection before byte reads/business invocation, explicit-null/nested/unknown/read-only JSON handling, operation projection wire equality, and preservation of input constraints. The final package passed all 82 offline tests with no failures, errors or skips, including the shared SERVICE policy execution checks. This count is one run, not a sum of repeated focused runs.

No server, browser, container, database migration, real JWT/IdP/RBAC request, remote manifest registration, activation, or live ingestion was performed. The final executable JAR contains the unique 116,262-byte FULL manifest and its evidence JSON under `META-INF/permissions/`. A pure Boot archive classloader probe verified Spring ClassPathResource can read it without loading application classes or starting a Spring context; CLI and processor JARs are absent from the runtime libraries. The shared SERVICE path was exercised with real policy/field executors and a controlled policy-client test double: create/write and management scopes remain independent, a read-only SERVICE cannot upload, a write SERVICE can bind and receive its response projection, and a mismatched artifact is rejected. These are local contract tests, not evidence of the remote SQL authority or a live token exchange.

The shared factory now supplies transport-only mapper copies; the original mapper is not mutated or auto-registered as a field-policy Module bean. Explicit USER Scope mappings remain enabled for read/write/admin, and a write-only Scope cannot reveal READ fields even when the role has both permission codes. The canonical Runtime `system-code` is `yaoguang`, matching the fixed catalog application. A configuration-only package reran its two host tests after this final configuration correction.

`GET /api/v1/authorization/effective-permissions` is a USER-only PUBLIC declaration (no RBAC group requirement) that returns the existing verified current snapshot with `Cache-Control: no-store`. It cannot select a subject, tenant or scope, and rejects SERVICE callers or absent proof. Its source resource key is `yaoguang.api.authorization.current`; it never forwards this host's access token to another application audience.

The final configuration package executable JAR SHA-256 is `785f2f0b70c7e75b9143e552abe1b6d8f986c262158a8e4788702c2078ee225f`; manifest digest `d8722534931abfb35cc695e71ea76d2f80a31b762462ac9f6beb29d98cbf4602`. Build ID `s27-a-runtime-local` denotes dirty local source verification, not a deployed or activated artifact. The archive resource probe was repeated successfully on this exact JAR.
