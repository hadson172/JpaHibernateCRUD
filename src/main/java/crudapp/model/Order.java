package crudapp.model;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Orders")
public class Order
{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;

    @NotNull
    private ZonedDateTime executionDate;

    private double orderPrice;


    @NotNull
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<ChocolateBox> chocolateBoxes = new HashSet<>();



    public Order() {}


    public Order(ZonedDateTime zonedDateTime)
    {
        this.executionDate = zonedDateTime;
    }


    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public void addChocolateBox(ChocolateBox box)
    {
        chocolateBoxes.add(box);
        box.setOrder(this);
        orderPrice += box.getBoxPrice();
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (Double.compare(order.orderPrice, orderPrice) != 0) return false;
        if (!executionDate.equals(order.executionDate)) return false;
        return chocolateBoxes.equals(order.chocolateBoxes);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = executionDate.hashCode();
        temp = Double.doubleToLongBits(orderPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + chocolateBoxes.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "executionDate=" + executionDate +
                ", orderPrice=" + orderPrice +
                ", chocolateBoxes=" + chocolateBoxes +
                '}';
    }
}
