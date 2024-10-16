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



    public static void main(String[] args) {
        launch(args);
    }


}
