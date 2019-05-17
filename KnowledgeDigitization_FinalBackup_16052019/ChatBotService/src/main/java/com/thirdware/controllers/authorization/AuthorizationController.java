package com.thirdware.controllers.authorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thirdware.services.user.AuthorizationService;
import com.thirdware.vo.AuthStatusVo;
import com.thirdware.vo.AuthorizationVo;

@RestController
@RequestMapping("/Auth")
public class AuthorizationController {
	
	@Autowired
	AuthorizationService authService;
	
	@RequestMapping("/getAuthScreenList")
	public List<AuthorizationVo> getAuthScreenList(@RequestBody String cdsid)
	{
		return authService.getScreenAuthList(cdsid);
		
	}
	@RequestMapping("/getUserStatus")
	public AuthStatusVo getUserStatus(@RequestBody String cdsid)
	{
		return authService.getUserStatusById(cdsid);
		
	}
	
}
