package pl.siarko.tracer.objects;

import pl.siarko.Main;
import pl.siarko.tracer.algorithm.IntersectionTestResult;
import pl.siarko.tracer.material.Material;
import pl.siarko.tracer.material.MaterialRegister;
import pl.siarko.tracer.objects.primitives.Triangle;
import pl.siarko.tracer.vec.Vec3;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PolygonMesh implements IRenderable {

    public PolygonMesh(){
        this.setMaterial(MaterialRegister.DEFAULT);
    }

    private boolean loaded = false;
    private Vec3 center = new Vec3();
    private ArrayList<Vec3> vertices = new ArrayList<>(), faces = new ArrayList<>();
    private ArrayList<Triangle> triangles = new ArrayList<>();
    private Material m;
    private float scale = 1;
    private Vec3 position = new Vec3();
    private Vec3 rotation = new Vec3();
    File modelFile = null;

    public void load(File f){
        try {
            Scanner s = new Scanner(f);
            while (s.hasNext()){
                String line = s.nextLine();
                if(line.length() < 2){continue;}
                String[] n = line.split("\\s+");
                for (int i = 0; i < n.length; i++){
                    n[i] = n[i].trim();
                }
                if(n[0].equals("v")){
                    Vec3 v = new Vec3(Float.parseFloat(n[1]), Float.parseFloat(n[2]), Float.parseFloat(n[3]));
                    vertices.add(v);
                }
                if(n[0].equals("f")) {
                    Vec3 v = new Vec3(Float.parseFloat(n[1])-1, Float.parseFloat(n[2])-1, Float.parseFloat(n[3])-1);
                    faces.add(v);
                }
            }

            this.rescale(1, new Vec3(), new Vec3());
            this.loaded = true;
            this.modelFile = f;

        } catch (FileNotFoundException e) {
            this.loaded = false;
            e.printStackTrace();
        }
        Main.settingsFrame.refresh();
    }

    public File getModelFile(){
        return this.modelFile;
    }

    public void rescale(float scale, Vec3 position, Vec3 rotation){
        this.triangles.clear();
        this.scale = scale;
        this.position = position;
        this.rotation = rotation;

        ArrayList<Vec3> tc = new ArrayList<>();
        for(Vec3 v : this.vertices){
            Vec3 v1 = new Vec3(v);
            v1.rotateAroundOrigin(rotation);
            v1.sub(center, true).multi(scale, true).add(position, true);
            tc.add(v1);
        }

        for(Vec3 fa: faces){
            Vec3[] ve = new Vec3[]{
                    tc.get((int)fa.get(0)),
                    tc.get((int)fa.get(1)),
                    tc.get((int)fa.get(2))
            };
            this.triangles.add(new Triangle(ve));
        }
    }

    public boolean isLoaded(){
        return this.loaded;
    }

    @Override
    public void drawOnScene(Graphics2D g2d, boolean selected) {
        if(this.loaded){
            g2d.scale(this.scale, this.scale);
            for (Triangle t :triangles) {
                t.drawOnScene(g2d, selected);
            }
            g2d.scale(1/scale, 1/scale);
        }
    }

    @Override
    public void drawOnSceneSide(Graphics2D g2d, boolean selected) {
        if(this.loaded){
            g2d.scale(this.scale, this.scale);
            for (Triangle t :triangles) {
                t.drawOnSceneSide(g2d, selected);
            }
            g2d.scale(1/scale, 1/scale);
        }
    }

    @Override
    public ObjectTypes getType() {
        return ObjectTypes.MESH;
    }

    @Override
    public Vec3 getCenter() {
        return position;
    }

    public void setCenter(Vec3 position){
        this.rescale(this.scale, position, this.rotation);
    }

    @Override
    public IntersectionTestResult intersect(Vec3 rayOrigin, Vec3 rayDirection) {

        IntersectionTestResult res = null;
        for(Triangle t: this.triangles){
            IntersectionTestResult r = t.intersect(rayOrigin, rayDirection);
            if(!r.getResult()){continue;}
            if(res == null || r.getDistance() < res.getDistance()){
                res = r;
                res.setTriangle(t);
            }
        }
        if(res == null){
            res = new IntersectionTestResult();
            res.setResult(false);
        }
        return res;
    }

    @Override
    public Vec3 getNormalAtPoint(IntersectionTestResult hitPoint) {
        return hitPoint.getTriangle().getNormalAtPoint(hitPoint);
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
        return this.m;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale){
        this.rescale(scale, this.position, this.rotation);
    }

    public Vec3 getRotation() {
        return rotation;
    }

    public void setRotation(Vec3 rotation) {
        this.rescale(this.scale, this.position, rotation);
    }
}
