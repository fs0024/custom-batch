package com.trinet.bnftnwbandbatchprocessor.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	
	private DateUtils() {}
	
	public static String getEffectiveDateString(String quarter, String effDateStr)
    {
		try {
            Date effDate = new SimpleDateFormat("dd-MMM-yyyy").parse(effDateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(effDate); // don't forget this if date is arbitrary
            
            int year = cal.get(Calendar.YEAR);
            Date finalEffectiveDate;
            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            
            switch(quarter)
            {
                case "Q1":
                case "AL":
                case "AC":
                    Date q1Date = new GregorianCalendar(year, Calendar.JANUARY, 1).getTime();
                    if(effDate.compareTo(q1Date) < 0)
                        finalEffectiveDate = new GregorianCalendar(year - 1, Calendar.DECEMBER, 31).getTime();
                    else
                        finalEffectiveDate =  effDate;
                    break;
                    
                case "Q2":
                case "SY":
                case "SM":
                    Date q2Date = new GregorianCalendar(year, Calendar.APRIL, 1).getTime();
                    if(effDate.compareTo(q2Date) < 0)
                        finalEffectiveDate = new GregorianCalendar(year - 1, Calendar.DECEMBER, 31).getTime();
                    else
                        finalEffectiveDate = effDate;
                    break;
                    
                case "Q3":
                    Date q3Date = new GregorianCalendar(year, Calendar.JULY, 1).getTime();
                    if(effDate.compareTo(q3Date) < 0)
                        finalEffectiveDate = new GregorianCalendar(year - 1, Calendar.DECEMBER, 31).getTime();
                    else
                        finalEffectiveDate = effDate;
                    break;
                    
                case "Q4":
                case "8Y":
                    Date q4Date = new GregorianCalendar(year, Calendar.OCTOBER, 1).getTime();
                    if(effDate.compareTo(q4Date) < 0)
                        finalEffectiveDate = new GregorianCalendar(year - 1, Calendar.DECEMBER, 31).getTime();
                    else
                        finalEffectiveDate = effDate;
                    break; 
                
                default:
                	finalEffectiveDate = null;
            }
            
            return dateFormat.format(finalEffectiveDate);
		}
		catch(Exception e) {
			return "Parse exception";
		}
    }

}
