package ua.edu.nuos.lab456graphics.model;

import java.util.ArrayList;
import java.util.List;

public class Figure3D {
    private final List<Point3D> points = new ArrayList<>();
    private final List<List<Integer>> polygons = new ArrayList<>();

    public Figure3D(double[] x, double[] y, double[] z, int[][] p) {
        for (int i = 0; i < x.length; i++) {
            points.add(new Point3D(x[i], y[i], z[i]));
        }

        for (int i = 0; i < p.length; i++) {
            var list = new ArrayList<Integer>();
            for (int j = 0; j < p[i].length; j++) {
                list.add(p[i][j]);
            }
            polygons.add(list);
        }
    }

    public int facesCount() {
        return polygons.size();
    }

    public Polygon3D getFace(int index) {
        if (index < 0 || index >= polygons.size()) {
            throw new IndexOutOfBoundsException("face num must be in [0.."+(polygons.size()-1)+"] but it is:" + index);
        }
        Point3D[] p3d = new Point3D[polygons.get(index).size()];
        for (int i = 0; i < polygons.get(index).size(); i++) {
            p3d[i] = points.get(polygons.get(index).get(i));
        }
        return new Polygon3D(p3d);
    }
}
