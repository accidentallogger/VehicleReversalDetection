package vehicles;
import java.util.*;
import java.time.LocalTime;

// Here, I have developed 2 separate approaches, advanced and basic.

class reverseCheck5 {
    
    // Constants for U-turn detection
    private static final double MIN_UTURN_ANGLE = 150.0; // Minimum angle change for U-turn (degrees)
    private static final double MIN_DISTANCE_THRESHOLD = 50; // Minimum distance in meters as per National Highways
    private static final int MIN_POINTS_FOR_UTURN = 3; // Minimum points to analyze
    private static final double EARTH_RADIUS = 6371000; // Earth's radius in meters
    
    
    public static List<UTurnEvent> basicReversal(LinkedList<vehicleTimeStamps> timestamps) {
        List<UTurnEvent> uTurns = new ArrayList<>();
        
        if (timestamps == null || timestamps.size() < MIN_POINTS_FOR_UTURN) {
            return uTurns;
        }
        
        // Convert to array for easier indexing
        vehicleTimeStamps[] points = timestamps.toArray(new vehicleTimeStamps[0]);
        
        // Analyze each set of consecutive points
        for (int i = 0; i < points.length - 2; i++) {
            UTurnEvent uTurn = analyzePointsForUTurn(points, i);
            if (uTurn != null) {
                uTurns.add(uTurn);
                // Skip ahead to avoid detecting the same U-turn multiple times
                i += 2;
            }
        }
        
        return uTurns;
    }
    
   
    private static UTurnEvent analyzePointsForUTurn(vehicleTimeStamps[] points, int startIndex) {
        if (startIndex + 2 >= points.length) return null;
        
        vehicleTimeStamps p1 = points[startIndex];
        vehicleTimeStamps p2 = points[startIndex + 1];
        vehicleTimeStamps p3 = points[startIndex + 2];
        
        // Skip if any coordinates are null
        if (p1.coordinates.latitude == null || p1.coordinates.longitude == null ||
            p2.coordinates.latitude == null || p2.coordinates.longitude == null ||
            p3.coordinates.latitude == null || p3.coordinates.longitude == null) {
            return null;
        }
        
        // Calculate bearings
        double bearing1 = calculateBearing(p1.coordinates, p2.coordinates);
        double bearing2 = calculateBearing(p2.coordinates, p3.coordinates);
        
        // Calculate angle change
        double angleChange = calculateAngleChange(bearing1, bearing2);
        
        // Calculate distances
        double distance1 = calculateDistance(p1.coordinates, p2.coordinates);
        double distance2 = calculateDistance(p2.coordinates, p3.coordinates);
        
        // Check if this qualifies as a U-turn
        if (Math.abs(angleChange) >= MIN_UTURN_ANGLE && 
            distance1 >= MIN_DISTANCE_THRESHOLD && 
            distance2 >= MIN_DISTANCE_THRESHOLD) {
            
            return new UTurnEvent(p1, p2, p3, angleChange, 
                                p1.timestamp, p3.timestamp);
        }
        
        return null;
    }
    
    
    private static double calculateBearing(Coordinates from, Coordinates to) {
        double lat1Rad = Math.toRadians(from.latitude);
        double lat2Rad = Math.toRadians(to.latitude);
        double deltaLonRad = Math.toRadians(to.longitude - from.longitude);
        
        double y = Math.sin(deltaLonRad) * Math.cos(lat2Rad);
        double x = Math.cos(lat1Rad) * Math.sin(lat2Rad) - 
                   Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(deltaLonRad);
        
        double bearingRad = Math.atan2(y, x);
        return Math.toDegrees(bearingRad);
    }
    
    
    private static double calculateAngleChange(double bearing1, double bearing2) {
        double diff = bearing2 - bearing1;
        
        // Normalize to [-180, 180]
        while (diff > 180) diff -= 360;
        while (diff < -180) diff += 360;
        
        return diff;
    }

