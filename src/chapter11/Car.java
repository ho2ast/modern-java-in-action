package chapter11;

import java.util.Optional;

public class Car {
//  private Insurance insurance;
//
//  public Insurance getInsurance() {
//    return insurance;
//  }

  //  Optional 추가
  private Optional<Insurance> insurance;

  public Optional<Insurance> getInsurance() {
    return insurance;
  }
}
