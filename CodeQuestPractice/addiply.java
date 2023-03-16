import java.util.Scanner;

public class addiply {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCases = sc.nextInt();
        for (int i = 0; i < testCases; i++) {
            int num1 = sc.nextInt();
            int num2 = sc.nextInt();
            int sum = num1 + num2;
            int product = num1 * num2;
            System.out.println(sum + " " + product);
        }
    }
}
