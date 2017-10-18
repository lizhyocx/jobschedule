CREATE TABLE `schedule_job` (
  `job_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(60) NOT NULL DEFAULT '' COMMENT '任务名称',
  `execute_select` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '执行机器：0 第一台 1 顺序选择 2 随机选择 3 全部执行',
  `execute_rule` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '通知保证：0 只执行一次 不管是否成功 1 保证成功 当一次执行不成功时，取下一主机执行，如果一直不成功，则到所有主机执行一遍为止 2 保证成功(当上次任务未完成时，不执行，否则跟1一至)',
  `job_desc` varchar(256) NOT NULL DEFAULT '' COMMENT '任务描述',
  `timeout` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '任务执行的超时时间',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '失效时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '1:启用，1：禁用',
  PRIMARY KEY (`job_id`),
  KEY `job_name` (`job_name`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=gbk COMMENT='任务表';

