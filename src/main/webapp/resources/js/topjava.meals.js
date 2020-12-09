let ctx, mealAjaxUrl = "profile/meals/";

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
                            return formaterDateTime(data);
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

const sd = $('#startDate');
const ed = $('#endDate');
sd.datetimepicker({
    format: 'Y-m-d',
    onShow: function (ct) {
        this.setOptions({
            maxDate: ed.val() ? ed.val() : false
        })
    },
    timepicker: false
});

ed.datetimepicker({
    format: 'Y-m-d',
    onShow: function (ct) {
        this.setOptions({
            minDate: sd.val() ? sd.val() : false
        })
    },
    timepicker: false
});

const st = $('#startTime');
const et = $('#endTime');
st.datetimepicker({
    format: 'H:i',
    onShow: function (ct) {
        this.setOptions({
            maxTime: et.val() ? et.val() : false
        })
    },
    datepicker: false
});

et.datetimepicker({
    format: 'H:i',
    onShow: function (ct) {
        this.setOptions({
            minTime: st.val() ? st.val() : false
        })
    },
    datepicker: false
});

const dt = $('#dateTime');
dt.datetimepicker({
    format: 'Y-m-d H:i',
});

dt.datetimepicker({
    format: 'Y-m-d H:i',
});