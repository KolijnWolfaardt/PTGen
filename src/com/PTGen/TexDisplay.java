package com.PTGen;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

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

	/**
	 * Render the current display. No mystery here
	 */
	public void render()
	{
		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		// R,G,B,A Set The Color To Blue One Time Only

		// Draw a Quad on the screen, which the user can move around
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(rotation, 0f, 0f, 1f);
		GL11.glTranslatef(-x, -y, 0);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glVertex2f(x - 50, y - 50);

		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glVertex2f(x + 50, y - 50);

		GL11.glColor3f(0.0f, 0.0f, 1.0f);
		GL11.glVertex2f(x + 50, y + 50);

		GL11.glColor3f(0.0f, 0.0f, 1.0f);
		GL11.glVertex2f(x - 50, y + 50);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public void update(int delta)
	{
		// rotate quad
		rotation += 0.15f * delta;

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
