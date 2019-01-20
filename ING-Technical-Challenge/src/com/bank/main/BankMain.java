package com.bank.main;

import java.time.LocalDate;
import java.util.Scanner;

import com.bank.controller.UserResource;
import com.bank.dao.UserDaoService;
import com.bank.model.User;

public class BankMain {

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// call the object initialization
		System.out.print("Enter customer ID: ");
		String custIdString = scanner.next();

		try {
			int custId = Integer.parseInt(custIdString);
			scanner.nextLine();
			System.out.println("Month and Year in this format yyyy-mm (sample: 2019-01)");
			System.out.print("Enter month and year: ");
			String dateDuration = scanner.next();

			UserResource userResource = new UserResource(new UserDaoService());
			User retrievedRecord = userResource.retrieveUser(custId, dateDuration);

			if (retrievedRecord != null) {
				System.out.println("USER RECORD		:: " + retrievedRecord.toString());

				if (retrievedRecord.getTransactions() != null) {
					System.out.println(
							"CLASSIFICATION	:: " + userResource.getClassification(retrievedRecord, dateDuration));

					System.out.println("TRANSACTIONS	:: ");
					userResource.retrieveUserTransactions(retrievedRecord, dateDuration);
				}
				System.out.println("CURRENT BALANCE	:: "
						+ retrievedRecord.getCurrentBalance(retrievedRecord.getTransactions()));
			}
		} catch (Exception e) {
			System.out.print("Invalid input.");
		}

		scanner.close();

		// call frame object

	}

}
