package edu.course.lab03.policy;

public class NoDiscount implements DiscountPolicy{
  @Override
  public double apply(double price) {
    if (price < 0) {
      throw new IllegalArgumentException("Price is below zero");
    }
    return price;
  }
}
