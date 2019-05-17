package com.thirdware.controllers.user;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thirdware.services.user.UserManageService;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.user.UserAreaVo;
import com.thirdware.vo.user.UserDataTableVo;
import com.thirdware.vo.user.UserManageVo;



@RestController
@RequestMapping("/User")
public class UserController {
	
	@Autowired
	UserManageService usrManageService;
	
	
	
	@RequestMapping("/ManageUserSearch")
	public List<UserDataTableVo> searchUser(@RequestBody UserManageVo user)
	{
		/*System.out.println("*********** getUser **********"+user.getWslIdSrch());
		System.out.println("Status ******************"+user.getStatus().getKey());
		System.out.println("Lang ******************"+user.getLang());
		System.out.println("Country ******************"+user.getCountry());*/
				
		
		return usrManageService.getManageUserList(user);
	}
	
	@RequestMapping("/ManageUserUpdateSearch")
	public UserDataTableVo searchManageUpdateUser(@RequestBody UserDataTableVo user)
	{
		/*System.out.println("*********** getUser **********"+user.getWslIdSrch());
		System.out.println("Status ******************"+user.getStatus().getKey());
		System.out.println("Lang ******************"+user.getLang());
		System.out.println("Country ******************"+user.getCountry());*/
		System.out.println("Coming to ManageUpdateUser");
		
		
		return usrManageService.getManageUserInfo(user);
	}
	
	@RequestMapping("/ManageUserSave")
	public void saveUser(@RequestBody UserAreaVo user)
	{
		System.out.println("*********** saveUserInfo **********"+user.getUserObj().getCdsid());
		 usrManageService.saveUser(user);
		
	}
	
	
	@RequestMapping("/ApproveUserSave")
	public void approveUser(@RequestBody UserAreaVo user)
	{
		System.out.println("*********** saveUserInfo **********"+user.getUserObj().getCdsid());
		 usrManageService.approveUser(user);
		
	}
	@RequestMapping("/DenyUserSave")
	public void denyUser(@RequestBody UserAreaVo user)
	{
		System.out.println("*********** saveUserInfo **********"+user.getUserObj().getCdsid());
		 usrManageService.denyUser(user);
		
	}
	
	
	//New User request Approval Search
	
	@RequestMapping("/RequestUserSearch")
	public List<UserDataTableVo> searchRequestUser()
	{
		/*System.out.println("*********** getUser **********"+user.getWslIdSrch());
		System.out.println("Status ******************"+user.getStatus().getKey());
		System.out.println("Lang ******************"+user.getLang());
		System.out.println("Country ******************"+user.getCountry());*/
				
		
		return usrManageService.getRequestUserList();
	}
	
	@RequestMapping("/RequestUserUpdateSearch")
	public UserDataTableVo searchRequestUpdateUser(@RequestBody UserDataTableVo user)
	{
		/*System.out.println("*********** getUser **********"+user.getWslIdSrch());
		System.out.println("Status ******************"+user.getStatus().getKey());
		System.out.println("Lang ******************"+user.getLang());
		System.out.println("Country ******************"+user.getCountry());*/
		System.out.println("Coming to ManageUpdateUser");
		
		
		return usrManageService.getRequestUserInfo(user);
	}
	
	//Update User
	@RequestMapping("/UpdateUserSearch")
	public UserDataTableVo searchUpdateUser(@RequestBody String user)
	{
		/*System.out.println("*********** getUser **********"+user.getWslIdSrch());
		System.out.println("Status ******************"+user.getStatus().getKey());
		System.out.println("Lang ******************"+user.getLang());
		System.out.println("Country ******************"+user.getCountry());*/
		System.out.println("Coming to ManageUpdateUser"+user);
		
		
		//return usrManageService.getManageUserInfo(user);
		return usrManageService.getUpdateUserInfo(user);
	}
	
	@RequestMapping("/UpdateUser")
	public void updateUser(@RequestBody UserAreaVo user)
	{
		System.out.println("*********** updateUserInfo **********"+user.getUserObj().getCdsid());
		 usrManageService.updateUser(user);
		
	}
	@RequestMapping("/SaveUserReg")
	public void saveUserReg(@RequestBody UserAreaVo user)
	{
		System.out.println("*********** saveUserInfo **********"+user.getUserObj().getCdsid());
		 usrManageService.saveUserInfo(user);
		
	}
	@RequestMapping("/getRoles")
	public List<SelectedItem> getRole()
	{
		return usrManageService.getAllRoles();
		
	}
	
	@RequestMapping("/getRoleList")
	public List<String> getRoleList()
	{
		return usrManageService.getRoleList();
		
	}
	@RequestMapping("/getCountriesReg")
	public List<String> getCountry()
	{
		return usrManageService.getAllCountriesPopulate();
		
	}
	@RequestMapping("/getLanguagesReg")
	public List<String> getLanguage()
	{
		return usrManageService.getAllLanguagesPopulate();
		
	}
	@RequestMapping("/getCountries")
	public List<SelectedItem> getCountryPopulate()
	{
		return usrManageService.getAllCountries();
		
	}
	@RequestMapping("/getLanguages")
	public List<SelectedItem> getLanguagePopulate()
	{
		return usrManageService.getAllLanguages();
		
	}
	@RequestMapping("/getAreaCode")
	public List<String> getAreaCode()
	{
		return usrManageService.getAreaCodes();
		
	}
}
