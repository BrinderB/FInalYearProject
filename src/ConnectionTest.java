
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

public class ConnectionTest {
	
	/*
	 * following class is used to connect to nxt and send commands to it over bluetooth
	 * uses code from nxt example class to establish connection  
	 */
	
	public static void main (String[] args) throws IOException{
		NXTConnector conn = new NXTConnector();
		
		conn.addLogListener(new NXTCommLogListener(){

			public void logEvent(String message) {
				System.out.println("BTSend Log.listener: "+message);
				
			}

			public void logEvent(Throwable throwable) {
				System.out.println("BTSend Log.listener - stack trace: ");
				 throwable.printStackTrace();
				
			}
			
		} 
		);
		// Connect to any NXT over Bluetooth
		boolean connected = conn.connectTo("btspp://");
		
		if (!connected) {
			System.err.println("Failed to connect to any NXT");
			System.exit(1);
		}else System.out.println("connected");
		
		DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
		DataInputStream dis = new DataInputStream(conn.getInputStream());
		Scanner scanner = new Scanner(System.in);
		
		boolean running = true;
		int command;
		
		while(running){//scanner interface is output into the console 
			//emo state logger robot is then used to input the associated commands
			
			System.out.println("-5 = exit");
			System.out.println("0 = forward");
			System.out.println("1 = turn left");
			System.out.println("2 = turn right");
			System.out.println("3 = stop");
			System.out.println("Enter command here: ");
			command = scanner.nextInt();
			
			try {
				if (command == -5 ) running = false;
				System.out.println("Sending " + (command));
				dos.writeInt((command));
				dos.flush();			
				
			} catch(IOException ioe) {
				System.out.println("IO Exception writing bytes:");
				System.out.println(ioe.getMessage());
				break;
			}
			
		}
		
		try {
			dis.close();
			dos.close();
			conn.close();
		} catch (IOException ioe) {
			System.out.println("IOException closing connection:");
			System.out.println(ioe.getMessage());
		}
		
	}

}

