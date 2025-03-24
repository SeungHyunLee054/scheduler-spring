# Scheduler
일정 관리 프로그램

## 기능
1. 일정 생성
- 생성된 유저의 id, 일정의 내용을 입력받아 일정을 생성한다.
2. 일정 조회
- 작성자명, 수정 날짜로 일정을 조회하거나 전체 조회할 수 있다.
- 일정의 id로 조회할 수 있다.
3. 일정 수정 
- 일정의 id, password를 입력받아 password가 일치했을 때 해당하는 일정의 할일, 작성자명을 수정할 수 있다.
4. 일정 삭제
- 일정의 id, password를 입력받아 password가 일치했을 때 해당하는 일정을 삭제할 수 있다.
5. 유저 생성
- 동명이인의 작성자가 있을 수 있기 때문에 작성자를 구분하기 위해 유저 테이블 생성
- 유저명, 이메일을 입력받아 유저를 생성한다.

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

## 프로젝트 폴더 구조
``` text
├───main
│   ├───java
│   │   └───com
│   │       └───lsh
│   │           └───scheduler
│   │               ├───common
│   │               │   ├───aop
│   │               │   │   └───log
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
