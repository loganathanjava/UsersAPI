package com.dreams.users.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utilities {
	
	public static void setCookie(HttpServletResponse response, String key, String value)
	{
		Cookie ck  = new Cookie(key, value);
		response.addCookie(ck);
	}

	public static String getCookie(HttpServletRequest request, String key)
	{
		String toRet = "";
		
		try
		{
			Cookie[] ck = request.getCookies();
			
			for(Cookie c : ck)
			{
				if(c.getName().equals(key))
				{
					return c.getValue();
				}
			}
		}
		catch(Exception e)
		{
			
		}
		return toRet;
	}
}
