/**
 * Created by sungman.you on 2017. 4. 21..
 */
let usernameArrayFromDatabase = []; // DB 에서 쿼리한 유저 네임 담을 배열
let resultCounts = 0; // Ajax 반응형 검색 결과 수 담을 변수


const userInfoView = $("#currentLoggedInUserInfo");
const ajaxReactiveSearchResult = $("#ajaxReactiveSearch-result");
const ajaxReactiveSearchResultCount = $("#ajaxReactiveSearch-resultCount");
const ajaxReactiveSearchUsername = $("#ajaxReactiveSearch-username");
const funcRestrictedByAuth = $("#funcRestrictedByAuth");
const funcRestrictedByAuthResult = $("#funcRestrictedByAuth-result");
const callRestApiResult = $("#callRestApi-result");


/*******************************************************************************
 * 현재 로그인 한 사용자 정보 호출 & 출력
 *******************************************************************************/
function showCurrentLoggedInUserInfo() {
    $.ajax({
        url: "/auth/user",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            // console.log(data);

            // 사용자 정보 출력 부분 클리어
            userInfoView.empty();

            //정보 출력 시작
            $.each(data, function (key, value) {

                // 사용자 권한 정보 출력
                // 배열 형태로 넘어오기 때문에 따로 처리
                if (key === "authorities") {
                    value.forEach(function (authorities) {
                        userInfoView.append($("<li></li>").text("authority : " + authorities.authority));
                    })
                }
                // 나머지 정보 출력
                else {
                    userInfoView.append($("<li></li>").text(key + " : " + value));
                }
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {

            // 401 리턴 : 토큰이 없거나 기존 토큰 사용 불가
            if (jqXHR.status === 401) {
                // 강제 로그아웃해서 헤더 토큰 정보 삭제
                // 사용자에게 새로 로그인 하도록 유도
                logout();
            }
        }
    });
}

/*******************************************************************************
 * 현재 로그인 한 사용자 출력 정보 삭제
 *******************************************************************************/
function clearLoggedInUserInfo() {
    userInfoView.empty();
    userInfoView.append($("<li></li>").text("비로그인 상태입니다"));
}


/*******************************************************************************
 * AJAX 반응형 검색
 * 데이터베이스에서 유저 이름 가져오기
 *******************************************************************************/
// ajaxReactiveSearchUsername.focus(function () {
//     // .focus() : 사용자 입력 칸이 포커싱 되면 함수 호출
//
//     if (usernameArrayFromDatabase.length === 0) {
//         // 배열 .length 체크로 배열에 내용이 없을때만 함수 호출
//         $.ajax({
//             url: "/public/getAllUsername",
//             type: "GET",
//             success: function (data, textStatus, jqXHR) {
//                 console.log("[AJAX Reactive Search : 사용자 목록 가져오기 성공]");
//
//                 data.forEach(function (username) {
//                     // 데이터베이스에서 넘어온 정보 배열에 담기
//                     usernameArrayFromDatabase.push(username);
//                 });
//
//                 //샘플 데이터
//                 usernameArrayFromDatabase.push("Lori Russell", "Debra Green", "Theresa Elliott", "Harry Graham",
//                     "Lois Shaw", "Dorothy Patterson", "Phillip Clark", "Nancy Bishop", "Frank Marshall",
//                     "Jonathan Marshall", "Melissa Collins", "Joseph Hunter", "Maria Wright", "Sandra Gordon",
//                     "Marie Ray", "Wanda Rivera", "Dorothy Walker", "Evelyn Bradley", "Carol Bradley", "Ernest Robinson",
//                     "Alice Ellis", "Jennifer Vasquez", "Russell Fernandez", "Margaret Martinez", "Joshua Berry",
//                     "Albert Porter", "Susan Shaw", "Harold Hansen", "Ruby Roberts", "Lois Ortiz",
//                     "Paula Hunter", "Anthony Welch", "Jeremy Morales", "Lisa Sanders", "Jerry Walker", "Paul Nichols",
//                     "Anne Edwards", "Marie Carr", "Donna Meyer", "Martha Nelson", "Dennis Perry", "Marie Lewis",
//                     "Paul Bennett", "Jennifer Gordon", "Robin Warren", "Anthony Larson", "Beverly Gomez", "Sean Brooks",
//                     "Diana Lynch", "Tina Mcdonald", "abf", "asdfdc", "DSfscs", "erdsf", "dvsabafbdsf",
//                     "유성만", "안녕", "안녕하세요", "으아아아", "권푸름", "나는사람", "이건테스트", "중간에 띄어쓰기가 있다면", "~123123432",
//                     "!@@#@!@", "유성민!", "으아아아어", "전륭", "전욜", "욜륭", "유 성 만", "권푸     름", "김기태", "김게이", "이것저것");
//             },
//
//             error: function (jqXHR, textStatus, errorThrown) {
//                 console.log("[AJAX Reactive Search : 사용자 목록 가져오기 실패]");
//             }
//         });
//     }
// });


/*******************************************************************************
 * AJAX 반응형 검색
 * 입력 칸 자동 완성 및 결과 출력
 *******************************************************************************/
// ajaxReactiveSearchUsername.keyup(function () {
//     $.ajax({
//         url : "/public/asyncFindUserByUsername?username=" + ajaxReactiveSearchUsername.val(),
//         type: "GET",
//         async: true,
//         contentType: "application/json; charset=utf-8",
//         dataType: "json",
//         headers: createAuthorizationTokenHeader(),
//         success: function (data, textStatus, jqXHR) {
//             console.log(data);
//         },
//         error: function (jqXHR, textStatus, errorThrown) {
//             console.log(jqXHR.error());
//         }
//     });
// });

// ajaxReactiveSearchUsername.keyup(function () {
//     // .keyup() : 사용자 입력 칸 내부에서 키보드 누를때마다 함수 호출
//
//     // 검색 결과 수 담을 변수 초기화
//     resultCounts = 0;
//
//     // 기존에 존재하는 검색 결과 삭제
//     ajaxReactiveSearchResult.empty();
//
//     if (ajaxReactiveSearchUsername.val().length === 0) {
//         // 입력 칸에 아무것도 안적었을때
//         ajaxReactiveSearchResult.append("<li></li>").text("검색 결과 없음");
//     } else {
//         // DB 에서 받아온 사용자 이름 배열이랑 비교
//         usernameArrayFromDatabase.forEach(function (username) {
//             if (username
//                 // 소문자로 비교
//                     .toLowerCase()
//                     // 사용자가 입력한 값을 포함하고 있으면
//                     .includes(ajaxReactiveSearchUsername.val())) {
//
//                 // 검색 결과 수 +1
//                 resultCounts++;
//
//                 // 검색 결과 리스트 출력
//                 ajaxReactiveSearchResult.append($("<li></li>").text(username));
//             }
//         });
//     }
//
//     // 검색 결과 수 출력
//     ajaxReactiveSearchResultCount.text(" (" + resultCounts + ")");
// });


/*******************************************************************************
 * 계정 권한에 따른 요청 제한 & REST API 직접 접근
 * 공통 응답 표시
 *******************************************************************************/
function showResponse(view, statusCode, data) {
    // view : 응답 출력 할 뷰
    // statusCode : 응답 코드 (Http Status)
    // data : 출력 할 데이터

    view
        .find(".card-text")
        .empty()
        .append("<strong> 응답 코드 : </strong>" + statusCode)
        .append(`<hr/>`);

    switch (data) {
        // 401 에러 출력
        case "401" : // 서버에서 인증 요구 : Unauthorized
            view
                .find(".card-text")
                .append("로그인이 필요합니다!");
            break;


        // 403 에러 출력
        case "403" : // 서버에서 요청 거부 : Forbidden
            view
                .find(".card-text")
                .append("권한이 없습니다!");
            break;


        // 에러 출력 제외한 나머지 출력 처리
        default :
            $.each(data, function (key, value) {

                // REST API 요청의 경우 href 요소만 따로 처리
                if (value.hasOwnProperty("href")) {
                    view
                        .find(".card-text")
                        // 링크 가능한 구조로 잘라서 출력
                        // .split("{") : { 기호 기준으로 나누기
                        .append(key + " : " + "<a href='#'>" + value.href.split("{")[0] + "</a>" + "<br/>");
                }


                // 나머지 요청들 처리
                else {
                    view
                        .find(".card-text")
                        .append(key + " : " + value + "<br/>");
                }
            });
            break;
    }
}

/*******************************************************************************
 * 계정 권한에 따른 요청 제한 & REST API 직접 접근
 * 공통 에러 처리
 *******************************************************************************/
function errorHandler(view, jqXHR, textStatus, errorThrown) {
    // view : 에러 출력 할 뷰
    // jqXHR : $.ajax() 함수가 반환하는 객체
    //         XMLHttpRequest 객체의 상위 집합


    switch (jqXHR.status) {
        case 401 :
            showResponse(view, jqXHR.status, "401");
            break;
        case 403 :
            showResponse(view, jqXHR.status, "403");
            break;
        default :
            break;
    }
}


/*******************************************************************************
 * 계정 권한에 따른 요청 제한
 * 인증이 필요없는 함수
 *******************************************************************************/
$("#funcRestrictedByAuth-public").click(function () {
    $.ajax({
        url: "/public/test",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            showResponse(funcRestrictedByAuthResult, jqXHR.status, data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorHandler(funcRestrictedByAuthResult, jqXHR, textStatus, errorThrown);
        }
    });
});

/*******************************************************************************
 * 계정 권한에 따른 요청 제한
 * ADMIN 권한 전용 함수
 *******************************************************************************/
$("#funcRestrictedByAuth-adminOnly").click(function () {
    $.ajax({
        url: "/admin/test",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            showResponse(funcRestrictedByAuthResult, jqXHR.status, data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorHandler(funcRestrictedByAuthResult, jqXHR, textStatus, errorThrown);
        }
    });
});


/*******************************************************************************
 * REST API 직접 호출
 * API 목록 출력
 *******************************************************************************/
$("#callRestApi-btn").click(function () {
    $.ajax({
        url: "/api",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            showResponse(callRestApiResult, jqXHR.status, data._links);

            callRestApiResult.find($("a").click(function (event) {
                event.preventDefault();

                callSelectedApi($(this).text());
            }));
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorHandler(callRestApiResult, jqXHR, textStatus, errorThrown);
        }
    })
});

/*******************************************************************************
 * REST API 직접 호출
 * API 목록에서 링크 클릭
 *******************************************************************************/

function callSelectedApi(selectedApiUrl) {
    $.ajax({
        url: selectedApiUrl,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            console.log(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorHandler(callRestApiResult, jqXHR, textStatus, errorThrown);
        }
    });
}