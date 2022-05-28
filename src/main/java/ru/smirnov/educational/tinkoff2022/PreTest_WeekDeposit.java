package ru.smirnov.educational.tinkoff2022;

import java.util.Scanner;

public class PreTest_WeekDeposit {

  public static void main(String[] args) {
    while(true) {
      Scanner scanner = new Scanner(System.in);
      System.out.println(calculateDeposit(scanner.nextInt()));
    }
  }

  public static int calculateDeposit(int days) {
    if(days<=0) return 0;

    int fullWeeksCount = (days / 7);
    int lastWeekDays = days % 7;

    int fullWeeksDeposit = calculateDepositForFullNWeeks(fullWeeksCount);
    int lastWeekDeposit = calculateDepositForLastWeek(fullWeeksCount + 1, lastWeekDays);
    return fullWeeksDeposit + lastWeekDeposit;
  }

  private static final int FULL_WEEK_DEPOSIT_FROM_ZERO = (0 + 6) * 7 / 2;
  private static int calculateDepositForFullNWeeks(int weeksCount) {
    return weeksCount * FULL_WEEK_DEPOSIT_FROM_ZERO + 7 * ((1 + weeksCount) * weeksCount / 2);
  }
  private static int calculateDepositForLastWeek(int lastWeekNumber, int lastWeekDays) {
    return lastWeekNumber * lastWeekDays + (0 + lastWeekDays - 1) * lastWeekDays / 2;
  }
}
