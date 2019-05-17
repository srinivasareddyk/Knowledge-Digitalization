fordApp.controller('loginController',['$scope','$rootScope','$window','$localStorage','loginService',function($scope,$rootScope,$window,$localStorage,loginService){
	
	$scope.googleshow=false;
	$rootScope.userSecurity="";
	$scope.init=function(){
			//$scope.googleshow=false;
			
			//$rootScope.google();
		if($localStorage.currentUser !=undefined){
			$window.location.href="#!/home";
		}
		
	}
	
	$scope.login=function(){
		
		
		if($scope.userName == undefined){
			$scope.reqErrMsg="Please enter Username"
		}
		
		if( $scope.userName != undefined){
		loginService
		.getUserStatus($scope.userName,$scope.password)
		.then(
				function(data) {
				
					$scope.userStatus = data;
					console.log(data)
					if ($scope.userStatus.activeStatus == 'Y'
							&& $scope.userStatus.userRegStatus == 'P') {
						// pending page
						$rootScope.security=false;
						$window.location.href = 'views/user/userRegInProcess.html';
					} else if ($scope.userStatus.activeStatus == 'N'
							&& $scope.userStatus.userRegStatus == 'A') {
						// Inactive page
						$rootScope.security=false;
						$window.location.href = 'views/user/userInactiveErrorPage.html';
					}
					
					
					else if($scope.userStatus.firstName != undefined && $scope.userStatus.activeStatus == 'Y' && $scope.userStatus.userRegStatus == 'A'){
						
						 $localStorage.currentUser="";
						 $localStorage.cdsid=""
						$rootScope.userSecurity=$scope.userStatus.firstName;
						$localStorage.currentUser=$scope.userStatus.firstName
						 $rootScope.user=$scope.userStatus.firstName
						 $rootScope.userCdsid=$scope.userStatus.cdsid
						 $localStorage.cdsid=data.cdsid
						 $rootScope.security=true;
						$rootScope.authorize();
					
						$window.location.href='#!/home';
						
						}
					if($scope.userStatus.credentials !=null)
						{
							$scope.reqErrMsg="Invalid Credentials"
						}
					
					
				
					$scope.$digest();
				}, function() {
					alert('error');
				}

		);
		}
	}
	
	
	
}]);

fordApp.service('loginService',['$http','$rootScope',function($http,$rootScope){
	

	this.getUserStatus = function(username,password) {
	
		return new Promise(
				function(resolve, reject) {

					$http.post($rootScope.baseUrl+'/authC/getUserStatus?cdsid='+username+'&password='+password)
							.then(function(response) {
								resolve(response.data);
							}, function(error) {
								reject([]);
							});
				});
	}
	
}]);

