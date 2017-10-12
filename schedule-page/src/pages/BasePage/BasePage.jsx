import React, {Component, PropTypes} from 'react';
import {Row} from 'antd';
import styles from './BasePage.less';
import mainLayout from '../../components/MainLayout/MainLayout.less';
import SliderMenu from '../../components/SliderMenu/SliderMenu';
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';
const BasePage = React.createClass({
    render() {
        return (
        	<div className={mainLayout['zhk-pro-body']}>
        		<Header />
        			{this.props.children}
            	<Footer />
            </div>
        );
    },
});
export default BasePage;
