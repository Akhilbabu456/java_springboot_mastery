import java.util.Scanner;

public class SumOfNnumber {

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    System.out.println("Enter the number of values: ");
    int n = s.nextInt();
    System.out.println("Enter the values:");
    int sum = 0;
    for (int i = 0; i < n; i++) {
      int num = s.nextInt();
      sum += num;
    }
    System.out.println("Sum of the values is: " + sum);
    s.close();
  }
}
