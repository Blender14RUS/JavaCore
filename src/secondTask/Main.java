package secondTask;

public class Main {
    public static void main(String[] args){
        int[][] array = {{0,1,2,3},
                         {4,5,6,7},
                         {8,9,10,11},
                         {12,13,14,15},
                         {16,17,18,19},
                         {20,21,22,23},
                         {24,25,26,27},
                         {28,29,30,31}};

        int[][] array2 = {{0,1,2,3,4,5,5},
                         {5,6,7,8,9,6,6},
                         {10,11,12,13,14,7,7}};

        int[][] array3 = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                          {10,11,12,14,15,16,17,18,19,20},
                          {21,22,23,24,25,26,27,28,29,30},
                          {31,32,33,34,35,36,37,38,39,40},
                          {41,42,43,44,45,46,47,48,49,50},
                          {51,52,53,54,55,56,57,58,59,60},
                          {61,62,63,64,65,66,67,68,69,70},
                          {71,72,73,74,75,76,77,78,79,80},
                          {81,82,83,84,85,86,87,88,89,90},
                          {91,92,93,94,95,96,97,98,99,100}};
        spiral(array3);
    }

    static public void spiral(int[][] arr){
        int left = 0, right = arr[0].length;
        int top = 0, bottom = arr.length -1;
        int count = 0;
       while (count != (arr.length*arr[0].length)) {
            for (int i = left; i < right; i++) {
                System.out.print(arr[top][i] + " ");
                count++;
            }
           if (count == (arr.length*arr[0].length)) continue;
            top++;
            for (int i = top; i <= bottom; i++) {
                System.out.print(arr[i][right-1] + " ");
                count++;
            }
           if (count == (arr.length*arr[0].length)) continue;
           right--;
            for (int i = right-1; i >= left; i--) {
                System.out.print(arr[bottom][i] + " ");
                count++;
            }
           if (count == (arr.length*arr[0].length)) continue;
            bottom--;
            for (int i = bottom; i >= top; i--) {
                System.out.print(arr[i][left] + " ");
                count++;
            }
           if (count == (arr.length*arr[0].length)) continue;
            left++;
       }
    }
}
