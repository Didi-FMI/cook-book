$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    requestRecipe(id)
    getComments(id)

    $("#comment-post").submit(function (e) {
        e.preventDefault();

        postComment(id)
    });
});

function requestRecipe(id) {
    $.ajax({
        url: "recipe/get",
        method: "GET",
        data: {
            id: id
        },
        success: function (data) {
            displayRecipe(data)
        }
    })
}

function displayRecipe(data) {
    $("#recipe-name").text(data.name)
    $("#recipe-img").attr("src", data.image)
    $("#recipe-description").text(data.description)
    $("#recipe-ingredients").text(data.ingredients)
    $("#recipe-instructions").text(data.instructions)
}

function getComments(id) {
    $.ajax({
        url: "comments/all",
        method: "GET",
        data: {
            recipeId: id
        },
        success: function (data) {
            fillComments(data)
        }
    })
}

function fillComments(data) {
    var emptyComments = $("#empty-comments")

    if (data.length == 0) {
        emptyComments.show()

        return
    }

    emptyComments.hide()

    $.each(data, function (index, element) {
        addComment(element)
    })
}

function addComment(data) {
    $("#empty-comments").hide()

    var comment = $("#comment-item").clone().attr("id", data.id)

    comment.find("#user-name").text(data.user.username)
    comment.find("#comment").text(data.content)

    handleDeleteAction(data.id, comment.find("#btn-delete"))

    $("#comments-list").append(comment)

    comment.show()
}

function handleDeleteAction(id, deleteButton) {
    $.ajax({
        url: "comments/delete",
        method: "GET",
        data: {
            id: id
        },
        success: function (data) {
            if (data) {
                deleteButton.show()
            } else {
                deleteButton.hide()
            }
        }
    })

    deleteButton.click(function () {
        $.ajax({
            url: "comments/delete",
            method: "POST",
            data: {
                id: id
            },
            success: function (data) {
                removeComment(id)
                $('#' + id).remove()

                if ($('#comment-item li').length == 0) {
                    $("#empty-comments").show()
                }
            }
        })
    })
}

function removeComment(id) {
    $("#comment-item")
}

function postComment(id) {
    var content = $("#comment-content").val()

    $.ajax({
        url: "comments/post",
        method: "POST",
        data: {
            recipeId: id,
            content: content
        },
        success: function (data) {
            addComment(data)
        }
    })
}