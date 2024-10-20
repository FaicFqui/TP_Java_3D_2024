import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
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
    public Rotate ry;
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

        Rotate rotateY = new Rotate(45, Rotate.Y_AXIS); // Pour centrer la France
        sphere.getTransforms().addAll(rotateY);


        //rotation de la terre autour de l'axe Y
        /*ry = new Rotate(0, Rotate.Y_AXIS);
        sphere.getTransforms().add(ry);

        AnimationTimer timer = new AnimationTimer() {
            private long startTime = -1;
            private static final double FULL_ROTATION = 360.0; // 360 degrés pour un tour complet
            private static final double DURATION = 15_000_000_000L; // 15 secondes en nanosecondes (15 * 10^9)
            @Override
            public void handle(long now) {
                if (startTime == -1) {
                    startTime = now; // Initialiser le temps de démarrage
                }

                long elapsedNanos = now - startTime;
                double fractionOfRotation = (double) elapsedNanos / DURATION;
                double angle = fractionOfRotation * FULL_ROTATION;

                // Appliquer l'angle de rotation
                ry.setAngle(angle % 360); // Assurer que l'angle reste entre 0 et 360 degrés

                // Repartir de 0 après 15 secondes
                if (elapsedNanos >= DURATION) {
                    startTime = now;
                }
            }
        };

        timer.start();*/

        this.getChildren().add(sphere);

    }

    public Sphere creatSphere(double rayon){
        this.sphere = new Sphere(rayon);
        return sphere;
    }

    public void displayRedSphere(double x, double y, double z){

        Sphere sphere_rouge = new Sphere(2);
        sphere_rouge.setMaterial(new PhongMaterial(Color.RED));

        //Point3D newLocalCoordinates = terre.getEarth().sceneToLocal(point3D);

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
