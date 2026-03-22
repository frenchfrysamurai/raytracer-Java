public class Main {
    public static void main(String[] args) {
	// Image size
	int imageWidth = 256;
	int imageHeight = 256;

	// Print out PPM header
	System.out.println("P3");
	System.out.println(imageWidth + " " + imageHeight);
	System.out.println("255");

	// Render image
	for (int j = 0; j < imageHeight; j++) {
	    System.err.print("\rScanlines remaining: " + (imageHeight - j) + " ");
	    System.err.flush();
	    for (int i = 0; i < imageWidth; i++) {
		double r = (double)i / (imageWidth - 1);
		double g = (double)j / (imageHeight - 1);
		double b = 0.0;

		int ir = (int)(255.999 * r);
		int ig = (int)(255.999 * g);
		int ib = (int)(255.999 * b);

		System.out.println(ir + " " + ig + " " + ib);
	    }
	}
	System.err.print("\rDone.                \n");
	System.err.flush();
    }
}
