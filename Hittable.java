public interface Hittable {
    boolean hit(Ray r, Interval rayT, HitRecord rec);
}
