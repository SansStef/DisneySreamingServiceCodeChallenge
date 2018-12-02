package com.sans.stef;

public class Coin {
	public String name;
	public int numRequired;
	
	public Coin(String n, int num) {
		name = n;
		numRequired = num;
	}
	
	/**
	 * 1.00 is used here representing 100% of target sum,
	 * not necessarily $1.00
	 */
	public double value() {
		return 1.00 / numRequired;
	}
}
