import java.util.Arrays;

public class Second{
    public static void main(String[] args) {
        int[] numbers = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11};
        Arrays.sort(numbers);
        System.out.println("Sorted numbers in ascending order:");
        for (int number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println(); 
        int sumHighestTwo = numbers[numbers.length - 1] + numbers[numbers.length - 2];
        System.out.println("Sum of the two highest numbers: " + sumHighestTwo);

        int highestNumber1 = numbers[numbers.length - 1];
        int highestNumber2 = numbers[numbers.length - 2];
        System.out.println("Highest numbers: " + highestNumber1 + " and " + highestNumber2);
    }
}

/* first I added all number in array 
    after that I sorted all the numbers in ascending order
    then I added last two number as in ascending order they are the highest number
    I got the value of the two number added and I printed the last two number
 */