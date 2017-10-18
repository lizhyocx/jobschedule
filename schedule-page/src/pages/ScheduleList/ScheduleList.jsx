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
                    	<Col span={6}>
                            <FormItem label="任务ID" style={sty}>
                                  {getFieldDecorator('jobId',{
                                    initialValue:''
                                  })(
                                    <Input style={{width:150}}/>
                                  )}
                            </FormItem>
                        </Col>
                        <Col  span={6}>
                            <FormItem label="任务名称" style={sty}>
                                  {getFieldDecorator('jobName',{
                                      initialValue:''
                                  })(
                                    <Input style={{width:150}}/>
                                  )}
                            </FormItem>
                        </Col>
                        <Col  span={6}>
                           <Button type="primary" htmlType="submit" size="large">搜索</Button>
                           <Button size="large" style={{marginLeft:16}} onClick={this.reset}>清除条件</Button>
                        </Col>
                    </Row>
                </Form>
        )
    }
}
const SearchPanle = Form.create()(SearchPanleForm);

class ScheduleList extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			params:{
				pageSize:10,
				pageNo:1
			},
			totalCount:0,
			datas:[]
		};
		this.columns = [
			{
				title: '任务ID',
				dataIndex: 'jobId',
				key: 'jobId',
				width:60
			}, {
				title: '任务名称',
				dataIndex: 'jobName',
				key: 'jobName',
				width:130
			}, {
				title: '最后执行时间',
				dataIndex: 'lastExecuteTime',
				key: 'lastExecuteTime',
				width:130
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
			}, {
				title: '操作',
				dataIndex: 'operations',
				key: 'operationis',
				render: (text, record, index) =>{
	        		return (
	        		    <span>
                          <a onClick={() => {this.editExecutor(record)}}>人工执行</a>
                          <span className="ant-divider" />
                          <a onClick={() => {this.deleteExecutor(record.exeId)}}>暂停调度</a>
                        </span>
	        		)
	        	}
			}
		];
	}

	/**
     * 生命周期函数调用
     */
    componentDidMount = () => {
        this.dataRequest(this.state.params);
    };

	dataRequest = (params) => {
        let serverUrl = commonUtil.serverIp() + '/mockjsdata/63/job/list.do';
        let sucFunc = (data) => {
            if(data && data.success) {
                let results = data.resultObject.dataList || [];
                let datas = [];
                for(let i=0;i<results.length;i++) {
                    let res = results[i];
                    let obj = {
                        key:i,
                        jobId:res.jobId,
                        jobName:res.jobName,
                        lastExecuteTime:res.lastExecuteTime,
                        exeUrl:res.exeUrl,
                        exeResult:res.exeResult,
                        resMsg:res.resMsg
                    };
                    datas.push(obj);
                }
                let count = data.resultObject.totalNumber || 0;
                this.setState({datas:datas, totalCount:count});
            } else {
                commonUtility.messageWarning(data.msg || "获取调度列表失败", commonUtility.tipTime);
            }
        };
        let errFunc = () => {
            commonUtility.messageWarning("获取调度列表失败", commonUtility.tipTime);
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
	            <ProTitle name="调度列表" />
                {<SearchPanle callbackParent={this.onChildChanged} />}
	            <div style={{ position: 'relative' }}>
	                <Table pagination={pagination} columns={this.columns} dataSource={this.state.datas} bordered />
	            </div>
        	</div>
		)
	}

}
export default ScheduleList;