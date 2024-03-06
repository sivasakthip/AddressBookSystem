package com.AddressManagementSystem;

	import java.io.*;
	import java.util.*;
	import java.util.ArrayList;
	import java.util.List;
	
	@SuppressWarnings("serial")
	class Contact implements Serializable { 
		private String name;
		private String phoneNumber;
		private String emailAddress;		

		public Contact(String name, String phoneNumber, String emailAddress) {
			this.name = name;
			this.phoneNumber = phoneNumber;
			this.emailAddress = emailAddress;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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
	}
	
	class AddressBook {
		private List<Contact> contacts;
		
		public AddressBook() {
			contacts = new ArrayList<>();
		}
		
		public void addContact(Contact contact) {
			contacts.add(contact);
		}
		
		public void removeContact(Contact contact) {
			contacts.remove(contact);
		}
		
		public Contact searchContact(String name) {
			for(Contact contact : contacts) {
				if(contact.getName().equalsIgnoreCase(name)) {
					return contact;
				}
			}
			return null;
		}
		
		public void displayAllContacts() {
			for(Contact contact : contacts) {
				System.out.println(contact.getName() + " | " + contact.getPhoneNumber() + " | " + contact.getEmailAddress());
			}
		}
		
		public List<Contact> getAllContacts() {
			return contacts;
		}
	}
	

	public class CMS {
		private static final String FILE_NAME = "contact.ser";
		public static void main(String[] args) {
			AddressBook addressBook = new AddressBook();
			
			Scanner in = new Scanner(System.in);
			int choice;
			
			do {
				System.out.println("1. Add Contact");
				System.out.println("2. Remove Contact");
				System.out.println("3. Search Contact");
				System.out.println("4. Display All Contacts");
				System.out.println("5. Exit");
				
				choice = in.nextInt();
				in.nextLine();
				
				switch(choice) {
				case 1:
					addContact(addressBook, in);
					break;
				case 2:
					removeContact(addressBook, in);
					break;
				case 3:
					searchContact(addressBook, in);
					break;
				case 4:
					displayAllContacts(addressBook);
					break;
				case 5:
					saveAddressBook(addressBook);
					break;
				default:
					System.out.println("Invalid Option");
				}
			} while (choice !=5);
			in.close();
		}
	
		private static void addContact(AddressBook addressBook, Scanner in) {
			System.out.println("Enter name: ");
			String name = in.nextLine();
			System.out.println("Enter the Phone Number");
			String phoneNumber = in.nextLine();
			System.out.println("Enter the Email Address");
			String emailAddress = in.nextLine();
			
			Contact contact = new Contact(name, phoneNumber, emailAddress);
			
			addressBook.addContact(contact);
			
			System.out.println("Contact Added Successfully");
		}
		
	   private static void removeContact(AddressBook addressBook, Scanner in) {
		   System.out.println("Enter the name of Contact to Remove: ");
		   String name = in.nextLine();
		   
		   Contact contactToRemove = addressBook.searchContact(name);
		   if(contactToRemove != null) {
			   addressBook.removeContact(contactToRemove);
			   System.out.println("Contact Removed Successfully");
		   } else {
			   System.out.println("Contact not Found");
		   }
	   }
	   
	  private static void searchContact(AddressBook addressBook, Scanner in) {
		  System.out.println("Enter the name of Contact to Search: ");
		  String name = in.nextLine();
		  
		  Contact contactToSearch = addressBook.searchContact(name);
		  if(contactToSearch != null) {
			  System.out.println("Contact Found");
			  System.out.println(contactToSearch.getName() + " | " + contactToSearch.getPhoneNumber() + " | " + contactToSearch.getEmailAddress());
		  } else {
			  System.out.println("Contact not Found");
		  }
	  }
	  
	  private static void displayAllContacts(AddressBook addressBook) {
			System.out.println("Display All Contacts");
			
			addressBook.displayAllContacts();
		}

	  
	  private static AddressBook loadAddressBook() {
		    AddressBook addressBook = null;
		    
		    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
		        addressBook = (AddressBook) ois.readObject();
		        System.out.println("Address Book Loaded Successfully");
		    } catch (FileNotFoundException ex) {
		        System.out.println("No existing address book found, creating a new one");
		        addressBook = new AddressBook();
		    } catch (IOException | ClassNotFoundException ex) {
		        System.out.println("Error Loading Address Book: " + ex.getMessage());
		    }
		    return addressBook;
		}
	  
	  @SuppressWarnings("unused")
	private static void saveAddressBook(AddressBook addressBook) {
		  
		  try
		  	(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
				  oos.writeObject(addressBook);
				  System.out.println("Address Book Saved Successfully");
			  } catch (IOException ex) {
				  System.out.println("Error Saving Address Book: "+ ex.getMessage());
			  }
		  }
	  }
