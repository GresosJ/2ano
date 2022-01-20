package Domain.Model;

import static java.lang.System.nanoTime;

public class Crono {

    private static long begin = 0L;
    private static long end = 0L;
    private static long elapsed = 0L;

    public double getTime() {
        return elapsed;
    }

    public static void start() {
        end = 0L;
        elapsed = 0L;
        begin = nanoTime();
    }

    public static double stop() {
        end = nanoTime();
        elapsed = end - begin;
        return elapsed / 1.0E09;
    }

    public static String getTimeAsString() {
        return "" + elapsed;
    }

    public static void printElapsedTime(){
        System.out.print("Decorridos: " + stop() + " segundos");
    }
}
