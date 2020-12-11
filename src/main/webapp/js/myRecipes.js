$(document).ready(function () {
    requestMyRecipes()
});

function requestMyRecipes() {
    $.ajax({
        url: "recipe/user",
        method: "GET",
        success: function (data) {
            fillRecipes(data)
        },
        fail: function () {
            window.location.replace('error.html');
        }
    });
}

function fillRecipes(data) {
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

function deleteRecipe(data) {
    $.ajax({
        url: "recipe/delete",
        method: "DELETE",
        data: {
            id: data
        },
        success: function (data) {
            $('#' + data).remove()
        },
        fail: function () {
            window.location.replace('error.html');
        }
    });
}