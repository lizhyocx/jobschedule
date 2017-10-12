//添加事件监听兼容函数 
window.addHandler=function(target, eventType, handler){  
    if(target.addEventListener){//主流浏览器  
        addHandler = function(target, eventType, handler){  
            target.addEventListener(eventType, handler, false);  
        };  
    }else{//IE  
        addHandler = function(target, eventType, handler){  
            target.attachEvent("on"+eventType, handler);  
        };        
    }  
    //执行新的函数  
    addHandler(target, eventType, handler);  
};
//删除事件监听兼容函数  
window.removeHandler=function(target, eventType, handler){  
    if(target.removeEventListener){//主流浏览器  
        removeHandler = function(target, eventType, handler){  
            target.removeEventListener(eventType, handler, false);  
        }         
    }else{//IE  
        removeHandler = function(target, eventType, handler){  
            target.detachEvent("on"+eventType, handler);  
        }         
    }  
    //执行新的函数  
    removeHandler(target, eventType, handler);  
};

window.fullScreenCall=function() { 

  var el= document.documentElement; //若要全屏页面中div，var element= document.getElementById("divID");

  //切换全屏
  var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullscreen;
  if (typeof rfs != "undefined" && rfs) {
      rfs.call(el);
  }
}  
window.fullScreenCan=function() { 

  var el= document.documentElement; //若要全屏页面中div，var element= document.getElementById("divID");

  //切换全屏
  var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullscreen;
  if (typeof rfs != "undefined" && rfs) {
      return true;
  } else{
      return false;
  }
}
window.fullExitCall=function(){
  var cfs = document.exitFullscreen || document.webkitCancelFullScreen || document.msExitFullscreen || document.mozCancelFullScreen;
  if (typeof cfs != "undefined" && cfs) {
      cfs.call(document);
  }
}

window.myBrowser=function(){
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
    var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器
    var isSafari = userAgent.indexOf("Safari") > -1; //判断是否Safari浏览器
    if (isIE) {
        var IE5 = IE55 = IE6 = IE7 = IE8 = false;
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        IE55 = fIEVersion == 5.5;
        IE6 = fIEVersion == 6.0;
        IE7 = fIEVersion == 7.0;
        IE8 = fIEVersion == 8.0;
        if (IE55) {
            return "IE55";
        }
        if (IE6) {
            return "IE6";
        }
        if (IE7) {
            return "IE7";
        }
        if (IE8) {
            return "IE8";
        }
    }//isIE end
    if (isFF) {
        return "FF";
    }
    if (isOpera) {
        return "Opera";
    }
}//myBrowser() end
$(function() {
    $(document).on('blur','.ant-pagination-options-size-changer',function(){
        $('.ant-select-dropdown.ant-select-dropdown-hidden').attr('style','');
    });
    $(document).on('click','.ant-select-dropdown-menu .ant-select-dropdown-menu-item',function(){
        $(this).parents('.ant-select-dropdown').attr('style','');
    });
    $(document).on('click','.ant-pagination',function(){
        $(this).find('.ant-pagination-options-size-changer').trigger('blur');
    });
});