package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class User {

	private Integer id;
	private String name;
	private Date birthDate;

	private List<Transaction> transactions;

	// private List<String> classification;

	public User() {

	}

	public User(Integer id, String name, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public User(Integer id, String name, Date birthDate, List<Transaction> transactions) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.transactions = transactions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public BigDecimal getCurrentBalance(List<Transaction> transactions) {

		if (transactions != null)
			return transactions.stream().map(Transaction::getTransactionAmount).reduce(BigDecimal.ZERO,
					BigDecimal::add);
		else
			return BigDecimal.valueOf(0);

	}

	public List<Transaction> getDeposits(List<Transaction> transactions) {

		return transactions.stream() // Convert to steam
				.filter(transaction -> transaction.getTransactionAmount().doubleValue() > 0)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public List<Transaction> getWithrawals(List<Transaction> transactions) {

		return transactions.stream() // Convert to steam
				.filter(transaction -> transaction.getTransactionAmount().doubleValue() < 0)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", transactions=" + transactions
				+ "]";
	}

}
