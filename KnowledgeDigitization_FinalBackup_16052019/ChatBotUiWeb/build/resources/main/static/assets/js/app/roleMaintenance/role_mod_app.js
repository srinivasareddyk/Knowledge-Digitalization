fordApp.controller('roleMaintenanceController', ['$scope','blockUI','$timeout','$translate','appliService','$route','$window','$anchorScroll',
	'$rootScope','$filter','$http',
	function($scope,blockUI,$timeout,$translate, appliService,$route,$window,$anchorScroll,$rootScope,$filter,$http) {

	$scope.allItmsNoList=[];
	$scope.selected = []; 
    $scope.dataadd=false;
    $scope.files = [];
    $scope.saveorUpdateFlag = false;
    $scope.asteriskFlag = false;
    $scope.reqErrs = [];
    
    $scope.searchApplicationName='';
    $scope.searchApplicationAcronym='';
    $scope.searchApplicationDesc='';
    
	   
	$scope.init = function(){
		$scope.itmsNoSearchList = [];
		$scope.searchApplicationName='';
	    $scope.searchApplicationAcronym='';
	    $scope.searchApplicationDesc='';
	    $scope.saveorUpdateFlag = false;
		$scope.message ;
		$scope.reqErrs = [];
		$scope.dsidMain;
		$scope.reqErrMsg=null;
		appliService.srchAllItmsNo().then(
				function(data){
					$scope.allItmsNoList= data;
					$scope.$digest();
				},function() {
					alert('error');
				});
		$scope.searchApplication(false);
	}
	
	
	$scope.refreshApplicationMain=function() {
		$scope.data=[];
		$scope.addcollection = [];
   	    $scope.rowCollection = [];      
     	$scope.dataadd=false;
     	$scope.selectdt=false;
     	$scope.selected=[];
     	$scope.reqErrMsg=null;
     	$scope.succMessage=null;
     	$scope.itmsNoSearchList = [];
		$scope.searchApplicationName='';
	    $scope.searchApplicationAcronym='';
	    $scope.searchApplicationDesc='';
		$scope.selectedData = [];	
		$scope.reqErrs = [];
		$scope.init();
		$scope.searchApplication(true);
		$scope.saveorUpdateFlag = false;
	}
	
	
	$scope.searchApplication = function(succflag){
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
		    var dt=new Date();
			$scope.collection = [];
			$scope.selectedData = [];
			 $scope.appliVo ={
					 'searchApplicationName':$scope.searchApplicationName,
					 'searchApplicationAcronym':$scope.searchApplicationAcronym,
					 'searchItmsId': $scope.itmsNoSearchList,
			         'searchApplicationDesc': $scope.searchApplicationDesc
		    	}
			 
			 appliService.srchApplicationMainServ($scope.appliVo)
		  			.then(
		  				function(data){
		  					$scope.data= data;
		  					$scope.rowCollection= data;
		  					if(succflag){
		  					 if($scope.data.length == 0){
		  					  $scope.reqErrMsg = '{{"appliMain.records.notFound" | translate}}';
		  					}
		  				}
		  					$scope.$digest();
		  				},
		  				function() {
		  					alert('error');
		  				});
		}
	
	$scope.addcollection=[];
	
	$scope.addNewAppliMaintenCntrl = function(){
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
     		'rowId':$scope.addcollection.length+1,
     		'itmsNo' : "",
			'applicationName'  :"",
			'applicationAcronym'  :"",
			'applicationDesc'  :"",
			'isSelected':true
     });   
     	angular.forEach($scope.addcollection, function(val) {
     	     var found = $scope.selected.indexOf(val.rowId);
     	      if(found == -1) $scope.selected.push(val.rowId);
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
            $scope.selected.push(val.rowId); 
             
           });
         // if there are items in the 'selected' array, 
         // add only those that ar not
         } else if ($scope.selected.length > 0 && $scope.selected.length != collection.length) {
        	 $scope.asteriskFlag = false;
           angular.forEach(collection, function(val) {
             var found = $scope.selected.indexOf(val.rowId);
             if(found == -1) $scope.selected.push(val.rowId);
             
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
       
       
       $scope.saveApplicationMaintenanceCntrl = function(collection){
    	   $scope.flag=false;
           $scope.reqErrMsg=null;
           $scope.message=null;
	   	   $scope.selectedData = [];		   
	   	   $scope.newDataList=[];
	       $scope.reqErrs = [];
	       //$scope.dsidMain.errors = null ;
	       
	   	  	angular.forEach(collection, function(row){	
	   	        if(row.selected){
	   	            row.isSelected=true;
	   	        }else{
	   	        	row.isSelected=false;
	   	        }
	   	    });
           angular.forEach(collection, function(row){
           	if($scope.selected.indexOf(row.rowId) !== -1) {
           		$scope.newDataList.push(row);
           		}
           }); 
           $scope.selectedData = $scope.newDataList;
           $scope.flag=$scope.validate($scope.selectedData);
     	   if(!$scope.flag)  {
     		   
     		   if(!$scope.selectedData.length == 0){
     			   
     		    if($scope.saveorUpdateFlag){
     		    	
     		    	appliService.saveAppliMaintenance($scope.selectedData)
     	 			.then(
     	 				function(data){
     	 					$scope.dsidMain = data;
     	 				if($scope.dsidMain.errors.length == 0){
     	 					$scope.refreshApplicationMain();
     	 					$scope.searchApplication(true);
     	 					$scope.message = 'appliMain.save.succ';
     	 					
     	 				   }
     	 				
     	 				 $scope.selectedData = null;
     	 				 $window.scrollTo(0, angular.element('#messages').offsetTop); 
     	 				 $scope.$digest();
     	 				 
     	 				},function(){
     	 					alert('save eroor');
     	 				}
     	 				);
     			   
     		   }else{
     			  
     			  appliService.updateAppliMaintenance($scope.selectedData)
     	 			.then(
     	 				function(data){
     	 					$scope.dsidMain = data;
     	 					if($scope.dsidMain.errors.length ==0){
     	 						$scope.refreshApplicationMain();
         	 					$scope.searchApplication(true);
         	 					$scope.message = 'appliMain.update.succ';
     	 				   }
     	 					$scope.selectedData = null;
     	 					$window.scrollTo(0, angular.element('#messages').offsetTop); 
     	 					$scope.$digest();
     	 				},
     	 				function()
     	 				{
     	 					alert('update error');
     	 				}
     	 				);
     		   }
     		  }else{
     			 $scope.reqErrMsg = 'appliMain.select.err';
     			 $window.scrollTo(0, angular.element('#messages').offsetTop); 
     			  
     		  }
         
     	   }
       };
       
       
       $scope.validate =  function($selectedData) {
    	   $scope.mandFlag=false;
    	   $scope.itmsNoFlag=false;
    	   $scope.appliNameFlag=false;
    	   $scope.appliAconymFlag=false;
    	   $scope.appliDescFlag=false;
    	   $scope.reqErrMsg="";
    	   $scope.reqErrs = [];
  		 
    	   angular.forEach($selectedData, function(row) {
    		   $scope.flag = false;
    		   $scope.mandFlag=false;
    		   
    		   //alert('****'+row.itmsNo+'****');
    		   if(row.itmsNo == '' || row.itmsNo == null){
    			   $scope.reqErrMsg +=  '{{"appliMain.itmsNo.Lable" | translate}}';  
    		       $scope.itmsNoFlag = true;
    		       $scope.mandFlag=true;
    		       $scope.flag = true;
    		   }else if(specialCharacterNumValidation(row.itmsNo)){
    			   if($scope.flag){
        			   $scope.reqErrMsg +=  ', ' ;
        		   }
    			   $scope.reqErrMsg +=  '{{"appliMain.dsidfield.validate.err" | translate}}';
    			   $scope.itmsNoFlag = true;
    			   $scope.flag = true; 
    		   }else{
    			   row.itmsNo =  parseInt(row.itmsNo,10); 
    			   if(parseInt(row.itmsNo,10) < 0){
    				   if($scope.flag){
            			   $scope.reqErrMsg +=  ', ' ;
            		   }
    				   $scope.reqErrMsg +=  '{{"appliMain.dsidfield.positive.err" | translate}}';
        			   $scope.itmsNoFlag = true;
        			   $scope.flag = true;  
    			   }
    		   }
    		   
    		   if(row.applicationName == '' || row.applicationName == null){
    			   if($scope.flag){
        			   $scope.reqErrMsg +=  ', ' ;
        		   }
    			   $scope.reqErrMsg += '{{"appliMain.applicationName.Lable" | translate}}' ;
    		       $scope.appliNameFlag = true;
    		       $scope.mandFlag=true;
    		       $scope.flag = true;
    		   }
    		   
    		   if(row.applicationAcronym == '' || row.applicationAcronym == null){
    			   if($scope.flag){
        			   $scope.reqErrMsg +=  ', ' ;
        		   }
    			   $scope.reqErrMsg += '{{"appliMain.applicationAcronym.Lable" | translate}}' ;
    		       $scope.appliAconymFlag = true;
    		       $scope.mandFlag=true;
    		       $scope.flag = true;
    		   }
    		   
    		   if(row.applicationDesc == '' || row.applicationDesc == null){
    			   if($scope.flag){
        			   $scope.reqErrMsg +=  ', ' ;
        		   }
    			   $scope.reqErrMsg += '{{"appliMain.applicationDesc.Lable" | translate}}' ;
    		       $scope.appliDescFlag = true;
    		       $scope.mandFlag=true;
    		       $scope.flag = true;
    		   }
    		   
    		   if($scope.mandFlag){
    			   $scope.reqErrMsg +=  ' {{"appliMain.filefield.err" | translate}}' ;
    		   }
    		   
    		   
    		   if($scope.flag){
  	             if($scope.reqErrMsg !==null){
  	                     $scope.reqErrMsg =' {{"appliMain.LineNo.Lable" | translate}}' + ".: ["+row.rowId+"] " + $scope.reqErrMsg; 
  	              } 
  	             }
    		   if($scope.reqErrMsg != ""){
    		   $scope.reqErrs.push($scope.reqErrMsg);
    		   $scope.reqErrMsg="";
    		   }
    		   
             });
    	   
    	   if($scope.reqErrs.length > 0){
    		   $scope.flag = true;  
    		   $window.scrollTo(0, angular.element('#messages').offsetTop); 
    	   }
    	   return $scope.flag;
       }
       
       
	
      $scope.DownloadTemplateAppliMainten =function(){
    	  appliService.downloadAppliMaintenTemplate()
  			.then(
  				function(data){
  					
  		           	  var fileName='Application Maintenance Template';
  		                    
  		              var anchor = angular.element('<a/>');
  		                 anchor.attr({
  		                     href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + data,
  		                     target: '_self',
  		                     download: fileName});

  		                 angular.element(document.body).append(anchor);
  		                 anchor[0].click();
  		               $scope.searchApplication(false);
  		              $scope.succMessage = '{{"appliMain.fileUpload.Success"  | translate}}';
  		                 angular.element('#uploadModalSingle').modal('hide');
  		                 $window.scrollTo(0, angular.element('#messages').offsetTop);
  		                
  		             },
  				function()
  				{
  					alert('error');
  				}
  				);
    	  };
    	  
    	  
    	  $scope.downloadExcelAppliMaintenCntrl =function(){
    			
    			  $http.post("http://35.154.237.162:9060/EsipService/Application/downloadExcel",$scope.rowCollection).then(function(response) {
    		          if (response) {
    		        	  var dt = new Date(); 
    		        	    $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
    				 		$scope.fileName = $scope.createdDate+"Application Maintenance";
    		           var anchor = angular.element('<a/>');
    		              anchor.attr({
    		                  href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + response.data,
    		                  target: '_self',
    		                  download: $scope.fileName});

    		              angular.element(document.body).append(anchor);
    		              anchor[0].click();
    		              
    		           $scope.succMessage = '{{"appliMain.fileUpload.Success"  | translate}}';
    		              angular.element('#uploadModalSingle').modal('hide');
    		              $window.scrollTo(0, angular.element('#messages').offsetTop);
    		              $scope.searchApplication(false);
    		          } else {
    		          $window.scrollTo(0, angular.element('#messages').offsetTop);
    		          $scope.reqErrMsg = '{{"appliMain.fileUpload.fail"  | translate}}';
    		          }
    		      });
   		 	  };
	
    		 	  
    		 	 $scope.openSinglePop = function() {
    			        $route.reload();
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
    		                  $scope.SingleMessage = 'appliMain.fileType.excel' ;
    		                  
    		                  $window.scrollTo(0, angular.element('#messages').offsetTop);
    		                 
    		              }

    		          });
    		  };
    		  
    		  
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
    					$scope.SingleMessage='appliMain.file.upload.not';
    				}
    				else{
    		      $scope.fileName = $scope.files[0].name;
    		      if ($scope.fileName.startsWith('Application Maintenance Template'))
    		      {
    		    	  $http.post("http://35.154.237.162:9060/EsipService/Application/ExcelUpload", data, config).then(function(response) {
    		              if (response) {
    		            	  var dt = new Date(); 
    		         	     $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
    		 		 		var fileName = $scope.createdDate+"Application Maintenance_Log";
    		            	
    		               var anchor = angular.element('<a/>');
    		                  anchor.attr({
    		                      href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + response.data,
    		                      target: '_self',
    		                      download: fileName});

    		                  angular.element(document.body).append(anchor);
    		                  anchor[0].click();
    		                  
    		               $scope.succMessage = '{{"appliMain.fileUpload.Success"  | translate}}';
    		                  angular.element('#uploadModalSingle').modal('hide');
    		                  $window.scrollTo(0, angular.element('#messages').offsetTop);
    		                  $scope.searchApplication(false);
    		              } else {
    		              $window.scrollTo(0, angular.element('#messages').offsetTop);
    		              $scope.reqErrMsg = '{{"appliMain.fileUpload.fail"  | translate}}';
    		              }

    		          });
    		      }
    		      else  
    		      {
    		    	  angular.element('#uploadModalSingle').modal('hide');
    		    	  $scope.reqErrMsg =  '{{"appliMain.uploadVal.validate"  | translate}}';
    		      }

    			} 
    		  }
    		  
    		//  Auto Complete project Name input field
  			$scope.autoComplete=function(appliName){    
                  if(appliName!="" && appliName!=undefined) {
                	  appliService.autoCompletePN(appliName).then(function(data){
                  	  // console.log(data);
                         $scope.PNS=data;
     					//$scope.$digest();
                         //console.log($scope.PNS);
                         },function(){alert('error');})
  			     }    
            }
            $scope.fillTextbox=function(string){
                  $scope.searchApplicationName=string;
                  $scope.PNS=null;
            }
       
}]);
	   

	fordApp.service("appliService",['$http',function($http){
	    
		/**  To Load Itms No List for Search */
		this.srchAllItmsNo = function() { 
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/EsipService/Application/ItmsNoOnload').then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
			}
	
		/** Application Maintenance Search  */
		this.srchApplicationMainServ = function(data) { 
			
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/EsipService/Application/applicationSearch',data)
				.then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
			}
		
		this.saveAppliMaintenance = function(data) { 
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/EsipService/Application/saveAppliMaintenance',data).then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
			
			}
		
		this.updateAppliMaintenance = function(data) { 
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/EsipService/Application/updateAppliMaintenance',data).then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
			
			}
		
		this.downloadAppliMaintenTemplate=function()  {
				return new Promise(function(resolve,reject){
				$http.get("http://35.154.237.162:9060/EsipService/Application/downloadTemplate").then(
				function(response){
					
				resolve(response.data);
				},
				function(error){
					
				reject([]);
				});
				});
		}
		
		this.autoCompletePN=function(projectName){
			return new Promise(
					function(resolve, reject) {				
						$http.post('http://35.154.237.162:9060/EsipService/Application/searchApplicationName',projectName)
						
						.then(function(response) {
							resolve(response.data);
						}, function(error) {
							reject([]);
						});
					});	
		}
		
	}]);
	    



