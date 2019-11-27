
-- Drop table

-- DROP TABLE tb_cart;

CREATE TABLE tb_cart (
	id serial NOT NULL,
	user_id int4 NULL,
	goods_id int4 NULL,
	goods_name varchar(50) NULL,
	goods_type varchar(50) NULL,
	shopping_count int4 NULL,
	remark varchar(50) NULL,
	create_id int4 NULL,
	create_time timestamp NULL,
	update_id int4 NULL,
	update_time timestamp NULL,
	status int4 NULL,
	CONSTRAINT pk_id PRIMARY KEY (id)
);

INSERT INTO tb_cart (id, user_id, goods_id, goods_name, goods_type, shopping_count, remark, create_id, create_time, update_id, update_time, status) VALUES(7, 9, 5, '炒河粉', '宵夜烧烤', 150, NULL, 9, '2019-11-26 16:22:03.542', 9, '2019-11-26 16:22:16.748', 2);
INSERT INTO tb_cart (id, user_id, goods_id, goods_name, goods_type, shopping_count, remark, create_id, create_time, update_id, update_time, status) VALUES(8, 9, 4, '牛肉火锅', '风味美食', 250, NULL, 9, '2019-11-26 16:22:59.801', 9, '2019-11-26 16:25:41.053', 2);
INSERT INTO tb_cart (id, user_id, goods_id, goods_name, goods_type, shopping_count, remark, create_id, create_time, update_id, update_time, status) VALUES(9, 9, 4, '牛肉火锅', '风味美食', 100, NULL, 9, '2019-11-26 16:28:27.251', 9, '2019-11-26 16:28:53.339', 1);
INSERT INTO tb_cart (id, user_id, goods_id, goods_name, goods_type, shopping_count, remark, create_id, create_time, update_id, update_time, status) VALUES(10, 11, 4, '牛肉火锅', '风味美食', 100, NULL, 11, '2019-11-26 16:29:33.526', 11, '2019-11-26 16:30:43.613', 1);
INSERT INTO tb_cart (id, user_id, goods_id, goods_name, goods_type, shopping_count, remark, create_id, create_time, update_id, update_time, status) VALUES(11, 13, 2, '饼干', '美味早餐', 10, NULL, 13, '2019-11-26 21:11:14.436', NULL, NULL, 2);
INSERT INTO tb_cart (id, user_id, goods_id, goods_name, goods_type, shopping_count, remark, create_id, create_time, update_id, update_time, status) VALUES(12, 13, 4, '牛肉火锅', '美味美食', 10, NULL, 13, '2019-11-26 21:24:01.366', NULL, NULL, 2);
