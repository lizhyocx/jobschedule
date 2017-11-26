import React from 'react';
import { Button, Table, Popconfirm, Form, Select, Input, Row, Col } from 'antd';
import ProTitle from '../../components/ProTitle/ProTitle';
import OperMore from '../../components/OperMore/OperMore';

const FormItem = Form.Item;
const Option = Select.Option;

class SearchPanleForm extends React.Component{
    constructor(props){
        super(props);
    }

    handleSearch=(e)=>{
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            this.setParams(values);

        });
    }
    setParams = (values)=>{
        values.pageNo=1;
        this.props.callbackParent(values);
    };
    reset=()=>{
        this.props.form.resetFields();
    };
    render(){
        let sty={marginBottom:24};
        const {getFieldDecorator}=this.props.form;
        return(
                <Form onSubmit={this.handleSearch} layout="inline">
                    <Row gutter={0}>
                        <Col  span={6}>
                            <FormItem label="任务名称" style={sty}>
                                  <span>{this.props.jobName}</span>
                            </FormItem>
                        </Col>
                        <Col  span={6}>
                            <FormItem label="执行结果" style={sty}>
                                {getFieldDecorator('exeResult',{
                                    initialValue:'0'
                                })(
                                     <Select size="large" style={{width:150}}>
                                            <Option value="0">全部结果</Option>
                                            <Option value="1">非成功结果</Option>
                                    </Select>
                                )}
                            </FormItem>
                        </Col>
                        <Col  span={6}>
                           <Button type="primary" htmlType="submit" size="large">刷新</Button>
                        </Col>
                    </Row>
                </Form>
        )
    }
}
const SearchPanle = Form.create()(SearchPanleForm);

class MonitorView extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			params:{
                jobId:this.props.params.jobId,
				pageSize:10,
				pageNo:1
			},
			totalCount:0,
			datas:[],
            jobName:'',
            timeout:0
		};
		this.columns = [
			{
				title: '执行ID',
				dataIndex: 'logId',
				key: 'logId',
				width:60
			}, {
				title: '触发时间',
				dataIndex: 'exeBeginTime',
				key: 'exeBeginTime',
				width:120
			}, {
				title: '结束时间',
				dataIndex: 'exeEndTime',
				key: 'exeEndTime',
				width:120
			}, {
				title: '耗时',
				dataIndex: 'exeTime',
				key: 'exeTime',
				width:50
			}, {
				title: '是否超时',
				dataIndex: 'timeout',
				key: 'timeout',
				width:70
			}, {
				title: '执行URL',
				dataIndex: 'exeUrl',
				key: 'exeUrl',
				width:250
			}, {
                title: '执行结果',
                dataIndex: 'exeResult',
                key: 'exeResult',
                width:100
            }, {
                title: '结果说明',
                dataIndex: 'resMsg',
                key: 'resMsg',
                width:150
            }
		];
	}

	/**
     * 生命周期函数调用
     */
    componentDidMount = () => {
        this.getJobName();
        this.dataRequest(this.state.params);
    };

    /*
     * 获取任务名称
    */
    getJobName = () => {
        let serverUrl = commonUtil.serverIp() + "/job/get.do";
        let params = {jobId:this.props.params.jobId};
        let sucFunc = (data) => {
            if(data && data.success && data.resultObject) {
                this.setState({jobName:data.resultObject.jobName, timeout:data.resultObject.timeout})
            } else {
                commonUtility.messageWarning(data.msg || "获取任务名称失败", commonUtility.tipTime);
            }
        }
        let errFunc = () => {
            commonUtility.messageWarning(data.msg || "获取任务名称失败", commonUtility.tipTime);
        }
        commonUtil.ajaxRequest(serverUrl,  'GET', params, sucFunc, errFunc, false);
    }

	dataRequest = (params) => {
        let serverUrl = commonUtil.serverIp() + '/schedule/monitor/list.do';
        let sucFunc = (data) => {
            if(data && data.success) {
                let results = data.resultObject.dataList || [];
                let datas = [];
                for(let i=0;i<results.length;i++) {
                    let res = results[i];
                    let obj = {
                        key:i,
                        logId:res.logId,
                        exeUrl:res.exeUrl,
                        resMsg:res.retMsg
                    };
                    let expand = 0;
                    if(res.notifyStart) {
                        obj.exeBeginTime = commonUtil.formatYYYY_MM_DD_HH_mm_ss(res.notifyStart);
                        expand = parseInt((new Date().getTime() - res.notifyStart) / 1000);
                    }
                    if(res.finished) {
                        obj.exeEndTime = commonUtil.formatYYYY_MM_DD_HH_mm_ss(res.finished);
                        expand = parseInt((res.finished - res.notifyStart) / 1000);
                    }
                    obj.exeTime = expand + '秒';
                    if(this.state.timeout < expand) {
                        obj.timeout = '是';
                    } else {
                        obj.timeout = '否';
                    }
                    if(Object.is(res.notifyStatus, 0)) {
                        obj.exeResult = '正在通知';
                    } else if(Object.is(res.notifyStatus, 2)) {
                        obj.exeResult = '通知失败';
                    } else if(Object.is(res.notifyStatus, 1)) {
                        if(Object.is(res.returnStatus, 0)) {
                            obj.exeResult = '通知成功，本次任务正在执行';
                        } else if(Object.is(res.returnStatus, 1)) {
                            obj.exeResult = '执行成功';
                        } else if(Object.is(res.returnStatus, 2)) {
                            obj.exeResult = '执行失败';
                        }
                    }
                    datas.push(obj);
                }
                let count = data.resultObject.totalNumber || 0;
                this.setState({datas:datas, totalCount:count});
            } else {
                commonUtility.messageWarning(data.msg || "获取监控明细失败", commonUtility.tipTime);
            }
        };
        let errFunc = () => {
            commonUtility.messageWarning("获取监控明细失败", commonUtility.tipTime);
        }
		commonUtil.ajaxRequest(serverUrl, 'GET', params, sucFunc, errFunc, false);
	}

	//当搜索panal变化时
    onChildChanged = (newParams) => {
        let old = this.state.params;
        this.setState({
            params:Object.assign(old,newParams)
        });
        this.dataRequest(this.state.params);
    }

	//重新请求 获取数据
    handlePageChange = (pageNo,pageSize)=>{
        let target = this.state.params;
        let source = {pageSize: pageSize, pageNo: pageNo};
        Object.assign(target, source);
        this.setState({params: target});
        /**请求数据*/
        this.dataRequest(target);
    }

	render() {
		let count = this.state.totalCount;
        let params = this.state.params;
        let handlePageChange = this.handlePageChange;
        let pagination = {
            total: count,
            current:params.pageNo,
            showSizeChanger: true,
            showQuickJumper: true,
            pageSizeOptions: ['10', '20', '30', '40'],
            showTotal(){
                return `共 ${count} 条`;
            },
            onShowSizeChange(current, pageSize) {
                handlePageChange(1, pageSize);
            },
            onChange(current,pageSize) {
                handlePageChange(current,pageSize);
            },
        };
		return (
			<div>
	            <ProTitle name="任务监控明细" />
                {<SearchPanle jobName={this.state.jobName} callbackParent={this.onChildChanged} />}
	            <div style={{ position: 'relative' }}>
	                <Table pagination={pagination} columns={this.columns} dataSource={this.state.datas} bordered />
	            </div>
        	</div>
		)
	}

}
export default MonitorView;