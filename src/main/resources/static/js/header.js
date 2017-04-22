/**
 * Created by sungman.you on 2017. 4. 21..
 */

const loginBtn = $("#navbar-loginBtn");
const logoutBtn = $("#navbar-logoutbtn");

// 로그인 버튼 선택시 호출 함수
loginBtn.click(function (event) {

    event.preventDefault();

    // 이미 로그인 한 사용자가 다시 로그인 시도 할 경우
    if (getJwtToken()) {
        if (window.confirm("이미 로그인 상태입니다.\n기존 로그인 정보가 삭제됩니다. 진행하시겠습니까?") === true) {
            // 기존 로그인 정보 삭제 (토큰 삭제)
            removeJwtToken();
            // 로그인 화면 리디렉트
            window.location.replace("/login");
        } else {
            return null;
        }
    } else {
        // 로그인 화면 리디렉트
        window.location.replace("/login");
    }
});

// 로그아웃 버튼 선택시 호출 함수
logoutBtn.click(function (event) {
    event.preventDefault();
    logout();
});
