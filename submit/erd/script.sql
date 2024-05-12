create table category
(
    category_id        int auto_increment comment '카테고리아아디'
        primary key,
    category_name      varchar(128) not null comment '카테고리이름',
    parent_category_id int          null comment '자식카테고리아이디',
    constraint category_ibfk_1
        foreign key (parent_category_id) references category (category_id)
)
    comment '카테고리';

create index parent_category_id
    on category (parent_category_id);

create table order_detail
(
    order_detail_id       int auto_increment comment '주문상세아이디'
        primary key,
    order_detail_quantity int          not null comment '주문상세수량',
    order_detail_price    varchar(128) not null comment '주문상세가격',
    address               varchar(256) not null comment '배달지',
    addressee             varchar(128) not null comment '수취인',
    phone                 varchar(64)  not null comment '받는분 전화번호',
    order_comment         varchar(512) null comment '주문 요청사항',
    order_rdate           datetime     null comment '주문 등록 일자'
)
    comment '주문상세';

create table product
(
    product_id          int auto_increment comment '상품아이디'
        primary key,
    product_name        varchar(128)                       not null comment '상품이름',
    product_price       bigint   default 999999999         not null comment '상품가격',
    product_description varchar(256)                       null comment '상품설명',
    product_field       int      default 0                 null comment '상품재고',
    product_rdate       datetime default CURRENT_TIMESTAMP null comment '상품등록일'
)
    comment '상품';

create table cart
(
    cart_id               int auto_increment,
    product_id            int      not null,
    cart_product_quantity int      not null comment '장바구니상품수량',
    cart_rdate            datetime null comment '장바구니등록날짜',
    primary key (cart_id, product_id),
    constraint cart_product_product_id_fk
        foreign key (product_id) references product (product_id)
)
    comment '장바구니';

create table product_category
(
    product_id  int not null,
    category_id int not null,
    primary key (product_id, category_id),
    constraint product_category_ibfk_1
        foreign key (product_id) references product (product_id),
    constraint product_category_ibfk_2
        foreign key (category_id) references category (category_id)
)
    comment '상품카테고리';

create index category_id
    on product_category (category_id);

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

create table users
(
    user_id         varchar(50)  not null comment '아이디'
        primary key,
    user_name       varchar(50)  not null comment '이름',
    user_password   varchar(200) not null comment 'mysql password 사용',
    user_birth      varchar(8)   not null comment '생년월일 : 19840503',
    user_auth       varchar(10)  not null comment '권한: ROLE_ADMIN,ROLE_USER',
    user_point      int          not null comment 'default : 1000000',
    created_at      datetime     not null comment '가입 일자',
    latest_login_at datetime     null comment '마지막 로그인 일자'
)
    comment '회원';

create table `order`
(
    product_id      int         not null,
    user_id         varchar(50) not null comment '회원아이디',
    order_detail_id int         not null,
    constraint order_ibfk_1
        foreign key (product_id) references product (product_id),
    constraint order_ibfk_2
        foreign key (user_id) references users (user_id),
    constraint order_ibfk_3
        foreign key (order_detail_id) references order_detail (order_detail_id)
)
    comment '주문';

create index order_detail_id
    on `order` (order_detail_id);

create index product_id
    on `order` (product_id);

create index user_id
    on `order` (user_id);

create table point_use
(
    user_id varchar(50) not null,
    constraint point_use_ibfk_1
        foreign key (user_id) references users (user_id)
)
    comment '포인트 사용내역';

create index user_id
    on point_use (user_id);

create table users_cart
(
    cart_id int         not null
        primary key,
    user_id varchar(50) null,
    constraint users_cart_cart_cart_id_fk
        foreign key (cart_id) references cart (cart_id),
    constraint users_cart_ibfk_2
        foreign key (user_id) references users (user_id)
)
    comment '상품장바구니';

create index user_id
    on users_cart (user_id);

