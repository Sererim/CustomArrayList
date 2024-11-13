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
}