    private static double calculateDistance(Coordinates from, Coordinates to) {
        double lat1Rad = Math.toRadians(from.latitude);
        double lat2Rad = Math.toRadians(to.latitude);
        double deltaLatRad = Math.toRadians(to.latitude - from.latitude);
        double deltaLonRad = Math.toRadians(to.longitude - from.longitude);
        
        double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return EARTH_RADIUS * c; // Distance in meters
    }
    

    public static List<UTurnEvent> detectUTurnsAdvanced(LinkedList<vehicleTimeStamps> timestamps) {
        List<UTurnEvent> uTurns = new ArrayList<>();
        
        if (timestamps == null || timestamps.size() < 5) {
            return uTurns;
        }
        
        vehicleTimeStamps[] points = timestamps.toArray(new vehicleTimeStamps[0]);
        
         for (int i = 0; i <= points.length - 5; i++) {
            UTurnEvent uTurn = analyzeWindowForUTurn(points, i, 5);
            if (uTurn != null) {
                uTurns.add(uTurn);
                i += 3;
            }
        }
        
        return uTurns;
    }
    
    private static UTurnEvent analyzeWindowForUTurn(vehicleTimeStamps[] points, 
                                                   int startIndex, int windowSize) {
        if (startIndex + windowSize > points.length) return null;
        
        List<Double> bearings = new ArrayList<>();
        List<vehicleTimeStamps> validPoints = new ArrayList<>();
        for (int i = startIndex; i < startIndex + windowSize - 1; i++) {
            vehicleTimeStamps p1 = points[i];
            vehicleTimeStamps p2 = points[i + 1];
            
            if (p1.coordinates.latitude != null && p1.coordinates.longitude != null &&
                p2.coordinates.latitude != null && p2.coordinates.longitude != null) {
                
                double bearing = calculateBearing(p1.coordinates, p2.coordinates);
                bearings.add(bearing);
                
                if (validPoints.isEmpty()) validPoints.add(p1);
                validPoints.add(p2);
            }
        }
        
        if (bearings.size() < 3) return null;
        double totalAngleChange = 0;
        for (int i = 1; i < bearings.size(); i++) {
            totalAngleChange += calculateAngleChange(bearings.get(i-1), bearings.get(i));
        }
        if (Math.abs(totalAngleChange) >= MIN_UTURN_ANGLE) {
            vehicleTimeStamps startPoint = validPoints.get(0);
            vehicleTimeStamps endPoint = validPoints.get(validPoints.size() - 1);
            vehicleTimeStamps midPoint = validPoints.get(validPoints.size() / 2);
            
            return new UTurnEvent(startPoint, midPoint, endPoint, totalAngleChange,
                                startPoint.timestamp, endPoint.timestamp);
        }
        
        return null;
    }
}

class UTurnEvent {
    public vehicleTimeStamps startPoint;
    public vehicleTimeStamps turnPoint;
    public vehicleTimeStamps endPoint;
    public double angleChange;
    public LocalTime startTime;
    public LocalTime endTime;
    public String turnDirection;
    
    public UTurnEvent(vehicleTimeStamps start, vehicleTimeStamps turn, vehicleTimeStamps end,
                     double angleChange, LocalTime startTime, LocalTime endTime) {
        this.startPoint = start;
        this.turnPoint = turn;
        this.endPoint = end;
        this.angleChange = angleChange;
        this.startTime = startTime;
        this.endTime = endTime;
        this.turnDirection = angleChange > 0 ? "LEFT" : "RIGHT";
    }
    
    @Override
    public String toString() {
        return String.format("U-Turn detected: %s turn of %.2f degrees from %s to %s at point (%.6f, %.6f)",
                           turnDirection, Math.abs(angleChange), startTime, endTime,
                           turnPoint.coordinates.latitude, turnPoint.coordinates.longitude);
    }
}


