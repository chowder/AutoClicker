package com.chowder.Components.Controls;

import com.chowder.Controller;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConsoleComponent extends JPanel
{
	public ConsoleComponent(Controller controller)
	{
		JLabel console = new JLabel("Application started");
		controller.setConsoleCallback(console::setText);
		add(console);
	}
}
