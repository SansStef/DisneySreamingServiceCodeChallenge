package com.sans.stef;

public class TestCoinCombinations {

	public static void main(String[] args) {
		System.out.println("Test static: " + testStaticCoins());
		System.out.println("Test regular coins: " + testDynamicRegularCoins());
		System.out.println("Test irregular coins: " + testDynamicIrregularCoins());
	}

	private static boolean testDynamicIrregularCoins() {
		CombinationMain cc = new CombinationMain("Coin,1.5,Arrowhead,3,Button,150");

		return cc.calculateDifferentCoinCombinations() == 6;
	}
	
	private static boolean testDynamicRegularCoins() {
		CombinationMain cc = new CombinationMain("Quarter,4,Dime,10,Nickel,20,Penny,100");

		return cc.calculateDifferentCoinCombinations() == 233;
	}
	
	private static boolean testStaticCoins() {
		CombinationMain cc = new CombinationMain("");

		//Old static coins way
		Coin quarter = new Coin("Quarter", 4);
		Coin dime = new Coin("Dime", 10);
		Coin nickel = new Coin("Nickel", 20);
		Coin penny = new Coin("Penny", 100);
		
		cc.coins.add(quarter);
		cc.coins.add(dime);
		cc.coins.add(nickel);
		cc.coins.add(penny);
		
		return cc.calculateDifferentCoinCombinations(quarter, dime, nickel, penny) == 233;
	}
}
