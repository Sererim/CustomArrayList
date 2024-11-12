package org.example.ArrLst;


import static org.example.ArrLst.Errors.*;

/**
 * Class for ErrorPairs, like in Go, ErrorPair consists of an object with value and possible Error,
 * if no Error is present it has 0.
 * @param <K> - Value part of ErrorPair
 * @param <Errors> - Error part of ErrorPair
 */
public class ErrorPair<K, Errors> {

  /**
   * Value part of ErrorPair
   */
  private final K value;

  /**
   * Error part of ErrorPair
   */
  private final Errors err;

  /**
   * Package only constructor for Pair
   */
  ErrorPair(K value, Errors err) {
    this.value = value;
    this.err = err;
  }

  /**
   * Returns the value part of ErrorPair
   * @return - value part of ErrorPair, or if error is present returns <strong>null</strong>.
   */
  public K getValue() {
    if (err == NO_ERROR) {
      return value;
    } else {
      return null;
    }
  }

  public Errors getError() {
    return err;
  }
}
