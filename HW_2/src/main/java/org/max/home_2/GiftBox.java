package org.max.home_2;

import java.util.ArrayList;
import java.util.List;

public class GiftBox implements Gift {
    private List<Gift> gifts = new ArrayList<>();

    public void addGift(Gift gift) {
        gifts.add(gift);
    }

    @Override
    public short open() {
        System.out.println("Открыта коробка с подарками:");
        for (Gift gift : gifts) {
            gift.open();
        }
        return 0;
    }
}
