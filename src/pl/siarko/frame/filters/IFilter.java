package pl.siarko.frame.filters;

import java.awt.image.BufferedImage;

/*
* Interfejs filtrów obrazu
* */

public interface IFilter {
    BufferedImage apply(BufferedImage source);
}
