import model.MenuItem;
import model.Order;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static List<MenuItem> initiateMenu () {
        List<MenuItem> listMenu = new ArrayList<>();
        listMenu.add(new MenuItem("Americano", 15000.0));
        listMenu.add(new MenuItem("Espresso", 20000.0));
        listMenu.add(new MenuItem("Cappuccino", 2500.0));
        return listMenu;
    }

    public static double createOrder(List<MenuItem> listMenu, Scanner scanner, List<Order> listOrder) {
        double totalBill = 0.0;
        while (true) {
            // by adding try-catch block we're already preparing for mismatch input
            try {
                System.out.print("Please input no of menu (type 0 for finish): ");
                int chosen = scanner.nextInt();
                if (chosen == 0) {
                    break;
                }
                if (chosen >= 1 && chosen <= listMenu.size()) {
                    System.out.print("Please input amount of product(type 0 for cancel) : ");
                    int amount = scanner.nextInt();
                    if (amount == 0) {
                        continue;
                    }
                    MenuItem chosenItem = listMenu.get(chosen-1);
                    totalBill += chosenItem.getPrice()*amount;
                    listOrder.add(new Order(chosenItem, amount));
                    System.out.printf("Your order is %s with price : %.2f x %d = %.2f \n", chosenItem.getName(), chosenItem.getPrice(), amount, chosenItem.getPrice()*amount);
                } else {
                    System.out.println("Invalid menu number");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please input correct number");
                // to prevent infinity loop we add scanner.next()
                scanner.next();
            }
        }

        return totalBill;
    }

    public static void printMenu(List<MenuItem> listMenu) {
        System.out.println("-----Menu-----");
        for (int i = 0; i < listMenu.size(); i++) {
            System.out.format("%d. %s - Rp %.2f", (i+1), listMenu.get(i).getName(), listMenu.get(i).getPrice());
            System.out.println();
        }
    }

    public static double calculateDiscount(double totalPrice) {
       return totalPrice * 0.1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Order> listOrder = new ArrayList<>();
        List<MenuItem> listMenu = initiateMenu();
        printMenu(listMenu);
        double totalPrice = createOrder(listMenu, scanner, listOrder);
        System.out.printf("Your bill total is - Rp %.2f \n", totalPrice);
        double discount = 0.0;
        if (totalPrice >= 50000.0) {
            discount = calculateDiscount(totalPrice);
        }
        System.out.printf("Total discount(10%%): %.2f \n", discount);

        printInvoice(listOrder);
        System.out.printf("Your final bill total is %.2f - %.2f = Rp %.2f \n", totalPrice, discount, (totalPrice - discount));

        scanner.close();
    }

    private static void printInvoice(List<Order> listOrder) {
        System.out.println("===============================================================");
        System.out.println("This is your invoice");
        for (Order order: listOrder) {
            System.out.printf("%s - %d \n", order.getMenuItem().getName(), order.getQuantity());
        }
    }
}