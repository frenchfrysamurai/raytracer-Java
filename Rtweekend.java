import java.util.concurrent.ThreadLocalRandom;

public final class Rtweekend {
    public static final double INFINITY = Double.POSITIVE_INFINITY;
    public static final double PI = 3.1415926535897932385;

    public static double degreesToRadians(double degrees) {
        return degrees * PI / 180.0;
    }

    /** Returns a random real in [0,1). */
    public static double randomDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    /** Returns a random real in [min,max). */
    public static double randomDouble(double min, double max) {
        return min + (max - min) * randomDouble();
    }

    private Rtweekend() {}
}
