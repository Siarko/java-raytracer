package pl.siarko.frame.settings;

import pl.siarko.Main;
import pl.siarko.frame.components.JNumberField;
import pl.siarko.frame.util.UiUtil;
import pl.siarko.tracer.environment.EnvironmentObject;
import pl.siarko.tracer.objects.PolygonMesh;
import pl.siarko.tracer.vec.Vec3;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class MeshParamsPanel extends JPanel implements ISettingsPanel{

    private JPanel mainPanel;
    private JButton selectModelButton;
    private JLabel modelName;
    private JPanel activeMeshParams;
    private JNumberField xpos;
    private JNumberField ypos;
    private JNumberField zpos;
    private JNumberField scale;
    private JNumberField rotX;
    private JNumberField rotY;
    private JNumberField rotZ;

    EnvironmentObject boundobject;

    public MeshParamsPanel(){
        this.setLayout(new BorderLayout());
        this.add(mainPanel);

        this.selectModelButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setPreferredSize(new Dimension(800, 600));
            fc.setFileFilter(new FileNameExtensionFilter("Object file", "obj"));
            fc.addActionListener(e1 -> {
                File f = fc.getSelectedFile();
                if(f == null){return;}
                modelName.setText(f.getName());
                if(boundobject.getRenderable() != null){
                    PolygonMesh p = (PolygonMesh) boundobject.getRenderable();
                    p.load(f);
                    this.activeMeshParams.setVisible(p.isLoaded());
                }
            });
            fc.showDialog(this, "Wybierz");
        });

        scale.addActionListener(e -> {
            PolygonMesh p = (PolygonMesh) this.boundobject.getRenderable();
            p.setScale(scale.floatValue());
            Main.settingsFrame.refresh();
        });

        xpos.addActionListener(e -> {
            PolygonMesh p = (PolygonMesh) this.boundobject.getRenderable();
            p.setCenter(new Vec3(xpos.floatValue(), p.getCenter().y, p.getCenter().z));
            Main.settingsFrame.refresh();
        });

        ypos.addActionListener(e -> {
            PolygonMesh p = (PolygonMesh) this.boundobject.getRenderable();
            p.setCenter(new Vec3(p.getCenter().x, ypos.floatValue(), p.getCenter().z));
            Main.settingsFrame.refresh();
        });

        zpos.addActionListener(e -> {
            PolygonMesh p = (PolygonMesh) this.boundobject.getRenderable();
            p.setCenter(new Vec3(p.getCenter().x, p.getCenter().y, zpos.floatValue()));
            Main.settingsFrame.refresh();
        });

        rotX.addActionListener(e -> {
            PolygonMesh p = (PolygonMesh) this.boundobject.getRenderable();
            p.setRotation(new Vec3(rotX.floatValue(), p.getRotation().y, p.getRotation().z));
            Main.settingsFrame.refresh();
        });
        rotY.addActionListener(e -> {
            PolygonMesh p = (PolygonMesh) this.boundobject.getRenderable();
            p.setRotation(new Vec3(p.getRotation().x, rotY.floatValue(), p.getRotation().z));
            Main.settingsFrame.refresh();
        });
        rotZ.addActionListener(e -> {
            PolygonMesh p = (PolygonMesh) this.boundobject.getRenderable();
            p.setRotation(new Vec3(p.getRotation().x, p.getRotation().y, rotZ.floatValue()));
            Main.settingsFrame.refresh();
        });

        UiUtil.styleButton(this.selectModelButton);
        UiUtil.styleTextField(this.xpos);
        UiUtil.styleTextField(this.ypos);
        UiUtil.styleTextField(this.zpos);
        UiUtil.styleTextField(this.scale);
        UiUtil.styleTextField(this.rotX);
        UiUtil.styleTextField(this.rotY);
        UiUtil.styleTextField(this.rotZ);
    }

    @Override
    public void setBoundObject(EnvironmentObject o) {
        this.boundobject = o;
        this.modelName.setText("Nie wybrano");
        if(o.getRenderable() != null){
            PolygonMesh p = (PolygonMesh) o.getRenderable();
            if(p.getModelFile() != null){
                this.modelName.setText(p.getModelFile().getName());
            }
            xpos.setText(p.getCenter().x);
            ypos.setText(p.getCenter().y);
            zpos.setText(p.getCenter().z);
            scale.setText(p.getScale());

            rotX.setText(p.getRotation().x);
            rotZ.setText(p.getRotation().y);
            rotY.setText(p.getRotation().z);
        }
    }
}
