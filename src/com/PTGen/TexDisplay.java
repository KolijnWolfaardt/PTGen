package com.PTGen;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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

	public TexDisplay()
	{

		int width = 64;
		int height = 64;

		ByteBuffer buf = BufferUtils.createByteBuffer(4 * width * height);

		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				buf.put((byte) (i+j));
				buf.put((byte) (i+j));
				buf.put((byte) (i+j));
				buf.put((byte) 255);
			}
		}

		//Make the buffer readable by OpenGL
		buf.flip();
		
		//Get a texture ID
		texId = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
		
		// Setup wrap mode
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		// Setup texture scaling filtering
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		// Send texel data to OpenGL
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width,
				height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

	}

	/**
	 * Render the current display. No mystery here
	 */
	public void render()
	{
		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// Draw a Quad on the screen, which the user can move around
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(rotation, 0f, 0f, 1f);
		GL11.glTranslatef(-x, -y, 0);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
		GL11.glBegin(GL11.GL_QUADS);
		{

			GL11.glVertex2f(x - 200, y - 200);
			GL11.glTexCoord2f(0, 0);

			GL11.glVertex2f(x + 200, y - 200);
			GL11.glTexCoord2f(1, 0);

			GL11.glVertex2f(x + 200, y + 200);
			GL11.glTexCoord2f(1, 1);

			GL11.glVertex2f(x - 200, y + 200);
			GL11.glTexCoord2f(0, 1);
		}
		GL11.glEnd();
		GL11.glPopMatrix();
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

	}

}
