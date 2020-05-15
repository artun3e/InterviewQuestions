// Artun Sarioglu

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class maxSum {

    public static final int ROWS = 15; // change it according to the file
    public static final int COLUMNS = 15; // change it according to the file

    public static int[][] pyramid = new int[ROWS][COLUMNS];

    /*
     A simple function to decide whether 'number' is prime or not
     */
    private static boolean isPrime(int number) {

        for (int i = 2; i < number; ++i)
            if ((number % i) == 0)
                return false;
        return true;
    }




    private static void processfile(String fileName, int[][] pyramid) {

        try {
            //System.out.println("inside read numbers from file function"); --debug purposes
            InputStream input = new FileInputStream(fileName);
            Scanner scanner = new Scanner(input);

            int index = 0;

            // iterate through file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] numbers = line.split("\\s+");
                for (int i = 0; i < numbers.length; ++i) {
                    //System.out.println("numbers are"); -- debug purposes
                    //System.out.println(numbers[i]); --debug purposes
                    pyramid[index][i] = Integer.parseInt(numbers[i]);
                }
                index++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found, please check your project folder or path!");
            java.lang.System.exit(1);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Please check your input file or row and column sizes!");
            java.lang.System.exit(1);
        }
    }

    public static int findMaxSum(String fileName, ArrayList<Location> path) {

        processfile(fileName, pyramid); // process the file
        // fit the lines into array

        if (isPrime(pyramid[0][0]))
            return 0;
         else
            return maxSumNaive(0, 0, ROWS, path);

    }

    /*
     -x,y denotes the current index
     */

    // Naive Recursive approach to the problem
    private static int maxSumNaive(int x, int y, int size, ArrayList<Location> path) {


        int maxSumLeft = 0;
        int maxSumRight = 0;

        ArrayList<Location> leftPath = new ArrayList<>();
        ArrayList<Location> rightPath = new ArrayList<>();

        if (x >= size)
            return 0;

         else if (x == (size - 1)) {
            path.add(new Location(x, y, pyramid[x][y]));
            return pyramid[x][y];
        }



        // traverse through all possible paths -> left || right
        if (!isPrime(pyramid[x + 1][y]))
            maxSumRight = maxSumNaive(x + 1, y, size, rightPath);
        if (!isPrime(pyramid[x + 1][y + 1]))
            maxSumLeft = maxSumNaive(x + 1, y + 1, size, leftPath);
        if (isPrime(pyramid[x + 1][y + 1]) && isPrime(pyramid[x + 1][y]))
            return Integer.MIN_VALUE;


        // trace the path
        if (maxSumLeft >= maxSumRight) {
            path.addAll(leftPath);
            path.add(new Location(x, y, pyramid[x][y]));
            return maxSumLeft + pyramid[x][y];
        }
        else {
            path.addAll(rightPath);
            path.add(new Location(x, y, pyramid[x][y]));
            return maxSumRight + pyramid[x][y];
        }
    }





    /*
     * A struct to keep information about the coordinates
     */
    private static class Location {
        private int x; // x axis of the corresponding point
        private int y; // y oordinate of the corresponding point
        private int number; // number

        public Location(int x, int y, int number) {
            this.x = x;
            this.y = y;
            this.number = number;
        }

        // Getters
        // Setters are not necessary!
        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getNumber() {
            return number;
        }
    }


    public static void main(String args[]) {

        ArrayList<Location> path = new ArrayList<>();

        System.out.printf("\nMaximum sum for the given file is: %d", findMaxSum("/Users/artun/Desktop/maxSum/resources/piworks_technical.txt", path));


        System.out.println("\n\nBelow path provides the maximum sum with NON PRIME integers \n");
        for (int i = path.size() - 1; i >= 0; --i) {
            if(i == 0)
                System.out.print(path.get(i).number );
            else
                System.out.print(path.get(i).number + " -> ");
        }


    }
}
