package com.trinet.bnftnwbandbatchprocessor.util;

public class Timer {


     public static boolean timeOutprocess(Long start , Long duration ){

         Long currentTimeMillis = System.currentTimeMillis() ;

         if(start+duration >= currentTimeMillis)
              return true ;


         return false ;
     }

}
