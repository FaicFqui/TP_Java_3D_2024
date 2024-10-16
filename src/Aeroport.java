public class Aeroport {
    private String Name, IATA, country, type;
    private double latitude, longitude;

    public Aeroport(String Name, String country, String IATA, double latitude, double longitude, String type){
        this.Name=Name;
        this.country=country;
        this.IATA=IATA;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    @Override
    public String toString(){
        //return ("Type: " + type + " ,Name : "+ Name + " ,Country: " + country + ", IATA Code: " + IATA + ", Latitude: " + latitude + ", Longitude: " + longitude);
        return ("Country: " + country+" / Name : "+ Name );
    }

    public String getIATA() {
        return IATA;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double calculDistance(Aeroport a) {
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(a.latitude);
        double lon2 = Math.toRadians(a.longitude);

        double latDifference = lat2 - lat1;
        double lonDifference = lon2 - lon1;
        double latAverage = (lat1 + lat2) / 2.0;

        double norme_proximite = Math.pow(latDifference, 2) +
                Math.pow(lonDifference * Math.cos(latAverage), 2);

        return norme_proximite;
    }


}
