
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

