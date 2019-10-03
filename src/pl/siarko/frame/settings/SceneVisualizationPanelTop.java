package pl.siarko.frame.settings;

import pl.siarko.Main;
import pl.siarko.tracer.environment.EnvironmentObject;
import pl.siarko.tracer.objects.IRenderable;
import pl.siarko.tracer.util.Util;
import pl.siarko.tracer.vec.Vec3;

import java.awt.*;
import java.util.ArrayList;

public class SceneVisualizationPanelTop extends SceneVisualizationPanel{

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = this.getWidth()/2;
        int h = this.getHeight()/2;
        g2.translate(w+this.offset.x, h+this.offset.y);
        g2.scale(scale, scale);


        ArrayList<EnvironmentObject> clone = new ArrayList<>(Main.environment.getRenderables());
        clone.sort((o1, o2) -> {
            if(o2.getRenderable() != null && o1.getRenderable() != null){
                return (int) (o1.getRenderable().getCenter().y - o2.getRenderable().getCenter().y);
            }else{
                return 0;
            }
        });

        for(EnvironmentObject e: clone){
            IRenderable p = e.getRenderable();
            if(p == null){continue;}
            boolean isSelected = (this.selectedObject != null && this.selectedObject.getRenderable() != null);
            if(isSelected){
                isSelected = p.equals(this.selectedObject.getRenderable());
            }
            p.drawOnScene(g2, isSelected);
        }
        int ox = Math.abs(offset.x);
        int oy = Math.abs(offset.y);
        g2.scale(1/scale, 1/scale);
        g2.setColor(new Color(206, 206, 206));
        g2.drawLine((-ox-w), 0, (ox+w), 0);
        g2.drawLine(0, (-oy-h), 0, (oy+h));

        g2.dispose();
    }

}
