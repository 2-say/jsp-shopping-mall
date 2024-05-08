use nhn_academy_31;

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
    `category_id`        int          NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리아아디',
    `category_name`      varchar(128) NOT NULL COMMENT '카테고리이름',
    `parent_category_id` int NULL COMMENT '자식카테고리아이디',
    FOREIGN KEY (`parent_category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='카테고리';

-- 상품
CREATE TABLE `product`
(
    `product_id`          int          NOT NULL AUTO_INCREMENT COMMENT '상품아이디',
    `product_name`        varchar(128) NOT NULL COMMENT '상품이름',
    `product_price`       bigint       NOT NULL DEFAULT 999999999 COMMENT '상품가격',
    `product_description` varchar(256) COMMENT '상품설명',
    `product_field`       int                   DEFAULT 0 COMMENT '상품재고',
    `product_rdate`       datetime              DEFAULT NOW() COMMENT '상품등록일',
    PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품';


-- 상품 카테고리
CREATE TABLE `product_category`
(
    `product_id`  int NOT NULL,
    `category_id` int NOT NULL,
    FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
    FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품카테고리';

-- 유저
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


-- 포인트 사용 내역
CREATE TABLE `point_use`
(
    `user_id` varchar(50) NOT NULL,
        FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='포인트 사용내역';


-- 주문상세
CREATE TABLE `order_detail`
(
    `order_detail_id`       int          NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '주문상세아이디',
    `order_detail_quantity` int          NOT NULL COMMENT '주문상세수량',
    `order_detail_price`    varchar(128) NOT NULL COMMENT '주문상세가격',
    `address`               varchar(256) not null comment '배달지',
    `addressee`             varchar(128) not null comment '수취인',
    `phone`                 varchar(64)  not null comment '받는분 전화번호',
    order_comment           varchar(512) null comment '주문 요청사항',
    order_rdate             datetime null comment '주문 등록 일자'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문상세';

-- 주문
CREATE TABLE `order`
(
    `product_id`      int         NOT NULL,
    `user_id`         varchar(50) NOT NULL COMMENT '회원아이디',
    `order_detail_id` int         NOT NULL,
    FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    FOREIGN KEY (`order_detail_id`) REFERENCES `order_detail` (`order_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문';


-- 상품 장바구니 (비회원 가능)
CREATE TABLE `product_cart`
(
    `cart_id`       int NOT NULL ,
    `product_id`      int NOT NULL,
    `user_id`         varchar(50) NULL,
    FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`),
    PRIMARY KEY (`product_id`, `cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품장바구니';

-- 장바구니
CREATE TABLE `cart`
(
    `cart_id`               int NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '장바구니아이디',
    `product_cart_id`       int NOT NULL COMMENT '상품아이디',
    `cart_product_quantity` int NOT NULL COMMENT '장바구니상품수량',
    `cart_rdate`            datetime COMMENT '장바구니등록날짜',
    FOREIGN KEY (`product_cart_id`) REFERENCES `product_cart` (`product_cart_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='장바구니';

-- 상품 이미지
create table product_image
(
    image_id   int auto_increment comment 'pk'
        primary key,
    product_id int          not null comment '상품 id',
    image_name varchar(256) not null comment '이미지 이름 ',
    constraint product_image_product_product_id_fk
        foreign key (product_id) references product (product_id)
)
    comment '상품 상세 이미지';