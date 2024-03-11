package com.example.geektrust;


public class Person {
   private String personType;
   private String metroCard;
   public Person(String personType,String metroCard){
      this.metroCard = metroCard;
      this.personType = personType;
   }
   public String getPersonType(){
      return this.personType;
   }
   public String getMetroCard(){
      return this.metroCard;
   }
}
