/*
  SlotMachine.java

  TCSS 142 - Assignment 4
  */

import java.util.Random;
import java.util.Scanner;

/*
  This program simulates a slot machine
  When given a money value, it prompts the
  user for a bet, uses a randomizer to decide
  output, and changes the user's money value.

  @author Isaac Keith
  @version 2/11/2020

  */

public class SlotMachine {

	private static final double MINCASH = .25;
	private static final double MAXCASH = 20.00;
	private static final int MINCHOICE = 1;
	private static final int MAXCHOICE = 4;


	public static void main (String[] args) {
		Scanner input = new Scanner(System.in);
		
		printIntro();
		promptForCash();
		double cashStart = getMoneyInRange(input);
		double cashCurrent = (cashStart);
		boolean restart = true;
		int totalRuns = -1;
		while (restart) {
			totalRuns++;
			promptForChoice();
			
			int choice = getChoiceInRange(input, cashCurrent);
			double bet = moneyBet(choice);
			int slot1 = genSlot1();
			int slot2 = genSlot2();
			int slot3 = genSlot3();
			double winnings = calculateSlotOutput(bet, slot1, slot2, slot3);
			cashCurrent = (cashCurrent + winnings);
			restart = playAgain(choice, cashCurrent);
			
			if (!(choice == 4)) {
				printFruit(slot1, slot2, slot3);
				printEndOfLoop(cashCurrent, winnings);
			}
			
		}

		printOutro(cashStart, cashCurrent,  totalRuns);
	
	}
	/**
	  this method prints the intro of the slot machine
	 */

	public static void printIntro() {
	
		System.out.println("This program simulates a slot machine.");
		System.out.println("The program will run until the user either has");
		System.out.println("less than 25 cents or until the user quits.");
		
	}
	
	/**
	  This method prints the end of the while loop in main, giving value for current
	  money and most recent winnings/losses

	  @param cashCurrent is the current value of money the user has
	  @param winnings is the most recent change in cashCurrent value
	 */

	public static void printEndOfLoop(double cashCurrent, double winnings) {
		System.out.printf("You have $%.2f", cashCurrent);
		System.out.println();
		System.out.printf("Your total changed by $%.2f", winnings);
		System.out.println();
		System.out.println();
	}
	
	/**
	  This method prints a statement asking the user how much money they have to gamble
	 */

	public static void promptForCash() {
		System.out.println();
		System.out.println("Enter the amount of money you want to gamble (between .25 and 20");
	}

	/**
	  This method prints the overall results of the game at the end

	  @param cashStart is the amount of money the user initially inputs and is used
	  to find the value of total change in cash during the entirety of the game

	  @param cashCurrent is the current amount of money the user has. It is used
	  to find the value of total change in cash during the entirety of the game

	  @param totalRuns is a count of all successful runs of the program and is
	  printed in this method
	 */

	public static void printOutro(double cashStart, double cashCurrent, int  totalRuns){
		System.out.println("Overall results:");
		System.out.println();
		System.out.println("You played " + totalRuns + " games");
		System.out.printf("You started with $%.2f", cashStart);
		System.out.printf("and ended with $%.2f", cashCurrent);
		System.out.println();
		if (cashStart < cashCurrent) {
			System.out.printf("You gained $%.2f", (cashCurrent - cashStart));
		} else if (cashStart > cashCurrent) {
			System.out.printf("You lost $%.2f", (cashStart - cashCurrent));
		} else {
			System.out.println("You did not gain or lose money");
		}
	}

	/**
	  This method prints the choices available to the user
	 */

	public static void promptForChoice() {
		System.out.println("Enter the amount of money you want to bet");
		System.out.println("1)   $0.25");
		System.out.println("2)   $0.50");
		System.out.println("3)   $1.00");
		System.out.println("4)   Quit");
	}

 	/**
	  This method is based off a method from
	  page 357 of "building java programs" by
	  Reges and Stepp

	  This method makes sure that the input from the user is specifically a
	  double

	  @param input is a scanner for getting input
	  @return input.nextDouble is the valid input from the scanner and is sent to 
	  getMoneyInRange
	 */ 

	public static double getMoney(Scanner input) {
		
		while (!input.hasNextDouble()) {
			input.next();
			System.out.println("Invalid input.");
			promptForCash();
		}
		
		return input.nextDouble();
	
	}

	/**
	  This method is based off the method "getIntInRange"
	  from the LuckyNumbers program by Alan Fowler
	  Method between lines 73 to 81

	  This method makes sure that the input from the user is between the range
	  set by the variables MINCASH and MAXCASH. 

	  @param input is a scanner used for getting user input
	  @return the filtered input of the user to main 

	 */ 


	public static double getMoneyInRange(Scanner input) {
		double moneyInRange = getMoney(input);
		while (moneyInRange < MINCASH || moneyInRange > MAXCASH) {
			System.out.println("The number entered is not in range.");
			promptForCash();
			moneyInRange = getMoney(input);
		}
		return moneyInRange;
	} 

	/**
	  This method is based off a method from
	  page 357 of "building java programs" by
	  Reges and Stepp

	  This method makes sure the choice inputed by the user is an specifically an integer


	  @param input is a scanner for getting input
	  @return input.nextInt is the user input and is sent to
	  the method getIntInRange
	 */ 

	 public static int getChoice(Scanner input) {
	 	while (!input.hasNextInt()) {
			input.next();
			System.out.println("Invalid input.");
			promptForChoice();
		}
		
		return input.nextInt();
	}

