package pl.siarko.frame.settings;

import pl.siarko.Main;
import pl.siarko.frame.components.JNumberField;
import pl.siarko.frame.util.UiUtil;
import pl.siarko.tracer.environment.EnvironmentObject;
import pl.siarko.tracer.objects.primitives.Sphere;
import pl.siarko.tracer.vec.Vec3;

import javax.swing.*;
import java.awt.*;

public class SphereParamsPanel extends JPanel implements ISettingsPanel {
    private JPanel mainPanel;
    private JNumberField xpos;
    private JNumberField ypos;
    private JNumberField zpos;
    private JNumberField radius;

    private EnvironmentObject boundSphere;

    public SphereParamsPanel(){
        this.add(mainPanel);
        this.setBackground(new Color(66, 66, 66));

        UiUtil.styleTextField(xpos);
        UiUtil.styleTextField(ypos);
        UiUtil.styleTextField(zpos);
        UiUtil.styleTextField(radius);

        xpos.addActionListener(e -> {
            if(this.boundSphere != null){
                ((Sphere) this.boundSphere.getRenderable()).setCenterX(xpos.floatValue());
                Main.settingsFrame.repaintVisualization();
            }
        });

        ypos.addActionListener(e -> {
            if(this.boundSphere != null){
                ((Sphere) this.boundSphere.getRenderable()).setCenterY(ypos.floatValue());
                Main.settingsFrame.repaintVisualization();
            }
        });

        zpos.addActionListener(e -> {
            if(this.boundSphere != null){
                ((Sphere) this.boundSphere.getRenderable()).setCenterZ(zpos.floatValue());
                Main.settingsFrame.repaintVisualization();
            }
        });

        radius.addActionListener(e -> {
            if(this.boundSphere != null){
                ((Sphere) this.boundSphere.getRenderable()).setR(radius.floatValue());
                Main.settingsFrame.repaintVisualization();
            }
        });
    }

    @Override
    public void setBoundObject(EnvironmentObject o) {
        this.boundSphere = o;
        Sphere s = (Sphere) this.boundSphere.getRenderable();
        this.xpos.setText(s.getCenter().x);
        this.ypos.setText(s.getCenter().y);
        this.zpos.setText(s.getCenter().z);
        this.radius.setText(s.getR());
    }
}
