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
/*$(function() {
	var pageHeightPost = function() {
		if (window.parent) {
			var pageHeight = $('body').height();
			window.parent.postMessage('{"pageHeight": ' + pageHeight + '}', '*');
		}
	};
	pageHeightPost();

	var intervalId = setInterval(function() {
		pageHeightPost();
	}, 1000);
	window.scrollTop = 0;
	window.parentWinHeight = 0;
	window.parentTop = 168;
	var addEvent = function(ele, event_name, func) {
		event_name = event_name.replace(/^on/, "");
		ele.addEventListener(event_name, func, false);
	};
	if (window.parent && window.parent.postMessage) {
		addEvent(window, 'message', function(obj) {
			window.getPos = $.parseJSON(obj.data);
			window.scrollTop = window.getPos.scrollTop;
			window.parentWinHeight = window.getPos.windowHeight;
			if (window.getPos.scrollTop !== undefined && window.getPos.windowHeight !== undefined) {
				resetMessagePosition();
				$("body").css({
					"overflow-y": "hidden"
				});
				if (isIE()) {
					resetDialogPosition();
				}
			}
		}, false);
	}
	$(document).on("dialogShow", function() {
		checkConfirmClass();
		resetDialogPosition();
	});
	$(document).on("messageShow", function() {
		resetMessagePosition();
	});
	var isIE = function() {
		if (!!window.ActiveXObject || "ActiveXObject" in window){
			return true;
		}else{
			return false;
		}
	};
	var checkConfirmClass = function(){
		var $confirm = $(".ant-modal.ant-confirm:visible");
		$confirm.each(function(index, el) {
			$(el).parent().addClass('vertical-center-modal zhk-dialog');
		});
	};
	var resetDialogPosition = function() {
		setTimeout(function() {
			var $zhkDialog = $(".zhk-dialog:visible");
			if (window.getPos.scrollTop !== undefined && window.getPos.windowHeight !== undefined && $zhkDialog.length !== 0) {
				$zhkDialog.each(function(index, el) {
					var $this = $(el);
					if (Number(window.scrollTop) < Number(window.parentTop)) {
						$this.css({
							"height": window.parentWinHeight - (window.parentTop - window.scrollTop),
							'top': 0
						});
					} else {
						$this.css({
							"height": window.parentWinHeight,
							'top': (window.scrollTop - window.parentTop)
						});
					}
					if (Number($this.height()) < Number($this.find(".ant-modal").height())) {
						$this.css({
							"height": $this.find(".ant-modal").height() + 30
						});
					} else {
						if ($this.height() + Number($this.css("top").replace("px", "")) > $("body").height()) {
							$this.css({
								"height": $("body").height() - $this.css("top").replace("px", "") + 30
							});
						}
						if (Number($this.height()) < Number($this.find(".ant-modal").height())) {
							$this.css({
								"height": $this.find(".ant-modal").height() + 30,
								"top": $("body").height() - $this.find(".ant-modal").height() - 30
							});
						}
					}
				});
			};
		}, 0);
	};
	var resetMessagePosition = function(){
		setTimeout(function() {
		if (window.getPos.scrollTop !== undefined && window.getPos.windowHeight !== undefined) {
				var $this = $(".ant-message");
				if (Number(window.scrollTop) < Number(window.parentTop)) {
					$this.css({
						'top': 26
					});
				} else {
					$this.css({
						'top': (window.scrollTop - window.parentTop) + 26
					});
				}
			}
		},0);
	};
});*/