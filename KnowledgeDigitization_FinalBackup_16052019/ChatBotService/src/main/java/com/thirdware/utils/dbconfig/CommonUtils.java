package com.thirdware.utils.dbconfig;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

	
	public static String getValuesList(List<String> valueList)
	{
		StringBuilder query = new StringBuilder();
		if (valueList.size() > 1) {
			query.append(" (");
			StringBuilder strB = new StringBuilder();
			for (String val : valueList) {
				strB.append("'" + val + "',");
			}
			strB.deleteCharAt(strB.lastIndexOf(","));
			query.append(strB.toString() + ")");
		} else {
			if (valueList.size() == 0)
				query.append("");
			else {
				for (String value : valueList) {
					query.append(" ('" + value + "')");
				}

			}
		}
		return query.toString();
	}

	public static String getValuesListi(List<Integer> valueList) {
		// TODO Auto-generated method stub

		StringBuilder query = new StringBuilder();
		if (valueList.size() > 1) {
			query.append(" (");
			StringBuilder strB = new StringBuilder();
			for (Integer val : valueList) {
				strB.append("'" + val.toString() + "',");
			}
			strB.deleteCharAt(strB.lastIndexOf(","));
			query.append(strB.toString() + ")");
		} else {
			if (valueList.size() == 0)
				query.append("");
			else {
				for (Integer value : valueList) {
					query.append(" ('" + value.toString() + "')");
				}

			}
		}
		return query.toString();
	}
	
	public static boolean isNumericWithSpaces(String s) {
        String pattern = "^[0-9\\s]+";
        return s.matches(pattern);
  } 
	public static boolean containsSpecialCharacter(String item)
    {
           Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
           Matcher m = p.matcher(item);
           return m.find();
    }
	public static boolean isAlphaNumeric(String s) {
		String pattern = "^[a-zA-Z0-9]*$";
		return s.matches(pattern);
	}
	public static boolean isAlpha(String s) {
		String pattern = "^[a-zA-Z]*$";
		return s.matches(pattern);
	}

	public static boolean isAlphaNumericWithSpaces(String s) {
		String pattern = "^[a-zA-Z0-9\\s]+";
		return s.matches(pattern);
	}
	public static boolean isNumeric(String s) {
		String pattern = "^[0-9]*$";
		return s.matches(pattern);
	} 
	
	public static boolean isNumericip(String s) {
		String pattern = "^[0-9.]*$";
		return s.matches(pattern);
	} 
	
}