import {Modal, message} from 'antd';
/**
 * Created by hipo on 2016/6/2.
 * 提供公共的方法
 */
window.commonUtil = {

    // 提示时间（秒）
    tipTime: 3,

    serverIp() {
       // const preIp = "http://localhost:8080";
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
    ajaxRequest: function(url, type, params, sucFunc, errorFunc, async, contentType){
        let hide = message.loading('正在执行中...', 0);
        $.ajax({
            url : url,
            type : type,
            data : params,
            async: !async,
            dataType : 'json',
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
