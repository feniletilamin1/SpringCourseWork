


$("#startDate").on("input", function () {
    let startDt = new Date($('#startDate').val());
    let endDt = new Date($('#endDate').val()); 
    if (startDt > endDt) {
        $('#startDateSpan').text("Дата начала работ не может превышать, дату завершения работ");
        $('#startDate').attr('aria-invalid', 'true');
    }
    else {
        $('#endDateSpan').text("");
        $('#startDateSpan').text("");
        $('#startDate').attr('aria-invalid', 'false');
    }
});

$("#endDate").on("input", function () {
    let startDt = new Date($('#startDate').val());
    let endDt = new Date($('#endDate').val()); 

    if (startDt > endDt) {
        $('#endDateSpan').text("Дата конца работ не может быть меньше, даты начала работ");
        $('#endDate').attr('aria-invalid', 'true');
    }
    else {
        $('#startDateSpan').text("");
        $('#endDateSpan').text("");
        $('#endDate').attr('aria-invalid', 'false');
    }
});
