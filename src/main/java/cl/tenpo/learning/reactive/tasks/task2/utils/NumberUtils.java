package cl.tenpo.learning.reactive.tasks.task2.utils;

import java.math.BigDecimal;

public final class NumberUtils {
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private NumberUtils() {
    }

    public static BigDecimal getPercentage(final BigDecimal number, final BigDecimal percentage) {
        return number.multiply(percentage).divide(HUNDRED);
    }

    public static BigDecimal addPercentage(final BigDecimal number, final BigDecimal percentage) {
        return number.add(getPercentage(number, percentage));
    }
}
