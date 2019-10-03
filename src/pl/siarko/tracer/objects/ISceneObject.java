package pl.siarko.tracer.objects;

import java.awt.*;

public interface ISceneObject {

    void drawOnScene(Graphics2D g2d, boolean selected);
    void drawOnSceneSide(Graphics2D g2d, boolean selected);

    ObjectTypes getType();

    String toString();
}
