/*
- Created by cyj
- 2018/6/8
*/

//分页
var pageTotal="",url="";
function pagination(){
    Page({
        num: pageTotal,
        startnum:1,
        elem:$('#page'),
        callback:function(currentPage){
            $.ajax({
                url:url + (currentPage-1) ,//后台页码从0开始
                dataType:'json',
                async:true ,
                type:"GET" ,
                success:function(result){
                    loadData(result);
                }
            });
        }
    });
}

//加载附件
function loadAttachment(){
    var divHtml = "";
    if(listAttachments!=null){
        listAttachments.forEach(function(item,index){
            divHtml += '<div class="listAttachment" onclick="downloadFile('+index+')">'+item.name+'.'+item.format+'</div>';
        });
    }
    $(".attachmentAppend").append(divHtml);
}

//附件下载
function downloadFile(index){
    var a = document.createElement('a');
    a.href = '/filePath/'+listAttachments[index].id+'?'+listAttachments[index].lastModified;
    document.body.appendChild(a);//此句用来兼容火狐浏览器
    a.click();
}
