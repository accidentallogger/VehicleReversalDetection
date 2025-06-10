package vehicles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class reverseCheck3 {
	public static void detectReversal3(LinkedList<vehicleTimeStamps> path) {
    	int win_size = 5;
    	int len = path.size();
    	int thres=3;
    	List<vehicleTimeStamps> prevList=new ArrayList<>();
    	List<vehicleTimeStamps> nextList=new ArrayList<>();
    	for(int i=win_size;i<=len-win_size-1;i++) {
    		prevList  = new ArrayList<>(path.subList(i - win_size, i));
            Collections.reverse(prevList);  // Reversing to simulate "return path"
    		nextList = new ArrayList<>(path.subList(i+1,win_size+i+1));
    		
    		for(int j=0;j<win_size;j++) {
    			double lat1=prevList.get(j).coordinates.latitude,lat2=nextList.get(j).coordinates.latitude
    					,lon1=nextList.get(j).coordinates.longitude,lon2=nextList.get(j).coordinates.longitude;
    			double distance = speedCalc.PointToPointDistance(lat1,lon1,lat2,lon2);
    			//System.out.println("between "+prevList.get(j).point+" and "+nextList.get(j).point+" : "+distance);
    			if(distance<thres) {
    				System.out.println();
    				 System.out.println("Reversal at "+nextList.get(j).point);
    				 break;
    			}
    			
    		}
    		
    	}
    }
}
