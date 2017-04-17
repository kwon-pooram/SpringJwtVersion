# SpringMVC
Clamer Website Spring MVC

<h1>JWT 인증 방식</h1>
<ul>
<h3>Thymeleaf 삭제</h3>
<li>SPA (싱글 뷰 페이지) 방식으로 변경</li>
<li>클라이언트 사이드에서 스크립트로 뷰 처리</li>
<li>디펜던시에 남아있으나 자동 설정 기능(뷰 리졸버 등록)외에 사용하지 않음</li>
<h3>STATELESS 세션</h3>
<li>세션 생성하지도, 사용하지도 않음</li>
<li>리퀘스트 헤더에 JWT로 인증</li>
<h3>Spring Mobile 적용</h3>
<li>클라이언트 접속하는 기기 (웹,모바일,테블릿) 체크하여 각각 다른 뷰 리턴</li>
<li>(추후 적용)</li>
</ul>
