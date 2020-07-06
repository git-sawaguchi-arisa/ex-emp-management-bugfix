
$(function(){
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    $("#partOfName").keyup(function(){
        $.ajax({
            type: "POST",
            url: "/autoComplete/get-info",
            dataType: "json",
            data: {
                partOfName: $(this).val()
            },
            success: function(results){
                $("#dt1").empty()
                for(var result of results){
                    var op = document.createElement("option");
                    op.value = result;
                    document.getElementById("dt1").appendChild(op);
                }
            }
        });
    });

})
