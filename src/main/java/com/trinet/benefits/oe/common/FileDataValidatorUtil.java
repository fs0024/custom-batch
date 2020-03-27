/**
 * 
 */
package com.trinet.benefits.oe.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trinet.benefits.oe.entity.ValidatorDef;

/**
 * @author imistry1
 *
 * 
 */
public class FileDataValidatorUtil {
	
	private FileDataValidatorUtil() {
		throw new AssertionError("Cannot instantiate com.trinet.benefits.oe.common.FileDataValidatorUtil");
	}
	
	
	
	public static boolean isValidData(String stringTobeValidated,String regexPattern) {
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(stringTobeValidated);
		return matcher.matches();
				
	}
	
	
	public static Map<Integer,ValidatorDef> convertValidationListToMap(List<ValidatorDef> fieldValidationsDefList){
		Map<Integer,ValidatorDef> results = new HashMap<>();
		
		for(ValidatorDef record: fieldValidationsDefList) {
			results.put(record.getSequence(), record);
		}
		return results;
	}
	
	
	
	
	

}
