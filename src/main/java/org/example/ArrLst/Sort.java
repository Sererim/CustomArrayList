package org.example.ArrLst;

import java.util.Comparator;

/**
 * Quicksort class that uses <strong>Comparator</strong> provided by the user
 * @param <E>
 */
class Sort<E> {

  /**
   * Comparator
   */
  private final Comparator<? super E> comparator;

  /**
   * Constructor that requires a valid comparator.
   * @param comparator - a valid comparator for the elements of the collection.
   */
  public Sort(Comparator<? super  E> comparator) {
    this.comparator = comparator;
  }

  /**
   * Main starting method does in place sort of the ArrList
   * @param arrList - ArrList to sort.
   */
  public void sort(ArrList<E> arrList) {
    quicksort(arrList, 0, arrList.size() - 1);
  }

  public void quicksort(ArrList<E> array, int low, int high) {
    if (low < high) {
      int pi = partition(array, low, high);
      quicksort(array, low, pi - 1);
      quicksort(array, pi + 1, high);
    }
  }

  private int partition(ArrList<E> array, int low, int high) {
    E pivot = array.getAt(high).getValue();
    int i = low - 1;
    for (int j = low; j < high; j++) {
      if (comparator.compare(array.getAt(j).getValue(), pivot) <= 0) {
        i++;
        swap(array, i, j);
      }
    }
    swap(array, i + 1, high);
    return i + 1;
  }

  private void swap(ArrList<E> array, int i, int j) {
    E temp = array.getAt(i).getValue();
    array.set(array.getAt(j).getValue(), i);
    array.set(temp, j);
  }

}
