package com.bank.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bank.dao.UserDaoService;
import com.bank.exception.UserNotFoundException;
import com.bank.model.Transaction;
import com.bank.model.User;

public class UserResource {

	private UserDaoService service;

	public UserResource(UserDaoService service) {
		super();
		this.service = service;
	}

	public List<User> retrieveUsers() {
		return service.findAll();
	}

	public User retrieveUser(int id, String dateDuration) {

		User user = service.findOne(id);
		if (user == null)
			System.out.println("User record not found with id=" + id);

		return user;

	}
	
	public void retrieveUserTransactions(User user, String dateDuration) {

		List<Transaction> transactions = service.findOneMonthTransactions(user.getTransactions(), dateDuration);

		transactions.forEach(transaction -> System.out.println(transaction.toString()));
		
	}

	public double calculatePercentage(double obtained, double total) {
		
		return obtained * 100 / total;
	}

	public List<String> getClassification(User user, String transDate) {


		List<String> classification = new ArrayList<>();
		
		List<Transaction> filteredTransactions = service.findOneMonthTransactions(user.getTransactions(), transDate);

		long morningTransactions = filteredTransactions.stream() // Convert to steam
				.filter(transaction -> transaction.getTransactionDate().toLocalTime()
						.isBefore(LocalTime.parse("12:00")))
				.count();

		long afternoonTransactions = filteredTransactions.stream()
				.filter(transaction -> transaction.getTransactionDate().toLocalTime().isAfter(LocalTime.parse("12:00")))
				.count();

		long wholeMonthTransactions = filteredTransactions.size();

		if ((morningTransactions > 0) && (calculatePercentage(morningTransactions, wholeMonthTransactions) > 50))
			classification.add("MORNING PERSON");

		if ((afternoonTransactions > 0) && (calculatePercentage(afternoonTransactions, wholeMonthTransactions) > 50))
			classification.add("AFTERNOON PERSON");

		//CHECKS IF MAJORITY OF TRANSACTIONS ARE DONE IN THE MORNING
		BigDecimal totalDeposit = user.getDeposits(filteredTransactions).stream().map(Transaction::getTransactionAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		//CHECKS IF MAJORITY OF TRANSACTIONS ARE DONE IN THE AFTERNOON
		BigDecimal totalWithdrawals = user.getWithrawals(filteredTransactions).stream().map(Transaction::getTransactionAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		//CHECKS IF BIG SPENDER
		if (calculatePercentage(totalWithdrawals.doubleValue(), totalDeposit.doubleValue()) > 80) {
			classification.add("BIG SPENDER");

		} else {

			//CHECKS IF POTENTIAL SAVER
			if (calculatePercentage(totalWithdrawals.doubleValue(), totalDeposit.doubleValue()) < 25)
				classification.add("POTENTIAL SAVER");
		}

		//CHECKS IF BIG TICKET SPENDER
		long bigWithdrawals = filteredTransactions.stream() 
				.filter(transaction -> transaction.getTransactionAmount().doubleValue() > 1000).count();
		if (bigWithdrawals > 0)
			classification.add("BIG TICKET SPENDER");

		//CHECKS IF FAST SPENDER
		long fastWithdrawals =  user.getDeposits(filteredTransactions).stream()
		.filter(transaction -> hasWithdrawalWithin7Days(user.getWithrawals(filteredTransactions),transaction))
		.count();
		if (fastWithdrawals > 0)
			classification.add("FAST SPENDER");
		
		//CHECKS IF POTENTIAL LOAN
		if(classification.contains("BIG SPENDER") && classification.contains("FAST SPENDER")) {
			
			classification.remove("BIG SPENDER");
			classification.remove("FAST SPENDER");
			classification.add("POTENTIAL LOAN");
		}
			
		return classification;
	}
	
	private boolean hasWithdrawalWithin7Days(List<Transaction> withdrawalTransactions, Transaction transaction) {
				LocalDateTime dateRange = transaction.getTransactionDate().plusDays(8);
		//sum the withdrawals within 7 days is greater than 75% of deposited amount on day1
		BigDecimal sumOfWithdrawal = withdrawalTransactions.stream()
							.filter(withdrawal -> withdrawal.getTransactionDate().isBefore(dateRange) && withdrawal.getTransactionDate().isAfter(transaction.getTransactionDate().minusDays(1)))
							.map(Transaction::getTransactionAmount).reduce(BigDecimal.ZERO, BigDecimal::add).abs();
		
		if(sumOfWithdrawal.doubleValue() > 0 && (calculatePercentage(sumOfWithdrawal.doubleValue(), (transaction.getTransactionAmount().doubleValue())) > 75))
			return true;
		
		return false;
	}

}
