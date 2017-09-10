package proj4;
/**
 * This class loads all of the data from the files, receives user input,
 * handles exceptions, and creates histograms.
 * @author EJ Kim
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class BabyNames {
	public static void main(String[] args) {
		//declare variables to use
		int beginYear = 0;
		int endYear = 0;
		int args0 = 0;
		int args1 = 0;
		
		//no arguments runs the program with default values
		if (args.length == 0) {
			beginYear = 1880;
			endYear = 2015;
		}
		//check if first two arguments are integers, exit if they aren't integers
		else if (args.length >= 2) {
			if (!isInteger(args[0]) || !isInteger(args[1])) {
				System.err.println("Incorrect arguments given.");
				System.exit(1);
			}
			//if the first two are integers, parse them into ints
			else {
				args0 = Integer.parseInt(args[0]);
				args1 = Integer.parseInt(args[1]);
				//if the second argument is bigger than the first, throw and error and exit
				if (args0 <= args1) {
					beginYear = args0;
					endYear = args1;
				}
				else {
					System.err.println("Incorrect arguments given.");
					System.exit(1);
				}
			}
		}
		else {
			System.err.println("Incorrect arguments given.");
			System.exit(1);
		}

		//call the getData() and store its info into a YearNames ArrayList.
		ArrayList<YearNames> yearData = getData(beginYear, endYear);

		//set up Scanner input.
		Scanner input = new Scanner(System.in);
		String nameInput = "";
		String genderInput = "";

		//loop until user input is "q" or "Q".
		while (!nameInput.equalsIgnoreCase("q")) {
			//ask for user input and save it into nameInput variable.
			System.out.println("Which name's popularity would you like to check? (Enter 'q' to terminate program.)");
			nameInput = input.nextLine();

			//stop the loop immediately if the user input is "q" or "Q".
			if (nameInput.equalsIgnoreCase("q")) {
				break;
			}
			
			//ask for gender input and save it into genderInput variable
			System.out.println("Which gender do you want to check the name's popularity with?");
			genderInput = input.nextLine();
			
			//skip iteration and ask for name input if gender inut is invalid.
			if (!genderInput.equalsIgnoreCase("f") && !genderInput.equalsIgnoreCase("m")) {
				System.err.println("Invalid gender.");
				continue;
			}
			
			/* if valid name and gender are given, check if a Name object exists in
			 * any YearNames object in the YearNames ArrayList and display appropriate
			 * info.
			 */
			//set up variables to use.
			boolean nameDoesNotExist = true;
			Name nameToCheck = null;

			/*try/catch block to create Name objects for both genders
			 * with the given nameInput string as the name.
			 */
			try {
				nameToCheck = new Name(nameInput, genderInput, 0);
			}
			catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			}

			/*check if the Name objects for both genders with given nameInput string
			 * as the name was successfully created.
			 */
			if (nameToCheck != null) {
				/* for every YearNames object in the YearNames ArrayList,
				 * if the BST of any YearNames object contains the specified
				 * Name object, display histogram information and break the loop.
				 */
				for (int i = 0; i < yearData.size(); i++) {
					if (yearData.get(i).contains(nameToCheck)) {
						displayHistogram(nameInput, genderInput, yearData);
						nameDoesNotExist = false;
						break;
					}
				}
				//if the Name object isn't found in any YearNames object, print an error message.
				if (nameDoesNotExist) {
					System.err.println("No such name in dataset.");
				}
			}
		}
		input.close();
	}
	
	/**
	 * method to check if a string is an integer
	 * @param s a string
	 * @return true if string is an integer, false if otherwise
	 */
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    //only returns true if there were no exceptions when parsing
	    return true;
	}

	/**
	 * reads through all of the files and creates and adds all names
	 * as Name objects to a YearNames BST, which is added to a YearNames
	 * ArrayList.
	 * @return an ArrayList of YearNames 
	 */
	public static ArrayList<YearNames> getData(int beginYear, int endYear) {
		//create YearNames ArrayList.
		ArrayList<YearNames> yearData = new ArrayList<>();

		//loop through every file in given interval (inclusive).
		for (int i = beginYear; i < endYear + 1; i++) {
			//create File object and the Scanner for it. exit and show error
			//message if file can't be read or is not found.
			String fileName = "data/yob" + i + ".txt";
			File file = new File(fileName);
			if (!file.canRead()) {
				System.err.printf("Error: cannot read data from file " + fileName);
				System.exit(1);
			}
			Scanner inputFile = null; 
			try {
				inputFile = new Scanner(file);
			} 
			catch (FileNotFoundException e) {
				System.err.printf("Error: file " + fileName + "not found.");
				System.exit(1);
			}

			//create a yearNames object with given year.
			YearNames yearNames = new YearNames(i);

			//go through every line in each file
			while (inputFile.hasNextLine()) {
				/* save one line of the file to the String object input.
				 * separate the info using commas and store them into a String array.
				 */
				String input = inputFile.nextLine();
				String[] data = input.split(",");

				//set up variables for Name object using the String array.
				String name = data[0];
				String gender = data[1];
				int count = Integer.parseInt(data[2]);

				//create Name object reference and try to instantiate it.
				Name nameObject = null;
				try {
					nameObject = new Name(name, gender, count);
				}
				catch (IllegalArgumentException e) {
					System.err.println(e.getMessage() + " in " + fileName + ". Data not added.");
				}

				//if instantiation successful, try adding the nameObject to
				//the YearNames variable.
				if (nameObject != null) {
					try {
						yearNames.add(nameObject);
					}
					catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
					}
				}
			}
			//sort the YearNames variable and add it to the YearNames ArrayList.
			yearData.add(yearNames);
			inputFile.close();
		}
		//return YearNames ArrayList.
		return yearData;
	}

	/**
	 * goes through the YearNames ArrayList and finds the percentage of
	 * babies with the given name and displays a histogram for each year.
	 * @param name the String of the name to check and present data for.
	 * @param yearData the YearNames ArrayList with the data for all files.
	 */
	public static void displayHistogram(String name, String gender, ArrayList<YearNames> yearData) {
		//loop through YearNames ArrayList.
		for (int i = 0; i < yearData.size(); i++) {
			//get year data from each YearNames object.
			String year = yearData.get(i).getYear() + "";

			//get fraction data for given name and multiply it by 100 to get percent
			double percent = ((yearData.get(i).getFractionByName(name, gender))*100);

			//get number of bars data by dividing percent by 0.01 and rounding up.
			double numOfBars = Math.ceil(percent/0.01);
			String bars = "";
			for (int j = 0; j < numOfBars; j++) {
				bars += "|";
			}

			//print histogram data as "YYYY (F.FFFF): HISTOGRAM_BAR
			System.out.printf(year + " " + "(%.4f): " + bars + "\n", percent);
		}
	}
}
