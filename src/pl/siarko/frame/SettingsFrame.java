package pl.siarko.frame;

import pl.siarko.Main;
import pl.siarko.frame.components.JNumberField;
import pl.siarko.frame.settings.*;
import pl.siarko.frame.util.UiUtil;
import pl.siarko.tracer.environment.EnvironmentObject;
import pl.siarko.tracer.material.Material;
import pl.siarko.tracer.material.NewMaterial;
import pl.siarko.tracer.objects.ObjectTypes;
import pl.siarko.tracer.objects.PolygonMesh;
import pl.siarko.tracer.objects.primitives.Sphere;
import pl.siarko.tracer.objects.primitives.Triangle;
import pl.siarko.tracer.vec.Vec3;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;

public class SettingsFrame extends JFrame implements TreeSelectionListener {

    private JPanel mainPanel;
    private JPanel sceneVisualizationPanelTop;
    private JPanel objectSettingsPanel;
    private JPanel objectSelectionPanel;
    private JTree objectSelectionTree;
    private JButton newObjectButton;
    private JButton generateButton;
    private JComboBox objectTypeSelect;
    private JPanel specificSettingsPannel;
    private JComboBox objectMaterialSelect;
    private JTabbedPane tabbedPane1;
    private JPanel scenePanel;
    private JPanel generalPanel;
    private JButton selectEnvColorBtn;
    private JNumberField globRayBounceCount;
    private JNumberField workerCount;
    private JButton removeObjectButton;
    private JPanel materialPanel;
    private JButton newMaterialButton;
    private JComboBox materialSettingsSelect;
    private JButton changeMaterialProps;
    public JPanel materialVisualization;
    private JPanel sceneVisualizationPanelSide;
    private JNumberField imageWidth;
    private JNumberField imageHeight;
    private JCheckBox applyFilter;
    private JNumberField cameraFov;
    private JPanel generalSettingsPanel;

    public DefaultMutableTreeNode treeRoot;
    public DefaultTreeModel treeModel;

    public ISettingsPanel sphereParamsPanel;
    public ISettingsPanel triangleParamsPanel;
    public ISettingsPanel meshParamsPanel;

    public EnvironmentObject selectedObject = null;

