/*
 Navicat Premium Data Transfer

 Source Server         : localhostPost
 Source Server Type    : PostgreSQL
 Source Server Version : 130006
 Source Host           : localhost:5432
 Source Catalog        : cloud_order
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 130006
 File Encoding         : 65001

 Date: 22/12/2023 16:12:00
*/


-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_order";
CREATE TABLE "public"."tb_order" (
  "id" int8 NOT NULL,
  "user_id" int8 NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default",
  "price" int8 NOT NULL,
  "num" int4
)
;
COMMENT ON COLUMN "public"."tb_order"."id" IS '订单id';
COMMENT ON COLUMN "public"."tb_order"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."tb_order"."name" IS '商品名称';
COMMENT ON COLUMN "public"."tb_order"."price" IS '商品价格';
COMMENT ON COLUMN "public"."tb_order"."num" IS '商品数量';

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO "public"."tb_order" VALUES (101, 1, 'Apple 苹果 iPhone 12 ', 699900, 1);
INSERT INTO "public"."tb_order" VALUES (102, 2, '雅迪 yadea 新国标电动车', 209900, 1);
INSERT INTO "public"."tb_order" VALUES (103, 3, '骆驼（CAMEL）休闲运动鞋女', 43900, 1);
INSERT INTO "public"."tb_order" VALUES (104, 4, '小米10 双模5G 骁龙865', 359900, 1);
INSERT INTO "public"."tb_order" VALUES (105, 5, 'OPPO Reno3 Pro 双模5G 视频双防抖', 299900, 1);
INSERT INTO "public"."tb_order" VALUES (106, 6, '美的（Midea) 新能效 冷静星II ', 544900, 1);
INSERT INTO "public"."tb_order" VALUES (107, 2, '西昊/SIHOO 人体工学电脑椅子', 79900, 1);
INSERT INTO "public"."tb_order" VALUES (108, 3, '梵班（FAMDBANN）休闲男鞋', 31900, 1);

-- ----------------------------
-- Indexes structure for table tb_order
-- ----------------------------
CREATE INDEX "username" ON "public"."tb_order" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table tb_order
-- ----------------------------
ALTER TABLE "public"."tb_order" ADD CONSTRAINT "tb_order_pkey" PRIMARY KEY ("id");
