const mealAjaxUrl = "meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable({
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
    // var startDate = $('#startDate');
    // var endDate = $('#endDate');
    // startDate.datetimepicker({
    //     timepicker: false,
    //     format: 'Y-m-d',
    //     formatDate: 'Y-m-d',
    //     onShow: function (ct) {
    //         this.setOptions({
    //             maxDate: endDate.val() ? endDate.val() : false
    //         })
    //     }
    // });
    // endDate.datetimepicker({
    //     timepicker: false,
    //     format: 'Y-m-d',
    //     formatDate: 'Y-m-d',
    //     onShow: function (ct) {
    //         this.setOptions({
    //             minDate: startDate.val() ? startDate.val() : false
    //         })
    //     }
    // });
    //
    // var startTime = $('#startTime');
    // var endTime = $('#endTime');
    // startTime.datetimepicker({
    //     datepicker: false,
    //     format: 'H:i',
    //     onShow: function (ct) {
    //         this.setOptions({
    //             maxTime: endTime.val() ? endTime.val() : false
    //         })
    //     }
    // });
    // endTime.datetimepicker({
    //     datepicker: false,
    //     format: 'H:i',
    //     onShow: function (ct) {
    //         this.setOptions({
    //             minTime: startTime.val() ? startTime.val() : false
    //         })
    //     }
    // });
    //
    // $('#dateTime').datetimepicker({
    //     format: 'Y-m-d H:i'
    // });
});