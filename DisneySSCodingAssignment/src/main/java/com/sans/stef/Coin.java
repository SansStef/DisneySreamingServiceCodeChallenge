package com.sans.stef;

/**
 * Class for representing a coin or any type of currency
 * 
 */
public class Coin {
	public String name;
	/**
	 * Maximum number of coins before target sum is reached
	 */
	public int maxNum;
	public double value;
	
	public Coin(String n, double num) {
		name = n;
		maxNum = (int) Math.floor(num);
		value = calculateValue(num);
	}
	
	/**
	 * 1.00 is used here representing 100% of target sum,
	 * not necessarily $1.00
	 */
	private double calculateValue(double num) {
		return 1.00 / num;
	}
}
