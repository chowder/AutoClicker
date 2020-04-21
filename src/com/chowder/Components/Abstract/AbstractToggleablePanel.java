package com.chowder.Components.Abstract;

import java.awt.Component;
import javax.swing.JPanel;

public class AbstractToggleablePanel extends JPanel
{
	@Override
	public void setEnabled(boolean enabled)
	{
		for (Component component : getComponents())
		{
			component.setEnabled(enabled);
		}
	}
}
