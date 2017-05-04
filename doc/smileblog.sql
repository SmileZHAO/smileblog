
DROP TABLE IF EXISTS t_user;
create table `t_user` (
	`id` varchar(64)  NOT NULL DEFAULT '' COMMENT '主键',
	`name` varchar(128) DEFAULT NULL COMMENT '姓名',
	`login_name` varchar(128) DEFAULT NULL COMMENT '登录姓名',
	`sex` varchar(4) DEFAULT NULL COMMENT '性别  1- 男   2-女',
	`pwd` varchar(128) NOT NULL DEFAULT '' COMMENT '密码',
	`mail` varchar(256) DEFAULT NULL COMMENT '邮箱',
	`introduction` varchar(500) DEFAULT NULL COMMENT '介绍',
    `crt_time` datetime NOT NULL COMMENT '创建时间',
    `mdf_time` datetime NOT NULL COMMENT '修改时间',
    `crt_user` varchar(64) NOT NULL DEFAULT ''  COMMENT '创建人',
    `mdf_user` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
    PRIMARY KEY (`id`)
) ;

DROP TABLE IF EXISTS t_article;
create table t_article(
	id varchar(64)  NOT NULL DEFAULT '' COMMENT '主键',
	user_id varchar(64)  NOT NULL DEFAULT '' COMMENT '用户的id',
	title varchar(64)  NOT NULL DEFAULT '' COMMENT '标题',
	content_markdown text DEFAULT NULL COMMENT 'markdowm内容',
	content_html text DEFAULT NULL COMMENT 'html内容',
	abstract_str varchar(300) DEFAULT NULL COMMENT '摘要',
	is_push  varchar(2) DEFAULT NULL COMMENT '是否发布 1是发布，2未发布',
	is_del  varchar(2) DEFAULT NULL COMMENT '是否删除 1是删除，2未删除',
	comment_num int DEFAULT 0 COMMENT '评论数量',
	visit_num int DEFAULT 0 COMMENT '访问数量',
	crt_time datetime NOT NULL COMMENT '创建时间',
    mdf_time datetime NOT NULL COMMENT '修改时间',
    crt_user varchar(64) NOT NULL DEFAULT ''  COMMENT '创建人',
    mdf_user varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS t_tag;
create table t_tag(
	id varchar(64)  NOT NULL DEFAULT '' COMMENT '主键',
	content_tag varchar(128)  NOT NULL DEFAULT '' COMMENT '标签的内容',
	crt_time datetime NOT NULL COMMENT '创建时间',
    mdf_time datetime NOT NULL COMMENT '修改时间',
    crt_user varchar(64) NOT NULL DEFAULT ''  COMMENT '创建人',
    mdf_user varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS t_article_tag;
create table t_article_tag(
	id varchar(64)  NOT NULL DEFAULT '' COMMENT '主键',
	article_id varchar(64)  NOT NULL DEFAULT '' COMMENT '文章id',
	tag_id varchar(64)  NOT NULL DEFAULT '' COMMENT '文章id',
	crt_time datetime NOT NULL COMMENT '创建时间',
    mdf_time datetime NOT NULL COMMENT '修改时间',
    crt_user varchar(64) NOT NULL DEFAULT ''  COMMENT '创建人',
    mdf_user varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS t_img;
create table t_img(
	id varchar(64)  NOT NULL DEFAULT '' COMMENT '主键',
	old_name varchar(1024)  NOT NULL DEFAULT '' COMMENT '图片原始的名字',
	img_name varchar(1024)  NOT NULL DEFAULT '' COMMENT '图片现在的名字',
	img_suffix varchar(128)  NOT NULL DEFAULT '' COMMENT '图片后缀',
	img_path  varchar(1024)  NOT NULL DEFAULT '' COMMENT '图片存放路径',
	crt_time datetime NOT NULL COMMENT '创建时间',
    mdf_time datetime NOT NULL COMMENT '修改时间',
    crt_user varchar(64) NOT NULL DEFAULT ''  COMMENT '创建人',
    mdf_user varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
    PRIMARY KEY (id)
);



DROP TABLE IF EXISTS t_comment;
create table t_comment(
	id varchar(64)  NOT NULL DEFAULT '' COMMENT '主键',
	article_id varchar(64)  NOT NULL DEFAULT '' COMMENT '文章的id',
	user_id varchar(64)  NOT NULL DEFAULT '' COMMENT '评论用户',
	comment_content text  NOT NULL COMMENT '评论内容',
	crt_time datetime NOT NULL COMMENT '创建时间',
    mdf_time datetime NOT NULL COMMENT '修改时间',
    crt_user varchar(64) NOT NULL DEFAULT ''  COMMENT '创建人',
    mdf_user varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS t_comment_user;
create table t_comment_user(
	id varchar(64)  NOT NULL DEFAULT '' COMMENT '主键',
	user_name varchar(1024)  NOT NULL DEFAULT '' COMMENT '用户姓名',
	email varchar(1024)  NOT NULL DEFAULT '' COMMENT '用户邮箱',
	password varchar(64) NOT NULL DEFAULT '' COMMENT '用来标识是否是该用户',
	crt_time datetime NOT NULL COMMENT '创建时间',
    mdf_time datetime NOT NULL COMMENT '修改时间',
    crt_user varchar(64) NOT NULL DEFAULT ''  COMMENT '创建人',
    mdf_user varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS t_visit_user;
create table t_visit_user(
	id varchar(64)  NOT NULL DEFAULT '' COMMENT '主键',
	url varchar(256)  NOT NULL DEFAULT '' COMMENT '访问的链接',
	article_id varchar(64)  NOT NULL DEFAULT '' COMMENT '文章的id',
	local_name varchar(256)  NOT NULL DEFAULT '' COMMENT '主机的名字',
	ip_address varchar(128)  NOT NULL DEFAULT '' COMMENT '访问者的ip地址',
 	crt_time datetime NOT NULL COMMENT '创建时间',
    mdf_time datetime NOT NULL COMMENT '修改时间',
    crt_user varchar(64) NOT NULL DEFAULT ''  COMMENT '创建人',
    mdf_user varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
    PRIMARY KEY (id)
);



