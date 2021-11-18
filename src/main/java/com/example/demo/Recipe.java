package com.example.demo;

public class Recipe 
{
	
	private String name;
	private String[] ingredients;
	private String[] instructions;
	
	public String getName() 
	{
		return name;
	}
	
	public String[] getIngredients() 
	{
		return ingredients;
	}
	
	public String[] getInstructions() 
	{
		return instructions;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public void setIngredients(String[] ingredients) 
	{
		this.ingredients = ingredients;
	}

	public void setInstructions(String[] instructions) 
	{
		this.instructions = instructions;
	}
	
}
