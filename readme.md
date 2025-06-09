# VehicleReversalDetection
Given a list of Coordinates(GPS Data) of a moving vehicle, We need to detect  the point where the vehicle starts on a reverse path.


Thought Process:
1. different types of reversals possible: U-Turn, Reverse
2. Possible logic: 

	* a. Possibility of finding individual vectors between each point and comparing their intermediate change in angles.
	* b. When a vehicle maneuvers into the reverse lane, then there is a possibility of the individual coordinates to fall closer to the older points, so we can make use of a sliding window approach to find optimal/nearly coinciding points.
	* c. Possible usage of the time and coordinates to find the corresponding distance and speed between two points.
	
	
	

	