package com.example.rewards.service;

import com.example.rewards.entity.Customer;
import com.example.rewards.entity.MonthlyPoints;
import com.example.rewards.entity.RewardPointSummary;
import com.example.rewards.entity.Transaction;
import com.example.rewards.exception.CustomerNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

/**
 * Service for working with rewards.
 */
public class RewardService
{
	/**
	 * Stores the customer transaction data in memory. In a production app, this would likely be stored in a DB instead.
	 */
	private Map<Long, List<Transaction>> _transactionMap = new HashMap<>();


	/**
	 * Stores the customer transaction data for later querying. This is not thread-safe.
	 *
	 * @param customers Customer transaction data.
	 */
	public void storeTransactions(List<Customer> customers)
	{
		_transactionMap = new HashMap<>();

		for (final Customer customer : customers)
		{
			_transactionMap.put(customer.getCustomerId(), customer.getTransactions());
		}
	}


	/**
	 * Calculate the monthly and total reward points for a customer.
	 *
	 * @param customerId ID of the customer to look up.
	 *
	 * @return The reward point summary for the customer.
	 */
	public RewardPointSummary calculateRewardSummary(final Long customerId)
	{
		List<Transaction> transactions = _transactionMap.get(customerId);

		if (transactions == null)
		{
			throw new CustomerNotFoundException();
		}

		final List<MonthlyPoints> monthlyPoints = calculatePointsForCustomer(transactions);

		return new RewardPointSummary(customerId, monthlyPoints);
	}


	/**
	 * Calculate the points for all transactions for a customer.
	 *
	 * @param transactions The customer's transactions.
	 *
	 * @return Points grouped by month for the customer's transactions.
	 */
	private List<MonthlyPoints> calculatePointsForCustomer(List<Transaction> transactions)
	{
		final List<MonthlyPoints> monthlyPoints = new LinkedList<>();
		final Map<LocalDate, Long> pointsPerMonthMap = new HashMap<>();

		for (final Transaction transaction : transactions)
		{
			calculatePointsForTransaction(pointsPerMonthMap, transaction);
		}

		for (final Map.Entry<LocalDate, Long> entry : pointsPerMonthMap.entrySet())
		{
			monthlyPoints.add(new MonthlyPoints(entry.getValue(), entry.getKey()));
		}

		return monthlyPoints;
	}


	/**
	 * Calculates the points for a single transaction.
	 *
	 * @param pointsPerMonthMap Map of months to accumulated points.
	 * @param transaction The current transaction for which to calculate points.
	 */
	private void calculatePointsForTransaction(Map<LocalDate, Long> pointsPerMonthMap, Transaction transaction)
	{
		final long points = determinePoints(transaction.getAmount());

		if (points > 0)
		{
			final LocalDate firstOfMonth = determineFirstOfMonth(transaction.getTransactionDate());
			final Long existingPoints = pointsPerMonthMap.get(firstOfMonth);

			pointsPerMonthMap.put(firstOfMonth, existingPoints == null ? points : existingPoints + points);
		}
	}


	/**
	 * Determines the first day of a month for a given date.
	 *
	 * @param transactionDate Date of month.
	 *
	 * @return A {@link LocalDate} representing the first day of the month passed in.
	 */
	private LocalDate determineFirstOfMonth(LocalDate transactionDate)
	{
		return transactionDate.withDayOfMonth(1);
	}


	/**
	 * Determine how many points to reward for a given transaction amount.
	 *
	 * @param amount The transaction amount.
	 *
	 * @return How many reward points should be awarded for the given transaction. Points are not awarded for
	 * fractional dollar amounts, $101.99 is rounded down to $101.
	 */
	private long determinePoints(BigDecimal amount)
	{
		final long roundedAmount = amount.setScale(0, RoundingMode.FLOOR).longValue();
		final long over100Dollars = Math.max(roundedAmount - 100, 0);
		final long over50Dollars = Math.max(roundedAmount - 50, 0);

		return (over100Dollars * 2) + over50Dollars;
	}
}
