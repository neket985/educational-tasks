package ru.smirnov.educational.tinkoff2022;

import java.util.Arrays;
import java.util.Iterator;

public class PreTest_Arrays {

  public static void main(String[] args) {
    int[] arr1 = {1, 2, 5};
    int[] arr2 = {3, 3, 4, 5};
    int[] arr3 = {2, 3, 4, 5, 6};
    int result = findFirstEquality(arr1, arr2, arr3);

    System.out.println(result);
  }

  public static int findFirstEquality(int[] arr1, int[] arr2, int[] arr3) {
    Iterator<Integer> iter1 = Arrays.stream(arr1).iterator();
    Iterator<Integer> iter2 = Arrays.stream(arr2).iterator();
    Iterator<Integer> iter3 = Arrays.stream(arr3).iterator();
    try {
      int v1 = getNextFromIterOrThrow(iter1);
      int v2 = getNextFromIterOrThrow(iter2);
      int v3 = getNextFromIterOrThrow(iter3);

      while (true) {
        if (v1 == v2 && v1 == v3) {
          return v1;
        }

        if (v1 <= v2 && v1 <= v3) {
          v1 = getNextFromIterOrThrow(iter1);
        }
        else if (v2 <= v1 && v2 <= v3) {
          v2 = getNextFromIterOrThrow(iter2);
        }
        else {
          v3 = getNextFromIterOrThrow(iter3);
        }
      }
    }catch (RuntimeException e){
      System.out.println("number is not found");
      return -1;
    }
  }

  public static int getNextFromIterOrThrow(Iterator<Integer> iter) {
    if (!iter.hasNext()) {
      throw new RuntimeException("iterator is ended");
    }

    return iter.next();
  }
}
