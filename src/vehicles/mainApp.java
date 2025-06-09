package vehicles;
import vehicles.*;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
public class mainApp {

	public static void main(String args[]) throws SQLException {
		dataRetrieval dataretrieval = new dataRetrieval();
		//dataretrieval.retrieveData();
		//dataRetrieval.readCSVFileandSave("new_data.csv","vehicleStamps3");
		LinkedList<vehicleTimeStamps> path =dataretrieval.generatePath("vehicleStamps");
		System.out.println(path);
		reverseCheck2.detectReversal2(path);
		
		/*for(int i=1;i<path.size();i++) {
			double dist = speedCalc.PointToPointDistance(path.get(i-1).coordinates.latitude,path.get(i-1).coordinates.longitude
					,path.get(i).coordinates.latitude,path.get(i).coordinates.longitude);
			System.out.println("Point "+Integer.toString(i)+" and point "+Integer.toString(i+1)+": "+dist);
//		}*/
	}
}


