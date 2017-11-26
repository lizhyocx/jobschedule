import React from 'react';
import { Table } from 'antd';
import ProTitle from '../../components/ProTitle/ProTitle';
import ItemTitleName from '../../components/ItemTitleName/ItemTitleName';
import styles from './ViewJob.less'

class ViewJob extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			jobInfo:{},
			timeRule:{},
			executors:[]
		}
	}

	componentDidMount() {
		this.requestJobInfo();
		this.requestTimeRule();
		this.requestExecutors();
	}

	/* 获取任务信息 */
	requestJobInfo = () => {
		let params = {"jobId":this.props.params.jobId};
		let serverUrl = commonUtil.serverIp() + "/job/get.do"
		let sucFunc = (data) => {
            if(data && data.success && data.resultObject) {
                this.setState({jobInfo: data.resultObject});
            } else {
                commonUtility.messageWarning(data.msg || "获取任务信息失败", commonUtility.tipTime);
            }
        };
        let errFunc = () => {
            commonUtility.messageWarning("获取任务信息失败", commonUtility.tipTime);
        }
        commonUtil.ajaxRequest(serverUrl, 'GET', params, sucFunc, errFunc, false);
	}

	/* 获取时间规则 */
	requestTimeRule = () => {
		let params = {"jobId":this.props.params.jobId};
		let serverUrl = commonUtil.serverIp() + "/timer/get.do"
		let sucFunc = (data) => {
            if(data && data.success && data.resultObject) {
                this.setState({timeRule: data.resultObject});
            } else {
                commonUtility.messageWarning(data.msg || "获取时间规则失败", commonUtility.tipTime);
            }
        };
        let errFunc = () => {
            commonUtility.messageWarning("获取时间规则失败", commonUtility.tipTime);
        }
        commonUtil.ajaxRequest(serverUrl, 'GET', params, sucFunc, errFunc, false);
	}

	/* 获取执行机器 */
	requestExecutors = () => {
		let params = {"jobId":this.props.params.jobId};
		let serverUrl = commonUtil.serverIp() + "/executor/list.do"
		let sucFunc = (data) => {
            if(data && data.success && data.resultObject) {
            	let executors = data.resultObject;
            	for(var i=0;i<executors.length;i++) {
            		executors[i].key = i;
            		executors[i].effectiveTime = commonUtil.formatYYYY_MM_DD_HH_mm_ss(executors[i].effectiveTime);
            	}
                this.setState({executors: executors});
            } else {
                commonUtility.messageWarning(data.msg || "获取执行机器失败", commonUtility.tipTime);
            }
        };
        let errFunc = () => {
            commonUtility.messageWarning("获取执行机器失败", commonUtility.tipTime);
        }
        commonUtil.ajaxRequest(serverUrl, 'GET', params, sucFunc, errFunc, false);
	}

	render() {
		this.executorColumns = [
			{
				title: '机器Id',
				dataIndex: 'exeId',
				key: 'exeId',
				width:20
			}, {
				title: '机器名称',
				dataIndex: 'exeName',
				key: 'exeName',
				width:20
			}, {
				title: '执行URL',
				dataIndex: 'exeUrl',
				key: 'exeUrl',
				width:20
			}, {
				title: '执行接口',
				dataIndex: 'exeInterface',
				key: 'exeInterface',
				width:20
			}, {
				title: '生效时间',
				dataIndex: 'effectiveTime',
				key: 'effectiveTime',
				width:20
			}
		];

		return (
			<div className={styles.ViewJob}>
				<ProTitle name="查看任务" />
				<ItemTitleName titleName="任务详情" />
				<table className={styles.tableMin}>
					<thead></thead>
					<tbody>
						<tr>
							<td>任务id</td>
							<td>{this.state.jobInfo.jobId}</td>
						</tr>
						<tr>
							<td>任务名称</td>
							<td>{this.state.jobInfo.jobName}</td>
						</tr>
						<tr>
							<td>通知保证</td>
							<td>
								{this.state.jobInfo.executeRule}
							</td>
						</tr>
						<tr>
							<td>超时警告时间</td>
							<td>{this.state.jobInfo.timeout}</td>
						</tr>
						<tr>
							<td>任务描述</td>
							<td>{this.state.jobInfo.jobDesc}</td>
						</tr>
					</tbody>
				</table>
				<ItemTitleName titleName="时间规则" />
				<table className={styles.tableMin}>
					<thead></thead>
					<tbody>
						<tr>
							<td>时间规则</td>
							<td>{this.state.timeRule.cron}</td>
						</tr>
						<tr>
							<td>生效时间</td>
							<td>{this.state.timeRule.effectiveTime}</td>
						</tr>
						<tr>
							<td>未来时间规则</td>
							<td>{this.state.timeRule.futureCron}</td>
						</tr>
						<tr>
							<td>未来生效时间</td>
							<td>{this.state.timeRule.futureEffectiveTime}</td>
						</tr>
					</tbody>
				</table>
				<ItemTitleName titleName="执行机器" />
				<Table className={styles.tableMax} columns={this.executorColumns} dataSource={this.state.executors} bordered />
			</div>
		)
	}
}

export default ViewJob;