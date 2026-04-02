public final class Lambertian implements Material {
    private final Color albedo;

    public Lambertian(Color albedo) {
        this.albedo = albedo;
    }

    @Override
    public boolean scatter(Ray rIn, HitRecord rec, ScatterRecord out) {
        Vec3 scatterDirection = Vec3.add(rec.normal, Vec3.randomUnitVector());
        if (scatterDirection.nearZero()) {
            scatterDirection = rec.normal;
        }
        out.scattered = new Ray(rec.p, scatterDirection);
        out.attenuation = albedo;
        return true;
    }
}
