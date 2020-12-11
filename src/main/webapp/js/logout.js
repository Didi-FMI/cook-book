$(document).ready(function () {
    $('#logout').click(function () {
        logout()
    });
});

function logout() {
    $.ajax({
        url: "logout",
        method: "POST",
        complete: function () {
            window.location.replace('index.html');
        }
    });
}