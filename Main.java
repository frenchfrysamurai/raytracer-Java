public class Main {

    private static Color rayColor(Ray r, Hittable world) {
        HitRecord rec = new HitRecord();
        if (world.hit(r, new Interval(0, Rtweekend.INFINITY), rec)) {
            Vec3 n = Vec3.add(rec.normal, new Color(1, 1, 1));
            return new Color(0.5 * n.x(), 0.5 * n.y(), 0.5 * n.z());
        }

        Vec3 unitDirection = Vec3.unitVector(r.direction());
        double a = 0.5 * (unitDirection.y() + 1.0);
        Vec3 blended = Vec3.add(
            Vec3.multiply(1.0 - a, new Color(1.0, 1.0, 1.0)),
            Vec3.multiply(a, new Color(0.5, 0.7, 1.0))
        );
        return new Color(blended.x(), blended.y(), blended.z());
    }

    public static void main(String[] args) {
        // Image
        final double aspectRatio = 16.0 / 9.0;
        int imageWidth = 400;

        int imageHeight = (int) (imageWidth / aspectRatio);
        imageHeight = (imageHeight < 1) ? 1 : imageHeight;

        // World
        HittableList world = new HittableList();
        world.add(new Sphere(new Point3(0, 0, -1), 0.5));
        world.add(new Sphere(new Point3(0, -100.5, -1), 100));

        // Camera
        double focalLength = 1.0;
        double viewportHeight = 2.0;
        double viewportWidth = viewportHeight * ((double) imageWidth / imageHeight);
        Point3 cameraCenter = new Point3(0, 0, 0);

        Vec3 viewportU = new Vec3(viewportWidth, 0, 0);
        Vec3 viewportV = new Vec3(0, -viewportHeight, 0);

        Vec3 pixelDeltaU = Vec3.divide(viewportU, imageWidth);
        Vec3 pixelDeltaV = Vec3.divide(viewportV, imageHeight);

        Vec3 viewportUpperLeft = Vec3.subtract(
            Vec3.subtract(
                Vec3.subtract(cameraCenter, new Vec3(0, 0, focalLength)),
                Vec3.divide(viewportU, 2)
            ),
            Vec3.divide(viewportV, 2)
        );
        Vec3 pixel00Loc = Vec3.add(
            viewportUpperLeft,
            Vec3.multiply(0.5, Vec3.add(pixelDeltaU, pixelDeltaV))
        );

        // Render
        System.out.println("P3");
        System.out.println(imageWidth + " " + imageHeight);
        System.out.println("255");

        for (int j = 0; j < imageHeight; j++) {
            System.err.print("\rScanlines remaining: " + (imageHeight - j) + " ");
            System.err.flush();
            for (int i = 0; i < imageWidth; i++) {
                Vec3 pixelCenter = Vec3.add(
                    Vec3.add(pixel00Loc, Vec3.multiply((double) i, pixelDeltaU)),
                    Vec3.multiply((double) j, pixelDeltaV)
                );
                Vec3 rayDirection = Vec3.subtract(pixelCenter, cameraCenter);
                Ray ray = new Ray(cameraCenter, rayDirection);

                Color pixelColor = rayColor(ray, world);
                Color.writeColor(System.out, pixelColor);
            }
        }

        System.err.print("\rDone.                 \n");
        System.err.flush();
    }
}
