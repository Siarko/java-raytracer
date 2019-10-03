package pl.siarko.tracer.util;

/**
 * Created by SiarkoWodór on 30.10.2018.
 * Klasa zawierająca statuczne metody pomocnicze
 */
public class Util {

    public static float EPSILON = 0.0001f;

    public static float mix(float a, float b, float ratio){
        return b*ratio + a*(1-ratio);
    }

    public static float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
