import React, { PropTypes } from 'react';
import { Menu, Icon,Popover } from 'antd';
import { Router, Route, IndexRoute } from 'react-router';
import styles from './Header.less';
import logo from '../../images/logo.png';

const Header = React.createClass({
    getInitialState: function() {
        return {
            logo:logo
        };
    },

    componentWillMount(){
    },

    
    render() {
        
        return (
            <div className={styles['pro-header']}>
                <div className={styles['content']}>
                    <div className={styles['left-logo']}>
                        <a href="/"><img src={this.state.logo} /></a>
                    </div>
                    <div className={styles['title']}>定时任务管理系统</div>
                </div>
            </div>
        );
    },
});
export default Header;