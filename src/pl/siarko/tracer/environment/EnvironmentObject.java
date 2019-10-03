package pl.siarko.tracer.environment;

import pl.siarko.tracer.objects.IRenderable;

/*
* Obiekt reprezentujący dowolny obiekt dodany do sceny
* */
public class EnvironmentObject {

    IRenderable IRenderableObject;
    String name;

    public EnvironmentObject(){
        this.name = "Obiekt";
    }

    public void setObject(IRenderable obj){
        this.IRenderableObject = obj;
    }

    public IRenderable getRenderable(){
        return this.IRenderableObject;
    }

    public String toString(){
        String sufix = "";
        if(this.IRenderableObject != null){
            sufix = " ("+this.IRenderableObject.getType()+")";
        }else{
            sufix = " (PUSTY)";
        }
        return this.name+sufix;
    }

}
