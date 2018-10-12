
--日志记录表
CREATE TABLE `tbl_sys_operation_log` (
   id int(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
   log_type varchar(100) DEFAULT NULL COMMENT '日志类型',
   log_name varchar(100) DEFAULT NULL COMMENT '日志名称',
   user_id varchar(40) DEFAULT NULL COMMENT '操作用户',
   classname varchar(255) DEFAULT NULL COMMENT '类名称',
   method varchar(100) DEFAULT COMMENT '方法名称',
   request varchar(500) DEFAULT COMMENT '方法请求参数',
   response varchar(500) DEFAULT NULL COMMENT '方法响应结果',
   message varchar(10000) COMMENT '旧值-新值',
   create_date datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1399 DEFAULT CHARSET=utf8 COMMENT='操作日志'