public class Interval {
    public double min;
    public double max;

    public Interval() {
        this.min = Rtweekend.INFINITY;
        this.max = -Rtweekend.INFINITY;
    }

    public Interval(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double size() {
        return max - min;
    }

    public boolean contains(double x) {
        return min <= x && x <= max;
    }

    public boolean surrounds(double x) {
        return min < x && x < max;
    }

    public static final Interval EMPTY = new Interval(Rtweekend.INFINITY, -Rtweekend.INFINITY);
    public static final Interval UNIVERSE = new Interval(-Rtweekend.INFINITY, Rtweekend.INFINITY);
}
