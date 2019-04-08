import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.emotiv.Iedk.*;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;

/** Simple example of JNA interface mapping and usage. */

public class EmoStateLogger implements Runnable {
	boolean waitingForStop = false;
	int testCount = 1;
	public static void main(String[] args) throws InterruptedException {
		EmoStateLogger e = new EmoStateLogger();
		
		
		while (true){
			e.run();
			Edk.INSTANCE.IEE_EngineDisconnect();
		}
		
	}

	@Override
	public synchronized void run() {
		Pointer eEvent = Edk.INSTANCE.IEE_EmoEngineEventCreate();
		Pointer eState = Edk.INSTANCE.IEE_EmoStateCreate();
		IntByReference userID = null;
		short composerPort = 1726;
		int option = 1;
		int state = 0;

		userID = new IntByReference(0);

		switch (option) {
		case 1: {
			if (Edk.INSTANCE.IEE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
				System.out.println("Emotiv Engine start up failed.");
				return;
			}
			break;
		}
		case 2: {
			System.out.println("Target IP of EmoComposer: [127.0.0.1] ");

			if (Edk.INSTANCE.IEE_EngineRemoteConnect("127.0.0.1", composerPort,
					"Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
				System.out
						.println("Cannot connect to EmoComposer on [127.0.0.1]");
				return;
			}
			System.out.println("Connected to EmoComposer on [127.0.0.1]");
			break;
		}
		default:
			System.out.println("Invalid option...");
			return;
		}
		
		int blinkcount = 0;
		
		
		try {
			Robot robot = new Robot();
			
		
		while (true) {
			state = Edk.INSTANCE.IEE_EngineGetNextEvent(eEvent);

			// New event needs to be handled
			if (state == EdkErrorCode.EDK_OK.ToInt()) {

				int eventType = Edk.INSTANCE.IEE_EmoEngineEventGetType(eEvent);
				Edk.INSTANCE.IEE_EmoEngineEventGetUserId(eEvent, userID);

				// Log the EmoState if it has been updated
				if (eventType == Edk.IEE_Event_t.IEE_EmoStateUpdated.ToInt()) {

					Edk.INSTANCE.IEE_EmoEngineEventGetEmoState(eEvent, eState);

					try{

					if (EmoState.INSTANCE.IS_FacialExpressionGetLowerFaceActionPower(eState) > 0.5 && !waitingForStop){
							robot.keyPress(KeyEvent.VK_2);
							robot.keyPress(KeyEvent.VK_ENTER);
							this.wait(3000);
							break;
						
						
					}

					if (EmoState.INSTANCE.IS_FacialExpressionGetUpperFaceActionPower(eState) == 1 && !waitingForStop){
							robot.keyPress(KeyEvent.VK_1);
							robot.keyPress(KeyEvent.VK_ENTER);
							this.wait(3000);
							break;
						
						
					}
					
					if (EmoState.INSTANCE.IS_FacialExpressionIsBlink(eState) == 1 && !waitingForStop){
						blinkcount++;
						if (blinkcount == 5){ 
							waitingForStop = true;
							blinkcount = 0;
							robot.keyPress(KeyEvent.VK_0);
							robot.keyPress(KeyEvent.VK_ENTER);
							break;
						}
						
					}
					
					if (EmoState.INSTANCE.IS_FacialExpressionIsEyesOpen(eState) != 1 && waitingForStop){
						waitingForStop = false;
						robot.keyPress(KeyEvent.VK_3);
						robot.keyPress(KeyEvent.VK_ENTER);
						this.wait(3000);
						break;
						
					}
					
					}catch(Exception e){
						break;
					}
                    
					
				}
			} else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
				System.out.println("Internal error in Emotiv Engine!");
				break;
			}
		}
		
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}