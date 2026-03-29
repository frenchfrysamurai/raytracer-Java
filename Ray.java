public class Ray {
    private Point3 orig;
    private Vec3 dir;

    public Ray() {
        this.orig = new Point3();
        this.dir = new Vec3();
    }

    public Ray(Point3 origin, Vec3 direction) {
        this.orig = new Point3(origin.x(), origin.y(), origin.z());
        this.dir = new Vec3(direction.x(), direction.y(), direction.z());
    }

    public Point3 origin() {
        return orig;
    }

    public Vec3 direction() {
        return dir;
    }

    public Point3 at(double t) {
        Vec3 scaled = Vec3.multiply(t, dir);
        Vec3 p = Vec3.add(orig, scaled);
        return new Point3(p.x(), p.y(), p.z());
    }
}
