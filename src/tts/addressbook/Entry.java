package tts.addressbook;

import java.util.Arrays;
import java.util.List;

public class Entry {

//Initialize Variables
	
	private String firstName;
	private String lastName;
	private String phoneNumber; //Treat as string because of typical phone-number formatting
								//E.g. (123)456-7890
	private String emailAddress;
	
//Entry Getters + Setters
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	
//Constructor
	public Entry(String firstName, String lastName, String phoneNumber, String emailAddress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}
	
	private static String spacerString() {
		return "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n";
	}
	
	public String entryToFileFormat() {
		List<String> properties = Arrays.asList(getFirstName(),getLastName(),getPhoneNumber(),getEmailAddress());
		return String.join(";", properties)+"\n"; 
	}
	
	@Override
	public String toString() {
		String msg = "";
		msg += spacerString();
		
		msg+= ("First Name: " + this.firstName);
		msg+= ("\nLast Name: " + this.lastName);
		msg+= ("\nPhone Number: " + this.phoneNumber);
		msg+= ("\nEmail Address: " + this.emailAddress);

		msg += "\n"+spacerString();

		return msg;
		
	}
}
