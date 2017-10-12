import React, { PropTypes } from 'react';
import { Menu, Icon,Popover } from 'antd';
import { Router, Route, IndexRoute } from 'react-router';
import styles from './Header.less';

import '../../base/sessionstorage.js';
const Header = React.createClass({
    getInitialState: function() {
        return {
            
        };
    },

    componentWillMount(){
    },

    
    render() {
        
        return (
            <div className={styles['zhk-pro-header']}>
                <div className={styles['content']}>
                    <div className={styles['left-logo']}>
                        <a href="/"></a>
                    </div>
                    <div className={styles['right-option']}>
                        
                    </div>
                </div>
            </div>
        );
    },
});
export default Header;