package org.example.ArrLst;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

class ArrListTest {

  @Test
  void add() {
    ArrList<Integer> arrList = new ArrList<>();
    Stream.iterate(0, n -> n + 1).limit(10).forEach(arrList::add);
    assert !arrList.isEmpty();
    assert arrList.size() == 9;
  }

  @Test
  void addAll() {
    ArrList<Integer> arrList = new ArrList<>();
    arrList.addAll(Stream.iterate(0, n -> n + 1).limit(16).toList());
    assert !arrList.isEmpty();
    assert arrList.size() == 15;
  }

  @Test
  void get() {
    ArrList<String> arrList = new ArrList<>();
    // Check the error pair working
    ErrorPair<String, Errors> result = arrList.get();
    assert result.getError().ordinal() != 0;
    // Check the general work of array
    arrList.add("Hello, World!");
    result = arrList.get();
    assert result.getValue().equals("Hello, World!");
    assert result.getError().ordinal() == 0;

    arrList.add("Hello, Mars!");
    result = arrList.get();
    assert result.getValue().equals("Hello, Mars!");
    assert result.getError().ordinal() == 0;
  }

  @Test
  void set() {
    ArrList<String> arrList = new ArrList<>();
    // Error pair working ?
    ErrorPair<String, Errors> result = arrList.set("Hello, World!", 10);
    assert result.getError().ordinal() != 0;

    Stream.iterate("s", n -> n + "s").limit(16).forEach(arrList::add);
    result = arrList.set("Hello, World!", 10);
    assert result.getError().ordinal() == 0;
  }

  @Test
  void getAt() {
  }

  @Test
  void getFirst() {
  }

  @Test
  void getLast() {
  }

  @Test
  void remove() {
  }

  @Test
  void removeAt() {
  }

  @Test
  void isEmpty() {
  }
}