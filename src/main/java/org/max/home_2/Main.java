package org.max.home_2;

public class Main {
    public static void main(String[] args) {
        GiftBox giftBox = new GiftBox();
        giftBox.addGift(new Toy("конструктор 'LEGO'"));
        giftBox.addGift(new Candy("'Мишки в лесу'"));
        giftBox.addGift(new Toy("кукла 'Маша'"));

        giftBox.open();
    }
}
