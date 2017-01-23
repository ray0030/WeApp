 /**  
     * ajax post提交  
     * @param url  
     * @param param  
     * @param datat 为html,json,text  
     * @param callback回调函数  
     * @return  
     */  
     function jsonAjax(url, param, data, callback) {  
         $.ajax({  
             type: "post",  
             url: url,  
             data: param,  
             contentType:'application/x-www-form-urlencoded; charset=UTF-8',
             dataType: data,  
             success: callback,  
             error: function (XMLHttpRequest, textStatus, errorThrown) {  
                 jQuery.fn.mBox({  
                     message: '恢复失败'  
                 });  
             }  
         });  
     } 
     
     