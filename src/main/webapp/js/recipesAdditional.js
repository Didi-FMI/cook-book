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
