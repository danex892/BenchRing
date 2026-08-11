# BenchRing Roadmap

## Purpose

BenchRing exists to answer a concrete engineering question:

> How do backend stacks behave under the same realistic REST, PostgreSQL, and cache workload when the contract, data, resources, and load profile are controlled?

The first release is an experiment with reproducible evidence. A general-purpose benchmark platform may grow from that experiment later, but it is not the V1.0 goal.

## Background

The repository started as a contacts-service comparison implemented in several languages and frameworks. That history is the product's reason to exist and must remain visible. Existing implementations should be treated as imported experimental subjects, not discarded during a platform rewrite.

The current baseline includes implementations in Python, PHP, Java, Rust, Go, Node.js, C, and C++, a shared PostgreSQL schema, a Go load generator, API contract tests, and Docker Compose orchestration.

## Product and Portfolio Thesis

BenchRing should demonstrate:

- careful benchmark methodology;
- Go backend and tooling work;
- controlled concurrency and resource allocation;
- PostgreSQL and cache behavior;
- cross-language API contract design;
- repeatable measurements and honest interpretation;
- useful result visualization without hiding raw data.

Adding technologies is not a goal by itself. Every component must either improve experimental validity, reproducibility, or the presentation of evidence.

## V1.0 Closure Contract

BenchRing may be tagged `v1.0.0` when all of the following are true:

1. `contacts/v1` is a versioned scenario with an API contract, database schema, seed procedure, cache semantics, workload mix, and resource budget.
2. Every implementation included in the published comparison passes the same conformance suite. At least four implementations must qualify; non-qualifying implementations are clearly marked experimental and excluded from rankings.
3. The runner records throughput, p50/p95/p99 latency, error counts, CPU, RSS, database timing where available, cache hit/miss counts, environment metadata, and raw samples or histograms.
4. Warm-up, repetition count, run ordering, database reset, cache state, connection limits, worker counts, and timeouts are controlled and documented.
5. A clean checkout can run the reference comparison with one documented command and produce machine-readable results plus a human-readable report.
6. CI validates formatting, unit tests, contract tests, builds, and Docker Compose configuration for the release set.
7. The repository contains a methodology document, limitations, an architecture diagram, representative results, and a short reproducible demo.
8. A tagged GitHub release contains the report, raw result bundle, scenario version, and exact revisions of compared implementations.

Anything not required by this contract is Post-1.0 unless it fixes correctness or reproducibility.

## Must Have

- A scenario-first repository layout such as `scenarios/contacts/v1/`.
- A canonical OpenAPI or equivalent executable API contract.
- Deterministic seed data and documented query/index choices.
- A PostgreSQL-only baseline scenario.
- A precisely defined read-through cache scenario with identical hit, miss, and invalidation behavior across implementations.
- A conformance test suite that runs before every benchmark.
- Correct handling of non-2xx responses, timeouts, incomplete bodies, and runner-side failures.
- Versioned JSON result files that remain useful without the web UI.
- Repeated runs and an explicit policy for noise, outliers, and confidence.
- CI and a release artifact containing evidence.

## Nice to Have

- A small React and TypeScript report viewer.
- Flame graphs or profiler links for selected implementations.
- More than four qualifying implementations.
- Automated before/after regression comparison.
- A Go control-plane API for storing historical runs after the scenario itself is complete.

## Explicitly Deferred

- Distributed agents and remote task assignment.
- A Rust agent unless process isolation or measurement precision proves it necessary.
- Live logs over SSE or WebSocket.
- Arbitrary benchmark plugins or remote code execution.
- Multi-tenancy, RBAC, billing, Kubernetes, and a custom time-series database.
- Claims that one language is universally faster based on a single workload.

## Milestones

### M0 — Preserve and Audit the Baseline

- Document the original performance question and repository history.
- Inventory every implementation and classify it as working, incomplete, or experimental.
- Reconcile the documented and implemented request mix.
- Record current worker, runtime, connection-pool, timeout, and resource settings.
- Tag or otherwise identify the imported baseline without fabricating historical commits.

**Exit evidence:** an implementation matrix and a list of known validity gaps.

### M1 — Freeze `contacts/v1`

- Define the API and error model.
- Define schema, indexes, seed data, and reset rules.
- Define PostgreSQL-only and cache-enabled modes.
- Define the exact request distribution and concurrency profiles.
- Define resource budgets and implementation configuration rules.

**Exit evidence:** the scenario specification is reviewable without reading service code.

### M2 — Build the Conformance Gate

- Validate response status, schema, filtering, pagination, and error behavior.
- Test all release candidates against a fresh database.
- Prevent benchmarking when conformance fails.
- Separate experimental implementations from the release comparison.

**Exit evidence:** one command produces a pass/fail matrix for every implementation.

### M3 — Make Measurements Trustworthy

- Replace total-time-only reporting with latency distributions and error accounting.
- Capture machine, OS, runtime, container, database, and scenario metadata.
- Collect CPU and RSS consistently.
- Run warm-up and multiple measured iterations.
- Preserve raw results in a stable format.

**Exit evidence:** repeated runs can be compared and independently analyzed.

### M4 — Publish the Comparison

- Generate a concise report with methodology, charts, results, and limitations.
- Explain observed behavior instead of presenting a context-free leaderboard.
- Add screenshots and a short killer demo.
- Publish the raw result bundle with the release.

**Exit evidence:** a reader can reproduce the experiment and challenge its conclusions.

### M5 — Post-1.0 Platform Work

Only after V1.0 is closed:

- store and compare historical runs;
- add regression detection;
- introduce a Go API and richer web UI;
- evaluate whether a Rust runner is justified;
- add local or distributed agents.

## Killer Demo

Run the same versioned contacts scenario against the release implementations, show the conformance gate, execute repeated measurements, and open a report containing throughput, latency percentiles, resource usage, cache behavior, and limitations. The demo must make the fairness controls visible rather than showing only a ranking.

## Release Evidence

The V1.0 release should contain:

- scenario specification and version;
- exact implementation revisions;
- environment manifest;
- raw JSON results;
- generated report;
- CI status;
- architecture and methodology documentation;
- a short demo recording.
