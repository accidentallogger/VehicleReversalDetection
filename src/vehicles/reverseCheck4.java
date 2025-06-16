package vehicles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class reverseCheck4 {

    static final double AngularThres = -0.7; // Adjusted threshold for reversal detection
    static final int win_size = 5;
    static final int thres = 1; // Distance threshold (ensure units match speedCalc)

    public static void normalize(double[] v) {
        double mag = Math.sqrt(v[0] * v[0] + v[1] * v[1]);
        if (mag != 0) {
            v[0] /= mag;
            v[1] /= mag;
        }
    }

    public static double[] vectorBetween(vehicleTimeStamps p1, vehicleTimeStamps p2) {
        return new double[]{
            p2.coordinates.latitude - p1.coordinates.latitude,
            p2.coordinates.longitude - p1.coordinates.longitude
        };
    }

    public static Boolean hasAngleReversed(vehicleTimeStamps p1, vehicleTimeStamps p2,
                                           vehicleTimeStamps p3, vehicleTimeStamps p4) {
        double[] v1 = vectorBetween(p1, p2);
        double[] v2 = vectorBetween(p3, p4);

        normalize(v1);
        normalize(v2);

        double dot = v1[0] * v2[0] + v1[1] * v2[1];
        return dot < AngularThres;
    }

    public static void detectReversal4(LinkedList<vehicleTimeStamps> path) {
        int len = path.size();

        if (len < 2 * win_size + 1) {
            System.out.println("Path too short for detection.");
            return;
        }

        for (int i = win_size; i <= len - win_size - 1; i++) {
            vehicleTimeStamps currVTS = path.get(i);

            List<vehicleTimeStamps> prevList = new ArrayList<>(path.subList(i - win_size, i));
            Collections.reverse(prevList);  // Simulate return path
            List<vehicleTimeStamps> nextList = new ArrayList<>(path.subList(i + 1, win_size + i + 1));

            for (int j = 0; j < win_size; j++) {
                double lat1 = prevList.get(j).coordinates.latitude;
                double lon1 = prevList.get(j).coordinates.longitude;
                double lat2 = nextList.get(j).coordinates.latitude;
                double lon2 = nextList.get(j).coordinates.longitude;

                double distance = speedCalc.PointToPointDistance(lat1, lon1, lat2, lon2);

                boolean hasanglereversed = false;
                vehicleTimeStamps[] v1;
                vehicleTimeStamps[] v2;

                if (j > 0 && j < win_size - 1) {
                    v1 = new vehicleTimeStamps[]{prevList.get(j + 1), prevList.get(j)};
                    v2 = new vehicleTimeStamps[]{nextList.get(j), nextList.get(j - 1)};
                    hasanglereversed = hasAngleReversed(v1[0], v1[1], v2[0], v2[1]);
                } else if (j == 0) {
                    v1 = new vehicleTimeStamps[]{prevList.get(j + 1), prevList.get(j)};
                    v2 = new vehicleTimeStamps[]{nextList.get(j), currVTS};
                    hasanglereversed = hasAngleReversed(v1[0], v1[1], v2[0], v2[1]);
                } else if (j == win_size - 1) {
                    v1 = new vehicleTimeStamps[]{currVTS, prevList.get(j)};
                    v2 = new vehicleTimeStamps[]{nextList.get(j), nextList.get(j - 1)};
                    hasanglereversed = hasAngleReversed(v1[0], v1[1], v2[0], v2[1]);
                }

                if (distance < thres && hasanglereversed) {
                    System.out.println("Reversal at " + nextList.get(j).point);
                }
            }
        }
    }
}
