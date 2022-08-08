package tts.addressbook;

import java.util.Arrays;
import java.util.Scanner;


public class AddressBookUI {

	
	
	public static void printMainMenu() {
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("Enter the number corresponding what you would like to do with your address book...\n");
		System.out.println("1) Add an entry\n");
		System.out.println("2) Remove an entry\n");
		System.out.println("3) Search for a specific entry\n");
		System.out.println("4) Print Address Book\n");
		System.out.println("5) Delete Book\n");
		System.out.println("6) Quit"); 
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		System.out.print("User Input: \n");

	}
	
	public static boolean checkNum(String input, int optionNum) { //Function that takes a given input that should parse to int and the number of possible options given 2 user.
		try {
			Integer intInput = Integer.parseInt(input);
			if (intInput > optionNum || intInput <= 0) {
				System.out.println("Please try another entry. Make sure to use a number that is available.");
				return false; //returns false if out of range of # of possible options
			} else { 
				return true;
			}
		} catch(Exception e) { //doesn't parse into an integer1
			System.out.println("Please try another entry. Make sure to use a number.");
			return false;
		}		
	}
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		boolean continueLoop = true;
		boolean accessingBook = true;
		String nextChoice;
		String confirm;
		String choiceInput;
		String searchInfo;
		AddressBook addressBook;
		
		while(continueLoop) {
			System.out.println("Please type in the name of an AddressBook you would like to access:");
			System.out.println("Names that are not already present will automatically be turned into a new address book.");
			
			String[] currentAddressBooks = AddressBook.returnCurrentAddressBooks();
			System.out.println("\nWritten name of AddressBook:");

			String addressBookName = input.nextLine();
			
			if (Arrays.asList(currentAddressBooks).contains(addressBookName)) { //the way AddressBook.initializeAddressBook() has been written allows for this to just be the illusion of giving user the choice
																				//it'll happen anyway
				System.out.printf("Accessing Addressbook '%s'...\n",addressBookName);
				
			} else {
				System.out.printf("Creating address book '%s' and accessing it...",addressBookName);
			}
			
			addressBook = new AddressBook(addressBookName);
			accessingBook = true;
			
			while(accessingBook) {
				printMainMenu();
				do {
					nextChoice = input.nextLine();
				} while(checkNum(nextChoice, 6) == false);
				
				//System.out.println(nextChoice);
				
				switch(Integer.parseInt(nextChoice)) {
				case 1:
					addressBook.addEntry();
					addressBook.saveAddressBook();
					break;
				case 2:
					addressBook.removeEntry();
					addressBook.saveAddressBook();
					break;
				case 3:
					//ask for parameter of entry to look up by
					//addressBook.searchEntry(entryInfo, entryType);
					System.out.println("Choose a search type?\n1)First Name\n2)Last Name\n3)Phone Number\n4)Email Address\n");
					do {
						choiceInput = input.nextLine();
					} while(checkNum(choiceInput,4) == false);
					System.out.println("Please Enter your Search: ");
					searchInfo = input.nextLine();
					addressBook.searchEntry(searchInfo, Integer.parseInt(choiceInput));
					break;
				case 4:
					addressBook.printAddressBook();
					break;
				case 5:
					System.out.println("Are you sure you would like to delete this book?\n1) Yes\n2) No");
					do {
						confirm = input.nextLine();
					} while(checkNum(confirm,2) == false);
					if (Integer.parseInt(confirm) == 1) {
						addressBook.deleteBook(); //automatically saves itself so no need for save
						accessingBook = false;
					} else {
						continue;
					}
					break;
				case 6:
					addressBook.saveAddressBook();
					accessingBook = false;
					break;
				}
				
			}
			
			//Ask if they want to work on another book
			System.out.println("Would you like to work on another address book?\n1) Yes\n2) No");
			do {
				confirm = input.nextLine();
			} while(checkNum(confirm,2) == false);
			if (Integer.parseInt(confirm) == 1) { 
				continue;
			} else {
				System.out.println("\n\nClosing database...");
				continueLoop = false;
			}
		}
		


	}

}
