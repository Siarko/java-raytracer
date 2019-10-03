package pl.siarko.frame.settings;

import pl.siarko.Main;
import pl.siarko.frame.components.JNumberField;
import pl.siarko.frame.util.UiUtil;
import pl.siarko.tracer.material.NewMaterial;
import pl.siarko.tracer.util.Util;
import pl.siarko.tracer.vec.Vec3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditMaterial extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel newMaterialLabel;
    private JTextField matName;
    private JSlider transparency;
    private JButton selectSCButton;
    private JButton selectELColor;
    private JNumberField ip;
    private JLabel transparencyLabel;
    private JLabel errorLabel;
    private JNumberField lightIntensity;

    private Color surfaceColor = new Color(0,0,0);
    private Color emissionColor = new Color(0,0,0);

    private NewMaterial reference = null;

    public EditMaterial() {
        this(null);
    }

    public EditMaterial(NewMaterial m) {
        this.reference = m;

        if(m != null){
            this.surfaceColor = m.getSurfaceColor().toColor();
            this.emissionColor = m.getEmissionColor().toColor();
            this.matName.setText(m.getName());
            this.transparency.setValue((int) Util.map(m.getTransparency(), 0, 1, 0, 200));
            this.ip.setText(m.getReflectionIndex());
            this.lightIntensity.setText(m.getLightIntensity());
            this.newMaterialLabel.setText("Edycja materiału");
        }

        setPreferredSize(new Dimension(600, 500));
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        UiUtil.styleButton(buttonOK);
        UiUtil.styleButton(buttonCancel);

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> {
                    onCancel();
                },
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );

        UiUtil.styleTextField(matName);
        UiUtil.styleTextField(ip);
        UiUtil.styleTextField(lightIntensity);
        this.transparencyLabel.setText((float) this.transparency.getValue() / 200f + "");
        this.transparency.addChangeListener(e -> {
            this.transparencyLabel.setText(this.transparency.getValue() / 200f + "");
        });

        UiUtil.styleButton(selectSCButton);
        UiUtil.styleButton(selectELColor);

        selectSCButton.setIcon(UiUtil.getButtonColorIcon(this.surfaceColor));
        selectELColor.setIcon(UiUtil.getButtonColorIcon(this.emissionColor));

        selectSCButton.addActionListener(e -> {
            Color c = JColorChooser.showDialog(this, "Kolor powierzchni materiału", surfaceColor);
            if (c != null) {
                surfaceColor = c;
                selectSCButton.setIcon(UiUtil.getButtonColorIcon(c));
            }
        });

        selectELColor.addActionListener(e -> {
            Color c = JColorChooser.showDialog(this, "Kolor światła", emissionColor);
            if (c != null) {
                emissionColor = c;
                selectELColor.setIcon(UiUtil.getButtonColorIcon(c));
            }
        });

        this.pack();
    }

    private boolean check(){
        boolean flag = true;
        if(this.matName.getText().trim().length() == 0){
            flag = false;
        }
        return flag;
    }

    private void setMaterialProps(NewMaterial m){
        m.setName(this.matName.getText().trim());
        m.setTransparency(this.transparency.getValue()/200f);
        m.setRf(this.ip.floatValue());
        m.setSurfaceColor(new Vec3(this.surfaceColor));
        m.setEmissionColor(new Vec3(this.emissionColor));
        m.setLightIntensity(this.lightIntensity.floatValue());
    }

    private void onOK() {
        if(!this.check()){
            this.errorLabel.setText("Należy określić nazwę nowego materiału");
            this.errorLabel.setVisible(true);
        }else{
            if(this.reference == null){
                NewMaterial m = new NewMaterial();
                this.setMaterialProps(m);
                Main.materialRegister.add(m);
            }else{
                this.setMaterialProps(this.reference);
                Main.settingsFrame.materialVisualization.repaint();
            }

            dispose();
        }
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Font f = new Font("Tahoma", Font.PLAIN, 30);
        this.newMaterialLabel = new JLabel();
        this.newMaterialLabel.setFont(f);
    }
}
