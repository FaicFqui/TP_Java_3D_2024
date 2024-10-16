import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class World {
    private ArrayList<Aeroport> list;

    public World(String csvPath) throws FileNotFoundException {

        String line = "";
        this.list = new ArrayList<>();

        // Lire le fichier en UTF-8
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath, StandardCharsets.UTF_8))) {
            // Sauter l'entête
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Séparer la ligne en utilisant la virgule comme délimiteur tout en prenant en compte les guillemets dans les coordonnées
                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Vérifier qu'il y a assez de colonnes dans la ligne
                if (fields.length > 11) {
                    // Extraire les champs d'intérêt
                    String name = fields[2];  // Nom de l'aéroport
                    String country = fields[5];  // iso_country
                    String iataCode = fields[9]; // iata_code
                    String coordinates = fields[11]; // Coordonnées
                    String type = fields[1];

                    // Si le code IATA est manquant, lui assigner "N/A"
                    if (iataCode == null || iataCode.isEmpty()) { // le pb se pose pas ici car tous les larges ont un IATA
                        iataCode = "N/A"; // Ou ignorer l'aéroport avec continue
                        //System.out.println("Code IATA manquant pour l'aéroport: " + name);
                    }

                    if (type.equals("large_airport") || type.equals("medium_airport")) {
                        String[] latLong = coordinates.replace("\"", "").split(", ");

                        // Vérification du tableau latLong
                        if (latLong.length < 2) {
                            System.out.println("Coordonnées manquantes ou mal formées pour l'aéroport: " + name);
                            continue; // Passer à l'aéroport suivant si les coordonnées sont invalides
                        }

                        try {
                            double latitude = Double.parseDouble(latLong[1]);
                            double longitude = Double.parseDouble(latLong[0]);

                            Aeroport aeroport = new Aeroport(name, country, iataCode, latitude, longitude, type);
                            this.list.add(aeroport);
                        } catch (NumberFormatException e) {
                            System.out.println("Format de coordonnées invalide pour l'aéroport: " + name);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Afficher les informations des aéroports
        /*for (Aeroport airport : this.list) {
            System.out.println(airport);
        }*/

    }
    public ArrayList<Aeroport> getList() {
        return this.list;
    }


    public double distance(double lat1, double lon1, double lat2, double lon2) { // distance réélle
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double latDifference = lat2 - lat1;
        double lonDifference = lon2 - lon1;
        // Calcul de la norme de proximité
        double latAverage = (lat1 + lat2) / 2.0;
        double norme_proximite = Math.pow(latDifference, 2) +
                Math.pow(lonDifference * Math.cos(latAverage), 2);


        final double rayon_terre = 6371.0; // Rayon de la Terre en km

        // Conversion de la norme en kilomètres
        return Math.sqrt(norme_proximite) * rayon_terre;

    }

    // Recherche l'aéroport par code IATA
    public Aeroport findByCode(String code) {
        for (Aeroport aeroport : this.list) {
            if (aeroport.getIATA().equals(code)) {
                return aeroport;
            }
        }
        return null;
    }

    // Recherche l'aéroport le plus proche d'une position donnée
    public Aeroport findNearestAirport(double lon, double lat) {
        Aeroport nearest = null;
        double Distance_min = Double.MAX_VALUE;//on commene la comparaison avec le plus grand double en java
        //Aeroport reference = new Aeroport("Reference", "N/A", "N/A", latitude, longitude, "N/A"); si calcul avec aeroport.calcuDistance
        for (Aeroport aeroport : this.list) {
            //double distance = aeroport.calculDistance(reference);
            double distance = distance(lat, lon, aeroport.getLatitude(), aeroport.getLongitude());
            if (distance < Distance_min) {
                Distance_min = distance;
                nearest = aeroport;
            }
        }

        return nearest;
    }

}
