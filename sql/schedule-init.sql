DROP TABLE IF EXISTS SCHEDULE_JOB;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(60) NOT NULL DEFAULT '' COMMENT '任务名称',
  `execute_select` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '执行机器：0 第一台 1 顺序选择 2 随机选择 3 全部执行',
  `execute_rule` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '通知保证：0 只执行一次 不管是否成功 1 保证成功 当一次执行不成功时，取下一主机执行，如果一直不成功，则到所有主机执行一遍为止 2 保证成功(当上次任务未完成时，不执行，否则跟1一至)',
  `job_desc` varchar(256) NOT NULL DEFAULT '' COMMENT '任务描述',
  `timeout` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '任务执行的超时时间',
  `create_time` bigint(13) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` bigint(13) unsigned NOT NULL DEFAULT '0' COMMENT '修改时间',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '1:启用，0：禁用',
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `job_name` (`job_name`) USING BTREE
) ENGINE=InnoDB COMMENT='任务表';

DROP TABLE IF EXISTS schedule_executor;
CREATE TABLE `schedule_executor` (
  `exe_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '执行者ID',
  `job_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '执行者所属任务ID',
  `exe_name` varchar(60) NOT NULL DEFAULT '' COMMENT '执行者名称',
  `exe_url` varchar(60) NOT NULL DEFAULT '' COMMENT '执行者IP',
  `exe_interface` varchar(60) DEFAULT NULL,
  `status` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态 0 无效 1 有效 2未来有效',
  `effective_time` bigint(13) unsigned NOT NULL DEFAULT '0' COMMENT '生效时间',
  `create_time` bigint(13) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` bigint(13) unsigned NOT NULL DEFAULT '0' COMMENT '修改时间',
  PRIMARY KEY (`exe_id`),
  KEY `job_id` (`job_id`),
  KEY `effective_time` (`effective_time`)
) ENGINE=InnoDB COMMENT='执行机器表';

DROP TABLE IF EXISTS schedule_timer;
CREATE TABLE `schedule_timer` (
  `timer_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '时间规则ID',
  `job_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '时间规则所属任务ID',
  `cron` varchar(60) NOT NULL DEFAULT '' COMMENT '时间规则 采用Quartz的cron的时间规则定义 ',
  `effective_time` bigint(13) unsigned NOT NULL DEFAULT '0' COMMENT '生效时间',
  `create_time` bigint(13) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` bigint(13) unsigned NOT NULL DEFAULT '0' COMMENT '修改时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态 0 无效 1 有效 2未来有效 一个定时任务只能允许一条有效时间规则',
  PRIMARY KEY (`timer_id`),
  UNIQUE KEY `idx_job_status` (`job_id`,`status`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB COMMENT='任务时间规则表';

DROP TABLE IF EXISTS schedule_job_log;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `job_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '本条日志所属任务ID',
  `executor_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '本条日志执行者ID',
  `exe_url` varchar(60) DEFAULT NULL COMMENT '执行url',
  `exe_interface` varchar(60) DEFAULT NULL COMMENT '执行接口',
  `notify_start` bigint(13) unsigned NOT NULL DEFAULT '0' COMMENT '任务通知时间',
  `notify_end` bigint(13) unsigned DEFAULT '0' COMMENT '任务通知返回时间',
  `finished` bigint(13) unsigned DEFAULT '0' COMMENT '任务执行结束时间',
  `ret_msg` varchar(125) DEFAULT '' COMMENT '执行返回信息',
  `notify_status` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '通知状态:0 未通知1 已通知－返回成功2 已通知－返回失败',
  `return_status` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '执行状态:0 未执行1 成功2 执行失败',
  `alert_times` int(2) NOT NULL DEFAULT '0' COMMENT '警告发送次数',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`),
  KEY `notify_start` (`notify_start`),
  KEY `query_log_idx` (`job_id`,`notify_status`,`return_status`)
) ENGINE=InnoDB COMMENT='任务执行日志表';







