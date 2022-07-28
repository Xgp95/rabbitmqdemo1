package rabbitmqtest.util;

/**
 * @author: Xugp
 * @date: 2022/7/23 15:11
 * @description:
 */
public class SleepUtils {
    public static void sleep(int i) {
        try {
            Thread.sleep(i * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
