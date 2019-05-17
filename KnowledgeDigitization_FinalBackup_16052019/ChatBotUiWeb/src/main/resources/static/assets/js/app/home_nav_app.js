/**
 * 
 */

/*
 * var fordApp = angular.module('fordApp', ['ngRoute','smart-table',
 * 'ui.bootstrap','blockUI','pascalprecht.translate','treeApp','pdf']);
 */
var fordApp = angular.module('fordApp', [ 'ngRoute', 'smart-table',
		'ui.bootstrap', 'pascalprecht.translate',  'pdf',//'blockUI',
		'ngSanitize','angular-web-notification','indexedDB','ngStorage']);

fordApp.run(function ($rootScope,$localStorage,$window) {
    $rootScope.envFlag = false;
    
    if($localStorage.currentUser == undefined){
    	 
    	$window.location="#!/";
    	$rootScope.security=false;
    		}
    else{
    	
    	$rootScope.security=true;
    	
    }
    
});

// Configuring i18n
fordApp.config([ "$translateProvider", function($translateProvider) {

	$translateProvider.useStaticFilesLoader({
		prefix : 'i18n/lang_',
		suffix : '.json'
	});

	$translateProvider.preferredLanguage('en');

} ]);

// configure our routes
fordApp.config(function($routeProvider) {
	$routeProvider
	.when('/configure', {
		templateUrl : 'views/configureScreens/config.html',
		controller : 'configController'
	})
	
	// route for the Application maintenance page
	.when('/applicationMaintenance', {
		templateUrl : 'views/application/applicationMaintenance.html',
		controller : 'applicationController'

	})
	
	// route for the Configuration User Manual page
	.when('/configUsrManual', {
		templateUrl : 'views/configUserManual/configUsrManual.html',
		controller : 'configUsrManualController'

	})
	
	// role maintenance And Role Map Configuration
	.when('/roleMaintenance', {
		templateUrl : 'views/user/roleMaintenance.html',
		controller : 'roleMaintenanceCtrl'
	})
	
	// route for the line page
	.when('/user/:screenName', {
		templateUrl : 'views/user/userRegistration.html',
		controller : 'UserRegController'

	})
	
	// Request Request Controller
	.when('/requestUserSearch/:screenName', {
		templateUrl : 'views/user/newUserRequest.html',
		controller : 'requestUserDetailsDatatable'

	})
	
	// manage User Search
	.when('/manageUserSearch/:screenName', {
		templateUrl : 'views/user/manageUser.html',
		controller : 'manageUserController'
	})
	// manage User Update 
	.when('/manageUserUpdate', {
		templateUrl : 'views/user/manageUserUpdate.html',
		controller : 'manageUserUpdateController'

	})
	.when('/', {
		templateUrl : 'views/Authenticate/index1.html',
		controller : 'DaController'
	})
	// route for the home page
	.when('/login', {
		templateUrl : 'views/Authenticate/login.html',
		controller : 'loginController'
	})
	.when('/Ssouser', {
		templateUrl : 'views/user/ssoUserRegistration.html',
		controller : 'DaController'

	})
	// Update user Controller
	.when('/updateUserDetails/:screenName', {
		templateUrl : 'views/user/userUpdateProfile.html',
		controller : 'UpdateUserController'
	})
	// Approve/Deny user Controller
	.when('/requestUserApprove', {
		templateUrl : 'views/user/newUserApproveOrDeny.html',
		controller : 'approveDenyUpdateController'

	}).when('/sfh/:screenName', {
		templateUrl : 'views/screenFunctionalityHelp.html',
		controller : 'sfhContoller'
	}) 
	
	// route for the home page
	.when('/home', {
		templateUrl : 'views/background_gif.html',
		controller : 'homeController'
	});
	//
});

