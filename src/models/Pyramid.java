package models;

import math.Vector3;
import third.IModel;
import third.MyPolygon;
import third.PolyLine3D;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

public class Pyramid implements IModel {
    private float height = 400f;
    private float radius = 100f;
    private int approximate = 10;
    private final Vector3 center;

    public Pyramid(Vector3 center) {
        this.center = center;
    }

    public Pyramid(float height, float radius, int approximate, Vector3 center) {
        this.height = height;
        this.radius = radius;
        this.approximate = approximate;
        this.center = center;
    }

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> lines = new LinkedList<>();
        List<Vector3> points = new LinkedList<>();
        List<Vector3> edges = new LinkedList<>();

        float step = (float) (2 * Math.PI / approximate);

        float x, y, z;

        for (float alpha = 0; alpha <= 2 * Math.PI; alpha += step) {
            x = (float) (radius * cos(alpha) + center.getX());
            y = (float) (radius * sin(alpha) + center.getY());
            z = 0 + center.getZ();
            points.add(new Vector3(x, y, z));
            edges.add(new Vector3(x, y, z));
            edges.add(new Vector3(0, 0, height + center.getZ()));
            lines.add(new PolyLine3D(edges, false));
            edges.clear();
        }
        lines.add(new PolyLine3D(points, true));

        return lines;
    }

    @Override
    public List<MyPolygon> getPolygons() {
        List<MyPolygon> polygons = new LinkedList<>();
        MyPolygon polygon;

        float step = (float) (2 * Math.PI / approximate);

        float x, y, z;

        Vector3 vector0 = new Vector3(center.getX(), center.getY(), height + center.getZ());

        for (float alpha = 0; alpha <= 2 * Math.PI; alpha += step) {
            x = (float) (radius * cos(alpha) + center.getX());
            y = (float) (radius * sin(alpha) + center.getY());
            z = 0 + center.getZ();

            Vector3 vector1 = new Vector3(x, y, z);

            x = (float) (radius * cos(alpha + step) + center.getX());
            y = (float) (radius * sin(alpha + step) + center.getY());

            Vector3 vector3 = new Vector3(x, y, z);

            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);

            polygon = new MyPolygon(center, vector1, vector3, new Color(r, g, b));
            polygons.add(polygon);
            polygon = new MyPolygon(vector0, vector1, vector3, new Color(r, g, b));
            polygons.add(polygon);
        }

        return polygons;
    }
}
