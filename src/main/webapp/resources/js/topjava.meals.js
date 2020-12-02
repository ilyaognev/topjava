var ctx;

function filter() {
    $.ajax({
        url: ctx.ajaxUrl + "filter",
        data: $('#filter').serialize(),
        type: "GET"
    }).done(function () {
        updateTable();
    });
}

function cancelFilter() {
    $('#filter')[0].reset();
    $.ajax({
        url: ctx.ajaxUrl,
        method: "GET",
        dataType: 'json',
       }).done(function () {
        updateTable();
    });
}

$(function () {
    ctx = {
        ajaxUrl: "meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    };
    makeEditable();
});