public class HitRecord {
    public Point3 p;
    public Vec3 normal;
    public double t;
    public boolean frontFace;

    public void setFaceNormal(Ray r, Vec3 outwardNormal) {
        frontFace = Vec3.dot(r.direction(), outwardNormal) < 0;
        normal = frontFace ? outwardNormal : outwardNormal.negate();
    }

    public void copyFrom(HitRecord src) {
        this.p = src.p;
        this.normal = src.normal;
        this.t = src.t;
        this.frontFace = src.frontFace;
    }
}
