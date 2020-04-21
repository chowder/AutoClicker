package com.chowder.Components;

import com.chowder.Components.Abstract.AbstractToggleablePanel;
import com.chowder.Components.Controls.ConsoleComponent;
import com.chowder.Components.Controls.IntervalsComponent;
import com.chowder.Components.Controls.SettingsComponent;
import com.chowder.Components.Controls.StartStopComponent;
import com.chowder.Controller;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

public class AppControlsComponent extends AbstractToggleablePanel
{
	public AppControlsComponent(Controller controller)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		add(new IntervalsComponent(controller));
		add(new SettingsComponent(controller));
		add(new StartStopComponent(controller));
		add(new ConsoleComponent(controller));
	}
}