    public SettingsFrame(String title, int w, int h){
        this.setTitle(title);
        this.setPreferredSize(new Dimension(w,h));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(this.mainPanel);
        this.pack();

        this.sphereParamsPanel = new SphereParamsPanel();
        this.triangleParamsPanel = new TriangleParamsPanel();
        this.meshParamsPanel = new MeshParamsPanel();

        this.newObjectButton.addActionListener(e -> {
            addNewObject();
        });

        this.removeObjectButton.addActionListener(e -> {
            removeObject();
        });

        this.newMaterialButton.addActionListener(e -> {
            EditMaterial editMaterialDialog = new EditMaterial();
            editMaterialDialog.setVisible(true);

        });

        this.generateButton.addActionListener(e -> {
            Main.render();
        });

        materialSettingsSelect.addActionListener(e -> {
            MaterialVisualizationPanel p = (MaterialVisualizationPanel) this.materialVisualization;
            p.changeMaterial((NewMaterial) materialSettingsSelect.getSelectedItem());
            this.changeMaterialProps.setVisible(true);
        });

        changeMaterialProps.addActionListener(e -> {
            NewMaterial m = (NewMaterial) materialSettingsSelect.getSelectedItem();
            EditMaterial editMaterialDialog = new EditMaterial(m);
            editMaterialDialog.setVisible(true);
        });

        UiUtil.styleButton(this.generateButton);
        UiUtil.styleButton(this.newObjectButton);
        UiUtil.styleButton(this.removeObjectButton);
        UiUtil.styleButton(this.newMaterialButton);
        UiUtil.styleButton(this.selectEnvColorBtn);
        UiUtil.styleButton(this.changeMaterialProps);

        UiUtil.styleTextField(this.globRayBounceCount);
        UiUtil.styleTextField(this.workerCount);
        UiUtil.styleTextField(this.imageWidth);
        UiUtil.styleTextField(this.imageHeight);
        UiUtil.styleTextField(this.cameraFov);

        UiUtil.styleComboBoc(objectTypeSelect);
        UiUtil.styleComboBoc(objectMaterialSelect);
        UiUtil.styleComboBoc(materialSettingsSelect);


        this.workerCount.addActionListener(e -> {
            Main.maxWorkerCount = this.workerCount.intValue();
        });

        this.imageWidth.addActionListener(e -> {
            Main.graphicsSize.width = this.imageWidth.intValue();
        });

        this.imageHeight.addActionListener(e -> {
            Main.graphicsSize.height = this.imageHeight.intValue();
        });

        Main.materialRegister.addListener(objectMaterialSelect);
        Main.materialRegister.addListener(materialSettingsSelect);
        objectMaterialSelect.addActionListener(e -> {
            this.selectedObject.getRenderable().setMaterial((Material) objectMaterialSelect.getSelectedItem());
            this.repaintVisualization();
        });


        objectTypeSelect.addItem(ObjectTypes.EMPTY);
        objectTypeSelect.addItem(ObjectTypes.SPHERE);
        objectTypeSelect.addItem(ObjectTypes.TRIANGLE);
        objectTypeSelect.addItem(ObjectTypes.MESH);

        objectTypeSelect.addActionListener(e -> {
            if( selectedObject.getRenderable() != null &&
                    objectTypeSelect.getSelectedItem().equals(selectedObject.getRenderable().getType())){
                return;
            }
            switch ((ObjectTypes)objectTypeSelect.getSelectedItem()){
                case EMPTY:{
                    this.selectedObject.setObject(null);
                    break;
                }
                case SPHERE:{
                    this.selectedObject.setObject(new Sphere());
                    break;
                }
                case TRIANGLE:{
                    this.selectedObject.setObject(new Triangle());
                    break;
                }
                case MESH:{
                    this.selectedObject.setObject(new PolygonMesh());
                }
            }

            this.objectMaterialSelect.setEnabled((objectTypeSelect.getSelectedItem() != ObjectTypes.EMPTY));
            if(objectTypeSelect.getSelectedItem() != ObjectTypes.EMPTY){
                this.objectMaterialSelect.setSelectedItem(this.selectedObject.getRenderable().getMaterial());
            }
            setParamsPanel(this.getParamPanelForObject(this.selectedObject));
            refresh();
        });

        this.selectEnvColorBtn.setIcon(UiUtil.getButtonColorIcon(Main.environment.getBackgroundColor().toColor()));
        this.selectEnvColorBtn.addActionListener(e -> {
            Color c = JColorChooser.showDialog(this,
                    "Kolor tła środowiska",
                    Main.environment.getBackgroundColor().toColor()
            );
            if(c != null){
                Main.environment.setBackgroundColor(new Vec3(c));
                this.selectEnvColorBtn.setIcon(UiUtil.getButtonColorIcon(c));
            }
        });

        this.globRayBounceCount.addActionListener(e -> {
            Main.environment.setRayMaxDepth(this.globRayBounceCount.intValue());
        });

        this.applyFilter.addActionListener(e -> {
            Main.applyFilter = this.applyFilter.isSelected();
        });

        this.cameraFov.addActionListener(e -> {
            Main.camera.setFov(this.cameraFov.intValue());
        });

        this.tabbedPane1.addChangeListener(e -> {
            this.globRayBounceCount.setText(Main.environment.rayMaxDepth);
            this.workerCount.setText(Main.maxWorkerCount);
            this.imageWidth.setText(Main.graphicsSize.width);
            this.imageHeight.setText(Main.graphicsSize.height);
            this.applyFilter.setSelected(Main.applyFilter);
            this.cameraFov.setText((int)Main.camera.getFov());
        });

        this.updateTree();

    }

