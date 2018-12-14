$(function(){
    var name = $("#username-text")
    var remain = $("#remain")
    var payBtn = $(".pay-btn")
    var dialog = $("#pay-dialog")
    var okBtn = dialog.find("#ok")
    var cancelBtn = dialog.find("#cancel")
    var moneyInput = dialog.find("input[name='money']")
    var pwdInput = dialog.find("input[name='pwd']")
    var payRecord = $(".pay-record")
    var account = null;
    var recordDialog = $("#record-dialog")
    var recordEmpty = $(".record-empty")
    //变更套餐
    var changeDialog = $("#change-dialog")
    var changeBtn = $(".change-btn")
    var typeSelect = $("select[name='type']");
    var expensesSelect = $("select[name='expenses']");
    var changeMoney = $("input[name='changemoney']");
    //当前套餐id
    var currentTypeId = null;

    //判断是否可以变更套餐
    var enableChange = true;

    //判断是否首次开通，如果是则可以直接选择套餐生效，否则需要预约套餐变更，下月生效
    var isFirstChange = false;

    var spinner = $(".spinner-wrap");

    var pkMonth,pkgData;
    if(schoolCode=='sf'){
        //包月套餐
        pkgMonth = [{
            name:'单WIFI包月40元10M',
            code:'27'
        },{
            name:'单宽带包月40元10M',
            code:'25'
        },{
            name:'WIFI+宽带包月70元10M ',
            code:'28'
        },{
            name:'单WIFI包月50元20M',
            code:'32'
        },{
            name:'单宽带包月50元20M',
            code:'31'
        },{
            name:'WIFI+宽带包月90元20M ',
            code:'33'
        }]


        //流量包套餐
        pkgData = [{
            name:'流量包',
            code:'26'
        }]
    }else if(schoolCode=='hj'){
        pkgMonth = [{
            name:'单WIFI包月40元10M',
            code:'6'
        },{
            name:'单宽带包月40元10M',
            code:'5'
        },{
            name:'WIFI+宽带包月70元10M ',
            code:'7'
        },{
            name:'单宽带包月50元20M',
            code:'10'
        },{
            name:'单WIFI包月50元20M',
            code:'14'
        },{
            name:'WIFI+宽带包月90元20M ',
            code:'11'
        }]

        //流量包套餐
        pkgData = [{
            name:'流量包',
            code:'4'
        }]
    }



    function init(){
        fillback()
        bindEvent()
        renderExpenses("0")
    }

    function bindEvent(){
        payBtn.on("click",function(){
            dialog.show()
        })

        okBtn.on("click",function(){
            var money = moneyInput.val()
            var pwd = pwdInput.val()
            var cardAccount = $("input[name='cardAccount']").val()
            if("" == money || !/^\d{1,4}$/.test(money)){
                alert("金额格式错误，最多缴纳9999元,必须整数")
                return;
            }
            if(""==pwd){
                alert("请输入密码");
                return;
            }
            if(""==cardAccount){
                alert("请输入一卡通账号");
                return;
            }
            debugger
            spinner.css("display","table");
            $.ajax({
                url:'/pay/dopay',
                data:{
                  name:account,
                  pwd:pwd,
                  amount:money,
                  cardAccount:cardAccount
                },
                dataType:'json',
                success:function(data){
                    spinner.css("display","none");
                    if(data.status=='t'){
                        remain.text(data.yue)
                    }else{
                        alert(data.msg)
                    }
                    dialog.hide()
                }
            })
        })

        cancelBtn.on("click",function(){
            pwdInput.val("")
            moneyInput.val("")
            dialog.hide()
        })

        payRecord.on("click",function(){
            spinner.css("display","table");
            $.ajax({
                url:'/pay/listLog/'+account,
                dataType:'json',
                success:function(data){
                    spinner.css("display","none");
                    recordDialog.find(".record-item").remove()
                    if(data.length>0){
                        recordEmpty.hide()
                        renderRecords(data)
                    }else{
                        recordEmpty.show()
                    }
                    recordDialog.show()
                }
            })
        })

        recordDialog.find("#close").on("click",function(){
            recordDialog.hide()
        })

        //变更套餐按钮
        changeBtn.on("click",function(e){
            if(enableChange){
                changeDialog.show()
            }else{
                alert("您本月已变更过套餐，无法继续变更套餐")
            }
        })

        typeSelect.on("change",function(){
            renderExpenses(this.value)
        })

        $("#change-cancel").on("click",function(){
            changeDialog.hide()
        })


        /**
         * 变更套餐
         */
        $("#change-ok").on("click",function(){
            var type = typeSelect.val()
            var expenses = expensesSelect.val()
            // var money = changeMoney.val()
            // var pwd = $("input[name='changepwd']").val()
            // var cardAccount = $("input[name='changeCardAccount']").val()
            // if("" == money || !/^\d{1,4}$/.test(money)){
            //     alert("金额格式错误，最多缴纳9999元,必须整数")
            //     return;
            // }
            // if(""==pwd){
            //     alert("请输入支付密码");
            //     return;
            // }
            if(expenses==currentTypeId){
                alert("不能选择与当前套餐一致的套餐");
                return;
            }
            // if(cardAccount==""){
            //     alert("请输入一卡通账号");
            //     return;
            // }
            spinner.css("display","table");
            $.ajax({
                url:'/pay/change',
                data:{
                    account:account,
                    typeId:expenses
                    // ,
                    // money:money,
                    // pwd:pwd,
                    // cardAccount:cardAccount
                },
                dataType:'json',
                type:'post',
                success:function(data){
                    spinner.css("display","none");
                    if(data.status=='t'){
                        enableChange = false;
                        changeDialog.hide();
                        remain.text(data.yue);
                    }else{
                        alert(data.msg);
                    }
                }
            })
        })

    }

    function renderRecords(data){
        var item = "";
        for(var i=0;i<data.length;i++){
            item+= '<div class="record-item">\n' +
                '                        <span class="record-money">'+data[i].amount+'</span>\n' +
                '                        <span class="record-time">'+data[i].create_time+'</span>\n' +
                '                    </div>'
        }
        recordDialog.find(".dialog-pane").append(item)
    }

    function fillback(){
        var search = decodeURIComponent(window.location.search);
        if(search!=null){
            var params = search.substr(1).split("&");
            name.text(params[1].split("=")[1]);
            remain.text(params[2].split("=")[1]);
            account = params[0].split("=")[1];
            enableChange = params[3].split("=")[1]=='t';
            currentTypeId = params[4].split("=")[1]
        }else{
            alert("您还未登录，请登录后访问");
            window.location.href = "/login"
        }
    }

    function renderExpenses(type){
        var arr = pkgMonth;
        if(type=='1'){
            arr = pkgData;
        }
        var opts = "";
        for(var i=0;i<arr.length;i++){
            opts+="<option value='"+arr[i].code+"'"+(i===0?'selected':'')+">"+arr[i].name+"</option>"
        }
        expensesSelect.empty().append(opts)
    }


    init()
})