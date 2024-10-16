import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Objects;

public class Earth {

    public Sphere sphere;
    private Rotate ry;
    private ArrayList<Sphere> yellowSphere;

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

        Rotate rotateX = new Rotate(25, Rotate.X_AXIS); // Pour centrer la France
        sphere.getTransforms().addAll(rotateX);


    }

    public Sphere creatSphere(double rayon){
        this.sphere = new Sphere(rayon);
        return sphere;
    }

    public void displayYellowSphere(){
        this.creatSphere(5).setMaterial(new PhongMaterial(Color.YELLOW));

    }


    public Sphere getEarth(){
        return sphere;
    }





    }
