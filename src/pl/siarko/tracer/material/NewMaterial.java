package pl.siarko.tracer.material;

import pl.siarko.tracer.vec.Vec3;


/*
* Klasa obiektu abstrakcyjnego materiału
* jego właściwości mogą się zmieniać podczas działania programu
* */
public class NewMaterial implements Material {

    private float transparency, rf, lightIntensity = 1f;
    private Vec3 surfaceColor, emissionColor;
    private String name;

    public NewMaterial(){
        this.name = "Domyslny";
        this.transparency = 0;
        this.rf = 0;
        this.emissionColor = new Vec3();
        this.surfaceColor = new Vec3(0.5f, 0.5f, 0);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    public void setRf(float rf) {
        this.rf = rf;
    }

    public void setSurfaceColor(Vec3 surfaceColor) {
        this.surfaceColor = surfaceColor;
    }

    public void setEmissionColor(Vec3 emissionColor) {
        this.emissionColor = emissionColor;
    }

    @Override
    public float getTransparency() {
        return this.transparency;
    }

    @Override
    public float getReflectionIndex() {
        return this.rf;
    }

    @Override
    public Vec3 getSurfaceColor() {
        return this.surfaceColor;
    }

    @Override
    public Vec3 getEmissionColor() {
        return this.emissionColor;
    }

    public void setLightIntensity(float v){
        this.lightIntensity = v;
    }

    @Override
    public float getLightIntensity() {
        return this.lightIntensity;
    }

    @Override
    public Vec3 getRenderableEmission() {
        return this.getEmissionColor().multi(this.lightIntensity);
    }

    public String toString(){
        return this.name;
    }
}
