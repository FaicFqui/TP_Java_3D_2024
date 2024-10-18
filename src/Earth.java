import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Objects;

public class Earth extends Group{

    public Sphere sphere;
    private Rotate ry;
    private ArrayList<Sphere> yellowSphere;

    public Group root;

    public Earth(){
        this.sphere = new Sphere(300);


        this.sphere.setTranslateX(0);
        this.sphere.setTranslateY(0);
        this.sphere.setTranslateZ(0);

        PhongMaterial earthMaterial = new PhongMaterial();
        try {

            Image earthImage = new Image(Objects.requireNonNull(getClass().getResource("terre_nasa.png")).toExternalForm(), true);

            earthMaterial.setDiffuseMap(earthImage);
            sphere.setMaterial(earthMaterial);


        } catch (NullPointerException e) {
            System.out.println("L'image n'a pas pu être chargée. Chemin incorrect ou image manquante.");
        }

        /*Rotate rotateX = new Rotate(25, Rotate.X_AXIS); // Pour centrer la France
        sphere.getTransforms().addAll(rotateX);*/

        this.getChildren().add(sphere);

    }

    public Sphere creatSphere(double rayon){
        this.sphere = new Sphere(rayon);
        return sphere;
    }

    public void displayRedSphere(double x, double y, double z){

        Sphere sphere_rouge = new Sphere(2);
        sphere_rouge.setMaterial(new PhongMaterial(Color.RED));

        // Appliquer la position calculée à la sphère rouge
        sphere_rouge.setTranslateX(x);
        sphere_rouge.setTranslateY(y);
        sphere_rouge.setTranslateZ(z);

        this.getChildren().add(sphere_rouge);

    }


    public Sphere getEarth(){
        return sphere;
    }





    }
