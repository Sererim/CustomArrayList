package org.example.ArrLst;


import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Simple implementation of ArrayList.
 * Main difference is usage of <strong>ErrorPair</strong> in place of Exceptions
 * <br></br>
 * ErrorPair should be checked, absence of Errors is equal to 0.
 * <br></br>
 * Without checking the getValue function may return <strong>null</strong>
 */
public class ArrList<E>
    implements Iterable<E>, RandomAccess, Cloneable {

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
   * Base constructor uses DEFAULT_CAPACITY for the starting array length.
   */
  public ArrList() {
    this.arr = new Object[DEFAULT_CAPACITY];
    this.length = DEFAULT_CAPACITY;
  }

  /**
   * Constructor that accepts a parameter for the starting array length.
   * @param initialCapacity - initial capacity for the array
   * @throws  IllegalArgumentException if initialCapacity is negative
   */
  public ArrList(int initialCapacity) {
    if (initialCapacity == 0) {
      this.arr = new Object[EMPTY_CAPACITY];
    } else if (initialCapacity > 0) {
      this.length = initialCapacity;
      this.arr = new Object[this.length];
    } else {
      throw new IllegalArgumentException("Capacity can not be a negative number!");
    }
  }

  /**
   * Constructor that accepts a class that implements Collection interface
   * @param collection any class that implements Collection interface
   *                   uses the length of the collection as starting length of the array.
   */
  public ArrList(Collection<? extends E> collection) {
    if (collection.isEmpty()) {
      this.arr = new Object[EMPTY_CAPACITY];
    } else {
      this.length = collection.size();
      this.arr = new Object[length];
      collection.forEach(this::add);
    }
  }

  /**
   * Get the size of the array.
   *
   * @return - size of the array with elements
   */
  public int size() {
    return tail + 1;
  }

  /**
   * Get the length of the array.
   * Length is the length of the array with and without elements.
   * @return - length of the array
   */
  public int getLength() {
    return this.length;
  }

  /**
   * Add an element to the ArrList.
   *
   * @param element - element of type E.
   */
  public void add(E element) {
    if (this.tail == this.length - 1) {
      grow();
    }
    // Increment tail.
    this.tail++;
    // Add element
    this.arr[this.tail] = element;
  }

  /**
   * Adds entire collection to the array, if needed the array will be expanded.
   * @param collection any class that implements the Collection interface.
   */
  public void addAll(Collection<? extends E> collection) {
    collection.forEach(this::add);
  }

  /**
   * Get object from the tail of the ArrList, <strong>Does not removes it from the ArrList</strong>
   *
   * @return ErrorPair
   */
  @SuppressWarnings ("unchecked")
  public ErrorPair<E> get() {
    if (this.tail == -1) {
      return new ErrorPair<>(null, Errors.NULL_VALUE);
    } else {
      return new ErrorPair<>((E) this.arr[this.tail], Errors.NO_ERROR);
    }
  }

  /**
   * Set value at specified index.
   *
   * @param element - to be put into the array
   * @param index   - where in the array
   * @return ErrorPair for <strong>both success and error</strong>.
   */
  public ErrorPair<E> set(E element, int index) {
    if (index >= 0 && index <= this.tail) {
      this.arr[index] = element;
      return new ErrorPair<>(null, Errors.NO_ERROR);
    } else {
      return getError(Errors.INDEX_OUT_OF_BOUNDS);
    }
  }

  /**
   * Get element at specified <strong>index</strong>
   *
   * @param index - index of an element
   * @return ErrorPair
   */
  @SuppressWarnings ("unchecked")
  public ErrorPair<E> getAt(int index) {
    if (index >= 0 && index <= this.tail) {
      return new ErrorPair<>((E) this.arr[index], Errors.NO_ERROR);
    } else {
      return getError(Errors.INDEX_OUT_OF_BOUNDS);
    }
  }

  @SuppressWarnings ("unchecked")
  public ErrorPair<E> getFirst() {
    if (this.tail != -1) {
      return new ErrorPair<>((E) this.arr[0], Errors.NO_ERROR);
    } else {
      return getError(Errors.NO_SUCH_ELEMENT);
    }
  }

  @SuppressWarnings ("unchecked")
  public ErrorPair<E> getLast() {
    if (this.tail != -1) {
      return new ErrorPair<>((E) this.arr[this.tail], Errors.NO_ERROR);
    } else {
      return getError(Errors.NO_SUCH_ELEMENT);
    }
  }

  /**
   * Remove an element from the tail of the ArrList
   *
   * @return - ErrorPair with value and error. <strong>User should check the ErrorPair</strong>
   */
  @SuppressWarnings ("unchecked")
  public ErrorPair<E> remove() {
    if (this.tail == -1) {
      return getError(Errors.NULL_VALUE);
    } else {
      return new ErrorPair<>((E) this.arr[this.tail--], Errors.NO_ERROR);
    }
  }

  /**
   * Remove element at specified index
   *
   * @param index - element's index
   * @return Error pair
   */
  @SuppressWarnings ("unchecked")
  public ErrorPair<E> removeAt(int index) {
    if (index >= 0 && index <= this.tail) {
      ErrorPair<E> pair = new ErrorPair<>((E) this.arr[index], Errors.NO_ERROR);
      // Write null to the index position
      this.arr[index] = null;
      // Regenerate the array to make it continuous again. Doesn't change the length of the array.
      regenerate(index);
      // We should decrement tail
      tail--;
      return pair;
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
    this.growingBy = growthRate;
  }

  /**
   * Private method that grows array with specified growth rate.
   */
  private void grow() {
    this.length = ((int) (this.growingBy * this.length)) + 1;
    this.arr = copyArray.apply(this.arr, this.length);
  }

  /**
   * Function to regenerate the array after an element removal.
   */
  private void regenerate(int index) {
    if (index != this.tail) {
      Object[] newArr = new Object[length];
      System.arraycopy(this.arr, 0, newArr, 0, index);
      System.arraycopy(this.arr, index + 1, newArr, index, this.length - index - 1);
      this.arr = newArr;
    }
  }

  public Boolean isEmpty() {
    return this.tail == -1;
  }

  private ErrorPair<E> getError(Errors err) {
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

  @Override
  public Iterator<E> iterator() {
    return new ArrListIterator();
  }

  /**
   * Method to create a clone of the arrList
   * @return a clone of the object
   * @throws RuntimeException - if clone is not supported
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrList<E> clone() {
    try {
      return (ArrList<E>) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Internal class for Iterator.
   */
  private class ArrListIterator implements Iterator<E> {

    private int currentIndex = 0;

    @Override
    public boolean hasNext() {
      return currentIndex <= tail && currentIndex != -1;
    }

    @SuppressWarnings ("unchecked")
    @Override
    public E next() {
      return (E) arr[currentIndex++];
    }
  }

  @Override
  public void forEach(Consumer<? super E> action) {
    Iterable.super.forEach(action);
  }

  public void hashSort() {
    if (tail >= 2) {
      HashSort.quicksort(arr, 0, this.tail);
    }
  }

  public void sort(Comparator<? super E> comparator) {
    Sort<E> sorter = new Sort<>(comparator);
    sorter.sort(this);
  }
}