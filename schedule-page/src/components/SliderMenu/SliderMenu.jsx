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
    },

    render() {
        return (
            <div className={styles['zhk-slider-menu'] + ' ' + 'zhk-sm-menu'}>
                <Menu theme={this.state.theme} onClick={this.handleClick} style={{ width: 200 }} openKeys={this.state.defaultOpenKeys} selectedKeys={[this.state.current]} mode="inline">
                    <Menu.Item key="index">
                        <Link to="/">
                            <span>
                                <span>首页</span>
                            </span>
                        </Link>
                    </Menu.Item>
                    <SubMenu
                        key="rwgl"
                        title={
                            <span>
                                
                                <span>任务管理</span>
                            </span>
                        }>
                        <Menu.Item key="1">
                            <Link to="/">任务列表</Link>
                        </Menu.Item>
                        <Menu.Item key="2">
                            <Link to="/">添加任务</Link>
                        </Menu.Item>
                        <Menu.Item key="3">
                            <Link to="/">导入任务</Link>
                        </Menu.Item>
                    </SubMenu>
                    <SubMenu
                        key="ddgl"
                        title={
                            <span>
                                
                                <span>调度管理</span>
                            </span>
                        }>
                        <Menu.Item key="1">
                            <Link to="/">任务列表</Link>
                        </Menu.Item>
                    </SubMenu>
                    
                </Menu>
            </div>
        );
    },
});
export default SliderMenu;
