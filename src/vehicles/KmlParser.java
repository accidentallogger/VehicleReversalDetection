package vehicles;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class KmlParser {

    public static void parseCoordinates(String filepath, String outputpath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath));
             PrintWriter writer = new PrintWriter(new FileWriter(outputpath))) {

            writer.println("id,Points,Time,Latitude,Longitude,Direction");

            String line;
            boolean inCoordinatesTag = false;
            StringBuilder coordinatesData = new StringBuilder();

            int id = 1;
            LocalTime time = LocalTime.now();
            
            while ((line = reader.readLine()) != null) {
                if (line.contains("<coordinates>")) {
                    inCoordinatesTag = true;
                    int start = line.indexOf("<coordinates>") + "<coordinates>".length();
                    int end = line.indexOf("</coordinates>");

                    if (end != -1) {
                        // Tag is on a single line
                        String coord = line.substring(start, end).trim();
                        writeCoordinates(coord, writer, id,time);
                        id += coord.split("\\s+").length;
                        inCoordinatesTag = false;
                        
                        
                    } else {
                        coordinatesData.append(line.substring(start).trim()).append(" ");
                    }
                } else if (line.contains("</coordinates>")) {
                    int end = line.indexOf("</coordinates>");
                    coordinatesData.append(line.substring(0, end).trim());
                    writeCoordinates(coordinatesData.toString(), writer, id,time);
                    id += coordinatesData.toString().split("\\s+").length;
                    coordinatesData.setLength(0);
                    inCoordinatesTag = false;
                    
                } else if (inCoordinatesTag) {
                    coordinatesData.append(line.trim()).append(" ");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeCoordinates(String coordText, PrintWriter writer, int startId, LocalTime startTime) {
        String[] coords = coordText.trim().split("\\s+");
        int index = 0;
        LocalTime time = startTime; // Copy to a local variable to mutate
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        for (String coord : coords) {
            String[] parts = coord.split(",");
            if (parts.length >= 2) {
                String longitude = parts[0];
                String latitude = parts[1];
                int id = startId + index;
                String strTime = time.format(formatter);
                
                writer.printf("%d,point %d,%s,%s,%s,Forward\n", id, id,strTime, latitude, longitude);
                
                time = time.plusMinutes(1); // Increment time by 1 minute
                index++;
            }
        }
    }

}
