import React from 'react';
import {Form, Input, Button, Select, InputNumber} from 'antd';
import ProTitle from '../../components/ProTitle/ProTitle';
import ItemTitleName from '../../components/ItemTitleName/ItemTitleName';
const FormItem = Form.Item;
class AddJobForm extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			job:{}
		}
	}

	componentDidMount() {
		if(this.props.jobId) {
			this.dataRequest({jobId:this.props.jobId});
		}
	}

	dataRequest = (params) => {
		let serverUrl = commonUtil.serverIp() + '/job/get.do';
		let sucFunc = (data) => {
			if(data && data.success) {
				commonUtility.messageSuccess("获取任务成功", commonUtility.tipTime);
				this.setState({job:data.resultObject})
			} else {
				commonUtility.messageWarning(data.msg || "获取任务失败", commonUtility.tipTime);
			}
		};
		let errFunc = () => {
			commonUtility.messageWarning("获取任务异常", commonUtility.tipTime);
		};
		commonUtil.ajaxRequest(serverUrl, 'GET', params, sucFunc, errFunc, false,);
	}

	handleSubmit = (e) => {
		e.preventDefault();
		this.props.form.validateFields((err, values) => {
			if(!err) {
				let serverUrl = commonUtil.serverIp() + '/job/save.do';
				let sucFunc = (data) => {
					if(data && data.success) {
						commonUtility.messageSuccess("保存成功", commonUtility.tipTime);
						window.location = "#/JobList"
					} else {
						commonUtility.messageWarning(data.msg || "保存任务失败", commonUtility.tipTime);
					}
				};
				let errFunc = () => {
					commonUtility.messageWarning("保存任务异常", commonUtility.tipTime);
				};
				let params = values;
				if(this.props.jobId) {
					let job = this.state.job;
					Object.assign(job, values);
					params = job;
				}
				commonUtil.ajaxRequest(serverUrl, 'POST', params, sucFunc, errFunc, false,);
			}
		});
	}

	render() {
		const { getFieldDecorator } = this.props.form;
		const formItemLayout = {
            labelCol: {span: 6},
            wrapperCol: {span: 17},
        };
        let _state = this.state.job;
        let title = '任务设置';
        if(this.props.jobId) {
        	title = '编辑任务'
        }
		return (
			<div>
                <ProTitle name={title}/>
                <div>
                    <Form onSubmit={this.handleSubmit}>
                        <FormItem {...formItemLayout} label="任务名称">
                            {getFieldDecorator('jobName', {
                                rules: [{required: true, message: '请输入任务名称'},{
                                         validator: ''}],
                                initialValue: _state.jobName || '',

                            })(
                                <Input  style={{display: "inline-block", width: 230}}
                                        placeholder="任务名称"
                                />
                            )}
                        </FormItem>
		                <FormItem {...formItemLayout} label="执行机器选择">
		                    {getFieldDecorator('executeSelect', {
		                        rules: [{required: true, message: '请选择执行机器'}],
		                        initialValue: _state.executeSelect || '0',
		                    })(
		                        <Select style={{display: "inline-block", width: 230}}>
		                        	<Option value="0">第一台</Option>
		                        	<Option value="1">顺序选取</Option>
		                        	<Option value="2">随机选取</Option>
		                        	<Option value="3">全部执行</Option>
		                        </Select>
		                    )}
		                </FormItem>
		                <FormItem {...formItemLayout} label="通知保证">
		                    {getFieldDecorator('executeRule', {
		                        rules: [{required: true, message: '请选择通知保证'}],
		                        initialValue: _state.executeRule || '0',
		                    })(
		                        <Select style={{display: "inline-block", width: 230}} >
		                        	<Option value="0">只通知一次</Option>
		                        	<Option value="1">保证通知成功</Option>
		                        </Select>
		                    )}

		                </FormItem>
		                <FormItem {...formItemLayout} label="超时警告时间">
		                    {getFieldDecorator('timeout', {
		                        rules: [{required: true, message: '请输入超时警告时间'}],
		                        initialValue: _state.timeout || '',
		                    })(
		                        <InputNumber style={{display: "inline-block", width: 230}}
		                        	   min={1}
		                               placeholder="超时警告时间"
		                        />
		                    )}<span>秒</span>
		                </FormItem>
		                <FormItem {...formItemLayout} label="任务描述">
		                    {getFieldDecorator('jobDesc', {
		                        rules: [{required: true, message: '请输入任务描述'}],
		                        initialValue: _state.jobDesc || '',
		                    })(
		                        <Input style={{display: "inline-block", width: 230}}
		                        	   type="textarea"
		                        	   rows={4}
		                               placeholder="任务描述"
		                        />
		                    )}
		                </FormItem>
		                <FormItem wrapperCol={{ span: 12, offset: 6 }}>
		                    <Button type="primary" htmlType="submit">提交</Button>
		                </FormItem>
             		</Form>
            	</div>
            </div>
		);
	}
}

const Info = Form.create()(AddJobForm);

class AddJob extends React.Component {
    render() {
        return (
            <div>
               <Info jobId={this.props.params.jobId}/>
            </div>
        );
    }
};
export default AddJob;
