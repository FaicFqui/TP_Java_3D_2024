import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.transform.Scale;
import javafx.scene.input.PickResult;


import java.io.FileNotFoundException;
import java.util.Objects;

public class Interf extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        Earth terre = new Earth();
        Group root = new Group();
        root.getChildren().addAll(terre.getEarth());
        Scene scene = new Scene(root, 700, 700, true);

        World w = new World("data/airport-codes_no_comma.csv");
        ///////////////////////**************test de world et aeroport**********************////////////
        System.out.println("found " + w.getList().size()+ " Airports");
        Aeroport parisNearest = w.findNearestAirport(2.316, 48.866);
        // Trouver un aéroport par son code IATA (CDG)
        Aeroport cdg = w.findByCode("CDG");
        // Calculer la distance entre Paris (48.866, 2.316) et l'aéroport le plus proche trouvé
        double distanceParis = w.distance(48.866, 2.316, parisNearest.getLatitude(), parisNearest.getLongitude());
        // Calculer la distance entre Paris (48.866, 2.316) et CDG
        double distanceCDG = w.distance(48.866, 2.316, cdg.getLatitude(), cdg.getLongitude());

        // Afficher les résultats
        System.out.println("Aéroport le plus proche de Paris : " + parisNearest);
        System.out.println("Distance entre Paris et l'aéroport le plus proche : " + distanceParis + " km");

        System.out.println("Aéroport CDG : " + cdg);
        System.out.println("Distance entre Paris et CDG : " + distanceCDG + " km");




        terre.getEarth().setOnMouseClicked(event -> {
            PickResult pickResult = event.getPickResult();
            if (pickResult != null && pickResult.getIntersectedNode() == terre.getEarth()) {
                Point3D localCoordinates = pickResult.getIntersectedPoint(); // Coordonnées locales du point cliqué
                double[] geoCoords = getGeographicalCoordinates(localCoordinates, terre.getEarth());
                double longitude = geoCoords[1];
                double latitude = geoCoords[0];
                if (geoCoords != null) {
                    System.out.println("Latitude: " + geoCoords[0] + ", Longitude: " + geoCoords[1]);
                    //System.out.println("X: " + localCoordinates.getX()/300 + ", Y: " + localCoordinates.getY()/300);

                    Aeroport plus_proche = w.findNearestAirport(longitude, latitude);
                    System.out.println("L'aeroport le plus proche de l'endroit cilqué : " + plus_proche);

                    // Convertir latitude et longitude en radians
                    double latRad = Math.toRadians(latitude);
                    double longRad = Math.toRadians(longitude);

                    // Calculer la position de la petite sphère (rayon de 5 pixels)
                    double smallSphereRadius = 5;
                    double r = 300; // rayon de la sphère principale
                    double x = r * Math.cos(latRad) * Math.cos(longRad);
                    double y = r * Math.sin(latRad);
                    double z = -r * Math.cos(latRad) * Math.sin(longRad);

                    Sphere smallSphere = new Sphere(20);
                    smallSphere.setTranslateX(300 + x); // décaler par rapport à la sphère principale
                    smallSphere.setTranslateY(300 + y); // ajuster Y pour correspondre au système de coordonnées
                    smallSphere.setTranslateZ(300 + z); // décaler par rapport à la sphère principale

                    // Changer la couleur de la petite sphère
                    smallSphere.setMaterial(new PhongMaterial(Color.RED));
                    root.getChildren().add(smallSphere);
                }
            }
        });







        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);
        scene.setCamera(camera);

        primaryStage.setTitle("Globe terrestre interactif");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private double[] getGeographicalCoordinates(Point3D localCoordinates, Sphere earth) {
        // Normaliser les coordonnées locales pour obtenir un vecteur unitaire (rayon de la Terre)
        double normalizedX = localCoordinates.getX() / 300;
        double normalizedY = localCoordinates.getY() / 300;
        double normalizedZ = localCoordinates.getZ() / 300;


        // Convertir les coordonnées normalisées en un intervalle de 0 à 1
        double xNormalized = (normalizedX + 1) / 2;
        double yNormalized = (1 - normalizedY) / 2;

        // Calculer la longitude (-180° à +180°)
        double longitude = (xNormalized * 360 - 180)/3.2; //   div par 3.2 : coef de corection empirique

        // Calculer la latitude (+90° à -90°)
        double latitude = yNormalized * 180 - 90;

        return new double[]{latitude, longitude};
    }

    public static void main(String[] args) {
        launch(args);
    }


}
