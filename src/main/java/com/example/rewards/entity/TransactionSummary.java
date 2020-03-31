package com.example.rewards.entity;

import java.util.List;

/**
 * Represents transaction date for all customers.
 */
public class TransactionSummary
{
	/**
	 * The customers and their transaction data.
	 */
	private List<Customer> _customers;


	public List<Customer> getCustomers()
	{
		return _customers;
	}


	public void setCustomers(List<Customer> customers)
	{
		_customers = customers;
	}
}
