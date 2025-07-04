package service;

import model.*;
import entity.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutService {
    public static void checkout(Customer customer, Cart cart) throws Exception {
        if (cart.isEmpty())
            throw new Exception("Cart is empty!");

        double subtotal = 0;
        List<Shippable> toShip = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();

            if (p instanceof Expirable && ((Expirable) p).isExpired())
                throw new Exception(p.getName() + " is expired.");

            // if (!p.isAvailable(qty))
            //     throw new Exception(p.getName() + " stock not enough.");

            if (!p.isAvailable(qty))
                throw new Exception(
                        "Requested " + qty + "x " + p.getName() + ", but only " + p.getQuantity() + " available.");

            if (p instanceof Shippable)
                for (int i = 0; i < qty; i++)
                    toShip.add((Shippable) p);

            subtotal += p.getPrice() * qty;
        }

        double shipping = toShip.isEmpty() ? 0 : 30;
        double total = subtotal + shipping;

        if (!customer.canAfford(total))
            throw new Exception("Insufficient balance!");

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet())
            entry.getKey().reduceQuantity(entry.getValue());

        customer.deduct(total);

        if (!toShip.isEmpty())
            ShippingService.ship(toShip);

        System.out.println("- * Checkout receipt **\n");
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            System.out.printf("%dx %-15s %.0f%n", entry.getValue(), entry.getKey().getName(),
                    entry.getKey().getPrice() * entry.getValue());
        }

        System.out.println("\n- ---------------------\n");
        System.out.printf("Subtotal         %.0f%n", subtotal);
        System.out.printf("Shipping         %.0f%n", shipping);
        System.out.printf("Amount           %.0f%n", total);
        System.out.printf("Remaining Balance %.0f%n", customer.getBalance());
    }
}
