package pl.siarko.tracer;

import pl.siarko.tracer.vec.Vec3;

import java.awt.*;

/**
 * Created by SiarkoWodór on 30.10.2018.
 * Klasa implementująca kamerę perspektywiczną
 */
public class Camera {

    //FOV (field of view) - kąt widzenia
    private float fov;
    //ułamek wysokości i szerokości
    private float invWidth, invHeight;
    //stosunek wysokości do szerokości obrazu
    private float aspectRatio;
    //kąt fov w radianach
    private float angle;

    //inicjalizacja kamery
    public Camera(float fov, int w, int h){
        this.invWidth = 1f/(float)w;
        this.invHeight = 1f/(float)h;
        this.aspectRatio = (float)w/(float)h;
        this.setFov(fov);
    }

    //zmiana rozmiaru obrazu
    public void resize(Dimension d){
        this.invWidth = 1f/(float)d.width;
        this.invHeight = 1f/(float)d.height;
        this.aspectRatio = (float)d.width/(float)d.height;
    }

    //pobranie wektora kierunkowego w punkcie x,y
    public Vec3 getDirectionVector(int x, int y){
        return new Vec3(
                (2f*((x+0.5f)*this.getInvWidth())-1f)*this.getAngle()*this.getAspectRatio(),
                (1f-2f*((y+0.5f)*this.getInvHeight()))*this.getAngle(),
                -1f
        );
    }

    //ustawienie kąta widzenia
    public void setFov(float fov) {
        this.fov = fov;
        this.angle = (float)Math.tan(Math.PI * this.fov / 360.0);
    }

    public float getAngle() {
        return angle;
    }

    public float getFov() {
        return fov;
    }

    public float getInvWidth() {
        return invWidth;
    }

    public float getInvHeight() {
        return invHeight;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public int getWidth() {
        return (int)(2f/invWidth);
    }
}
