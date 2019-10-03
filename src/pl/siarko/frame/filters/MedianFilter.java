package pl.siarko.frame.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* Klasa implementująca filtr medianowy
* */
public class MedianFilter implements IFilter {

    private void apply(BufferedImage source, BufferedImage target, int x, int y, int matrixSize){
        int matSq = matrixSize*matrixSize;
        Color[] colors = FilterUtils.getColorArray(source, x,y, matrixSize);
        int r = 0, g = 0, b = 0;
        for(int i = 0; i < matSq; i++){
            Color c = colors[i];
            r += c.getRed();
            g += c.getGreen();
            b += c.getBlue();
        }
        r /= matSq;
        g /= matSq;
        b /= matSq;
        target.setRGB(x,y, FilterUtils.newColor(r,g,b).getRGB());
    }


    //Publiczna metoda stosująca filtr na przekazanym obrazie
    @Override
    public BufferedImage apply(BufferedImage source) {
        BufferedImage output = new BufferedImage(
                source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB
        );

        final float colorGap = 0.05f;
        final int edgeArea = 4;
        final int matrixSize = 3;
        FilterUtils.forEachPixel(output, (x, y) -> {
            Color c = FilterUtils.getColor(source, x, y);
            Color c1 = FilterUtils.getColor(source, x+1, y);
            Color c2 = FilterUtils.getColor(source, x, y-1);
            if(FilterUtils.getCollorDiff(c, c1) > colorGap || FilterUtils.getCollorDiff(c, c2) > colorGap){ //linia
                for(int a = x-edgeArea; a <= x+edgeArea; a++){
                    for(int b = y-edgeArea; b <= y+edgeArea; b++){
                        if(a > 0 && b > 0 && a < output.getWidth() && b < output.getHeight()){
                            MedianFilter.this.apply(source, output, a, b, matrixSize);
                        }
                    }
                }
                return null;
            }
            //do testów - pokazuje tylko wygładzone krawędzie
            //return null;
            return c;
        });

        return output;
    }
}
