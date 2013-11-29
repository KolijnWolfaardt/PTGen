package com.PTGen;

import javax.swing.*;

public class ConfigureWindow extends JFrame
{
	public ConfigureWindow()
	{
		super();
		
		//Set all the configurations, and create a form
		this.setTitle("Texture Configuration");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		this.pack();
		this.setVisible(true);
		this.setSize(300, 800);
	}
}
