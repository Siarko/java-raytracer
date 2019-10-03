package pl.siarko.tracer.environment;

import pl.siarko.tracer.vec.Vec3;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Created by SiarkoWodór on 30.10.2018.
 * Klasa środowiska - obiekt tej klasy zawiera wszystkie obiekty znajdujące się na scenie
 */
public class Environment{

    private Vec3 backgroundColor;

    public int rayMaxDepth = 5;

    private ArrayList<EnvironmentObject> renderables = new ArrayList<>();

    public void add(EnvironmentObject p){
        this.renderables.add(p);
    }

    public void remove(EnvironmentObject p){
        this.renderables.remove(p);
    }

    public ArrayList<EnvironmentObject> getRenderables(){
        return this.renderables;
    }

    public Vec3 getBackgroundColor() {
        if(this.backgroundColor == null){
            return new Vec3();
        }
        return backgroundColor;
    }

    public void setBackgroundColor(Vec3 backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setRayMaxDepth(int intValue) {
        this.rayMaxDepth = intValue;
    }
}
