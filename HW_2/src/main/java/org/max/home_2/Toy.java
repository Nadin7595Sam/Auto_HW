package org.max.home_2;

public class Toy implements Gift {
    private String name;

    public Toy(String name) {
        this.name = name;
    }

    @Override
    public short open() {
        System.out.println("Открыт подарок: игрушка — " + name);
        return 0;
    }
}
