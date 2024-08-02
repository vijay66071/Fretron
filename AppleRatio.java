import java.util.*;

public class AppleRatio {
    private static String ConvertToString(List<Integer> list) {
        if (list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append("g");
            if (i < list.size() - 1) {
                sb.append(" , ");
            }
        }
        return sb.toString();
    }
    private static void dividingApples(List<Integer> apples, List<Integer> ramApples, List<Integer> shamApples, List<Integer> rahimApples,
                                         int w1, int w2, int w3) {
        for (int weight : apples) {
            if (w1 >= weight) {
                ramApples.add(weight);
                w1 -= weight;
            } else if (w2 >= weight) {
                shamApples.add(weight);
                w2 -= weight;
            } else if (w3 >= weight) {
                rahimApples.add(weight);
                w3 -= weight;
            }
        }
    }
    private static void quickSort(List<Integer> apples, int l, int h) {
        if (l < h) {
            int x = partition(apples, l, h);
            quickSort(apples, l, x - 1);
            quickSort(apples, x + 1, h);
        }
    }

    private static int partition(List<Integer> apples, int l, int h) {
        int pivot = apples.get(h);
        int i = (l - 1);
        for (int j = l; j < h; j++) {
            if (apples.get(j) > pivot) {
                i++;
                int temp = apples.get(i);
                apples.set(i, apples.get(j));
                apples.set(j, temp);
            }
        }
        int temp = apples.get(i + 1);
        apples.set(i + 1, apples.get(h));
        apples.set(h, temp);
        return i + 1;
    }
    
    public static void main(String[] args) {
        // User input for apple weights
        Scanner sc = new Scanner(System.in);
        List<Integer> apples = new ArrayList<>();
        System.out.println("run distribute_apple");
        while (true) {
            System.out.print("Enter apple weight in gram (-1 to stop): ");
            int value = sc.nextInt();
            if (value == -1) {
                break;
            }
            apples.add(value);
        }

        int totalWeight = 0;
        for (int value : apples) {
            totalWeight += value;
        }

        // Amount paid by each person
        int ramPay = 50;
        int shamPay = 30;
        int rahimPay = 20;
        int totalMoney = ramPay + shamPay + rahimPay;

        // Proportions
        double p1 = (double) ramPay / totalMoney;
        double p2 = (double) shamPay / totalMoney;
        double p3 = (double) rahimPay / totalMoney;

        // Weight each person should get
        int w1 = (int) Math.round(p1 * totalWeight);
        int w2 = (int) Math.round(p2 * totalWeight);
        int w3 = (int) Math.round(p3 * totalWeight);

        // Divide apples
        List<Integer> ramApples = new ArrayList<>();
        List<Integer> shamApples = new ArrayList<>();
        List<Integer> rahimApples = new ArrayList<>();

        quickSort(apples, 0, apples.size() - 1);
        dividingApples(apples, ramApples, shamApples, rahimApples, w1, w2, w3);

        // Result
        System.out.println("Distribution Result:");
        System.out.println("Ram : " + ConvertToString(ramApples));
        System.out.println("Sham : " + ConvertToString(shamApples));
        System.out.println("Rahim : " + ConvertToString(rahimApples));
    }
}
