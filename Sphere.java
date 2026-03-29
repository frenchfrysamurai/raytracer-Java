public class Sphere implements Hittable {
    private final Point3 center;
    private final double radius;

    public Sphere(Point3 center, double radius) {
        this.center = center;
        this.radius = Math.max(0, radius);
    }

    @Override
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        Vec3 oc = Vec3.subtract(center, r.origin());
        double a = r.direction().lengthSquared();
        double h = Vec3.dot(r.direction(), oc);
        double c = oc.lengthSquared() - radius * radius;
        double discriminant = h * h - a * c;
        if (discriminant < 0) {
            return false;
        }

        double sqrtd = Math.sqrt(discriminant);
        double root = (h - sqrtd) / a;
        if (!rayT.surrounds(root)) {
            root = (h + sqrtd) / a;
            if (!rayT.surrounds(root)) {
                return false;
            }
        }

        rec.t = root;
        rec.p = r.at(rec.t);
        Vec3 outwardNormal = Vec3.divide(Vec3.subtract(rec.p, center), radius);
        rec.setFaceNormal(r, outwardNormal);

        return true;
    }
}
