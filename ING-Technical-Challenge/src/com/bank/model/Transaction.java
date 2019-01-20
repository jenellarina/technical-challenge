package com.bank.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class Transaction {
	private Integer id;
	private String description;

	private String transactionType;
	
	private LocalDateTime transactionDate;
	
	private BigDecimal transactionAmount;

	private User user;
	
	public Transaction() {
		super();
	}

	public Transaction(Integer id, String description, User user) {
		super();
		this.id = id;
		this.description = description;
		this.user = user;
	}
	
	

	public Transaction(Integer id, String description, String transactionType, LocalDateTime transactionDate,
			BigDecimal transactionAmount, User user) {
		
		super();
		this.id = id;
		this.description = description;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.transactionAmount = transactionAmount;
		this.user = user;
	}

	@Override
	public String toString() {
		
		return String.format("Transaction [id= %s, description= %s, date=%s,  amount= %s]", id, description,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
				  .format(transactionDate), transactionAmount);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	
}
