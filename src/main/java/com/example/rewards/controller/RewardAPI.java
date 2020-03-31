package com.example.rewards.controller;

import com.example.rewards.entity.RewardPointSummary;
import com.example.rewards.entity.TransactionSummary;
import com.example.rewards.service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

/**
 * This is the REST API for the rewards functionality.
 */
@RestController
@RequestMapping("/application")
public class RewardAPI
{
	/**
	 * Service for handling reward requests.
	 */
	private final RewardService _rewardService;


	/**
	 * Constructs the API.
	 *
	 * @param rewardService {@see #_rewardService}.
	 */
	public RewardAPI(final RewardService rewardService)
	{
		_rewardService = rewardService;
	}


	/**
	 * Stores customer transactions so they can be queried for the associated point amounts.
	 *
	 * @param transactionSummary A summary of transactions for all customers.
	 */
	@Operation(summary = "Store Transactions", description = "Saves customer transaction data.",
			tags = { "rewards" })
	@PutMapping(value = "/rewards", consumes = "application/json", produces = "application/json")
	public void storeTransactions(@RequestBody TransactionSummary transactionSummary)
	{
		_rewardService.storeTransactions(transactionSummary.getCustomers());
	}


	/**
	 * Looks up the customer point amounts for a specific customer.
	 *
	 * @param customerId The customer ID.
	 *
	 * @return Reward points by month and total.
	 */
	@Operation(summary = "Calculate Reward Points", description = "Calculates monthly and total reward point amounts.",
			tags = { "rewards" })
	@GetMapping(value = "/rewards/{customerId}", produces = "application/json")
	public RewardPointSummary calculateRewardPointSummary(@PathVariable("customerId") final Long customerId)
	{
		return _rewardService.calculateRewardSummary(customerId);
	}
}
