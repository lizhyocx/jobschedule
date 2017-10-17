import React, { PropTypes } from 'react';
import { Router, Route, IndexRoute, Link, hashHistory } from 'react-router';
import BasePage from '../pages/BasePage/BasePage';
import BasePageWithMenu from '../pages/BasePageWithMenu/BasePageWithMenu';


import '../base/common-service.js';
import '../base/sessionstorage.js';
import '../base/utility.js';
import '../base/common.js';

const NotFound = (location, cb) => {
    require.ensure([], require => {
        cb(null, require('../pages/NotFound/NotFound'))
    },'NotFound');
};

const AddJob = (location, cb) => {
    require.ensure([], require => {
        cb(null, require('../pages/AddJob/AddJob'))
    }, 'AddJob');
}

const JobList = (location, cb) => {
    require.ensure([], require => {
        cb(null, require('../pages/JobList/JobList'))
    }, 'JObList');
}

const AddTimeRule = (location, cb) => {
    require.ensure([], require => {
        cb(null, require('../pages/JobList/AddTimeRule'))
    }, 'AddTimeRule');
}


const Routes = React.createClass({
    render() {
        let updatePage = function() {
            var cur = this.router.routes;
            document.title = cur[cur.length - 1].breadcrumbName;
            window.scrollTo(0, 0);
        };
        return (
            <Router history={hashHistory} onUpdate={updatePage}>
                <Route name="app" component={BasePage}>
                    <Route name="app" component={BasePageWithMenu}>
                    <Route name="IndexPage" breadcrumbName="首页" path="/" getComponent={NotFound} />
                        <Route name="NotFound" breadcrumbName="404" path="/NotFound" getComponent={NotFound} />
                        <Route name="AddJob" breadcrumbName="添加任务" path="/AddJob" getComponent={AddJob} />
                        <Route name="JobList" breadcrumbName="任务列表" path="/JobList" getComponent={JobList} />
                        <Route name="EditJob" breadcrumbName="修改任务" path="/JobList/EditJob/:jobId" getComponent={AddJob} />
                        <Route name="AddTimeRule" breadcrumbName="设置时间规则" path="/JobList/AddTimeRule/:jobId" getComponent={AddTimeRule} />
                    </Route>
                </Route>
            </Router>
        );
    },
});
export default Routes;
