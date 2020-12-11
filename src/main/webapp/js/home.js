$(document).ready(function () {
    fillCuisines()

    fillDiets()

    $('#search-form').submit(function (e) {
        e.preventDefault();

        $("#recipes-list").empty()

        search()
    });
});

function fillCuisines() {
    $.ajax({
        url: "recipe/cuisines",
        method: "GET",
        success: function (data) {
            $.each(data, function (index, element) {
                $('#cuisine').append(new Option(element.name, element.id));
            })
        }
    });
}

function fillDiets() {
    $.ajax({
        url: "recipe/diets",
        method: "GET",
        success: function (data) {
            $.each(data, function (index, element) {
                $('#diet').append(new Option(element.name, element.id));
            })
        }
    });
}

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
            fillResult(data)
        }
    })
}

function fillResult(data) {
    var noResults = $("#no-results")

    if (data.length == 0) {
        noResults.show()

        return
    }

    noResults.hide()

    var recipesList = $("#recipes-list")

    $.each(data, function (index, element) {
        var recipe = $("#recipe").clone().attr("id", element.id)

        recipe.find("#recipe-name").text(element.name)
        recipe.find("#recipe-img").attr("src", element.image)
        recipe.find("#recipe-description").text(element.description)
        recipe.find("#recipe-ingredients").text(element.ingredients)
        recipe.find("#recipe-instructions").text(element.instructions)

        var viewMoreButton = recipe.find("#btn-view-more")
        var moreInfoView = recipe.find("#more-info")

        viewMoreButton.click(function () {

            if (moreInfoView.is(":hidden")) {
                // moreInfoView.show()
                moreInfoView.attr('hidden', false)
                viewMoreButton.text("View less")
            } else {
                // moreInfoView.hide()
                moreInfoView.attr('hidden', true)
                viewMoreButton.text("View more")
            }
        })

        recipe.find("#btn-delete").click(function () {
            deleteRecipe(element.id)
        })

        recipesList.append(recipe)

        recipe.show()
    });
}