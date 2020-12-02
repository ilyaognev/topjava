var ctx;

function filter() {
    $.ajax({
        url: ctx.ajaxUrl + "filter",
        data: $(this).serialize(),
        type: "GET"
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