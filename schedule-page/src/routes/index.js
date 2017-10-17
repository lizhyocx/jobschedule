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

const TimeRule = (location, cb) => {
    require.ensure([], require => {
        cb(null, require('../pages/JobList/TimeRule'))
    }, 'TimeRule');
}

const Executor = (location, cb) => {
    require.ensure([], require => {
        cb(null, require('../pages/JobList/Executor'))
    }, 'Executor');
}

const ViewJob = (location, cb) => {
    require.ensure([], require => {
        cb(null, require('../pages/JobList/ViewJob'))
    }, 'ViewJob');
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
                        <Route name="TimeRule" breadcrumbName="设置时间规则" path="/JobList/TimeRule/:jobId" getComponent={TimeRule} />
                        <Route name="Executor" breadcrumbName="设置执行机器" path="/JobList/Executor/:jobId" getComponent={Executor} />
                        <Route name="ViewJob" breadcrumbName="查看任务" path="/JobList/ViewJob/:jobId" getComponent={ViewJob} />
                    </Route>
                </Route>
            </Router>
        );
    },
});
export default Routes;
