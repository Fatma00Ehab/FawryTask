package service;

import model.Shippable;
import java.util.*;

public class ShippingService {

    private static class ItemSummary {
        double weightPerItem;
        int count;

        public ItemSummary(double weightPerItem) {
            this.weightPerItem = weightPerItem;
            this.count = 1;
        }

        public void increment() {
            count++;
        }

        public double totalWeight() {
            return count * weightPerItem;
        }
    }

    public static void ship(List<Shippable> items) {
        System.out.println("- * Shipment notice **\n");

        Map<String, ItemSummary> summaryMap = new LinkedHashMap<>();
        for (Shippable item : items) {
            // String key = item.getName();
            String key = item.getName() + "|" + String.format("%.2f", item.getWeight());

            if (summaryMap.containsKey(key)) {
                summaryMap.get(key).increment();
            } else {
                summaryMap.put(key, new ItemSummary(item.getWeight()));
            }
        }

        double totalWeight = 0;

        for (Map.Entry<String, ItemSummary> entry : summaryMap.entrySet()) {
            String[] parts = entry.getKey().split("\\|");
            String name = parts[0];
            ItemSummary summary = entry.getValue();
            int count = summary.count;
            double combinedWeight = summary.totalWeight();

            System.out.printf("%dx %-15s %.0fg%n", count, name, combinedWeight * 1000);
            totalWeight += combinedWeight;
        }

        System.out.printf("%nTotal package weight %.1fkg%n%n", totalWeight);
    }
}
