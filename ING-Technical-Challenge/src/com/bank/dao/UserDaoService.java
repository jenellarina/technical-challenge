package com.bank.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.DateFormatter;

import com.bank.model.Transaction;
import com.bank.model.User;


public class UserDaoService {

	private static List<User> users = new ArrayList<>();
	

	
	
	private static int usersCount = 3;
	static {
		
		//ADD USER 1:

		User user1 = new User(10001, "Jose Dela Cruz", new Date());
		
		List<Transaction> transactions = new ArrayList<>();
		
		Transaction trans1 = new Transaction(11001, "1st transaction", "debit",LocalDateTime.of(2018, Month.JANUARY, 1, 10, 10, 30),new BigDecimal(1000.0), user1);
		Transaction trans2 = new Transaction(11002, "2nd transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 15, 10, 10, 30),new BigDecimal(2000.0), user1);
		Transaction trans3 = new Transaction(11003, "3rd transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 1, 10, 10, 30),new BigDecimal(3000.0), user1);
		Transaction trans4 = new Transaction(11004, "4th transaction", "debit",LocalDateTime.of(2019, Month.JANUARY, 1, 13, 40, 30),new BigDecimal(100.0), user1);
		Transaction trans5 = new Transaction(11005, "5th transaction", "debit",LocalDateTime.of(2019, Month.JANUARY, 17, 22, 10, 30),new BigDecimal(5000.0), user1);
		Transaction trans6 = new Transaction(11006, "6th transaction", "credit",LocalDateTime.of(2019, Month.JANUARY, 19, 13, 50, 30),new BigDecimal(-5000.0), user1);
		Transaction trans7 = new Transaction(11007, "7th transaction", "debit",LocalDateTime.of(2019, Month.JANUARY, 20, 15, 10, 30),new BigDecimal(100.0), user1);
		Transaction trans8 = new Transaction(11008, "8th transaction", "debit",LocalDateTime.of(2018, Month.OCTOBER, 15, 9, 10, 30),new BigDecimal(1000.0), user1);
		Transaction trans9 = new Transaction(11009, "9th transaction", "debit",LocalDateTime.of(2018, Month.JANUARY, 20, 10, 10, 30),new BigDecimal(200.0), user1);
		

		transactions.add(trans1);
		transactions.add(trans2);
		transactions.add(trans3);
		transactions.add(trans4);
		transactions.add(trans5);
		transactions.add(trans6);
		transactions.add(trans7);
		transactions.add(trans8);
		transactions.add(trans9);
		
		user1.setTransactions(transactions);
		users.add(user1);
		
		User user2 = new User(10002, "Jon Snow", new Date());
		users.add(user2);
		
		User user3 = new User(10003, "Joe Smith", new Date());
		transactions = new ArrayList<>();
		trans1 = new Transaction(11010, "1st transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 1, 10, 10, 30),new BigDecimal(1000.0), user3);
		trans2 = new Transaction(11011, "2nd transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 4, 8, 10, 30),new BigDecimal(2000.0), user3);
		trans3 = new Transaction(11012, "3rd transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 4, 11, 00, 30),new BigDecimal(3000.0), user3);
		trans4 = new Transaction(11013, "4th transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 10, 10, 10, 30),new BigDecimal(100.0), user3);
		trans5 = new Transaction(11014, "5th transaction", "credit",LocalDateTime.of(2018, Month.DECEMBER, 21, 10, 10, 30),new BigDecimal(-400.0), user3);
		
		transactions.add(trans1);
		transactions.add(trans2);
		transactions.add(trans3);
		transactions.add(trans4);
		transactions.add(trans5);
		user3.setTransactions(transactions);
		users.add(user3);
		
		User user4 = new User(10004, "Jane Vergara", new Date());
		transactions = new ArrayList<>();
		trans1 = new Transaction(11010, "1st transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 1, 10, 10, 30),new BigDecimal(1000.0), user4);
		trans2 = new Transaction(11011, "2nd transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 4, 8, 10, 30),new BigDecimal(-200.0), user4);
		trans3 = new Transaction(11012, "3rd transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 4, 11, 00, 30),new BigDecimal(-500.0), user4);
		trans4 = new Transaction(11013, "4th transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 6, 10, 10, 30),new BigDecimal(-150.0), user4);
		trans5 = new Transaction(11014, "5th transaction", "credit",LocalDateTime.of(2019, Month.JANUARY, 12, 10, 10, 30),new BigDecimal(-50.0), user4);
		
		transactions.add(trans1);
		transactions.add(trans2);
		transactions.add(trans3);
		transactions.add(trans4);
		transactions.add(trans5);
		user4.setTransactions(transactions);
		users.add(user4);
		
	}
	
	//findAll
	public List<User> findAll(){
		return users;
	}
	//save
	public User save(User user) {
		if(user.getId()==null) {
			user.setId(++usersCount);
		}
		
		users.add(user);
		return user;
	}
	
	//findOne
	public User findOne(int id) {
		for(User user:users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public List<Transaction> findOneMonthTransactions(List<Transaction> transactions, String transDate){
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		
		//filter month transactions
		YearMonth yearMonth = YearMonth.parse(transDate, formatter);
		List<Transaction> filtered = new ArrayList<>();
				
		filtered =	transactions.stream()
				.filter(transaction -> YearMonth.from(transaction.getTransactionDate().toLocalDate()).equals(yearMonth))
				.sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
				.collect(Collectors.toCollection(ArrayList::new));
		
		return filtered;
	}

	

}
