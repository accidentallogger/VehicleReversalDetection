package vehicles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import vehicles.vehicleTimeStamps;
public class dataRetrieval {
void retrieveData() throws SQLException{
		
		String url = "jdbc:mysql://localhost:3306/twproj";
		String user = "tanmay";
		String pass = "Qwerty@123";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
try(Connection con=DriverManager.getConnection(url,user,pass);Statement st = con.createStatement();){
	ResultSet rs = st.executeQuery("Select * from vehicleStamps");
	System.out.println(" id | Points | Time | Latitude | Longitude | Direction ");

	while(rs.next()) {
		System.out.println(rs.getInt("id")+" | "+rs.getString("Points")+" | "+rs.getTime("Time")+" | "+rs.getDouble("Latitude")+" | "+rs.getDouble("Longitude")+" | "+rs.getString("Direction"));
	}
	
}		
	}

LinkedList<vehicleTimeStamps> generatePath(String tableName) throws SQLException{
	LinkedList<vehicleTimeStamps> path = new LinkedList<>();
	String url = "jdbc:mysql://localhost:3306/twproj";
	String user = "tanmay";
	String pass = "Qwerty@123";
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}catch(ClassNotFoundException e) {
		e.printStackTrace();
	}
try(Connection con=DriverManager.getConnection(url,user,pass);Statement st = con.createStatement();){
ResultSet rs = st.executeQuery("Select * from "+tableName);
while(rs.next()) {
	vehicleTimeStamps ts = new vehicleTimeStamps(rs.getInt("id"),rs.getString("Points"),rs.getTime("Time").toLocalTime(),rs.getDouble("Latitude"),
	rs.getDouble("Longitude"),rs.getString("Direction"));
	path.add(ts);
}
//System.out.println(path);
return path;
}		

}





    public static void readCSVFileandSave(String csvFile,String tableName) {
        String url = "jdbc:mysql://localhost:3306/twproj";
        String user = "tanmay";
        String pass = "Qwerty@123";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
try(Connection con=DriverManager.getConnection(url,user,pass);Statement st = con.createStatement();){
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue;
            }

            String[] data = line.split(",");
            if (data.length >= 5) {
            	int id =Integer.parseInt(data[0]);
            	String point=data[1];
            	String timestamp = data[2];
            	System.out.println(timestamp.toString());
            	
            	double lat=Double.parseDouble(data[3]),lon=Double.parseDouble(data[4]);
            	String direction=data[5];
            	String values = "("+Integer.toString(id)+",'"+point+"','"+timestamp.toString()+"',"+Double.toString(lat)+","+Double.toString(lon)+",'"+direction+"')";
                String query = "insert into "+tableName+"(id,Points,Time,Latitude,longitude,Direction) values "+values+" ON DUPLICATE KEY UPDATE id ="+Integer.toString(id);
                int c = st.executeUpdate("create table IF NOT EXISTS "+tableName+"(id int, Points varchar(200),Time time, Latitude double(10,8),Longitude double(10,8), Direction varchar(50), check(Direction='Reverse'\n" + 
                		" or Direction='Forward'),primary key(id));\n" + 
                		"");
                c = st.executeUpdate(query);
        		if(c!=0) {
        			System.out.println("inserted!");
        		}else {
        			System.out.println("error!");
        		}
            	//vehicleTimeStamps vehicleData = new vehicleTimeStamps(id,point,timestamp,lat,lon,direction);
                
            }
        }
    }
	
}catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