fordApp
		.controller(
				"homeController",
				[
						"$localStorage","$routeParams","$scope",
						"$window","$interval",
						"homeService",
						"$translate",
						"$rootScope","$indexedDB",//"blockUI",
						function($localStorage,$routeParams,$scope,$window,$interval,
								homeService, $translate, 
								 $rootScope,$indexedDB) {//,,blockUI
							
							$rootScope.baseUrl=""
						        
						        if($rootScope.envFlag){
						            console.log($rootScope.envFlag)
						           $rootScope.baseUrl= "http://35.154.237.162:9070/ChatBotService";
						        }else{
						            console.log($rootScope.envFlag)
						            $rootScope.baseUrl= "http://localhost:9070/ChatBotService";
						        }
							
							$scope.changeLanguage = function(lang) {
								$translate.use(lang);
							}
							
							$rootScope.userCdsid = $localStorage.cdsid;
							$rootScope.userId = $scope.userCdsid;
							
							
							$scope.$on('$routeChangeSuccess', function () {
								 $rootScope.screenId=undefined;
								 $rootScope.selectedScreen=undefined;
								 $rootScope.isAlternateDoc=false;
									angular.forEach($rootScope.userManualData, function (value, key) {
										if(value.selectedScreen==$routeParams.screenName)
											{
											 $rootScope.screenId=value.module;
											 $rootScope.selectedScreen=value.selectedScreen;
											 $rootScope.isAlternateDoc=value.altDocRequired;
											}
										
									});
									
									if(!$rootScope.screenId){
										$rootScope.screenId='NA';
								      }
								});
							
							$scope.init=function(){
								//alert($localStorage.currentUser)
								if($localStorage.currentUser ==undefined){
									    	
									 
						    	$window.location="#!/";
							}
								
							}
							$scope.logout=function(){
								delete $localStorage.currentUser;
								delete $localStorage.cdsid;
								$rootScope.security=false;
								$window.location="#!/";
								$rootScope.userCdsid=""
							}
							$scope.login=function(){
							
								$window.location="#!/";
								
							}


							
							$scope.isAuthorized = function(screenN, functionN) {
								$scope.flag = false;
								$scope.keepgoing = true;
								angular.forEach($rootScope.screenFunctionList,
												function(row) {
													if ($scope.keepgoing) {
														if (row.screenName == screenN && row.functionName == functionN) {
															// console.log(row.screenName,screenN);
															$scope.flag = true;
															$scope.keepgoing = false;
														}
													}
												});
								// console.log($scope.flag);
								return $scope.flag;
							}
							
							if($localStorage.currentUser!=undefined){
								homeService.getAuthScreenNames($rootScope.userCdsid)
								.then(function(data) {
									$rootScope.screenFunctionList = data;
									
									$rootScope.$digest();
									
									// console.log(data);
									
								}, function() {
									alert('error');
								});
							}
						
							$rootScope.authorize=function(){
							homeService.getAuthScreenNames($rootScope.userCdsid)
							
							.then(function(data) {
								$rootScope.screenFunctionList = data;
								$rootScope.$digest();
								// console.log(data);
								$scope.$digest();
							}, function() {
								alert(' auht error');
							});
						}
							
						}]);


		fordApp.service("homeService",['$http','$rootScope',function($http,$rootScope) {
			
			this.getAuthScreenNames = function(data) {
				return new Promise(function(resolve, reject) {
					$http.post($rootScope.baseUrl+'/Auth/getAuthScreenList',data).then(function(response) {
								resolve(response.data);
							}, function(error) {
								reject([]);
							});
				});
			}	
						
		}]);

/*fordApp.config(function ($indexedDBProvider) {
    $indexedDBProvider
      .connection('notificationDB')
      .upgradeDatabase(1, function(event, db, tx){
        var objStore = db.createObjectStore('notification', {keyPath: 'client'});
        objStore.createIndex('sakey_idx', 'sakey', {unique: false});
        objStore.createIndex('fault_idx', 'fault', {unique: false});
        objStore.createIndex('count_idx', 'count', {unique: false});
      });
  });*/

/*fordApp.config(["blockUIConfig", function (blockUIConfig) {
    blockUIConfig.requestFilter = function (config) {
    //Perform a global, case-insensitive search on the request url for 'noblockui' ...
    if (config.url.match(/noblockui/gi)) {
        return false; // ... don't block it.
    }
};}]);*/
		
		window.onhelp = function() {
		    return false;
		};
		window.onkeydown = evt => {
		    switch (evt.keyCode) {
		        case 112:
		            this.onF1();
		            break;
		        //Fallback to default browser behaviour
		        default:
		            return true;
		    }
		    //Returning false overrides default browser event
		    return false;
		};


