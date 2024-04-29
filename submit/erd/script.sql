use
nhn_academy_31;

DROP TABLE IF EXISTS `auth`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `address`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS `order_detail`;
DROP TABLE IF EXISTS `cart`;
DROP TABLE IF EXISTS `users`;


-- 키테고리
CREATE TABLE `category`
(
    `category_id`       int          NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리아아디',
    `category_name`     varchar(128) NOT NULL COMMENT '카테고리이름',
    `parent_category_id` int NULL COMMENT '자식카테고리아이디',
    FOREIGN KEY (`child_category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='카테고리';


-- 등급


-- 회원
CREATE TABLE `users`
(
    `user_id`         varchar(50)  NOT NULL COMMENT '아이디',
    `user_name`       varchar(50)  NOT NULL COMMENT '이름',
    `user_password`   varchar(200) NOT NULL COMMENT 'mysql password 사용',
    `user_birth`      varchar(8)   NOT NULL COMMENT '생년월일 : 19840503',
    `user_auth`       varchar(10)  NOT NULL COMMENT '권한: ROLE_ADMIN,ROLE_USER',
    `user_point`      int          NOT NULL COMMENT 'default : 1000000',
    `created_at`      datetime     NOT NULL COMMENT '가입 일자',
    `latest_login_at` datetime DEFAULT NULL COMMENT '마지막 로그인 일자',
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원';


-- 주소
CREATE TABLE `address`
(
    `user_id`        varchar(50)  NOT NULL COMMENT '회원아이디',
    `zip_code`       varchar(13)  NOT NULL COMMENT '우편번호',
    `address`        varchar(128) NOT NULL COMMENT '주소',
    `address_detail` varchar(128) NOT NULL COMMENT '상세주소',
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주소';


-- 상품
CREATE TABLE `product`
(
    `product_id`          int          NOT NULL AUTO_INCREMENT COMMENT '상품아이디',
    `product_name`        varchar(128) NOT NULL COMMENT '상품이름',
    `product_price`       varchar(128) NOT NULL COMMENT '상품가격',
    `product_description` varchar(256) COMMENT '상품설명',
    `product_field`       int      DEFAULT 0 COMMENT '상품재고',
    `product_rdate`       datetime DEFAULT NOW() COMMENT '상품등록일',
    `category_id`         int          NOT NULL COMMENT '카테고리아이디',
    `image_name`          varchar(256) NULL COMMENT '상품이미지이름',
    `image_path`          varchar(512) NULL COMMENT '상품이미지경로',
    PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품';

-- 주문
CREATE TABLE `order`
(
    `order_id`     int         NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '주문아아디',
    `user_id`      varchar(50) NOT NULL COMMENT '회원아이디',
    `order_price`  int         NOT NULL COMMENT '주문결제금액',
    `order_date`   datetime    NOT NULL DEFAULT NOW() COMMENT '주문날짜',
    `order_status` varchar(16) NOT NULL DEFAULT '주문완료' COMMENT '주문상태',
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문';

-- 주문상세
CREATE TABLE `order_detail`
(
    `order_detail_id`       int          NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '주문상세아이디',
    `product_id`            int          NOT NULL COMMENT '상품아이디',
    `order_id`              int          NOT NULL COMMENT '주문아아디',
    `order_detail_quantity` int          NOT NULL COMMENT '주문상세수량',
    `order_detail_price`    varchar(128) NOT NULL COMMENT '주문상세가격',
    FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
    FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문상세';

-- 장바구니
CREATE TABLE `cart`
(
    `cart_id`               int         NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '장바구니아이디',
    `user_id`               varchar(50) NOT NULL COMMENT '회원아이디',
    `product_id`            int         NOT NULL COMMENT '상품아이디',
    `cart_product_quantity` int         NOT NULL COMMENT '장바구니상품수량',
    `cart_rdate`            datetime COMMENT '장바구니등록날짜',
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='장바구니';
