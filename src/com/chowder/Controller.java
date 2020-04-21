package com.chowder;

import com.chowder.Components.AppControlsComponent;
import java.awt.AWTException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

public class Controller
{
	private JLabel console;
	private Clicker clicker;

	private boolean cyclic = true;
	private boolean gaussian = true;
	private float timeout = -1;

	private DefaultListModel<Interval> intervals;
	private JList<Interval> list;
	private AppControlsComponent controlsComponent;


	public void addInterval(String interval)
	{
		if (interval.length() > 0)
		{
			try
			{
				float value = Float.parseFloat(interval);
				intervals.addElement(new Interval(value));
			}
			catch (NumberFormatException e)
			{
				console.setText("Invalid input given.");
				return;
			}
			console.setText("Added new interval");
		}
	}

	public void setDisplayList(JList<Interval> list)
	{
		this.list = list;
	}

	public void setIntervals(DefaultListModel<Interval> intervals)
	{
		this.intervals = intervals;
	}

	public void setConsole(JLabel console)
	{
		this.console = console;
	}

	public void setControlsComponent(AppControlsComponent controlsComponent)
	{
		this.controlsComponent = controlsComponent;
	}

	public void clear()
	{
		intervals.clear();
		console.setText("Cleared all intervals");
	}

	public void stop()
	{
		clicker.interrupt();
	}

	public boolean start()
	{
		if (intervals.size() <= 0)
		{
			console.setText("No intervals given");
			return false;
		}
		try
		{
			clicker = new Clicker(intervals, console, list, gaussian, cyclic, timeout);
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
		clicker.start();
		return true;
	}

	public void setGaussian(boolean gaussian)
	{
		console.setText(String.format("Random mode %s", gaussian ? "enabled" : "disabled"));
		this.gaussian = gaussian;
	}

	public void setCyclic(boolean cyclic)
	{
		console.setText(String.format("Cyclic mode %s", cyclic ? "enabled" : "disabled"));
		this.cyclic = cyclic;
	}

	public void remove()
	{
		List<Interval> selected = list.getSelectedValuesList();
		for (Interval i : selected)
		{
			intervals.removeElement(i);
			console.setText(String.format("Removed interval: %ss", i.value));
		}
	}

	public void setControlsEnabled(boolean enabled)
	{
		controlsComponent.setEnabled(enabled);
	}

	public void setTimeout(String timeout)
	{
		float value;
		if (timeout.length() > 0)
		{
			try
			{
				value = Float.parseFloat(timeout);
			}
			catch (NumberFormatException e)
			{
				console.setText("Invalid timeout value given");
				return;
			}
			if (value < 0)
			{
				console.setText("Timeout cannot be negative");
			}
			else if (value == 0)
			{
				this.timeout = 0;
				console.setText("Timeout disabled");
			}
			else
			{
				this.timeout = value;
				console.setText(String.format("Autoclicker will stop after %s minutes", this.timeout));
			}
		}
	}
}
