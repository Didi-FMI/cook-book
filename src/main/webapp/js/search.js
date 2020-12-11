$(document).ready(function () {
    fillCuisines()

    fillDiets()

    $('#search-form').submit(function (e) {
        e.preventDefault();

        $("#recipes-list").empty()

        search()
    });
});

function search() {
    $.ajax({
        url: "recipe/search",
        method: "GET",
        data: {
            name: $('#search').val(),
            image: $('#image').val(),
            cuisineId: $('#cuisine').val(),
            dietId: $('#diet').val()
        },
        success: function (data) {
            handleNoResultsView(data, $("#no-results"))
            displayRecipes(data, $("#recipes-list"), false)
        }
    })
}