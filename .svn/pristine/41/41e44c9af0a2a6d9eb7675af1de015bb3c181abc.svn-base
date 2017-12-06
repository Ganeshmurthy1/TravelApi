package com.tayyarah.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ArithmeticUtil {
	private static String[] units = { "", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight",
	" Nine" };
	private static String[] teen = { " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen",
			" Seventeen", " Eighteen", " Nineteen" };
	private static String[] tens = { " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty",
	" Ninety" };
	private static String[] maxs = { "", "", " Hundred", " Thousand", " Lakh", " Crore" };

	public static BigDecimal divideTo2Decimal(BigDecimal value1, BigDecimal value2)
	{
		try{
			value1 = value1.divide(value2);
		}
		catch(ArithmeticException ex)
		{
			value1 = value1.divide(value2, 7, RoundingMode.HALF_UP);
		}
		return value1;
		
	}
	
	public static BigDecimal multiplyTo2Decimal(BigDecimal value1, BigDecimal value2)
	{
		try{
			value1 = value1.multiply(value2);
		}
		catch(ArithmeticException ex)
		{
			//value1 = value1.multiply(value2, RoundingMode.HALF_UP);
		}
		return value1.setScale(2);
	}

	public static String convertNumberToWords(int n) {
		String input = numToString(n);
		String converted = "";
		int pos = 1;
		boolean hun = false;
		while (input.length() > 0) {
			if (pos == 1) // TENS AND UNIT POSITION
			{
				if (input.length() >= 2) // TWO DIGIT NUMBERS
				{
					String temp = input.substring(input.length() - 2, input.length());
					input = input.substring(0, input.length() - 2);
					converted += digits(temp);
				} else if (input.length() == 1) // 1 DIGIT NUMBER
				{
					converted += digits(input);
					input = "";
				}
				pos++;
			} else if (pos == 2) // HUNDRED POSITION
			{
				String temp = input.substring(input.length() - 1, input.length());
				input = input.substring(0, input.length() - 1);
				if (converted.length() > 0 && digits(temp) != "") {
					converted = (digits(temp) + maxs[pos] + " ") + converted;
					hun = true;
				} else {
					if (digits(temp) == "")
						;
					else
						converted = (digits(temp) + maxs[pos]) + converted;
					hun = true;
				}
				pos++;
			} else if (pos > 2) // REMAINING NUMBERS PAIRED BY TWO
			{
				if (input.length() >= 2) // EXTRACT 2 DIGITS
				{
					String temp = input.substring(input.length() - 2, input.length());
					input = input.substring(0, input.length() - 2);
					if (!hun && converted.length() > 0)
						converted = digits(temp) + maxs[pos] + " " + converted;
					else {
						if (digits(temp) == "")
							;
						else
							converted = digits(temp) + maxs[pos] + converted;
					}
				} else if (input.length() == 1) // EXTRACT 1 DIGIT
				{
					if (!hun && converted.length() > 0)
						converted = digits(input) + maxs[pos] + " " + converted;
					else {
						if (digits(input) == "")
							;
						else
							converted = digits(input) + maxs[pos] + converted;
						input = "";
					}
				}
				pos++;
			}
		}
		return converted;
	}

	public static  String digits(String temp) // TO RETURN SELECTED NUMBERS IN WORDS
	{
		String converted = "";
		for (int i = temp.length() - 1; i >= 0; i--) {
			int ch = temp.charAt(i) - 48;
			if (i == 0 && ch > 1 && temp.length() > 1)
				converted = tens[ch - 2] + converted; // IF TENS DIGIT STARTS
			// WITH 2 OR MORE IT
			// FALLS UNDER TENS
			else if (i == 0 && ch == 1 && temp.length() == 2) // IF TENS DIGIT
				// STARTS WITH 1
				// IT FALLS
				// UNDER TEENS
			{
				int sum = 0;
				for (int j = 0; j < 2; j++)
					sum = (sum * 10) + (temp.charAt(j) - 48);
				return teen[sum - 10];
			} else {
				if (ch > 0)
					converted = units[ch] + converted;
			} // IF SINGLE DIGIT PROVIDED
		}
		return converted;
	}

	public static  String numToString(int x) // CONVERT THE NUMBER TO STRING
	{
		String num = "";
		while (x != 0) {
			num = ((char) ((x % 10) + 48)) + num;
			x /= 10;
		}
		return num;
	}

	public static  String DecimalValueCheacker(String inputData) {
		int num;
		String amountinwords = "";
		if (inputData.contains(".")) {
			// Separating rupee Here
			String[] decimalSeparator = inputData.split("\\.");
			num = Integer.parseInt(decimalSeparator[0]);
			// Separating Paisa Here
			BigDecimal finalValue = new BigDecimal(inputData);
			finalValue = finalValue.setScale(2, BigDecimal.ROUND_HALF_UP);
			decimalSeparator = String.valueOf(finalValue).split("\\.");
			num = Integer.parseInt(decimalSeparator[1]);
			amountinwords = convertNumberToWords(num);
		}
		else {
			num = Integer.parseInt(inputData);
			amountinwords = convertNumberToWords(num);
		}
		return amountinwords;
	}
}