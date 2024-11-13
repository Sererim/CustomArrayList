package org.example.ArrLst;

/**
 * Naive quicksort class
 * Compares objects based on their hash value.
 */
class HashSort {

  /**
   * Quicksort function, does in place array sorting, using hash for compartment
   * @param array array Object type
   * @param low begging of the array
   * @param high the end of the array
   */
  public static void quicksort(Object[] array, int low, int high) {
    if (low < high) {
      int pi = partition(array, low, high);
      quicksort(array, low, pi - 1);
      quicksort(array, pi + 1, high);
    }
  }

  private static int partition(Object[] array, int low, int high) {
    int pivotHash = array[high].hashCode();
    int i = (low - 1);
    for (int j = low; j < high; j++) {
      if (array[j].hashCode() <= pivotHash) {
        i++;
        swap(array, i, j);
      }
    }
    swap(array, i + 1, high);
    return i + 1;
  }

  private static void swap(Object[] array, int i, int j) {
    Object temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }
}
