package com.example.geektrust;

import java.util.*;

import static com.example.geektrust.Calculation.calculateRemainingBalance;

public class Main {
    public static void main (String[] args) {

        Scanner sc = new Scanner(System.in);
        final double discount = 0.5;


        takeInput(sc, discount);
        String[] source ={"CENTRAL","AIRPORT"};
        for(int i=0;i<source.length;i++){
            System.out.println("TOTAL_COLLECTION " + source[i]+" " + Composition.getSourceCost(source[i])+" "+Composition.getDiscount(source[i]));
            System.out.println("PASSENGER_TYPE_SUMMARY");

            Composition.composePassengers(Composition.getPassengerCountMap(),source[i]);
        }


    }

    private static void takeInput (Scanner sc, double discount) {
        int index =1;
        while (true) {
            String input = sc.nextLine();


            if (input.contentEquals("PRINT_SUMMARY")) {

                break;   // Break condition
            }

            if (input.startsWith("BALANCE")) {
                String[] inputArr = input.split(" ");

                Composition.putIntoCardBalance(inputArr[index],Integer.valueOf(inputArr[index+1]));
            } else if (input.startsWith("CHECK_IN")) {
                String[] inputArr = input.split(" ");

                String mc = inputArr[index]; // card number
                String person = inputArr[index+1]; // person type
                String source = inputArr[index+2]; // source

                Composition.populateCardBalance(source,mc,person, discount);


            }
        }
    }


}
