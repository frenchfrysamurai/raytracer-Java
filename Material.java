public interface Material {
    boolean scatter(Ray rIn, HitRecord rec, ScatterRecord out);
}
