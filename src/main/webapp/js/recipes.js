function displayRecipes(data, list, isFromFavourites) {

    $.each(data, function (index, element) {
        var recipe = $("#recipe").clone().attr("id", element.id)

        recipe.find("#recipe-name").text(element.name)
        recipe.find("#recipe-img").attr("src", element.image)
        recipe.find("#recipe-description").text(element.description)
        recipe.find("#recipe-ingredients").text(element.ingredients)
        recipe.find("#recipe-instructions").text(element.instructions)

        recipe.find("#recipe-name").click(function () {
            window.location.href = "recipe.html?id=" + element.id;
        })

        var addToFavouriteIcon = recipe.find("#add-to-favourite")
        var removeFromFavouriteIcon = recipe.find("#remove-from-favourite")

        setIsFavourite(element.id, addToFavouriteIcon, removeFromFavouriteIcon)

        addToFavouriteIcon.click(function () {
            addToFavourite(element.id, addToFavouriteIcon, removeFromFavouriteIcon)
        })

        removeFromFavouriteIcon.click(function () {
            removeFromFavourite(element.id, addToFavouriteIcon, removeFromFavouriteIcon, isFromFavourites)
        })

        var viewMoreButton = recipe.find("#btn-view-more")
        var moreInfoView = recipe.find("#more-info")

        viewMoreButton.click(function () {

            if (moreInfoView.is(":hidden")) {
                moreInfoView.attr('hidden', false)
                viewMoreButton.text("View less")
            } else {
                moreInfoView.attr('hidden', true)
                viewMoreButton.text("View more")
            }
        })

        handleDeleteVisibility(element.id, recipe.find("#btn-delete"))

        list.append(recipe)

        recipe.show()
    });
}

function handleNoResultsView(data, view) {
    if (data.length == 0) {
        view.show()
    } else {
        view.hide()
    }
}

function handleDeleteVisibility(id, removeButton) {
    $.ajax({
        url: "/recipe/delete",
        method: "GET",
        data: {
            id: id
        },
        success: function (data) {
            if (data == true) {
                removeButton.show()

                removeButton.click(function () {
                    deleteRecipe(id)
                })
            } else {
                removeButton.hide()
            }
        }
    })
}

function deleteRecipe(id) {
    $.ajax({
        url: "recipe/delete",
        method: "DELETE",
        data: {
            id: id
        },
        success: function (data) {
            $('#' + data).remove()
        },
        fail: function () {
            window.location.replace('error.html');
        }
    });
}

function setIsFavourite(id, add, remove) {
    $.ajax({
        url: "favourites/check",
        method: "GET",
        data: {
            id: id
        },
        success: function (data) {
            if (data) {
                add.hide()
                remove.show()
            } else {
                add.show()
                remove.hide()
            }
        },
        fail: function () {
            add.show()
            remove.hide()
        }
    })
}

function addToFavourite(id, add, remove) {
    $.ajax({
        url: "favourites/add",
        method: "POST",
        data: {
            id: id
        },
        success: function () {
            add.hide()
            remove.show()
        }
    })
}

function removeFromFavourite(id, add, remove, isFromFavourites) {
    $.ajax({
        url: "favourites/remove",
        method: "POST",
        data: {
            id: id
        },
        success: function () {
            add.show()
            remove.hide()

            if (isFromFavourites) {
                $("#favourites-list").find("#" + id).remove();
            }
        }
    })
}