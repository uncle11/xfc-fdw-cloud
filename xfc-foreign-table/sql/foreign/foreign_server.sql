-- 创建fdw扩展
CREATE EXTENSION IF NOT EXISTS postgres_fdw;
-- 链接cloud_user数据库 创建server及user mapping
CREATE SERVER fdw_user FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host '127.0.0.1', port '5432', dbname 'cloud_user');
CREATE USER MAPPING FOR postgres SERVER fdw_user OPTIONS (USER 'postgres', PASSWORD 'postgres');

--链接cloud_order数据库 创建server及user mapping
CREATE SERVER fdw_order FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host '127.0.0.1', port '5432', dbname 'cloud_order');
CREATE USER MAPPING FOR postgres SERVER fdw_order OPTIONS (USER 'postgres', PASSWORD 'postgres');