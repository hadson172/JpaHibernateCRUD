package crudapp.dao;

import crudapp.model.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;


public class CustomerDAOTest {

    private static CustomerDAO customerDao;

    @BeforeClass
    public static void init() {
        customerDao = new CustomerRepository();
    }


    @Test
    public void firstTest() {
        assertThat(customerDao, not(nullValue()));
    }

    @Test
    public void addTest() {
        Order order1 = new Order(ZonedDateTime.now().plusHours(3));

        ChocolateBox chocolateBox = new ChocolateBox();
        chocolateBox.addChocolate(Chocolate.APPLE_PLEASURE);
        chocolateBox.addChocolate(Chocolate.APPLE_PLEASURE);
        chocolateBox.addChocolate(Chocolate.AVOCADO_AMBROSIA);

        order1.addChocolateBox(chocolateBox);


        Customer customer = new Customer("johndoe123", "John", "Doe", 0024125233, new Address("Washington", "3021", "GoldenStreet"));
        Customer customer2 = new Customer("janedoe32", "Jane", "Doe", 203123123, new Address("New Yourk", "2000"));


        customer.addOrder(order1);

        customerDao.saveCustomerIfNotExists(customer);
        customerDao.saveCustomerIfNotExists(customer2);


    }

    @Test
    public void retrieveTest() {
        Order order = new Order(ZonedDateTime.now().plusDays(1));
        ChocolateBox chocolateBox = new ChocolateBox();
        chocolateBox.addChocolate(Chocolate.NUTS_EXPLOSION);
        chocolateBox.addChocolate(Chocolate.SWEET_LILY);
        chocolateBox.addChocolate(Chocolate.SWEET_LILY);
        chocolateBox.addChocolate(Chocolate.APPLE_PLEASURE);

        order.addChocolateBox(chocolateBox);

        Customer customer = new Customer("johndoe3", "John", "Doe", 0024125233, new Address("Washington", "3021", "GoldenStreet"));

        customer.addOrder(order);

        customerDao.saveCustomerIfNotExists(customer);

        Optional<Customer> result = customerDao.getCustomerByUsername("johndoe3");

        assertThat(result.isPresent(), equalTo(true));
        assertThat(result.get().equals(customer), equalTo(true));
    }

    @Test
    public void removeTest() {
        Order order = new Order(ZonedDateTime.now().plusDays(3));
        ChocolateBox chocolateBox = new ChocolateBox();
        chocolateBox.addChocolate(Chocolate.APPLE_PLEASURE);

        order.addChocolateBox(chocolateBox);
        Customer customer = new Customer("janedoe4", "John", "Doe", 23123, new Address("New York", "232"));
        customer.addOrder(order);

        customerDao.saveCustomerIfNotExists(customer);
        assertThat(customerDao.containsCustomer(customer.getUsername()), equalTo(true));
        customerDao.removeCustomer(customer.getUsername());
        assertThat(customerDao.containsCustomer(customer.getUsername()), equalTo(false));

    }

    @Test
    public void updateTest() {
        Order order = new Order(ZonedDateTime.now().plusDays(5));
        ChocolateBox chocolateBox = new ChocolateBox();
        chocolateBox.addChocolate(Chocolate.APPLE_PLEASURE);
        chocolateBox.addChocolate(Chocolate.SWEET_LILY);

        order.addChocolateBox(chocolateBox);
        Customer customer = new Customer("janedoe4", "Bill", "NewBerg", 664, new Address("New York", "232", "BlockStreet"));
        customer.addOrder(order);

        customerDao.saveCustomerIfNotExists(customer);

        ChocolateBox chocolateBox1 = new ChocolateBox();
        chocolateBox1.addChocolate(Chocolate.AVOCADO_AMBROSIA);

        Order order1 = new Order(ZonedDateTime.now().plusDays(10));
        order1.addChocolateBox(chocolateBox1);
        customer.addOrder(order1);


        Customer customer1 = customerDao.getCustomerByUsername(customer.getUsername()).get();

        assertThat(customer1, not(equalTo(customer)));
        customerDao.updateCustomer(customer);
        customer1 = customerDao.getCustomerByUsername(customer.getUsername()).get();
        assertThat(customer1, equalTo(customer));

    }


}