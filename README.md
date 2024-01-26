# Madgo Backend

맞고의 백엔드 어플리케이션입니다. 서비스는 다음과 같이 구성됩니다.
* auth
* TODO

## 실행방법
### 자바 빌드
TODO
### 도커 빌드
TODO
### 레디스 빌드
```
docker run --name local-redis -p 6379:6379 -v redis_temp:/data -d redis:latest redis-server
 ```

# Auth

* Auth는 회원가입, 로그인등의 기능을 지원하는 서비스입니다.
* JWT기반 보안 통신을 합니다.
## 개요 
* sign-up 시 디비에 등록
* login 시 access 토큰이 http로 반환이 되고, refresh 토큰이 쿠키에 들어갑니다. 
	* (이때 프론트는 http-only를 사용하여 자바스크립트에서 refresh 토큰을 접근하지 못하게 해야합니다.)
* refresh 토큰은 객체형식으로 레디스에 저장되어, 토큰을 까보지 않고도 검증할 수 있게합니다.
