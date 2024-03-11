package com.example.geektrust;

public class Calculation {

    private static double balance;
    private static final int zeroN = 0;
    private static  final double extraFee = 0.02;
    public static Integer calculateRemainingBalance (String person, double balance1, double discount) {

        double computedSum =computeAmountForAge(person);

        if (discount > zeroN) {
            computedSum = discount * computedSum;
        }
        if (balance1 < computedSum) {
            computedSum = (int) (computedSum + (computedSum - balance1) * extraFee);
            int amountNeeded = (int) (computedSum - balance1);
            balance = (computedSum - (balance1 + amountNeeded));

        } else {
            balance = balance1 - computedSum;
        }
        return (int) balance;
    }

    public static double computeAmountForAge(String person){
        double computedSum;
        switch (person) {
            case "ADULT":
                computedSum = 200;

                break;
            case "SENIOR_CITIZEN":
                computedSum = 100;
                break;
            case "KID":
                computedSum = 50;
                break;
            default:
                computedSum = zeroN;
        }
        return computedSum;
    }
    public static double computeTotalCollection (String person, double discount, int balance) {
        double computedSum =computeAmountForAge(person);
        if (discount > zeroN) {
            computedSum = discount * computedSum;
        }
        if (balance < computedSum) {
            computedSum += (computedSum - balance) * extraFee;
        }

        return computedSum;
    }
}
