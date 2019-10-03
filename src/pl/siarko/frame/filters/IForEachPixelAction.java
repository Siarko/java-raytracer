package pl.siarko.frame.filters;

import java.awt.*;

/*
* Interfejs akcji - Wykorzystywany przez metodę pl.siarko.frame.filters.FilterUtils.forEachPixel
* */

public interface IForEachPixelAction {
    Color action(int x, int y);
}
