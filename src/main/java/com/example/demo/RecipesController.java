package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class RecipesController 
{
	
	//Data structure to contain our POJOs
	private static Map<String, Recipe> recipeHashMap = new HashMap<>();
	static 
	{
		ObjectMapper objectMapper = new ObjectMapper();
		try 
		{
			//Populate our data structure using .JSON file in class path
			Recipe[] listR = objectMapper.readValue(new File("C:/Users/Logan/eclipse-workspace/Recipes/src/main/resources/data.json"), new TypeReference<Recipe[]>() {});
			for(Recipe r : listR) 
			{
				recipeHashMap.put(r.getName(), r);
			}
			
		} catch (JsonParseException e) 
		{
			e.printStackTrace();
		} catch (JsonMappingException e) 
		{
			e.printStackTrace();
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//Handles DELETE request for specified recipe name at http:/localhost:3000/recipes/"name"
	@RequestMapping (value = "/recipes/{name}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("name") String name) 
	{
		recipeHashMap.remove(name);
		return new ResponseEntity<>("Recipe deleted successfully", HttpStatus.OK);
	}
	
	//Handles GET request for all recipes at http:/localhost:3000/recipes
    @RequestMapping(value = "/recipes")
    public ResponseEntity<Object> getRecipe() 
    {   
        return new ResponseEntity<>(recipeHashMap.keySet(), HttpStatus.OK);
    }
    
    //Handles GET request for specified recipe name at http:/localhost:3000/recipes/"name"
    @RequestMapping(value = "/recipes/{name}", method = RequestMethod.GET)
    public ResponseEntity<Object> getRecipeByName(@PathVariable("name") String name) 
    {   
    	if(recipeHashMap.containsKey(name)) 
    	{
    		return new ResponseEntity<>(recipeHashMap.get(name), HttpStatus.OK);
    	}
    	else 
    	{
    		return new ResponseEntity<>("Error: Recipe does not exist", HttpStatus.NOT_FOUND);
    	}
    }
   
    //Handles PUT request for specified recipe name, request body expects recipe JSON compatible with Recipe POJO, at http:/localhost:3000/recipes/"name"
    @RequestMapping(value = "/recipes/{name}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateRecipe(@PathVariable("name") String name, @RequestBody Recipe recipe) 
    {
    	if(recipeHashMap.containsKey(name)) 
    	{
		    recipeHashMap.remove(name);
		    recipeHashMap.put(name, recipe);
		    return new ResponseEntity<>("Recipe updated successfully", HttpStatus.OK);
    	}
    	else
    	{
    		return new ResponseEntity<>("Error: Recipe does not exist", HttpStatus.NOT_FOUND);
    	}
    }
    
    //Handles POST request, request body expects recipe JSON compatible with Recipe POJO, at http:/localhost:3000/recipes
    @RequestMapping(value = "/recipes", method = RequestMethod.POST)
    public ResponseEntity<Object> postRecipe(@RequestBody Recipe recipe) 
    {
    	if(recipeHashMap.containsKey(recipe.getName()))
    	{
    		return new ResponseEntity<>("Error: Recipe already exists", HttpStatus.valueOf(400));
    	}
    	else
    	{
    		recipeHashMap.put(recipe.getName(), recipe);
    		return new ResponseEntity<>("Recipe added successfully", HttpStatus.CREATED);
    	}
    }
}
