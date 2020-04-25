package com.chowder.Components;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class AppFrameComponent extends JFrame
{
	public AppFrameComponent(String title)
	{
		setTitle(title);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(
			new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)
		);
	}
}
