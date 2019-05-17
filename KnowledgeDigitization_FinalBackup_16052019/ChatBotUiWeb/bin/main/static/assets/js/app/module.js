
var mod=angular.module("myapp",[]);

mod.controller('one',['$scope','SharedService',function($scope,SharedService)
{
$scope.data="DIV 1 Controller.........";
$scope.things=["One","Two","Three"];
$scope.handle=function(){
	$scope.things.push($scope.current);
	$scope.current="";
}

$scope.people=SharedService.getData();

}]);

mod.controller('two',['$scope','SharedService',function($scope,SharedService)
{
$scope.info="DIV 2 Controller.......";
$scope.addPerson=function(){
	var temp={sno:$scope.sNo,name:$scope.name};
	SharedService.dataStore.push(temp);
	$scope.sNo="";
	$scope.name="";
}

}]);


mod.controller('rest',['$scope','dataService',function($scope,dataService){
	
	dataService.getPeople().then(
			function(data) {
				$scope.personarray=data;
				$scope.$digest();
			},function(error)
			{
				$scope.personarray=data;
				$scope.$digest();
			}
	);
	
	/*$scope.store=function({
		var data={name:$scope.name,city:$scope.city};
		dataService.addData(data).then(
		function(){
			
			alert('added');
		},
		function()
		{
			alert('error');
		}
		);
		
		
	});*/
	
}]);

//To get external data eg: from Rest
mod.service("SharedService",function(){
	this.dataStore=[{sno:11,name:"Bharath"},
		{sno:12,name:"Gnana"}]
	this.getData=function(){
		return this.dataStore;
	}
	
});

/*mod.service("dataService",['$http',function($http,$scope) {

	this.getPeople=function(){ new Promise(function(resolve,reject) {
	$http.get("https://rest-first.cfapps.io/restdata/repos/people")
.then(function(response){
	
	resolve(response.data);
	
		},function(error) {
	
			reject([]);
		});
	});
	}
}]);*/


mod.service("dataService",['$http',function($http){
	this.getPeople = function() { return new Promise(function(resolve,reject){
	$http.get('https://restsumantht.cfapps.io/restdata/repos/people').then(
	function(response){
	resolve(response.data);
	},
	function(error){
	reject([]);
	});
	});
	}

	this.addData=function(data)
	{
		return new Promise(function(resolve,reject){
			$http.post('https://restsumantht.cfapps.io/restdata/repos/people',data).then(
			function(response){
			resolve(response.data);
			},
			function(error){
			reject([]);
			});
			});
	}
	
}]);



