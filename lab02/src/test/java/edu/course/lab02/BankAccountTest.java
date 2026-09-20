package edu.course.lab02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class BankAccountTest {
  private BankAccount account;

  @BeforeEach
  void setup() {
    account = new BankAccount(100);
  }

  @Test
  void onCreateWithBelowZeroBalanceThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> new BankAccount(-100));
  }

  @Test
  void depositAddValueToBalance() {
    int valueBefore = account.getBalance();
    account.deposit(100);
    assertEquals(valueBefore + 100, account.getBalance());
  }

  @ParameterizedTest
  @ValueSource(ints = {-100, 0})
  void depositNonPositiveAmountThrowsIllegalArgumentException(int amount) {
    assertThrows(IllegalArgumentException.class, () -> account.deposit(amount));
  }

  @Test
  void withdrawSubValueToBalance() {
    int valueBefore = account.getBalance();
    account.withdraw(1);
    assertEquals(valueBefore - 1, account.getBalance());
  }

  @ParameterizedTest
  @ValueSource(ints = {-100, 0})
  void withdrawNonPositiveAmountThrowsIllegalArgumentException(int amount) {
    assertThrows(IllegalArgumentException.class, () -> account.withdraw(amount));
  }

  @Test
  void withdrawWithMoreAmountThanOnBalanceNowThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> account.withdraw(account.getBalance() + 100));
  }
}
