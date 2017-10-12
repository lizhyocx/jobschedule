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
                        <Route name="NotFound" breadcrumbName="404" path="/" getComponent={NotFound} />
                    </Route>
                </Route>
            </Router>
        );
    },
});
export default Routes;
