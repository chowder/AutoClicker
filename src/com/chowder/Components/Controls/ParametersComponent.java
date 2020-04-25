package com.chowder.Components.Controls;

import com.chowder.Controller;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ParametersComponent extends JPanel
{
	private HashMap<String, ParameterLabel> parameterLabels = new HashMap<>();

	final private String[] Parameters = {
		"Timeout"
	};

	public ParametersComponent(Controller controller)
	{
		setBorder(BorderFactory.createTitledBorder("Parameters"));
		setLayout(new GridLayout(1, 1));
		JPanel innerPanel = new JPanel(new GridLayout(0, 2, 2, 2));
		innerPanel.setBorder(new EmptyBorder(3, 5, 5, 0));
		add(innerPanel);

		setupLabels(innerPanel);
		controller.setUpdateParameterCallback(this::setParameter);
	}

	private void setupLabels(JPanel innerPanel)
	{
		for (String parameter: Parameters)
		{
			ParameterLabel label = new ParameterLabel(parameter);
			parameterLabels.put(parameter, label);
			innerPanel.add(label);
		}
	}

	private void setParameter(String parameterName, Object value)
	{
		if (parameterLabels.containsKey(parameterName))
		{
			parameterLabels.get(parameterName).setValue(value.toString());
		}
	}

	private class ParameterLabel extends JLabel {

		private final String parameterName;
		private String parameterValue;

		ParameterLabel(String parameterName) {
			this.parameterName = parameterName;
		}

		void setValue(String parameterValue)
		{
			this.parameterValue = parameterValue;
			this.updateUI();
		}

		@Override
		public String getText()
		{
			return String.format("%s: %s", parameterName, parameterValue);
		}
	}
}
