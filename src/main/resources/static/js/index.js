/**
 * Created by sungman.you on 2017. 4. 21..
 */

// DB 에서 쿼리한 유저 네임 담을 배열
let usernames = [];
let resultCounts = 0;

const userInfoView = $("#currentLoggedInUserInfo");
const ajaxReactiveSearchResult = $("#ajaxReactiveSearch-result");
const ajaxReactiveSearchResultCount = $("#ajaxReactiveSearch-resultCount");
const ajaxReactiveSearchUsername = $("#ajaxReactiveSearch-username");
const funcRestrictedByAuth = $("#funcRestrictedByAuth");
const funcRestrictedByAuthResult = $("#funcRestrictedByAuth-result");

// 로그아웃 함수
function logout() {
    // 로컬스토리지 토큰 삭제
    removeJwtToken();
    // 인덱스 화면 리디렉트
    window.location.replace("/index");
}


// 현재 로그인 한 사용자 정보 호출 & 출력 함수
function showCurrentLoggedInUserInfo() {
    $.ajax({
        url: "/auth/user",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            userInfoView.empty();
            userInfoView.append($("<li></li>").text("Username: " + data.username));
            userInfoView.append($("<li></li>").text("Email: " + data.email));
            userInfoView.append($("<li></li>").text("Enabled: " + data.enabled));
            userInfoView.append($("<li></li>").text("accountNonLocked: " + data.accountNonLocked));
            userInfoView.append($("<li></li>").text("accountNonExpired: " + data.accountNonExpired));
            userInfoView.append($("<li></li>").text("credentialsNonExpired: " + data.credentialsNonExpired));
            userInfoView.append($("<li></li>").text("Token: " + getJwtToken()));

            data.authorities.forEach(function (authorityItem) {
                userInfoView.append($("<li></li>").text("Authority: " + authorityItem.authority));
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
                logout();
            }
        }
    });
}

function clearLoggedInUserInfo() {
    userInfoView.empty();
    userInfoView.append($("<li></li>").text("비로그인 상태입니다"));
}


// AJAX 반응형 검색 입력 칸 선택시 호출되는 함수
// 데이터베이스에서 유저 이름 가져오기
ajaxReactiveSearchUsername.focus(function () {
    if (usernames.length === 0) {
        $.ajax({
            url: "/public/getAllUsername",
            type: "GET",
            success: function (data, textStatus, jqXHR) {
                console.log("[AJAX Reactive Search : 사용자 목록 가져오기 성공]");
                data.forEach(function (username) {
                    usernames.push(username);
                });
                //샘플 데이터
                usernames
                    .push("abf", "asdfdc", "DSfscs", "erdsf", "dvsabafbdsf",
                        "유성만", "안녕", "안녕하세요", "으아아아", "권푸름", "나는사람", "이건테스트", "중간에 띄어쓰기가 있다면",
                    "!@@#@!@", "유성민!", "으아아아어", "전륭", "전욜", "욜륭", "유 성 만", "권푸     름");
                usernames.push("Lori Russell", "Debra Green", "Theresa Elliott", "Harry Graham",
                    "Lois Shaw", "Dorothy Patterson", "Phillip Clark", "Nancy Bishop", "Frank Marshall",
                    "Jonathan Marshall", "Melissa Collins", "Joseph Hunter", "Maria Wright", "Sandra Gordon",
                    "Marie Ray", "Wanda Rivera", "Dorothy Walker", "Evelyn Bradley", "Carol Bradley", "Ernest Robinson",
                    "Alice Ellis", "Jennifer Vasquez", "Russell Fernandez", "Margaret Martinez", "Joshua Berry",
                    "Albert Porter", "Susan Shaw", "Harold Hansen", "Ruby Roberts", "Lois Ortiz",
                    "Paula Hunter", "Anthony Welch", "Jeremy Morales", "Lisa Sanders", "Jerry Walker", "Paul Nichols",
                    "Anne Edwards", "Marie Carr", "Donna Meyer", "Martha Nelson", "Dennis Perry", "Marie Lewis",
                    "Paul Bennett", "Jennifer Gordon", "Robin Warren", "Anthony Larson", "Beverly Gomez", "Sean Brooks",
                    "Diana Lynch", "Tina Mcdonald");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("[AJAX Reactive Search : 사용자 목록 가져오기 실패]");
            }
        });
    } else {
        return null;
    }
});


// AJAX 반응형 검색 입력 칸 자동 완성
ajaxReactiveSearchUsername.keyup(function () {

    resultCounts = 0;
    ajaxReactiveSearchResult.empty();
    if (ajaxReactiveSearchUsername.val().length === 0) {
        ajaxReactiveSearchResult.append("<li></li>").text("검색 결과 없음");
    } else {
        usernames.forEach(function (username) {
            if (username.toLowerCase().includes(ajaxReactiveSearchUsername.val())) {
                resultCounts++;
                ajaxReactiveSearchResult.append($("<li></li>").text(username));
            }
        });
    }
    ajaxReactiveSearchResultCount.text(" (" + resultCounts + ")");
});


// 응답 표시 함수
function showResponse(statusCode, data) {
    funcRestrictedByAuthResult
        .find(".card-text")
        .empty()
        .append("<strong> 응답 코드 : </strong>" + statusCode)
        .append(`<hr/>`);

    switch (data) {
        case "401" : // 서버에서 인증 요구 : Unauthorized
            funcRestrictedByAuthResult
                .find(".card-text")
                .append("로그인이 필요합니다!");
            break;
        case "403" : // 서버에서 요청 거부 : Forbidden
            funcRestrictedByAuthResult
                .find(".card-text")
                .append("권한이 없습니다!");
            break;
        default :
            $.each(data, function (key, value) {
                funcRestrictedByAuthResult
                    .find(".card-text")
                    .append(key + " : " + value + "<br/>");
            });
            break;
    }
}

//        에러 처리 함수
function errorHandler(jqXHR, textStatus, errorThrown) {
    switch (jqXHR.status) {
        case 401 :
            showResponse(jqXHR.status, "401");
            break;
        case 403 :
            showResponse(jqXHR.status, "403");
            break;
        default :
            break;
    }
}

// 인증 불필요 함수
$("#funcRestrictedByAuth-public").click(function () {
    $.ajax({
        url: "/public/test",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            showResponse(jqXHR.status, data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorHandler(jqXHR, textStatus, errorThrown);
        }
    });
});

// 어드민 전용 함수
$("#funcRestrictedByAuth-adminOnly").click(function () {
    $.ajax({
        url: "/admin/test",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            showResponse(jqXHR.status, data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorHandler(jqXHR, textStatus, errorThrown);
        }
    });
});