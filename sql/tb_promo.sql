
-- Drop table

-- DROP TABLE tb_promo;

CREATE TABLE tb_promo (
	id serial NOT NULL,
	status int4 NOT NULL,
	promoname varchar(64) NULL,
	start_time timestamp NULL,
	end_time timestamp NULL,
	item_id int4 NULL,
	promo_item_price numeric NULL
);

INSERT INTO tb_promo (id, status, item_id, promoname, start_time, end_time, promo_item_price) VALUES(2, 2, 2, '圣诞大促销', '2019-12-04 21:46:19.000', '2019-12-04 19:46:19.000', 10);
INSERT INTO tb_promo (id, status, item_id, promoname, start_time, end_time, promo_item_price) VALUES(1, 2, 4, '年终大降价', '2019-12-03 19:46:19.000', '2019-12-03 20:33:19.000', 18.00);
INSERT INTO tb_promo (id, status, item_id, promoname, start_time, end_time, promo_item_price) VALUES(3, 2, 6, '春节大促销', '2019-12-03 19:46:19.000', '2019-12-04 19:46:19.000', 15);
