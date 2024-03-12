package com.example.geektrust;




import java.util.*;

import static com.example.geektrust.Calculation.calculateRemainingBalance;
import static com.example.geektrust.Calculation.computeAmountForAge;

public class Composition {
    private static Map<String,Integer> finalMap;
    private static final int zeroN = 0;
    static int centralSum = zeroN;
    static int airportSum = zeroN;

    private static Map<String, Integer> cardBalance = new HashMap(); // map to manage card balances

    private static Map<String,Double> discountBalance = new HashMap<>(); // map to manage discounts
    private static Map<String, Map> passengerCountMap = new HashMap(); // map to manage passenger counts whose source are Airport and Central
    private static Map<String, Map> personCentralCount = new HashMap(); // map to manage passenger counts whose source is Central
    private static Map<String, Map> personAirportCount = new HashMap(); // map to manage passenger counts whose source is Airport
    public static void composePassengers (Map<String, Map> passengerCountMap, String source) {
        Map res = passengerCountMap.get(source);
        finalMap = new TreeMap(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                char firstLetter1 = o1.charAt(zeroN);
                char firstLetter2 = o2.charAt(zeroN);
                return Character.compare(firstLetter1, firstLetter2);
            }
        });

        if (res != null) {
            Set keys = res.keySet();
            keys.forEach(key -> {
                Map<String, Object> resultMap = (Map<String, Object>) res.get(key);
                Person p = (Person) resultMap.get("identity");
                Integer existing = (Integer) resultMap.get("count");
                if (existing == null) {
                    existing = zeroN;
                }
                if(finalMap.get(p.getPersonType())!=null)
                    existing = finalMap.get(p.getPersonType())+1;
                finalMap.put(p.getPersonType(), existing);

            });
        }

        Set personType = finalMap.keySet();
        personType.forEach(item -> {
            if(finalMap.get(item)!=null)
                System.out.println(String.valueOf(item) + ' ' + finalMap.get(item).intValue());
        });
    }
    public static void populateCardBalance(String source,String mc,String person,double discount){
        if (source.contentEquals("CENTRAL")) {
            calculateTotalCollectionAndPersonCount(source,mc, person, discount,"AIRPORT");
            populatePeopleCount(mc, person,personCentralCount,passengerCountMap,source);
        } else {
          calculateTotalCollectionAndPersonCount(source,mc,person,discount,"CENTRAL");
          populatePeopleCount(mc,person,personAirportCount,passengerCountMap,source);
        }
        cardBalance.put("CENTRAL", centralSum);
        cardBalance.put("AIRPORT", airportSum);
    }

    public static int getDiscount(String source){

            return discountBalance.getOrDefault(source,0.00).intValue();

    }
    private  static void calculateTotalCollectionAndPersonCount (String source,String mc, String person, double discount,String destination) {
        Map individualCount = passengerCountMap.get(destination);
        double centralDiscount=zeroN;
        double airportDiscount=zeroN;


        if (individualCount != null && individualCount.get(mc) != null && (passengerCountMap.get(source)==null || passengerCountMap.get(source).get(mc)==null)) {//checks whether this person ia already part of the airport if it is, it means this is a return journey
            // and it also checks that this card is not present in the Central array itself(in case from 2nd onwards)
            if(source.contentEquals("CENTRAL")){
                centralSum += Calculation.computeTotalCollection(person, discount, cardBalance.get(mc));
                centralDiscount=discount*Calculation.computeAmountForAge(person);
            }

            else{
                    airportSum += Calculation.computeTotalCollection(person, discount, cardBalance.getOrDefault(mc,0));
                    airportDiscount=discount*Calculation.computeAmountForAge(person);

            }


        } else {
            if(source.contentEquals("CENTRAL") && cardBalance.get(mc)!=null){
                centralSum += Calculation.computeTotalCollection(person, zeroN, cardBalance.get(mc)); // this means it is not a return journey
            }

            else {
                airportSum += Calculation.computeTotalCollection(person, zeroN, cardBalance.getOrDefault(mc, 0)); // this means it is not a return journey


            }
        }


        if(cardBalance.get(mc)!=null){
            int balance = Calculation.calculateRemainingBalance(person, cardBalance.get(mc), discount);
            cardBalance.put(mc, balance);
        }


        if(centralDiscount> zeroN || airportDiscount > zeroN){


            if(source.contentEquals("CENTRAL")){
                discountBalance.put(source,centralDiscount);
            }

            else{
                discountBalance.put(source,airportDiscount);

            }

        }



    }

    private static void populatePeopleCount (String mc, String person,Map<String, Map> personMap,Map<String, Map> personTotalMap,String source) {
        Map<String, Object> existingCountObj = personMap.get(mc);
        if (existingCountObj != null) {
            Person p1 = (Person) existingCountObj.get("identity");
            personMap.put(mc, Map.of("count", (Integer) (existingCountObj.getOrDefault("count", zeroN)) + 1, "identity", p1));
            personTotalMap.put(source ,personMap);
        } else {
            Person person1 = new Person(person, mc);
            personMap.put(mc, Map.of("count", (Integer)1, "identity", person1));
            personTotalMap.put(source, personMap);
        }
    }

    public static Map<String,Map> getPassengerCountMap(){
        return passengerCountMap;
    }
    public static Integer  getSourceCost(String source){
        return cardBalance.get(source);
    }
    public static void putIntoCardBalance(String key,Integer value){
        cardBalance.put(key, value); // update the card balances
    }
}
