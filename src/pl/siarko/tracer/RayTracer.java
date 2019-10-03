package pl.siarko.tracer;

import pl.siarko.tracer.environment.Environment;
import pl.siarko.tracer.environment.EnvironmentObject;
import pl.siarko.tracer.material.Material;
import pl.siarko.tracer.algorithm.IntersectionTestResult;
import pl.siarko.tracer.objects.IRenderable;
import pl.siarko.tracer.util.Util;
import pl.siarko.tracer.vec.Vec3;

import java.awt.*;

/**
 * Created by SiarkoWodór on 29.10.2018.
 * Klasa algorytmu Ray tracingu
 */
public class RayTracer {

    private Environment environment;
    private Camera camera;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    //publiczna metoda trace - wrapper do prywatnej metody
    public Color trace(int x, int y){
        Vec3 rayDirection = this.camera.getDirectionVector(x, y).normalize();
        return this.trace(new Vec3(), rayDirection, 0).toColor();
    }

    //rekursywna metoda trace - generuje kolor piksela
    private Vec3 trace(Vec3 rayOrigin, Vec3 rayDirection, int depth){

        float tNear = Float.POSITIVE_INFINITY;
        IRenderable intersectedPrimitive = null;
        IntersectionTestResult intersectionTestResult = null;

        //ustalenie najbliższego kolidującego z promieniem obiektu na scenie
        for (EnvironmentObject o :this.environment.getRenderables()){
            IRenderable renderable = o.getRenderable();
            if(renderable == null){continue;}
            IntersectionTestResult result = renderable.intersect(rayOrigin, rayDirection);
            if(!result.getResult()){continue;}
            if(result.getDistance() < tNear){
                tNear = result.getDistance();
                intersectionTestResult = result;
                intersectedPrimitive = renderable;
            }
        }

        //brak przeszkody - kolor piksela = kolor środowiska
        if(intersectedPrimitive == null){ return this.environment.getBackgroundColor();}

        Vec3 surfaceColor = new Vec3();
        //punkt zderzenia
        Vec3 hitPoint = rayOrigin.add(rayDirection.multi(tNear));
        intersectionTestResult.setHitPoint(hitPoint);
        //pobranie wektora normalnego powierzchni zderzenia
        Vec3 hitNormal = intersectedPrimitive.getNormalAtPoint(intersectionTestResult);

        float offset = (float)1e-4;
        boolean isInside = false;

        if(rayDirection.dot(hitNormal) > 0){
            hitNormal.inverse(true);
            isInside = true;
        }

        //pobranie materiału obiektu
        Material primitiveMaterial = intersectedPrimitive.getMaterial();
        boolean translucent = (primitiveMaterial.getTransparency() > 0 || primitiveMaterial.getReflectionIndex() > 0);

        //czy obiekt jest przezroczysty
        if(translucent && depth < this.environment.rayMaxDepth){
            float facingRatio = -rayDirection.dot(hitNormal);
            float fressnel = Util.mix((float)Math.pow((double)(1f-facingRatio), 3), 1, 0.1f);

            Vec3 reflectionDirection = rayDirection.sub(hitNormal.multi(2f * rayDirection.dot(hitNormal)));
            reflectionDirection.normalize();

            Vec3 reflection = this.trace(hitPoint.add(hitNormal.multi(offset)), reflectionDirection, depth+1);
            Vec3 refraction = new Vec3();

            if(primitiveMaterial.getTransparency() > 0){
                float ior = 1.1f;
                float eta = ((isInside)? ior : 1f/ior);
                float cosInverse = -hitNormal.dot(rayDirection);
                float k = 1f - eta*eta*(1f-cosInverse*cosInverse);
                Vec3 refractionDirection = rayDirection.multi(eta)
                        .add(hitNormal.multi(eta*cosInverse-(float)(Math.sqrt(k)) ));
                refractionDirection.normalize();
                //rekursywne śledzenie promienia
                refraction = this.trace(hitPoint.sub(hitNormal.multi(offset)), refractionDirection, depth+1);
            }

            /*sc = (reflection * fressnel + refraction * (1-fressnel) * transparency) * surfaceColor*/

            surfaceColor = new Vec3(
                    reflection.multi(fressnel).add(
                    refraction.multi(1f-fressnel).multi(primitiveMaterial.getTransparency())
            )).multi(primitiveMaterial.getSurfaceColor());


        }else{ //obiekt nie jest przezroczysty
            for (EnvironmentObject o1 :this.environment.getRenderables()) {
                IRenderable r1 = o1.getRenderable();
                if(r1 == null){continue;}
                Vec3 emission = r1.getMaterial().getRenderableEmission();
                if(emission.x + emission.y + emission.z > 0){
                    Vec3 transmission = new Vec3(1);
                    Vec3 lightDirection = r1.getLightDirection(hitPoint);
                    //sprawdzenie ścieżki do źródeł światła (czy w tym punkcie pada cień)
                    for (EnvironmentObject o2 :this.environment.getRenderables()) {
                        IRenderable r2 = o2.getRenderable();
                        if(r2 == null){continue;}
                        if(!r1.equals(r2)){
                            IntersectionTestResult result1 = r2.intersect(
                                    hitPoint.add(hitNormal.multi(offset)),
                                    lightDirection
                            );
                            if(result1.getResult()){
                                transmission = new Vec3(0);
                                break;
                            }

                        }
                    }
                    surfaceColor.add(
                            intersectedPrimitive.getMaterial().getSurfaceColor()
                            .multi(transmission)
                            .multi(Math.max(0, hitNormal.dot(lightDirection)))
                            .multi(r1.getMaterial().getRenderableEmission())
                            , true);
                }
            }
        }

        return surfaceColor.add(intersectedPrimitive.getMaterial().getRenderableEmission());

    }


}
