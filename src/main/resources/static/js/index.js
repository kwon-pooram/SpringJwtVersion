/**
 * Created by stephan on 20.03.16.
 */

// $(function () {
// VARIABLES =============================================================

// FUNCTIONS =============================================================

function showResponse(statusCode, message) {
    $response
        .empty()
        .text("status code: " + statusCode + "\n-------------------------\n" + message);
}

// REGISTER EVENT LISTENERS =============================================================

$("#logoutButton").click(doLogout);

$("#exampleServiceBtn").click(function () {
    $.ajax({
        url: "/persons",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            showResponse(jqXHR.status, JSON.stringify(data));
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showResponse(jqXHR.status, errorThrown);
        }
    });
});

$("#adminServiceBtn").click(function () {
    $.ajax({
        url: "/protected",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        headers: createAuthorizationTokenHeader(),
        success: function (data, textStatus, jqXHR) {
            showResponse(jqXHR.status, data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showResponse(jqXHR.status, errorThrown);
        }
    });
});

$loggedIn.click(function () {
    $loggedIn
        .toggleClass("text-hidden")
        .toggleClass("text-shown");
});

// INITIAL CALLS =============================================================
if (getJwtToken()) {
    $login.hide();
    $notLoggedIn.hide();
    showTokenInformation();
    showCurrentLoggedInUserInfo();
}
// });