package pl.siarko.tracer.objects;

import pl.siarko.tracer.algorithm.IntersectionTestResult;
import pl.siarko.tracer.material.Material;
import pl.siarko.tracer.vec.Vec3;

public interface IRenderable extends ISceneObject{

    Vec3 getCenter();

    IntersectionTestResult intersect(Vec3 rayOrigin, Vec3 rayDirection);

    Vec3 getNormalAtPoint(IntersectionTestResult hitPoint);

    Vec3 getLightDirection(Vec3 hitPoint);

    void setMaterial(Material m);

    Material getMaterial();
}
