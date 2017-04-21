/**
 * Created by sungman.you on 2017. 4. 17..
 */

/**
 * 이니셜라이저 (페이지 로딩시 호출되는 함수들)
 * JS & CSS 로딩 담당
 * 페이지 호출시 아래 선언된 함수로 각 뷰에 필요한 리소스 호출
 */


// JS 로딩 함수
function SCRIPT_LOADER (array, path) {
    let jsArray = $.map(array, function (src) {
        console.log("[JS 로딩] : " + array.toString());
        return $.getScript((path || "") + src);
    });

    jsArray.push($.Deferred(function (deferred) {
        $(deferred.resolve);
    }));

    return $.when.apply($, jsArray);
}

// CSS 로딩 함수
function CSS_LOADER (array, path) {
    let cssArray = $.map(array, function (src) {
        console.log("[CSS 로딩] : " + array.toString());
        return $("<link/>", {
            rel: "stylesheet",
            type: "text/css",
            href: (path || "") + src
        }).appendTo("head");
    });

    cssArray.push($.Deferred(function (deferred) {
        $(deferred.resolve);
    }));
    return $.when.apply($, cssArray);
}