# Scheduler
## [ERD](https://seunghyun937.notion.site/ERD-1bbc72e464458025bd62c5f529324874?pvs=4)
## [API 명세서](https://seunghyun937.notion.site/API-1bbc72e46445800bbe6ec841f6dab977?pvs=4)
## [트러블슈팅](https://seunghyun937.notion.site/1bbc72e46445806f8ffff92420dc5cb8?pvs=4)

## 기술 스택
### Language
  - Java17
### Framework
  - SpringBoot 3.4.3
### Build Tool
  - Gradle
### Database
  - MySQL

## 프로젝트 구조
``` text
├───main
│   ├───java
│   │   └───com
│   │       └───lsh
│   │           └───scheduler
│   │               ├───common
│   │               │   ├───exception
│   │               │   ├───response
│   │               │   ├───security
│   │               │   │   └───config
│   │               │   ├───swagger
│   │               │   │   └───config
│   │               │   └───utils
│   │               └───module
│   │                   ├───member
│   │                   │   ├───controller
│   │                   │   ├───domain
│   │                   │   │   └───model
│   │                   │   ├───dto
│   │                   │   ├───exception
│   │                   │   ├───repository
│   │                   │   └───service
│   │                   └───scheduler
│   │                       ├───controller
│   │                       ├───domain
│   │                       │   └───model
│   │                       ├───dto
│   │                       ├───exception
│   │                       ├───repository
│   │                       └───service
│   └───resources
│       ├───static
│       └───templates
└───test
    └───java
        └───com
            └───lsh
                └───scheduler
                    └───module
                        ├───member
                        │   ├───controller
                        │   └───service
                        └───scheduler
                            ├───controller
                            └───service
```
