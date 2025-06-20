package vehicles;
import vehicles.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
public class mainApp {

	public static void main(String args[]) throws SQLException, FileNotFoundException, IOException {
		dataRetrieval dataretrieval = new dataRetrieval();
		
		//KmlParser.parseCoordinates("kmls/edge_case2.kml","Vehicledata/edge_case2.csv");
		
		//dataretrieval.retrieveData();
		//dataRetrieval.readCSVFileandSave("Vehicledata/edge_case2.csv","edge_case2");
		
		
		LinkedList<vehicleTimeStamps> path =dataretrieval.generatePath("edge_case2");
		//System.out.println(path);
		List<UTurnEvent> basicUTurns =reverseCheck5.basicReversal(path);
		 System.out.println("\nBasic U-turn detection results:");
		for(UTurnEvent b:basicUTurns) {
			 System.out.println(b);
		}
		 List<UTurnEvent> advancedUTurns = reverseCheck5.detectUTurnsAdvanced(path);
		 System.out.println("\nAdvanced U-turn detection results:");
	        
		for (UTurnEvent uTurn : advancedUTurns) {
            System.out.println(uTurn);
        }
		/*for(int i=1;i<path.size();i++) {
			double dist = speedCalc.PointToPointDistance(path.get(i-1).coordinates.latitude,path.get(i-1).coordinates.longitude
					,path.get(i).coordinates.latitude,path.get(i).coordinates.longitude);
			System.out.println("Point "+Integer.toString(i)+" and point "+Integer.toString(i+1)+": "+dist);
	}*/
		
		
	}
}


