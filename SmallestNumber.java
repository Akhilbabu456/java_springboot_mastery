import java.util.Scanner;

public class SmallestNumber {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("ENter the number of elements: ");
        int n = sc.nextInt();
        int[] array = new int[n];
        System.out.println("Enter the numbers: ");
        for(int i = 0; i<n ; i++){
           array[i] = sc.nextInt();
        }
        int smallest = array[0];
        for (int i = 1; i < n; i++) {
            if (array[i] < smallest) {
                smallest = array[i];
            }
        }
        System.out.println("The smallest number is: "+ smallest);
        sc.close();
    }
}
