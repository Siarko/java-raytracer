package pl.siarko.frame;

import pl.siarko.Main;
import pl.siarko.util.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SiarkoWodór on 29.10.2018.
 */
public class Frame extends JFrame {

    private Panel panel;
    private SettingsFrame settingsPanel;
    public Drawing drawing;

    public Frame(String title, int w, int h){

        this.panel = new Panel();
        this.drawing = new Drawing();
        drawing.setFrameSize(Main.graphicsSize.width, Main.graphicsSize.height);
        this.setResizable(false);

        this.panel.setDrawingHelper(this.drawing);
        this.panel.setPreferredSize(new Dimension(w,h));

        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(panel);
        this.pack();

    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        drawing.setFrameSize(d.width, d.height);
    }

    public void setDrawingAction(Drawable action){
        this.panel.setDrawingAction(action);
    }

    public void refresh(){
        panel.repaint();
    }

}
