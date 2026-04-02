public class Camera {

    public double aspectRatio = 1.0;
    public int imageWidth = 100;
    public int samplesPerPixel = 10;
    public int maxDepth = 10;

    public double vfov = 90;
    public Point3 lookfrom = new Point3(0, 0, 0);
    public Point3 lookat = new Point3(0, 0, -1);
    public Vec3 vup = new Vec3(0, 1, 0);

    public double defocusAngle = 0;
    /** Distance from camera to plane of focus; if <= 0, uses distance from lookfrom to lookat. */
    public double focusDist = 0;

    private int imageHeight;
    private double pixelSamplesScale;
    private Point3 center;
    private Point3 pixel00Loc;
    private Vec3 pixelDeltaU;
    private Vec3 pixelDeltaV;
    private Vec3 u;
    private Vec3 v;
    private Vec3 w;
    private Vec3 defocusDiskU;
    private Vec3 defocusDiskV;

    private final ScatterRecord scatterScratch = new ScatterRecord();

    public void render(Hittable world) {
        initialize();

        System.out.println("P3");
        System.out.println(imageWidth + " " + imageHeight);
        System.out.println("255");

        for (int j = 0; j < imageHeight; j++) {
            System.err.print("\rScanlines remaining: " + (imageHeight - j) + " ");
            System.err.flush();
            for (int i = 0; i < imageWidth; i++) {
                Vec3 pixelColor = new Vec3(0, 0, 0);
                for (int sample = 0; sample < samplesPerPixel; sample++) {
                    Ray r = getRay(i, j);
                    Vec3 sampleColor = rayColor(r, maxDepth, world);
                    pixelColor = Vec3.add(pixelColor, sampleColor);
                }
                Color.writeColor(System.out, Vec3.multiply(pixelSamplesScale, pixelColor));
            }
        }

        System.err.print("\rDone.                 \n");
        System.err.flush();
    }

    private void initialize() {
        imageHeight = (int) (imageWidth / aspectRatio);
        imageHeight = (imageHeight < 1) ? 1 : imageHeight;

        pixelSamplesScale = 1.0 / samplesPerPixel;

        center = lookfrom;

        double theta = Rtweekend.degreesToRadians(vfov);
        double h = Math.tan(theta / 2);
        double fd = focusDist > 0 ? focusDist : Vec3.subtract(lookfrom, lookat).length();

        double viewportHeight = 2 * h * fd;
        double viewportWidth = viewportHeight * ((double) imageWidth / imageHeight);

        w = Vec3.unitVector(Vec3.subtract(lookfrom, lookat));
        u = Vec3.unitVector(Vec3.cross(vup, w));
        v = Vec3.cross(w, u);

        Vec3 viewportU = Vec3.multiply(viewportWidth, u);
        Vec3 viewportV = Vec3.multiply(viewportHeight, v.negate());

        pixelDeltaU = Vec3.divide(viewportU, imageWidth);
        pixelDeltaV = Vec3.divide(viewportV, imageHeight);

        Vec3 viewportUpperLeft = Vec3.subtract(
            Vec3.subtract(Vec3.subtract(center, Vec3.multiply(fd, w)), Vec3.divide(viewportU, 2)),
            Vec3.divide(viewportV, 2)
        );
        Vec3 upperLeftPixel = Vec3.add(
            viewportUpperLeft,
            Vec3.multiply(0.5, Vec3.add(pixelDeltaU, pixelDeltaV))
        );
        pixel00Loc = new Point3(upperLeftPixel.x(), upperLeftPixel.y(), upperLeftPixel.z());

        double defocusRadius = fd * Math.tan(Rtweekend.degreesToRadians(defocusAngle / 2));
        defocusDiskU = Vec3.multiply(u, defocusRadius);
        defocusDiskV = Vec3.multiply(v, defocusRadius);
    }

    private Ray getRay(int i, int j) {
        Vec3 offset = sampleSquare();
        Vec3 pixelSample = Vec3.add(
            Vec3.add(
                pixel00Loc,
                Vec3.multiply((double) i + offset.x(), pixelDeltaU)
            ),
            Vec3.multiply((double) j + offset.y(), pixelDeltaV)
        );

        Point3 rayOrigin = defocusAngle <= 0 ? center : defocusDiskSample();
        Vec3 rayDirection = Vec3.subtract(pixelSample, rayOrigin);
        return new Ray(rayOrigin, rayDirection);
    }

    private Point3 defocusDiskSample() {
        Vec3 p = Vec3.randomInUnitDisk();
        Vec3 offset = Vec3.add(Vec3.multiply(p.x(), defocusDiskU), Vec3.multiply(p.y(), defocusDiskV));
        Vec3 q = Vec3.add(center, offset);
        return new Point3(q.x(), q.y(), q.z());
    }

    private Vec3 sampleSquare() {
        return new Vec3(Rtweekend.randomDouble() - 0.5, Rtweekend.randomDouble() - 0.5, 0);
    }

    private Color rayColor(Ray r, int depth, Hittable world) {
        if (depth <= 0) {
            return new Color(0, 0, 0);
        }

        HitRecord rec = new HitRecord();
        if (world.hit(r, new Interval(0.001, Rtweekend.INFINITY), rec)) {
            if (rec.mat != null && rec.mat.scatter(r, rec, scatterScratch)) {
                Vec3 attenuated = Vec3.multiply(scatterScratch.attenuation, rayColor(scatterScratch.scattered, depth - 1, world));
                return new Color(attenuated.x(), attenuated.y(), attenuated.z());
            }
            return new Color(0, 0, 0);
        }

        Vec3 unitDirection = Vec3.unitVector(r.direction());
        double a = 0.5 * (unitDirection.y() + 1.0);
        Vec3 blended = Vec3.add(
            Vec3.multiply(1.0 - a, new Color(1.0, 1.0, 1.0)),
            Vec3.multiply(a, new Color(0.5, 0.7, 1.0))
        );
        return new Color(blended.x(), blended.y(), blended.z());
    }
}
