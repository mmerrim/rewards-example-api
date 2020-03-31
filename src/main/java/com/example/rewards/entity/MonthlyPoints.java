package com.example.rewards.entity;

import java.time.LocalDate;

/**
 * Represents a number of points awarded to a customer in one month.
 */
public class MonthlyPoints
{
	/**
	 * Points rewarded for this month.
	 */
	private final long _points;

	/**
	 * The first day of the month.
	 */
	private final LocalDate _monthBeginDate;


	/**
	 * @param points {@see #_points}.
	 * @param monthBeginDate {@see #_monthBeginDate}.
	 */
	public MonthlyPoints(final long points, final LocalDate monthBeginDate)
	{
		_points = points;
		_monthBeginDate = monthBeginDate;
	}


	public LocalDate getMonthBeginDate()
	{
		return _monthBeginDate;
	}


	public long getPoints()
	{
		return _points;
	}
}
