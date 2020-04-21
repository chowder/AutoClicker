package com.chowder;

public class Interval
{
	float value;

	Interval(float value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return String.format("%s seconds", this.value);
	}
}
