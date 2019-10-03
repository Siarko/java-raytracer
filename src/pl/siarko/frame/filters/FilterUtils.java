package pl.siarko.frame.filters;

import pl.siarko.tracer.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* Klasa zawierająca statyczne metody pomocnicze dla filtrów obrazu
* */
public class FilterUtils {

    //metoda zwraca kolor danego piksela na obrazie
    public static Color getColor(BufferedImage image, int x, int y){
        int posX = x, posY = y;
        if(posX < 0){posX = Math.abs(posX);}
        if(posY < 0){posY = Math.abs(posY);}

        if(posX >= image.getWidth()){
            posX = image.getWidth()-(posX-image.getWidth())-1;
        }
        if(posY >= image.getHeight()){
            posY = image.getHeight()-(posY-image.getHeight())-1;
        }
        return new Color(image.getRGB(posX, posY));
    }

    //metoda zwraca tablicę kolorów. Kolory są pobierane z kwadratu pikseli w danej lokalizacji x,y
    public static Color[] getColorArray(BufferedImage image, int x, int y, int size){
        Color[] out = new Color[size*size];
        int index = 0;
        for(int xp = x-size/2; xp <= x+size/2; xp++){
            for(int yp = y-size/2; yp <= y+size/2; yp++){
                Color c = FilterUtils.getColor(image, xp, yp);
                out[index] = c;
                index++;
            }
        }
        return out;
    }

    //akcja pomocnicza do wykonywania dostarczonej akcji na każdym pikselu obrazu
    public static void forEachPixel(BufferedImage image, IForEachPixelAction action){
        for(int x = 0; x < image.getWidth(); x++){
            for(int y = 0; y < image.getHeight(); y++){
                Color c = action.action(x,y);
                if(c != null){
                    image.setRGB(x,y,c.getRGB());
                }
            }
        }
    }

    public static Color newColor(int r, int g, int b){
        if(r < 0){r = 0;}
        if(g < 0){g = 0;}
        if(b < 0){b = 0;}

        if(r > 255){r=255;}
        if(g > 255){g=255;}
        if(b > 255){b=255;}

        return new Color(r,g,b);
    }

    public static float getCollorDiff(Color c1, Color c2){
        double value =  Math.sqrt(
                Math.pow((c2.getRed()-c1.getRed()), 2) +
                Math.pow((c2.getGreen()-c1.getGreen()),2) +
                Math.pow((c2.getBlue()-c1.getBlue()),2)
        );

        return Util.map((float)value, 0, 442, 0, 1);
    }
}
