INSERT INTO product (product_id, product_name, product_price, product_description, product_field, product_rdate, category_id, image_name)
VALUES
    (3, 'Adidas', 15000, 'Comfortable running shoes.', 12, '2023-05-15 09:45:18', 2, 'adidas.jpeg' ),
    (4, 'New Balance', 12000, 'Sleek design with great comfort.', 11, '2023-06-02 13:20:05', 1, 'new_balance.jpg'),
    (5, 'Puma', 18000, 'Stylish and durable footwear.', 10, '2023-07-10 16:55:42', 2, 'puma.png'),
    (6, 'Converse', 22000, 'Classic sneakers for everyday wear.', 14, '2023-08-18 08:30:11', 1, 'converse.jpeg'),
    (7, 'Under Armour', 25000, 'High-performance athletic shoes.', 12, '2023-09-25 14:10:37', 2, 'under_armour.jpg'),
    (8, 'Vans', 17000, 'Iconic skateboarding shoes.', 11, '2023-10-30 17:20:58', 1, 'vans.jpg'),
    (9, 'Reebok', 16000, 'Versatile and stylish footwear.', 10, '2023-11-14 09:35:29', 2, 'reebok.jpeg'),
    (10, 'Skechers', 20000, 'Comfortable shoes for all-day wear.', 14, '2023-12-19 11:55:42', 1, 'skechers.jpg'),
    (11, 'Nike Air Jordan', 30000, 'Iconic basketball shoes.', 12, '2024-01-22 15:30:17', 2, 'nike_air_jordan.jpg'),
    (12, 'Asics', 22000, 'High-quality running shoes.', 11, '2024-02-28 18:40:58', 1, 'asics.png'),
    (13, 'Salomon', 28000, 'Durable trail running shoes.', 10, '2024-03-12 09:15:29', 2, 'salomon.jpg'),
    (14, 'Brooks', 24000, 'Comfortable and supportive running shoes.', 14, '2024-04-05 11:35:42', 1, 'brooks.jpeg'),
    (15, 'Mizuno', 19000, 'Reliable sports footwear.', 12, '2024-05-20 14:30:11', 2, 'mizuno.jpg'),
    (16, 'Fila', 17000, 'Fashionable athletic shoes.', 11, '2024-06-25 17:40:37', 1, 'fila.jpg'),
    (17, 'Hoka One One', 32000, 'Maximalist running shoes.', 10, '2024-07-30 20:20:58', 2, 'hoka_one_one.jpg'),
    (18, 'Merrell', 26000, 'Outdoor adventure footwear.', 14, '2024-08-14 09:35:29', 1, 'merrell.jpeg'),
    (19, 'Columbia', 23000, 'Performance outdoor shoes.', 12, '2024-09-19 11:55:42', 2, 'columbia.jpg'),
    (20, 'Keen', 29000, 'Durable hiking shoes.', 11, '2024-10-24 15:30:17', 1, 'keen.jpg');


-- 상위 카테고리 추가
INSERT INTO category (category_id, category_name) VALUES
                                                      (1, '의류'),
                                                      (2, '전자제품'),
                                                      (3, '가구'),
                                                      (4, '도서'),
                                                      (5, '식품'),
                                                      (6, '화장품'),
                                                      (7, '기타');

-- 하위 카테고리 추가
INSERT INTO category (category_id, category_name, parent_category_id) VALUES
                                                                          (25, '남성 의류', 1),
                                                                          (8, '여성 의류', 1),
                                                                          (9, '아동 의류', 1),
                                                                          (10, '스마트폰', 2),
                                                                          (11, '노트북', 2),
                                                                          (12, '태블릿', 2),
                                                                          (13, '소파', 3),
                                                                          (14, '침대', 3),
                                                                          (15, '식탁', 3),
                                                                          (16, '소설', 4),
                                                                          (17, '전문서적', 4),
                                                                          (18, '컴퓨터 도서', 4),
                                                                          (19, '과일', 5),
                                                                          (20, '채소', 5),
                                                                          (21, '유제품', 5),
                                                                          (22, '스킨케어', 6),
                                                                          (23, '메이크업', 6),
                                                                          (24, '향수', 6);

