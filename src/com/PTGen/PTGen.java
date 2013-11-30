package com.PTGen;

import org.lwjgl.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class PTGen
{
	TexDisplay texDisplay;
	
	// time at last frame
	long lastFrame;
	//frames per second 
	int fps;
	// last fps time
	long lastFPS;
	ConfigureWindow configWindow;
	
	
	public PTGen ()
	{
		System.out.println("Starting");	
		
		//First create a GLSettings Class. This class contains all the settings for the openGL section
		GLSettings glSettings = new GLSettings();
		
		//LWJGL initialization
		OpenGLTools.setupWindow(glSettings);
		OpenGLTools.initGL(glSettings);
		
		//Create the 3d Window object
		texDisplay = new TexDisplay();
		
		//Create the configuration Window
		configWindow = new ConfigureWindow(texDisplay);
		
		getDelta();
		lastFPS = getTime();

		while (!Display.isCloseRequested())
		{		
			int delta = getDelta();	
						
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
			
			texDisplay.update(delta);
			updateFPS();
			
			texDisplay.render();

			Display.update();
			Display.sync(120); 
		}
		
		configWindow.dispose();
		Display.destroy();
		
	}
	
	//Calculate how many milliseconds have passed since last frame.
	public int getDelta()
	{
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	 
	    return delta;
	}
		

	//Get the accurate system time
	public long getTime()
	{
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
		
		
	// Calculate the FPS and set it in the title bar
	public void updateFPS()
	{
		if (getTime() - lastFPS > 1000)
		{
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	
	public static void main (String [] args)
	{
		PTGen theInstance = new PTGen();
	}
}
