/**
 * Created by sungman.you on 2017. 4. 21..
 */


// 회원가입 폼
const registrationForm = $("#registrationForm");
// 에러 뷰
const registrationFormErrorView = $("#registrationForm-error");


// 사용자 입력 검증 오류 발생시 버튼 비활성화
registrationForm.find("input").on("keyup", function () {
    if ($(".parsley-error").length === 0) {
        registrationForm.find("button").prop("disabled", "");
    } else {
        registrationForm.find("button").prop("disabled", "disabled");
    }
});


// 회원 가입 폼 submit 함수
registrationForm.submit(function (event) {
    event.preventDefault();

    // 사용자 입력 정보 가져오기
    let formData = {
        email: $("#registrationEmail").val(),
        username: $("#registrationUsername").val(),
        password: $("#registrationPassword").val(),
        passwordConfirmation: $("#registrationPasswordConfirmation").val()
    };
    // 회원가입 함수 호출
    submitRegistrationForm(formData);
    // 입력 정보 삭제
    $(":input", $(this)).val("");
});


// 회원 가입 함수
function submitRegistrationForm(formData) {

    $.ajax({
        url: "/registration",
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {

            console.log("[회원 가입 성공]");

            // 이메일 인증 화면 리디렉트
            // 성공시 반환되는 리스펀스 바디에서 리디렉트 주소 받아옴
            window.location.replace(JSON.parse(jqXHR.responseText)["redirectUrl"]);
        },
        error: function (jqXHR, textStatus, errorThrown) {

            /**
             * 에러 처리 :  Response Status 따라서 다른 에러 처리
             * */

            // 406 에러 : 허용되지 않음 (NOT_ACCEPTABLE)
            if (jqXHR.status === 406) {
                console.log("[회원 가입 에러] : 406");
                registrationFormErrorView
                    .find(".card-text")
                    .empty()
                    .html(`
                                   <p>회원 가입에 실패했습니다.</p>
                                   <p>원인 : <span id="registrationErrorCausedBy"></span></p>
                                 `);
                $("#registrationErrorCausedBy").text(jqXHR.responseText);
                registrationFormErrorView
                    .show();
            }

            // 400 에러 : 프로세스가 비정상적으로 종료 될 경우 서버에서 ResponseEntity.BAD_REQUEST 반환
            else if (jqXHR.status === 400) {
                console.log("[회원 가입 에러] : 400");

                registrationFormErrorView
                    .find(".card-text")
                    .empty()
                    .html(`
                                   회원 가입이 정상적으로 완료되지 않았습니다.<br/>
                                   잠시 후 다시 시도해 주세요.<br/>
                                   문제가 지속 될 경우 관리자에게 문의하세요.
                                 `);
                registrationFormErrorView
                    .show();
            } else {
                throw new Error("[회원 가입 에러] : " + errorThrown);
            }
        }
    });
}