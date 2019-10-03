package pl.siarko.frame;

import pl.siarko.Main;
import pl.siarko.frame.filters.IFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;

/**
 * Created by SiarkoWodór on 29.10.2018.
 */
public class Drawing {

    private BufferedImage mainImage = null;
    private Graphics context;
    private ArrayDeque<IFilter> filters = new ArrayDeque<>();

    public void setFrameSize(int width, int height){
        this.mainImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.context = this.mainImage.createGraphics();
    }

    private void validateAction(){
        if(this.context == null){
            System.out.println("Brak kontekstu graficznego!");
            System.exit(0);
        }
    }

    public void drawToGraphics(Graphics g) {
        g.drawImage(this.mainImage, 0, 0, null);
    }


    public void drawImage(BufferedImage image, int x, int y){
        this.validateAction();
        this.context.drawImage(image, x,y, null);
    }
    public void registerFilter(IFilter filter){
        this.filters.add(filter);
    }

    public void applyFilters() {
        this.validateAction();

        for (IFilter filter :this.filters) {
            this.mainImage = filter.apply(this.mainImage);
        }

    }
}
