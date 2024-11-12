package org.example.ArrLst;


import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.function.BiFunction;

/**
 * Simple implementation of ArrayList.
 * Main difference is usage of <strong>ErrorPair</strong> in place of Exceptions
 * <br></br>
 * ErrorPair should be checked, absence of Errors is equal to 0.
 * <br></br>
 * Without checking the getValue function may return <strong>null</strong>
 */
public class ArrList<E> {

  /**
   * Default capacity.
   */
  private static final int DEFAULT_CAPACITY = 10;

  /**
   * For empty array.
   */
  private static final int EMPTY_CAPACITY = 0;

  /**
   * Growing constant.
   * We will grow arr by it.
   */
  private double growingBy = 1.5;

  /**
   * Base array for ArrList.
   */
  private Object[] arr;


  /**
   * Tail pointer.
   */
  private int tail = -1;

  /**
   * Length of the underlying array.
   */
  private int length = EMPTY_CAPACITY;

  /**
   * ErrorPair
   * For handling all returns.
   */
  private ErrorPair<E, Errors> errorPair;

  public ArrList() {
    this.arr = new Object[DEFAULT_CAPACITY];
  }

  public ArrList(int initialCapacity) {
    if (initialCapacity == 0) {
      this.arr = new Object[EMPTY_CAPACITY];
    } else if (initialCapacity > 0) {
      length = initialCapacity;
      this.arr = new Object[length];
    } else {
      throw new IllegalArgumentException("Capacity can not be a negative number!");
    }
  }

  public ArrList(Collection<? extends E> collection) {
    if (collection.isEmpty()) {
      this.arr = new Object[EMPTY_CAPACITY];
    } else {
      length = collection.size();
      this.arr = new Object[length];
      collection.forEach(this::add);
    }
  }

  /**
   * Add an element to the ArrList.
   *
   * @param element - element of type E.
   */
  public void add(E element) {
    if (tail == length - 1) {
      grow();
    }
    // Increment tail.
    tail++;
    // Add element
    arr[tail] = element;
  }

  public void addAll(Collection<? extends E> collection) {
    collection.forEach(this::add);
  }

  /**
   * Get object from the tail of the ArrList, <strong>Does not removes it from the ArrList</strong>
   * @return ErrorPair
   */
  @SuppressWarnings("unchecked")
  public ErrorPair<E, Errors> get() {
    if (tail == -1) {
      return new ErrorPair<>(null, Errors.NULL_VALUE);
    } else {
      return new ErrorPair<>((E) arr[tail], Errors.NO_ERROR);
    }
  }

  /**
   * Set value at specified index.
   * @param element - to be put into the array
   * @param index - where in the array
   * @return ErrorPair for <strong>both success and error</strong>.
   */
  public ErrorPair<E, Errors> set(E element, int index) {
    if (index >= 0 && index <= tail) {
      arr[index] = element;
      return new ErrorPair<>(null, Errors.NO_ERROR);
    } else {
      return getError(Errors.INDEX_OUT_OF_BOUNDS);
    }
  }

  /**
   * Get element at specified <strong>index</strong>
   * @param index - index of an element
   * @return ErrorPair
   */
  @SuppressWarnings("unchecked")
  public ErrorPair<E, Errors> getAt(int index) {
    if (index >= 0 && index <= tail) {
      return new ErrorPair<>((E) arr[index], Errors.NO_ERROR);
    } else {
      return getError(Errors.INDEX_OUT_OF_BOUNDS);
    }
  }

  @SuppressWarnings("unchecked")
  public ErrorPair<E, Errors> getFirst() {
    if (tail != -1) {
      return new ErrorPair<>((E) arr[0], Errors.NO_ERROR);
    } else {
      return getError(Errors.NO_SUCH_ELEMENT);
    }
  }

  @SuppressWarnings("unchecked")
  public ErrorPair<E, Errors> getLast() {
    if (tail != -1) {
      return new ErrorPair<>((E) arr[tail], Errors.NO_ERROR);
    } else {
      return getError(Errors.NO_SUCH_ELEMENT);
    }
  }

  /**
   * Remove an element from the tail of the ArrList
   * @return - ErrorPair with value and error. <strong>User should check the ErrorPair</strong>
   */
  @SuppressWarnings("unchecked")
  public ErrorPair<E, Errors> remove() {
    if (tail == -1) {
      return getError(Errors.NULL_VALUE);
    } else {
      return new ErrorPair<>((E) arr[tail--], Errors.NO_ERROR);
    }
  }

  public ErrorPair<E, Errors> removeAt(int index) {
    if (index >= 0 && index <= tail) {
      // TODO finish the method
      return null;
    } else {
      return getError(Errors.INDEX_OUT_OF_BOUNDS);
    }
  }

  /**
   * Method to set growthRate if needed.
   * Default growth rate is 1.5
   *
   * @param growthRate double that specifies how much should array grow when tail is equal length.
   */
  public void setGrowthRate(double growthRate) {
    growingBy = growthRate;
  }

  /**
   * Private method that grows array with specified growth rate.
   */
  private void grow() {
    length = ((int) (growingBy * length)) + 1;
    arr = copyArray.apply(arr, length);
  }

  /**
   * Function to regenerate the array after an element removal.
   */
  private void regenerate() {
    return;
  }

  public Boolean isEmpty() {
    return tail == -1;
  }

  private ErrorPair<E, Errors> getError(Errors err) {
    return new ErrorPair<>(null, err);
  }

  /**
   * Convenient function to make a copy of an array.
   */
  private static final BiFunction<Object[], Integer, Object[]> copyArray = (source, newSize) -> {
    Object[] newArray = new Object[newSize];
    System.arraycopy(source, 0, newArray, 0, source.length);
    return newArray;
  };
}