
-- Drop table

-- DROP TABLE tb_order;

CREATE TABLE tb_order (
	id serial NOT NULL,
	user_id int4 NULL,
	goods_id int4 NULL,
	goods_name varchar(50) NULL,
	goods_type varchar(50) NULL,
	shopping_count int4 NULL,
	address varchar(50) NULL,
	status int4 NULL,
	remark varchar(50) NULL,
	create_id varchar NULL,
	create_time timestamp NULL,
	update_id int4 NULL,
	update_time timestamp NULL,
	CONSTRAINT pk_oid PRIMARY KEY (id)
);
