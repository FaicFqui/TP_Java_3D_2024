import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
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
                Point2D point2D = pickResult.getIntersectedTexCoord();
                double[] geoCoords = getGeographicalCoordinates(point2D);
                double longitude = geoCoords[1];
                double latitude = geoCoords[0];
                if (geoCoords != null) {
                    System.out.println("picresul " + pickResult);

                    System.out.println("Latitude_cliquée: " + geoCoords[0] + ", Longitude_cliquée: " + geoCoords[1]);
                    //System.out.println("X_local: " + localCoordinates.getX() + ", Y_local: " + localCoordinates.getY() + " Z_local: " + localCoordinates.getZ());

                    Aeroport plus_proche = w.findNearestAirport(longitude, latitude);
                    System.out.println("L'aeroport le plus proche de l'endroit cilqué : " + plus_proche);
                    System.out.println("aeroport lat = "+ plus_proche.getLatitude() + " long = "+plus_proche.getLongitude());


                    double[] vecteur = convertLatLongToLocalXYZ(plus_proche.getLatitude(), plus_proche.getLongitude(), 300);
                    double x = vecteur[0];
                    double y = vecteur[1]/1.5;
                    double z = -300;

                   // Appliquer la transformation de rotation inverse pour corriger la position de la petite sphère
                    Point3D point3D = new Point3D(x, y, z);

                    /*Point3D transformedPoint = terre.ry.transform(point3D); // Applique la transformation
                    double newX = transformedPoint.getX();
                    double newY = transformedPoint.getY();
                    double newZ = transformedPoint.getZ();*/
                    // Transformer les coordonnées locales en coordonnées globales (après rotation)
                    //Point3D newLocalCoordinates = terre.getEarth().sceneToLocal(point3D);
                    //System.out.println("x_local_apres : "+ point3D.getX() +" y_local_apres: "+point3D.getY()+ " z_local_apres : " + point3D.getZ());

                    // Maintenant, transformer les coordonnées de la scène en coordonnées locales pour placer la petite sphère
                    //Point3D newLocalCoordinates = terre.getEarth().sceneToLocal(sceneCoordinates);
                    System.out.println("x_aero_cal : "+ x +" y_aero_cal: "+y+ " z_aero_cal : " + z);

                    Point3D p = new Point3D(-34.94683061221838, -113.3332354661558, -275.56178394133974);
                    // Convertir les coordonnées locales en coordonnées de scène
                    Point3D globalCoordinates = terre.getEarth().sceneToLocal(point3D);
                    //System.out.println("x_local_cal : "+ point3D.getX() +" y_local_cal: "+point3D.getY()+ " z_local_cal : " + point3D.getZ());
                    System.out.println("x_global : "+ globalCoordinates.getX() +" y_global: "+globalCoordinates.getY()+ " z_global : "+ globalCoordinates.getZ());

                    // Appliquer la transformation inverse pour repasser dans le repère de la Terre
                    Point3D localAfterRotation = terre.getEarth().localToScene(globalCoordinates);
                    // Afficher la petite sphère rouge aux nouvelles coordonnées locales
                    terre.displayRedSphere(localAfterRotation.getX(),y, -300);

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
        double y = -(radius * Math.sin(lat_rad));
        double z = -radius * Math.cos(lat_rad)*Math.sin(long_rad);

        return new double[]{x, y, z};
    }

    private double[] getGeographicalCoordinates(Point2D localCoordinates) {
        // Normaliser les coordonnées locales pour obtenir un vecteur unitaire (rayon de la Terre)
        /*double normalizedX = localCoordinates.getX() / 300;
        double normalizedY = localCoordinates.getY() / 300;
        double normalizedZ = localCoordinates.getZ() / 300;


        // Convertir les coordonnées normalisées en un intervalle de 0 à 1
        double xNormalized = (normalizedX + 1) / 2;
        double yNormalized = (1 - normalizedY) / 2;

        // Calculer la longitude (-180° à +180°)
        double longitude = (xNormalized * 360 - 180); //   div par 3.2 : coef de corection empirique

        // Calculer la latitude (+90° à -90°)
        double latitude = yNormalized * 180 - 90;*/

        double latitude = 180 * (0.5 - localCoordinates.getY());
        double longitude = 360 * (localCoordinates.getX()-0.5);



        return new double[]{latitude, longitude};
    }

    public static void main(String[] args) {
        launch(args);
    }


}
