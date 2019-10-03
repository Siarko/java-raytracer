package pl.siarko.frame;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    Drawable drawingActions = null;
    private Drawing drawingHelper;

    void setDrawingAction(Drawable action){
        this.drawingActions = action;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.drawingActions != null){
            this.drawingActions.draw(g);
            this.drawingHelper.applyFilters();
        }
        this.drawingHelper.drawToGraphics(g);
    }

    public void setDrawingHelper(Drawing drawingHelper) {
        this.drawingHelper = drawingHelper;
    }
}
