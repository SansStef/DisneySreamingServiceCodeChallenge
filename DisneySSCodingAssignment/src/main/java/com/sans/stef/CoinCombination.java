package com.sans.stef;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for representing combinations of coin amounts
 */
public class CoinCombination implements Cloneable {
	
	private Map<Coin, Integer> coinAmounts = new HashMap<Coin, Integer>();
	
	public void add(final Coin coin, final int amount) {
		coinAmounts.put(coin, amount);
	}
	
	public CoinCombination copy(final CoinCombination original) {
		CoinCombination copy = new CoinCombination();
		
		for(Coin coin : original.coinAmounts.keySet()) {
			copy.add(coin, original.coinAmounts.get(coin));
		}
		
		return copy;
	}
	
	/**
	 * Check if the target sum has been exceeded by coin amounts
	 * 
	 * (Good idea to check if equal first)
	 */
	public boolean overTarget(final double target) {
		return amountTotal() > target;
	}
	
	/**
	 * Check if the target sum has been reached by coin amounts
	 */
	public boolean equalTarget(final double target) {
		return Math.abs(amountTotal() - target) < CombinationMain.THRESHOLD;
//		return amountTotal() == target;
	}
	
	/**
	 * Calculate total amount
	 */
	private double amountTotal() {
		double sum = 0;
		
		for(Coin coin : coinAmounts.keySet()) {
			sum += coin.value * coinAmounts.get(coin);
		}
		return sum;
	}

	/**
	 * Gives amount of coin in the combo, 0 if none
	 */
	public int coinAmount(final Coin coin) {
		Integer amt = coinAmounts.get(coin);
		if(amt != null) {
			return amt;
		}
		return 0;
	}
}
