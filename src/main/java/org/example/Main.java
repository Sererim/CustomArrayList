package org.example;


import org.example.ArrLst.ArrList;

import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    ArrList<Integer> arrList = new ArrList<>(25);
    arrList.add(10);
    arrList.getAt(0);
  }
}