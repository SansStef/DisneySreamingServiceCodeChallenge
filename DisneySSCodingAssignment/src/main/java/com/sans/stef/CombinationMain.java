package com.sans.stef;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CombinationMain {

	protected static final String COIN_NAME_PRINT = "%10s";
	protected static final String COMBO_COUNT_PRINT = "Count: %d";
	protected static final String COIN_VALUE_PRINT = "Value of %s: %.2f";

	protected static final String DEFAULT_ARGUMENT = "Quarter,4,Dime,10,Nickel,20,Penny,100";
	protected static final String GIVEN_COINS_DELIM = ",";
	
	protected static final double TARGET_SUM = 1.00;
	protected static final double THRESHOLD = .0000000000000002;
	
	protected int defaultSpacing = 10;
	protected List<Coin> coins = new LinkedList<Coin>();
	protected StringBuilder output = new StringBuilder();

	public static void main(String[] args) {
		String givenCoins;
		if(args.length == 0) {
			givenCoins = DEFAULT_ARGUMENT;
		}
		else {
			givenCoins = args[0];
		}
		
		CombinationMain coinCombinations = new CombinationMain(givenCoins);

		coinCombinations.doDynamicWay();
	}

	public CombinationMain(String givenCoins) {
		parseCoins(givenCoins);
	}
	
	/**
	 * Find different combinations of coins, dynamic way
	 */
	private void doDynamicWay() {
		printCoinNamesHeader(coins);
		
		int countCombos = calculateDifferentCoinCombinations();
		
		printComboCount(countCombos);
		
		System.out.print(output);
	}
	
	/**
	 * Find different combinations of coins, static way
	 */
	@Deprecated
	protected void doStaticWay() {

		//Old static coins way
		Coin quarter = new Coin("Quarter", 4);
		Coin dime = new Coin("Dime", 10);
		Coin nickel = new Coin("Nickel", 20);
		Coin penny = new Coin("Penny", 100);
		
//		coins.add(quarter);
//		coins.add(dime);
//		coins.add(nickel);
//		coins.add(penny);
		
		
		printCoinNamesHeader(coins);
		
		int countCombos = calculateDifferentCoinCombinations(quarter, dime, nickel, penny);
		
		printComboCount(countCombos);
		
		System.out.print(output);
	}

	/**
	 * For each possible combination of number of coins (up to a coins numRequired) see if the total is the target sum,
	 * if it is that means the combination is valid and we can output it
	 * 
	 * We create each possible combination by for each coin, create X+1 combinations for each existing
	 * combination with the added coin where X is max number of times that coin can be used. We also create a combo
	 * using 0 of that coin
	 */
	protected int calculateDifferentCoinCombinations() {
		int countCombos = 0;
		
		Queue<CoinCombination> combinations = new LinkedList<CoinCombination>();
		for(Coin coin : coins) {
			int currentCombos = combinations.size();
			
			//If no current combos i.e. first coin,
			//create combos with just first coin
			if(combinations.isEmpty()) {
				for(int c = 0; c <= coin.maxNum; c++) {
					CoinCombination origCombo = new CoinCombination();
					origCombo.add(coin, c);
					
					if(origCombo.equalTarget(TARGET_SUM)) {
						printCoinCombination(origCombo);
						countCombos++;
					}
					else if(!origCombo.overTarget(TARGET_SUM)) {
						combinations.add(origCombo);
					}
				}
			}
			//For each combo, clone it and create a version with the next coin
			//repeat for max number of times coin can be used
			//ie if coin can be used max 4 times, create 5 new combos, one with each amount of that coin and 0 of that coin
			else {
				for(int i = 0; i < currentCombos; i++) {
					CoinCombination coinCombo = combinations.remove();
					for(int c = 0; c <= coin.maxNum; c++) {
						CoinCombination cloneCombo = coinCombo.copy(coinCombo);
						cloneCombo.add(coin, c);
						
						if(cloneCombo.equalTarget(TARGET_SUM)) {
							printCoinCombination(cloneCombo);
							countCombos++;
						}
						else if(!cloneCombo.overTarget(TARGET_SUM)) {
							combinations.add(cloneCombo);
						}
					}
				}				
					
			}
		}
		
		return countCombos;
	}

	/**
	 * Prints out CoinCombination taking into account correct spacing
	 */
	protected void printCoinCombination(final CoinCombination cloneCombo) {
		boolean first = true;
		for(Coin coin : coins) {
			if(first) {
				printFirstNumberOfCoin(coin, cloneCombo.coinAmount(coin));
				first = false;
			}
			else {
				printNumberOfCoin( cloneCombo.coinAmount(coin), defaultSpacing );				
			}
		}
		output.append("\n");
	}
	
	/**
	 * For each possible combination of number of coins (up to a coins numRequired) see if the total is the target sum,
	 * if it is that means the combination is valid and we can output it
	 */
	@Deprecated
	protected int calculateDifferentCoinCombinations(final Coin quarter, final Coin dime, final Coin nickel, final Coin penny) {
		int countCombos = 0;

		for(int q = 0; q <= quarter.maxNum; q++) {
			for(int d = 0; d <= dime.maxNum; d++) {
				for(int n = 0; n <= nickel.maxNum; n++) {
					for(int p = 0; p <= penny.maxNum; p++) {
						double sum = 	q * quarter.value +
										d * dime.value	+
										n * nickel.value	+
										p * penny.value;
						
						if(Math.abs(sum - TARGET_SUM) < THRESHOLD) {
							printFirstNumberOfCoin(quarter, q);
							printNumberOfCoin( d, defaultSpacing );
							printNumberOfCoin( n, defaultSpacing );
							printNumberOfCoin( p, defaultSpacing );
							output.append("\n");

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
	protected void printCoinNamesHeader(final List<Coin> coins) {
		boolean first = true;
		for(Coin coin : coins) {
			
			if(first) {
				output.append(coin.name);
				first = false;
			}
			else {
				output.append(String.format(COIN_NAME_PRINT, coin.name));
			}
		}
		output.append("\n");
		
	}

	/**
	 * Parses a string that alternates between a coin's name and its num required to reach target sum
	 * e.g."Coin,1.5,Arrowhead,3,Button,150"
	 */
	protected void parseCoins(final String givenCoins) {
		boolean isName = true;
		String coinName = null;
		double coinNumberRequired;
		Coin newCoin;
		
		for(String nameOrNumRequired : givenCoins.split(GIVEN_COINS_DELIM)) {
			if(isName) {
				coinName = nameOrNumRequired;
				defaultSpacing = Math.max(defaultSpacing, coinName.length() + 1); //Adjust defaultSpacing in case a coin name is very long
			}
			else {
				coinNumberRequired = Double.parseDouble(nameOrNumRequired);
				newCoin = new Coin(coinName, coinNumberRequired);
				
				coins.add(newCoin);
			}
			isName = !isName;
		}
	}
	
	/**
	 * Print out number of coins used with correct spacing relative to coin name
	 */
	protected void printFirstNumberOfCoin(final Coin coin, final int num) {
		printNumberOfCoin(num, coin.name.length());
	}
	
	/**
	 * Print out number of coins used with correct spacing
	 */
	protected void printNumberOfCoin(final int num, final int spacing) {
		output.append(String.format("%" + spacing + "d", num));
	}
	
	/**
	 * Prints value of a coin
	 * e.g. "Value of Quarter: 0.25"
	 * 
	 */
	protected void printCoinValue(final Coin coin) {
		output.append(String.format(COIN_VALUE_PRINT, coin.name, coin.value))
			  .append("\n");
	}

	/**
	 * Prints valid combination count
	 * e.g. "Count: 232"
	 */
	protected void printComboCount(final int count) {
		output.append("\n")
			  .append(String.format(COMBO_COUNT_PRINT, count))
			  .append("\n");
	}

}
