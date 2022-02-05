# Todo-List Restful API

[http://3.38.36.238:8080/swagger-ui/index.html](http://3.38.36.238:8080/swagger-ui/index.html)

## GOAL

- 주어진 API Reference에 따라 간단한 Todo 서비스 구현
- 적절한 예외처리와 코너케이스 처리
- 개발환경 / 프로덕드 환경 분리
- 로그 추적기능 추가
- 인가처리
- 이미지 업로드 부가기능
- CI/CD
- 단위테스트, 통합테스트를 병행하여 tdd에 가깝게 개발

## 사용 기술

### 주요 프레임워크 / 라이브러리 

- java 11 openjdk
- SpringBoot 2.6.3
- Spring Data Jpa

### build tool

- gradle

### DB

- 개발환경 : H2
- 프로덕트 : MySql

### infra

- AWS EC2
- AWS S3
- Travis Ci
- Aws CodeDeploy

### 기타 라이브러리

- Lombok
- QueryDsl
- Swagger

### 개발 환경

- IntelliJ
- PostMan
- GitHub
- Mysql Workbench
- H2 console

### 1. 주어진 API Reference에 따라 간단한 Todo 서비스 구현

- Get, Post, Delete, Put 요청에 따른 restful api 구현 완료
- Get으로 list 요청시 헤이티오스를 통한 다음 리소스 제시
- 요구된 스펙대로 예외 처리(403,404,500 등)

### 2. 개발환경 / 프로덕트 환경 분리

- 개발환경시 h2db사용, 개발환경설정 프로필 분리
- 프로덕트환경시 mysql사용, 프로덕트 환경설정 프로필 분리
- 깃헙 dev, main 브랜치 분리하여 dev에서 작업후 병합

### 3. 인가처리
- api 레퍼런스에서 제시한 간단한 쿼리스트링 apikey로 인터셉터를 통해 인가 처리 구현

### 4. 이미지 업로드 모듈
- 이미지 업로드시 todo를 삽입할때 이미지를 바이너리나 파일로 보낼지, 따로 요청을 나눌지 고민하다 기존 코드를 최대한 손대지 않고
유연한 확장 설계를 위해 별도의 Get 요청으로 분리함
- 실무환경이라면 기획자와 클라이언트 개발자와 소통후 결정
- aws s3서버로 파일업로드 후 url을 반환받아 url을 todo에 삽입하는 방식으로 구현
- todo와 url은 1대 다 매핑연관관계로 db에서 관리

### 5. 로그 추적 모듈
- aop를 사용하여 로그 추적 모듈 추가
- 요청이 들어올때 할당 쓰레드 단위로 고유 난수를 붙여 로그 추적토록함

### 6. Ci / Cd
- github, travis , codedeploy를 사용하여 깃헙으로 메인 브랜치가 푸시되거나 병합될 때, 트래비스에서 테스트를 수행, 
빌드후 ec2서버로 보내지면 codedeploy가 쉘스크립트를 실행시켜 자동 배포 릴리즈되도록 구성

### <p style="text-decoration:line-through">7. 단위 테스트, 통합 테스트</p>

- 기본기능에 한에 단뒤 테스트, 통합테스트 코드 구현 완료

- 개발환경에서 정상 테스트 구동 확인

- **실제 프로덕트 환경으로 배포하는과정에서 테스트 코드가 제대로 작동하지 않아 주석처리함. 차후 수정예정**