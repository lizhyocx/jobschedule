import {Modal,message} from 'antd';
import moment from 'moment';
window.commonUtility = {
	
	// 错误提示时间（秒）
	tipTime: 3,

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
	messageInfo(content,duration){
		setTimeout(function(){
			$(document).trigger('messageShow');
		},10);
		if(duration){
			return message.info(content,duration);
		}else{
			return message.info(content);
		}
	},
	messageWarning(content,duration){
		setTimeout(function(){
			$(document).trigger('messageShow');
		},10);
		if(duration){
			return message.warning(content,duration);
		}else{
			return message.warning(content);
		}
	},
	messageSuccess(content,duration){
		setTimeout(function(){
			$(document).trigger('messageShow');
		},10);
		if(duration){
			return message.success(content,duration);
		}else{
			return message.success(content);
		}
	},
	messageError(content,duration){
		setTimeout(function(){
			$(document).trigger('messageShow');
		},10);
		if(duration){
			return message.error(content,duration);
		}else{
			return message.error(content);
		}
	},
	messageLoading(content,duration){
		setTimeout(function(){
			$(document).trigger('messageShow');
		},10);
		if(duration){
			return message.loading(content,duration);
		}else{
			return message.loading(content);
		}
	}
};