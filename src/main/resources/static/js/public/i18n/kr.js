/**
 * Created by sungman.you on 2017. 4. 21..
 */

// Parsley 에러 한국어 메세지 출력

Parsley.addMessages('kr', {
    defaultMessage: "유효하지 않은 입력입니다.",
    type: {
        email:        "유효한 이메일 주소가 아닙니다.",
        url:          "유효한 URL이 아닙니다.",
        number:       "유효한 숫자가 아닙니다.",
        integer:      "유효한 정수가 아닙니다.",
        digits:       "유효한 수가 아닙니다.",
        alphanum:     "영문자와 숫자만 입력 가능합니다."
    },
    notblank:       "공백은 허용되지 않습니다.",
    required:       "필수 입력 정보입니다.",
    pattern:        "유효하지 않습니다.",
    min:            "최소 %s 자리 이상입니다.",
    max:            "최대 %s 자리 이하입니다.",
    range:          "최소 %s자리, 최대 %s자리 까지 입력 가능합니다.",
    minlength:      "최소 %s 자리 이상입니다.",
    maxlength:      "최대 %s 자리 이하입니다.",
    length:         "최소 %s자리, 최대 %s자리 까지 입력 가능합니다.",
    mincheck:       "최소 %s개 이상 선택해야 합니다.",
    maxcheck:       "최대 %s개 이하까지 선택 가능합니다.",
    check:          "최소 %s개 이상, 최대 %s개 까지 선택 가능합니다.",
    equalto:        "비밀번호가 일치하지 않습니다."
});

Parsley.setLocale('kr');