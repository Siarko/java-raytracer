package pl.siarko.tracer.objects;

public enum ObjectTypes {
    EMPTY("Pusty"),
    SPHERE("Sfera"),
    TRIANGLE("Trójkąt"),
    MESH("Siatka");


    String name;
    ObjectTypes(String name){
        this.name = name;
    }

    public String toString(){
        return this.name;
    }

}
