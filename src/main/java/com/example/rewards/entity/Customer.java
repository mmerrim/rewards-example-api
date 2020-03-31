package com.example.rewards.entity;

import java.util.List;

/**
 * Represents a customer and their transactions.
 */
public class Customer
{
    /**
     * ID of the customer.
     */
    private Long _customerId;

    /**
     * Transactions for this customer.
     */
    private List<Transaction> _transactions;


    public Long getCustomerId()
    {
        return _customerId;
    }


    public void setCustomerId(final Long customerId)
    {
        _customerId = customerId;
    }

    public List<Transaction> getTransactions()
    {
        return _transactions;
    }


    public void setTransactions(final List<Transaction> transactions)
    {
        _transactions = transactions;
    }
}
