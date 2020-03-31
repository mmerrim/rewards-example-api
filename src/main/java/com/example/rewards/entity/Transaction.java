package com.example.rewards.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a single customer transaction.
 */
public class Transaction
{
	/**
	 * Transaction amount.
	 */
	private BigDecimal _amount;

	/**
	 * Transaction date.
	 */
	private LocalDate _transactionDate;


	public BigDecimal getAmount()
	{
		return _amount;
	}


	public void setAmount(final BigDecimal amount)
	{
		_amount = amount;
	}


	public LocalDate getTransactionDate()
	{
		return _transactionDate;
	}


	public void setTransactionDate(final LocalDate transactionDate)
	{
		_transactionDate = transactionDate;
	}
}
