package com.javacore;

//only symmetrical multidimensional arrays
public class Spiral {
    public void print(int[][] arr){
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
