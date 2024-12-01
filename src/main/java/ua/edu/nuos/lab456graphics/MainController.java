package ua.edu.nuos.lab456graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ua.edu.nuos.lab456graphics.model.Figure3D;
import ua.edu.nuos.lab456graphics.view.MyView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MainController {

    @FXML
    private CheckBox visibility;
    @FXML
    private Pane pane;

    @FXML
    private Canvas canvas;

    private Figure3D figure = null;
    private MyView view = new MyView();


    public void initialize() {

        figure = readFromFile(new File("table.txt"));

        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());

        canvas.widthProperty().addListener(e -> draw());
        canvas.heightProperty().addListener(e -> draw());
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        view.setCheckVisibility(visibility.isSelected());
        view.drawFigure(figure, gc);
    }

    private Figure3D readFromFile(File file) {
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            String[] s = reader.readLine().split(" ");
            int nPoints = Integer.parseInt(s[0]);
            int nFaces = Integer.parseInt(s[1]);
            double[] x = new double[nPoints];
            double[] y = new double[nPoints];
            double[] z = new double[nPoints];
            int[][] p = new int[nFaces][];
            for (int i = 0; i < x.length; i++) {
                s = reader.readLine().split(" ");
                x[i] = Double.parseDouble(s[0]);
                y[i] = Double.parseDouble(s[1]);
                z[i] = Double.parseDouble(s[2]);
            }
            for (int i = 0; i < p.length; i++) {
                s = reader.readLine().split(" ");
                p[i] = new int[s.length];
                for (int j = 0; j < p[i].length; j++) {
                    p[i][j] = Integer.parseInt(s[j]);
                }
            }
            return new Figure3D(x,y,z,p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveXminus() {
        view.moveFigure(-MyView.STEP, 0, 0);
        draw();
    }

    public void moveXplus() {
        view.moveFigure(MyView.STEP, 0, 0);
        draw();
    }
    public void moveYminus() {
        view.moveFigure(0, -MyView.STEP, 0);
        draw();
    }

    public void moveYplus() {
        view.moveFigure(0, MyView.STEP, 0);
        draw();
    }
    public void moveZminus() {
        view.moveFigure(0,0, -MyView.STEP);
        draw();
    }

    public void moveZplus() {
        view.moveFigure(0,0, MyView.STEP);
        draw();
    }

    public void rotateX() {
        view.rotateFigureX(1);
        draw();
    }
    public void rotateY() {
        view.rotateFigureY(1);
        draw();
    }
    public void rotateXminus() {
        view.rotateFigureX(-1);
        draw();
    }
    public void rotateYminus() {
        view.rotateFigureY(-1);
        draw();
    }
    public void rotateZ() {
        view.rotateFigureZ(1);
        draw();
    }

    public void rotateZminus() {
        view.rotateFigureZ(-1);
        draw();
    }

    public void repaint() {
        draw();
    }
}