/**
 * Created by sungman.you on 2017. 4. 19..
 */


// 전체 페이지 공통 사용 함수




// 로컨 스토리지에서 토큰 가져오기
function getJwtToken() {
    return localStorage.getItem("jwtToken");
}

// 로컬 스토리지에 토큰 저장
function setJwtToken(token) {
    localStorage.setItem("jwtToken", token);
}

// 로컬 스토리지에서 토큰 삭제
function removeJwtToken() {
    localStorage.removeItem("jwtToken");
}

// 리퀘스트 인증 헤더 생성
function createAuthorizationTokenHeader() {
    const token = getJwtToken();
    if (token) {
        return {"Authorization": token};
    } else {
        return {};
    }
}

// 로그아웃 함수
function logout() {
    // 로컬스토리지 토큰 삭제
    removeJwtToken();
    // 인덱스 화면 리디렉트
    window.location.replace("/index");
}