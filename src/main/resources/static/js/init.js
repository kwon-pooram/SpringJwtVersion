/**
 * Created by sungman.you on 2017. 4. 17..
 */

// Initializer
// 공용 JS & CSS 로딩

// !!!!!!!!!!!!!!!!!!!!!!!
// 사용 안하는 스크립트 (테스트용)
// !!!!!!!!!!!!!!!!!!!!!!!


// JS 로딩 함수
// $.loadScripts = function (array, path) {
//     let jsArray = $.map(array, function (src) {
//         console.log("[JS 로딩중] : " + array.toString());
//         return $.getScript((path || "") + src);
//     });
//
//     jsArray.push($.Deferred(function (deferred) {
//         $(deferred.resolve);
//     }));
//
//     return $.when.apply($, jsArray);
// };

// CSS 로딩 함수
// $.loadStylesheet = function (array, path) {
//     let cssArray = $.map(array, function (src) {
//         console.log("[CSS 로딩중] : " + array.toString());
//         return $("<link/>", {
//             rel: "stylesheet",
//             type: "text/css",
//             href: (path || "") + src
//         })
//             .appendTo("head");
//     });
//
//     cssArray.push($.Deferred(function (deferred) {
//         $(deferred.resolve);
//     }));
//     return $.when.apply($, cssArray);
// };

// JS 목록
// const JAVASCRIPT_ARRAY = [
//     "parsley.min.js", // 클라이언트 사이드 폼 입력 값 검증 라이브러리
// ];

// CSS 목록
// const STYLESHEET_ARRAY = [
//     "index.css"
// ];


// $(document).ready(function () {
//
//     // JS 로딩
//     $.loadScripts(JAVASCRIPT_ARRAY, "/js/")
//         .done(function () { // 호출 성공
//             console.log("[JAVASCRIPT 호출 성공]");
//         })
//         .fail(function (error) { // 에러 발생
//             console.log("[JAVASCRIPT 호출 과정에서 에러 발생]");
//             console.log(error);
//         })
//         .always(function () { // 항상 수행되는 함수
//         });
//
//     // CSS 로딩
//     $.loadStylesheet(STYLESHEET_ARRAY, "/css/")
//         .done(function () { // 호출 성공
//             console.log("[CSS 호출 성공]");
//         })
//         .fail(function (error) { // 에러 발생
//             console.log("[CSS 호출 과정에서 에러 발생]");
//             console.log(error);
//         })
//         .always(function () { // 항상 수행되는 함수
//         });
// });