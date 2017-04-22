/**
 * Created by sungman.you on 2017. 4. 22..
 */




const resendVerificationEmailForm = $("#resendVerificationEmailForm");
const resendVerificationEmailFormEmail = $("#resendVerificationEmailFormEmail");
const resendVerificationEmailFormUsername = $("#resendVerificationEmailFormUsername");
const resendVerificationEmailFormErrorView = $("#resendVerificationEmailForm-error");


// 인증 이메일 재발송 폼 submit 함수
resendVerificationEmailForm.submit(function (event) {
    event.preventDefault();

    // 사용자 입력 정보 가져오기
    let formData = {
        resendVerificationEmailFormEmail : resendVerificationEmailFormEmail.val(),
        resendVerificationEmailFormUsername: resendVerificationEmailFormUsername.val()
    };
    // 인증 이메일 재발송 함수 호출
    submitResendVerificationEmailForm(formData);

    // 입력 정보 삭제
    $(":input", $(this)).val("");
});


// 인증 이메일 재발송 함수
function submitResendVerificationEmailForm(formData) {

    $.ajax({
        url: "/registration/resendVerificationEmail",
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {

            console.log("[인증 이메일 재발송 성공]");

            // 인증 화면 리디렉트
            // 성공시 반환되는 리스펀스 바디에서 리디렉트 주소 받아옴
            window.location.replace(JSON.parse(jqXHR.responseText)["redirectUrl"]);
        },
        error: function (jqXHR, textStatus, errorThrown) {

            /**
             * 에러 처리 :  Response Status 따라서 다른 에러 처리
             * */

            // 406 에러 : 허용되지 않음 (NOT_ACCEPTABLE)
            if (jqXHR.status === 406) {
                console.log("[인증 이메일 재발송 에러] : 406");
                resendVerificationEmailFormErrorView.find(".card-text").empty()
                    .html(`
                                   <p>인증 이메일 재발송에 실패했습니다.</p>
                                   <p>원인 : <span id="resendVerificationEmailErrorCausedBy"></span></p>
                                 `);
                $("#resendVerificationEmailErrorCausedBy").text(jqXHR.responseText);


                resendVerificationEmailFormErrorView.show();
            }

            // 400 에러 : 프로세스가 비정상적으로 종료 될 경우 서버에서 ResponseEntity.BAD_REQUEST 반환
            else if (jqXHR.status === 400) {
                console.log("[인증 이메일 재발송 에러] : 400");

                resendVerificationEmailFormErrorView
                    .find(".card-text")
                    .empty()
                    .html(`
                                   인증 이메일 재발송이 정상적으로 완료되지 않았습니다.<br/>
                                   잠시 후 다시 시도해 주세요.<br/>
                                   문제가 지속 될 경우 관리자에게 문의하세요.
                                 `);

                resendVerificationEmailFormErrorView.show();
            } else {
                throw new Error("[이메일 인증 에러] : " + errorThrown);
            }
        }
    });
}