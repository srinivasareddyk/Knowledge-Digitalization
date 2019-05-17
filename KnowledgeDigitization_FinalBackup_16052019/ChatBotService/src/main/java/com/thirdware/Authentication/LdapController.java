package com.thirdware.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping("/authC")
public class LdapController {
	
	@Autowired
	AuthorizationServicee authService;
	

	@RequestMapping(value="/getUserStatus",method=RequestMethod.POST)
	public AuthStatusvo getUserStatus(@RequestParam("cdsid") String cdsid,@RequestParam("password") String password)
	{
		

		return authService.getUserStatusById(cdsid,password);
		
	}
	
	
	@RequestMapping(value="/checkUser",method=RequestMethod.GET)
	public AuthStatusvo checkUser(@RequestParam("email") String email) {
		
		return authService.checkUser(email);
		
	}
	
}
