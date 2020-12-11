$(document).ready(function () {
    fillCuisines()

    fillDiets()

    $('#create-recipe').submit(function (e) {
        e.preventDefault();

        submitRecipe()
    });
});

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