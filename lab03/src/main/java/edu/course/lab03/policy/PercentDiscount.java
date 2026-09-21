package edu.course.lab03.policy;

public class PercentDiscount implements DiscountPolicy {
  private final double percent;

  public PercentDiscount(double percent) {
    if (percent < 0 || percent > 100) {
      throw new IllegalArgumentException("Percent must be between 0 and 100");
    }
    this.percent = percent;
  }

  @Override
  public double apply(double price) {
    if (price < 0) {
      throw new IllegalArgumentException("Price is below zero");
    }
    return price * (1 - percent / 100);
  }
}
