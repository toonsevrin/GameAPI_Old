package com.yoshigenius.lib.util;

import java.util.regex.Pattern;

/**
 * @author Nick Robson
 */
public class RegexUtil {

    public static final Pattern UPPERCASE_LETTERS = Pattern.compile("[A-Z]");

    public static final Pattern LOWERCASE_LETTERS = Pattern.compile("[a-z]");

    public static final Pattern NUMBERS = Pattern.compile("[0-9]");

}
