package org.demo.nosql.redisdemo.utils;

import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.DataValueType;

public class NumericalUtils {

    private NumericalUtils() {
    }

    public static boolean isValidLong(DataValue value) {
        boolean isNumeric = false;
        if (DataValueType.STRING.equals(value.getType())) {
            try {
                Long number = Long.parseLong(value.getValue());
                if (number >= 0) {
                    isNumeric = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("The string: " + value.getValue() + " is not a valid number");
            }
        }
        return isNumeric;
    }
}
