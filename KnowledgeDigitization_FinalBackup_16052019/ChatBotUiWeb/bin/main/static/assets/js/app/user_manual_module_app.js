fordApp.service('userManualService',['$http',
				function($http) {
					this.getUserManuals = function(endpointURL) {
						return new Promise(function(resolve,reject){
							 $http.get(endpointURL+'/sfh/commonSFH').then(
								function(response){
									resolve(response.data);
								},
								function(error){
				                 alert("error------>");							
									reject([]);
								});
						});
						}		
					
				} ]);
fordApp.directive("userManualDirective",function() {
    return {
        scope:{ name:'@',userManualData:'='},
        scope:true,
        template:'<div align="right"><span ng-repeat="x in userManualData | filter:{module:screenId}:true"><a href="{{x.helpDocLoc}}" id="myAnchor" target="_blank" ng-show="{{x.helpDocRequired}}">Help Doc Required</a>|<a href="{{x.helpVideoLoc}}" target="_blank" ng-show="{{x.helpVideoRequired}}">Help Video Required</a>|<a href="http://35.154.237.162:9060/ChatBotService/sfh/downloadManFile?screenId={{screenId}}" target="_blank" ng-show="{{x.altDocRequired}}">Alternate Doc Required</a>| </span></div>'
   /*     link:{
            pre:function(){ 
            	alert(angular.element(document.querySelector('[ng-app]')).injector().get('$routeParams').screenId);
                    },
            post:function(){ 
            alert(angular.element(document.querySelector('[ng-app]')).injector().get('$routeParams').screenId);
                    }
            
            }*/
    };
});

fordApp.controller('userManualController',['$scope','$rootScope','$http','userManualService','$localStorage','$window',function($scope,$rootScope,$http,userManualService,$localStorage,$window){
	  
	
	if($localStorage.currentUser ==undefined){
		$window.location.href="#!/";
	}
	
	$scope.getUserManuals = function() {
		  userManualService.getUserManuals($rootScope.baseUrl)
			.then(
				function(data){
					$scope.userManualData=data;
					$rootScope.userManualData=data;
					$scope.$digest();
				},
				function()
				{
					alert('error');
				}
				);
			
	  };
	  $scope.getUserManuals();
	  

}]);

//configuring window event

$(function(){
    $("body").keydown(function(event){
    /*	event.preventDefault();*/
    	
         var keyCode = event.keyCode || event.which;
         if(keyCode==112)
        	 {
        var isAlternateDoc = angular.element(document.querySelector('[ng-app]')).injector().get('$rootScope').isAlternateDoc;
        if(isAlternateDoc=='true')
        	{
        var screenId = angular.element(document.querySelector('[ng-app]')).injector().get('$rootScope').screenId;
        if(screenId!=undefined)
        	{
        window.open($rootScope.baseUrl='/sfh/downloadManFile?screenId='+screenId, '_blank');
        	}
        else{
        	alert("User Manual is not available for this screen");
        	}	
        	}
        else{
        	alert("User Manual is not available for this screen");
        }
        
        return false;
        
        	 }	
    });
});

//configuring window event


