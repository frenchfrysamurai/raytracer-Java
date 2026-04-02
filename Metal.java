public final class Metal implements Material {
    private final Color albedo;
    private final double fuzz;

    public Metal(Color albedo) {
        this(albedo, 0);
    }

    public Metal(Color albedo, double fuzz) {
        this.albedo = albedo;
        this.fuzz = fuzz < 1 ? fuzz : 1;
    }

    @Override
    public boolean scatter(Ray rIn, HitRecord rec, ScatterRecord out) {
        Vec3 reflected = Vec3.reflect(Vec3.unitVector(rIn.direction()), rec.normal);
        reflected = Vec3.add(
            Vec3.unitVector(reflected),
            Vec3.multiply(fuzz, Vec3.randomUnitVector())
        );
        out.scattered = new Ray(rec.p, reflected);
        out.attenuation = albedo;
        return Vec3.dot(out.scattered.direction(), rec.normal) > 0;
    }
}
