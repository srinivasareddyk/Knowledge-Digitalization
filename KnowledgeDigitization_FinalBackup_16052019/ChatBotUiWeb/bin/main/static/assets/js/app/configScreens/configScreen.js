/**
 * 
 */
fordApp.controller('screenController', ['$scope','$timeout','$translate','screenService','$route','$window','$anchorScroll',
	'$rootScope','$filter','$http',//,'blockUI'
	function($scope,$timeout,$translate, screenService,$route,$window,$anchorScroll,$rootScope,$filter,$http) { //blockUI,
	
	$scope.allitmsList=[];
	$scope.selected = []; 
    $scope.dataadd=false;
    $scope.files = [];
    $scope.saveorUpdateFlag = false;
    $scope.asteriskFlag = false;
    $scope.reqErrs = [];
    $scope.appitmsname=[];
    $scope.modulelist=[];
    $scope.submodulelist=[];
    $scope.itmsList=[];
    $scope.screenvo;
    
	$scope.init=function(){
		$scope.message;
		$scope.reqErrs = [];
		$scope.addcollection=[];
		$scope.saveorUpdateFlag = false;
		$scope.selectedMod = [];
		$scope.selectedSubMod =[];
		$scope.selectedScreen =[];
		
		/*$scope.selectedItms = {
					key : null,
					value : "Please Select"
				};*/
		
		$scope.selectedItmsList=[];
		screenService.srchAllItms()
			.then(
				function(data){
					$scope.allitmsList= data;
					$scope.$digest();
				},
				function()
				{
					alert('error');
				});
		
		screenService.getItmsAcrnym().then(
				function(data){
					console.log(data);
			$scope.itmsList=data;
			$scope.$digest();
		},function(){
			alert('error');
		});
		
		
		
		
		$scope.search();
		
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
					 
					 //'appItmsName':$scope.selectedItms ,
						'itmsList':$scope.selectedItmsList,
					 'moduleList': $scope.selectedMod ,
			         'submoduleList': $scope.selectedSubMod,
			         'screenNames':$scope.selectedScreen
		    			
		    	}
			
			
			screenService.fetchmodule($scope.moduleobj)
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
	
	 $scope.clearMsg = function(){
	    	$scope.reqErrs = null;
	    }
	 
	 $scope.showRecordsInDatatable=function(selectedata){
		 angular.forEach(selectedata,function(values){
			 if(!$scope.selectedItmsList.includes(values.appItmsNoName.value)){
				 $scope.selectedItmsList.push(values.appItmsNoName.value);
			 }
			 $scope.selectedScreen.push(values.screenName);
		 });
		 console.log($scope.selectedItmsList);
		 console.log($scope.selectedScreen);
		 $scope.search();
	 }
	 
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
  		    		screenService.savemodule($scope.selectedData)
  	  	 			.then(
  	  	 				function(data){
  	  	 					$scope.screenvo=data;
  	  	 					if($scope.screenvo.errors.length ==0){
  	  	 						$scope.refresh(false);
	  	 						$scope.showRecordsInDatatable($scope.selectedData);
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
  		    		screenService.updatemodule($scope.selectedData)
  	  	 			.then(
  	  	 				function(data){
  	  	 				$scope.screenvo=data;
  	  	 					if($scope.screenvo.errors.length ==0){
  	  	 						$scope.refresh(false);
  	  	 						$scope.showRecordsInDatatable($scope.selectedData);
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
	 

	$scope.processSearch=function(){
		$scope.search();
		/*$scope.flag=$scope.validate();
		if(!$scope.flag){
		$scope.search();
		}*/
	}
	
	
	
	 
	 
	 $scope.onItmsnoChange=function(itms){
		 $scope.selectedMod=[];
		 $scope.modulelist=[];
		 $scope.selectedSubMod=[];
		 $scope.submodulelist=[];
		 $scope.selectedScreen=[];
		 $scope.screenlist=[];
		 
		 screenService.getmodulelist(itms)
	 			.then(
	 				function(data){
	 					console.log(data);
	 					$scope.modulelist=data;
	 					$scope.$digest();
	 					
	 				},
	 				function(){
	 					alert('error');
	 				});
		 
			 screenService.getsubmodulelist(itms)
				.then(
					function(data){
						$scope.submodulelist=data;
						$scope.$digest();
						
					},
					function(){
						alert('error');
					});
			 
			 screenService.getscreenlist(itms)
				.then(
					function(data){
						$scope.screenlist=data;
						$scope.$digest();
						
					},
					function(){
						alert('error');
					});
		 
	 }
	 $scope.$watchCollection('selectedItmsList',function(){
		 $scope.onItmsnoChange($scope.selectedItmsList);
	 });
	 
	    $scope.getModule=function(appitmsacrnym,id){
	    	 $scope.list=[];
	    	 $scope.list.push({"key":null,"value":"Please Select"});
	    	 
	    	 screenService.getAllModuleCodeName(appitmsacrnym).then(
						function(data){
					 angular.forEach(data,function(values){
			    			 $scope.list.push(values);
			    			 
			    	 });
					$scope.$digest();
				},function(){
					alert('error');
				});
	    	
	    	 $scope.rowCollection[id-1].modulecodenameList= $scope.list;
	    	 console.log($scope.rowCollection);
	     }
	    
	    $scope.getSubModule=function(module,id){
	    	$scope.arrayList=[];
			$scope.arrayList.push({"key":null,"value":"Please Select"});
			 screenService.getSubModuleCodeName(module).then(
						function(data){
					console.log(data);
					angular.forEach(data,function(values){
						$scope.arrayList.push(values);
					});
					
					$scope.rowCollection[id-1].submoduleCodeName={key:null,value:"Please Select"};
					$scope.rowCollection[id-1].submodulecodenameList=$scope.arrayList;
					if($scope.arrayList.length>1){
						$scope.rowCollection[id-1].isSubModuleExists=true;
					}
					$scope.$digest();
				},function(){
					alert('error');
				});
	    }
	
	 $scope.refresh=function(flag){
			$scope.reqErrMsg=null;
			$scope.data=[];
			$scope.rowCollection=[];
			$scope.init();
			//$scope.searchDevice();
			$scope.selectedItms = {
					key : null,
					value : "Please Select"
				};
			
			$scope.itmsList=[];
			$scope.addcollection=[];
			$scope.saveorUpdateFlag=false;
			$scope.asteriskFlag=false;
			$scope.message='';
			$scope.reqErrs=[];
			if(flag){
				$scope.init();
			}
			
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
	     	$scope.list=[];
	    	 $scope.list.push({"key":null,"value":"Please Select"});
	     	$scope.addcollection.push({
	     		'id':$scope.addcollection.length+1,
				'moduleCodeName':{key:null,value:"Please Select"},
				'submoduleCodeName':{key:null,value:"Please Select"},
				'screenCode':"",
				'screenName':"",
				'screenPurpose':"",
				"appItmsNoName":{key:null,value:"Please Select"},
				"modulecodenameList":$scope.list,
				"submodulecodenameList":$scope.list,
				'isSubModuleExists':false,
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
	       $scope.select = function(id,row) {
	    	console.log(row);
	         var found = $scope.selected.indexOf(id); 
	         if(found == -1){
	        	 $scope.rowCollection[id-1].moduleCodeName.key = row.moduleCodeName.key;
	        	 if(row.isSubModuleExists){
	        		 $scope.rowCollection[id-1].submoduleCodeName.key = row.submoduleCodeName.key;
	        	 }
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
	       			    $scope.reqErrMsg = 'ITMS No';
	          	            $scope.flag = true;
	          	          $scope.mandFlag = true;
	       			 }
	       		 
	       		 
	       		 
	       		 if(row.moduleCodeName.key==null || row.moduleCodeName.value=='Please Select')
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
	       		 
	       		/*if(row.isSubModuleExists)
	  			 {
	       			if(row.submoduleCodeName.key==null||row.submoduleCodeName.key==''){
	       				if($scope.flag)
     	            	{
     	             $scope.reqErrMsg += ', SubModule';
     	            	}else
     	            		{
     	            	 $scope.reqErrMsg = ' SubModule';
           			  $scope.flag = true;
     	            		}
     	           $scope.mandFlag = true;
	       			}
	     	            
	  			 }*/
	       		 if(row.screenCode==null||row.screenCode==''){
	       			if($scope.flag)
 	            	{
 	             $scope.reqErrMsg += ', Screen Code';
 	            	}else
 	            		{
 	            	 $scope.reqErrMsg = ' Screen Code';
       			  $scope.flag = true;
 	            		}
 	           $scope.mandFlag = true;
	       		 }
	       		 
	       		 
	       		 if(row.screenName==null || row.screenName=='')
	   			 {
	      	            if($scope.flag)
	      	            	{
	      	             $scope.reqErrMsg += ', ScreenName';
	      	            	}else
	      	            		{
	      	            	 $scope.reqErrMsg = ' ScreenName';
	            			  $scope.flag = true;
	      	            		}
	      	          $scope.mandFlag = true;
	   			 }
	       		 
	       		if($scope.mandFlag){
	   			   $scope.reqErrMsg +=  ' Cannot be blank' ;
	   		   }
	       		if(row.screenCode!=null && row.screenCode!='')
	  			 {
	       			if(alphaCharacterValidation(row.screenCode))
	       				{
	       				if($scope.flag)
	 	            	{
	       					$scope.reqErrMsg += ', Screen Code should contain only alphabets';
	 	            	}else
	 	            		{
	 	            			$scope.reqErrMsg = 'Screen Code should contain only alphabets';
	 	            			$scope.flag = true;
	 	            		}
	       				}else{
		       				if(row.screenCode.length>5){
			       				if($scope.flag)
			       				{
			       					$scope.reqErrMsg += ',Screen Code Should not be greater than 5';
			       				}else
			       				{
			       					$scope.reqErrMsg = ' Screen Code  Should not be greater than 5';
			       					$scope.flag = true;
			       				}
			       			}
		       			}
	     	            
	  			 }
	     
	       		
	       		if(row.screenName!=null && row.screenName!='')
	       		{
	       			if(alphaCharacterValidation(row.screenName))
	       			{

	       				if($scope.flag)
	       				{
	       					$scope.reqErrMsg += ',ScreenName Should contain only alphabets';
	       				}else
	       				{
	       					$scope.reqErrMsg = ' ScreenName  Should contain only alphabets';
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
	    		  $http.post("http://35.154.237.162:9060/ChatBotService/configscreen/downloadExcel",$scope.rowCollection).then(function(response) {
	    	          if (response) {
	    	        	  var dt = new Date(); 
	    	        	     $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
	    			 		$scope.fileName = $scope.createdDate+"Screen Configuration";
	    				
	    	             
	    	                 
	    	           var anchor = angular.element('<a/>');
	    	              anchor.attr({
	    	                  href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + response.data,
	    	                  target: '_self',
	    	                  download: $scope.fileName});

	    	              angular.element(document.body).append(anchor);
	    	              anchor[0].click();
	    	              
	    	           $scope.succMessage = '{{"common.fileUpload.Success"  | translate}}';
	    	              angular.element('#ScreenuploadModalSingle').modal('hide');
	    	              $window.scrollTo(0, angular.element('#messages').offsetTop);
	    	             // $scope.searchDsid(false);
	    	          } else {
	    	          $window.scrollTo(0, angular.element('#messages').offsetTop);
	    	          $scope.reqErrMsg = '{{"common.fileUpload.fail"  | translate}}';
	    	          }

	    	      });
	    	 		
	    	 	  };
	    	 	  
	    	      $scope.DownloadTemplate =function(){
	    		      	 //if(!$scope.flag){
	    		      		  screenService.downloadTemplate()
	  	    	 			.then(
	  	    	 				function(data){
	  	    	 					
	  	    	 		           	  var fileName='Screen Maintenance Template';
	  	    	 		                    
	  	    	 		              var anchor = angular.element('<a/>');
	  	    	 		                 anchor.attr({
	  	    	 		                     href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + data,
	  	    	 		                     target: '_self',
	  	    	 		                     download: fileName});

	  	    	 		                 angular.element(document.body).append(anchor);
	  	    	 		                 anchor[0].click();
	  	    	 		                 $scope.searchDsid(false);
	  	    	 		              $scope.succMessage = '{{"common.fileUpload.Success"  | translate}}';
	  	    	 		                 angular.element('#ScreenuploadModalSingle').modal('hide');
	  	    	 		                 $window.scrollTo(0, angular.element('#messages').offsetTop);
	  	    	 		                
	  	    	 		             },
	  	    	 				function()
	  	    	 				{
	  	    	 					alert('error');
	  	    	 				}
	  	    	 				);
	  	    	     	  
	    		      	// }
	    	    	
	    	     	 
	    	   	  };
	    	   	  
	    	   	  
	    	   	  
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
	    	  
	    	  $scope.clearFile=function(){
		 			 
		 			 $scope.errmsg=""
		 		  	 $scope.SingleMessage = "";
		 		  	 $scope.ErrorMessage ="";
		 		  	 angular.element("input[type='file']").val(null);
		 		  	 $scope.files = [];
		 		 }
	    	  
	    	 $scope.openSinglePopforScreen = function() {
	    		 $scope.files = [];
				 angular.element("input[id='single']").val(null); 
	    		  angular.element('#ScreenuploadModalSingle').modal('show');
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
	    	      if ($scope.fileName.startsWith('Screen Maintenance Template'))
	    	      {
	    	    	  $http.post("http://35.154.237.162:9060/ChatBotService/configscreen/ExcelUpload", data, config).then(function(response) {
	    	              if (response) {
	    	            	  var dt = new Date(); 
	    	         	     $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
	    	 		 		var fileName = $scope.createdDate+"Screen Maintenance_Log";
	    	            	
	    	               var anchor = angular.element('<a/>');
	    	                  anchor.attr({
	    	                      href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + response.data,
	    	                      target: '_self',
	    	                      download: fileName});

	    	                  angular.element(document.body).append(anchor);
	    	                  anchor[0].click();
	    	                  
	    	               $scope.succMessage = '{{"common.fileUpload.Success"  | translate}}';
	    	                  angular.element('#ScreenuploadModalSingle').modal('hide');
	    	                  $window.scrollTo(0, angular.element('#messages').offsetTop);
	    	                 // $scope.searchDsid(false);
	    	              } else {
	    	              $window.scrollTo(0, angular.element('#messages').offsetTop);
	    	              $scope.reqErrMsg = '{{"common.fileUpload.fail"  | translate}}';
	    	              }

	    	          });
	    	      }
	    	      else  
	    	      {
	    	    	  angular.element('#ScreenuploadModalSingle').modal('hide');
	    	    	  $scope.reqErrMsg =  '{{"DSIDMain.uploadVal.validate"  | translate}}';
	    	      }

	    		} 
	    	      

	    	  }
	    	
}]);




fordApp.service("screenService",['$http',function($http){
	
this.getAllModuleCodeName = function(data) { 
		
		return new Promise(function(resolve,reject){
			
			$http.post('http://35.154.237.162:9060/ChatBotService/configscreen/getModuleCodeName',data)
			.then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}

this.getSubModuleCodeName = function(module) { 
		
		return new Promise(function(resolve,reject){
			
			$http.post('http://35.154.237.162:9060/ChatBotService/configscreen/getSubModuleCodeName',module)
			.then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}
this.downloadTemplate = function(data) { 
		
		return new Promise(function(resolve,reject){
			$http.get('http://35.154.237.162:9060/ChatBotService/configscreen/downloadTemplate')
			.then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}
	
this.getscreenlist = function(data) { 
		
		return new Promise(function(resolve,reject){
			$http.post('http://35.154.237.162:9060/ChatBotService/configscreen/getScreen',data)
			.then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}
	
this.getsubmodulelist = function(data) { 
		
		return new Promise(function(resolve,reject){
			$http.post('http://35.154.237.162:9060/ChatBotService/configscreen/getSubModule',data)
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
			$http.post('http://35.154.237.162:9060/ChatBotService/configscreen/getParentModule',data)
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
			$http.post('http://35.154.237.162:9060/ChatBotService/configscreen/fetchmodules',data)
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
			$http.post('http://35.154.237.162:9060/ChatBotService/configscreen/savemodules',data)
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
		$http.post('http://35.154.237.162:9060/ChatBotService/configscreen/updatemodules',data)
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
			
			$http.post('http://35.154.237.162:9060/ChatBotService/configscreen/getItmsNoAndAppName',data)
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
			
			$http.post('http://35.154.237.162:9060/ChatBotService/configscreen/getItmsNoAndAcrnym')
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