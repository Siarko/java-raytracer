package pl.siarko.tracer.vec;

/*
* Klasa implementująca metody obrotu wektorów
* */
public class Rotation {
    public static void rotateX(Vec3 vector, float angle){
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float y = vector.y, z = vector.z;
        vector.y = cos * y - sin * z;
        vector.z = sin * y + cos * z;
    }

    public static void rotateY(Vec3 vector, float angle){
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float x = vector.x, z = vector.z;
        vector.x = cos * x - sin * z;
        vector.z = sin * x + cos * z;
    }

    public static void rotateZ(Vec3 vector, float angle){
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float x = vector.x, y = vector.y;
        vector.x = cos * x - sin * y;
        vector.y = sin * x + cos * y;
    }


}
