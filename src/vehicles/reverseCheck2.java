package vehicles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class reverseCheck2 {
    
// checks for points that are close to each other using a sliding window approach.
    // failure

        private static final int WINDOW_SIZE = 5;
        private static final double DISTANCE_THRESHOLD = 1.0; // meters

        public static boolean detectReversal2(LinkedList<vehicleTimeStamps> path) {
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
