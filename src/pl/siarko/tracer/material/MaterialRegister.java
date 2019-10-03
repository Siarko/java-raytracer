package pl.siarko.tracer.material;

import pl.siarko.tracer.vec.Vec3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;

/*
* Rejestr materiałów
* */
public class MaterialRegister {

    private ArrayDeque<Material> register = new ArrayDeque<>();
    private ArrayDeque<JComboBox> boxes = new ArrayDeque<>();
    public static Material DEFAULT = new NewMaterial();
    public static NewMaterial LIGHT = new NewMaterial();
    public static NewMaterial GLASS = new NewMaterial();

    public MaterialRegister(){
        LIGHT.setEmissionColor(new Vec3(2));
        LIGHT.setName("Swiatło");

        GLASS.setSurfaceColor(new Vec3(new Color(200, 20, 0)));
        GLASS.setTransparency(1f);
        GLASS.setRf(1.5f);
        GLASS.setName("Szkło");
        this.add(DEFAULT);
        this.add(LIGHT);
        this.add(GLASS);
    }

    public void add(Material m){
        this.register.add(m);
        for(JComboBox box: boxes){
            box.addItem(m);
        }
    }

    public void remove(Material m) {
        this.register.remove(m);
        for(JComboBox box: boxes){
            box.removeItem(m);
        }
    }

    public ArrayDeque<Material> getMaterials(){
        return this.register;
    }

    public void addListener(JComboBox box){
        this.boxes.add(box);
        for(Material m: this.register){
            box.addItem(m);
        }
    }
}
