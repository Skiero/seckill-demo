
-- Drop table

-- DROP TABLE tb_goods;

CREATE TABLE tb_goods (
	id serial NOT NULL,
	g_name varchar(50) NULL,
	g_index_code varchar(50) NULL,
	g_type varchar(50) NULL,
	g_count int4 NULL,
	status int4 NULL,
	remark varchar(50) NULL,
	create_id int4 NULL,
	create_time timestamp NULL,
	update_id int4 NULL,
	update_time timestamp NULL,
	g_price numeric(10) NULL,
	g_image_url varchar(100) NULL,
	sales int4 NULL,
	CONSTRAINT pk_gid PRIMARY KEY (id)
);


INSERT INTO tb_goods (id, g_name, g_index_code, g_type, g_price, g_count, sales, status, remark, create_id, create_time, update_id, update_time, g_image_url) VALUES(1, '可口可乐', 'bfca69d5d3974c478d7cfe1d45c759eb', '风味美食', 20, 50000000, 85, 1, '15', '1', '2019-11-22 17:40:41.000', 11, '2019-11-22 17:40:41.029', 'https://www.baidu.com/img/bd_logo1.png');
INSERT INTO tb_goods (id, g_name, g_index_code, g_type, g_price, g_count, sales, status, remark, create_id, create_time, update_id, update_time, g_image_url) VALUES(6, '雪碧', '8e51fe9d84004308b0ea626cb93ab0c5', '饮料', 20, 499990, 25, 1, '18', '15', '2019-12-03 11:17:45.958', NULL, '2019-12-03 11:17:45.958', 'https://www.baidu.com/img/bd_logo1.png');
INSERT INTO tb_goods (id, g_name, g_index_code, g_type, g_price, g_count, sales, status, remark, create_id, create_time, update_id, update_time, g_image_url) VALUES(2, '饼干', '1aebbd2e70b74e84a71dc172ee439f46', '美味早餐', 20, 498740, 0, 1, '11', '1', '2019-11-22 19:19:32.452', 1, '2019-11-22 19:19:32.451', 'https://www.baidu.com/img/bd_logo1.png');
INSERT INTO tb_goods (id, g_name, g_index_code, g_type, g_price, g_count, sales, status, remark, create_id, create_time, update_id, update_time, g_image_url) VALUES(4, '牛肉火锅', '134ddf55aa704405a6758a616a3f1bd3', '风味美食', 20, 44699, 5301, 1, '17', '1', '2019-11-22 19:51:53.872', 1, '2019-11-22 19:51:53.871', 'https://www.baidu.com/img/bd_logo1.png');
INSERT INTO tb_goods (id, g_name, g_index_code, g_type, g_price, g_count, sales, status, remark, create_id, create_time, update_id, update_time, g_image_url) VALUES(3, '早餐奶', 'c1ea832020e14cc5a4b71576c202d371', '风味早餐', 20, 99800, 299, 1, '15', '1', '2019-11-22 19:21:55.053', 1, '2019-11-22 19:21:55.053', 'https://www.baidu.com/img/bd_logo1.png');
INSERT INTO tb_goods (id, g_name, g_index_code, g_type, g_price, g_count, sales, status, remark, create_id, create_time, update_id, update_time, g_image_url) VALUES(5, '炒河粉', '2b918b874aad4620aeac96d091c83587', '宵夜烧烤', 20, 70000, 1020, 1, '19', '1', '2019-11-23 11:52:39.335', 1, '2019-11-23 11:52:39.334', 'https://www.baidu.com/img/bd_logo1.png');
