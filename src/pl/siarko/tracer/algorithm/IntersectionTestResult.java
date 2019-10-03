package pl.siarko.tracer.algorithm;

import pl.siarko.tracer.objects.primitives.Triangle;
import pl.siarko.tracer.vec.Vec3;

/**
 * Created by SiarkoWodór on 30.10.2018.
 * Obiekt tej klasy reprezentuje wynik testu zderzenia promienia z obiektem
 */
public class IntersectionTestResult {

    private boolean result = true;
    private float distance;
    private Triangle t = null;
    private Vec3 hitPoint = new Vec3();

    public IntersectionTestResult(){}

    public IntersectionTestResult(boolean result, float distance){
        this.result = result;
        this.distance = distance;
    }

    public Vec3 getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(Vec3 hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void setTriangle(Triangle t){
        this.t = t;
    }

    public Triangle getTriangle(){
        return this.t;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
