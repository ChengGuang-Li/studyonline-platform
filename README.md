## Background
The project is based on the B2B2C business model, where educational institutions can join the platform, publish courses, and operational personnel can review the published courses. Only courses that pass the review can be successfully published. Users can choose courses for online learning, including both free and paid formats. For free courses, users can directly join the course list for learning, while for paid courses, successful payment is required before continuing with the learning process.

The backend architecture of this project is designed based on a distributed microservices framework to support high-concurrency and high-availability scenarios. 

<p align="center"> <a href="./resources/microservices.md">The included microservices</a> </p>
<img src="./resources/Microservices.png" alt="Microservices">


### System Architecture Diagram

<img align="center" alt="Architecture"  src="./resources/Architecture.gif">

### Module Structure

```mermaid
---
title: Studyonline-Platform module structure
---
flowchart BT
base[Studyonline-Platform-Base] --> |inherit| parent[Studyonline-Platform-Parent]
content[Studyonline-Platform-Content] --> |inherit| parent
contentApi[Studyonline-Platform-Content-api] --> |inherit| content
contentService[Studyonline-Platform-Content-service] --> |inherit| content
contentApi[Studyonline-Platform-Content-api] --> |dependency| contentDb[Studyonline-Platform-Content-model]
contentApi --> |dependency| contentService
contentService --> |dependency| contentDb
contentDb --> |dependency| base
```

