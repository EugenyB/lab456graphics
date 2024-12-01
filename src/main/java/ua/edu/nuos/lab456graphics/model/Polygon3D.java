package ua.edu.nuos.lab456graphics.model;

import java.util.Arrays;
import java.util.List;

public class Polygon3D {
    private final List<Point3D> points;

    public Polygon3D(List<Point3D> points) {
        this.points = points;
    }

    public Polygon3D(Point3D... points) {
        this(Arrays.asList(points));
    }

    public List<Point3D> getPoints() {
        return points;
    }


    public double distanceFromO(double[][] matrix) {
        return -points.stream()
                .map(p->p.transform(matrix))
                .mapToDouble(p -> p.x() * p.x() + p.y() * p.y() + p.z() * p.z()).min().orElse(0);
    }
}
