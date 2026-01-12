# 🗓️ 일정 관리 앱 만들기 - Develop
## 📘 API 명세서
🔗 [API 명세서 보기](https://documenter.getpostman.com/view/50332844/2sBXVfirVn)

## 🗂️ ERD
<img width="917" height="344" alt="ERD-2" src="https://github.com/user-attachments/assets/87927421-7311-402a-bec6-fa46f2e2982b" />

## ⚙️ 기술 스택
- **Language**: Java 17  
- **Framework**: Spring Boot 4.0.1, Spring Web  
- **ORM**: JPA (Hibernate)  
- **Database**: MySQL 8.4.7
- **Library**: Lombok, Bean Validation, BCrypt
- **Build Tool**: Gradle  
- **Tools**: Postman, dbdiagram.io  
- **Version Control**: Git, GitHub

## 🚀 구현 기능
<details>
<summary><strong> 필수 기능 </strong></summary>
<br>
  
- **일정 CRUD**
  - `일정 제목`, `일정 내용`, `작성자명`, `작성일`, `수정일`을 저장
- **유저 CRUD**
  - `유저명`, `이메일`, `작성일`, `수정일`을 저장
  - 일정의 `작성자명` → `유저 고유 식별자`
- **회원가입**
  - 유저에 `비밀번호` 필드 추가
    - 비밀번호는 8자 이상
- **로그인(인증)**
  - Cookie/Session을 활용해 로그인 기능 구현
  - `이메일`과 `비밀번호`를 활용

</details>

<details>
<summary><strong> 도전 기능 </strong></summary>
<br>
  
- **다양한 예외처리**
  - Validation을 활용해 다양한 예외처리 적용
    - `@RestControllerAdvice`를 활용
- **비밀번호 암호화**
  - `PasswordEncoder`를 직접 만들어 비밀번호 암호화(BCrypt)
- **댓글 CRUD**
  - 댓글 - 일정 연관관계 매핑
  - `댓글 내용`, `작성일`, `수정일`, `유저 고유 식별자`, `일정 고유 식별자`를 저장

</details>

<details>
<summary><strong> 공통 </strong></summary>
<br>

- 모든 테이블은 고유 식별자(ID)를 가짐
- `3 Layer Architecture`에 따라 각 Layer의 목적에 맞게 개발
- CRUD 필수 기능은 모두 데이터베이스 연결 및 `JPA`를 사용해서 개발해야 함
- 인증/인가 절체는 `Cookie/Session`을 활용하여 개발
- JPA 연관관계는 `단방향`

</details>
