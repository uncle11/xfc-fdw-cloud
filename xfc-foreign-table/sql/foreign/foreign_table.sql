-- 创建外部表 f_user_info，获取用户信息
CREATE FOREIGN TABLE f_user_info (
  "id" int8,
  "username" varchar(100),
  "address" varchar(255)
)
SERVER fdw_user
OPTIONS (SCHEMA_NAME 'public', TABLE_NAME 'tb_user');
-- 创建 f_order_info,获取订单信息
CREATE FOREIGN TABLE f_order_info (
  "id" int8,
  "user_id" int8,
  "name" varchar(100),
  "price" int8,
  "num" int4
)
SERVER fdw_order
OPTIONS (SCHEMA_NAME 'public', TABLE_NAME 'tb_order');

