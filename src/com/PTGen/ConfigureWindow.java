package com.PTGen;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ConfigureWindow extends JFrame implements ActionListener
{
	JTabbedPane tabPane;
	JButton rotateButton;
	
	JPanel graphicsConfigPanel;
	FlowLayout graphicsLayout;
	
	private TexDisplay theTex;
	
	public ConfigureWindow(TexDisplay t)
	{
		super();
		theTex = t;
		
		//Set all the configurations, and create a form
		this.setTitle("Texture Configuration");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Graphics Config Panel
		graphicsConfigPanel = new JPanel();
		graphicsLayout = new FlowLayout();
		graphicsConfigPanel.setLayout(graphicsLayout);
		
		//Button
		rotateButton = new JButton("Toggle Rotation");
		graphicsConfigPanel.add(rotateButton);
		rotateButton.addActionListener(this);
		
		//Tabbed Pane
		tabPane = new JTabbedPane(JTabbedPane.TOP);
		tabPane.addTab("Tab", graphicsConfigPanel);
		
		this.add(tabPane);
		this.pack();
		this.setVisible(true);
		this.setSize(300, 800);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		if(arg0.getSource() == rotateButton)
		{
			//Set the rotation of the part
			theTex.enableRotation = !theTex.enableRotation;
		}
		
	}
}
