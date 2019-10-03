package pl.siarko.tracer.objects.primitives;

import pl.siarko.tracer.algorithm.IntersectionTestResult;
import pl.siarko.tracer.material.Material;
import pl.siarko.tracer.material.MaterialRegister;
import pl.siarko.tracer.objects.IRenderable;
import pl.siarko.tracer.objects.ObjectTypes;
import pl.siarko.tracer.util.Util;
import pl.siarko.tracer.vec.Vec3;

import java.awt.*;

/**
 * Created by SiarkoWodór on 29.10.2018.
 * Klasa reprezentująca sferę
 */
public class Sphere implements IRenderable {

    private Vec3 center;
    private float r, r2;
    private Material material;

    public Sphere(){
        this(new Vec3(), 0, MaterialRegister.DEFAULT);
    }

    private Sphere(Vec3 center, float r){
        this.center = center;
        this.r = r;
        this.r2 = r*r;
    }

    public Sphere(Vec3 center, float r, Material m){
        this(center, r);
        this.material = m;
    }

    public void setMaterial(Material m){
        this.material = m;
    }

    public Material getMaterial(){
        return this.material;
    }


    public Vec3 getCenter(){
        return this.center;
    }

    public void setCenter(Vec3 center){
        this.center = center;
    }

    public void setCenterX(float x){
        this.center.x = x;
    }

    public void setCenterY(float y){
        this.center.y = y;
    }

    public void setCenterZ(float z){
        this.center.z = z;
    }

    public float getR(){
        return this.r;
    }

    public void setR(float r){
        this.r = r;
        this.r2 = r*r;
    }

    @Override
    public void drawOnScene(Graphics2D g2d, boolean selected) {
        g2d.setColor(this.material.getSurfaceColor().toColor());
        g2d.fillOval((int)(this.center.x-r), (int)(this.center.z-r), (int)r*2, (int)r*2);
        if(selected){
            g2d.setColor(Color.white);
            g2d.drawOval((int)(this.center.x-r), (int)(this.center.z-r), (int)r*2, (int)r*2);
        }
    }

    @Override
    public void drawOnSceneSide(Graphics2D g2d, boolean selected) {
        g2d.setColor(this.material.getSurfaceColor().toColor());
        g2d.fillOval((int)(-this.center.z-r), (int)(-this.center.y-r), (int)r*2, (int)r*2);
        if(selected){
            g2d.setColor(Color.white);
            g2d.drawOval((int)(-this.center.z-r), (int)(-this.center.y-r), (int)r*2, (int)r*2);
        }
    }

    @Override
    public ObjectTypes getType() {
        return ObjectTypes.SPHERE;
    }


    @Override
    public IntersectionTestResult intersect(Vec3 rayOrigin, Vec3 rayDirection) {

        IntersectionTestResult result = new IntersectionTestResult();

        //test - czy promień zderza się ze sferą
        Vec3 l = this.center.sub(rayOrigin);
        float tca = l.dot(rayDirection);
        if(tca < -Util.EPSILON){
            result.setResult(false);
            return result;
        }
        float d2 = r2 - l.dot(l) + tca*tca;
        if(d2 < -Util.EPSILON){
            result.setResult(false);
            return result;
        }
        float thc = (float)Math.sqrt(d2);

        float t0 = tca - thc;
        float t1 = tca+thc;
        result.setResult(true);
        if(t0 > Util.EPSILON){
            result.setDistance(t0);
        }else{
            result.setDistance(t1);
        }

        return result;
    }

    @Override
    public Vec3 getNormalAtPoint(IntersectionTestResult hitPoint) {
        return new Vec3(hitPoint.getHitPoint().sub(this.center)).normalize();
    }

    @Override
    public Vec3 getLightDirection(Vec3 hitPoint) {
        return new Vec3(this.center.sub(hitPoint)).normalize();
    }

    public boolean equals(Object p){
        if(p instanceof Sphere){
            return (this.center.equals(((Sphere) p).center));
        }
        return false;
    }

}
