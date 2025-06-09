package vehicles;

import java.util.*;

public class reverseCheck {
//with vector conversion and angle measurement
	//works on some datasets
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
                System.out.println("Reversal detected at " + p2.point);
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

}
