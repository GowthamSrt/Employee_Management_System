package com.exception;

/**
 * <p>
 * Custom exception class to handle database-related errors.
 * This class extends the Exception class and provides a constructor
 * to pass error messages.
 * </p>
 */

public class DatabaseException extends Exception {
    /**
     * Constructs a new DatabaseException with the specified detail message.
     *
     * @param error the detail message, saved for later retrieval by the
     * getMessage() method.
     */
    public DatabaseException (String error) {
        super(error);
    }
}