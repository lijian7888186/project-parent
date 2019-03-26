window.tool = {
    getXHR:function(){
        var xhr;
        if(window.XMLHttpRequest){
            xhr = new XMLHttpRequest();
        }else{
            xhr = new ActiveXObject('Microsoft.XMLHTTP');
        }
        if(!xhr){
            throw new Error("用户浏览器不支持异步请求");
        }
        return xhr;
    },
    ajax:function(opt){
        var url = opt.url;
        if(!url){
            throw new Error("url不能为空");
        }
        // url = "http://test.guara.bjdai.com.cn/" + url;
        var xhr = this.getXHR();
        var data = opt.data || data;
        var success = opt.success || (function(){});
        var error = opt.error || (function(data){console.log(opt.url + '请求失败');});
        var method = opt.method || "post";
        var async;
        if(opt.async == undefined){
            async = true;
        }else{
            async = opt.async;
        }
        var contentType = opt.contentType || "json";

        var params = [];
        for(var key in data){
            params.push(key + "=" + (data[key] ? encodeURIComponent(data[key]) : ""));
        }
        var postData = params.join("&");
        if(method.toLowerCase() == "get"){
            xhr.open(method,url + "?" + postData,async);
        }
        if(method.toLowerCase() == "post"){
            xhr.open(method,url,async);
            xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        }
        xhr.onreadystatechange = function(){
            if(xhr.readyState == 4){
                if(xhr.status >= 400){
                    error(xhr.responseText);
                    return;
                }
                var result = xhr.responseText;
                if(contentType == "json"){
                    try{
                        result = JSON.parse(result);
                        //在这里验证几种特殊响应状态
                        //登录超时
                        if(result.code == "250"){
                            //抽空补全
                            //alert("登录超时");
                            window.location.href = "login.html"
                            return;
                        }
                    }catch(e){

                    }
                }
                success(result);
            }
        }
        xhr.send(postData);
    },
    alert:function(message){
        // 蒙层
        var layer = document.createElement("div");
        layer.style.position = "fixed";
        layer.style.width = "100%";
        layer.style.height = "100%";
        layer.style.left = "0";
        layer.style.top = "0";
        layer.style.zIndex = "100";
        // layer.style.opacity = "0.3";
        layer.style.display = "block";
        layer.style.backgroundColor = "rgba(0,0,0,0.3)";
        document.body.appendChild(layer);
        // 弹框
        var alertFrame = document.createElement("div");
        alertFrame.style.opacity = "1.0";
        alertFrame.style.width = "500px";
        alertFrame.style.minHeight = "200px";
        alertFrame.style.height = "auto";
        alertFrame.style.position = "relative";
        alertFrame.style.marginLeft = "50%";
        // alertFrame.style.display = "50%";
        alertFrame.style.marginTop = "10%";
        alertFrame.style.transform = "translate(-50%,-50%)";
        alertFrame.style.backgroundColor = "white";
        alertFrame.style.borderRadius = "4px";
        alertFrame.style.boxShadow = "0 2px 4px 0 rgba(0, 0, 0, 0.7)";
        layer.appendChild(alertFrame);

        var content = document.createElement("div");
        content.style.maxHeight = "120px";
        content.style.overflowY = "auto";
        content.style.fontFamily = "PingFangSC-Regular";
        content.style.fontSize = "14px";
        content.style.color = "#20232C";
        content.style.paddingTop = "30px";
        content.style.textAlign = "center";
        content.innerHTML = message || "";
        alertFrame.appendChild(content);

        var buttonLine = document.createElement("div");
        buttonLine.style.width = "100%";
        buttonLine.style.height = "60px";
        buttonLine.style.backgroundColor = "none";
        buttonLine.style.position = "absolute";
        buttonLine.style.bottom = "0";
        buttonLine.style.borderTop = "solid 1px #D8D8D8";
        alertFrame.appendChild(buttonLine);

        var button = document.createElement("span");
        button.style.padding = "7px 50px";
        button.style.backgroundColor = "#35C5FE";
        button.style.color = "#FFFFFF";
        button.style.position = "absolute";
        button.style.left = "50%";
        button.style.top = "30%";
        button.style.transform = "translate(-50%,-50%)";
        button.style.fontSize = "12px";
        button.style.borderRadius = "3px";
        button.style.cursor = "pointer";
        button.style.lineHeight = "15px";
        button.style.marginTop = "10px";
        button.innerHTML = "确定";
        buttonLine.appendChild(button);
        var result = {
            then:function(fun){
                this.callback = fun;
            }
        };
        button.addEventListener("click",function(event){
            document.body.removeChild(layer);
            if (result.callback) {
                result.callback();
            }
        });
        return result;
    },
    confirm:function(message){
        // 蒙层
        var layer = document.createElement("div");
        layer.style.position = "fixed";
        layer.style.width = "100%";
        layer.style.height = "100%";
        layer.style.left = "0";
        layer.style.top = "0";
        // layer.style.opacity = "0.3";
        layer.style.display = "block";
        layer.style.backgroundColor = "rgba(0,0,0,0.3)";
        document.body.appendChild(layer);
        // 弹框
        var alertFrame = document.createElement("div");
        alertFrame.style.opacity = "1.0";
        alertFrame.style.width = "500px";
        alertFrame.style.minHeight = "200px";
        alertFrame.style.height = "auto";
        alertFrame.style.position = "relative";
        alertFrame.style.marginLeft = "50%";
        // alertFrame.style.display = "50%";
        alertFrame.style.marginTop = "10%";
        alertFrame.style.transform = "translate(-50%,-50%)";
        alertFrame.style.backgroundColor = "white";
        alertFrame.style.borderRadius = "4px";
        alertFrame.style.boxShadow = "0 2px 4px 0 rgba(0, 0, 0, 0.7)";
        layer.appendChild(alertFrame);

        var content = document.createElement("div");
        content.style.maxHeight = "120px";
        content.style.overflowY = "auto";
        content.style.fontFamily = "PingFangSC-Regular";
        content.style.fontSize = "14px";
        content.style.color = "#20232C";
        content.style.paddingTop = "30px";
        content.style.textAlign = "center";
        content.innerHTML = message || "";
        alertFrame.appendChild(content);

        var buttonLine = document.createElement("div");
        buttonLine.style.width = "100%";
        buttonLine.style.height = "60px";
        buttonLine.style.backgroundColor = "none";
        buttonLine.style.position = "absolute";
        buttonLine.style.bottom = "0";
        buttonLine.style.borderTop = "solid 1px #D8D8D8";
        alertFrame.appendChild(buttonLine);

        var button = document.createElement("span");
        button.style.padding = "7px 50px";
        button.style.backgroundColor = "#35C5FE";
        button.style.color = "#FFFFFF";
        button.style.position = "absolute";
        button.style.left = "30%";
        button.style.top = "30%";
        button.style.transform = "translate(-50%,-50%)";
        button.style.fontSize = "12px";
        button.style.borderRadius = "3px";
        button.style.cursor = "pointer";
        button.style.lineHeight = "15px";
        button.style.marginTop = "10px";
        button.innerHTML = "确定";
        buttonLine.appendChild(button);
        var result = {
            then:function(fun){
                this.callback = fun;
            }
        };
        button.addEventListener("click",function(event){
            document.body.removeChild(layer);
            if(result.callback){
                result.callback();
            }
        });
        var cancelButton = document.createElement("span");
        cancelButton.style.padding = "7px 50px";
        cancelButton.style.backgroundColor = "#FB6767";
        cancelButton.style.color = "#FFFFFF";
        cancelButton.style.position = "absolute";
        cancelButton.style.left = "70%";
        cancelButton.style.top = "30%";
        cancelButton.style.transform = "translate(-50%,-50%)";
        cancelButton.style.fontSize = "12px";
        cancelButton.style.borderRadius = "3px";
        cancelButton.style.cursor = "pointer";
        cancelButton.style.lineHeight = "15px";
        cancelButton.style.marginTop = "10px";
        cancelButton.innerHTML = "取消";
        buttonLine.appendChild(cancelButton);
        cancelButton.addEventListener("click",function(event){
            document.body.removeChild(layer);
        });
        return result;
    },
    postNewWindow:function(url,param){//post方式打开新窗口并请求数据
        var form = document.createElement("form");
        form.target = "_blank";
        form.method = "post";
        form.action = url;
        for(var key in param){
            var value = param[key];
            if(!value){
                continue;
            }
            var input = document.createElement("input");
            input.type = "hidden";
            input.name=key;
            input.value=value;
            form.appendChild(input);
        }
        document.body.appendChild(form);
        form.submit();
        document.body.removeChild(form);
    },
    postCurWindow:function(url,param){//post方式在当前页面请求数据
        var form = document.createElement("form");
        form.method = "post";
        form.action = url;
        for(var key in param){
            var value = param[key];
            if(!value){
                continue;
            }
            var input = document.createElement("input");
            input.type = "hidden";
            input.name=key;
            input.value=value;
            form.appendChild(input);
        }
        document.body.appendChild(form);
        form.submit();
        document.body.removeChild(form);
    },
    getCurWindow:function(url,param){//post方式在当前页面请求数据
        var form = document.createElement("form");
        form.method = "get";
        form.action = url;
        for(var key in param){
            var value = param[key];
            if(!value){
                continue;
            }
            var input = document.createElement("input");
            input.type = "hidden";
            input.name=key;
            input.value=value;
            form.appendChild(input);
        }
        document.body.appendChild(form);
        form.submit();
        document.body.removeChild(form);
    }
}