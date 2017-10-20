import React from 'react';
import {Form, Input, Button, DatePicker, Table} from 'antd';
import ProTitle from '../../components/ProTitle/ProTitle';
import ItemTitleName from '../../components/ItemTitleName/ItemTitleName';
import moment from 'moment';

const FormItem = Form.Item;
const dateFormat = 'YYYY-MM-DD HH:mm:ss';
class ExecutorForm extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			jobName:''
		}
	}

	componentDidMount() {
		this.getJobName();
	}

	/*
	 * 获取任务名称
	*/
	getJobName = () => {
		let serverUrl = commonUtil.serverIp() + "/job/get.do";
		let params = {jobId:this.props.jobId};
		let sucFunc = (data) => {
			if(data && data.success && data.resultObject) {
				this.setState({jobName:data.resultObject.jobName})
			} else {
				commonUtility.messageWarning(data.msg || "获取任务名称失败", commonUtility.tipTime);
			}
		}
		let errFunc = () => {
			commonUtility.messageWarning(data.msg || "获取任务名称失败", commonUtility.tipTime);
		}
		commonUtil.ajaxRequest(serverUrl,  'GET', params, sucFunc, errFunc, false);
	}

	handleSubmit = (e) => {
		e.preventDefault();
		//let _this = this;
		this.props.form.validateFields((err, values) => {
			if(!err) {
				let formatTime = values.effectiveTime.format(dateFormat);
				values.effectiveTime = Date.parse(new Date(formatTime))
				values.jobId = this.props.jobId;
				if(this.props.editExecutor) {
					values.exeId = this.props.editExecutor.exeId;
				}
				let serverUrl = commonUtil.serverIp() + '/executor/save.do';
				let sucFunc = (data) => {
					if(data && data.success) {
						commonUtility.messageSuccess("保存成功", commonUtility.tipTime);
						//重新加载列表
						this.props.handleBack();
						this.props.form.resetFields();
					} else {
						commonUtility.messageWarning(data.msg || "保存执行机器失败", commonUtility.tipTime);
					}
				};
				let errFunc = () => {
					commonUtility.messageWarning("保存执行机器异常", commonUtility.tipTime);
				};
				commonUtil.ajaxRequest(serverUrl, 'POST', values, sucFunc, errFunc, false);
			}
		});
	}

	render() {
		const { getFieldDecorator } = this.props.form;
		const formItemLayout = {
            labelCol: {span: 6},
            wrapperCol: {span: 17},
        };
        let _executor = this.props.editExecutor;
        let effectiveTime;
        if(_executor && _executor.effectiveTime) {
        	effectiveTime = moment(_executor.effectiveTime, dateFormat);
        }
		return (
			<div>
                <ProTitle name='任务设置'/>
                <div>
                    <Form onSubmit={this.handleSubmit}>
                        <FormItem {...formItemLayout} label="任务名称">
                            <span>{this.state.jobName}</span>
                        </FormItem>
		                
		                <FormItem {...formItemLayout} label="执行机器名">
		                    {getFieldDecorator('exeName', {
		                        rules: [{required: true, message: '请输入执行机器名'}],
		                        initialValue: _executor.exeName || '',
		                    })(
		                        <Input style={{display: "inline-block", width: 230}}
		                               placeholder="执行机器名"
		                        />
		                    )}
		                </FormItem>
		                <FormItem {...formItemLayout} label="执行URL">
		                    {getFieldDecorator('exeUrl', {
		                        rules: [{required: true, message: '请输入执行URL'}],
		                        initialValue: _executor.exeUrl || '',
		                    })(
		                        <Input style={{display: "inline-block", width: 230}}
		                               placeholder="执行URL"
		                        />
		                    )}
		                </FormItem>
		                <FormItem {...formItemLayout} label="执行接口">
		                    {getFieldDecorator('exeInterface', {
		                        rules: [{required: true, message: '请输入执行接口'}],
		                        initialValue: _executor.exeInterface || '',
		                    })(
		                        <Input style={{display: "inline-block", width: 230}}
		                               placeholder="执行接口"
		                        />
		                    )}
		                </FormItem>
		                <FormItem {...formItemLayout} label="生效时间">
		                    {getFieldDecorator('effectiveTime', {
		                        rules: [{required: true, message: '请输入生效时间'}],
		                        initialValue: effectiveTime,
		                    })(
		                        <DatePicker style={{display: "inline-block", width: 230}}
			                        showTime={
		                                {   
		                                    hideDisabledOptions:true
		                                }
                            		} 
		                        	format="YYYY-MM-DD HH:mm:ss"
                        		/>
		                    )}
		                </FormItem>
		                <FormItem wrapperCol={{ span: 12, offset: 6 }}>
		                    <Button type="primary" htmlType="submit">提交</Button>
		                </FormItem>
             		</Form>
            	</div>
            </div>
		)
	}

}

