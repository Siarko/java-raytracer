package pl.siarko.frame;

import pl.siarko.Main;
import pl.siarko.tracer.Camera;
import pl.siarko.tracer.RayTracer;
import pl.siarko.tracer.environment.Environment;
import pl.siarko.tracer.environment.EnvironmentObject;
import pl.siarko.tracer.material.MaterialRegister;
import pl.siarko.tracer.material.NewMaterial;
import pl.siarko.tracer.objects.primitives.Sphere;
import pl.siarko.tracer.vec.Vec3;
import pl.siarko.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MaterialVisualizationPanel extends JPanel {

    RayTracer rayTracer;
    float previewFOV = 50;
    Environment environment;
    Sphere representation = null;

    public MaterialVisualizationPanel(){
        this.rayTracer = new RayTracer();
        this.environment = new Environment();
        this.rayTracer.setEnvironment(this.environment);
        this.setBackground(Color.BLACK);

        this.constructPreviewEnv();
    }

    private void constructPreviewEnv(){
        this.environment.setBackgroundColor(Main.environment.getBackgroundColor());

        EnvironmentObject o1 = new EnvironmentObject();
        Sphere s1 = new Sphere();
        s1.setCenterY(10);
        s1.setCenterX(5);
        s1.setR(1);
        s1.setMaterial(MaterialRegister.LIGHT);
        o1.setObject(s1);
        environment.add(o1);

        EnvironmentObject o2 = new EnvironmentObject();
        representation = new Sphere();
        representation.setCenterZ(-30);
        representation.setR(7);
        o2.setObject(representation);
        environment.add(o2);
    }

    public void changeMaterial(NewMaterial m){
        this.representation.setMaterial(m);
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.representation.getMaterial() != null){
            this.environment.setBackgroundColor(Main.environment.getBackgroundColor());
            this.rayTracer.setCamera(new Camera(this.previewFOV, this.getWidth(), this.getHeight()));

            BufferedImage previev = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = previev.createGraphics();
            for(int y = 0; y < this.getHeight(); y++){
                for(int x = 0; x < this.getWidth(); x++){
                    Color c = this.rayTracer.trace(x,y);
                    g2d.setColor(c);
                    g2d.drawLine(x,y,x,y);
                }
            }
            g2d.dispose();
            g.drawImage(previev, 0, 0, null);
        }

    }
}
