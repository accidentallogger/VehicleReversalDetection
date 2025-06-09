package vehicles;
import vehicles.*;
import java.time.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.sql.Time;
import java.util.*;
public class speedCalc {
	static final NumberFormat numberformat = new DecimalFormat("#0.00");
	static double PointToPointDistance(double lat1, double lon1, double
			lat2, double lon2) {
		
			         double theta = lon1 - lon2;
			         double dist = Math.sin(lat1 * 3.141592653589793D / 180.0D) *
			Math.sin(lat2 * 3.141592653589793D / 180.0D) + Math.cos(lat1 *
			3.141592653589793D / 180.0D) * Math.cos(lat2 * 3.141592653589793D /
			180.0D) * Math.cos(theta * 3.141592653589793D / 180.0D);
			         dist = Math.acos(dist);
			         dist = dist * 180.0D / 3.141592653589793D;
			         dist = dist * 60.0D * 1.1515D;
			         double dist2 = dist * 1609.344D;// distance in meters
			         double distance = Double.parseDouble(numberformat.format(dist2));
			         return distance;  //in meters
			         
			      }
	public static double PointToPointSpeed(double distPointToPoint,LocalTime timeAtPoint1,LocalTime timeAtPoint2){
		System.out.println(timeAtPoint2);
		System.out.println(timeAtPoint1);

		Duration duration = Duration.between(timeAtPoint1,timeAtPoint2);
		System.out.println(duration.getSeconds());
		double speed = (double)(distPointToPoint/(double)duration.getSeconds());
		return speed;
	}
}
