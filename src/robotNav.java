import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.Scanner;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.remote.RemoteNXT;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;

public class robotNav {
	/*
	 * following class is run on nxt to detect commands sent to it from connection test class
	 */
	public static BTConnection btc;

	
	public static void main (String[] args) throws IOException{
		String connected = "Connected";
	    String waiting = "Waiting...";
	    
	    DifferentialPilot dp = new DifferentialPilot(3, 18.5f, Motor.A, Motor.C);
	    OdometryPoseProvider odomPose = new OdometryPoseProvider(dp);
	  
	    	
	      LCD.drawString(waiting,0,0);
	      NXTConnection connection = Bluetooth.waitForConnection(); 
	      LCD.clear();
	      LCD.drawString(connected,0,0);

	      DataInputStream dis = connection.openDataInputStream();
	      DataOutputStream dos = connection.openDataOutputStream();
	      dp.setTravelSpeed(10);
	      int n = -1;
	      int x, y;
	      
	      
	      while(n!=-5) {
	    
	        n = dis.readInt();
	        LCD.drawInt(n,7,0,1);//draws the command to the nxt screen
	        
	        if (n == 0){
	        	dp.forward();//moves the robot according to the command sent from connection test class  	
	        }
	        if (n == 1){
	        	dp.rotate(-45);
	        }
	        if (n == 2){
	        	dp.rotate(45);
	        }
	        if (n == 3){
	        	dp.stop();
	        }
	        
	      }
	      
	  
	    
	    try{
	    	dos.close();
	    	dis.close();
	    	btc.close();
	    }catch(IOException e){
	    	System.out.println("error closing csv file");
	    } 
	  
	    
	    Button.waitForAnyPress();
	    
	    
	}

}
