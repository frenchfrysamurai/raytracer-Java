import java.io.PrintStream;

// color is just an alias for vec3, but useful for clarity in the code.
public final class Color extends Vec3 {
    public Color() {
        super();
    }

    public Color(double r, double g, double b) {
        super(r, g, b);
    }

    public static Color random() {
        return new Color(Rtweekend.randomDouble(), Rtweekend.randomDouble(), Rtweekend.randomDouble());
    }

    public static Color random(double min, double max) {
        return new Color(
            Rtweekend.randomDouble(min, max),
            Rtweekend.randomDouble(min, max),
            Rtweekend.randomDouble(min, max)
        );
    }

    private static final Interval INTENSITY = new Interval(0.000, 0.999);

    private static double linearToGamma(double linearComponent) {
        if (linearComponent > 0) {
            return Math.sqrt(linearComponent);
        }
        return 0;
    }

    public static void writeColor(PrintStream out, Vec3 pixelColor) {
        double r = linearToGamma(pixelColor.x());
        double g = linearToGamma(pixelColor.y());
        double b = linearToGamma(pixelColor.z());

        int rByte = (int) (256 * INTENSITY.clamp(r));
        int gByte = (int) (256 * INTENSITY.clamp(g));
        int bByte = (int) (256 * INTENSITY.clamp(b));

        out.println(rByte + " " + gByte + " " + bByte);
    }
}
