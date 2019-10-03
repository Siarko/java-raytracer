package pl.siarko.tracer.vec;

import pl.siarko.tracer.util.Util;

import java.awt.*;

/**
 * Created by SiarkoWodór on 29.10.2018.
 * Klasa implementująca trówymiarowy wektor przechowujący liczby zmiennoprzecinkowe
 */
public class Vec3 {

    public float x, y, z;

    public Vec3(Color c){
        this.x = Util.map(c.getRed(), 0, 255, 0, 1);
        this.y = Util.map(c.getGreen(), 0, 255, 0, 1);
        this.z = Util.map(c.getBlue(), 0, 255, 0, 1);
    }

    public Vec3(Vec3 vector){
        this(vector.x, vector.y, vector.z);
    }

    public Vec3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(float value){
        this(value, value, value);
    }

    public Vec3(){
        this(0);
    }

    //pobranie wartości za pomocą indeksu - 0:x, 1:y, 2:z
    public float get(int index){
        if(index == 0){return x;}
        if(index == 1){return y;}
        if(index == 2){return z;}
        return 0;
    }

    //ustawienie wartości za pomocą indeksu
    public void set(int index, float v){
        if(index == 0){x = v;}
        if(index == 1){y = v;}
        if(index == 2){z = v;}
    }

    //obrót wektora wokół (0,0,0)
    public void rotateAroundOrigin(Vec3 angles){
        if(angles.x != 0){Rotation.rotateX(this, angles.x);}
        if(angles.y != 0){Rotation.rotateY(this, angles.y);}
        if(angles.z != 0){Rotation.rotateZ(this, angles.z);}
    }

    //długość wektora do kwadratu
    public float lenSqr(){
        return x*x+y*y+z*z;
    }

    //długość wektora
    public float len(){
        return (float)Math.sqrt(this.lenSqr());
    }

    //mnożenie wektora przez skalar
    public Vec3 multi(float scalar){
        return new Vec3(this.x*scalar, this.y*scalar, this.z*scalar);
    }
    public Vec3 multi(float scalar, boolean toThis){
        if(toThis){
            this.x *= scalar;
            this.y *= scalar;
            this.z *= scalar;
            return this;
        }else{
            return this.multi(scalar);
        }
    }

    //mnożenie wektora przez wektor
    public Vec3 multi(Vec3 v){
        return new Vec3(this.x*v.x, this.y*v.y, this.z*v.z);
    }

    //dodawanie wektorów
    public Vec3 add(Vec3 v){
        return new Vec3(this.x+v.x, this.y+v.y, this.z+v.z);
    }
    public Vec3 add(Vec3 v, boolean toThis){
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    //odejmowanie wektorów
    public Vec3 sub(Vec3 v){
        return new Vec3(this.x-v.x, this.y-v.y, this.z-v.z);
    }
    public Vec3 sub(Vec3 v, boolean toThis){
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }

    //odwórcenie zwrotu wektora
    public Vec3 inverse(){
        return new Vec3(-x, -y, -z);
    }
    public Vec3 inverse(boolean toThis){
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    //normalizacja wektora
    public Vec3 normalize(){
        float len2 = this.lenSqr();
        if(len2 > 0){
            float inverseNorm = 1f/(float)(Math.sqrt(len2));
            this.x *= inverseNorm;
            this.y *= inverseNorm;
            this.z *= inverseNorm;
        }
        return this;
    }

    public float dot(Vec3 v){
        return this.x*v.x + this.y*v.y + this.z*v.z;
    }

    //wyznacznik
    public Vec3 det(Vec3 v){
        return new Vec3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    //przekrztałcenie wektora na kolor RGB
    public Color toColor(){
        int r = (int)(255f*Math.min(1,this.x));
        int g = (int)(255f*Math.min(1,this.y));
        int b = (int)(255f*Math.min(1,this.z));

        return new Color(r,g,b);
    }

    public boolean equals(Vec3 v){
        return (x == v.x && y == v.y && z == v.z);
    }

    public String toString(){
        return "Vec3["+this.x+", "+this.y+", "+this.z+"]";
    }
}
