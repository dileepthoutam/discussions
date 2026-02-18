package com.dileep.parkinglot;

import java.util.List;

public class ParkingLot {
  private static ParkingLot instance;

  public static synchronized ParkingLot getInstance() {
    if (instance == null) {
      return new ParkingLot();
    }
    return instance;
  }
}
