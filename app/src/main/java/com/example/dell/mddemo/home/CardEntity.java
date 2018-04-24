package com.example.dell.mddemo.home;

/**
 * Description：卡片实体
 * <p>
 * Created by Flower.G on 2018/3/27.
 */
public class CardEntity {
    private String cardName = "";
    private int cardImageId;

    public CardEntity() {
    }

    public CardEntity(String cardName, int cardImageId) {
        this.cardName = cardName;
        this.cardImageId = cardImageId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardImageId() {
        return cardImageId;
    }

    public void setCardImageId(int cardImageId) {
        this.cardImageId = cardImageId;
    }
}
