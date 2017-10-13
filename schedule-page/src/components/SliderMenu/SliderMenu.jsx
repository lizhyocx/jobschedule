import React, { PropTypes } from 'react';
import { Router, Route, IndexRoute, Link } from 'react-router';
import styles from './SliderMenu.less';
import { Menu, Icon, Switch } from 'antd';
const SubMenu = Menu.SubMenu;

const SliderMenu = React.createClass({
    getInitialState() {
        return {
            theme: 'light',
            current: this.getCurrent(),
            defaultOpenKeys: ['rwgl', 'ddgl'],
            visible: false,
        };
    },

    componentWillReceiveProps(nextProps) {
        this.setState({
            current: this.getCurrent(nextProps),
        });
    },

    /**
     * 生命周期函数调用
     */
    componentDidMount() {
        /*this.menueRightRequest();*/
    },

    getCurrent(nextProps) {
        let current;
        if (this.props && this.props.pathname) {
            current = this.props.pathname.split('/')[1];
            if (current === '') {
                current = 'index';
            }
        }
        if (nextProps && nextProps.pathname) {
            current = nextProps.pathname.split('/')[1];
            if (current === '') {
                current = 'index';
            }
        }
        return current;
    },

    handleClick(e) {
        this.setState({
            current: e.key,
        });
        console.log(e.key)
    },

    render() {
        return (
            <div className={styles['slider-menu'] + ' ' + 'sm-menu'}>
                <Menu theme={this.state.theme} onClick={this.handleClick} style={{ width: 200 }} openKeys={this.state.defaultOpenKeys} selectedKeys={[this.state.current]} mode="inline">
                    <Menu.Item key="index">
                        <Link to="/">
                            <span>
                                <Icon type="home" />
                                <span>首页</span>
                            </span>
                        </Link>
                    </Menu.Item>
                    <SubMenu
                        key="rwgl"
                        title={
                            <span>
                                <Icon type="bars" />
                                <span>任务管理</span>
                            </span>
                        }>
                        <Menu.Item key="JobList">
                            <Link to="/NotFound">任务列表</Link>
                        </Menu.Item>
                        <Menu.Item key="AddJob">
                            <Link to="/AddJob">添加任务</Link>
                        </Menu.Item>
                        <Menu.Item key="ImportJob">
                            <Link to="/ImportJob">导入任务</Link>
                        </Menu.Item>
                    </SubMenu>
                    <SubMenu
                        key="ddgl"
                        title={
                            <span>
                                <Icon type="clock-circle" />
                                <span>调度管理</span>
                            </span>
                        }>
                        <Menu.Item key="ScheduleList">
                            <Link to="/AddJob">任务列表</Link>
                        </Menu.Item>
                    </SubMenu>
                    
                </Menu>
            </div>
        );
    },
});
export default SliderMenu;
