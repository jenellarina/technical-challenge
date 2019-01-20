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
	

	private static List<Transaction> transactions = new ArrayList<>();
	
	private static int usersCount = 3;
	static {
		
		//ADD USER 1:

		User user1 = new User(10001, "Jose Dela Cruz", new Date());
		
		
		Transaction trans1 = new Transaction(11001, "1st transaction", "debit",LocalDateTime.of(2018, Month.JANUARY, 1, 10, 10, 30),new BigDecimal(1000.0), user1);
		Transaction trans2 = new Transaction(11001, "2nd transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 15, 10, 10, 30),new BigDecimal(2000.0), user1);
		Transaction trans3 = new Transaction(11001, "3rd transaction", "debit",LocalDateTime.of(2018, Month.DECEMBER, 1, 10, 10, 30),new BigDecimal(3000.0), user1);
		Transaction trans4 = new Transaction(11001, "4th transaction", "debit",LocalDateTime.of(2019, Month.JANUARY, 1, 10, 10, 30),new BigDecimal(100.0), user1);
		Transaction trans5 = new Transaction(11001, "5th transaction", "debit",LocalDateTime.of(2019, Month.JANUARY, 17, 10, 10, 30),new BigDecimal(5000.0), user1);
		Transaction trans6 = new Transaction(11001, "6th transaction", "credit",LocalDateTime.of(2019, Month.JANUARY, 19, 10, 10, 30),new BigDecimal(-5000.0), user1);
		Transaction trans7 = new Transaction(11001, "7th transaction", "debit",LocalDateTime.of(2019, Month.JANUARY, 20, 10, 10, 30),new BigDecimal(100.0), user1);
		Transaction trans8 = new Transaction(11001, "8th transaction", "debit",LocalDateTime.of(2018, Month.OCTOBER, 15, 10, 10, 30),new BigDecimal(1000.0), user1);
		Transaction trans9 = new Transaction(11001, "9th transaction", "debit",LocalDateTime.of(2018, Month.JANUARY, 20, 10, 10, 30),new BigDecimal(200.0), user1);
		

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
	//findOne
	public User findOne(int id, String transDate) {
		for(User user:users) {
			if(user.getId() == id) {
				
				user.setTransactions(transactions);
				return user;
			}
		}
		return null;
	}
	

}
