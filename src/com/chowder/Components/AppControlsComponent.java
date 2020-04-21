package com.chowder.Components;

import com.chowder.Components.Controls.ConsoleComponent;
import com.chowder.Components.Controls.IntervalsComponent;
import com.chowder.Components.Controls.SettingsComponent;
import com.chowder.Components.Controls.StartStopComponent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class AppControlsComponent extends JPanel
{
	private final IntervalsComponent intervalsComponent;
	private final SettingsComponent settingsComponent;
	private final StartStopComponent startStopComponent;
	private final ConsoleComponent consoleComponent;


	public AppControlsComponent() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		intervalsComponent = new IntervalsComponent();
		settingsComponent = new SettingsComponent();
		startStopComponent = new StartStopComponent();
		consoleComponent = new ConsoleComponent();
	}
}
