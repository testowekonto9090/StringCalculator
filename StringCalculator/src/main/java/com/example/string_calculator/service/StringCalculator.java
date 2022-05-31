package com.example.string_calculator.service;

import com.example.string_calculator.exception.NegativeNumException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalculator {
    private static final int DEFAULT_RETURN_VALUE = 0;
    private static final int MAX_VALUE = 1000;

    private static final String[] metacharacters = {"+", "*", "^", "{", "}"};

    private static final String CUSTOM_DELIMETER_START = "//[";
    private static final String CUSTOM_DELIMETER_END = "]\n";
    private static final String CUSTOM_DELIMETER_SEPARATOR_TEXT = "][";
    private static final String CUSTOM_DELIMETER_SEPARATOR_SPLITTER = "\\]\\[";

    private List<String> delimeters = new ArrayList<>(List.of(",", "\n"));

    public int add(String numbers) {
        if (numbers.isEmpty()) return DEFAULT_RETURN_VALUE;

        List<Integer> nums = prepareNums(numbers);
        nums = handleNegativeNums(nums);

        return nums.isEmpty() ? DEFAULT_RETURN_VALUE : nums.stream().mapToInt(x -> x.intValue()).sum();
    }

    private List<Integer> handleNegativeNums(List<Integer> nums) throws NegativeNumException {
        List<Integer> negativesNums = nums.stream().filter(x -> x.intValue() < 0).collect(Collectors.toList());
        if (negativesNums.isEmpty())
            return nums.stream().filter(x -> x.intValue() >= 0).collect(Collectors.toList());

        throw new NegativeNumException("Negatives not allowed: " + negativesNums.stream().map(x -> x.toString()).collect(Collectors.joining(";")));
    }

    private List<Integer> prepareNums(String numbers) {
        if (numbers.startsWith(CUSTOM_DELIMETER_START))
            numbers = handleCustomDelimeter(numbers);

        String[] numArr = numbers.split(delimeters.stream().collect(Collectors.joining("|")));

        try {
            return Stream.of(numArr).map(Integer::valueOf).filter(x -> x <= MAX_VALUE).collect(Collectors.toList());
        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
            return Collections.emptyList();
        }
    }

    private String handleCustomDelimeter(String numbers) {
        int endIndex = numbers.indexOf(CUSTOM_DELIMETER_END);
        String customDelimeter = numbers.substring(CUSTOM_DELIMETER_START.length(), endIndex);
        customDelimeter = resolveMetacharacters(customDelimeter);

        addDelimeters(customDelimeter);

        return numbers.substring(endIndex + CUSTOM_DELIMETER_END.length());
    }

    private void addDelimeters(String customDelimeter) {
        if (!customDelimeter.contains(CUSTOM_DELIMETER_SEPARATOR_TEXT))
            delimeters.add(customDelimeter);
        else
            delimeters.addAll(Stream.of(customDelimeter.split(CUSTOM_DELIMETER_SEPARATOR_SPLITTER)).collect(Collectors.toList()));
    }

    private String resolveMetacharacters(String customDelimeter) {
        for (String character : metacharacters) {
            customDelimeter = customDelimeter.replace(character, "\\" + character);
        }

        return customDelimeter;
    }

}
