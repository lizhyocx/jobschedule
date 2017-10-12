import React, {Component, PropTypes} from 'react';
import {Router, Route, IndexRoute, Link} from 'react-router';
import {Breadcrumb, Row, Col, Button, message, Icon, Select, Radio,Popover} from 'antd';
import styles from './BasePageWithMenu.less';
import mainLayout from '../../components/MainLayout/MainLayout.less';
import SliderMenu from '../../components/SliderMenu/SliderMenu';
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';
const BasePage = React.createClass({
	getInitialState: function() {
    	return {
    		minHeight: (window.innerHeight - 200)+'px'
    	};
    },
    handleResize: function(e) {
    	this.setState({minHeight: (window.innerHeight - 220)+'px'});
  	},
  	componentDidMount: function() {
        addHandler(window,'resize',this.handleResize);
    },
    componentWillUnmount: function() {
        removeHandler(window,'resize',this.handleResize);
    },
    render() {
        let needBg;
        if(this.props.children){
            switch(this.props.children.props.route.path){
                case "/":
                    needBg=<div className={mainLayout['right-bg']}>
                        <div className={mainLayout['top']}></div>
                        <div className={mainLayout['left']}></div>
                        <div className={mainLayout['right']}></div>
                    </div>;
                break;
                default:
                    needBg=<div className={mainLayout['right-bg']}>
                        <div className={mainLayout['top']}></div>
                        <div className={mainLayout['left']}></div>
                        <div className={mainLayout['right']}></div>
                    </div>;
                break;
            }
        }
        return (
    		<Row className={mainLayout['page-content-with-menu']} style={{'minHeight':this.state.minHeight}}>
    			<div className={mainLayout['left-bg']}></div>
    			<div className={mainLayout['left-menu']} style={{'minHeight':this.state.minHeight}}>
    				<SliderMenu pathname={this.props.location.pathname}/>
    			</div>
                <div className={mainLayout['right-content']}>
    			{this.props.children}
                </div>
                {needBg}
    		</Row>
        );
    },
});
export default BasePage;
