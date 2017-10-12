import {Modal, message} from 'antd';
/**
 * Created by hipo on 2016/6/2.
 * 提供公共的方法
 */
window.commonUtil = {

    // 错误提示时间（秒）
    errorTipTime: 3,

    chnageIp() {
       //const  preIp = "http://10.200.21.56:10460";
       //const preIp = "http://10.100.23.9:10400";
       //const preIp = "http://mall-pc.ts.yunzongnet.com";
       //const preIp = "http://mall-pc.yf.yunzongnet.com";
       //const preIp = "http://mall-pc.yunzongnet.com";
       //const preIp = "http://10.200.39.191:8080";  //zhanghuikong ip
       //const preIp = "http://d1303.shoppingmall.yunzong:10845";
       //const preIp = "http://zhmall.yf.yunzongnet.com";
       //const preIp = "http://d1068.shoppingmall.yunzong:10845";
       //const preIp = "http://10.200.37.164:8080";
       //const preIp = "https://m.zonghengke.com";
       const preIp = "";
       return preIp;
    },

    /**
     * ajax请求
     * @param url
     * @param type
     * @param params
     * @param sucFunc
     * @param errorFunc
     * @param async
     * @param dataType
     * @param contentType
     */
    ajaxRequest: function(url, type, params, sucFunc, errorFunc, async, dataType, contentType){
        let hide = message.loading('正在执行中...', 0);
        $.ajax({
            url : url,
            type : type,
            data : params,
            async: !async,
            dataType : dataType || 'json',
            contentType : contentType || 'application/x-www-form-urlencoded',
            success: function (data) {
                hide();
                if (data && Object.is(data.code, 302) || Object.is(data.code, 301)) {
                    redirect(data);
                } else {
                    sucFunc(data);
                }
            },
            error: function () {
                hide();
                errorFunc();
            }
        });
    },

    /**
     * 公共ajax方法(满足基本需求)
     * @param  {[type]} url: 访问后台接口地址
     * @param  {[type]} type: 访问方式，默认GET请求
     * @param  {[type]} params: 访问后台接口参数
     * @param  {[type]} callBack: 回调函数
     * @return {[type]}      后台响应数据
     */
    ajaxCommon(url, type, params, callBack) {
        $.ajax({
            url: url,
            type: type || "GET",
            dataType: "json",
            data: params,
            async: false,
            success: function (result) {
                if (result.success) {
                   callBack.call(this, result);
                } else {
                    if(!redirect(result)){
                        commonUtility.messageError(result.msg || "服务异常", commonUtility.errorTipTime);
                    }
                }
            }.bind(this),
            error: function () {
                commonUtility.messageError("服务器繁忙，请稍后再试", commonUtility.errorTipTime);
            }
        });
    },

    /**
     * 公共ajax方法(满足基本需求)
     * 添加异步、同步参数
     * @param  {[type]} url: 访问后台接口地址
     * @param  {[type]} type: 访问方式，默认GET请求
     * @param  {[type]} params: 访问后台接口参数
     * @param  {[type]} callBack: 回调函数
     * @return {[type]}      后台响应数据
     */
    ajaxCommonNew(url, type, params, async, callBack) {
        $.ajax({
            url: url,
            type: type || "GET",
            dataType: "json",
            data: params,
            async: async,
            success: function (result) {
                if (result.success) {
                   callBack.call(this, result);
                } else {
                    if(!redirect(result)){
                        commonUtility.messageError(result.msg || "服务异常", commonUtility.errorTipTime);
                    }
                }
            }.bind(this),
            error: function () {
                commonUtility.messageError("服务器繁忙，请稍后再试", commonUtility.errorTipTime);
            }
        });
    },

    /**
     * 100000 -> 10万
     * @param number 要转换的目标数据
     * @param hasDecimals 是否保留两位小数
     * @return string
     */
    formatNumber(number, hasDecimals) {
        if (!number) {
            return "0";
        }
        let unit = "";
        const yi = 100000000;
        const wan = 10000;
        let result = parseFloat(number);
        if (isNaN(result)) {
            return "0";
        }
        if (number > yi) {
            unit = "亿";
            result = number / yi;
        } else if (number > 10 * wan) {
            unit = "万";
            result = number / wan;
        }

        if (hasDecimals) {
            result = result.toFixed(2);
            while (result.charAt(result.length - 1) == "0") {
                result = result.substring(0, result.length - 1);
            }
            if (result.charAt(result.length - 1) == ".") {
                result = result.substring(0, result.length - 1);
            }
        } else {
            result = parseInt(result);
        }
        return this.thousandsSeparator(result) + unit;
        //return result + unit;
    },

    /**
     * 千位分隔符
     * @return {[type]} [description]
     */
    thousandsSeparator(num) {
        let reg = "";
        let numStr = num && num.toString();
        if(numStr.indexOf(".") > 0) { //金额
            reg = /(\d)(?=(\d{3})+\.)/g;
        }else { //数字
            reg = /(\d)(?=(?:\d{3})+$)/g;
        }
        return numStr
                 .replace(reg, function($0, $1) {
                    return $1 + ",";
                 });
    },

    /**
     * 获取缩略图路径
     * @param path 原图路径
     * @param suffix 后缀，默认_200x200
     * @returns {string}
     */
    getSamllPicturePath(path, suffix = "_200_200") {
        if (!path) {
            return "";
        }
        let paths = path.split(".");
        paths[paths.length - 2] = paths[paths.length - 2] + suffix; // 取文件名
        return paths.join(".");
    },

    /**
     * 检测是否包含特殊字符
     * @param str
     * @returns {boolean}
     */
    checkSpecialStr(str){
        var myReg = /^[^@\/\'\\\"#_$%&\|\^\*\s\+]+$/;
        if(myReg.test(str)) return true;
        return false;
    },

    /**
     * 检测券信息是否包含特殊字符
     * @param str
     * @returns {boolean}
     */
    checkTicketSpecialStr(str){
        var myReg = /^[^@\/\'\\\"#_$%&\|\^\*\s\+\.。、！]+$/;
        if(myReg.test(str)) return true;
        return false;
    },

    /**
     * 转换成标准格式的字符串
     * @param time 时间戳或者Date
     * @returns {string}
     */
    formatTime: function (time) {
        var d = time ? new Date(time) : new Date();
        var month = d.getMonth() + 1;
        if (month < 10) {
            month = "0" + month;
        }
        var date = d.getDate();
        if (date < 10) {
            date = "0" + date;
        }
        var hour = d.getHours() > 9 ? d.getHours() : "0" + d.getHours();
        var minute = d.getMinutes() > 9 ? d.getMinutes() : "0" + d.getMinutes();
        var second = d.getSeconds() > 9 ? d.getSeconds() : "0" + d.getSeconds();
        return d.getFullYear() + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
    },
    /**
     * 转换成标准格式的字符串
     * @param time 时间戳或者Date
     * @returns {string}
     */
    formatYYYY_MM_DD_HH_mm: function (time) {
        var d = time ? new Date(time) : new Date();
        var month = d.getMonth() + 1;
        if (month < 10) {
            month = "0" + month;
        }
        var date = d.getDate();
        if (date < 10) {
            date = "0" + date;
        }
        var hour = d.getHours() > 9 ? d.getHours() : "0" + d.getHours();
        var minute = d.getMinutes() > 9 ? d.getMinutes() : "0" + d.getMinutes();
        var second = d.getSeconds() > 9 ? d.getSeconds() : "0" + d.getSeconds();
        return d.getFullYear() + "-" + month + "-" + date + " " + hour + ":" + minute;
    },
    /**
     * 转换成标准格式的字符串
     * @param time 时间戳或者Date
     * @returns {string}
     */
    formatHourMinute: function (time) {
        var d = time ? new Date(time) : new Date();
        var month = d.getMonth() + 1;
        if (month < 10) {
            month = "0" + month;
        }
        var date = d.getDate();
        if (date < 10) {
            date = "0" + date;
        }
        var hour = d.getHours() > 9 ? d.getHours() : "0" + d.getHours();
        var minute = d.getMinutes() > 9 ? d.getMinutes() : "0" + d.getMinutes();
        return hour + ":" + minute;
    },

    /**
     * 转换成标准格式的字符串
     * @param time 时间戳或者Date
     * @returns {string}
     */
    formatDate: function (time) {
        var d = time ? new Date(time) : new Date();
        var month = d.getMonth() + 1;
        if (month < 10) {
            month = "0" + month;
        }
        var date = d.getDate();
        if (date < 10) {
            date = "0" + date;
        }
        return d.getFullYear() + "-" + month + "-" + date;
    },

    /**
     * 根据传入值得到活动状态名称-即将废弃
     */
    getActivityStatusName(value) {
        if(value){
            let auditStatus = "";
            if(value.indexOf("-") > -1){
                auditStatus = value.split("-")[1];
                value = value.split("-")[0];
            }
            if(Object.is(value,"STARTING")){
                return (Object.is(auditStatus, "AUDITING")) ? "未开始-商圈审核" : "未开始";
            }else if(Object.is(value,"STARTED")){
                return "进行中";
            }else if(Object.is(value,"CLOSING")){
                return "终止中";
            }else if(Object.is(value,"CLOSED")){
                return "已完成";
            }else if(Object.is(value,"DISABLED")){
                return (Object.is(auditStatus, "REJECT")) ? "创建失败-商圈驳回" : "创建失败";
            }else if(Object.is(value,"MODIFYING")){
                return "修改中";
            }else{
                return "";
            }
        }
    },

    /**
     * 根据传入值得到活动状态名称
     * value-活动主状态
     * auditStatus-商圈审核或口碑审核状态
     * planStatus-招商审核状态
     * type-活动类型
     * 展雄:如果是ED结尾的状态 你们用不着关心审核状态(STARTED,CLOSED,DISABLED)
     */
    getActivityStatusName_Test(value,auditStatus,planStatus,type) {
    	if(value && auditStatus && planStatus && type){
                //创建失败
                if(Object.is(value,"DISABLED")){
                    if(Object.is(auditStatus,"empty") && Object.is(planStatus,"ENDED")){
                        return "创建失败";
                    }else if(Object.is(auditStatus,"REJECT")){
                        return "创建失败-商圈审核驳回";
                    }else if(Object.is(auditStatus,"CANCEL")){
                        return "创建失败-商圈审核撤销";
                    }else if(Object.is(auditStatus,"FAIL")){
                        return "创建失败-商圈审核失败";
                    }else{
                        return "创建失败";
                    }
                //进行中
                }else if(Object.is(value,"STARTED")){
                        return "进行中";
                //已完成
                }else if(Object.is(value,"CLOSED")){
                        return "已完成";
                //未开始
                }else if(Object.is(value,"STARTING")){
                    //this.getSubstatesName("未开始",auditStatus,planStatus);
                    if(Object.is(auditStatus,"AUDITING")){
                        //return "未开始(待商圈确认)";
                        return "未开始";
                    }else if(Object.is(auditStatus,"KOUBEI_AUDITING")){
                        //return "未开始(待口碑确认)";
                        return "未开始";
                    }else if(Object.is(auditStatus,"KOUBEI_FAIL")){
                        //return "未开始(口审核失败)";
                        return "未开始";
                    }else if(Object.is(auditStatus,"empty") && Object.is(planStatus,"CREATED")){
                        //return "未开始(待门店确认)";
                        return "未开始";
                    }else if((Object.is(auditStatus,"KOUBEI_PASS") || Object.is(auditStatus,"empty"))
                            && Object.is(planStatus,"GOING")){
                        //return "未开始(待门店确认)";
                        return "未开始";
                    }else if((Object.is(auditStatus,"KOUBEI_PASS") || Object.is(auditStatus,"empty"))
                            && Object.is(planStatus,"CREATED")){
                        //return "未开始(待门店确认)";
                        return "未开始";
                    }else if((Object.is(auditStatus,"KOUBEI_PASS") || Object.is(auditStatus,"empty"))
                            && Object.is(planStatus,"ENDED")){
                        return "未开始";
                    }else{
                        //子状态不符合规则以主状态为主
                        return "未开始";
                    }
                //终止中
                }else if(Object.is(value,"CLOSING")){
                        return "终止中";
                //修改中
                }else if(Object.is(value,"MODIFYING")){
                        return "修改中";
                }else{
                        return "";
                }
    	}else{
            /**后台接口不稳定，容错处理如果不给审核子状态，主状态不为空则按主状态展示*/
            if(!auditStatus && value){
                if(Object.is(value,"STARTING")){
                    return "未开始";
                }else if(Object.is(value,"STARTED")){
                    return "进行中";
                }else if(Object.is(value,"CLOSING")){
                    return "终止中";
                }else if(Object.is(value,"CLOSED")){
                    return "已完成";
                }else if(Object.is(value,"DISABLED")){
                    return "创建失败";
                }else if(Object.is(value,"MODIFYING")){
                    return "修改中";
                }else{
                    return "";
                }
            }else{
                return "";
            }
        }
    },

    //获取子状态的名称
    getSubstatesName(value,auditStatus,planStatus){
        if(Object.is(auditStatus,"AUDITING")){
            return "未开始(待商圈确认)";
        }else if(Object.is(auditStatus,"KOUBEI_AUDITING")){
            return "未开始";
        }else if(Object.is(auditStatus,"KOUBEI_FAIL")){
            return "未开始";
        }else if(Object.is(auditStatus,"empty") && Object.is(planStatus,"CREATED")){
             return "未开始(待门店确认)";
        }else if((Object.is(auditStatus,"KOUBEI_PASS") || Object.is(auditStatus,"empty"))
                && Object.is(planStatus,"GOING")){
            return "未开始(待门店确认)";
        }else if((Object.is(auditStatus,"KOUBEI_PASS") || Object.is(auditStatus,"empty"))
                && Object.is(planStatus,"CREATED")){
            return "未开始(待门店确认)";
        }else if((Object.is(auditStatus,"KOUBEI_PASS") || Object.is(auditStatus,"empty"))
                && Object.is(planStatus,"ENDED")){
            return value;
        }else{
             //子状态不符合规则以主状态为主
            return value;
        }
    },

     /**
     * 根据传入活动状态名称得到活动状态-即将废弃
     */
    getActivityStatus(value) {

         if(Object.is(value,"未开始")){
            return "STARTING";
        }else if(Object.is(value,"进行中")){
            return "STARTED";
        }else if(Object.is(value,"终止中")){
            return "CLOSING";
        }else if(Object.is(value,"已完成")){
            return "CLOSED";
        }else if(Object.is(value,"创建失败")){
            return "DISABLED";
        }else if(Object.is(value,"修改中")){
            return "MODIFYING";
        }else{
            return "";
        }

    },

    /**
     * 根据传入活动状态名称得到活动状态-new
     */
    getActivityStatus_Test(value) {

       if(Object.is(value,"(未开始)创建中")){
            //return "CREATE_ING";
            return "STARTING";
        }else if(Object.is(value,"未开始")){ //未开始(待商圈确认)
            //return "AUDIT_STATUS";
            return "STARTING";
        }else if(Object.is(value,"未开始")){ //未开始(待口碑确认)
            return "STARTING";
            //return "KOUBEI_STATUS";
        }else if(Object.is(value,"未开始")){ //未开始(待门店确认)
            return "STARTING";
            //return "PLAN_STATUS";
        }else if(Object.is(value,"未开始")){
            return "STARTING";
        }else if(Object.is(value,"进行中")){
            return "STARTED";
        }else if(Object.is(value,"终止中")){
            return "CLOSING";
        }else if(Object.is(value,"已完成")){
            return "CLOSED";
        }else if(Object.is(value,"创建失败")){
            return "DISABLED";
        }else if(Object.is(value,"修改中")){
            return "MODIFYING";
        }else{
            return "";
        }
    },

    /*
    *根据传入的券类型，获取名称
     */
    getVoucherTypeName(value) {
        if (Object.is(value,'RATE')) {
            return '折扣券';
        }else if(Object.is(value,'MONEY')) {
            return '代金券';
        }else if(Object.is(value,'DIRECT_SEND')) {
            return '扫码送券';
        }else if(Object.is(value,'CONSUME_SEND')) {
            return '消费送券';
        }
    },
    /*
    *根据传入的券名称，获取券类型
     */
    getVoucherType(value) {
        if (Object.is(value,'折扣券')) {
            return 'RATE';
        }else if(Object.is(value,'代金券')) {
            return 'MONEY';
        }
    },

    /**根据活动类型获取活动名称*/
    getActivityNameByType(type) {
        let ActivityName = "消费送券";
        if (type == 2) {
            ActivityName = "扫码送券";
        } else if (type == 3) {
            ActivityName = "口令优惠";
        } else if (type == 4) {
            ActivityName = "节日营销";
        } else if (type == 5) {
            ActivityName = "挽回流失会员";
        } else if (type == 6) {
            ActivityName = "生日优惠";
        } else if (type == 7) {
            ActivityName = "位置营销";
        } else if (type == 201) {
            ActivityName = "集卡营销";
        } else if (type == 101) {
            ActivityName = "高频高额";
        } else if (type == 102) {
            ActivityName = "高频低额";
        } else if (type == 103) {
            ActivityName = "低频高额";
        } else if (type == 104) {
            ActivityName = "低频低额";
        } else if (type == 105) {
            ActivityName = "亲子活动";
        }  else if (type == 106) {
            ActivityName = "自定义活动";
        }



        return ActivityName;
    },

    /**
     * 按指定字段排序数组
     * @param dataArr 目标数组（数组内为对象）
     * @param sortField 排序字段
     * @param isAsc 是否升序
     */
    sort(dataArr, sortField, isAsc) {
        if (!dataArr || !dataArr.length) {
            return;
        }
        let sortFn = function(item1, item2) {
            let v1 = item1[sortField];
            let v2 = item2[sortField];
            if (typeof v1 === "undefined" || v1 === "") {
                return isAsc ? -1 : 1;
            } else if (typeof v2 === "undefined" || v2 === "") {
                return isAsc ? 1 : -1;
            } else if (v1 < v2) {
                return isAsc ? -1 : 1;
            } else if (v1 > v2) {
                return isAsc ? 1 : -1;
            } else {
                return 0;
            }
        };
        dataArr.sort(sortFn);
    },
    /**
     * Modal确认弹框
     * @param  {[type]} obj [description]
     */
    showConfirm(obj) {
        Modal.confirm({
            title: obj&&obj.title?obj.title:'',
            content: obj&&obj.content?obj.content:'',
            okText:obj&&obj.okText?obj.okText:'确定',
            cancelText:obj&&obj.cancelText?obj.cancelText:'取消',
            onOk() {
                obj&&obj.onOk&&obj.onOk();
            },
            onCancel() {
                obj&&obj.onCancel&&obj.onCancel();
            },
        });
        $(document).trigger('dialogShow');
    },
    /**
     * Modal警告弹框
     * @param  {[type]} obj [description]
     */
    warning(obj) {
         Modal.warning({
            title: obj&&obj.title?obj.title:'',
            content: obj&&obj.content?obj.content:'',
            okText:obj&&obj.okText?obj.okText:'我知道了',
            onOk:function(){
                obj&&obj.onOK&&obj.onOK();
            }
        });
        $(document).trigger('dialogShow');
    },
    /**
     * Modal信息提示弹框
     * @param  {[type]} obj [description]
     */
    info(obj) {
         Modal.info({
            title: obj&&obj.title?obj.title:'',
            content: obj&&obj.content?obj.content:'',
            okText:obj&&obj.okText?obj.okText:'我知道了',
            onOk:function(){
                obj&&obj.onOK&&obj.onOK();
            }
        });
        $(document).trigger('dialogShow');
    },
    /**
     * Modalc成功提示弹框
     * @param  {[type]} obj [description]
     */
    success(obj) {
         Modal.success({
            title: obj&&obj.title?obj.title:'',
            content: obj&&obj.content?obj.content:'',
            okText:obj&&obj.okText?obj.okText:'我知道了',
            onOk:function(){
                obj&&obj.onOK&&obj.onOK();
            }
        });
        $(document).trigger('dialogShow');
    },
    /**
     * Modal错误提示弹框
     * @param  {[type]} obj [description]
     */
    error(obj) {
         Modal.error({
            title: obj&&obj.title?obj.title:'',
            content: obj&&obj.content?obj.content:'',
            okText:obj&&obj.okText?obj.okText:'我知道了',
            onOk:function(){
                obj&&obj.onOK&&obj.onOK();
            }
        });
        $(document).trigger('dialogShow');
    },
    /**
     * 字符串截取
     * @param  {[type]} str 原字符串
     * @param  {[type]} num 截取的长度
     * @return {[type]}     截取后的字符串
     */
    strCutOut(str, num) {
        if(str.length>num) {
            return str.substring(0, num)+"...";
        }else {
            return str;
        }
    },

     /**
     * 判断数组
     * @param o
     * @returns {boolean}
     */
    isArray: function (o) {
        return Object.prototype.toString.call(o) === '[object Array]';
    },

    /**
     * 校验邮箱格式是否正确
     * @param  {[type]} value  输入的内容
     * @return {[type]}    true:邮箱格式正确 false:邮箱格式错误
     */
    checkEmailFormat(value) {
        let reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        let bool = reg.test(value);
        if(bool) {
            return true;
        }
        return false;
    },

    /**
     *  判断是否登录，通过cookie判断
     * @return {Boolean} [description]
     */
    isLogin() {
        var loginFlag = false; //未登录
        var cookies = document.cookie.split(";");
        for(let i=0; i<cookies.length; i++){
            let cookieObj = cookies[i].split("=");
            if($.trim(cookieObj[0]) == "Y_B_L_S") {
                if(cookieObj[1] == "1"){
                    loginFlag = true;
                }
                break;
            }
        }
        return loginFlag;
    }

};

/**
 * 创建活动工具方法集合
 */
window.createActivityUtil = {

    // 创建活动路由跳转是否需要弹出提示标识位
    isNeedShowTip: true,

    /**
     * 校验券信息中的可用时间
     * @returns string 校验成功返回空串""，失败返回错误信息
     */
    checkTicketUseTime(isLimit, items) {
        if (isLimit) {
            if (!items || !items.length) {
                return "请设置券可用时间";
            } else {
                for (let m = 0; m < items.length; m++) {
                    for (let n = m; n < items.length; n++) {
                        let item = items[n];
                        if (!item.weekList || !item.weekList.length) {
                            return "第" + (n + 1) + "条记录星期为空";
                        } else if (!item.timeList || !item.timeList.length) {
                            return "第" + (n + 1) + "条记录时间为空";
                        } else {
                            for (let i = 0; i < item.timeList.length; i++) {
                                if (!item.timeList[i].startTime) {
                                    return "第" + (n + 1) + "条记录第" + (i + 1) + "行包含空开始时间";
                                } else if (!item.timeList[i].endTime) {
                                    return "第" + (n + 1) + "条记录第" + (i + 1) + "行包含空结束时间";
                                } else if (item.timeList[i].startTime >= item.timeList[i].endTime) {
                                    return "第" + (n + 1) + "条记录第" + (i + 1) + "行开始时间须小于结束时间";
                                }

                                for (let j = i + 1; j < item.timeList.length; j++) {
                                    if (!item.timeList[j].startTime) {
                                        return "第" + (n + 1) + "条记录第" + (j + 1) + "行包含空开始时间";
                                    } else if (!item.timeList[j].endTime) {
                                        return "第" + (n + 1) + "条记录第" + (j + 1) + "行包含空结束时间";
                                    }
                                    if (item.timeList[j].startTime < item.timeList[i].endTime && item.timeList[j].endTime > item.timeList[i].startTime) {
                                        return "第" + (n + 1) + "条记录第" + (i + 1) + "行与第" + (j + 1) + "行时间重叠";
                                    }
                                }
                            }

                        }
                        if (n == m) {
                            continue;
                        }

                        // 如果选择了相同的星期，不同记录之间也要时间判重
                        if (this._checkHasSameDay(item.weekList, items[m].weekList)) {
                            for (let i = 0; i < item.timeList.length; i++) {
                                for (let j = 0; j < items[m].timeList.length; j++) {
                                    if (items[m].timeList[j].startTime < item.timeList[i].endTime && items[m].timeList[j].endTime > item.timeList[i].startTime) {
                                        return "第" + (m + 1) + "条记录第" + (j + 1) + "行与第" + (n + 1) + "条记录第" + (i+ 1) + "行时间重叠";
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
        return "";
    },

    // 检测是否选择了相同的星期
    _checkHasSameDay(srcWeekList, targetWeekList) {
        if (!srcWeekList || !targetWeekList || !srcWeekList.length || !targetWeekList.length) {
            return false;
        }
        for (let srcItem of srcWeekList) {
            for (let targetItem of targetWeekList) {
                if (srcItem.value == targetItem.value) {
                    return true;
                }
            }
        }
        return false;
    },
    /**
     * 校验券信息中的不可用时间
     * @returns string 校验成功返回空串""，失败返回错误信息
     */
    checkTicketForbiddenTime(isLimit, items,activityDate) {
        if (isLimit) {
            if (!items || !items.length || (items.length === 1 && !items[0].startTime)) {
                return "请设置券不可用日期";
            } else {
                let hasEmptyItem = false;
                let hasSameDate = false;
                let hasOverDate = false;
                let errorTip = "";
                loop:
                for (let i = 0; i < items.length; i++) {
                    if (!items[i].startTime) {
                        hasEmptyItem = true;
                        continue;
                    }
                    if(activityDate && activityDate.length){
                        if (items[i].startTime < activityDate[0].split(" ")[0] || items[i].endTime > activityDate[1].split(" ")[0]) {
                            hasOverDate = true;
                            continue;
                        }
                    }

                    for (let j = i + 1; j < items.length; j++) {
                        if (!items[j].startTime || !items[i].startTime) {
                            hasEmptyItem = true;
                            continue;
                        }
                        if (items[j].startTime < items[i].endTime && items[j].endTime > items[i].startTime) {
                            hasSameDate = true;
                        }

                        if (hasEmptyItem && hasSameDate && hasOverDate) {
                            break loop;
                        }
                    }
                }
                let errorTips = [];
                if (hasEmptyItem) {
                    errorTips.push("不可用时间条目不能为空");
                }
                if(hasOverDate){
                    errorTips.push("不可用时间必须在活动时间范围内");
                }
                if (hasSameDate) {
                    errorTips.push("不可用时间不能重叠");
                }
                return errorTips.join(",");
            }
        }
        return "";
    },

    /**
     * 校验券使用说明
     * @returns string 校验成功返回空串""，失败返回错误信息
     */
    checkTicketForDesc(desc) {
        if (!desc || !desc.length || (desc.length === 1 && !desc[0].length)) {
            return "请输入使用说明";
        } else {
            for (let item of desc) {
                if (new RegExp(/^\s*$/).test(item)) {
                    return "券使用说明条目不能为空";
                }
            }
        }
        return "";
    },
    /**
     *
     * @param minConsume 最低消费
     * @param maxConsume 最高优惠金额
     * @param rate  折扣率
     */
    checkTicketForMaxAmount(minConsume,maxAmount,rate){
        if(maxAmount && minConsume*(1-rate/10) > maxAmount){
            return "折扣券的最高优惠金额必须大于等于最低消费*(1-折扣率)";
        }else{
            return "";
        }
    },
    /**
     * 统一校验券信息中的图片，可用时间等
     * @param ticketInfo
     * @param activityTime
     * @returns {*} 校验成功返回null，失败返回错误对象
     */
    ckeckTicketInfo(ticketInfo,activityDate) {
        debugger
        // 需要校验图片等信息是否完整
        let checked = true;
        let errorTip = {};
        if (!ticketInfo.logoUri) {
            checked = false;
            errorTip.logoUri = "请上传券Logo";
        }

        errorTip.forbiddenTime = createActivityUtil.checkTicketForbiddenTime(ticketInfo.isLimitForbiddenTime, ticketInfo.forbiddenTimeItems,activityDate);
        if (errorTip.forbiddenTime) {
            checked = false;
        }

        errorTip.useTime = createActivityUtil.checkTicketUseTime(ticketInfo.isLimitUseTime, ticketInfo.useTimeItems);
        if (errorTip.useTime) {
            checked = false;
        }

        errorTip.desc = createActivityUtil.checkTicketForDesc(ticketInfo.desc);
        if (errorTip.desc) {
            checked = false;
        }
        errorTip.maxAmount = createActivityUtil.checkTicketForMaxAmount(ticketInfo.minConsume,ticketInfo.maxAmount,ticketInfo.rate);
        if (errorTip.maxAmount) {
            checked = false;
        }
        // 适用门店
        if (!ticketInfo.isApplyStoreCBChecked && (!ticketInfo.suitShopsItems || !ticketInfo.suitShopsItems.length)) {
            checked = false;
            errorTip.suitShops = "请选择适用门店";
        }

        if (checked) {
            return null;
        } else {
            return errorTip;
        }
    },

    /** 组装使用频率*/
    buildUserWinFrequency(isLimitPerson, limitPerson) {
        let userWinFrequency = ""; // 领取上限，每日|周|月 每人多少张 格式 D||2
        if (isLimitPerson == "1") {
            userWinFrequency = "D||" + limitPerson;
        } else if (isLimitPerson == "2") {
            userWinFrequency = "W||" + limitPerson;
        } else if (isLimitPerson == "3") {
            userWinFrequency = "M||" + limitPerson;
        }
        return userWinFrequency;
    },

    /** 组装使用频率描述*/
    buildUserWinFrequencyDesc(isLimitPerson, limitPerson) {
        let limitPersonDesc = "不限制";
        if (isLimitPerson == "1") {
            limitPersonDesc = "每人每日" + limitPerson + "张";
        } else if (isLimitPerson == "2") {
            limitPersonDesc = "每人每周" + limitPerson + "张";
        } else if (isLimitPerson == "3") {
            limitPersonDesc = "每人每月" + limitPerson + "张";
        }
        return limitPersonDesc;
    }
};

// 创建活动也用户刷新提醒
window.onbeforeunload = function(e) {
    let href = window.location.href;
    const activityCreate = "#/MarketingCenter/ActivityCreate/";
    const activityModify = "#/ActivityManagement/ActivityModify/";
    const RecommendStoreSetting = "#/RecommendStores/RecommendStoreSetting";
    const RecommendStoreEdit = "#/RecommendStores/RecommendStoreEdit";
    if (href.indexOf(activityCreate) > 0 
        || href.indexOf(activityModify) > 0
        || href.indexOf(RecommendStoreSetting) > 0
        || href.indexOf(RecommendStoreEdit) > 0) {
        return "确认离开当前页面？";
    }
    return;
};

// 服务端filter重定向
window.redirect = function(data) {
    if (data && data.code == 301) { //跳转到登录页面
        createActivityUtil.isNeedShowTip = false; // 创建活动的路由监听
        window.location.href="/login.html";
        return true;
    }else if (data && data.code == 302 && data.resultObject) { //跳转到授权引导页面
        createActivityUtil.isNeedShowTip = false; // 创建活动的路由监听
        sessionStorageClient.put("redirectUrl",data.resultObject);
        window.location.href="#/RedirectSetUp";
        return true;
    }else{
        return false;
    }
};

/***
 * 数组对象定义一个函数，用于查找指定的元素在数组中的位置，即索引
 * @param val
 * @returns {number}
 */
Array.prototype.indexOf = function(val) {
    for (let i = 0; i < this.length; i++) {
        if (Object.is(this[i], val)) return i;
    }
    return -1;
};
/***
 * 构造remove函数
 * 使用通过得到这个元素的索引，使用js数组自己固有的函数去删除这个元素
 * @param val
 * @returns {number}
 */
Array.prototype.remove = function(val) {
    let index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

/**
 * 对扩展原生 JavaScript 能够将这种方法加入到数组原型(Array prototype)中
 * @param index
 * @param item
 */
Array.prototype.insert = function (index, item) {
    this.splice(index, 0, item);
};
