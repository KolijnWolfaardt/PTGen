package com.PTGen;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;

/**
 * Manage the 3D display
 * 
 * @author Kolijn
 * @version 0.1
 * @since 2013-11-29
 */
public class TexDisplay
{
	/** position of quad */
	float x = 400, y = 300;
	/** angle of quad rotation */
	float rotation = 0;
	boolean enableRotation = false;

	int texId;

	float rotateZ = 0.785f;
	float rotateTheta = 0.785f;
	float zoomR = 6;
	
	float cameraX = 3;
	float cameraY = 3;
	float cameraZ = 5;

	public TexDisplay()
	{

		int width = 64;
		int height = 64;

		ByteBuffer buf = BufferUtils.createByteBuffer(4 * width * height);
		
		Generator.generate(64, buf);
		/*
		double a = Math.random();
		

		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				buf.put((byte) (i + j));
				buf.put((byte) (i + j));
				buf.put((byte) (i + j));
				buf.put((byte) 255);
			}
		}*/

		// Make the buffer readable by OpenGL
		buf.flip();

		// Get a texture ID
		texId = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

		// Setup wrap mode
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
				GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
				GL12.GL_CLAMP_TO_EDGE);

		// Setup texture scaling filtering
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_NEAREST);

		// Send texel data to OpenGL
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height,
				0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

	}

	/**
	 * Render the current display. No mystery here
	 */
	public void render()
	{
		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		//Configure the camera position
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GLU.gluLookAt(cameraX,cameraY,cameraZ,0,0,0,0,0,1);

		GL11.glPushMatrix();
		//GL11.glRotatef(rotation,0,0,1);
		
		GL11.glRotatef(rotateZ,0,0,1);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
		
		GL11.glBegin(GL11.GL_TRIANGLES);
		{
			float num = 1.0f;
			
			
			/*
			 *  1 -- 2
			 *  |    |
			 *  |    |
			 *  3 -- 4
			 *  
			 *  1 - Red
			 *  2 - Blue
			 *  3 - Green
			 *  4 - Black
			 */
			
			//GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(0.0f,1.0f);
			GL11.glVertex3f(-num, num, 0.0f);  //1
			//GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(0.0f,0.0f);
			GL11.glVertex3f(-num, -num, 0.0f); //3
			//GL11.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(num, num, 0.0f);   //2
			
			//GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(1.0f,0.0f);
			GL11.glVertex3f(num, -num, 0.0f);  //4
			//GL11.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(num, num, 0.0f);   //2
			//GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(0.0f,0.0f);
			GL11.glVertex3f(-num,-num, 0.0f);  //3
			
			
		}
		GL11.glEnd();
		
	}

	public void update(int delta)
	{
		// rotate quad
		if (this.enableRotation)
			rotation += 0.05f * delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			x -= 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			x += 0.35f * delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			y += 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			y -= 0.35f * delta;

		
		int wheelchange = Mouse.getDWheel();
		if (Mouse.isButtonDown(0) || wheelchange != 0)
		{
			//Change Z rotation
			rotateZ -= ((float)Mouse.getDX())/100.0f;
			rotateZ = rotateZ % (float)(2*Math.PI);

			//Theta rotation
			rotateTheta -= ((float)Mouse.getDY())/100.0f;
			if (rotateTheta < 0.001f )
				rotateTheta = 0.001f;
			if (rotateTheta > (float)Math.PI/2)
				rotateTheta = (float)( Math.PI/2 - 0.001f);
	
			//Change Zoom
			zoomR -= ((float)wheelchange)/50.0f;
			if (zoomR <0.5)
				zoomR = 0.5f;
			if (zoomR > 20)
				zoomR = 20;
			
			//Use these points to calculate the camera position.
			
			cameraZ = (float) (Math.sin(rotateTheta)*zoomR);
			float d = (float) (Math.cos(rotateTheta)*zoomR);
			cameraX = (float) (Math.cos(rotateZ)*d);
			cameraY = (float) (Math.sin(rotateZ)*d);
		}
	}

}
