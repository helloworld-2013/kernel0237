$(document).ready(function(){
    var $table = $("#blacklist-table"),
        selected = 0;

    $table.bootstrapTable()
    .on('check-all.bs.table', function(e){
        selected = this.rows.length - 1;
        $('#deleteBtn').removeClass('disabled');
    })
    .on('check.bs.table', function(e){
        selected++;
        $('#deleteBtn').removeClass('disabled');
    })
    .on('uncheck-all.bs.table', function(e){
        selected = 0;
        $('#deleteBtn').removeClass('disabled').addClass('disabled');
    })
    .on('uncheck.bs.table', function(e){
        selected--;
        if (selected==0) $('#deleteBtn').removeClass('disabled').addClass('disabled');
    });

    $("#deleteBtn").click(function(){
        var selections = $table.bootstrapTable('getSelections'),
            ids = $.map(selections, function(row){ return row.id; });

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/EmailDomainBlacklister/delete?ids=' + ids,
            success: function() {
                location.reload();
            }
        });
    });

    $("#saveBtn").click(function(){
        var newEmailDomain = $("#inputEmail").val();

        $.ajax({
            url: 'http://localhost:8080/EmailDomainBlacklister/get?emailDomain=' + newEmailDomain,
            success: function(data) {
                if (data.emailBlacklistForm!=null) {
                    $("#addEmailErrorMsg").removeClass("hide");
                    $("#addEmailSuccessMsg").removeClass("hide").addClass("hide");
                } else {
                    var paramData = {emailBlacklistForm: {emailDomain: newEmailDomain}};
                    $.ajax({
                        type: 'POST',
                        url: 'http://localhost:8080/EmailDomainBlacklister/create',
                        data: JSON.stringify(paramData),
                        contentType: 'application/json',
                        success: function() {
                            $("#addEmailErrorMsg").removeClass("hide").addClass("hide");
                            $("#addEmailSuccessMsg").removeClass("hide");
                        }
                    });
                }
            }
        });
    });

    $("#addEmailDialog").on('hide.bs.modal', function(){
        location.reload();
    });

    $("#inputEmail").on('keyup', function(){
        var val = $("#inputEmail").val();
        if(val!=null && val!="") $("#saveBtn").removeClass("disabled");
        else $("#saveBtn").removeClass('disabled').addClass('disabled');
    });

});

function responseHandler(data) {
    return data.emailBlacklistForms;
}