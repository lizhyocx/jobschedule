import React, { PropTypes } from 'react';
import { Router, Route, IndexRoute, Link } from 'react-router';
import styles from './Footer.less';

const Footer = React.createClass({
  render() {
    return (
        <div className={styles['zhk-pro-footer']}>
            <div className={styles['content']}>
                <div className={styles['footer-info']}>
                    <p>Copyright © Beijing Yunzong Co., Ltd., All Rights Reserved 京ICP备15054615号</p>
                    <p>北京云纵信息技术有限公司版权所有</p>
                </div>
            </div>
        </div>
    );
  },
});
export default Footer;