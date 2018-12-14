$(function() {
    var accountInput = $("input[name='account']")
    var btn = $("#login");

    function bindEvent() {
        btn.on("click", function () {
            var account = accountInput.val()
            $.ajax({
                url:'/pay/login/'+account,
                dataType:'json',
                success:function(data){
                    if(data!=null){
                        //登录成功
                        if(data.status == 't'){
                            window.location = "/index?account="+account+"&name="+data.name+"&remain="+data.yue+"&enableChange="+data.enableChange+"&currentTypeId="+data.typeId
                        }else{
                            alert(data.msg)
                        }
                    }else{
                        alert("登录失败")
                    }
                }
            })
        })
    }

    function init() {
        bindEvent()
    }

    init()
})