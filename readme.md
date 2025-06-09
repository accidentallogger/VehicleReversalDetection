# VehicleReversalDetection
Given a list of Coordinates(GPS Data) of a moving vehicle, We need to detect  the point where the vehicle starts on a reverse path.
different types of reversals possible: U-Turn, Reverse

* Possible Solutions: 

	* a. Point to Point Vector Comparison
	* b. When a vehicle maneuvers into the reverse lane, then there is a possibility of the individual coordinates to fall closer to the older points.
	* c. Possible usage of the time and coordinates to find the corresponding distance and speed between two points.(Failed due to lack of proper time data)

* reverseCheck class which implements detectReversal which is based on a.
* reverseCheck3 class which implements detectReversal3 which is based on b.

## Test Data used:
I have 3 MySql Tables named as VehicleStamps, VehicleStamps2, VehicleStamps3, each with the below given table structure:
![VehicleStamps3](https://github.com/user-attachments/assets/61521489-c8ec-443f-8fea-ccdb821e2219)


Here is the representation of the required data on google map: 

[vehicleStamps](https://www.google.com/maps/d/u/0/edit?mid=12mG_vCcJK-nh3DRrg33za-EdDM07SEo&usp=sharing)
![image](https://github.com/user-attachments/assets/acc87f48-ba95-417c-965f-8332bd69ac79)

[vehicleStamps2](https://www.google.com/maps/d/u/0/edit?mid=1PUmuwE3ULFQCK-3EKHDWxPnTDPfDv6w&usp=sharing)
![image](https://github.com/user-attachments/assets/bbc1d78d-8657-4038-8a35-fd2e9842b0d7)

[vehicleStamps3](https://www.google.com/maps/d/u/0/edit?mid=17SoGYsjML2vbNBFjjcVcR9sTnDNfDq8&usp=sharing)
![image](https://github.com/user-attachments/assets/994387b1-0f7f-4e3d-828e-97a245cb229e)

Furthermore the required CSVs and Kml files are attached in [/VehicleData/]() 

## Point to Point Vector Comparison
Vectors are a way to represent a quantity that has magnitude and direction.
In this approach we can derive the vector quantity between every point, ie. while iterating through the LinkedList of points encompassed in the path followed by the vehicle.

* Steps:

	* We can take 3 consecutive points: p1, p2, p3.

	* Create two vectors:   (v1 = p2 - p1),              (v2 = p3 - p2)

	* Normalize both vectors (so the magnitude becomed 1 making it easier to separate from direction).

	* Compute dot product: If dot product <**threshold**, it means the angle between the vectors is large (threshold assumed to be -0.7≈ more than 135°). This indicates the vehicle may have reversed direction.

 ## Detection of nearly/completely Coinciding points 
Check whether the next few GPS points closely match the reverse of the previous few points — indicating the vehicle is returning on the same path, i.e., reversing.
 * Steps:
   	* Sliding window of size 5 over the path.

	* For each center point i, get: prevList → 5 points before i (in reverse order to simulate going backward). nextList → 5 points after i.
	* Compare each pair of prevList[j] and nextList[j]:
	* Calculate distance between corresponding points.
	* If distance < threshold (assumed to be 3 meters but should be equal to the width of the road), count it as a match.
	* If enough close matches (even 1 here), declare reversal detected at that point.
=======

Here is the representation of the required data on google map: 

[vehicleStamps](https://www.google.com/maps/d/u/0/edit?mid=12mG_vCcJK-nh3DRrg33za-EdDM07SEo&usp=sharing)
![image](https://github.com/user-attachments/assets/acc87f48-ba95-417c-965f-8332bd69ac79)

[vehicleStamps2](https://www.google.com/maps/d/u/0/edit?mid=1PUmuwE3ULFQCK-3EKHDWxPnTDPfDv6w&usp=sharing)
![image](https://github.com/user-attachments/assets/bbc1d78d-8657-4038-8a35-fd2e9842b0d7)

[vehicleStamps3](https://www.google.com/maps/d/u/0/edit?mid=17SoGYsjML2vbNBFjjcVcR9sTnDNfDq8&usp=sharing)
![image](https://github.com/user-attachments/assets/994387b1-0f7f-4e3d-828e-97a245cb229e)

Furthermore the required CSV files are attached in [/Vehicledata/](Vehicledata) 

## Point to Point Vector Comparison
Vectors are a way to represent a quantity that has magnitude and direction.
In this approach we can derive the vector quantity between every point, ie. while iterating through the LinkedList of points encompassed in the path followed by the vehicle.

* Steps:

	* We can take 3 consecutive points: p1, p2, p3.

	* Create two vectors:   (v1 = p2 - p1),              (v2 = p3 - p2)

	* Normalize both vectors (so the magnitude becomed 1 making it easier to separate from direction).

	* Compute dot product: If dot product <**threshold**, it means the angle between the vectors is large (threshold assumed to be -0.7≈ more than 135°). This indicates the vehicle may have reversed direction.

 ## Detection of nearly/completely Coinciding points 
Check whether the next few GPS points closely match the reverse of the previous few points — indicating the vehicle is returning on the same path, i.e., reversing.
 * Steps:
   	* Sliding window of size Window_Size (assumed to be 5 for now) over the path.
	* For each center point i, get: prevList → 5 points before i (in reverse order to simulate going backward). nextList → 5 points after i.
	* Compare each pair of prevList[j] and nextList[j]:
	* Calculate distance between corresponding points.
	* If distance < threshold (assumed to be 3 meters but should be equal to the width of the road), count it as a match.
	* If enough close matches (even 1 here), declare reversal detected at that point.


## Brief Analysis

The second solution worked better for data sets with lesser number of mislabeled data leading to lesser number of false positives while on the other hand the first solution worked better even when there were mislabeled points at the edges of the data clusters.
There were some false positives in both the techniques

---------------------------------------------------------------------------------


Future TODOS:
1. Workout ways to see how both can be used.
2. Speed can be another factor on which we can make our decision.

---------------------------------------------------------------------------------
