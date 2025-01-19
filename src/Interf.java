import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.*;
import javafx.stage.Stage;
import javafx.util.Duration;
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


        terre.addEventHandler(MouseEvent.ANY, event -> {
            // Vérifier si c'est un clic droit
            if (event.getButton() == MouseButton.SECONDARY && event.getEventType() == MouseEvent.MOUSE_CLICKED) {

                // Obtenir l'élément sur lequel l'événement a été déclenché (le globe)
                PickResult pickResult = event.getPickResult();

                // Vérifier que le pickResult n'est pas null et que le nœud intersecté est la Terre
                if (pickResult.getIntersectedNode() != null && pickResult.getIntersectedNode() == terre.getEarth()) {
                    // Récupérer les coordonnées locales du point cliqué
                    Point3D localCoordinates = pickResult.getIntersectedPoint();  // Coordonnées locales du point cliqué
                    Point2D point2D = pickResult.getIntersectedTexCoord(); // Coordonnées 2D de la texture

                    // Conversion des coordonnées de texture en latitude et longitude
                    double[] geoCoords = getGeographicalCoordinates(point2D);
                    double longitude = geoCoords[1];
                    double latitude = geoCoords[0];

                    if (geoCoords != null) {
                        System.out.println("PickResult: " + pickResult);

                        System.out.println("Latitude cliquée: " + latitude + ", Longitude cliquée: " + longitude);

                        // Recherche de l'aéroport le plus proche basé sur la latitude et longitude
                        Aeroport plusProche = w.findNearestAirport(longitude, latitude);
                        System.out.println("L'aéroport le plus proche de l'endroit cliqué : " + plusProche);
                        System.out.println("Aéroport lat = " + plusProche.getLatitude() + " long = " + plusProche.getLongitude());

                        // Conversion des coordonnées de l'aéroport en coordonnées locales (XYZ)
                        double[] vecteur = convertLatLongToLocalXYZ(plusProche.getLatitude(), plusProche.getLongitude(), 300);
                        double x = vecteur[0];
                        double y = vecteur[1];
                        double z = vecteur[2];

                        // Appliquer la transformation de rotation inverse pour corriger la position de la petite sphère
                        Point3D point3D = new Point3D(x, y, z);

                        System.out.println("x_aero_cal : " + x + " y_aero_cal: " + y + " z_aero_cal : " + z);

                        // Afficher la petite sphère rouge aux nouvelles coordonnées locales sur le globe
                        terre.displayRedSphere(x, y);
                    }
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
        double z = -radius * Math.cos(lat_rad)*Math.sin(long_rad);

        return new double[]{x, y, z};
    }

    private double[] getGeographicalCoordinates(Point2D localCoordinates) {
        double latitude = 180 * (0.5 - localCoordinates.getY());
        double longitude = 360 * (localCoordinates.getX()-0.5);
        return new double[]{latitude, longitude};
    }

    public static void main(String[] args) {
        launch(args);
    }


}
