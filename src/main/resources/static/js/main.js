/**
 * Created by sungman.you on 2017. 4. 17..
 */

// Main Javascript file
// 모든 JS & CSS 로딩


// JS 로딩 함수
$.loadScripts = function (arr, path) {
    let _arr = $.map(arr, function (src) {
        return $.getScript((path || "") + src);
    });

    _arr.push($.Deferred(function (deferred) {
        $(deferred.resolve);
    }));

    return $.when.apply($, _arr);
};

// CSS 로딩 함수
$.loadStylesheet = function (arr, path) {
    let _arr = $.map(arr, function (src) {
       return $("<link/>", {
            rel: "stylesheet",
            type: "text/css",
            href: (path || "") + src
        })
            .appendTo("head");
    });

    _arr.push($.Deferred(function (deferred) {
        $(deferred.resolve);
    }));
    return $.when.apply($, _arr);
};

// JS 목록
const SCRIPT_ARRAY = [
    "index.js",
    "parsley.min.js"
];

// CSS 목록
const STYLESHEET_ARRAY = [
    "index.css"
];


$(document).ready(function () {

    // JS 로딩
    $.loadScripts(SCRIPT_ARRAY, "/js/")
        .done(function () { // 호출 성공
            console.log("[JAVASCRIPT 호출 성공]");
        })
        .fail(function (error) { // 에러 발생
            console.log("[JAVASCRIPT 호출 과정에서 에러 발생]");
            console.log(error);
        })
        .always(function () { // 항상 수행되는 함수
        });

    // CSS 로딩
    $.loadStylesheet(STYLESHEET_ARRAY, "/css/")
        .done(function () { // 호출 성공
            console.log("[CSS 호출 성공]");
        })
        .fail(function (error) { // 에러 발생
            console.log("[CSS 호출 과정에서 에러 발생]");
            console.log(error);
        })
        .always(function () { // 항상 수행되는 함수
        });
});