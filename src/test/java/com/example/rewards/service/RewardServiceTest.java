package com.example.rewards.service;

import com.example.rewards.controller.RewardAPI;
import com.example.rewards.entity.*;
import com.example.rewards.exception.CustomerNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Tests the {@link RewardService} class.
 */
public class RewardServiceTest
{
	/**
	 * Tests the {@link RewardService#calculateRewardSummary(Long)} method.
	 */
	@Test
	public void calculateRewardSummary()
	{
		final RewardService rewardService = new RewardService();
		final RewardAPI rewardAPI = new RewardAPI(rewardService);
		final TransactionSummary transactionSummary = createTransactionSummary();

		rewardAPI.storeTransactions(transactionSummary);

		final RewardPointSummary customer1Summary = rewardAPI.calculateRewardPointSummary(1L);

		// This is the example transaction in the problem description, the problem description says the total should
		// be 90 but it seems like it should be 110. There is either a mistake in my interpretation of the problem or
		// a mistake in the description, need to clarify.
		Assert.assertEquals(110, customer1Summary.getTotalPoints());

		final RewardPointSummary customer2Summary = rewardService.calculateRewardSummary(2L);
		final MonthlyPoints februaryPoints = customer2Summary.getMonthlyPoints().get(0);
		final MonthlyPoints januaryPoints = customer2Summary.getMonthlyPoints().get(1);

		// Sent in 3 months transactions, but only 2 should get non-zero point totals.
		Assert.assertEquals(2, customer2Summary.getMonthlyPoints().size());

		// Above 50 points but below 100 should only get the 50+ points.
		Assert.assertEquals(49, februaryPoints.getPoints());
		Assert.assertEquals(1950, januaryPoints.getPoints());

		// Total points should be sum of the monthly points.
		Assert.assertEquals(februaryPoints.getPoints() + januaryPoints.getPoints(),
				customer2Summary.getTotalPoints());

		final RewardPointSummary customer3Summary = rewardService.calculateRewardSummary(3L);

		// No transactions should mean no monthly totals and no total points.
		Assert.assertEquals(0, customer3Summary.getMonthlyPoints().size());
		Assert.assertEquals(0, customer3Summary.getTotalPoints());
	}


	/**
	 * Tests that the {@link RewardService#calculateRewardSummary(Long)} method returns a 404 for a missing customer ID.
	 */
	@Test
	public void test404ForMissingCustomer()
	{
		final RewardService rewardService = new RewardService();
		final RewardAPI rewardAPI = new RewardAPI(rewardService);
		final TransactionSummary transactionSummary = createTransactionSummary();

		rewardAPI.storeTransactions(transactionSummary);

		try
		{
			// No customer with ID -1.
			rewardAPI.calculateRewardPointSummary(-1L);

			Assert.fail("Should have thrown exception");
		}
		catch (final Exception e)
		{
			Assert.assertTrue(e instanceof CustomerNotFoundException);
		}
	}


	/**
	 * @return A list of customers to be used in the unit test.
	 */
	private TransactionSummary createTransactionSummary()
	{
		final TransactionSummary transactionSummary = new TransactionSummary();
		final List<Customer> customers = new LinkedList<>();

		for (int i = 0; i < 3; i++)
		{
			final Customer customer = new Customer();

			customer.setCustomerId((long) i + 1);

			customers.add(customer);
		}

		customers.get(0).setTransactions(createCustomer1Transactions());
		customers.get(1).setTransactions(createCustomer2Transactions());
		customers.get(2).setTransactions(new LinkedList<>());

		transactionSummary.setCustomers(customers);

		return transactionSummary;
	}


	/**
	 * @return Create transactions for customer 1.
	 */
	private List<Transaction> createCustomer1Transactions()
	{
		final List<Transaction> transactions = new LinkedList<>();

		transactions.add(createTransaction("120.00", "2020-01-01"));

		return transactions;
	}


	/**
	 * @return Create transactions for customer 2.
	 */
	private List<Transaction> createCustomer2Transactions()
	{
		final List<Transaction> transactions = new LinkedList<>();

		transactions.add(createTransaction("49.99", "2020-01-01"));
		transactions.add(createTransaction("99.99", "2020-02-29"));
		transactions.add(createTransaction("200.00", "2020-03-29"));
		transactions.add(createTransaction("300.00", "2020-03-29"));
		transactions.add(createTransaction("400.00", "2020-03-29"));

		return transactions;
	}


	/**
	 * @param amount Amount of the transaction as a String.
	 * @param dateString Date of the transaction as a String.
	 *
	 * @return A transaction created from the input data.
	 */
	private Transaction createTransaction(final String amount, final String dateString)
	{
		final Transaction transaction = new Transaction();

		transaction.setAmount(new BigDecimal(amount));
		transaction.setTransactionDate(LocalDate.parse(dateString));

		return transaction;
	}
}