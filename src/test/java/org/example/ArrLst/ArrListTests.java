package org.example.ArrLst;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class ArrListTest {

  @Test
  void add() {
    ArrList<Integer> arrList = new ArrList<>();
    Stream.iterate(0, n -> n + 1).limit(10).forEach(arrList::add);
    assert !arrList.isEmpty();
    assert arrList.size() == 10;
  }

  @Test
  void addAll() {
    ArrList<Integer> arrList = new ArrList<>();
    arrList.addAll(Stream.iterate(0, n -> n + 1).limit(16).toList());
    assert !arrList.isEmpty();
    assert arrList.size() == 16;
  }

  @Test
  void get() {
    ArrList<String> arrList = new ArrList<>();
    // Check the error pair working
    ErrorPair<String> result = arrList.get();
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
    ErrorPair<String> result = arrList.set("Hello, World!", 10);
    assert result.getError().ordinal() != 0;

    Stream.iterate("s", n -> n + "s").limit(16).forEach(arrList::add);
    result = arrList.set("Hello, World!", 10);
    assert result.getError().ordinal() == 0;
  }

  @Test
  void getAt() {
    ArrList<String> arrList = new ArrList<>();

    ErrorPair<String> errorPair = arrList.getAt(10);
    assert errorPair.getError().ordinal() != 0;
    assert errorPair.getValue() == null;

    arrList.addAll(Stream.iterate("Ello, Orld!", s -> s + "\n").limit(10).toList());
    errorPair = arrList.getAt(9);
    assert errorPair.getError().ordinal() == 0;
    assert !errorPair.getValue().isEmpty();
  }

  @Test
  void getFirst() {
    ArrList<Character> arrList = new ArrList<>();

    ErrorPair<Character> errorPair = arrList.getFirst();
    assert errorPair.getError().ordinal() != 0;
    assert errorPair.getValue() == null;

    IntStream.rangeClosed('a', 'z').mapToObj(c -> (char) c).forEach(arrList::add);
    errorPair = arrList.getFirst();
    assert errorPair.getError().ordinal() == 0;
    assert errorPair.getValue().equals('a');
  }

  @Test
  void getLast() {
    ArrList<String> arrList = new ArrList<>();

    ErrorPair<String> errorPair = arrList.getLast();
    assert errorPair.getError().ordinal() != 0;
    assert errorPair.getValue() == null;

    Stream.iterate("", str -> str + "Hello, World!\n\r\n\r").limit(10).forEach(arrList::add);
    errorPair = arrList.getLast();
    assert errorPair.getError().ordinal() == 0;
    assert !errorPair.getValue().isEmpty();
  }

  @Test
  void remove() {
    // Check remove method working for arrlist
    ArrList<Float> arrList = new ArrList<>();
    ErrorPair<Float> errorPair = arrList.remove();
    assert errorPair.getError().ordinal() != 0;
    assert errorPair.getValue() == null;

    // Check remove for non-empty arraylist
    arrList.addAll(Stream.iterate(0.0F, n -> n + 0.1F).limit(10).toList());
    errorPair = arrList.remove();
    assert errorPair.getValue().equals(0.9000001F); // IEEE 754 :)
    assert errorPair.getError().ordinal() == 0;
  }

  @Test
  void removeAt() {
    ArrList<String> arrList = new ArrList<>();
    ErrorPair<String> errorPair = arrList.removeAt(10);
    assert errorPair.getError().ordinal() != 0;

    arrList.addAll(Stream.iterate("", str -> str + "s").limit(16).toList());

    errorPair = arrList.removeAt(10);
    assert errorPair.getError().ordinal() == 0;
    assert !errorPair.getValue().isEmpty();
  }

  @Test
  void isEmpty() {
    ArrList<Double> arrList = new ArrList<>();
    assert arrList.isEmpty();

    arrList.add(42.0D);
    assert !arrList.isEmpty();
  }

  @Test
  void size() {
    ArrList<Integer> arrList = new ArrList<>();
    assert arrList.size() == 0;

    arrList.addAll(Stream.iterate(0, n -> n + 10).limit(13).toList());
    assert arrList.size() == 13;
  }

  @Test
  void getLength() {
    ArrList<Integer> arrList = new ArrList<>();
    assert arrList.getLength() == 10;

    arrList.addAll(Stream.iterate(0, n -> n + 10).limit(13).toList());
    assert arrList.getLength() == 16;
  }

  /**
   * Test the iterator correctness,
   * We will use foreach loop for that.
   */
  @Test
  void iteratorTest() {
    ArrList<Integer> arrList = new ArrList<>();
    arrList.addAll(Stream.iterate(0, n -> n + 1).limit(10).toList());

    Integer num = 0;
    for (Integer integer : arrList) {
      num += integer;
    }
    assert num.equals(Stream.iterate(0, n -> n + 1).limit(10).reduce(0, Integer::sum));
  }

  @Test
  void setGrowthRate() {
    ArrList<Integer> arrList = new ArrList<>();
    arrList.setGrowthRate(2.5D);

    Stream.iterate(0, n -> n + 1).limit(16).forEach(arrList::add);
    assert arrList.getLength() == 26;
  }

  @Test
  void testClone() {
    ArrList<Integer> arrList = new ArrList<>();
    Stream.iterate(0, n -> n + 1).limit(16).forEach(arrList::add);

    ArrList<Integer> cloneList = arrList.clone();
    assert !cloneList.equals(arrList);

    for (int i = 0; i < arrList.size(); i++) {
      assert Objects.equals(arrList.getAt(i).getValue(), cloneList.getAt(i).getValue());
    }
  }

  @Test
  void testRegenerate() {
    ArrList<Integer> arrList = new ArrList<>();
    Stream.iterate(0, n -> n + 1).limit(16).forEach(arrList::add);

    ErrorPair<Integer> errorPair = arrList.removeAt(12);
    assert errorPair.getValue() == 12;
    assert errorPair.getError().ordinal() == 0;
    assert arrList.size() == 15;

    errorPair = arrList.removeAt(12);
    assert errorPair.getValue() == 13;
    assert errorPair.getError().ordinal() == 0;
    assert arrList.size() == 14;

    errorPair = arrList.removeAt(12);
    assert errorPair.getValue() == 14;
    assert errorPair.getError().ordinal() == 0;
    assert arrList.size() == 13;

    errorPair = arrList.removeAt(12);
    assert errorPair.getError().ordinal() == 0;
    assert errorPair.getValue() != null;
    assert arrList.size() == 12;

    errorPair = arrList.removeAt(12);
    assert errorPair.getError().ordinal() != 0;
    assert errorPair.getValue() == null;
    // Because we can't remove the 11th element so size is still 12.
    assert arrList.size() == 12;
  }

  @Test
  void hashSort() {
    ArrList<Integer> arrList = new ArrList<>(Stream.iterate(16, n -> n - 1).limit(17).toList());

    // Test hashSort
    assert arrList.getAt(0).getValue() == 16;
    arrList.hashSort();
    assert arrList.getAt(0).getValue() == 0;
  }

  @Test
  void sort() {
    ArrList<Character> arrList = new ArrList<>(
        IntStream.iterate('z', n -> n - 1).limit(26).mapToObj(c -> (char) c).toList()
    );
    assert arrList.getAt(0).getValue() == 'z';
    arrList.sort(Comparator.naturalOrder());
    assert arrList.getAt(0).getValue() == 'a';
  }

  @Test
  void clean() {
    ArrList<Character> arrList = new ArrList<>(
        IntStream.iterate('a', n -> n + 1).limit(26).mapToObj(c -> (char) c).toList()
    );
    assert arrList.get().getValue() != null;
    assert !arrList.isEmpty();

    arrList.clean();
    assert arrList.get().getValue() == null;
    assert arrList.isEmpty();
  }
}