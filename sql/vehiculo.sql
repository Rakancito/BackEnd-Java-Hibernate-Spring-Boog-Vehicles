/*
 Navicat Premium Data Transfer
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vehiculo
-- ----------------------------
DROP TABLE IF EXISTS `vehiculo`;
CREATE TABLE `vehiculo`  (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `placa` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT '',
  `entrada` bigint(20) NULL DEFAULT 0,
  `salida` bigint(20) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vehiculo
-- ----------------------------
INSERT INTO `vehiculo` VALUES (1, 'JLK12', 1709664895853, 1709687657611);
INSERT INTO `vehiculo` VALUES (2, 'MZALK2', 1709686681460, 1709687631868);
INSERT INTO `vehiculo` VALUES (3, 'MBALK2', 1709686549176, 1709687672984);

-- ----------------------------
-- Table structure for vehiculo_oficial
-- ----------------------------
DROP TABLE IF EXISTS `vehiculo_oficial`;
CREATE TABLE `vehiculo_oficial`  (
  `placa` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `fecha_registro` bigint(20) NOT NULL,
  PRIMARY KEY (`placa`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vehiculo_oficial
-- ----------------------------
INSERT INTO `vehiculo_oficial` VALUES ('MZALK2', 1709687604561);

-- ----------------------------
-- Table structure for vehiculo_residente
-- ----------------------------
DROP TABLE IF EXISTS `vehiculo_residente`;
CREATE TABLE `vehiculo_residente`  (
  `placa` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `fecha_registro` bigint(20) NOT NULL,
  `tiempo` bigint(20) NOT NULL,
  PRIMARY KEY (`placa`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vehiculo_residente
-- ----------------------------
INSERT INTO `vehiculo_residente` VALUES ('JLK12', 1709664875388, 379);

SET FOREIGN_KEY_CHECKS = 1;
