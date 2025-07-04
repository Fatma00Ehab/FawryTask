import model.*;
import service.*;
import entity.Customer;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
 
        try {
            Customer customer0 = new Customer("Ali", 20000);
            Product cheese = new Cheese("Cheese", 100, 5, 0.2, LocalDate.now().plusDays(2));
            Product biscuits = new Cheese("Biscuits", 150, 2, 0.7, LocalDate.now().plusDays(1));
            Cart cart0 = new Cart();
            cart0.add(cheese, 2);
            cart0.add(biscuits, 1);
            CheckoutService.checkout(customer0, cart0);
        } catch (Exception e) {
            System.out.println("ERROR (Success Case): " + e.getMessage());
        }

    
        try {
            Customer customer1 = new Customer("Ali", 20000);
            Product expiredCheese = new Cheese("Expired Cheese", 100, 5, 0.5, LocalDate.now().minusDays(1));
            Cart cart1 = new Cart();
            cart1.add(expiredCheese, 1);
            CheckoutService.checkout(customer1, cart1);
        } catch (Exception e) {
            System.out.println("ERROR (Expired Product): " + e.getMessage());
        }

        
        try {
            Customer poorCustomer = new Customer("Poor Ali", 10);
            Product tv = new TV("TV", 5000, 3, 5.0);
            Cart cart2 = new Cart();
            cart2.add(tv, 1);
            CheckoutService.checkout(poorCustomer, cart2);
        } catch (Exception e) {
            System.out.println("ERROR (Insufficient Balance): " + e.getMessage());
        }
 
        try {
            Customer customer3 = new Customer("Ali", 20000);
            Cart emptyCart = new Cart();
            CheckoutService.checkout(customer3, emptyCart);
        } catch (Exception e) {
            System.out.println("ERROR (Empty Cart): " + e.getMessage());
        }

         try {
            Customer customer4 = new Customer("Ali", 20000);
            Product cheese = new Cheese("Cheese", 100, 2, 0.2, LocalDate.now().plusDays(2));
            Cart cart3 = new Cart();
            cart3.add(cheese, 5); 
            CheckoutService.checkout(customer4, cart3);
        } catch (Exception e) {
            System.out.println("ERROR (Stock Exceeded): " + e.getMessage());
        }

         try {
            Customer customer5 = new Customer("Ali", 20000);
            Product scratchCard = new ScratchCard("ScratchCard", 50, 10);
            Cart cart4 = new Cart();
            cart4.add(scratchCard, 2);
            CheckoutService.checkout(customer5, cart4);
        } catch (Exception e) {
            System.out.println("ERROR (Scratch Card Test): " + e.getMessage());
        }
    }
}
