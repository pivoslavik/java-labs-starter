package edu.course.lab02;

public class BankAccount {
  private int balance;

  public BankAccount(int balance) {
    if (balance < 0) {
      throw new IllegalArgumentException("balance is below zero");
    }
    this.balance = balance;
  }

  public void deposit(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount is zero or below zero");
    }
    balance += amount;
  }

  public void withdraw(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount is zero or below zero");
    }
    if (balance - amount < 0) {
      throw new IllegalArgumentException("balance is lower than withdraw amount");
    }
    balance -= amount;
  }

  public int getBalance() {
    return balance;
  }
}
