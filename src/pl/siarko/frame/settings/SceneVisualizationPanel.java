package pl.siarko.frame.settings;

import pl.siarko.tracer.environment.EnvironmentObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class SceneVisualizationPanel extends JPanel implements MouseWheelListener, MouseMotionListener,
        MouseListener, KeyListener{

    public float scale = 1;
    protected Point draggingStart = null;
    protected Point offset = new Point();
    public EnvironmentObject selectedObject = null;
    public float scaleStep = 0.2f;

    public SceneVisualizationPanel(){
        super();
        this.addMouseWheelListener(this);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
    }
    public void setSelectedObject(EnvironmentObject o){
        this.selectedObject = o;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.scale += -e.getWheelRotation()*scaleStep;
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        this.draggingStart = e.getPoint();
        this.setFocusable(true);
        this.setRequestFocusEnabled(true);

        this.requestFocus();
        this.grabFocus();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.draggingStart = null;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if(this.draggingStart != null){
            float xdelta = e.getX()-this.draggingStart.x;
            float ydelta = e.getY()-this.draggingStart.y;

            this.offset.x += xdelta;
            this.offset.y += ydelta;
            this.draggingStart = e.getPoint();
            this.repaint();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == '-'){
            this.scale -= scaleStep;
        }
        if(e.getKeyChar() == '+'){
            this.scale += scaleStep;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
