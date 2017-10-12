// Learn more on how to config.
// - https://github.com/ant-tool/atool-build#配置扩展

const webpack = require('atool-build/lib/webpack');
const fs = require('fs');
const path = require('path');
const glob = require('glob');

module.exports = function (webpackConfig) {
  webpackConfig.babel.plugins.push('transform-runtime');
  webpackConfig.babel.plugins.push(['import', {
    libraryName: 'antd',
    style: true,
  }]);

  // Enable this if you have to support IE8.
  // webpackConfig.module.loaders.unshift({
  //   test: /\.jsx?$/,
  //   loader: 'es3ify-loader',
  // });
  webpackConfig.module.loaders.unshift({ test: /\.(woff|svg|eot|ttf)\??.*$/, loader: 'url-loader?limit=50000&name=[path][name].[ext]'});
  // Parse all less files as css module.
  // webpackConfig.module.loaders.forEach(function(loader, index) {
  //   if (typeof loader.test === 'function' && loader.test.toString().indexOf('\\.less$') > -1) {
  //     loader.test = /\.dont\.exist\.file/;
  //   }
  //   if (loader.test.toString() === '/\\.module\\.less$/') {
  //     loader.test = /\.less$/;
  //   }
  // });

  // Load src/entries/*.js as entry automatically.
  // const files = glob.sync('./src/entries/**/*.js');
  // const newEntries = files.reduce(function(memo, file) {
  //   const name = path.basename(file, '.js');
  //   memo[name] = file;
  //   return memo;
  // }, {});
  // webpackConfig.entry = Object.assign({}, webpackConfig.entry, newEntries);
webpackConfig.module.loaders.forEach(function(loader, index) {
    if (typeof loader.test === 'function' && loader.test.toString().indexOf('\\.less$') > -1) {
      loader.include = /node_modules/;
      loader.test = /\.less$/;
    }
    if (loader.test.toString() === '/\\.module\\.less$/') {
      loader.exclude = /node_modules/;
      loader.test = /\.less$/;
    }
    if (typeof loader.test === 'function' && loader.test.toString().indexOf('\\.css$') > -1) {
      loader.include = /node_modules/;
      loader.test = /\.css$/;
    }
    if (loader.test.toString() === '/\\.module\\.css$/') {
      loader.exclude = /node_modules/;
      loader.test = /\.css$/;
    }
  });
webpackConfig.output={
    path: path.join(__dirname, './dist'),
    filename: '[name].js',
    sourceMapFilename: '[file].map',
    //加这个！
    chunkFilename: './pages/[name].[chunkhash:5].chunk.js',
};
  return webpackConfig;
};
