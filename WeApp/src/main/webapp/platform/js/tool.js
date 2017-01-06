 /**  
     * ajax post提交  
     * @param url  
     * @param param  
     * @param datat 为html,json,text  
     * @param callback回调函数  
     * @return  
     */  
     function jsonAjax(url, param, datat, callback) {  
         $.ajax({  
             type: "post",  
             url: url,  
             data: param,  
             dataType: datat,  
             success: callback,  
             error: function () {  
                 jQuery.fn.mBox({  
                     message: '恢复失败'  
                 });  
             }  
         });  
     } 
     
     