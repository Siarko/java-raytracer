package pl.siarko.frame.util;

import pl.siarko.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UiUtil {

    public static JButton styleButton(JButton button){

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(Constants.BUTTON_BG);
        button.setForeground(Constants.BUTTON_TEXT);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                button.setBackground(Constants.BUTTON_BG_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                button.setBackground(Constants.BUTTON_BG);
            }
        });

        return button;
    }

    public static JTextField styleTextField(JTextField field){
        field.setBackground(Constants.BUTTON_BG);
        field.setForeground(Constants.BUTTON_TEXT);
        field.setBorder(null);

        Insets i = new Insets(3, 5, 5, 5);
        field.setMargin(i);
        if(field instanceof JFormattedTextField){
            JFormattedTextField tf = (JFormattedTextField) field;
            tf.setCaretColor(Constants.BUTTON_TEXT);
        }

        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                field.setBackground(Constants.BUTTON_BG_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                field.setBackground(Constants.BUTTON_BG);
            }
        });

        return field;
    }

    public static JComboBox styleComboBoc(JComboBox b){
        b.setBorder(null);
        b.setBackground(Constants.BUTTON_BG);
        b.setForeground(Constants.BUTTON_TEXT);
        return b;
    }

    public static Icon getButtonColorIcon(Color c){
        return getButtonColorIcon(c, new Point(60, 30));
    }

    public static Icon getButtonColorIcon(Color c, Point size){
        BufferedImage b = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = b.createGraphics();
        g.setColor(c);
        g.fillRect(0,0, size.x, size.y);
        return new ImageIcon(b);
    }
}
