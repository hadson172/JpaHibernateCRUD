package crudapp.model;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


public class ChocolateBoxTest {

    @Test
    public void addChocolateTest() {
        ChocolateBox chocolateBox = new ChocolateBox();
        chocolateBox.addChocolate(Chocolate.APPLE_PLEASURE);
        chocolateBox.addChocolate(Chocolate.APPLE_PLEASURE);
        chocolateBox.addChocolate(Chocolate.AVOCADO_AMBROSIA);

        assertThat(chocolateBox.getBoxPrice(), equalTo(4.9));
    }

    @Test
    public void quantityTest() {

        ChocolateBox chocolateBox = new ChocolateBox();
        chocolateBox.addChocolate(Chocolate.APPLE_PLEASURE);
        chocolateBox.addChocolate(Chocolate.APPLE_PLEASURE);
        chocolateBox.addChocolate(Chocolate.NUTS_EXPLOSION);

        Map<Chocolate, Integer> chocolates = chocolateBox.getChocolates();

        int appleQuantity = chocolates.get(Chocolate.APPLE_PLEASURE);
        int nutsQuantity = chocolates.get(Chocolate.NUTS_EXPLOSION);

        assertThat(appleQuantity, equalTo(2));
        assertThat(nutsQuantity, equalTo(1));

    }


}