	/**
	  This method is based off the method "getIntInRange"
	  from the LuckyNumbers program by Alan Fowler
	  Method between lines 73 to 81

	  This method is used to make sure the choice selection of the user is valid.
	  If the user has less money than a bet option, this will filter that as well.

	  @param input is a scanner for getting user input
	  @param cashCurrent is the user's total cash and is used to make sure the
	  user has enough cash for their betting choice

	  @return choiceInRange is the final filtered result of the user's choice input
	  and is sent to main
	 */ 

	public static int getChoiceInRange(Scanner input, double cashCurrent) {
		int choiceInRange = getChoice(input);
		while (choiceInRange < MINCHOICE || choiceInRange > MAXCHOICE) {
			System.out.println("The number entered is not a valid choice.");
			promptForChoice();
			choiceInRange = getChoice(input);
		}
		
		if (cashCurrent <.5 ) {
			while (choiceInRange == 2 || choiceInRange == 3) {
				System.out.println("You don't have enough money for that option.");
				System.out.println("Please choose a lower option.");
				System.out.println();
				promptForChoice();
				choiceInRange = getChoice(input);
			}
		} else if (cashCurrent < 1.00) {
			while (choiceInRange == 3) {
				System.out.println("You don't have enough money for that option.");
				System.out.println("Please choose a lower option.");
				System.out.println();
				promptForChoice();
				choiceInRange = getChoice(input);
			}
		}
		return choiceInRange;
	} 

	/**
	  This method determines whether or not the user chooses to quit the program
	  or if the user has enough money to keep playing

	  @param choiceInRange is used to detect if the user presses the "quit" option
	  @param cashCurrent is used to determine if the user has enough money to keep playing
	  @return true/false is the result of whether or not the program will quit and is sent
	  to main
	 */

	public static boolean playAgain(int choiceInRange, double cashCurrent) {	
	
		int c = choiceInRange;
		boolean restart = (c == 4);
		boolean broke = (cashCurrent < .25);
		
		if (restart) {
			return false;
		} else if (broke) {
			return false;
		} else {
			return true;
		}
	}

	/**
	  This method converts the user's choice input into a money value for betting

	  @param choice is used to determine the user's choice
	  @return bet is the final cash value of the user's bet and is sent to main
	 */

	public static double moneyBet(int choice) {
		int userChoice = choice;
		double bet = 0;
		if (userChoice == 1) {
			bet = .25;
		} else if (userChoice == 2) {
			bet = .5;
		} else if (userChoice == 3) {
			bet = 1.0;
		} else {
			bet = 0;
		}
		return bet;
	}

	/**
	  This method is a randomizer used to generate the results of the slot machine

	  @return slot is the random number between 0 and 5 that is later used for matching
	 */

	public static int createSlotOutput() {
		Random slots = new Random();
		int slot = slots.nextInt(6); //self reminder; 6 = 0 to 5
		return slot;
	}

	/**
	  The following 3 methods (between this and the next javadoc comment) are used to 
	  put 3 seperate slot machine outputs in main
	  
	  @return numslot(1 through 3) returns the randomized number to main 
	 */

	public static int genSlot1() {
		int numSlot1 = createSlotOutput();
		return numSlot1;
	}

	public static int genSlot2() {
		int numSlot2 = createSlotOutput();
		return numSlot2;
	}

	public static int genSlot3() {
		int numSlot3 = createSlotOutput();
		return numSlot3;
	}

	/**
	  This method calculates matches in the slot machine

	  @param bet is the value of the user's bet and is used calculate winnings
	  @param numSlot(1 through 3) are random values that are mnatched and 
	  used to determine winnings
	  @return winnings the cash value of the amount won or lost by the user
	 */

	public static double calculateSlotOutput(double bet, int numSlot1, int numSlot2, int numSlot3) {
		double winnings = bet;
		
		int slot1 = numSlot1;
		int slot2 = numSlot2;
		int slot3 = numSlot3;
		
		if (slot1 == slot2 && slot2 == slot3) {
			winnings = (bet * 2);
		} else if (slot1 == slot2 || slot1 == slot3 || slot2 == slot3) {
			winnings = (bet);
		} else {
			winnings = (bet * -1);
		}

		return winnings;
		
	}

	/**
	  This method converts the random outputs from methods genSlot 1-3 and converts them
	  to readable outputs

	  @param slotOutput is the number output of the slot machine and is converted to
	  readable output
	  @return c does not do anything, it is used to prevent a compile error. 
	 */
	
	public static int convertSlotToFruit(int slotOutput) {
		int c = slotOutput;
		if  (c == 0) {
			System.out.print("Cherries ");
		} else if (c == 1) {
			System.out.print("Oranges ");
		} else if (c == 2) {
			System.out.print("Plums ");
		} else if (c == 3) {
			System.out.print("Bells ");
		} else if (c == 4) {
			System.out.print("Melons ");
		} else {
			System.out.print("Bars ");
		}
		return c;
	}

	/**
	  This method prints the readable slot machine output

	  @param slot(1 through 3) are the number values of the slot machine output
	 */

	public static void printFruit (int slot1, int slot2, int slot3){
		System.out.print("You got ");
		int newSlot1 = convertSlotToFruit(slot1);
		int newSlot2 = convertSlotToFruit(slot2);
		int newSlot3 = convertSlotToFruit(slot3);
		System.out.println();
	}
}