const FormInfo = Form.create()(ExecutorForm);

class Executor extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			datas:[],
			editExecutor:{}
		}
		this.columns = [
			{
				title: '机器Id',
				dataIndex: 'exeId',
				key: 'exeId',
				width:100
			}, {
				title: '机器名称',
				dataIndex: 'exeName',
				key: 'exeName',
				width:130
			}, {
				title: '执行URL',
				dataIndex: 'exeUrl',
				key: 'exeUrl',
				width:250
			}, {
				title: '执行接口',
				dataIndex: 'exeInterface',
				key: 'exeInterface',
				width:180
			}, {
				title: '生效时间',
				dataIndex: 'effectiveTime',
				key: 'effectiveTime',
				width:130
			}, {
				title: '状态',
				dataIndex: 'status',
				key: 'status',
				width:80
			}, {
				title: '操作',
				dataIndex: 'operations',
				key: 'operations',
				render: (text, record, index) =>{
	        		return (
	        		    <span>
					      <a onClick={() => {this.editExecutor(record)}}>编辑</a>
					      <span className="ant-divider" />
					      <a onClick={() => {this.deleteExecutor(record.exeId)}}>删除</a>
					    </span>
	        		)
	        	}
			}
		]
	}
	componentDidMount() {
		this.dataRequest();
	}

	/* 保存执行机器后，刷新列表 */
	handleBack = () => {
		this.setState({editExecutor:{}})
		this.dataRequest();
	}
	/* 编辑执行机器，表单填充数据 */
	editExecutor = (record) => {
		if(record.effectiveTime) {
			record.effectiveTime = commonUtil.formatTime(record.effectiveTime)
		} else {
			record.effectiveTime = '';
		}
		this.setState({editExecutor:record});
	}
	/* 删除执行机器 */
	deleteExecutor = (exeId) => {
		alert("delete:"+exeId)
	}

	dataRequest = () => {
		let serverUrl = commonUtil.serverIp() + '/executor/list.do';
		let params = {jobId:this.props.params.jobId}
        let sucFunc = (data) => {
            if(data && data.success) {
                let results = data.resultObject || [];
                let datas = [];
                for(let i=0;i<results.length;i++) {
                    let res = results[i];
                    let status = '';
                    if(Object.is(res.status, 0)) {
                    	status = '无效';
                    } else if(Object.is(res.status, 1)) {
                    	status = '有效';
                    } else if(Object.is(res.status, 2)) {
                    	status = '未来生效';
                    }
                    let obj = {
                        key:i,
                        exeId:res.exeId,
						exeName:res.exeName,
						exeUrl:res.exeUrl,
						exeInterface:res.exeInterface,
						effectiveTime:commonUtil.formatTime(res.effectiveTime),
						status:status

                    };
                    datas.push(obj);
                }
                this.setState({datas:datas});
            } else {
                commonUtility.messageWarning(data.msg || "获取执行机器列表失败", commonUtility.tipTime);
            }
        };
        let errFunc = () => {
            commonUtility.messageWarning("获取执行机器列表失败", commonUtility.tipTime);
        }
		commonUtil.ajaxRequest(serverUrl, 'GET', params, sucFunc, errFunc, false);
	}

	render() {
		return (
			<div>
				<FormInfo jobId={this.props.params.jobId} editExecutor={this.state.editExecutor} handleBack={this.handleBack} />
				<ItemTitleName titleName="已有执行机器" />
				<div style={{ position: 'relative' }}>
	                <Table columns={this.columns} pagination={false} dataSource={this.state.datas} bordered />
	            </div>
			</div>
		)
	}
}
export default Executor;