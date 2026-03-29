public class Vec3 {
    public final double[] e = new double[3];

    public Vec3() {
        e[0] = 0;
        e[1] = 0;
        e[2] = 0;
    }

    public Vec3(double e0, double e1, double e2) {
        e[0] = e0;
        e[1] = e1;
        e[2] = e2;
    }

    public double x() {
        return e[0];
    }

    public double y() {
        return e[1];
    }

    public double z() {
        return e[2];
    }

    public Vec3 negate() {
        return new Vec3(-e[0], -e[1], -e[2]);
    }

    public double get(int i) {
        return e[i];
    }

    public void set(int i, double v) {
        e[i] = v;
    }

    public Vec3 addAssign(Vec3 v) {
        e[0] += v.e[0];
        e[1] += v.e[1];
        e[2] += v.e[2];
        return this;
    }

    public Vec3 multiplyAssign(double t) {
        e[0] *= t;
        e[1] *= t;
        e[2] *= t;
        return this;
    }

    public Vec3 divideAssign(double t) {
        return multiplyAssign(1.0 / t);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return e[0] * e[0] + e[1] * e[1] + e[2] * e[2];
    }

    @Override
    public String toString() {
        return e[0] + " " + e[1] + " " + e[2];
    }

    public static Vec3 add(Vec3 u, Vec3 v) {
        return new Vec3(u.e[0] + v.e[0], u.e[1] + v.e[1], u.e[2] + v.e[2]);
    }

    public static Vec3 subtract(Vec3 u, Vec3 v) {
        return new Vec3(u.e[0] - v.e[0], u.e[1] - v.e[1], u.e[2] - v.e[2]);
    }

    public static Vec3 multiply(Vec3 u, Vec3 v) {
        return new Vec3(u.e[0] * v.e[0], u.e[1] * v.e[1], u.e[2] * v.e[2]);
    }

    public static Vec3 multiply(double t, Vec3 v) {
        return new Vec3(t * v.e[0], t * v.e[1], t * v.e[2]);
    }

    public static Vec3 multiply(Vec3 v, double t) {
        return multiply(t, v);
    }

    public static Vec3 divide(Vec3 v, double t) {
        return multiply(1.0 / t, v);
    }

    public static double dot(Vec3 u, Vec3 v) {
        return u.e[0] * v.e[0] + u.e[1] * v.e[1] + u.e[2] * v.e[2];
    }

    public static Vec3 cross(Vec3 u, Vec3 v) {
        return new Vec3(
            u.e[1] * v.e[2] - u.e[2] * v.e[1],
            u.e[2] * v.e[0] - u.e[0] * v.e[2],
            u.e[0] * v.e[1] - u.e[1] * v.e[0]
        );
    }

    public static Vec3 unitVector(Vec3 v) {
        return divide(v, v.length());
    }
}
