import React from 'react';
import {Form, Input, Button, DatePicker} from 'antd';
import ProTitle from '../../components/ProTitle/ProTitle'
import moment from 'moment';

const FormItem = Form.Item;

class AddTimeRuleForm extends React.Component {
	constructor(params) {
		super(params);
		this.state = {
			jobName:'',
			editable:false,
			timeRule:{}
		}
	}

	componentDidMount() {
		this.getJobName();
		this.getTimer();
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
	/*
	 * 获取时间规则
	 */
	 getTimer = () => {
	 	let serverUrl = commonUtil.serverIp() + "/timer/get.do";
		let params = {jobId:this.props.jobId};
		let sucFunc = (data) => {
			if(data && data.success && data.resultObject) {
				this.setState({timeRule:data.resultObject})
			} else {
				commonUtility.messageWarning(data.msg || "获取时间规则失败", commonUtility.tipTime);
			}
		}
		let errFunc = () => {
			commonUtility.messageWarning(data.msg || "获取时间规则失败", commonUtility.tipTime);
		}
		commonUtil.ajaxRequest(serverUrl,  'GET', params, sucFunc, errFunc, false);
	 }

	/* 设置新规则 */
	editNewRule = () => {
		this.setState({editable:true});
	}

	handleSubmit = (e) => {
		e.preventDefault();
		this.props.form.validateFields((err, values) => {
			if(!err) {
				values.effectiveTime = values.effectiveTime.format('YYYY-MM-DD HH:mm:ss');
				values.jobId = this.props.jobId;
				let serverUrl = commonUtil.serverIp() + '/timer/save.do';
				let sucFunc = (data) => {
					if(data && data.success) {
						commonUtility.messageSuccess("设置时间规则成功", commonUtility.tipTime);
						this.getTimer();
						this.props.form.resetFields();
					} else {
						commonUtility.messageWarning(data.msg || "设置时间规则失败", commonUtility.tipTime);
					}
				};
				let errFunc = () => {
					commonUtility.messageWarning("设置时间规则异常", commonUtility.tipTime);
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
		return (
			<div>
                <ProTitle name='时间规则设置'/>
                <div>
                    <Form onSubmit={this.handleSubmit}>
                        <FormItem {...formItemLayout} label="任务名称">
                            <span>{this.state.jobName}</span>
                        </FormItem>
                        <FormItem {...formItemLayout} label="当前时间规则">
                            <span>
                            	{this.state.timeRule.cron}
                            </span>
                        </FormItem>
                        <FormItem {...formItemLayout} label="当前生效时间">
                            <span>
                            	{this.state.timeRule.effectiveTime}
                            </span>
                        </FormItem>
                        <FormItem {...formItemLayout} label="未来时间规则">
                            <span>
                            	{this.state.timeRule.futureCron}
                            </span>
                        </FormItem>
                        <FormItem {...formItemLayout} label="未来生效时间">
                            <span>
                            	{this.state.timeRule.futureEffectiveTime}
                            </span>
                        </FormItem>
                        <FormItem {...formItemLayout} label="时间规则" style={{display:this.state.editable ? 'block' : 'none'}}>
		                    {getFieldDecorator('cron', {
		                        rules: [{required: true, message: '请输入时间规则'}],
		                        initialValue: '',
		                    })(
		                        <Input style={{display: "inline-block", width: 230}}
		                               placeholder="时间规则"
		                        />
		                    )}
		                </FormItem>
		                <FormItem {...formItemLayout} label="生效时间" style={{display:this.state.editable ? 'block' : 'none'}}>
		                    {getFieldDecorator('effectiveTime', {
		                        rules: [{required: true, message: '请输入生效时间'}]
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
		                    <Button type="primary" htmlType="button" onClick={this.editNewRule} style={{display:this.state.editable ? 'none' : 'block'}}>设置新规则</Button>
		                    <Button type="primary" htmlType="submit" style={{display:this.state.editable ? 'block' : 'none'}}>保存规则</Button>
		                </FormItem>
             		</Form>
            	</div>
            </div>
		);
	}
}
const Info = Form.create()(AddTimeRuleForm);

class TimeRule extends React.Component {

    render() {
        return (
            <div>
               <Info jobId={this.props.params.jobId} />
            </div>
        );
    }
};
export default TimeRule;