    private ISettingsPanel getParamPanelForObject(EnvironmentObject object){
        if(object == null || object.getRenderable() == null || object.getRenderable().getType().equals(ObjectTypes.EMPTY)){
            return null;
        }
        if(object.getRenderable().getType().equals(ObjectTypes.SPHERE)){
            return this.sphereParamsPanel;
        }
        if(object.getRenderable().getType().equals(ObjectTypes.TRIANGLE)){
            return this.triangleParamsPanel;
        }
        if(object.getRenderable().getType().equals(ObjectTypes.MESH)){
            return this.meshParamsPanel;
        }
        return null;
    }

    public void refresh(){
        this.repaint();
    }

    public void addNewObject(){
        EnvironmentObject e = new EnvironmentObject();
        Main.environment.add(e);
        this.updateTree();
        this.selectObject(e);
    }

    public void removeObject(){
        Main.environment.remove(this.selectedObject);
        this.selectObject(null);
        this.updateTree();
        this.selectObject(null);
    }

    public void updateTree(){
        this.treeRoot.removeAllChildren();
        this.treeModel.reload();
        for(EnvironmentObject e: Main.environment.getRenderables()){

            this.treeModel.insertNodeInto(
                    new DefaultMutableTreeNode(e), this.treeRoot,
                    this.treeRoot.getChildCount()
            );
        }
        if(this.treeRoot.getChildCount() > 0){
            this.objectSelectionTree.scrollPathToVisible(new TreePath(this.treeRoot.getFirstLeaf().getPath()));
        }

        refresh();
    }

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode selected = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
        Object userObject = selected.getUserObject();
        if(userObject instanceof EnvironmentObject){
            this.selectObject((EnvironmentObject) userObject);
        }else{
            this.selectObject(null);
        }

    }

    public void selectObject(EnvironmentObject o){
        this.selectedObject = o;
        this.objectTypeSelect.setEnabled((o != null));
        this.objectMaterialSelect.setEnabled((o != null && o.getRenderable() != null));
        this.removeObjectButton.setVisible((o != null));
        SceneVisualizationPanel panelTop = (SceneVisualizationPanel) this.sceneVisualizationPanelTop;
        SceneVisualizationPanel panelSide = (SceneVisualizationPanel) this.sceneVisualizationPanelSide;
        panelTop.setSelectedObject(this.selectedObject);
        panelSide.setSelectedObject(this.selectedObject);
        if(o != null){
            if(this.selectedObject.getRenderable() != null){
                this.objectTypeSelect.setSelectedItem(this.selectedObject.getRenderable().getType());
                this.objectMaterialSelect.setSelectedItem(this.selectedObject.getRenderable().getMaterial());
            }else{
                this.objectTypeSelect.setSelectedItem(ObjectTypes.EMPTY);
            }
        }
        setParamsPanel(this.getParamPanelForObject(o));
        this.sceneVisualizationPanelSide.repaint();
        this.sceneVisualizationPanelTop.repaint();

    }

    public void repaintVisualization(){
        this.sceneVisualizationPanelSide.repaint();
        this.sceneVisualizationPanelTop.repaint();
    }

    private void setParamsPanel(ISettingsPanel panel){
        this.specificSettingsPannel.removeAll();
        if(panel != null){
            this.specificSettingsPannel.add((JPanel)panel);
            panel.setBoundObject(this.selectedObject);
        }
        this.specificSettingsPannel.validate();
        this.specificSettingsPannel.repaint();
    }

    private void createUIComponents() {
        treeRoot = new DefaultMutableTreeNode("/");
        this.treeModel = new DefaultTreeModel(treeRoot);
        this.objectSelectionTree = new JTree(this.treeModel);
        this.objectSelectionTree.addTreeSelectionListener(this);
        this.objectSelectionTree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        this.sceneVisualizationPanelTop = new SceneVisualizationPanelTop();
        this.sceneVisualizationPanelSide = new SceneVisualizationPanelSide();
        this.materialVisualization = new MaterialVisualizationPanel();


    }
}
