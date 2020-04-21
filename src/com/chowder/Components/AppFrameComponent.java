package com.chowder.Components;

import java.awt.GridLayout;
import javax.swing.JFrame;

public class AppFrameComponent extends JFrame
{
	public AppFrameComponent(String title)
	{
		setTitle(title);
		setLayout(new GridLayout(0, 1, 0, 5));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
