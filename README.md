##jobschedule


###打包命令
开发环境：mvn clean package -Denv=dev或mvn clean package -Pdev

###jobSchedule---quartz集群测试
1、采用数据库方式存储job，系统启动自动恢复
       补偿机制：短时间（20s）内，不会有太多的任务执行。
       若间隔时间很长时，如何补偿，待测试？？
2、通过判断quartz库中是否存在任务，来决定是否需要重新加载定时任务（集群中可以使用一台机器加载即可）
3、大量定时任务时，存在延迟，不适用于对执行时间高精准的任务。