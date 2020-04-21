package com.chowder.Components.Controls;

import com.chowder.Controller;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StartStopComponent extends JPanel
{
	private final JButton startButton;

	public StartStopComponent(Controller controller)
	{
		startButton = new JButton("Start");
		add(startButton);
		setController(controller);
	}

	private void setController(Controller controller)
	{
		startButton.addActionListener(e -> {
			switch (startButton.getText())
			{
				case "Start":
					if (controller.start())
					{
						startButton.setText("Stop");
						controller.setControlsEnabled(false);
					}
					break;
				case "Stop":
					startButton.setText("Start");
					controller.stop();
					controller.setControlsEnabled(true);
			}
		});
	}
}
