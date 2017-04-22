/**
 * Created by sungman.you on 2017. 4. 22..
 */


const emailVerificationForm = $("#emailVerificationForm");
const emailVerificationEmailAddress = $("#emailVerificationEmailAddress");
const emailVerificationCode = $("#emailVerificationCode");
const emailVerificationFormErrorView = $("#emailVerificationForm-error");


// 이메일 인증 폼 submit 함수
emailVerificationForm.submit(function (event) {
    event.preventDefault();

    // 사용자 입력 정보 가져오기
    let formData = {
        emailVerificationEmailAddress : emailVerificationEmailAddress.val(),
        emailVerificationCode: emailVerificationCode.val()
    };
    // 이메일 인증 함수 호출
    submitEmailVerificationForm(formData);

    // 입력 정보 삭제
    $(":input", $(this)).val("");
});


// 이메일 인증 함수
function submitEmailVerificationForm(formData) {

    $.ajax({
        url: "/registration/verifyEmailAddress",
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {

            console.log("[이메일 인증 성공]");

            // 로그인 화면 리디렉트
            // 성공시 반환되는 리스펀스 바디에서 리디렉트 주소 받아옴
            window.location.replace(JSON.parse(jqXHR.responseText)["redirectUrl"]);
        },
        error: function (jqXHR, textStatus, errorThrown) {

            /**
             * 에러 처리 :  Response Status 따라서 다른 에러 처리
             * */

            // 406 에러 : 허용되지 않음 (NOT_ACCEPTABLE)
            if (jqXHR.status === 406) {
                console.log("[이메일 인증 에러] : 406");
                emailVerificationFormErrorView.find(".card-text").empty()
                    .html(`
                                   <p>이메일 인증에 실패했습니다.</p>
                                   <p>원인 : <span id="emailVerificationErrorCausedBy"></span></p>
                                 `);
                $("#emailVerificationErrorCausedBy").text(jqXHR.responseText);


                emailVerificationFormErrorView.show();
            }

            // 400 에러 : 프로세스가 비정상적으로 종료 될 경우 서버에서 ResponseEntity.BAD_REQUEST 반환
            else if (jqXHR.status === 400) {
                console.log("[회원 가입 에러] : 400");

                emailVerificationFormErrorView
                    .find(".card-text")
                    .empty()
                    .html(`
                                   이메일 인증이 정상적으로 완료되지 않았습니다.<br/>
                                   인증 메일 재발송을 요청하여 새로운 코드로 시도 하거나, 잠시 후 다시 시도해 주세요.<br/>
                                   문제가 지속 될 경우 관리자에게 문의하세요.
                                 `);

                emailVerificationFormErrorView.show();
            } else {
                throw new Error("[이메일 인증 에러] : " + errorThrown);
            }
        }
    });
}