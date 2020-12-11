$(document).ready(function () {
    requestMyRecipes()
});

function requestMyRecipes() {
    $.ajax({
        url: "recipe/user",
        method: "GET",
        success: function (data) {
            displayRecipes(data)
        },
        fail: function () {
            window.location.replace('error.html');
        }
    });
}