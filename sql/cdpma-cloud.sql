DROP DATABASE IF EXISTS `cdpma-cloud`;

CREATE DATABASE  `cdpma-cloud` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `cdpma-cloud`;

DROP TABLE IF EXISTS cdpma_operator;
CREATE TABLE cdpma_operator (
                                operator_id         BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '操作员ID',
                                operator_name       VARCHAR(64)     DEFAULT ''                   COMMENT '操作员名称',
                                operator_nickname   VARCHAR(64)     DEFAULT NULL                 COMMENT '操作员昵称',
                                operator_id_card    VARCHAR(20)     DEFAULT NULL                 COMMENT '操作员身份证',
                                operator_phone      VARCHAR(20)     DEFAULT NULL                 COMMENT '操作员电话',
                                operator_gender     char(1)         DEFAULT 'O'                  COMMENT '操作员性别',
                                operator_age        INT             DEFAULT NULL                 COMMENT '操作员年龄',
                                operator_email      VARCHAR(255)    DEFAULT NULL                 COMMENT '操作员邮箱',
                                operator_role       VARCHAR(50)     DEFAULT NULL                 COMMENT '操作员角色',
                                operator_region     VARCHAR(100)    DEFAULT NULL                 COMMENT '操作员所属大区',
                                created_at          DATETIME        DEFAULT NULL                 COMMENT '操作员创建时间',
                                is_disabled         BOOLEAN         DEFAULT FALSE                COMMENT '是否停用',
                                password_hash       VARCHAR(255)    DEFAULT NULL                 COMMENT '操作员密码（哈希加盐）',
                                PRIMARY KEY (operator_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作员表';

DROP TABLE IF EXISTS cdpma_operator_tag;
CREATE TABLE cdpma_operator_tag(
                                    operator_id       BIGINT(20)      NOT NULL                     COMMENT '操作员ID',
                                    tag_id            BIGINT(20)     NOT NULL                     COMMENT '标签ID',
                                    tag_name          VARCHAR(64)     NOT NULL                     COMMENT '标签名称',
                                    PRIMARY KEY (operator_id, tag_id)
)

DROP TABLE IF EXISTS cdpma_menu;
CREATE TABLE cdpma_menu (
                            menu_id              BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '菜单ID',
                            menu_name            VARCHAR(128)    NOT NULL                     COMMENT '菜单名称',
                            created_by           BIGINT(20)      NOT NULL                     COMMENT '创建者ID',
                            created_at           DATETIME        NOT NULL                     COMMENT '创建时间',
                            updated_by           BIGINT(20)      DEFAULT NULL                 COMMENT '更新者ID',
                            updated_at           DATETIME        DEFAULT NULL                 COMMENT '更新时间',
                            is_disabled          BOOLEAN         DEFAULT FALSE                COMMENT '是否停用',
                            sort_order           INT(4)          DEFAULT 0                    COMMENT '菜单排序',
                            route_url            VARCHAR(255)    DEFAULT NULL                 COMMENT '路由地址',
                            menu_icon            VARCHAR(50)     DEFAULT NULL                 COMMENT '菜单图标',
                            component_path       VARCHAR(255)    DEFAULT NULL                 COMMENT '组件路径',
                            PRIMARY KEY (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

DROP TABLE IF EXISTS cdpma_user;
CREATE TABLE cdpma_user (
                            user_id              BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '用户ID',
                            operator_id          BIGINT(20)      NOT NULL                     COMMENT '操作员编号（关联到操作员表）',
                            account_balance      DECIMAL(10, 2)  DEFAULT 0.00                 COMMENT '账户金额',
                            points               INT             DEFAULT 0                    COMMENT '积分',
                            coupons              INT             DEFAULT 0                    COMMENT '优惠券数量',
                            orders               INT             DEFAULT 0                    COMMENT '订单数量',
                            user_level           INT             DEFAULT 0                    COMMENT '用户等级',
                            rfm                  VARCHAR(50)     DEFAULT '000'                COMMENT 'RFM（Recency, Frequency, Monetary）指标',
                            lifecycle            VARCHAR(50)     DEFAULT ''                   COMMENT '生命周期（如活跃，沉默，流失等）',
                            created_at           DATETIME        NOT NULL                     COMMENT '用户创建时间',
                            updated_at           DATETIME        DEFAULT NULL                 COMMENT '用户更新时间',
                            is_disabled          BOOLEAN         DEFAULT FALSE                COMMENT '是否停用',
                            PRIMARY KEY (user_id),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

DROP TABLE IF EXISTS cdpma_coupons;

CREATE TABLE cdpma_coupons (
                               coupon_id           BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '优惠券ID',
                               coupon_name         VARCHAR(255)    NOT NULL                     COMMENT '优惠券名称',
                               created_by          BIGINT(20)      NOT NULL                     COMMENT '创建者',
                               created_at          DATETIME        NOT NULL                     COMMENT '创建时间',
                               updated_by          BIGINT(20)      DEFAULT NULL                 COMMENT '更新者',
                               updated_at          DATETIME        DEFAULT NULL                 COMMENT '更新时间',
                               is_disabled         BOOLEAN         DEFAULT FALSE                COMMENT '是否停用（过期）',
                               is_issued           BOOLEAN         DEFAULT FALSE                COMMENT '是否发放',
                               issuance_target     BIGINT(20)      DEFAULT NULL                 COMMENT '发放对象(操作者id)',
                               discount_effect     VARCHAR(255)    DEFAULT NULL                 COMMENT '优惠效果',
                               expiration_duration INT             DEFAULT NULL                 COMMENT '过期时长（单位：分钟）',
                               issuance_time       DATETIME        DEFAULT NULL                 COMMENT '发放时间',
                               PRIMARY KEY (coupon_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';


DROP TABLE IF EXISTS cdpma_good;

CREATE TABLE cdpma_good (
                            good_id             BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '商品ID',
                            good_name           VARCHAR(255)    NOT NULL                     COMMENT '商品名称',
                            category_id         BIGINT(20)      NOT NULL                     COMMENT '种类编号',
                            created_by          BIGINT(20)      NOT NULL                     COMMENT '创建者',
                            created_at          DATETIME        NOT NULL                     COMMENT '创建时间',
                            updated_by          BIGINT(20)      DEFAULT NULL                 COMMENT '更新者',
                            updated_at          DATETIME        DEFAULT NULL                 COMMENT '更新时间',
                            is_disabled         BOOLEAN         DEFAULT FALSE                COMMENT '是否停用',
                            price               DECIMAL(10,2)   NOT NULL                     COMMENT '价格',
                            PRIMARY KEY (good_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

DROP TABLE IF EXISTS cdpma_good_category;

CREATE TABLE cdpma_good_category (
                                     category_id         BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '种类ID',
                                     category_name       VARCHAR(255)    NOT NULL                     COMMENT '种类名称',
                                     parent_category     BIGINT(20)      DEFAULT -1                 COMMENT '父类ID,如果是最高级就是-1',
                                     PRIMARY KEY (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品种类表';

DROP TABLE IF EXISTS cdpma_favorites_record;

CREATE TABLE cdpma_favorites_record (
                                        record_id           BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '收藏记录ID',
                                        operator_id         BIGINT(20)      NOT NULL                     COMMENT '操作员编号',
                                        good_id             BIGINT(20)      NOT NULL                     COMMENT '商品ID',
                                        created_at          DATETIME        NOT NULL                     COMMENT '时间',
                                        is_deleted          BOOLEAN         DEFAULT FALSE                COMMENT '是否删除',
                                        PRIMARY KEY (record_id),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏记录表';

DROP TABLE IF EXISTS cdpma_like_record;

CREATE TABLE cdpma_like_record (
                                   record_id           BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '点赞记录ID',
                                   operator_id         BIGINT(20)      NOT NULL                     COMMENT '操作员编号',
                                   good_id             BIGINT(20)      NOT NULL                     COMMENT '商品ID',
                                   created_at          DATETIME        NOT NULL                     COMMENT '时间',
                                   is_deleted          BOOLEAN         DEFAULT FALSE                COMMENT '是否删除',
                                   PRIMARY KEY (record_id),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞记录表';


DROP TABLE IF EXISTS cdpma_shopping_cart_record;

CREATE TABLE cdpma_shopping_cart_record (
                                            record_id           BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '购物车记录ID',
                                            operator_id         BIGINT(20)      NOT NULL                     COMMENT '操作员编号',
                                            good_id             BIGINT(20)      NOT NULL                     COMMENT '商品ID',
                                            created_at          DATETIME        NOT NULL                     COMMENT '时间',
                                            is_deleted          BOOLEAN         DEFAULT FALSE                COMMENT '是否删除',
                                            PRIMARY KEY (record_id),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车记录表';

DROP TABLE IF EXISTS cdpma_order;

CREATE TABLE cdpma_order (
                             order_id            BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '订单ID',
                             operator_id         BIGINT(20)      NOT NULL                     COMMENT '操作员编号',
                             good_id             BIGINT(20)      NOT NULL                     COMMENT '商品ID',
                             quantity            INT(11)         NOT NULL                     COMMENT '商品数量',
                             unit_price          DECIMAL(10, 2)  NOT NULL                     COMMENT '单价',
                             order_time          DATETIME        NOT NULL                     COMMENT '下单时间',
                             is_paid             BOOLEAN         DEFAULT FALSE                COMMENT '是否付款',
                             is_cancelled        BOOLEAN         DEFAULT FALSE                COMMENT '是否取消',
                             feedback            VARCHAR(255)    DEFAULT NULL                 COMMENT '订单反馈',
                             feedback_score      INT             DEFAULT 0                 COMMENT '订单反馈评分',
                             PRIMARY KEY (order_id),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';


DROP TABLE IF EXISTS cdpma_user_behavior_record;

CREATE TABLE cdpma_user_behavior_record (
                                            record_id           BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '用户行为记录ID',
                                            operator_id         BIGINT(20)      NOT NULL                     COMMENT '操作员编号',
                                            behavior_id         BIGINT(20)      NOT NULL                     COMMENT '用户行为编号',
                                            behavior_time       DATETIME        NOT NULL                     COMMENT '用户操作时间',
                                            behavior_status     INT             DEFAULT 0                    COMMENT '用户操作状态（自定义）',
                                            PRIMARY KEY (record_id),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行为记录表';

DROP TABLE IF EXISTS cdpma_user_behavior;

CREATE TABLE cdpma_user_behavior (
                                     behavior_id         BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '用户行为编号',
                                     behavior_type       INT             NOT NULL                     COMMENT '用户操作类型（注册/登陆/访问/购买/加入购物车/等级变动）',
                                     webpage             VARCHAR(255)    DEFAULT NULL                 COMMENT '用户操作对象-网页',
                                     good_id             BIGINT(20)      DEFAULT NULL                 COMMENT '用户操作对象-商品',
                                     total_price         DECIMAL(10, 2)  DEFAULT NULL                 COMMENT '用户操作对象-商品-总价',
                                     order_id            BIGINT(20)      DEFAULT NULL                 COMMENT '用户操作对象-商品-订单号',
                                     category_name       VARCHAR(100)    DEFAULT NULL                 COMMENT '用户操作对象-商品种类',
                                     level_change        VARCHAR(100)    DEFAULT NULL                 COMMENT '用户操作对象-等级变动',
                                     explanation         TEXT            DEFAULT NULL                 COMMENT '解释',
                                     PRIMARY KEY (behavior_id),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行为表';

DROP TABLE IF EXISTS cdpma_sales_assistant;

CREATE TABLE cdpma_sales_assistant (
                                       assistant_id         BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '导购编号',
                                       operator_id          BIGINT(20)      NOT NULL                     COMMENT '操作者编号',
                                       total_users          INT(11)         DEFAULT 0                    COMMENT '用户人数',
                                       total_spent_amount   DECIMAL(10, 2)  DEFAULT 0                    COMMENT '用户消费金额',
                                       new_users            INT(11)         DEFAULT 0                    COMMENT '新增用户',
                                       PRIMARY KEY (assistant_id),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导购表';


DROP TABLE IF EXISTS cdpma_sales_assistant_user;

CREATE TABLE cdpma_sales_assistant_user (
                                            assistant_id         BIGINT(20)      NOT NULL                     COMMENT '导购编号',
                                            user_id              BIGINT(20)      NOT NULL                     COMMENT '用户编号',
                                            join_time            DATETIME        NOT NULL                     COMMENT '加入时间',
                                            is_related           BOOLEAN         DEFAULT TRUE                 COMMENT '是否存在关系',
                                            update_time          DATETIME        NOT NULL                     COMMENT '变动时间',
                                            PRIMARY KEY (assistant_id, user_id),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导购-用户表';

DROP TABLE IF EXISTS cdpma_operation;

CREATE TABLE cdpma_operation (
                                 operation_id         BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '操作ID',
                                 trigger_condition_id BIGINT(20)      NOT NULL                     COMMENT '操作触发条件编号',
                                 operation_response   BIGINT(20)      NOT NULL                     COMMENT '操作响应 (如 发信息/发优惠券等)',
                                 create_time          DATETIME        NOT NULL                     COMMENT '创建时间',
                                 created_by           BIGINT(20)      NOT NULL                     COMMENT '创建者',
                                 update_time          DATETIME        NOT NULL                     COMMENT '更新时间',
                                 updated_by           BIGINT(20)      NOT NULL                     COMMENT '更新者',
                                 operation_permission INT(1)          NOT NULL                     COMMENT '操作权限（1表示管理，2表示导购）',
                                 operation_description TEXT           DEFAULT NULL                 COMMENT '操作说明',
                                 is_enabled           BOOLEAN         DEFAULT TRUE                 COMMENT '是否启用',
                                 PRIMARY KEY (operation_id),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作表';


DROP TABLE IF EXISTS cdpma_operation_approval;

CREATE TABLE cdpma_operation_approval (
                                          approval_id          BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '审批编号',
                                          operation_id         BIGINT(20)      NOT NULL                     COMMENT '操作编号',
                                          is_approved          BOOLEAN         NOT NULL                     COMMENT '是否通过',
                                          suggestion           TEXT            DEFAULT NULL                 COMMENT '建议',
                                          approval_time        DATETIME        NOT NULL                     COMMENT '审批时间',
                                          PRIMARY KEY (approval_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作审批表';

DROP TABLE IF EXISTS cdpma_operation_trigger_condition;

CREATE TABLE cdpma_operation_trigger_condition (
                                                   condition_id         BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '触发条件编号',
                                                   condition_name       VARCHAR(255)    NOT NULL                     COMMENT '触发条件名称',
                                                   is_urgent            BOOLEAN         DEFAULT FALSE                COMMENT '及时/非及时',
                                                   description          TEXT            DEFAULT NULL                 COMMENT '解释',
                                                   PRIMARY KEY (condition_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作触发条件表';


DROP TABLE IF EXISTS cdpma_operation_response;

CREATE TABLE cdpma_operation_response (
                                          response_id          BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '响应编号',
                                          response_name        VARCHAR(255)    NOT NULL                     COMMENT '触发响应名称',
                                          is_urgent            BOOLEAN         DEFAULT FALSE                COMMENT '及时/非及时',
                                          description          TEXT            DEFAULT NULL                 COMMENT '解释',
                                          PRIMARY KEY (response_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作响应表';


DROP TABLE IF EXISTS cdpma_notification;

CREATE TABLE cdpma_notification (
                                    notification_id      BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '提示编号',
                                    operator_id          BIGINT(20)      NOT NULL                     COMMENT '操作者编号',
                                    timestamp            DATETIME        NOT NULL                     COMMENT '时间',
                                    sender               BIGINT(20)      NOT NULL                     COMMENT '发送者',
                                    receiver             BIGINT(20)      NOT NULL                     COMMENT '接收者',
                                    send_time            DATETIME        NOT NULL                     COMMENT '发送时间',
                                    notification_type    INT DEFAULT 0 COMMENT '提示类型',
                                    content              TEXT            NOT NULL                     COMMENT '正文',
                                    PRIMARY KEY (notification_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='提示表';

DROP TABLE IF EXISTS cdpma_runtime_log;

CREATE TABLE cdpma_runtime_log (
                                   operation_id      BIGINT AUTO_INCREMENT PRIMARY KEY   COMMENT '操作ID',
                                   title             VARCHAR(100) DEFAULT ""             COMMENT '操作名称（方法作用）',
                                   business_type     INT NOT NULL                        COMMENT '业务类型（如 INSERT=1, UPDATE=2）',
                                   method            VARCHAR(255) DEFAULT ""             COMMENT  '调用方法',
                                   request_method    VARCHAR(10) DEFAULT ""              COMMENT '请求方式（GET/POST/PUT）',
                                   operator_type     INT                                 COMMENT '操作人类别（USER/ADMIN/ASSISTANT/UNKNOWN）',
                                   operator_name     VARCHAR(50)                         COMMENT '操作人名称',
                                   operation_url     VARCHAR(255)                        COMMENT '请求URL',
                                   operator_ip       VARCHAR(45)                         COMMENT '操作IP',
                                   operator_id       Long                         COMMENT '操作ID',
                                   operation_param   TEXT                                COMMENT '请求参数',
                                   json_result       TEXT                                COMMENT '返回结果',
                                   status            INT NOT NULL DEFAULT 0              COMMENT '操作状态（0成功 1失败）',
                                   error_msg         VARCHAR(500)                        COMMENT '错误消息',
                                   cost_time         BIGINT                              COMMENT '消耗时间（毫秒）',
                                   create_time       DATETIME                            COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志记录表';


DROP TABLE IF EXISTS cdpma_login_logout_log;

CREATE TABLE cdpma_login_logout_log (
                                        log_id               BIGINT(20)      NOT NULL AUTO_INCREMENT      COMMENT '日志编号',
                                        operator_id          BIGINT(20)      NOT NULL                     COMMENT '操作者编号',
                                        operator_name             VARCHAR(64)     NOT NULL                     COMMENT '操作者用户名',
                                        message              TEXT            DEFAULT NULL                 COMMENT '日志消息',
                                        action               INT             NOT NULL                COMMENT '操作类型（登录/登出）',
                                        timestamp            DATETIME        NOT NULL     COMMENT '时间',
                                        ip_address           VARCHAR(50)     DEFAULT NULL                 COMMENT 'IP地址',
                                        PRIMARY KEY (log_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录登出日志表';