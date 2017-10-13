import React from 'react';
import { Button, Table, Popconfirm, Form, Select, Input, Row, Col } from 'antd';
import ProTitle from '../../components/ProTitle/ProTitle';

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
                            <FormItem label="状态" style={sty}>
                                {getFieldDecorator('status',{
                                    initialValue:'1'
                                })(
                                     <Select size="large" style={{width:150}}>
                                            <Option value="1">有效</Option>
                                            <Option value="0">无效</Option>
                                    </Select>
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

class JobList extends React.Component {
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
				title: '任务Id',
				dataIndex: 'jobId',
				key: 'jobId',
				width:130
			}, {
				title: '任务名称',
				dataIndex: 'jobName',
				key: 'jobName',
				width:130
			}, {
				title: '执行机器',
				dataIndex: 'executeSelect',
				key: 'executeSelect',
				width:130
			}, {
				title: '通知保证',
				dataIndex: 'executeRule',
				key: 'executeRule',
				width:130
			}, {
				title: '超时时间',
				dataIndex: 'timeout',
				key: 'timeout',
				width:130
			}, {
				title: '状态',
				dataIndex: 'status',
				key: 'status',
				width:130
			}, {
				title: '更新时间',
				dataIndex: 'updateTime',
				key: 'updateTime',
				width:130
			}, {
				title: '操作',
				dataIndex: 'operations',
				key: 'operationis',
				render: (text, record, index) =>{
	        		return (
	        			<div>
		        			<Popconfirm key={1} title="确定审核通过？" okText="确定" cancelText="取消" onConfirm={() => this.adAudit(record.mallId, record.adId, 2)} ><a>通过</a></Popconfirm>
	                        <span className="ant-divider"></span>
	                        <Popconfirm key={2} title="确定审核不通过？" okText="确定" cancelText="取消" onConfirm={() => this.adAudit(record.mallId, record.adId, 3)} ><a>不通过</a></Popconfirm>	
	        			</div>
                        
                    
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
		let datas = [
			{	key: 1,
				jobId: 1,
				jobName: '任务ee',
				executeSelect: '随机选取',
				executeRule: '只通知一次',
				timeout:300,
				status:'有效',
				updateTime:'2014-12-23 12:00:03'
			},
			{	key: 2,
				jobId: 1,
				jobName: '任务ee',
				executeSelect: '随机选取',
				executeRule: '只通知一次',
				timeout:300,
				status:'有效',
				updateTime:'2014-12-23 12:00:03'
			}
		];
		this.setState({datas:datas});
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
	            <ProTitle name="任务列表" />
                {<SearchPanle callbackParent={this.onChildChanged} />}
	            <div style={{ position: 'relative' }}>
	                <Table pagination={pagination} columns={this.columns} dataSource={this.state.datas} bordered />
	            </div>
        	</div>
		)
	}

}
export default JobList;