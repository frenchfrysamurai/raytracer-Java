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

    public boolean nearZero() {
        double s = 1e-8;
        return Math.abs(e[0]) < s && Math.abs(e[1]) < s && Math.abs(e[2]) < s;
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

    public static Vec3 random() {
        return new Vec3(Rtweekend.randomDouble(), Rtweekend.randomDouble(), Rtweekend.randomDouble());
    }

    public static Vec3 random(double min, double max) {
        return new Vec3(
            Rtweekend.randomDouble(min, max),
            Rtweekend.randomDouble(min, max),
            Rtweekend.randomDouble(min, max)
        );
    }

    public static Vec3 randomUnitVector() {
        while (true) {
            Vec3 p = random(-1, 1);
            double lensq = p.lengthSquared();
            if (1e-160 < lensq && lensq <= 1) {
                return divide(p, Math.sqrt(lensq));
            }
        }
    }

    public static Vec3 randomOnHemisphere(Vec3 normal) {
        Vec3 onUnitSphere = randomUnitVector();
        if (dot(onUnitSphere, normal) > 0.0) {
            return onUnitSphere;
        }
        return onUnitSphere.negate();
    }

    public static Vec3 randomInUnitDisk() {
        while (true) {
            Vec3 p = new Vec3(Rtweekend.randomDouble(-1, 1), Rtweekend.randomDouble(-1, 1), 0);
            if (p.lengthSquared() < 1) {
                return p;
            }
        }
    }

    public static Vec3 reflect(Vec3 v, Vec3 n) {
        return subtract(v, multiply(2 * dot(v, n), n));
    }

    public static Vec3 refract(Vec3 uv, Vec3 n, double etaiOverEtat) {
        double cosTheta = Math.min(dot(uv.negate(), n), 1.0);
        Vec3 rOutPerp = multiply(etaiOverEtat, add(uv, multiply(cosTheta, n)));
        Vec3 rOutParallel = multiply(-Math.sqrt(Math.abs(1.0 - rOutPerp.lengthSquared())), n);
        return add(rOutPerp, rOutParallel);
    }
}
