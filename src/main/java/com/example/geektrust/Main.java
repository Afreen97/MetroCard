package com.example.geektrust;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.example.geektrust.Calculation.calculateRemainingBalance;

public class Main {
    public static void main (String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        final double discount = 0.5;


        takeInput(sc, discount, args);
        String[] source ={"CENTRAL","AIRPORT"};
        for(int i=0;i<source.length;i++){
            System.out.println("TOTAL_COLLECTION " + source[i]+" " + Composition.getSourceCost(source[i])+" "+Composition.getDiscount(source[i]));
            System.out.println("PASSENGER_TYPE_SUMMARY");

            Composition.composePassengers(Composition.getPassengerCountMap(),source[i]);
        }


    }

    private static void takeInput (Scanner sc, double discount,String[] args) throws IOException {
        int index =1;
        String filePath = args[0];
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {

            if(!line.isEmpty()){
                if (line.contentEquals("PRINT_SUMMARY")) {

                    break;   // Break condition
                }

                if (line.startsWith("BALANCE")) {
                    String[] inputArr = line.split(" ");

                    Composition.putIntoCardBalance(inputArr[index],Integer.valueOf(inputArr[index+1]));
                } else if (line.startsWith("CHECK_IN")) {
                    String[] inputArr = line.split(" ");

                    String mc = inputArr[index]; // card number
                    String person = inputArr[index+1]; // person type
                    String source = inputArr[index+2]; // source

                    Composition.populateCardBalance(source,mc,person, discount);


                }
            }

        }
    }


}
