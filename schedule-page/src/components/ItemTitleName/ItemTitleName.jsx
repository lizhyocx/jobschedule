import React, { Component, PropTypes } from 'react';
import styles from './ItemTitleName.less';
import { Row,Col} from 'antd';
const ItemTitleName=React.createClass({
	render(){
		return(
			<div>
				<Row>
			        <Col span={4}>
			                        <div className={styles.boxWrapper +' '+styles.leftName+' '+styles.itemTitle}>
			                            <i className={styles.infoLine}></i>{this.props.titleName}
			                        </div>
			        </Col>
			        <Col span={20}>
			            <div className={styles.boxWrapper} style={{ display:this.props.childHide ? 'none': 'block' }}>
			                {this.props.children?this.props.children:""}
			            </div>
			        </Col>
				</Row>
			</div>

		)
	}

})
export default ItemTitleName;
