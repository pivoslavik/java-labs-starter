package edu.course.lab03.policy;

public class PriceCalculator {
  private DiscountPolicy policy;

  public PriceCalculator(DiscountPolicy policy) {
    if (policy == null) {
      throw new IllegalArgumentException("Policy is null");
    }
    this.policy = policy;
  }

  public void setPolicy(DiscountPolicy policy) {
    if (policy == null) {
      throw new IllegalArgumentException("Policy is null");
    }
    this.policy = policy;
  }

  public double calculatePrice(double price) {
    return policy.apply(price);
  }
}
