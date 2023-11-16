// Please see documentation at https://docs.microsoft.com/aspnet/core/client-side/bundling-and-minification
// for details on configuring this project to bundle and minify static web assets.

// Write your JavaScript code.

$(".burger-menu").click(function () {
    $(".burger-menu").toggleClass("burger-menu--active");
    $(".menu").toggleClass("menu--active");
});

$.mask.definitions['h'] = "[0|1|2|3|4|5|6|7|9]"
$(".mask-phone").mask("+7 (h99) 999-99-99");
$(".mask-index").mask("999999");

let inputs = $(".form__input");
let labels = $(".form__label")

if ($("#userTypeSelect").val() == "Сотрудник") {
    for (let i = 9; i < inputs.length; i++) {
        inputs[i].classList.remove("form__input--hidden");
        inputs[i].value = "";
    }

    for (let i = 8; i < labels.length; i++) {
        labels[i].classList.remove("form__label--hidden");
    }
}

$("#userTypeSelect").on('change', function () {
    let inputs = $(".form__input");
    let labels = $(".form__label")
    let valueSelected = this.value;


    if (valueSelected == "Клиент") {
        for (let i = 9; i < inputs.length; i++) {
            inputs[i].classList.add("form__input--hidden");
            inputs[10].value = "Специальность";
        }

        for (let i = 8; i < labels.length; i++) {
            labels[i].classList.add("form__label--hidden");
        }
    }
    else if (valueSelected == "Сотрудник") {
        for (let i = 9; i < inputs.length; i++) {
            inputs[i].classList.remove("form__input--hidden");
            inputs[10].value = "";
        }

        for (let i = 8; i < labels.length; i++) {
            labels[i].classList.remove("form__label--hidden");
        }
    }
});

const elements = document.querySelectorAll('.form__select');

elements.forEach(el => {
    const choices = new Choices(el, {
        itemSelectText: 'Выбрать',
        noResultsText: 'Ничего не найдено',
        noChoicesText: "Нечего выбирать",
    });

})
