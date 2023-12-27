package org.max.home_2;

public class Candy implements Gift {
    private String flavor;

    public Candy(String flavor) {
        this.flavor = flavor;
    }

    @Override
    public short open() {
        System.out.println("Открыт подарок: шоколадка " + flavor);
        return 0;
    }
}
