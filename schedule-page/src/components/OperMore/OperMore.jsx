import React from 'react';
import { Menu, Dropdown, Icon } from 'antd';

class OperMore extends React.Component {
	constructor(params) {
		super(params);
	}

	render() {
		let editHref = "#/JobList/EditJob/" + this.props.jobId;
		let statusHtml;
		if(Object.is(this.props.status, '有效')) {//有效
			statusHtml = <Menu.Item><a href="http://www.tmall.com/">禁用</a></Menu.Item>
		} else if(Object.is(this.props.status, '无效')) {//无效
			statusHtml = <Menu.Item><a href="http://www.tmall.com/">启用</a></Menu.Item>
		}
		let timeRuleHref = "#/JobList/TimeRule/" + this.props.jobId;
		let executorHref = "#/JobList/Executor/" + this.props.jobId;
		let viewJobHref = "#/JobList/ViewJob/" + this.props.jobId;
		const menu = (
			<Menu>
				<Menu.Item>
			      <a href={viewJobHref}>查看</a>
			    </Menu.Item>
			    <Menu.Item>
			      <a href={editHref}>修改</a>
			    </Menu.Item>
			    {statusHtml}
			    <Menu.Item>
			      <a href={timeRuleHref}>设置时间规则</a>
			    </Menu.Item>
			    <Menu.Item>
			      <a href={executorHref}>设置执行机器</a>
			    </Menu.Item>
			</Menu>
		);
		return (
			<Dropdown overlay={menu}>
			    <a className="ant-dropdown-link">
			      操作 <Icon type="down" />
			    </a>
			</Dropdown>
		)
	}
}

export default OperMore;