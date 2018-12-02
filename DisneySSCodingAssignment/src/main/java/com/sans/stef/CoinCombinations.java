package com.sans.stef;

import java.util.LinkedList;
import java.util.List;

public class CoinCombinations {

	private static final String COIN_NAME_PRINT = "%10s";
	private static final String COMBO_COUNT_PRINT = "Count: %d";
	private static final String COIN_VALUE_PRINT = "Value of %s: %.2f";

	private static final String DEFAULT_ARGUMENT = "Quarter,4,Dime,10,Nickel,5,Penny,100";
	private static final String GIVEN_COINS_DELIM = ",";
	
	private static final Double TARGET_SUM = 1.00;
	
	private int defaultSpacing = 10;
	private List<Coin> coins = new LinkedList<Coin>();

	public static void main(String[] args) {
		String givenCoins;
		if(args.length == 0) {
			givenCoins = DEFAULT_ARGUMENT;
		}
		else {
			givenCoins = args[0];
		}
		
		CoinCombinations coinCombinations = new CoinCombinations(givenCoins);

		coinCombinations.doJob();
	}
	
	public CoinCombinations(String givenCoins) {
		//parseCoins(givenCoins);
	}
	
	/**
	 * Run through the different steps 
	 */
	private void doJob() {
		
		// TODO Auto-generated method stub
		Coin quarter = new Coin("Quarter", 4);
		Coin dime = new Coin("Dime", 10);
		Coin nickel = new Coin("Nickel", 20);
		Coin penny = new Coin("Penny", 100);
		
		coins.add(quarter);
		coins.add(dime);
		coins.add(nickel);
		coins.add(penny);
		
		
		printCoinNamesHeader(coins);
		
		int countCombos = calculateDifferentCoinCombinations(quarter, dime, nickel, penny);
		
		printComboCount(countCombos);
	}

	private int calculateDifferentCoinCombinations(final Coin quarter, final Coin dime, final Coin nickel, final Coin penny) {
		int countCombos = 0;
		
		for(int q = 0; q <= quarter.numRequired; q++) {
			for(int d = 0; d <= dime.numRequired; d++) {
				for(int n = 0; n <= nickel.numRequired; n++) {
					for(int p = 0; p <= penny.numRequired; p++) {
						double sum = 	q * quarter.value() +
										d * dime.value()	+
										n * nickel.value()	+
										p * penny.value();
						
						if(sum == TARGET_SUM) {
							printFirstNumberOfCoin(quarter, q);
							printNumberOfCoin( d, defaultSpacing );
							printNumberOfCoin( n, defaultSpacing );
							printNumberOfCoin( p, defaultSpacing );
							System.out.println();

							countCombos++;
						}
						
					}
				}
			}
			
		}
		return countCombos;
	}

	/**
	 * 	Print coin names in a column format evenly spaced with no extra spacing on first coin
	 * 	e.g.
	 * 	Quarter      Dime    Nickel     Penny
	 */
	private void printCoinNamesHeader(final List<Coin> coins) {
		boolean first = true;
		for(Coin coin : coins) {
			
			if(first) {
				System.out.print(coin.name);
				first = false;
			}
			else {
				System.out.print(String.format(COIN_NAME_PRINT, coin.name));
			}
		}
		System.out.print("\n");
		
	}

	/**
	 * Parses a string that alternates between a coin's name and its num required to reach target sum
	 * e.g."Coin,1.5,Arrowhead,3,Button,150"
	 */
	private void parseCoins(final String givenCoins) {
		boolean isName = true;
		String coinName = null;
		int coinNumberRequired;
		Coin newCoin;
		
		for(String nameOrNumRequired : givenCoins.split(GIVEN_COINS_DELIM)) {
			if(isName) {
				coinName = nameOrNumRequired;
				defaultSpacing = Math.max(defaultSpacing, coinName.length() + 1); //Adjust defaultSpacing in case a coin name is very long
			}
			else {
				coinNumberRequired = Integer.parseInt(nameOrNumRequired);
				newCoin = new Coin(coinName, coinNumberRequired);
				
				coins.add(newCoin);
			}
			isName = !isName;
		}
	}
	
	/**
	 * Print out number of coins used with correct spacing relative to coin name
	 */
	private void printFirstNumberOfCoin(final Coin coin, final int num) {
		printNumberOfCoin(num, coin.name.length());
	}
	
	/**
	 * Print out number of coins used with correct spacing
	 */
	private void printNumberOfCoin(final int num, final int spacing) {
		System.out.print(String.format("%" + spacing + "d", num));
	}
	
	/**
	 * Prints value of a coin
	 * e.g. "Value of Quarter: 0.25"
	 * 
	 */
	private void printCoinValue(final Coin coin) {
		System.out.println(String.format(COIN_VALUE_PRINT, coin.name, coin.value()));
	}

	/**
	 * Prints valid combination count
	 * e.g. "Count: 232"
	 */
	private void printComboCount(final int count) {
		System.out.println();
		System.out.println(String.format(COMBO_COUNT_PRINT, count));
	}

}
