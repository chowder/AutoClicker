package com.chowder;

import com.chowder.Components.AppControlsComponent;
import com.chowder.Components.AppDisplayComponent;
import com.chowder.Components.AppFrameComponent;
import javax.swing.UIManager;

public class AutoClicker
{

	final private static String TITLE = "Bakery";

	final private static Controller controller = new Controller();

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// Create components
		AppFrameComponent frame = new AppFrameComponent(TITLE);
		AppDisplayComponent display = new AppDisplayComponent(controller);
		AppControlsComponent controls = new AppControlsComponent(controller);

		//Adding the components to the frame.
		frame.add(display);
		frame.add(controls);

		// View the frame
		frame.pack();
		frame.setVisible(true);
	}
}
