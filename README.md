# yaoguang-service

Knowledge-base and Wiki backend for Stellar Enclosure's Yaoguang platform. All first-party modules start at **0.0.1**.

## Current capabilities

| Area | Function |
|---|---|
| Knowledge bases | Create/update knowledge bases, configure chat/embedding models and local/cloud egress policy, and manage READER/EDITOR/OWNER membership within the tenant boundary. |
| Documents | Upload source material, track document revisions, parse/chunk/embed through ingestion jobs, delete documents and request reindexing. |
| Retrieval and answers | Support keyword, vector and hybrid search over authorized active document chunks; return answers with citations and DOCUMENTS/WIKI/BOTH evidence scope. |
| Background jobs | Query ingestion and Wiki-generation jobs, inspect status/stage and retry eligible failures. |
| Wiki | Generate pages from source evidence, edit drafts, publish revisions, inspect the page relationship graph and unpublish pages with authorization checks. |

## Code and integration

The only Maven application module is [stellar-enclosure-yaoguang-admin](stellar-enclosure-yaoguang-admin/). Its `knowledge` package owns knowledge bases, membership, ingestion, jobs and retrieval; `wiki` owns page/revision workflows; `auth` and `support` provide management authentication and infrastructure. [yaoguang-web](https://github.com/Stellar-Enclosure/yaoguang-web) owns the independent browser workspace.

Authentication bootstrap is `GET /api/v1/yaoguang/auth/bootstrap`. The existing knowledge and Wiki business APIs retain the `/api/v1/yuheng/admin` prefix and `yuheng:knowledge:*` capabilities for compatibility; ownership moved to Yaoguang without renaming those contracts. Browser access uses the Yuheng Edge `edge-yaoguang` registration, Shoubing identity and Jianshen authorization. Model calls use the configured model integration; LLM channel/model gateway administration remains in [yuheng-service](https://github.com/Stellar-Enclosure/yuheng-service).

Document/query embeddings stay local without a cloud fallback; generation follows the configured egress policy. The current code covers the knowledge/Wiki workspace within the broader data-governance platform roadmap. It does not establish delivery of a general BI, ETL or data-warehouse platform. Building the Admin JAR does not verify external databases, indexes, model endpoints or end-to-end ingestion.

## Build

Requires Java 25 and the included Maven Wrapper. The shared parent manages Spring Boot 4.1.2. Once the 0.0.1 dependencies are published:

```bash
./mvnw -B -ntp test-compile
```

Before the first Central publication, clone the eight Java repositories next to one another and bootstrap their artifact graph explicitly:

```bash
bash ../Stellar-Enclosure-Parent/scripts/bootstrap-workspace.sh \
  --workspace "$(cd .. && pwd)" \
  --local-repository "$HOME/.m2/repository"

# Resolve subsequent Maven commands and generator subprocesses from this same cache.
export MAVEN_ARGS="${MAVEN_ARGS:-} -Dmaven.repo.local=$HOME/.m2/repository"
```

The bootstrap installs parent/BOM models before building components and business starters in Maven reactor order. It compiles test sources and installs packages with runtime tests disabled. It never runs applications, databases, containers, browsers, or deploy.

## Application delivery (no package publication)

This repository contains only the Yaoguang Admin application. Neither its aggregator POM nor the executable application is published to Maven Central or npm. Both inherit `maven.deploy.skip=true`, which the Central plugin also honors.

```bash
# Produce the deployable application without starting it.
./mvnw -B -ntp -DskipITs -Dinvoker.skip=true clean package
```

Deliver `stellar-enclosure-yaoguang-admin/target/stellar-enclosure-yaoguang-admin-exec.jar` through your application deployment process. Unit tests remain enabled. Prerequisite libraries must be published or installed by the Build section's bootstrap.

## Compatibility and publication

Java packages/classes, Bean names, configuration keys, OAuth/RPC identities, APIs, and database ownership are retained. Existing SQL and DDL history are copied byte for byte. Artifact/build versions and necessary build paths change. A successful build does not imply application startup or live infrastructure acceptance.

Git pushes run validation only. Central and npm publication are separate user actions. CI defaults to initial source bootstrap; after publication set `STELLAR_USE_SOURCE_BOOTSTRAP=false` to consume published artifacts directly. Private organizations may supply the Actions secret `STELLAR_ENCLOSURE_READ_TOKEN` with repository read access.

Original MIT/LGPL licenses and author attribution are preserved.
