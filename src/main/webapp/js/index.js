$(document).ready(function () {
    $('#switch-signup').click(function () {

        $('#signin-section').hide();
        $('#signup-section').show();

    });

    $('#switch-signin').click(function () {

        $('#signin-section').show();
        $('#signup-section').hide();

    });

    $('#login-form').submit(function (e) {
        e.preventDefault();

        login()
    });

    $('#register-form').submit(function (e) {
        e.preventDefault()

        register()
    });
});

function login() {
    $.ajax({
        url: "login",
        method: "POST",
        data: {
            username: $('#login-username').val(),
            password: $('#login-pass').val()
        },
        complete: function (data) {
            switch (data.status) {
                case 200:
                    window.location.replace('home.html');
                    break;

                default:
                    window.location.replace('error.html');
                    break;
            }
        }
    });
}

function register() {
    $.ajax({
        url: "register",
        method: "POST",
        data: {
            username: $('#username').val(),
            email: $('#email').val(),
            password: $('#pass').val(),
            repeatPassword: $('#re_pass').val()
        },
        complete: function (data) {
            switch (data.status) {
                case 200:
                    window.location.replace('home.html');
                    break;

                default:
                    window.location.replace('error.html');
                    break;
            }
        }
    });
}