$(document).ready(function () {
    fillCuisines()

    fillDiets()

    $('#create-recipe').submit(function (e) {
        e.preventDefault();

        submitRecipe()
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

function submitRecipe() {
    $.ajax({
        url: "recipe/create",
        method: "POST",
        data: {
            name: $('#name').val(),
            image: $('#image').val(),
            description: $('#description').val(),
            ingredients: $('#ingredients').val(),
            instructions: $('#instructions').val(),
            cuisineId: $('#cuisine').val(),
            dietId: $('#diet').val()
        },
        success: function () {
            window.location.replace('home.html');
        }
    })
}