package be.vinci.ipl.instances;

import be.vinci.ipl.classes.Order;
import be.vinci.ipl.classes.OrderLine;
import be.vinci.ipl.classes.User;

import be.vinci.ipl.utils.InstanceGraphBuilder;
import java.time.LocalDateTime;

public class InstanceGraph1 {

    @InstanceGraphBuilder
    public Object initInstanceGraph() {
        User u = new User(1, "Laurent", "Leleux");
        u.addOrder(this.createOrder());
        return u;
    }

    private Order createOrder() {
        Order o = new Order(LocalDateTime.now());
        o.addOrderLine(new OrderLine("Casque audio", 1, 97.25));
        o.addOrderLine(new OrderLine("Souris", 2, 51.57));
        return o;
    }

}
