$(function(){
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    $('#mailAddress').keyup(function() {
        $.ajax({
            type: "POST",
            url: "/mailAddressCheck/get-info",
            dataType: "text",
            data: {
                mailAddress: $('#mailAddress').val()
            },
            success: function (results) {
                console.log(results)
                var inputMailAddress = $('#mailAddress').val();
                if (results === inputMailAddress) {
                    $("#mailAddressExist").text("このメールアドレスは使用されています");
                    $("#submit").prop("disabled", true);
                } else {
                    $("#mailAddressExist").empty();
                    $("#submit").prop("disabled", false);
                }
            }
        });
    });
})