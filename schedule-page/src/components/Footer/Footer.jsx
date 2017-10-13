import React, { PropTypes } from 'react';
import { Router, Route, IndexRoute, Link } from 'react-router';
import styles from './Footer.less';

const Footer = React.createClass({
  render() {
    return (
        <div className={styles['zhk-pro-footer']}>
            <div className={styles['content']}>
                <div className={styles['footer-info']}>
                    <p>Copyright© 个人版权所有</p>
                </div>
            </div>
        </div>
    );
  },
});
export default Footer;