
/*
  This program simulates a slot machine.
  When given a money value, it prompts the
  user for a bet, uses a randomizer to decide
  output, and changes the user's money value.

  @author Isaac Keith
  @version 7/10/2022

  */

import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class ImprovedSlotMachine {
	
	private static final double MINCASH = .25; //Minimum cash the user may start with
	private static final double MAXCASH = 20; //Maximum cash the user may start with
	private static final int MINCHOICE = 1; //
	private static final int MAXCHOICE = 4;	
	private static Scanner input = new Scanner(System.in); //A console scanner for reading user input	
	private static double userCash; //Declaring here since this variable will be used in several methods, will lower complexity by not passing it around everywhere	
	
	
	public static void main(String[] args) {		
		int totalRuns = 0; //The number of times the user ran the slot machine
		boolean runGame = true;
		printIntro(); 
		userCash = (getUserStartingMoney()); //Prompt the user for the amount they want to start with
		double startingCash = userCash; //Set starting cash to calculate final stats at the end		
		
		while (runGame) {
			int choice = getUserChoice();			
			if (choice == 4) { //If the user selects 4, they wish to quit the game				
				runGame = false;			
			} else { 
				spinSlotMachine(decodeUserChoice(choice)); //Runs the slot machine process, check method for more detail		
				totalRuns++;
			}				
			if (userCash < .25) { //If user does not have enough money to play, do not run the game again. 
				runGame = false;
			}
		}		
		printOutro(startingCash, totalRuns); //After the loop, print the outro of the program
	}
	
	/**
	 * Prints the into to the program
	 */
	public static void printIntro() {
		System.out.println("This program simulates a slot machine.");
		System.out.println("The program will run until the user either has");
		System.out.println("less than 25 cents or until the user quits.");
	}
	
	/**
	 * Creates a string representing a menu
	 * <p>
	 * Returns a string representing a menu with numbered options,
	 * can be modified to represent any type of menu in a console app
	 * <p>
	 * 
	 * @return A string representing the menu
	 */
	public static String choiceMenu() {
		String menu = "Select the amount you would like to bet: \n";
		menu += "(1) - $.25 \n";
		menu += "(2) - $.50 \n";
		menu += "(3) - $1.00 \n";
		menu += "(4) - Quit";
		
		return menu;
	}
	
	/**
	 * Gets input for the amount of money the user wants to start with
	 * <p>
	 * Prompts the user for the amount of money they want to start with between MINCASH and MAXCASH
	 * Checks if the input is of type double, then checks if it is within bounds. 
	 * The method tells users if their input is invalid and how to correct it.
	 * <p>
	 * 
	 * @return A double representing the amount of cash the user wants to start with
	 * 
	 */
	public static double getUserStartingMoney() {
		boolean validInput = false; //whether or not use input is valid (of type double between min and max cash)
		double userInput = 0; //used to store user input during the process, 0 is a placeholder number since this variable must be declared. 
		while (!validInput) {			
			System.out.println("Enter the amount of money you want to gamble (between $" + MINCASH + " and $" + MAXCASH + ")");			
			if (!input.hasNextDouble()) { //is user input of type double?
				input.next();
				System.out.println("Input must be a number");
			} else {				
				userInput = input.nextDouble(); //User input is confirmed to be of type double		
				if (userInput < MINCASH || userInput > MAXCASH) { //I usually don't like to nest if statements, but I wanted unique error messages for each case
					System.out.println("Input must be within range");
					userInput = 0;
				} else {
					validInput = true; //all input has been validated, the response is a double within range
				}			
			}						
		}
		return userInput; //Return the user's validated input
	}
	
	/**
	 * Gets input for the choice the user selects on the menu
	 * <p>
	 * Prompts the user for the choice the would like to select
	 * Checks if the choice is of type integer and falls between MINCHOICE and MAXCHOICE
	 * Checks if user has enough money for their selected choice
	 * 
	 * Very similar to getUserStartingMoney(), main difference is that it works in type integer and checks if the user has enough cash
	 * for their selected option. 
	 * <p>
	 * 
	 * @return An integer representing the user's choice
	 */
	public static int getUserChoice() {
		boolean validInput = false; //whether or not use input is valid (of type double between min and max cash)
		int userInput = 0; //used to store user input during the process, 0 is a placeholder number since this variable must be declared. 
		while (!validInput) {			
			System.out.println(choiceMenu());
			System.out.println("Select a choice");			
			if (!input.hasNextInt()) { //is user input of type int?
				input.next();
				System.out.println("Input must be a number");
			} else {				
				userInput = input.nextInt(); //User input is confirmed to be of type int
				if (userInput < MINCHOICE || userInput > MAXCHOICE) { //Determine if choice is within range of available choices
					System.out.println("Input must be within range");
				} else if (userCash < 1 && userInput == 3 || userCash < .5 && userInput != 1) {
					System.out.println("You do not have enough money for that choice");
				} else {
					validInput = true; //all input has been validated, the response is a int within range and user has enough money
				}			
			}						
		}
		return userInput; //Return the user's validated input
	}
	
	/**
	 * Executes the slot machine process
	 * <p>
	 * Runs the slot machine process by creating three random integers representing the wheels of the machine,
	 * then prints the corresponding fruits for those numbers. It calls a method to find matches and change 
	 * the user cash based on the bet. 
	 * <p>
	 * 
	 * @param Bet the amount of money the user is betting
	 */
	public static void spinSlotMachine(double bet) {

		
		int wheel1 = ThreadLocalRandom.current().nextInt(1, 7); //Give the three wheels a random value 1 through 6
		int wheel2 = ThreadLocalRandom.current().nextInt(1, 7);
		int wheel3 = ThreadLocalRandom.current().nextInt(1, 7);
		
		printFruitName(wheel1); //print out the values for the user to see as fruit
		printFruitName(wheel2);
		printFruitName(wheel3);
		
		System.out.println(); //blank space
		
		findMatches(wheel1, wheel2, wheel3, bet);
		
	}
	
	/**
	 * Finds matching numbers between the three inputs and applies
	 * the bet based on those matches
	 * 
	 *<p>
	 *Finds the number of matches between the three inputs. When two numbers match,
	 *it adds the amount bet to the total cash. When three numbers match, it adds
	 *twice the amount bet to the total cash. When no numbers match, it removes
	 *the amount bet from the total cash. 
	 *<p>
	 * @param w1 Wheel 1 of the slot machine
	 * @param w2 Wheel 2 of the slot machine
	 * @param w3 Wheel 3 of the slot machine
	 * @param bet The amount the user wishes to bet
	 */
	
	public static void findMatches(int w1, int w2, int w3, double bet) {
		if (w1 == w2 && w2 == w3) {  //all matches adds 2x bet
			userCash += (bet * 2);
			System.out.println("All matches!");
			System.out.printf("Your total changed by $%.2f \n", bet * 2);
			System.out.printf("You have $%.2f", userCash);
			System.out.println();
		} else if (w1 == w2 || w1 == w3 || w2 == w3) { //two matches adds 1x bet
			userCash += bet; 
			System.out.println("Two matches!");
			System.out.printf("Your total changed by $%.2f \n", bet);
			System.out.printf("You have $%.2f", userCash);
			System.out.println();
		} else { //else = no matches, subtracts 1x bet
			userCash -= bet;
			System.out.println("No matches :(");
			System.out.printf("Your total changed by -$%.2f \n", bet);
			System.out.printf("You have $%.2f", userCash);
			System.out.println();
		}
	}
	
	/**
	 * Decodes the user's choice into a number representing the amount they wish to bet
	 * @param choice The user's selected choice 1-4
	 * @return The amount of money the user wants to bet
	 */
	public static double decodeUserChoice(int choice) {
		switch(choice) {
		case 1:
			return .25;		
		case 2:
			return .5;		
		case 3:
			return 1;			
		default:
			return 0;
		}
	}
	
	/**
	 * Decodes and prints an integer into a fruit type
	 * @param number The number being decoded (1-6)
	 */
	
	public static void printFruitName(int number) {
		switch(number) {
		case 1: 
			System.out.println("Cherries");
			break;
		case 2: 
			System.out.println("Oranges");
			break;
		case 3: 
			System.out.println("Plums");
			break;
		case 4: 
			System.out.println("Bells");
			break;
		case 5: 
			System.out.println("Melons");
			break;
		case 6: 
			System.out.println("Bars");
			break;
		default:
			System.out.println("Error: invalid roll");
		
		}
	}
	
	/**
	 * Prints the outro of the program including various statistics about the user's games
	 * @param cashStart The amount of money the user started with before playing
	 * @param totalRuns The number of times the user spun the slot machine
	 */
	public static void printOutro(double cashStart, int  totalRuns){
		System.out.println("Overall results:");
		System.out.println();
		System.out.println("You played " + totalRuns + " games");
		System.out.printf("You started with $%.2f", cashStart);
		System.out.printf(" and ended with $%.2f", userCash);
		System.out.println();
		
		if (cashStart < userCash) { //user has more cash than they started with
			System.out.printf("You gained $%.2f", (userCash - cashStart));
		} else if (cashStart > userCash){ //user has less cash than they started with
			System.out.printf("You lost $%.2f", (cashStart - userCash));
		} else { //user's cash remained the same
			System.out.println("You did not gain or lose money");
		}
	}
	
}
