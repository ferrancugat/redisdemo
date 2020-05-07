package org.demo.nosql.redisdemo.utils;

import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.DataValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumericalUtils {

    private static Logger logger = LoggerFactory.getLogger(NumericalUtils.class);

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
                logger.error("The string: " + value.getValue() + " is not a valid number", e);
            }
        }
        return isNumeric;
    }
}
