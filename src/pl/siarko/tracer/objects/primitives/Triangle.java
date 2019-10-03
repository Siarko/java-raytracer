package pl.siarko.tracer.objects.primitives;

import pl.siarko.tracer.algorithm.IntersectionTestResult;
import pl.siarko.tracer.material.Material;
import pl.siarko.tracer.material.MaterialRegister;
import pl.siarko.tracer.material.NewMaterial;
import pl.siarko.tracer.objects.IRenderable;
import pl.siarko.tracer.objects.ObjectTypes;
import pl.siarko.tracer.util.Util;
import pl.siarko.tracer.vec.Vec3;

import java.awt.*;


/*
* Klasa reprezentująca trójkąt
* */
public class Triangle implements IRenderable {

    public Vec3[] vertices;
    public Vec3 normal;
    public Material m;

    public Triangle(){
        this(new Vec3[]{
                new Vec3(-1, 0, 0),
                new Vec3(0, 1, 0),
                new Vec3(1, 0, 0)
        }, MaterialRegister.DEFAULT);
    }

    public Triangle(Vec3[] vertices, Material m){
        this.m = m;
        this.vertices = vertices;
        this.normal = new Vec3(vertices[1].sub(vertices[0])).det(vertices[2].sub(vertices[1])).normalize();
    }

    public Triangle(Vec3[] vertices){
        this(vertices, MaterialRegister.DEFAULT);
    }

    @Override
    public Vec3 getCenter() {
        float x = (this.vertices[0].x+this.vertices[1].x+this.vertices[2].x)/3;
        float y = (this.vertices[0].y+this.vertices[1].y+this.vertices[2].y)/3;
        float z = (this.vertices[0].z+this.vertices[1].z+this.vertices[2].z)/3;
        return new Vec3(x,y,z);
    }

    @Override
    public IntersectionTestResult intersect(Vec3 rayOrigin, Vec3 rayDirection) {
        IntersectionTestResult result = new IntersectionTestResult();
        Vec3 vectorP = rayDirection.det(vertices[2].sub(vertices[0]));
        float det = vectorP.dot(vertices[1].sub(vertices[0]));
        if(Math.abs(det) < Util.EPSILON){
            result.setResult(false);
            return result;
        }

        Vec3 vectorT = rayOrigin.sub(vertices[0]);
        float u = vectorT.dot(vectorP) / det;
        if(u < -Util.EPSILON || u > 1f + Util.EPSILON) {
            result.setResult(false);
            return result;
        }

        Vec3 vectorQ = vectorT.det(vertices[1].sub(vertices[0]));
        float v = rayDirection.dot(vectorQ)/det;
        if(v < -Util.EPSILON || u + v > 1f + Util.EPSILON){
            result.setResult(false);
            return result;
        }

        float distance = vectorQ.dot(vertices[2].sub(vertices[0]))/det;
        if(distance < -Util.EPSILON){
            result.setResult(false);
            return result;
        }
        result.setResult(true);
        result.setDistance(distance);

        return result;
    }

    @Override
    public Vec3 getNormalAtPoint(IntersectionTestResult hitPoint) {
        return normal;
    }

    @Override
    public Vec3 getLightDirection(Vec3 hitPoint) {
        return null;
    }

    @Override
    public void setMaterial(Material m) {
        this.m = m;
    }

    @Override
    public Material getMaterial() {
        return m;
    }


    @Override
    public void drawOnScene(Graphics2D g2d, boolean selected) {
        g2d.setColor(this.getMaterial().getSurfaceColor().toColor());
        Polygon p = new Polygon();
        p.addPoint((int)vertices[0].x, (int)vertices[0].z);
        p.addPoint((int)vertices[1].x, (int)vertices[1].z);
        p.addPoint((int)vertices[2].x, (int)vertices[2].z);
        g2d.fillPolygon(p);
    }

    @Override
    public void drawOnSceneSide(Graphics2D g2d, boolean selected) {
        g2d.setColor(this.getMaterial().getSurfaceColor().toColor());
        Polygon p = new Polygon();
        p.addPoint((int)-vertices[0].z, (int)-vertices[0].y);
        p.addPoint((int)-vertices[1].z, (int)-vertices[1].y);
        p.addPoint((int)-vertices[2].z, (int)-vertices[2].y);
        g2d.fillPolygon(p);
    }

    @Override
    public ObjectTypes getType() {
        return ObjectTypes.TRIANGLE;
    }

}
