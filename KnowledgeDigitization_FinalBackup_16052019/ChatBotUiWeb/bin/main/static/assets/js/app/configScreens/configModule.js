/**
 * 
 */

fordApp.controller('moduleController', ['$scope','$timeout','$translate','moduleService','$route','$window','$anchorScroll',
	'$rootScope','$filter','$http',//,'blockUI'
	function($scope,$timeout,$translate, moduleService,$route,$window,$anchorScroll,$rootScope,$filter,$http) {
	
	$scope.selected = []; 
    $scope.dataadd=false;
    $scope.files = [];
    $scope.saveorUpdateFlag = false;
    $scope.asteriskFlag = false;
    $scope.reqErrs = [];
    $scope.appitmsname=[];
    $scope.modulelist=[];
    $scope.modulecodelist=[];
    $scope.itmsList=[];
	$scope.allitmsList=[];
	$scope.modulevo;
    
    $scope.init=function(){
		$scope.message;
		$scope.reqErrs = [];
		$scope.addcollection=[];
		$scope.saveorUpdateFlag = false;
		$scope.selectedMod = [];
		$scope.selectedModcode =[];
		$scope.selectedItmsList=[];
		$scope.modulecodenamelist=[];
		 $scope.selectedModcodeName=[];
		
		moduleService.srchAllItms()
		.then(
			function(data){
				$scope.allitmsList= data;
				$scope.$digest();
			},
			function()
			{
				alert('error');
			});
	
	
		moduleService.getItmsAcrnym().then(
				function(data){
					console.log(data);
			$scope.itmsList=data;
			$scope.$digest();
		},function(){
			alert('error');
		});
		
		
		$scope.search();
		
	}
    $scope.clearMsg = function(){
    	$scope.reqErrs = null;
    }
    
	 $scope.onItmsnoChange=function(itms){
		 $scope.selectedMod=[];
		 $scope.modulelist=[];
		 $scope.selectedModcode=[];
		 $scope.modulecodelist=[];
		 $scope.modulecodenamelist=[];
		 $scope.selectedModcodeName=[];
		
		 
		 moduleService.getmodulelist(itms)
	 			.then(
	 				function(data){
	 					console.log(data);
	 					$scope.modulelist=data;
	 					$scope.$digest();
	 					
	 				},
	 				function(){
	 					alert('error');
	 				});
		 
			 moduleService.getmodcodelist(itms)
				.then(
					function(data){
						$scope.modulecodelist=data;
						$scope.$digest();
						
					},
					function(){
						alert('error');
					});
			 
			 moduleService.getmodcodenamelist(itms)
				.then(
					function(data){
						$scope.modulecodenamelist=data;
						$scope.$digest();
						
					},
					function(){
						alert('error');
					});
			 
			 
			
		 
	 }
	 
	 $scope.$watchCollection('selectedItmsList',function(){
		 $scope.onItmsnoChange($scope.selectedItmsList);
	 });
	 
	 $scope.refresh=function(){
			$scope.reqErrMsg=null;
			$scope.data=[];
			$scope.rowCollection=[];
			$scope.init();
			//$scope.searchDevice();
			$scope.selectedItms = {
					key : null,
					value : "Please Select"
				};
			$scope.modulecodenamelist=[];
			 $scope.selectedModcodeName=[];
			$scope.itmsList=[];
			$scope.addcollection=[];
			$scope.saveorUpdateFlag=false;
			$scope.asteriskFlag=false;
			$scope.message='';
			$scope.reqErrs=[];
		}
	// Function to get data for all selected items
     $scope.selectAll = function (collection) {
       // if there are no items in the 'selected' array, 
       // push all elements to 'selected'
  	   
       if ($scope.selected.length === 0) {
      	 $scope.asteriskFlag = true;
         angular.forEach(collection, function(val) {
          $scope.selected.push(val.id); 
           
         });
       // if there are items in the 'selected' array, 
       // add only those that ar not
       } else if ($scope.selected.length > 0 && $scope.selected.length != collection.length) {
      	 $scope.asteriskFlag = false;
         angular.forEach(collection, function(val) {
           var found = $scope.selected.indexOf(val.id);
           if(found == -1) $scope.selected.push(val.id);
           
         });
         
       // Otherwise, remove all items
       } else  {
      	 $scope.asteriskFlag = false;
         $scope.selected = [];
       }
      
     };
     // Function to get data by selecting a single row
     $scope.select = function(id) {
  	
       var found = $scope.selected.indexOf(id); 
       if(found == -1){
      	 $scope.asteriskFlag = true;
      	 $scope.selected.push(id);
       }
       else {
      	 $scope.selected.splice(found, 1);
      	 if($scope.selected.length == 0	){
      		 $scope.asteriskFlag = false;  
      	 }
          	 
       }
       
     }
     $scope.search=function(){
		 $scope.saveorUpdateFlag = false;
		    $scope.data=[];
			$scope.addcollection = [];
	   	    $scope.rowCollection = [];
	     	$scope.dataadd=false;
	     	$scope.selectdt=false;
	     	$scope.selected=[];
	     	$scope.reqErrMsg="";
	     	$scope.reqErrs = [];
	     	$scope.message=""; 
		    $scope.flag =  false;
		    $scope.asteriskFlag = false;
			$scope.collection = [];
			$scope.selectedData = [];
			
			$scope.moduleobj ={
					 
						'itmsList':$scope.selectedItmsList,
					/* 'moduleList': $scope.selectedMod ,
			         'moduleCodeList':$scope.selectedModcode,*/
			         'moduleCodeNameList':$scope.selectedModcodeName
		    			
		    	}
			
			
			moduleService.fetchmodule($scope.moduleobj)
	  			.then(
	  				function(data){
	  					$scope.data= data;
	  					$scope.rowCollection= data;
	  					console.log(data);
	  					 if($scope.data.length == 0){
	  					  $scope.reqErrMsg = '{{"common.records.notFound" | translate}}';
	  					}
	  				
	  					$scope.$digest();
	  				},
	  				function()
	  				{
	  					alert('error');
	  				}
	  				);
	}
	 $scope.addNewModule = function(){
		 
	     	$scope.data = [];   
	     	$scope.reqErrs = [];
	     	$scope.dataadd=true;
	     	$scope.selectdt=true;
	     	$scope.selected =[];
	     	$scope.asteriskFlag = true;
	     	$scope.reqErrMsg=null;
	     	$scope.succMessage=null;
	     	$scope.message=null;
	     	//$scope.dsidMain.errors = null ;
	     	$scope.addcollection.push({
	     		'id':$scope.addcollection.length+1,
				'module':"",
				'modulecode':"",
				"appItmsNoName":{key:null,value:"Please Select"},
				'isSelected':true
	     });   
	     	angular.forEach($scope.addcollection, function(val) {
	     	     var found = $scope.selected.indexOf(val.id);
	     	      if(found == -1) $scope.selected.push(val.id);
	     	    });

	     	$scope.saveorUpdateFlag = true;
	     	$scope.data=$scope.addcollection;
	     	$scope.rowCollection=$scope.addcollection;
	       };
	       
	       
	       $scope.save=function(collection){
	  		 $scope.flag=false;
	           $scope.reqErrMsg=null;
	           $scope.message=null;
	  	   	   $scope.selectedData = [];		   
	  	   	   $scope.newDataList=[];
	  	       $scope.reqErrs = [];
	  	       //$scope.dsidMain.errors = null ;
	  	   	  	angular.forEach($scope.selected, function(row){	  		
	  	   	        if(row.selected){
	  	   	            row.isSelected=true;
	  	   	        }
	  	   	        else{
	  	   	        	row.isSelected=false;
	  	   	        }
	  	   	    });
	           angular.forEach(collection, function(row){
	           	if($scope.selected.indexOf(row.id) !== -1) {
	           		$scope.newDataList.push(row);
	           		}
	           }); 
	           $scope.selectedData = $scope.newDataList;
	           console.log($scope.selectedData);
	           
	           
	           if(!$scope.selectedData.length == 0){
	    		    if($scope.saveorUpdateFlag){
	    		    	$scope.flag=$scope.validateforAdd($scope.selectedData);
	    		    	if(!$scope.flag){
	    		    		moduleService.savemodule($scope.selectedData)
	    	  	 			.then(
	    	  	 				function(data){
	    	  	 					$scope.modulevo=data;
	    	  	 					if($scope.modulevo.errors.length ==0){
	    	  	 						$scope.refresh();
	    	  	 						$scope.search();
	    	  	  	 					$scope.message = 'Module Saved Successfully';
	    	  	 					}
	    	  	 	 					$scope.selectedData = null;
	    	  	 	 					$window.scrollTo(0, angular.element('#messages').offsetTop); 
	    	  	 	 					$scope.$digest();
	    	  	 				},
	    	  	 				function()
	    	  	 				{
	    	  	 					alert('save eroor');
	    	  	 					$scope.$digest();
	    	  	 				}
	    	  	 				);
	    	  			   
	    		    	}
	    		    	
	    		   }else{
	    			   //for update
	    				$scope.flag=$scope.validateforAdd($scope.selectedData);
	    		    	if(!$scope.flag){
	    		    		moduleService.updatemodule($scope.selectedData)
	    	  	 			.then(
	    	  	 				function(data){
	    	  	 						$scope.modulevo=data;
	    	  	 					if($scope.modulevo.errors.length ==0){
	    	  	 						$scope.refresh();
	    	  	 						$scope.search();
	    	  	  	 					$scope.message = 'Module updated Successfully';
	    	  	 					}
	    	  	 	 					$scope.selectedData = null;
	    	  	 	 					$window.scrollTo(0, angular.element('#messages').offsetTop); 
	    	  	 	 					$scope.$digest();
	    	  	 					
	    	  	 					
	    	  	 				},
	    	  	 				function()
	    	  	 				{
	    	  	 					alert('update eroor');
	    	  	 					$scope.$digest();
	    	  	 				}
	    	  	 				);
	    	  			   
	    		    	}
	    		   }
	           }
	  		
	  	}
	  	 
	       $scope.validateforAdd=function($selecteddata){

	    	   $scope.mandFlag=false;
	    	   $scope.flag=false;
	    	   $scope.reqErrMsg="";
	    	   $scope.reqErrs = [];
	    	   
	    	   angular.forEach($selecteddata,function(row){
	    		   $scope.mandFlag=false;
		    	   $scope.flag=false;
		    	   if(row.appItmsNoName.key==null || row.appItmsNoName.key==''||row.appItmsNoName.value=='Please Select')
	       			 {
	       			    $scope.reqErrMsg = 'ITMS No.';
	          	            $scope.flag = true;
	          	          $scope.mandFlag = true;
	       			 }
	       		 
	       		 
	       		 
	       		 if(row.module==null || row.module=='')
	   			 {
	      	            if($scope.flag)
	      	            	{
	      	             $scope.reqErrMsg += ', Module';
	      	            	}else
	      	            		{
	      	            	 $scope.reqErrMsg = 'Module';
	            			  $scope.flag = true;
	      	            		}
	      	          $scope.mandFlag = true;
	   			 }
	       		 
	       		
	       		 
	       		 
	       		 
	       		 if(row.modulecode==null || row.modulecode=='')
	   			 {
	      	            if($scope.flag)
	      	            	{
	      	             $scope.reqErrMsg += ', Module Code';
	      	            	}else
	      	            		{
	      	            	 $scope.reqErrMsg = ' Module Code';
	            			  $scope.flag = true;
	      	            		}
	      	          $scope.mandFlag = true;
	   			 }
	       		 
	       		if($scope.mandFlag){
	   			   $scope.reqErrMsg +=  ' Cannot be blank' ;
	   		   }
	       		if(row.modulecode!=null && row.modulecode!='')
	       		{
	       			
	       			if(alphaCharacterValidation(row.modulecode))
	       			{

	       				if($scope.flag)
	       				{
	       					$scope.reqErrMsg += ',Module Code Should contain only alphabets';
	       				}else
	       				{
	       					$scope.reqErrMsg = ' Module Code  Should contain only alphabets';
	       					$scope.flag = true;
	       				}


	       			}else{
	       				if(row.modulecode.length>5){
		       				if($scope.flag)
		       				{
		       					$scope.reqErrMsg += ',Module Code Should not be greater than 5';
		       				}else
		       				{
		       					$scope.reqErrMsg = ' Module Code  Should not be greater than 5';
		       					$scope.flag = true;
		       				}
		       			}
	       			}

	       		}
	       		if(row.module!=null && row.module!='')
	  			 {
	       			if(alphaCharacterValidation(row.module))
	       				{
	       				if($scope.flag)
	 	            	{
	 	             $scope.reqErrMsg += ', Module should contain only alphabets';
	 	            	}else
	 	            		{
	 	            	 $scope.reqErrMsg = 'Module should contain only alphabets';
	       			  $scope.flag = true;
	 	            		}
	       				}
	     	            
	  			 }
	       		
	     		 
	       		if($scope.flag)  
	       		 {
	               if($scope.reqErrMsg !==null)
	       				 {
	            	   $scope.reqErrMsg='Line.No '+row.id+" "+$scope.reqErrMsg;
	            	  
	            	   
	             }            
	             
	             }
	       		if( $scope.reqErrMsg!==""){
		       		$scope.reqErrs.push($scope.reqErrMsg);
		       		$scope.reqErrMsg= null;
	       		 }
	    	   });
	    	   if( $scope.reqErrs.length>0 ){
	    		   $scope.flag = true;  
	    		   $window.scrollTo(0, angular.element('#messages').offsetTop); 
	    	   }
	    	   
	    	  
	    	   
	    	   return $scope.flag;
	    	   
	       
	       }
	       
	       $scope.downloadExcel =function(){
	    		  $http.post("http://35.154.237.162:9060/ChatBotService/configmodule/downloadExcel",$scope.rowCollection).then(function(response) {
	    	          if (response) {
	    	        	  var dt = new Date(); 
	    	        	     $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
	    			 		$scope.fileName = $scope.createdDate+"Module Configuration";
	    				
	    	             
	    	                 
	    	           var anchor = angular.element('<a/>');
	    	              anchor.attr({
	    	                  href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + response.data,
	    	                  target: '_self',
	    	                  download: $scope.fileName});

	    	              angular.element(document.body).append(anchor);
	    	              anchor[0].click();
	    	              
	    	           $scope.succMessage = '{{"common.fileUpload.Success"  | translate}}';
	    	              angular.element('#ModuleUploadModalSingle').modal('hide');
	    	              $window.scrollTo(0, angular.element('#messages').offsetTop);
	    	             $scope.search();
	    	          } else {
	    	          $window.scrollTo(0, angular.element('#messages').offsetTop);
	    	          $scope.reqErrMsg = '{{"common.fileUpload.fail"  | translate}}';
	    	          }

	    	      });
	    	 		
	    	 	  }
	       
	       $scope.DownloadTemplate =function(){
 		      		  moduleService.downloadTemplate()
	    	 			.then(
	    	 				function(data){
	    	 					
	    	 		           	  var fileName='Module Maintenance Template';
	    	 		                    
	    	 		              var anchor = angular.element('<a/>');
	    	 		                 anchor.attr({
	    	 		                     href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + data,
	    	 		                     target: '_self',
	    	 		                     download: fileName});

	    	 		                 angular.element(document.body).append(anchor);
	    	 		                 anchor[0].click();
	    	 		                 $scope.succMessage = '{{"common.fileUpload.Success"  | translate}}';
	    	 		                 angular.element('#ModuleUploadModalSingle').modal('hide');
	    	 		                 $window.scrollTo(0, angular.element('#messages').offsetTop);
	    	 		                
	    	 		             },
	    	 				function()
	    	 				{
	    	 					alert('error');
	    	 				}
	    	 				);
	    	     	  
 	    	
 	     	 
 	   	  };
 	   	  
 	   	  $scope.clearFile=function(){
 			 
 			 $scope.errmsg=""
 		  	 $scope.SingleMessage = "";
 		  	 $scope.ErrorMessage ="";
 		  	 angular.element("input[type='file']").val(null);
 		  	 $scope.files = [];
 		 }
     
 	   	$scope.getExcelFileDetails = function(e) {     
			$scope.SingleMessage = "";
	          $scope.$apply(function() {
	              
	              var fileType = e.files[0].name.substring(e.files[0].name.lastIndexOf("."), e.files[0].name.length);
	              if ( fileType == '.xlsx') {
	                    
	                 /* if (((e.files[0].size / 1024) / 1024) > 4) {
	                      $scope.SingleMessage = "Maximum Size of File allowed is 4MB";
	                      $window.scrollTo(0, angular.element('#messages').offsetTop);
	                  } else {*/
	                		$scope.SingleMessage = "";
	                      $scope.files.push(e.files[0]);
	                 /* }*/
	              } else {
	                  $scope.SingleMessage = 'common.fileType.excel' ;
	                  
	                  $window.scrollTo(0, angular.element('#messages').offsetTop);
	                 
	              }

	          });
	          
	      
	  };
	 
	 $scope.openSinglePopforMainModule = function() {
		 //alert("hi");
		 $scope.files = [];
		 angular.element("input[id='single']").val(null); 
		  angular.element('#ModuleUploadModalSingle').modal('show');
	        //$route.reload();
	    }
	  
	 $scope.SingleMessage = "";
	  
	  $scope.excelUploadFile = function() {
	      $scope.FileMessage = "";
	  	 $scope.SingleMessage = "";
	     var data = new FormData();
	      data.append("file", $scope.files[0]);

	      var config = {
	          transformRequest: angular.identity,
	          transformResponse: angular.identity,
	          headers: {
	              'Content-Type': undefined
	          }
	      }
	      if($scope.files.length==0){
				$scope.SingleMessage='common.file.upload.not';
			}
			else{
	      $scope.fileName = $scope.files[0].name;
	      if ($scope.fileName.startsWith('Module Maintenance Template'))
	      {
	    	  $http.post("http://35.154.237.162:9060/ChatBotService/configmodule/ExcelUpload", data, config).then(function(response) {
	              if (response) {
	            	  var dt = new Date(); 
	         	     $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
	 		 		var fileName = $scope.createdDate+"Module Maintenance_Log";
	            	
	               var anchor = angular.element('<a/>');
	                  anchor.attr({
	                      href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + response.data,
	                      target: '_self',
	                      download: fileName});

	                  angular.element(document.body).append(anchor);
	                  anchor[0].click();
	                  
	               $scope.succMessage = '{{"common.fileUpload.Success"  | translate}}';
	                  angular.element('#ModuleUploadModalSingle').modal('hide');
	                  $window.scrollTo(0, angular.element('#messages').offsetTop);
	                  $scope.search();
	              } else {
	              $window.scrollTo(0, angular.element('#messages').offsetTop);
	              $scope.reqErrMsg = '{{"common.fileUpload.fail"  | translate}}';
	              }

	          });
	      }
	      else  
	      {
	    	  angular.element('#ModuleUploadModalSingle').modal('hide');
	    	  $scope.reqErrMsg =  '{{"conifgmodule.uploadVal.validat"  | translate}}';
	      }

		} 
	      

	  }
	
	
}]);

