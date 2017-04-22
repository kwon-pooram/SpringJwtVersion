/**
 * Created by sungman.you on 2017. 4. 21..
 */
    // 에러 뷰 표시
const loginFormErrorView = $("#loginForm-error");

// 로그인 폼 submit 함수
$("#loginForm").submit(function (event) {
    event.preventDefault();

    // 사용자 입력 정보 가져오기
    let formData = {
        username: $("#loginUsername").val(),
        password: $("#loginPassword").val()
    };
    // 로그인 함수 호출
    submitLoginForm(formData);
    // 입력 정보 삭제
    $(":input", $(this)).val("");
});


// 로그인 함수
function submitLoginForm(formData) {
    $.ajax({
        url: "/auth",
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            console.log("[로그인 성공]");

            // 로그인 성공시 토큰 저장
            setJwtToken(data.token);

            // 인덱스 화면 리디렉트
            window.location.replace("/index");
        },
        error: function (jqXHR, textStatus, errorThrown) {

            /**
             * 에러 처리 :  Response Status 따라서 다른 에러 처리
             * */

            // 406 에러 : 허용되지 않음 (NOT_ACCEPTABLE)
            if (jqXHR.status === 406) {
                console.log("[로그인 에러] : 406");
                loginFormErrorView
                    .find(".card-text")
                    .empty()
                    .html(`
                                   <p>로그인에 실패했습니다.</p>
                                   <p>원인 : <span id="loginErrorCausedBy"></span></p>
                                 `);
                $("#loginErrorCausedBy").text(jqXHR.responseText);
                loginFormErrorView
                    .show();
            }

            // 401 에러 : 접근 권한 없음
            else if (jqXHR.status === 401) {
                console.log("[로그인 에러] : 401");
                loginFormErrorView
                    .find(".card-text")
                    .empty()
                    .html(`
                                   입력하신 정보와 일치하는 사용자가 없습니다.<br/>
                                   다시 시도해 주세요.<br/>
                                   <button type="button" class="btn btn-secondary" id="forgotUserInfoBtn"
                                   onclick="window.location.replace('/index');">
                                        아이디 / 비밀번호 찾기
                                   </button>
                                 `);
                loginFormErrorView
                    .show();
            } else {
                throw new Error("[로그인 에러] : " + errorThrown);
            }
        }
    });
}