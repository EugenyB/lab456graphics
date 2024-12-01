package ua.edu.nuos.lab456graphics.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ua.edu.nuos.lab456graphics.model.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MyView {
    double cx = 300;
    double cy = 300;
    double cz = 700;

    double f = 400;

    public static double STEP = 10;
    double FACTOR = 1.1;
    double ALPHA = 10;

    boolean checkVisibility = false;

    double[][] matrix = {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};

    private double xs(Point3D p) {
        return p.x() * f / p.z();
    }

    private double ys(Point3D p) {
        return p.y() * f / p.z();
    }

    public void setCheckVisibility(boolean value) {
        this.checkVisibility = value;
    }

    public void drawFigure(Figure3D f, GraphicsContext gc) {
        Polygon3D[] faces = new Polygon3D[f.facesCount()];
        for (int i = 0; i < f.facesCount(); i++) {
            faces[i] = f.getFace(i);
        }

        Arrays.sort(faces, Comparator.comparingDouble(p -> p.distanceFromO(matrix)));

        for (Polygon3D face : faces) {
            if (!checkVisibility || visible(face)) {
                drawPolygon(face, gc, checkVisibility);
            }
        }
    }

    private void drawPolygon(Polygon3D face, GraphicsContext gc, boolean checkVisibility) {
        Polygon2D polygon2D = makeProjection(face);
        if (checkVisibility) {
            gc.setFill(Color.GRAY);
            gc.fillPolygon(polygon2D.getXs(), polygon2D.getYs(), polygon2D.size());
        }
        gc.setStroke(Color.WHITE);
        gc.strokePolygon(polygon2D.getXs(), polygon2D.getYs(), polygon2D.size());
    }

    private Polygon2D makeProjection(Polygon3D polygon3D) {
        return new Polygon2D(polygon3D.getPoints().stream().map(p->p.transform(matrix)).map(this::projection).toList());
    }

    private Point2D projection(Point3D p) {
        return new Point2D(xs(p), ys(p));
    }

    public void moveFigure(double dx, double dy, double dz) {
        double[][] t = {
                {1, 0, 0, dx},
                {0, 1, 0, dy},
                {0, 0, 1, dz},
                {0, 0, 0, 1}
        };
        matrix = multiply(t, matrix);
    }

    private double[][] multiply(double[][] t, double[][] matrix) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result[i][j] += t[i][k] * matrix[k][j];
                }
            }
        }
        return result;
    }

    public void rotateFigureX(int sign) {
        var angle = Math.toRadians(Math.signum(sign)*ALPHA);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double[][] t = {
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0, 1}
        };
        moveFigure(-cx, -cy, -cz);
        matrix = multiply(t, matrix);
        moveFigure(cx, cy, cz);
    }

    public void rotateFigureY(int sign) {
        var angle = Math.toRadians(Math.signum(sign)*ALPHA);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double[][] t = {
                {cos, 0, sin, 0},
                {0, 1, 0, 0},
                {-sin, 0, cos, 0},
                {0, 0, 0, 1}
        };
        moveFigure(-cx, -cy, -cz);
        matrix = multiply(t, matrix);
        moveFigure(cx, cy, cz);
    }

    public void rotateFigureZ(int sign) {
        var angle = Math.toRadians(Math.signum(sign)*ALPHA);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double[][] t = {
                {cos, -sin, 0, 0},
                {sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        moveFigure(-cx, -cy, -cz);
        matrix = multiply(t, matrix);
        moveFigure(cx, cy, cz);
    }

    boolean visible(Polygon3D face) {
        List<Point3D> points = face.getPoints().stream().map(p -> p.transform(matrix)).toList();
        // Вектор к наблюдателю
        double xv = -points.get(0).x();
        double yv = -points.get(0).y();
        double zv = -points.get(0).z();

        double xa = points.get(1).x() + xv;
        double ya = points.get(1).y() + yv;
        double za = points.get(1).z() + zv;

        double xb = points.get(2).x() + xv;
        double yb = points.get(2).y() + yv;
        double zb = points.get(2).z() + zv;

        // Вектор нормали
        double xn = ya * zb - yb * za;
        double yn = za * xb - zb * xa;
        double zn = xa * yb - xb * ya;

        return xn*xv + yn*yv + zn*zv > 0;
    }
}
