/*
 Navicat Premium Data Transfer

 Source Server         : localhostPost
 Source Server Type    : PostgreSQL
 Source Server Version : 130006
 Source Host           : localhost:5432
 Source Catalog        : cloud_user
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 130006
 File Encoding         : 65001

 Date: 22/12/2023 16:10:08
*/


-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_user";
CREATE TABLE "public"."tb_user" (
  "id" int8 NOT NULL,
  "username" varchar(100) COLLATE "pg_catalog"."default",
  "address" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_user"."username" IS '收件人';
COMMENT ON COLUMN "public"."tb_user"."address" IS '地址';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO "public"."tb_user" VALUES (1, '柳岩', '湖南省衡阳市');
INSERT INTO "public"."tb_user" VALUES (2, '文二狗', '陕西省西安市');
INSERT INTO "public"."tb_user" VALUES (3, '华沉鱼', '湖北省十堰市');
INSERT INTO "public"."tb_user" VALUES (4, '张必沉', '天津市');

-- ----------------------------
-- Indexes structure for table tb_user
-- ----------------------------
CREATE INDEX "username" ON "public"."tb_user" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table tb_user
-- ----------------------------
ALTER TABLE "public"."tb_user" ADD CONSTRAINT "tb_user_pkey" PRIMARY KEY ("id");
