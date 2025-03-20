package cl.tenpo.learning.reactive.utils;

public class CourseUtils {

    public static void sleepSeconds(final long seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
