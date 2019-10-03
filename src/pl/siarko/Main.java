package pl.siarko;

import pl.siarko.frame.Frame;
import pl.siarko.frame.SettingsFrame;
import pl.siarko.frame.filters.MedianFilter;
import pl.siarko.tracer.Camera;
import pl.siarko.tracer.RayTracer;
import pl.siarko.tracer.environment.Environment;
import pl.siarko.tracer.environment.EnvironmentObject;
import pl.siarko.tracer.material.MaterialRegister;
import pl.siarko.tracer.objects.primitives.Sphere;
import pl.siarko.tracer.vec.Vec3;
import pl.siarko.util.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static SettingsFrame settingsFrame;
    public static Frame mainFrame;
    public static Environment environment;
    public static Camera camera;
    public static RayTracer raytracer;
    public static MaterialRegister materialRegister;

    //liczba aktywnych wątków renderujących
    private static int workersActive = 0;
    //liczba aktywowanych wątków renderujących przy rozpoczęciu renderowania
    public static int maxWorkerCount = 1;

    //czy zastosować filtr medianowy na gotowej grafice
    public static boolean applyFilter = false;

    //rozmiar generowanej grafiki
    public static Dimension graphicsSize;



    public Main(){

        //pobierz domyślny rozmiar z klasy Constants
        Main.graphicsSize = new Dimension(Constants.WIDTH, Constants.HEIGHT);

        //inicjalizacja środowiska
        environment = new Environment();
        environment.setBackgroundColor(new Vec3(new Color(15, 157, 200)));

        //inicjalizacja rejestru materiałów
        materialRegister = new MaterialRegister();


        //stworzenie i dodanie domyślnej sfery oświetleniowej do środowiska
        EnvironmentObject o2 = new EnvironmentObject();
        Sphere s2 = new Sphere();
        s2.setCenterY(10);
        s2.setR(1);
        s2.setMaterial(MaterialRegister.LIGHT);
        o2.setObject(s2);
        environment.add(o2);


        //utworzenie nowej instancji algorytmu
        raytracer = new RayTracer();

        //inicjalizacja okna ustawień
        settingsFrame = new SettingsFrame("Ustawienia", Constants.SETTINGS_WIDTH, Constants.SETTINGS_HEIGHT);
        //inicjalizacja okna obrazu
        mainFrame = new Frame("Ray tracer", Constants.WIDTH, Constants.HEIGHT);

        //inicjalizacja kamery
        camera = new Camera(50, Constants.WIDTH, Constants.HEIGHT);


        //przypisanie kamery i środowiska do instancji raytracera
        raytracer.setCamera(camera);
        raytracer.setEnvironment(environment);

        //rejstracja filtra medianowego
        mainFrame.drawing.registerFilter(new MedianFilter());

        //wyświetlenie ramki ustawień
        settingsFrame.setVisible(true);
    }

    //metoda generująca obraz
    public static void render(){

        //ustawienie rozmiaru okna z obrazem
        Main.mainFrame.setSize(Main.graphicsSize);
        Main.camera.resize(Main.graphicsSize);

        Main.workersActive = Main.maxWorkerCount;
        Main.mainFrame.setVisible(true);
        Main.mainFrame.setTitle("Renderowanie");

        long start = System.currentTimeMillis();
        int h = Main.graphicsSize.height/Main.workersActive;

        //utworzenie buforów obrazu
        //tworzony jest jeden bufor dla każdego wątku
        BufferedImage[] buffers = new BufferedImage[Main.maxWorkerCount];

        /*
            -- Utworzenie wątków --
            + każdy wątek pracuje na swojej części obrazu
            + obraz jest dzielony w pionie na tyle części ile jest wątków, następnie każda z wygenerowanych części
            jest doklejana do finalnego obrazu
        */
        for(int i = 0; i < Main.workersActive; i++){
            int finalI = i;
            buffers[i] = new BufferedImage(Main.graphicsSize.width, h, BufferedImage.TYPE_INT_ARGB);
            Thread t = new Thread(() -> {
                Graphics2D g = buffers[finalI].createGraphics();
                for(int y = 0; y < h; y++){
                    for(int x = 0; x < Main.graphicsSize.width; x++){
                        //wygenerowanie koloru dla piksela w punkcie x,y
                        Color c = raytracer.trace(x,y+(finalI*h));
                        g.setColor(c);
                        //narysowanie piksela na buforze za pomocą linii o długości 1 piksela
                        g.drawLine(x,y,x,y);
                    }
                }
                //uwolnienie kontekstu grafiki
                g.dispose();

                mainFrame.drawing.drawImage(buffers[finalI], 0, finalI*h);
                System.out.println("Thread ["+(finalI+1)+"] finished in: "+(System.currentTimeMillis()-start)+"ms");
                Main.workersActive--;
                if(Main.workersActive == 0){
                    Main.mainFrame.setTitle("Obraz gotowy");
                    System.out.println("==== Render ukonczony w czasie "+(System.currentTimeMillis()-start)+"ms ====");

                    if(Main.applyFilter){
                        Main.mainFrame.drawing.applyFilters();
                    }

                    //odświeżenie okna z obrazem
                    Main.mainFrame.repaint();
                }
            });
            t.start();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
