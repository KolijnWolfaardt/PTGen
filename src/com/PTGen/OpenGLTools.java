package com.PTGen;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 * This class creates and manages a window for displaying lwjgl content.
 * 
 * @author Kolijn Wolfaardt
 * @version 0.1
 * @since 2013-11-29
 */
public class OpenGLTools
{
	/**
	 * This function creates a window, and assigns resolution and settings. This
	 * function should be called before the initGL function
	 * 
	 * @return Returns 1 if successful, 0 otherwise
	 * @param glSettings
	 *            glSettings object
	 */
	public static int setupWindow(GLSettings glSettings)
	{
		try
		{
			// Clear the current display mode
			DisplayMode targetDisplayMode = null;

			// Set the window size to 800x600, and create the window
			targetDisplayMode = new DisplayMode(glSettings.getWindowWidth(),
					glSettings.getWindowHeight());
			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(false);
			Display.create();

		} catch (LWJGLException e)
		{
			System.out.println("An error occured when setting up the display");
			System.out.println(e.getMessage());
			return 0;
		}
		return 1;
	}

	/**
	 * Configure the OpenGL settings. This function should be called after the
	 * setupWindow function. The GL settings can only be configured after a
	 * window is created.
	 * 
	 * @param glSettings
	 *            glSettings object
	 */
	public static void initGL(GLSettings glSettings)
	{
		//Configure
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH); 
		
		// Blue Background
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);    
		
        
        //Configure the perspective
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(40f,glSettings.getWindowWidth()/glSettings.getWindowHeight(),0.1f,500f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);  
	}
}
