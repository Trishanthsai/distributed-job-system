# Distributed Job Processing System

> A fault-tolerant distributed backend for reliable asynchronous job processing across multiple worker nodes.

## 📌 Overview

A production-inspired backend infrastructure system where clients submit jobs through REST APIs. Jobs are persisted, queued, and asynchronously processed by distributed workers with retries, rate limiting, failure recovery, and horizontal scaling.

```text
Client
  ↓
API Gateway
  ↓
Rate Limiter
  ↓
Job Service
  ↓
Message Queue
  ↓
Worker Pool
  ↓
PostgreSQL / Redis
```

## 🏗️ Architecture

```text
                    ┌─────────────┐
                    │   Client    │
                    └──────┬──────┘
                           ↓
                    ┌─────────────┐
                    │ API Gateway │
                    └──────┬──────┘
                           ↓
                    ┌─────────────┐
                    │Rate Limiter │
                    └──────┬──────┘
                           ↓
                    ┌─────────────┐
                    │ Job Service │
                    └──────┬──────┘
                           ↓
                    ┌─────────────┐
                    │Message Queue│
                    └──────┬──────┘
                           ↓
             ┌─────────────┼─────────────┐
             ↓             ↓             ↓
         Worker 1      Worker 2      Worker 3
             └─────────────┼─────────────┘
                           ↓
                    ┌─────────────┐
                    │ PostgreSQL  │
                    └─────────────┘

       Redis → Distributed State / Locks / Rate Limits
```

## ⚙️ Backend Components

* **API Gateway** — Request routing and rate limiting
* **Job Service** — Validation, job creation and lifecycle management
* **Message Queue** — Asynchronous job distribution
* **Worker Pool** — Concurrent job execution
* **Scheduler** — Delayed and scheduled jobs
* **Retry Manager** — Retry policies with exponential backoff
* **Dead Letter Queue** — Isolates permanently failed jobs
* **Worker Registry** — Tracks worker health and assignments
* **Heartbeat Monitor** — Detects failed workers
* **Recovery Engine** — Requeues abandoned jobs
* **Idempotency Layer** — Prevents duplicate side effects
* **Distributed Lock Manager** — Coordinates shared resources
* **Persistence Layer** — Job state, history and results
* **Metrics Layer** — Throughput, latency, failures and queue depth

## 🔄 Job Lifecycle

```text
PENDING
   ↓
QUEUED
   ↓
PROCESSING
   ↓
COMPLETED

PROCESSING
   ↓
FAILED
   ↓
RETRY + BACKOFF
   ↓
QUEUED

FAILED
   ↓
MAX RETRIES
   ↓
DEAD LETTER QUEUE
```

## 💥 Failure Recovery

```text
Worker
   ↓
Heartbeat
   ↓
Failure Detection
   ↓
Mark Worker Unavailable
   ↓
Recover Job
   ↓
Requeue
   ↓
Another Worker
```

## 🚀 Key Features

* RESTful APIs
* Asynchronous processing
* Concurrent worker execution
* Retry & exponential backoff
* Dead Letter Queue
* Rate limiting
* Distributed locks
* Worker heartbeats
* Failure detection & recovery
* Idempotent job execution
* Priority queues
* Scheduled & delayed jobs
* Backpressure
* Horizontal worker scaling
* Load balancing
* Performance benchmarking
* Metrics, logging & tracing

## 🛠️ Tech Stack

**Java 21 · Spring Boot · PostgreSQL · Redis · RabbitMQ/Kafka · Docker · Prometheus · Grafana**

## 🧠 Core Concepts

**Distributed Systems · Concurrency · Message Queues · Rate Limiting · Distributed Locks · Fault Tolerance · Idempotency · Failure Recovery · Backpressure · Horizontal Scaling · Observability**

## 🗺️ Roadmap

**Core Processing → Reliability → Distributed Workers → Scheduling → Scaling → Observability → Production Deployment**

## 📌 Status

🚧 **Planned**

> Goal: Build reliable distributed backend infrastructure that continues processing work correctly under concurrency, failures, retries, and increasing load.