fordApp.service("moduleService",['$http',function($http){
	
this.downloadTemplate = function(data) { 
		
		return new Promise(function(resolve,reject){
			$http.get('http://35.154.237.162:9060/ChatBotService/configmodule/downloadTemplate')
			.then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}
	
	
this.savemodule = function(data) { 
		
		return new Promise(function(resolve,reject){
			$http.post('http://35.154.237.162:9060/ChatBotService/configmodule/savemodules',data)
			.then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}
this.updatemodule = function(data) { 
	
	return new Promise(function(resolve,reject){
		$http.post('http://35.154.237.162:9060/ChatBotService/configmodule/updatemodules',data)
		.then(
			function(response){
				resolve(response.data);
			},
			function(error){
				reject([]);
			});
	});
	}
	
this.getItmsAcrnym = function() { 
		
		return new Promise(function(resolve,reject){
			
			$http.post('http://35.154.237.162:9060/ChatBotService/configmodule/getItmsNoAndAcrnym')
			.then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}
	
this.srchAllItms = function(data) { 
	
	return new Promise(function(resolve,reject){
		
		$http.post('http://35.154.237.162:9060/ChatBotService/configmodule/getItmsNoAndAppName',data)
		.then(
			function(response){
				resolve(response.data);
			},
			function(error){
				reject([]);
			});
	});
	}
this.getmodcodelist = function(data) { 
	
	return new Promise(function(resolve,reject){
		$http.post('http://35.154.237.162:9060/ChatBotService/configmodule/getModuleCode',data)
		.then(
			function(response){
				resolve(response.data);
			},
			function(error){
				reject([]);
			});
	});
	}

this.getmodcodenamelist = function(data) { 
	
	return new Promise(function(resolve,reject){
		$http.post('http://35.154.237.162:9060/ChatBotService/configmodule/getModuleCodeName',data)
		.then(
			function(response){
				resolve(response.data);
			},
			function(error){
				reject([]);
			});
	});
	}
this.fetchmodule = function(data) { 
	
	return new Promise(function(resolve,reject){
		$http.post('http://35.154.237.162:9060/ChatBotService/configmodule/fetchmodules',data)
		.then(
			function(response){
				resolve(response.data);
			},
			function(error){
				reject([]);
			});
	});
	}
this.getmodulelist = function(data) { 
	
	return new Promise(function(resolve,reject){
		$http.post('http://35.154.237.162:9060/ChatBotService/configmodule/getParentModule',data)
		.then(
			function(response){
				resolve(response.data);
			},
			function(error){
				reject([]);
			});
	});
	}

}]);
