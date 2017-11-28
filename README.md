##jobschedule-定时任务管理系统

后台采用Java开发，数据库使用Mysql，前台页面采用Ant.Design开发。

###工程结构
    jobschedule
        profile----------------打包配置问题件
        schedule-center--------后台核心模块，包括service、dao层实现
        schedule-common--------后台通用模块，包括工具类，基础框架等
        schedule-console-------后台控制模块，包括controller层实现
        schedule-client--------客户端模块，用于客户端引入使用
        schedule-page----------前台页面，采用Ant.Design开发
        sql--------------------数据库脚本
        
###界面截图


###打包命令
    后台：mvn clean package -Denv=dev或mvn clean package -Pdev
    前台：npm run build

