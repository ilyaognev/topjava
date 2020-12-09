var ctx,mealAjaxUrl = "profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    ctx = {
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (data, type, row) {
                        if (type === "display") {
                            return data.replace("T", " ");
                        }
                        return data;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    };
    makeEditable();
});

$('#startDate').datetimepicker({
    format:'Y-m-d',
    onShow:function( ct ){
        this.setOptions({
            maxDate:$('#endDate').val()?$('#endDate').val():false
        })
    },
    timepicker:false
});

$('#endDate').datetimepicker({
    format:'Y-m-d',
    onShow:function( ct ){
        this.setOptions({
            minDate:$('#startDate').val()?$('#startDate').val():false
        })
    },
    timepicker:false
});

$('#startTime').datetimepicker({
    format:'H:i',
    onShow:function( ct ){
        this.setOptions({
            maxTime:$('#endTime').val()?$('#endTime').val():false
        })
    },
    datepicker:false
});

$('#endTime').datetimepicker({
    format:'H:i',
    onShow:function( ct ){
        this.setOptions({
            minTime:$('#startTime').val()?$('#startTime').val():false
        })
    },
    datepicker:false
});

$('#dateTime').datetimepicker({
    format:'Y-m-d H:i',
});