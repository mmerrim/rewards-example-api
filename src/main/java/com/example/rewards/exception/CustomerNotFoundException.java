package com.example.rewards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom response thrown when a request is made for a customer that does not exist.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer not found.")
public class CustomerNotFoundException extends RuntimeException
{
}
