public class Main {

    public static void main(String[] args) {
        HittableList world = buildScene();

        Camera cam = new Camera();
        cam.aspectRatio = 16.0 / 9.0;
        cam.imageWidth = 400;
        cam.samplesPerPixel = 100;
        cam.maxDepth = 50;

        cam.vfov = 52;
        cam.lookfrom = new Point3(2.2, 0.85, 1.3);
        cam.lookat = new Point3(-0.15, 0.38, -3.15);
        cam.vup = new Vec3(0, 1, 0);

        cam.defocusAngle = 0;
        cam.focusDist = 0;

        cam.render(world);
    }

    /**
     * New layout: dark ground, warm “moon” backdrop, coral / rose metal / glass trio up front,
     * plus tiny glass and silver accents — all new positions and palette vs the book demo.
     */
    private static HittableList buildScene() {
        HittableList world = new HittableList();

        Material ground = new Lambertian(new Color(0.12, 0.14, 0.18));
        Material moon = new Lambertian(new Color(0.92, 0.88, 0.78));
        Material coral = new Lambertian(new Color(0.95, 0.42, 0.32));
        Material roseMetal = new Metal(new Color(0.88, 0.32, 0.38), 0.18);
        Material glass = new Dielectric(1.5);
        Material chrome = new Metal(new Color(0.92, 0.91, 0.88), 0.04);

        world.add(new Sphere(new Point3(0, -100, -2), 100, ground));

        world.add(new Sphere(new Point3(0, 1.35, -5.5), 1.05, moon));

        world.add(new Sphere(new Point3(-1.45, 0.42, -3.05), 0.42, roseMetal));
        world.add(new Sphere(new Point3(0.0, 0.36, -3.25), 0.46, coral));
        world.add(new Sphere(new Point3(1.35, 0.4, -2.95), 0.38, glass));

        world.add(new Sphere(new Point3(-0.55, 0.14, -2.75), 0.13, glass));
        world.add(new Sphere(new Point3(0.95, 0.52, -3.45), 0.14, chrome));

        return world;
    }
}
