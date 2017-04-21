/**
 * Created by sungman.you on 2017. 4. 21..
 */

// 토큰 이름
const JWT = "jwtToken";

// 로컬 스토리지에서 토큰 가져오기
function getJwtToken() {
    return localStorage.getItem(JWT);
}

// 로컬 스토리지에 토큰 저장
function setJwtToken(token) {
    localStorage.setItem(JWT, token);
}

// 로컬 스토리지에서 토큰 삭제
function removeJwtToken() {
    localStorage.removeItem(JWT);
}

// 리퀘스트 인증 헤더 생성
function createAuthorizationTokenHeader() {
    const token = getJwtToken();

    // 로컬 스토리지에 토큰이 존재하면
    if (token) {
        // 헤더 생성
        return {"Authorization": token};
    } else {
        // 빈 헤더
        return {};
    }
}