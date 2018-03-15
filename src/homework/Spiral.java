package homework;

import java.util.Random;

//only symmetrical multidimensional arrays
public class Spiral {
    public int[][] testArray() {
        Random random = new Random();
        int rowCount = 3 + random.nextInt(10);
        int colCount = 3 + random.nextInt(10);
        int[][] resultArray = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                resultArray[row][col] = 1 + random.nextInt(99);
            }
        }

        for (int[] row : resultArray) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
        System.out.println();
        return resultArray;
    }

    public void print(int[][] arr) {
        int left = 0, right = arr[0].length;
        int top = 0, bottom = arr.length - 1;
        int elementCount = 0;
        while (elementCount != (arr.length * arr[0].length)) {
            for (int i = left; i < right; i++) {
                System.out.print(arr[top][i] + " ");
                elementCount++;
            }
            if (elementCount == (arr.length * arr[0].length)) continue;
            top++;
            for (int i = top; i <= bottom; i++) {
                System.out.print(arr[i][right - 1] + " ");
                elementCount++;
            }
            if (elementCount == (arr.length * arr[0].length)) continue;
            right--;
            for (int i = right - 1; i >= left; i--) {
                System.out.print(arr[bottom][i] + " ");
                elementCount++;
            }
            if (elementCount == (arr.length * arr[0].length)) continue;
            bottom--;
            for (int i = bottom; i >= top; i--) {
                System.out.print(arr[i][left] + " ");
                elementCount++;
            }
            if (elementCount == (arr.length * arr[0].length)) continue;
            left++;
        }
    }

}
