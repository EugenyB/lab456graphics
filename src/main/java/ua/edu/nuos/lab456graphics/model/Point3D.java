package ua.edu.nuos.lab456graphics.model;

public record Point3D(double x, double y, double z) {
    public Point3D transform(double[][] m) {
        double x = m[0][0] * x() + m[0][1] * y() + m[0][2] * z() + m[0][3];
        double y = m[1][0] * x() + m[1][1] * y() + m[1][2] * z() + m[1][3];
        double z = m[2][0] * x() + m[2][1] * y() + m[2][2] * z() + m[2][3];
        return new Point3D(x, y, z);
    }
}
