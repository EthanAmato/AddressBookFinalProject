package tts.addressbook;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AddressBook {
	private ArrayList<Entry> entries;
	private Scanner fileReader;
	private String bookName;
	private String filePath;

	private Scanner input = new Scanner(System.in);

	public String getBookName() {
		return bookName;
	}

	public String getFilePath() {
		return filePath;
	}

	private void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public AddressBook(String bookName) {
		this.bookName = bookName;
		this.entries = new ArrayList<>();
		this.fileReader = new Scanner(System.in);
		initializeAddressBook();
	}
	
	
	private void initializeAddressBook() { //Name of addressbook, parse through a txt file and initialize entries into existing object
		//check for file of name filePath.txt in directory
		
		String filePath = bookName;
		
		String fileName = String.format("\\%s", filePath);
		String absoluteFilePath =(System.getProperty("user.dir")+"\\src\\tts\\addressbook\\bookstorage\\"+fileName+".txt");
		Path p1 = Paths.get(absoluteFilePath); //navigates to bookstorage folder to inputted text file
		boolean exists = Files.exists(p1);    //checks if the path to text file exists
		
		if (exists) {
			File addressBookFile = new File(absoluteFilePath);

			try {
				Scanner sc = new Scanner(addressBookFile);
				String[] splitAddress;
				while(sc.hasNextLine()) {
					splitAddress = sc.nextLine().split(";"); //EAch line split on ; delimiter
					addEntry(new Entry(splitAddress[0],splitAddress[1],splitAddress[2],splitAddress[3])); //Firstname, Lastname, Phone Number, Email
				}
				this.setFilePath(absoluteFilePath);
				sc.close();

			} catch (FileNotFoundException e) {System.out.println("File Not Found");}  
		} else {
			File newAddressBook = new File(absoluteFilePath);
			System.out.println(newAddressBook);
			try {
				newAddressBook.createNewFile();
				System.out.println(absoluteFilePath);
				System.out.printf("New Addressbook %s.txt Created Successfully\n", filePath);
				this.setFilePath(absoluteFilePath);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public void saveAddressBook() { //call after do while ends, format each entry onto a single line
		File addressBookFile = new File(this.getFilePath());
		FileWriter fw;
		try {
			fw = new FileWriter(addressBookFile);
			BufferedWriter out = new BufferedWriter(fw);
			for(Entry address: entries) {
				out.write(address.entryToFileFormat());
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void addEntry(Entry newEntry) {
		entries.add(newEntry);
	}
	
	public void addEntry() {
		String firstName;
		String lastName;
		String phoneNum;
		String email;
		
		
		System.out.println("First Name:");
		firstName = input.nextLine().strip();
		System.out.println("Last Name:");
		lastName = input.nextLine().strip();
		System.out.println("Phone Number:");
		phoneNum = input.nextLine().strip();
		System.out.println("Email Address:");
		email = input.nextLine().strip();
		for(Entry entry: entries) {
			if (entry.getEmailAddress().equals(email)) {
				System.out.printf("Did not add %s %s because %s is already in the Addressbook...\n",firstName,lastName,email);
				return;
			}
		}
		entries.add(new Entry(firstName,lastName,phoneNum,email));
		System.out.printf("Successfully added %s %s to AddressBook...\n",firstName, lastName);
	}
	
	public void removeEntry() {//specified in instructions to remove entries based on email
		String entryToRemove;
		
		System.out.println("Enter an entry's email to remove: ");
		entryToRemove = input.nextLine();
		
		for(Entry entry: entries) {
			if (entry.getEmailAddress().equals(entryToRemove)) {
				System.out.println("Deleted the following entry: ");
				System.out.println(entry);
				entries.remove(entry);
				return;
			}
		}
		System.out.printf("An entry containing %s could not be found.\n", entryToRemove);
	}
	
	public void searchEntry(String entryInfo, Integer infoType) {
		for(Entry entry: entries) {
			switch(infoType) {
			case 1:
				if (entry.getFirstName().contains(entryInfo)) {
					System.out.println(entry);
				}
				break;
			case 2:
				if (entry.getLastName().contains(entryInfo)) {
					System.out.println(entry);
				}
				break;
			case 3:
				if (entry.getPhoneNumber().contains(entryInfo)) {
					System.out.println(entry);
				}
				break;
			case 4:
				if (entry.getEmailAddress().contains(entryInfo)) {
					System.out.println(entry);
				}
				break;
			default:
				System.out.printf("An entry containing %s could not be found.\n", entryInfo);
				break;
			}
		}
		
	}	
	
	public void deleteBook() {
		System.out.printf("Clearing Address Book %s...\n",bookName);
		entries.clear();
		File file = new File(filePath);
		if (file.delete()) {
            System.out.printf("Addressbook '%s' deleted successfully\n",bookName);
        }
        else {
            System.out.printf("Failed to delete the Addressbook '%s'\n",bookName);
        }
	}
	
	//quit implemented by breaking out of do while loop
	
	public void printAddressBook() {
		if(entries.size() == 0) {
			System.out.printf("Unable to print contents of %s because it is empty...\n", bookName);
		} else {
			System.out.printf("Printing the Contents of %s...\n", bookName);
			for (Entry entry: entries) {
				System.out.println(entry);
			}
		}
	}
	
	public static String[] returnCurrentAddressBooks() {
		File folder = new File(System.getProperty("user.dir")+"\\src\\tts\\addressbook\\bookstorage\\");
		File[] listOfFiles = folder.listFiles();
		Integer counter = 1;
		String[] fileNames = new String[listOfFiles.length];
		
		String fileName;
		
		for (File file: listOfFiles) {
			fileName = file.getName().split(".txt")[0];
			System.out.println(counter.toString()+". "+fileName);
			fileNames[counter-1] = fileName;
			counter+=1;
		}
		return fileNames;
	}
 
}
