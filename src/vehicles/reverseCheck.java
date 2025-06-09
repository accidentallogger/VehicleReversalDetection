package vehicles;

import java.util.*;

public class reverseCheck {
//with vector conversion and angle measurement
    public static void detectReversal(LinkedList<vehicleTimeStamps> path) {
        if (path.size() < 3) {
            System.out.println("Not enough data points.");
            return;
        }

        for (int i = 0; i < path.size() - 2; i++) {

            vehicleTimeStamps p1 = path.get(i);
            vehicleTimeStamps p2 = path.get(i + 1);
            vehicleTimeStamps p3 = path.get(i + 2);
            double[] v1 = vectorBetween(p1, p2);
            double[] v2 = vectorBetween(p2, p3);

            normalize(v1);
            normalize(v2);

            double dot = v1[0] * v2[0] + v1[1] * v2[1];

            if (dot < -0.7) {  
                System.out.println("Reversal detected at point ID: " + p2.id);
            }
        }
    }

    private static double[] vectorBetween(vehicleTimeStamps a, vehicleTimeStamps b) {
        double dLat = b.coordinates.latitude - a.coordinates.latitude;
        double dLon = b.coordinates.longitude - a.coordinates.longitude;
        return new double[]{dLat, dLon};
    }

    private static void normalize(double[] vector) {
        double mag = Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1]);
        if (mag != 0) {
            vector[0] /= mag;
            vector[1] /= mag;
        }
    }
    
    
    
// checks for points that are close to each other using a sliding window approach.

        private static final int WINDOW_SIZE = 5;
        private static final double DISTANCE_THRESHOLD = 1.0; // meters

        public boolean detectReversal2(LinkedList<vehicleTimeStamps> path) {
            int len = path.size();
            if (len < 2 * WINDOW_SIZE) return false;

            for (int i = WINDOW_SIZE; i <= len - WINDOW_SIZE; i++) {
                // Create reversed previous list (W1)
                List<vehicleTimeStamps> prevlist = new ArrayList<>(path.subList(i - WINDOW_SIZE, i));
                Collections.reverse(prevlist);  // Reversing to simulate "return path"

                // Next list (W2)
                List<vehicleTimeStamps> nextlist = path.subList(i, i + WINDOW_SIZE);

                boolean allClose = true;

                for (int j = 0; j < WINDOW_SIZE; j++) {
                    double lat1 = prevlist.get(j).coordinates.latitude;
                    double lon1 = prevlist.get(j).coordinates.longitude;
                    double lat2 = nextlist.get(j).coordinates.latitude;
                    double lon2 = nextlist.get(j).coordinates.longitude;

                    double dist = speedCalc.PointToPointDistance(lat1, lon1, lat2, lon2);
                    if (dist > DISTANCE_THRESHOLD) {
                        allClose = false;
                        break;
                    }
                }

                if (allClose) {
                    System.out.println("Reversal detected near point: " + nextlist.get(0).point);
                    return true;
                }
            }

            return false;
        }
        

}
