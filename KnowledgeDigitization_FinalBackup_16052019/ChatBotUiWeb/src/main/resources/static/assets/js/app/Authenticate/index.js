fordApp.controller('DaController',['$scope','$rootScope','$localStorage','$window','auth',function($scope,$rootScope,$localStorage,$window,auth){
	$scope.init=function(){
		//alert($localStorage.currentUser)
		$scope.googleshow=true
		if($localStorage.currentUser !=undefined){
			$window.location.href ='#!/home';
		}
		
	}
	 
	 $rootScope.user="";
	$scope.gmail={
			userName:"",
			email:""
			
	};
	
	$scope.regUser;
	$scope.regUser = {
			country : {
				key : null,
				value : "Please Select"
			},
			lang : {
				key : null,
				value : "Please Select"
			},
			status : {
				key : "Y",
				value : "Active"
			},
			role : {
				key : null,
				value : "Please Select"
			}
		}
/*	$rootScope.google=function(){
		$scope.googleshow=false
	}*/
	
	
	
	
	
	$scope.ssoUserRegSave=function($window,$location)
	{
    	 $scope.formVal ={
					'userObj':$scope.regUser
			}
    	 
    	/* $scope.formVal ={
					'userObj':$scope.regUser,
					'selectedList':$scope.areaSelectedList
			}*/
    	$scope.flag=$scope.validate();	
   	 if(!$scope.flag)
			{
   		auth.saveUserService($scope.formVal)
			.then(
				function(data){
					
					$scope.message='User Successfully Registered';
					
					/* $scope.loginShow=true;*/
					 $scope.$digest();
					
					
					
						},
				function()
				{
					alert('error');
					
				}
				);
			}
		
	}
    //validate page
	$scope.validate=function()
    {
    	  $scope.flag = false;
    	  $scope.reqErrMsg=null;
    	  $scope.message=null;
    	  if($scope.regUser==null || $scope.regUser.cdsid == '' || $scope.regUser.cdsid == null){
  	        $scope.reqErrMsg = '{{"User.CdsID.Lable" | translate}}';
  	        $scope.flag = true;
  	  }
 
    	  if($scope.regUser==null || $scope.regUser.wPhone == '' || $scope.regUser.wPhone == null){
    		  if($scope.flag) {
    	        $scope.reqErrMsg += ', {{"User.workPhone.Lable" | translate}}';
    		  }else{
    			  $scope.reqErrMsg = '{{"User.workPhone.Lable" | translate}}';
    	          $scope.flag = true;
    	  }
    	  }
    	  if($scope.regUser==null || $scope.regUser.addr1 == '' || $scope.regUser.addr1 == null){
    		  if($scope.flag) {
    	        $scope.reqErrMsg += ', {{"User.addr1.Lable" | translate}}';
    		  }else{
    			  $scope.reqErrMsg = '{{"User.addr1.Lable" | translate}}';
    	          $scope.flag = true;
    	  }
    	  }
    	  if($scope.regUser==null || $scope.regUser.city == '' || $scope.regUser.city == null){
    		  if($scope.flag) {
    	        $scope.reqErrMsg += ', {{"User.city.Lable" | translate}}';
    		  }else{
    			  $scope.reqErrMsg = '{{"User.city.Lable" | translate}}';
    	          $scope.flag = true;
    	  }
    	  }
    	 if($scope.regUser==null || $scope.regUser.country.key == '' || $scope.regUser.country.key == null){
  		  if($scope.flag) {
  			  $scope.reqErrMsg += ', {{"User.country.Lable" | translate}}';
  		  }else{
  			  $scope.reqErrMsg = '{{"User.country.Lable" | translate}}';
  			  $scope.flag = true;
  		  }
 	     }
    	 if($scope.regUser==null || $scope.regUser.lang.key == '' || $scope.regUser.lang.key == null){
   		  if($scope.flag) {
   			  $scope.reqErrMsg += ', {{"User.lang.Lable" | translate}}';
   		  }else{
   			  $scope.reqErrMsg = '{{"User.lang.Lable" | translate}}';
   			  $scope.flag = true;
   		  }
  	     }
    
    	 if($scope.flag)  {
    		$scope.reqErrMsg += ' {{"User.save.req.err" | translate}}';
    	 }
    	
    	  return $scope.flag;
    	}

	
	
	    $rootScope.Logout=function(a) {
           // remove user from local storage and clear http auth header
	    	if(a==1){
           delete $localStorage.currentUser;
           $rootScope.user=""
           localStorage.removeItem('currentUser')
           $window.location.href = '#!/'   
	    	}
	    	a++;
       }
	    
	    $scope.getData=function(){
	    	
	    	$scope.regUser.fName=""
	    		auth.getCountriesRegs().then(

	    				function(data){ 
	    					 $scope.countries=arrayToSingleSelect(data);
	    					$scope.$digest();
	    				},
	    				function()
	    				{
	    					alert(' error');
	    				}
	    				
	    			  );
	    		auth.getLanguagesRegs().then(

	    				function(data){ 
	    					 $scope.languages=arrayToSingleSelect(data);
	    					$scope.$digest();
	    				},
	    				function()
	    				{
	    					alert('error');
	    				}
	    				
	    			  );
	    
	    		
	    	$scope.regUser.fName=$rootScope.SSOUser.name.givenName;
	    	/*$scope.regUser.uName=$rootScope.SSOUser.displayName;*/
	    	$scope.regUser.email=$rootScope.SSOUser.emails[0].value;
	    	$scope.regUser.lName=$rootScope.SSOUser.name.familyName;

	    }
	    
	    
	    
	    
	$scope.googleLogin=function(){
		var params={
				'clientid':'512115741883-s0f38pvcna9n8a2hi39po2cc41s3m4hn.apps.googleusercontent.com',
				'cookiepolicy':'single_host_origin',
				
				'callback':function(result){
					if(result['status']['signed_in']){
						var request=gapi.client.plus.people.get({
							'userId':'me'
						});
						request.execute(function(resp){
							$scope.$apply(function(){
							console.log(resp)
							if(resp.domain =="thirdware.com"){
							
								auth.checkUser(resp.emails[0].value).then(function(data){
									console.log(data)
									if(data.firstName != null){
										
											if (data.activeStatus == 'Y' && data.userRegStatus == 'P') {
											// pending page
											$window.location.href = 'views/user/userRegInProcess.html';
										} else if (data.activeStatus == 'N') {
											// Inactive page
											$window.location.href = 'views/user/userInactiveErrorPage.html';
										}
										else if(data.activeStatus =='Y' && data.userRegStatus =='A'){
											
										
											
										$rootScope.userSecurity=resp.displayName;
									// delete $localStorage.currentUser
									$localStorage.currentUser=""
										$localStorage.cdsid=""
									 $localStorage.currentUser=resp.displayName;
									console.log(data.cdsid)
									$localStorage.cdsid=data.cdsid
									$rootScope.user=resp.displayName;
									$rootScope.userCdsid=data.cdsid;
									
									/*$scope.gmail.email=resp.emails.value;*/
									 $rootScope.security=true;
									 $rootScope.authorize();
									
									$window.location.href = '#!/home'
									}
									}
									else{
									
									 
										$rootScope.security=false;
										$rootScope.SSOUser=resp;
										$rootScope.$digest();
										$window.location.href ="#!/Ssouser"
											
									}
									
								},function(){
									alert('if error')
								});
								
							}
							else{
								//gapi.auth.signOut();
								$window.location.href="#!/"
									
							}
							});
						});
					}
				},
				'approvalprompt':'force',
				'scope':'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/plus.profile.emails.read'
		};
		gapi.auth.signIn(params);
		
	}
	
}]);



fordApp.service('auth',['$http','$rootScope',function($http,$rootScope){
	
	this.checkUser=function(email){
		return new Promise(function(resolve,reject){
			$http.get($rootScope.baseUrl+'/authC/checkUser?email='+email).then(function(response){
				resolve(response.data)
			},function(error){
				reject([]);
			})
		});
	}
	
	
	this.getCountriesRegs=function()
	{
      return new Promise(function(resolve,reject){
			
			$http.post($rootScope.baseUrl+'/User/getCountriesReg').then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
	}
	this.getLanguagesRegs=function()
	{
      return new Promise(function(resolve,reject){
			
			$http.post($rootScope.baseUrl+'/User/getLanguagesReg').then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
	}
	
	this.saveUserService=function(data)
	{
		
        return new Promise(function(resolve,reject){
			
			$http.post($rootScope.baseUrl+'/User/SaveUserReg',data).then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
	}
	
	
}]);