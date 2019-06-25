# BCI Project
## Motivation/Overview
The following project is based around BCI technology, it is about researching the capabilities of retail EEG technology 
and how powerful readily available headsets are. The motivation of this project is to investigate the capabilities of retail BCI, its purposes 
and whether or not it can become a ubiquitous technology.

## Hardware used
**Lego Mindstorms NXT** - A programmable Lego robot which is built into a RC car with tracks. The reason it was chosen was because of my prior experience with the system. Furthermore integrating both emotiv and nxt code is much easier as they both use java, which is the language I am the most familiar with. 

**Emotiv Insight EEG Headset** - An EEG headset that retails at £235.00. Chosen due to it being a mid range EEG product available to the public, providing reasonably accurate EEG readings for its price range. 

## Libraries and resources used

- **Bluecove bluetooth library** - Imported bluetooth library used to communicate with the emotiv headset and the NXT. Downloaded from [here](https://sourceforge.net/projects/bluecove/).
- **Emotiv sdk** -  Provides the library necessary to communicate with the emotiv headset, for example being able to query it for facial expression and mental command detection. Software Development Kit downloaded from [here](https://github.com/Emotiv/community-sdk).
- **Lejos library** - Provides the library necessary to program the NXT, for example being able to move the NXT with a differential Pilot. Downloaded from [here](http://www.lejos.org/).

## How to run

1.  **Send headsetNav program to NXT and run.** 
    A "waiting" prompt will then appear, meaning the NXT will wait until a program attempts to connect to it. 

2.  **Run the ConnectionTest class.** 
    If successfully connected a "connected" prompt will appear on the NXT's LCD screen and the console the program is ran in will appear with an entry menu (describing the inputs required to operate the robot), waiting for input. 

3.  **Connect the emotiv headset to the system running the programs and run EmoStateLogger.** 
    Upon doing so, return to the console running Connection test and click on the prompt waiting for input. 

4. **Make one of the three prompted expressions.** 
    The EmoStateLogger class will then enter the appropriate inputs depending on the facial expressions made via a java robot that is used in emostatelogger. The user simply enters -5 into the input prompt when they are finished.  



