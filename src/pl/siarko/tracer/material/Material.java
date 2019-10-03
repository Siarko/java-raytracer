package pl.siarko.tracer.material;

import pl.siarko.tracer.vec.Vec3;

import java.awt.*;

/**
 * Created by SiarkoWodór on 30.10.2018.
 * Interfejs materiału obiektu
 */
public interface Material {

    float getTransparency();
    float getReflectionIndex();

    Vec3 getSurfaceColor();
    Vec3 getEmissionColor();

    float getLightIntensity();

    Vec3 getRenderableEmission();
}
