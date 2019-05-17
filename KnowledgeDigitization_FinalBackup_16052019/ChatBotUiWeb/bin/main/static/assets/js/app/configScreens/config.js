/**
 * 
 */


fordApp.controller('configController', ['$scope','$timeout','$translate',//,'blockUI'
	function($scope,$timeout,$translate) {//,blockUI
	
	$scope.configApp=true;
	$scope.configScreen= false;
	$scope.configDB=false;
	$scope.configMod=false;
	$scope.configSubMod=false;
	
	$scope.configApp=true;
    $scope.toggleEvent1=function(){
 	if($scope.configApp){
 		$scope.configApp=true;
 		$scope.configScreen=false;
 		$scope.configDB=false;
 		$scope.configMod=false;
 		$scope.configSubMod=false;
 	}
 
 		
    }

    $scope.toggleEvent2=function(){
 	if($scope.configScreen){
 		$scope.configScreen=true;
 		$scope.configApp=false;
 		$scope.configDB=false;
 		$scope.configMod=false;
 		$scope.configSubMod=false;
 	}
 		
 	
 		
    }
    
    $scope.toggleEvent3=function(){
     	if($scope.configDB){
     		$scope.configDB=true;
     		$scope.configApp=false;
     		$scope.configScreen=false;
     		$scope.configMod=false;
     		$scope.configSubMod=false;
     	}
     		
     	
     		
        }
    
    $scope.toggleEvent4=function(){
     	if($scope.configMod){
     		$scope.configMod=true;
     		$scope.configDB=false;
     		$scope.configApp=false;
     		$scope.configScreen=false;
     		$scope.configSubMod=false;
     	}
     		
     	
     		
        }
    
    $scope.toggleEvent5=function(){
     	if($scope.configSubMod){
     		$scope.configSubMod=true;
     		$scope.configDB=false;
     		$scope.configApp=false;
     		$scope.configScreen=false;
     		$scope.configMod=false;
     		
     	}
     		
     	
     		
        }
    
 	$scope.configApp=true;
 	
 
  
}]);