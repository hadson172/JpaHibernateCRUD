package crudapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
public class ChocolateBox {

    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    private Order order;

    private double boxPrice;


    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyEnumerated(EnumType.STRING)
    @CollectionTable(name = "ChocolateBox_Content")
    private Map<Chocolate, Integer> chocolates = new HashMap<>();


    public ChocolateBox() {
    }


    public void setOrder(Order order) {
        this.order = order;
    }

    public void addChocolate(Chocolate chocolate) {
        this.chocolates.computeIfPresent(chocolate, (k, v) -> v + 1);
        this.chocolates.computeIfAbsent(chocolate, k -> 1);
        this.boxPrice += chocolate.getChocolatePrice();
    }

    public double getBoxPrice() {
        return boxPrice;
    }

    public Map<Chocolate, Integer> getChocolates() {
        return chocolates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChocolateBox)) return false;

        ChocolateBox that = (ChocolateBox) o;

        if (Double.compare(that.boxPrice, boxPrice) != 0) return false;
        return chocolates.equals(that.chocolates);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(boxPrice);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + chocolates.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ChocolateBox{" +
                "boxPrice=" + boxPrice +
                ", chocolates=" + chocolates +
                '}';
    }
}
