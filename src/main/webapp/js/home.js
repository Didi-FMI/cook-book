$(document).ready(function () {
    requestFavourites()
    requestMyRecipes()

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        if (e.target.id == "favourite-tab") {
            requestFavourites()
        } else {
            requestMyRecipes()
        }
    })
});

function requestMyRecipes() {
    $("#recipes-list").empty()

    $.ajax({
        url: "recipe/user",
        method: "GET",
        success: function (data) {
            handleNoResultsView(data, $("#no-results"))
            displayRecipes(data, $("#recipes-list"), false)
        },
        fail: function () {
            window.location.replace('error.html');
        }
    });
}

function requestFavourites() {
    $("#favourites-list").empty()

    $.ajax({
        url: "favourites/all",
        method: "GET",
        success: function (data) {
            handleNoResultsView(data, $("#no-favourites"))
            displayRecipes(data, $("#favourites-list"), true)
        }
    })
}