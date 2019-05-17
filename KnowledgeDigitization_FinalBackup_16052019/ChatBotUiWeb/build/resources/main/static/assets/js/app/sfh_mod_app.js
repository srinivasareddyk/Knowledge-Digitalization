fordApp.controller('sfhContoller', ['$scope','$timeout','$translate','$route','$window','$anchorScroll',
	'$rootScope','$filter','$http','sfsService',
	function($scope,$timeout,$translate,$route,$window,$anchorScroll,$rootScope,$filter,$http,sfsService) {
	var newRow={isSelected:false,rowId:'',itms:'',module:'',subModule:'',selectedScreen:'',isReqHelpDoc:false,helpDocFileName:'',helpDocLoc:'',isReqHelpVideo:false,helpVideoFileName:'',helpVideoLoc:'',isReqAltDoc:false};
	
	$scope.screen='New';
	$scope.error;
	$scope.isError=false;
	$scope.isSuccess=false;
	$scope.success;
	$scope.checkAll=false;
	$scope.ENDPOINT_URL='http://35.154.237.162:9060/ChatBotService';
    $scope.files = [];
	$scope.validationError=[];
	$scope.formData=[];
	$scope.myForm = {
	        files: []
	    }
	  $scope.parseInt = function(number) {
        return parseInt(number, 10);
    }
	$scope.init = function(){
		$scope.isSearch=true;
		$scope.searchItmsList = [];
		$scope.searchModuleList = [];
		$scope.searchSubModuleList = [];
		$scope.searchScreenList = [];
		$scope.message ;
		$scope.reqErrs = [];
		$scope.dsidMain;
		/*$scope.statusList = { Inactive: "N", Active : "Y"};*/
		$scope.searchDetails();
		$scope.getDropDownList('All');
		$scope.getDropDownList1('All');	
		
		sfsService.srchAllItmsForGrid($scope.ENDPOINT_URL)
		.then(
			function(data){
				$scope.srchAllItmsForGrid= data;
				$scope.$digest();
			},
			function()
			{
				alert('error');
			}
			);
		
		
		
		sfsService.srchAllItms($scope.ENDPOINT_URL)
		.then(
			function(data){
				$scope.srchAllItms= data;
				$scope.$digest();
			},
			function()
			{
				alert('error');
			}
			);
	}

	$scope.getDropDownList = function(action){
		$scope.inputDetails ={
				 'operation':action, 
				 'itms':$scope.searchItmsList,
				 'module':$scope.searchModuleList,	
				 'subModule':$scope.searchSubModuleList,
				 'screen':$scope.searchScreenList
	    	}
		sfsService.getDropDownList($scope.inputDetails,$scope.ENDPOINT_URL)
		.then(
			function(data){
				$scope.srchAllModules= data.MODULE;				
				$scope.srchAllSubModules= data.SUBMODULE;				
				$scope.srchAllScreens= data.SCREENS;
				$scope.$digest();
			},
			function()
			{
				alert('error');
			}
			);
		
		
	}
	
	$scope.getDropDownList1 = function(action){
		$scope.inputDetails ={
				 'operation':action, 
				 'itms':$scope.searchItmsList,
				 'module':$scope.searchModuleList,	
				 'subModule':$scope.searchSubModuleList,
				 'screen':$scope.searchScreenList
	    	}
		sfsService.getDropDownList1($scope.inputDetails,$scope.ENDPOINT_URL)
		.then(
			function(data){
				var dropdownList=JSON.stringify(data.MODULE);
				$scope.gridAllModules= data.MODULE;				
				$scope.gridAllSubModules= data.SUBMODULE;				
				$scope.gridAllScreens= data.SCREENS;
				$scope.$digest();
			},
			function()
			{
				alert('error');
			}
			);
		
		
	}
	$scope.searchDetails = function(){
		$scope.error="";
		$scope.isError=false;
		$scope.isSuccess=false;
		$scope.success="";
		$scope.isSearch=true;
		$scope.screen='Search';
		$scope.inputDetails ={
				 'operation':'', 
				 'itms':$scope.searchItmsList,
				 'module':$scope.searchModuleList,	
				 'subModule':$scope.searchSubModuleList,
				 'screen':$scope.searchScreenList
	    	}
		
		sfsService.srchDatatableDetails($scope.inputDetails,$scope.ENDPOINT_URL)
			.then(
				function(data){
					$scope.formData= data;
					$scope.displayed = []
					.concat($scope.formData);
					if($scope.screen==='Search' && $scope.formData.length<=0){
						$scope.isSuccess=true;
			      		$scope.success="No Records found";
					}
					$scope.$digest();
				},
				function()
				{
					alert('error');
				}
				);
			
	}
	
	$scope.selectAllGridDetails=function(){
		$scope.gridCheckAll=!$scope.gridCheckAll;
		for(i=0;i<$scope.formData.length;i++){
			$scope.formData[i].isSelected=$scope.gridCheckAll;
		}
	}
       $scope.addNew = function(){
    	   $scope.isSearch=false;
        $scope.error="";
   		$scope.isError=false;
   		$scope.isSuccess=false;
   		$scope.success="";
    	   if($scope.screen==='Save'||$scope.screen==='New'||$scope.screen==='Search')
    		   {
   		    $scope.formData=[]; 
    		$scope.myForm.files=[];
    		$scope.screen='Add';
    		   }
    	   $scope.isError=false;
    	   $scope.isSuccess=false;
    	   var length=null ==$scope.formData?0:$scope.formData.length;
    	   $scope.formData[length]={};
    	   $scope.formData[length].isSelected=true;
    	  /* $scope.formData[length].statusSearch='Y';*/
       };
       $scope.save = function(collection){
    	   $scope.isSearch=true;
           $scope.error="";
      		$scope.isError=false;
      		$scope.isSuccess=false;
      		$scope.success="";
    	   $scope.screen='Save';
    	   var isValid=true;
    	   var selectedCount=0;
    	   var validationErrors='';
               angular.forEach($scope.formData, function(value,key){
    		   
    		   if(value.isSelected!=null&&value.isSelected!='undefined'&&value.isSelected)
    			   {
    			   selectedCount=selectedCount+1;
    			  if(value.itms==null||value.itms==''||value.itms=='undefined')
    				  {
    			  validationErrors+='ITMS No,';
    			  isValid=false;
    				  }
    			  if(value.module==null||value.module==''||value.module=='undefined'){
    				  if(!isValid)
    					  validationErrors+=",";  
         			  validationErrors+="Module";
         			  isValid=false;
         			   } 
    			  if(value.subModule==null||value.subModule==''||value.subModule=='undefined'){
    				  if(!isValid)
    					  validationErrors+=",";
         			  validationErrors+="Sub Module";
         			  isValid=false;
         			   } 
    			  
    			  if(value.selectedScreen==null||value.selectedScreen==''||value.selectedScreen=='undefined'){
    				  if(!isValid)
    					  validationErrors+=",";
         			  validationErrors+="Screen Name ";
         			  isValid=false;
         			   } 
    			  if(value.reqHelpDoc!=null&&value.reqHelpDoc){
    				  if(value.helpDocFileName==null||value.helpDocFileName==''||value.helpDocFileName=='undefined'){
    					  if(!isValid)
        					  validationErrors+=",";
         			  validationErrors+="Helf Document File Name";
         			  isValid=false;
    				  }
    				  if(value.helpDocLoc==null||value.helpDocLoc==''||value.helpDocLoc=='undefined'){
    					  if(!isValid)
        					  validationErrors+=",";
             			  validationErrors+="Helf Document Location";
             			  isValid=false;
        				  }
         			   } 
    			  
    			  if(value.reqHelpVideo!=null&&value.reqHelpVideo){
    				  if(value.helpVideoFileName==null||value.helpVideoFileName==''||value.helpVideoFileName=='undefined'){
    					  if(!isValid)
        					  validationErrors+=",";
         			  validationErrors+="Helf Videos File Name";
         			  isValid=false;
    				  }
    				  if(value.helpVideoLoc==null||value.helpVideoLoc==''||value.helpVideoLoc=='undefined'){
    					  if(!isValid)
        					  validationErrors+=",";
             			  validationErrors+="Helf Videos Location";
             			  isValid=false;
        				  }
         			   } 
    			  
    			  if(value.reqAltDoc!=null&&value.reqAltDoc){
    				  
    				  if($scope.myForm.files[value.rowId]==null){
    					  if(!isValid)
        					  validationErrors+=",";
             			  validationErrors+="Upload Alternate Document ";
             			  isValid=false;  
    				  }else{
    					  var size=Math.round((($scope.myForm.files[value.rowId].size) /(1024*1024) ));
    					  if(size>1){
    						  if(!isValid)
            					  validationErrors+=",";
                 			  validationErrors+="File size should be less than 1 MB;";
                 			  isValid=false;  
    						  
    					  }
    					  
    				  }
    				  
    				  }
    			  
    			  if(!isValid){
               	   validationErrors=validationErrors+" Required for Row ID["+value.rowId+"].";
                  }
    			   }
               
               });
               
               if(!isValid)
            	   {
            	   $scope.error= validationErrors;
            	   $scope.isError=true;
            	   }
               
               selectedCount
               if(selectedCount==0){
            	   validationErrors="Please select at least 1 Record";
            	   $scope.error= validationErrors;
            	   $scope.isError=true;   
               }
               
               
           var deleteIds="";
    	   var inputFileData = new FormData();
    	   
    	   angular.forEach($scope.formData, function(value,key){
    		   
    		   if(value.isSelected!=null&&value.isSelected!=undefined&&value.isSelected&&isValid)
    			   {
    			   
    			   if(!value.reqHelpDoc && !value.reqHelpVideo && !value.reqAltDoc)  
    				   {
    				   deleteIds +=(value.selectedScreen).split('-')[0];
    				   }else{   			   
    		      		       		   
    		   inputFileData.append("module",value.module);
    		   inputFileData.append("subModule",value.subModule);
    		   inputFileData.append("selectedScreen",value.selectedScreen);
    		   if(value.reqHelpDoc){
    		   inputFileData.append("helpDocRequired","Y");
    		   inputFileData.append("helpDocFileName",value.helpDocFileName);
    		   inputFileData.append("helpDocLoc",value.helpDocLoc);
    		   }
    		   if(value.reqHelpVideo){
    		   inputFileData.append("helpVideoRequired","Y");
    		   inputFileData.append("helpVideoFileName",value.helpVideoFileName);
    		   inputFileData.append("helpVideoLoc",value.helpVideoLoc);
    		   }
    		   if(value.reqAltDoc){
    		   inputFileData.append("altDocRequired","Y");
    		   inputFileData.append("files",$scope.myForm.files[value.rowId]);
    		   
    		   }
    		   /*inputFileData.append("statusSearch",value.statusSearch);*/
    		   inputFileData.append("createdBy",$rootScope.userId);
    		   inputFileData.append("updatedBy",$rootScope.userId);
    		   sfsService.saveDataTableList(inputFileData,$scope.ENDPOINT_URL)
	 			.then(
	 				function(data){
	 					 $scope.success="saved";
	 					$scope.isSuccess=true;
	 					$scope.$digest();
	 				
	 				},
	 				function()
	 				{
	 					 $scope.error="Error while saving";
	 					$scope.isError=true;
	 					$scope.$digest();
	 				}
	 				);  
    		   
    		   
    		   inputFileData = new FormData();
    		   
    			   }
    		   
    			   }
    		   
    	   });
    	   
    	   if(deleteIds!=""){
    	   sfsService.deleteDataTableList(deleteIds,$scope.ENDPOINT_URL)
			.then(
				function(data){
					 $scope.success="saved";
					$scope.isSuccess=true;
					$scope.$digest();
				},
				function()
				{
					 $scope.error="Error while saving";
					$scope.isError=true;
					$scope.$digest();
				}
				);  
    	   }
    	   
    	   
       };
       
       $scope.downloadSFHTemplate=function(){
    	   $scope.error='';
    	   $scope.isError=false;
    	   $scope.isSuccess=false;
    		$scope.success='';
       sfsService.downloadSFHTemplate($scope.ENDPOINT_URL)
   	.then(
   		function(data){
   			
              	  var fileName='SFH Maintenance Template';
                       
                 var anchor = angular.element('<a/>');
                    anchor.attr({
                        href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + data,
                        target: '_self',
                        download: fileName});

                    angular.element(document.body).append(anchor);
                    anchor[0].click();
                    $scope.searchDsid(false);
                    $scope.isSuccess=true;
                 $scope.success = '{{"common.fileUpload.Success"  | translate}}';
                    angular.element('#uploadModalSingle').modal('hide');
                    $window.scrollTo(0, angular.element('#messages').offsetTop);
                   
                },
   		function()
   		{
   			alert('error');
   		}
   		);
    
       }
       
       $scope.downloadExcelSFH =function(){  
    	   $scope.error='';
    	   $scope.isError=false;
    	   $scope.isSuccess=false;
    		$scope.success='';
    		  $http.post($scope.ENDPOINT_URL+"/sfh/downloadExcel",$scope.formData).then(function(response) {
    	          if (response) {
    	        	  var dt = new Date(); 
    	        	     $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
    			 		$scope.fileName = $scope.createdDate+"SFH Maintenance";
    				
    	             
    	                 
    	           var anchor = angular.element('<a/>');
    	              anchor.attr({
    	                  href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + response.data,
    	                  target: '_self',
    	                  download: $scope.fileName});

    	              angular.element(document.body).append(anchor);
    	              anchor[0].click();    	              
    	           $scope.succMessage = '{{"common.fileUpload.Success"  | translate}}';
    	              angular.element('#uploadModalSingle').modal('hide');
    	              $window.scrollTo(0, angular.element('#messages').offsetTop);
    	              $scope.searchDsid(false);
    	          } else {
    	          $window.scrollTo(0, angular.element('#messages').offsetTop);
    	          $scope.reqErrMsg = '{{"common.fileUpload.fail"  | translate}}';
    	          }

    	      });
    	 		
    	 	  }
       $scope.excelUploadFile = function() {
    	   $scope.error='';
    	   $scope.isError=false;
    	   $scope.isSuccess=false;
    		$scope.success='';
    	      $scope.FileMessage = "";
    	  	 $scope.SingleMessage = "";
    	     var data = new FormData();
    	      data.append("file", $scope.files[0]);
    	      data.append("user",$rootScope.userId);
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
    	      $scope.SingleMessage= $scope.fileName;
    	      if ($scope.fileName.startsWith('SFH Maintenance Template'))
    	      {
    	    	  $http.post($scope.ENDPOINT_URL+"/sfh/ExcelUpload", data, config).then(function(response) {
    	              if (response) {
    	            	  var dt = new Date(); 
    	         	     $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
    	 		 		var fileName = $scope.createdDate+"SFH Maintenance_Log";
    	            	
    	               var anchor = angular.element('<a/>');
    	                  anchor.attr({
    	                      href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + response.data,
    	                      target: '_self',
    	                      download: fileName});

    	                  angular.element(document.body).append(anchor);
    	                  anchor[0].click();
    	                  
    	               $scope.succMessage = '{{"common.fileUpload.Success"  | translate}}';
    	                  angular.element('#uploadModalSingle').modal('hide');
    	                  $window.scrollTo(0, angular.element('#messages').offsetTop);
    	              } else {
    	              $window.scrollTo(0, angular.element('#messages').offsetTop);
    	              $scope.reqErrMsg = '{{"common.fileUpload.fail"  | translate}}';
    	              }

    	          });
    	      }
    	      else  
    	      {
    	    	  angular.element('#uploadModalSingle').modal('hide');
    	    	  $scope.SingleMessage =  '{{"SFH.uploadVal.validate"  | translate}}';
    	      }
    	      $scope.searchDetails();
    		} 
    	  }
           
       $scope.getExcelFileDetails = function(e) { 
    	   $scope.error='';
    	   $scope.isError=false;
    	   $scope.isSuccess=false;
    		$scope.success='';
   		$scope.SingleMessage = "";
             $scope.$apply(function() {
                 
                 var fileType = e.files[0].name.substring(e.files[0].name.lastIndexOf("."), e.files[0].name.length);
                 if ( fileType == '.xlsx') {
                                           
                   		$scope.SingleMessage = "";
                         $scope.files.push(e.files[0]);
                   
                 } else {
                     $scope.SingleMessage = 'common.fileType.excel' ;
                     
                     $window.scrollTo(0, angular.element('#messages').offsetTop);
                    
                 }

             });
     };
       
       $scope.openSinglePop = function() {
	        $route.reload();
	    }
       
     
	
}]);


