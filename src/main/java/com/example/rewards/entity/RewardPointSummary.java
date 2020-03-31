package com.example.rewards.entity;

import java.util.List;

/**
 * Represents a summary of the reward points received by a group of customers over a 3 month period.
 */
public class RewardPointSummary
{
	/**
	 * ID of the customer.
	 */
	private final Long _customerId;

	/**
	 * A list of the monthly point totals.
	 */
	private final List<MonthlyPoints> _monthlyPoints;

	/**
	 * Sum of points for all months.
	 */
	private final long _totalPoints;


	/**
	 * @param monthlyPoints {@see #_monthlyPoints}.
	 */
	public RewardPointSummary(final Long customerId, final List<MonthlyPoints> monthlyPoints)
	{
		_customerId = customerId;
		_monthlyPoints = monthlyPoints;

		_totalPoints = monthlyPoints.stream().mapToLong(MonthlyPoints::getPoints).sum();
	}


	public Long getCustomerId()
	{
		return _customerId;
	}


	public List<MonthlyPoints> getMonthlyPoints()
	{
		return _monthlyPoints;
	}


	public long getTotalPoints()
	{
		return _totalPoints;
	}
}
