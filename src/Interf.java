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


        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);




        World w = new World("data/airport-codes_no_comma.csv");


        terre.getEarth().setOnMouseClicked(event -> {
            PickResult pickResult = event.getPickResult();
            if (pickResult != null && pickResult.getIntersectedNode() == terre.getEarth()) {
                Point3D localCoordinates = pickResult.getIntersectedPoint(); // Coordonnées locales du point cliqué
                double[] geoCoords = getGeographicalCoordinates(localCoordinates, terre.getEarth());
                double longitude = geoCoords[1];
                double latitude = geoCoords[0];
                if (geoCoords != null) {
                    System.out.println("Latitude_cliquée: " + geoCoords[0] + ", Longitude_cliquée: " + geoCoords[1]);
                    System.out.println("X_cliqué: " + localCoordinates.getX() + ", Y_cliqué: " + localCoordinates.getY() + " Z_cliqué: " + localCoordinates.getZ());

                    Aeroport plus_proche = w.findNearestAirport(longitude, latitude);
                    System.out.println("L'aeroport le plus proche de l'endroit cilqué : " + plus_proche);
                    System.out.println("aeroport lat = "+ plus_proche.getLatitude() + " long = "+plus_proche.getLongitude());


                    double[] vecteur = convertLatLongToLocalXYZ(plus_proche.getLatitude(), plus_proche.getLongitude(), 300);
                    double x = vecteur[0];
                    double y = vecteur[1];
                    double z = vecteur[2];


                    System.out.println("x_calculé : "+ x +" y_calculé: "+y+ "z_calculé : " + z);

                    terre.displayRedSphere(x,y,-300);

                }
            }
        });
        Scene scene = new Scene(terre, 700, 700, true);
        scene.setCamera(camera);

        primaryStage.setTitle("Globe terrestre interactif");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static double[] convertLatLongToLocalXYZ(double latitude, double longitude, double radius) {

        double long_rad = Math.toRadians(longitude);
        double lat_rad = Math.toRadians(latitude);

        double x = radius * Math.cos(lat_rad) * Math.sin(long_rad);
        double y = -(radius * Math.sin(lat_rad))/1.5;
        double z = -radius * Math.cos(lat_rad) ;

        return new double[]{x, y, z};
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
