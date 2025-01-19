import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Objects;

public class Earth extends Group{

    public Sphere sphere;
    public  Rotate ry;
    public ArrayList<Sphere> yellowSphere;

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

        this.ry = new Rotate(0, Rotate.Y_AXIS);
        sphere.getTransforms().addAll(ry);

        

       /*AnimationTimer timer = new AnimationTimer() {
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

        //ry = new Rotate(45, Rotate.Y_AXIS);
        //sphere.getTransforms().add(ry);

        this.getChildren().add(sphere);

    }



    public Sphere creatSphere(double rayon){
        this.sphere = new Sphere(rayon);
        return sphere;
    }

    public void displayRedSphere(double x, double y) {
        // Calcul initial des coordonnées XYZ sans rotation
        /*double[] initialCoords = convertLatLongToLocalXYZ(latitude, longitude, 300);
        Point3D IP = new Point3D(initialCoords[0], initialCoords[1], initialCoords[2]);

        Point3D RP = rotatePointY(IP,45);*/

        // Créer la sphère rouge et positionner aux coordonnées corrigées
        Sphere sphere_rouge = new Sphere(2);
        sphere_rouge.setMaterial(new PhongMaterial(Color.RED));
        sphere_rouge.setTranslateX(x);
        sphere_rouge.setTranslateY(y);
        sphere_rouge.setTranslateZ(-300);

        //System.out.println("x = "+ IP.getX()+" x' = "+ RP.getX());
        //System.out.println("z = "+ IP.getZ()+" z' = "+ RP.getZ());
        //System.out.println("x_fonc = "+ initialCoords[0]+" y' = "+ initialCoords[1]+ " z= "+initialCoords[2]);
        //System.out.println("x_3D = "+ IP.getX()+" y' = "+ IP.getY()+ " z= "+IP.getZ());

        // Ajouter la sphère rouge à la grande sphère
        this.getChildren().add(sphere_rouge);
    }

    public Point3D rotatePointY(Point3D point, double angleInDegrees) {
        double angleInRadians = Math.toRadians(angleInDegrees);

        double x = point.getX();
        double y = point.getY();
        double z = point.getZ();

        // Calcul du nouveau x et z après rotation
        double newX = Math.cos(angleInRadians) * x + Math.sin(angleInRadians) * (-300);
        double newY = y;
        double newZ = -Math.sin(angleInRadians) * x + Math.cos(angleInRadians) * (-300);

        return new Point3D(newX, newY, newZ);
    }



    public static double[] convertLatLongToLocalXYZ(double latitude, double longitude, double radius) {


        double long_rad = Math.toRadians(longitude);
        double lat_rad = Math.toRadians(latitude);

        double x = radius * Math.cos(lat_rad) * Math.sin(long_rad);
        double y = -(radius * Math.sin(lat_rad))/1.5;
        double z = -radius * Math.cos(lat_rad)*Math.sin(long_rad);

        return new double[]{x, y, z};
    }


    public Sphere getEarth(){
        return sphere;
    }





    }
