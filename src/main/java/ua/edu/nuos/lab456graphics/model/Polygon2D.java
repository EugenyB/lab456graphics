package ua.edu.nuos.lab456graphics.model;

import java.util.List;

public record Polygon2D(List<Point2D> points) {

    public double[] getXs() {
        double[] xs = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xs[i] = points.get(i).x();
        }
        return xs;
    }

    public double[] getYs() {
        double[] ys = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            ys[i] = points.get(i).y();
        }
        return ys;
    }

    public int size() {
        return points.size();
    }
}
