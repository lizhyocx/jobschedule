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
	render() {
		this.executorColumns = [
			{
				title: '机器Id',
				dataIndex: 'exeId',
				key: 'exeId',
				width:80
			}, {
				title: '机器名称',
				dataIndex: 'exeName',
				key: 'exeName',
				width:100
			}, {
				title: '执行URL',
				dataIndex: 'exeUrl',
				key: 'exeUrl',
				width:250
			}, {
				title: '执行接口',
				dataIndex: 'exeInterface',
				key: 'exeInterface',
				width:150
			}, {
				title: '生效时间',
				dataIndex: 'effectiveTime',
				key: 'effectiveTime',
				width:150
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
				<Table className={styles.tableMax} columns={this.executorColumns} dataSource={this.state.executors} />
			</div>
		)
	}
}

export default ViewJob;