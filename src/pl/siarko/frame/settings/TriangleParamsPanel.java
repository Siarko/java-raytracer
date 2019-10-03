package pl.siarko.frame.settings;

import pl.siarko.frame.components.JNumberField;
import pl.siarko.frame.util.UiUtil;
import pl.siarko.tracer.environment.EnvironmentObject;

import javax.swing.*;
import java.awt.*;

public class TriangleParamsPanel extends JPanel implements ISettingsPanel{
    private JPanel mainPanel;
    private JNumberField x1;
    private JNumberField y1;
    private JNumberField z1;
    private JNumberField x2;
    private JNumberField y2;
    private JNumberField z2;
    private JNumberField x3;
    private JNumberField y3;
    private JNumberField z3;

    public TriangleParamsPanel(){
        this.add(mainPanel);
        this.setBackground(new Color(66, 66, 66));

        UiUtil.styleTextField(x1);
        UiUtil.styleTextField(x2);
        UiUtil.styleTextField(x3);
        UiUtil.styleTextField(y1);
        UiUtil.styleTextField(y2);
        UiUtil.styleTextField(y3);
        UiUtil.styleTextField(z1);
        UiUtil.styleTextField(z2);
        UiUtil.styleTextField(z3);
    }

    @Override
    public void setBoundObject(EnvironmentObject o) {

    }
}
