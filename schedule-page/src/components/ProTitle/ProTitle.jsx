import React, { Component, PropTypes } from 'react';
import styles from './ProTitle.less';
import { Alert } from 'antd';
const ProTitle = React.createClass({
  render() {
    return (
      <div className={styles.titleContent +' '+this.props.className}>
        <div className={styles.title}>{this.props.name}</div>
      </div>
    );
  },
});
export default ProTitle;