fordApp.directive('fileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
    	  var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;
          element.bind('change', function(){
             scope.$apply(function(){
            	 
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
     
}]);


fordApp.service("sfsService",['$http',function($http){
			this.srchDatatableDetails = function(data,endpointURL) { 
				return new Promise(function(resolve,reject){
					
					$http.post(endpointURL+'/sfh/searchDetails',data)
					.then(
						function(response){
							resolve(response.data);
						},
						function(error){
							reject([]);
						});
				});
				}
	
			this.getDropDownList = function(inputDetails,endpointURL) { 
				return new Promise(function(resolve,reject){
					 $http.post(endpointURL+'/sfh/fillDropDown',inputDetails).then(
						function(response){
							resolve(response.data);
						},
						function(error){
                         alert("error");							
							reject([]);
						});
				});
				}	
			
			this.getDropDownList1 = function(inputDetails,endpointURL) { 
				return new Promise(function(resolve,reject){
					 $http.post(endpointURL+'/sfh/fillDropDown1',inputDetails).then(
						function(response){
							resolve(response.data);
						},
						function(error){
                         alert("error");							
							reject([]);
						});
				});
				}	
			this.saveDataTableList = function(inputFileData,endpointURL) { 
				return new Promise(function(resolve,reject){
					var config = {
		    				transformRequest: angular.identity,
		    				transformResponse: angular.identity,
		    				headers: {
		    					'Content-Type': undefined
		    				}
		    		}
					$http.post(endpointURL+'/sfh/save',inputFileData,config).then(
						function(response){
							resolve(response.data);
						},
						function(error){
							reject([]);
						});
				});
				
				}
			
			this.deleteDataTableList = function(screenIds,endpointURL) {
				return new Promise(function(resolve,reject){
					$http.post(endpointURL+'/sfh/delete',screenIds).then(
						function(response){
							resolve(response.data);
						},
						function(error){
							reject([]);
						});
				});
				
				}
			
			
	this.srchAllItms = function(endpointURL) { 
		return new Promise(function(resolve,reject){
			$http.post(endpointURL+'/sfh/getAllItmsNumbers').then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}
	
	this.srchAllItmsForGrid = function(endpointURL) { 
		return new Promise(function(resolve,reject){
			$http.post(endpointURL+'/sfh/getAllItmsNumbersForGrid').then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}
	
	
	this.downloadSFHTemplate=function(endpointURL)
	{
			return new Promise(function(resolve,reject){
			$http.get(endpointURL+'/sfh/downloadTemplate').then(
			function(response){
			resolve(response.data);
			},
			function(error){
				
			reject([]);
			});
			});
	}
	
}]);




