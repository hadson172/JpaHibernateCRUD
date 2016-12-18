package crudapp.model;


public enum Chocolate {
    APPLE_PLEASURE(1.2),
    AVOCADO_AMBROSIA(2.5),
    SWEET_LILY(3.3),
    NUTS_EXPLOSION(9.1);

    private double chocolatePrice;

    Chocolate(double chocolatePrice) {
        this.chocolatePrice = chocolatePrice;
    }

    public double getChocolatePrice() {
        return chocolatePrice;
    }
}
