package vehicles;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class Coordinates {
	Double latitude;
	Double longitude;
	Coordinates(Double latitude, Double longitude){
		this.latitude=latitude;
		this.longitude=longitude;
	}
	Coordinates(){
		this.latitude=null;
		this.longitude=null;
	}
}
public class vehicleTimeStamps{
	int id;
	String point;
	LocalTime timestamp;
	Coordinates coordinates = new Coordinates();
	String direction;
	vehicleTimeStamps(int id,String point,LocalTime timestamp,Double latitude,Double longitude,String direction){
		this.id=id;
		this.point =point;
		this.timestamp=timestamp;
		coordinates = new Coordinates(latitude,longitude);
		this.direction=direction;
	}
	
	void setPoint(String point) {
		this.point = point;
	}
	void setTimestamp(LocalTime timestamp) {
		this.timestamp=timestamp;
	}
	void setCoordinates(double latitude, double longitude) {
		this.coordinates = new Coordinates(latitude,longitude);
	}
void setDirection(String direction) {
	this.direction=direction;
}
}

