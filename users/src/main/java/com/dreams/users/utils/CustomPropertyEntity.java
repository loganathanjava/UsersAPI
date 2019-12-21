package com.dreams.users.utils;

import java.beans.PropertyEditorSupport;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class CustomPropertyEntity<T> extends PropertyEditorSupport{

	public Class<T> type;
    public HttpServletResponse response;
    
    public CustomPropertyEntity(Class<T> type,HttpServletResponse response) {
        this.type = type;
        this.response = response;
    }
    
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        ObjectMapper mapper = new ObjectMapper();
        
        Object obj = null; 
        
        try
        {
            obj = mapper.readValue(text, this.type);
        }
        catch(UnrecognizedPropertyException propertyEx)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch(JsonParseException parseEx)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch(Exception e)
        {
            System.out.println("Error in Property editor: "+e);
        }
        setValue(obj);
    }
}
