package edu.course.lab03;

import edu.course.lab03.policy.DiscountPolicy;
import edu.course.lab03.policy.NoDiscount;
import edu.course.lab03.policy.PercentDiscount;
import edu.course.lab03.policy.PriceCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountPolicyTest {

  @Test
  public void noDiscountPolicyReturnsEqualsToPriceValue() {
    DiscountPolicy policy = new NoDiscount();
    double noDiscountValue = 100.0;
    assertEquals(noDiscountValue, policy.apply(noDiscountValue), 1e-9);
  }

  @Test
  public void percentDiscountReturnsPriceMultipliedOnDiscount() {
    double discount = 20.0;
    double price = 100.0;
    DiscountPolicy policy = new PercentDiscount(discount);
    assertEquals((1.0 - discount / 100.0) * price, policy.apply(price));
  }

  @Test
  public void changePolicyInPriceCalculatorReturnAnotherValue() {
    double discount = 20.0;
    double price = 100.0;
    PriceCalculator calculator = new PriceCalculator(new NoDiscount());
    assertEquals(price, calculator.calculatePrice(price));
    calculator.setPolicy(new PercentDiscount(discount));
    assertEquals((1.0 - discount / 100.0) * price, calculator.calculatePrice(price));
  }
}
