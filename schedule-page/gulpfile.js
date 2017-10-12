/**
 * 组件安装
 * npm install gulp-imagemin gulp-sass gulp-replace gulp-minify-css gulp-rename gulp-concat gulp-clean gulp-livereload --save-dev
 */

// 引入 gulp及组件
var gulp = require('gulp'), //基础库
    md5 = require('gulp-md5-plus'),
    gulpSequence = require('gulp-sequence'),
    clean = require('gulp-clean'), //清空文件夹
    cheerio = require('gulp-cheerio'),
    replace = require('gulp-replace'),
    gulpif = require('gulp-if'),
    run = require('gulp-run');
var srcDst = './dist/';
var relDst = './dist/';
var opts;
var CDN = function() {
    var temp = false;
    if (!!opts.cdnUrl) {
        temp = true;
    }
    return temp;
};
// 清空图片、样式、js
gulp.task('clean', function() {
    return gulp.src([relDst], {
            read: false
        })
        .pipe(clean({
            force: true
        }));
});
gulp.task('build', function() {
    return run('npm run build').exec()
});
gulp.task('repalceJquery', function(done) {
    return gulp.src(srcDst + '*.html')
        .pipe(cheerio({
            run: function($) {
                $('script[repalce]').remove();
                $('body').append('<script src="./jquery.min.js"></script>');
            },
            parserOptions: {
                decodeEntities: false
            }
        }))
        .pipe(gulp.dest(relDst))
});
gulp.task('addVersionTime', function(done) {
    return gulp.src(srcDst + '*.html')
        .pipe(cheerio({
            run: function($) {
                var time = new Date();
                var temp ='<div id="version-time" style="position:fixed;bottom:0;width:100%;text-align:center;color:transparent">'+time.getTime()+'</div>';
                $('body').append(temp);
            },
            parserOptions: {
                decodeEntities: false
            }
        }))
        .pipe(gulp.dest(relDst))
});
gulp.task('copyjs', function(done) {
    return gulp.src('./src/base/jquery.min.js')
        .pipe(gulp.dest(relDst))
});
gulp.task('md5:js', function() {
    return gulp.src([srcDst + '*.js','!'+srcDst+'.chunk.js'])
        .pipe(md5(10, srcDst + '*.html'))
        .pipe(gulp.dest(relDst))
});
gulp.task('md5:css', function(done) {
    return gulp.src(srcDst + '*.css')
        .pipe(md5(10, srcDst + '*.html'))
        .pipe(gulp.dest(relDst))
});
gulp.task('copy', function(done) {
    return gulp.src(['robots*.txt'])
        .pipe(gulp.dest(relDst))
});

gulp.task('replaceCDN', function() {
    return gulp.src(srcDst + '*.html')
        .pipe(gulpif(CDN, replace('href="./', 'href="' + opts.cdnUrl)))
        .pipe(gulpif(CDN, replace('src="./', 'src="' + opts.cdnUrl)))
        .pipe(gulp.dest(relDst))
});

//拷贝browser文件夹
gulp.task('copy:browser', function() {
    return gulp.src('./src/browser/**/**.*')
        .pipe(gulp.dest(relDst + 'browser/'));
});

gulp.task('copy:favicon', function() {
    return gulp.src('./*.ico')
        .pipe(gulp.dest(relDst))
});
//获取配置文件
gulp.task('getOptsDev', function() {
    opts = require('./profile/dev.json');
    console.log(opts);
});
gulp.task('getOptsIdc_product', function() {
    opts = require('./profile/idc_product.json');
    console.log(opts);
});
gulp.task('getOptsTest', function() {
    opts = require('./profile/test.json');
    console.log(opts);
});



gulp.task('default', gulpSequence("clean", "getOptsIdc_product", "build", 'repalceJquery', 'copyjs', 'md5:js', 'md5:css', 'copy','replaceCDN','addVersionTime','copy:browser','copy:favicon'));
gulp.task('prod', gulpSequence("clean", "getOptsIdc_product", "build", 'repalceJquery', 'copyjs', 'md5:js', 'md5:css', 'copy','replaceCDN','addVersionTime','copy:browser','copy:favicon'));
gulp.task('dev', gulpSequence("clean", "getOptsDev", "build", 'repalceJquery', 'copyjs', 'md5:js', 'md5:css', 'copy','replaceCDN','addVersionTime','copy:browser','copy:favicon'));
gulp.task('test', gulpSequence("clean", "getOptsTest", "build", 'repalceJquery', 'copyjs', 'md5:js', 'md5:css', 'copy','replaceCDN','addVersionTime','copy:browser','copy:favicon'));
