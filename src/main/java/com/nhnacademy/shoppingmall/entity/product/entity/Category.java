package com.nhnacademy.shoppingmall.entity.product.entity;
public enum Category {
    CLOTHES("의류"),
    ELECTRONICS("전자제품"),
    FURNITURE("가구"),
    BOOKS("도서"),
    FOOD("식품"),
    COSMETICS("화장품"),
    MEN_CLOTHING("남성 의류"),
    WOMEN_CLOTHING("여성 의류"),
    CHILDREN_CLOTHING("아동 의류"),
    SMARTPHONES("스마트폰"),
    LAPTOPS("노트북"),
    TABLETS("태블릿"),
    SOFA("소파"),
    BED("침대"),
    DINING_TABLE("식탁"),
    NOVELS("소설"),
    TEXTBOOKS("전문서적"),
    COMPUTER_BOOKS("컴퓨터 도서"),
    FRUITS("과일"),
    VEGETABLES("채소"),
    DAIRY_PRODUCTS("유제품"),
    SKINCARE("스킨케어"),
    MAKEUP("메이크업"),
    PERFUME("향수");

    private final String nameInKorean;

    Category(String nameInKorean) {
        this.nameInKorean = nameInKorean;
    }

    public String getNameInKorean() {
        return nameInKorean;
    }

    public static Category getByKoreanName(String koreanName) {
        for (Category category : Category.values()) {
            if (category.getNameInKorean().equals(koreanName)) {
                return category;
            }
        }
        return null; // or throw an exception if not found
    }
}