package com.utils;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *<p>
 * Validating various type of input such as name,email addresses and dates.
 * </p>
 */
public class EmployeeValidator {
    private static final Pattern EmailPattern = Pattern.compile("\\b[A-za-z0-9._%-]"
                                            +"+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");
    private static final Pattern NamePattern = Pattern.compile("^^[\\p{L} .'-]+$");
    
    /**
     * Validates if the given name is valid.
     *
     * @param name - The name to validate
     * @return true if the name is valid, false otherwise
     */
    public static boolean isValidName(String Name) {
        if(Name == null) {
            return false;
        }

        Matcher matcher=NamePattern.matcher(Name);
        return matcher.matches();
    }

    /**
     * Validates if the given emailId is valid.
     *
     * @param emailId - The emailId to Validate
     * @return true if the emailid is valid, false otherwise
     */
    public static boolean isValidEmail(String emailId) {
        
        if(emailId.isEmpty()) {
           return false;
         }
         Matcher matcher = EmailPattern.matcher(emailId);
         return matcher.matches();
    }

    /**
     * Validates if the given date is not a future date.
     *
     * @param givenDate - The date to check
     * @return true if the date is not in future, false otherwise
     */
    public static boolean isValidFutureDate(LocalDate givenDate){
         if (givenDate == null) {
            return false;
         }
         return !givenDate.isAfter(LocalDate.now());
    }
}