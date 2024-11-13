package org.example.ArrLst;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.Stream;

public class SortTest {

  @Test
  void hashSort() {
    ArrList<Integer> arrList = new ArrList<>(
        Stream.iterate(16, n -> n - 1).limit(17).toList()
    );
    arrList.hashSort();
    assert arrList.getAt(0).getValue() == 0;
  }

  @Test
  void sort() {
    ArrList<Integer> arrList = new ArrList<>(
        Stream.iterate(16, n -> n - 1).limit(17).toList()
    );
    Sort<Integer> sorter = new Sort<>(Comparator.naturalOrder());
    sorter.sort(arrList);

    assert arrList.getAt(0).getValue() == 0;
  }

  @Test
  void hashSorting() {
    Object[] array = new Object[10];

    for (int i = 0; i < array.length; i++) {
      array[i] = 10 - i;
    }

    HashSort.quicksort(array, 0, array.length - 1);
  }

  @Test
  void Sorting() {
    ArrList<Integer> arrList = new ArrList<>();
    Stream.iterate(12, n -> n - 1).limit(13).forEach( arrList::add );

    Sort<Integer> integerSort = new Sort<>(Comparator.naturalOrder());
    assert arrList.getAt(0).getValue() == 12;
    integerSort.sort(arrList);
    assert arrList.getAt(0).getValue() == 0;
  }

}
