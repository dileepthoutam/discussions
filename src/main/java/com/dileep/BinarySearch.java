package com.dileep;

public class BinarySearch {
  public static int binarySearch(int arr[], int target) {
    int start = 0;
    int end = arr.length - 1;

    int res = -1;

    while (start <= end) {
      int mid = start + (end - start) / 2;
      switch (mid) {
        case int val when arr[val] == target -> {
          res = val;
          break;
        }
        case int val when target < arr[val] -> end = mid - 1;
        case int val when target > arr[val] -> start = mid + 1;
        default -> throw new IllegalArgumentException("Unexpected value: " + mid);
      }
    }

    return res;
  }

  public static int firstOccurrence(int arr[], int target) {
    int start = 0;
    int end = arr.length - 1;

    int res = -1;

    while (start <= end) {
      int mid = start + (end - start) / 2;
      switch (mid) {
        case int val when arr[val] <= target -> {
          res = val;
          end = mid - 1;
        }
        case int val when target > arr[val] -> start = mid + 1;
        default -> throw new IllegalArgumentException("Unexpected value: " + mid);
      }
    }

    return res;
  }

}
