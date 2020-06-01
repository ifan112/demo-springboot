-- 创建 module 表
CREATE TABLE `module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `group_id` varchar(64) NOT NULL,
  `artifact_id` varchar(64) NOT NULL,
  `version` varchar(64) NOT NULL,
  `packing` varchar(64) NOT NULL,
  `parent_module_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=772 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `module_dep` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module_id` int(11) NOT NULL,
  `group_id` varchar(64) NOT NULL,
  `artifact_id` varchar(64) NOT NULL,
  `version` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6034 DEFAULT CHARSET=utf8mb4;
