import java.io.PrintStream;

// color is just an alias for vec3, but useful for clarity in the code.
public final class Color extends Vec3 {
    public Color() {
        super();
    }

    public Color(double r, double g, double b) {
        super(r, g, b);
    }

    public static void writeColor(PrintStream out, Vec3 pixelColor) {
        double r = pixelColor.x();
        double g = pixelColor.y();
        double b = pixelColor.z();

        int rByte = (int) (255.999 * r);
        int gByte = (int) (255.999 * g);
        int bByte = (int) (255.999 * b);

        out.println(rByte + " " + gByte + " " + bByte);
    }
}
