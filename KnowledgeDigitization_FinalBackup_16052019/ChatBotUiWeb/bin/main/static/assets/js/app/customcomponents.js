fordApp.directive('multiselect', function() {
	return {
		restrict : 'EA',
		scope : {
			data : '=',
			selectItems : '=',
			checkId : '@'
		},
		templateUrl : '../ChatBotUiWeb/views/template/multiselect.html',
		link : function(scope, elem, attrs) {
			scope.datadup = [];
			scope.searchFilter;
			scope.totallength=[];
			/*angular.forEach(scope.data, function(value) {
				scope.datadup.push(value);
			});*/
			scope.$watch('data', function() {
				scope.searchFilter='';
				scope.datadup = [];
				scope.totallength=[];
				scope.totallength=scope.data;
				angular.forEach(scope.data, function(value) {
					scope.datadup.push(value);
				});
				
				angular.forEach(scope.selectItems, function(value) {
					/*if(!scope.datadup.includes(value))
					scope.datadup.push(value);
					else{
						var index = scope.datadup.indexOf(value);
						scope.datadup.splice(index, 1);
					}*/
					if(scope.datadup.indexOf(value)>=0){
						var index = scope.datadup.indexOf(value);
						scope.datadup.splice(index, 1);
					}
				});	
			},true);
			scope.$watch('selectItems', function() {
				scope.searchFilter='';
				if (scope.selectItems[0] == null) {
					scope.label = "Please Select";
					scope.datadup = [];
					angular.forEach(scope.data, function(value) {
						scope.datadup.push(value);
					});
				}
				if(scope.selectItems.length===1){
					var value1 = scope.selectItems[0];
					var newValue;
					if(value1.length>=15){
						newValue=value1.substring(0,15);
						newValue=newValue.concat("..");
					}else{
						newValue=scope.selectItems[0];
					}
					scope.label = newValue;
				}
				if(scope.selectItems.length > 1){
					scope.label = "Values Selected";
				}
				
			});
			
		
			
		
		//checkbox	
			scope.changeCheckBox = function (k1,$event) {

				if (scope.selectedcheck == true){
                  
					scope.remove(k1)
					$event.stopImmediatePropagation();
					
				}
				else
				{
			
				scope.add(k1)
				}
				
				}
			scope.addchangeCheckBox = function (k1) {

				if (scope.check == true){
					alert("aif")
					scope.add(k1)
				}
				else
				{
					alert("aelse")
				scope.remove(k1)
				}
				}
			
				
			
			
			scope.selectall=function(){
				 if (scope.checkboxEl) {
					
				       scope.checkAll();
				     } else {
				       scope.unCheckAll();
				     }
			}
			scope.add = function(value) {
				scope.searchFilter='';
				scope.selectItems.push(value);
				var index = scope.datadup.indexOf(value);
				scope.datadup.splice(index, 1);
				if (scope.selectItems.length > 1)
					scope.label = "Values Selected";
				else{
					var newValue;
					if(value.length>=15){
						newValue=value.substring(0,15);
						newValue=newValue.concat("..");
					}else{
						newValue=value;
					}
					scope.label = newValue;
				}
				
				if(scope.selectItems.length==scope.totallength.length){
					
					scope.checkboxEl=true;
				}
				if(scope.selectItems.length < scope.totallength.length){
					
					scope.checkboxEl=false;
				}
				scope.selectedcheck=true;
			};

			scope.remove = function(value) {
				scope.searchFilter='';
				var index = scope.selectItems.indexOf(value);
				scope.selectItems.splice(index, 1);
				scope.datadup.push(value);
				if(scope.data.indexOf(value)<0)
				scope.data.push(value);
				
				/*if(!scope.data.includes(value)){
					scope.data.push(value);
				}*/
				if (scope.selectItems.length > 1) {
					scope.label = "Values Selected";
				} else if (scope.selectItems.length = 1) {
//					console.log(scope.selectItems);
					var value1 = scope.selectItems[0];
//					console.log(value1);
					var newValue;
					if(value1!=null && value1!=undefined && value1!=''){
					if(value1.length>=15){
						newValue=value1.substring(0,15);
						newValue=newValue.concat("..");
					}else{
					newValue=value1;
					}
					}
					scope.label = newValue;
				}

				if (scope.selectItems[0] == null) {
					scope.selectItems = [];
					scope.label = "Please Select";
				}
				
				if(scope.selectItems.length==scope.totallength.length){
					
					scope.checkboxEl=true;
				}
				if(scope.selectItems.length < scope.totallength.length){
					
					scope.checkboxEl=false;
				}
				scope.selectedcheck=true;
			}

			scope.checkAll = function() {
				scope.searchFilter='';
				scope.datadup = [];
				scope.datadup = [];
				
				angular.forEach(scope.selectItems, function(value) {
					if(scope.data.indexOf(value)<0)
						scope.data.push(value);
				});
				
				scope.selectItems = [];
				angular.forEach(scope.data, function(value) {
					scope.selectItems.push(value);
				});
				scope.datadup = [];
				scope.datadup = [];
				scope.label = "Values Selected";
			}

			scope.unCheckAll = function() {
				scope.searchFilter='';
				scope.datadup = [];
				angular.forEach(scope.selectItems, function(value) {
					
					if(scope.data.indexOf(value)<0){
						scope.data.push(value);
					}
				});
				
				angular.forEach(scope.data, function(value) {
					scope.datadup.push(value);
				});
				scope.selectItems = [];
				scope.label = "Please Select";
			}

			scope.toggleDropdown = function() {
			scope.open = !scope.open;
			};

		}
	};
});


   
   
   