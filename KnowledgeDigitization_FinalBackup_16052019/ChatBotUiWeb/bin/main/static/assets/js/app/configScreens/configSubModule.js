/**
 * 
 */
fordApp.controller('submoduleController', ['$scope','$timeout','$translate','submoduleService','$route','$window','$anchorScroll',
	'$rootScope','$filter','$http',//,'blockUI'
	function($scope,$timeout,$translate, submoduleService,$route,$window,$anchorScroll,$rootScope,$filter,$http) {
	
	$scope.selected = []; 
    $scope.dataadd=false;
    $scope.files = [];
    $scope.saveorUpdateFlag = false;
    $scope.asteriskFlag = false;
    $scope.reqErrs = [];
    $scope.appitmsname=[];
    $scope.modulecodelist=[];
    $scope.submodulelist=[];
    $scope.itmsList=[];
	$scope.allitmsList=[];
	$scope.submodulevo;
	
	 $scope.init=function(){
			$scope.message;
			$scope.reqErrs = [];
			$scope.addcollection=[];
			$scope.saveorUpdateFlag = false;
			$scope.selectedModcode = [];
			$scope.selectedsubMod =[];
			$scope.selectedItmsList=[];
			 $scope.itmsList=[];
			 $scope.allmodcodenameList=[];
			 $scope.modulecodenameList=[];	
			 
			submoduleService.srchAllItms()
			.then(
				function(data){
					$scope.allitmsList= data;
					$scope.$digest();
				},
				function()
				{
					alert('error');
				});
		
		
			submoduleService.getItmsAcrnym().then(
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
	
	 	 $scope.$watchCollection('selectedItmsList',function(){
			 $scope.onItmsnoChange($scope.selectedItmsList);
		 });
	 	  
		 $scope.onItmsnoChange=function(itms){
			 $scope.selectedModcode=[];
			 $scope.modulecodelist=[];
			 $scope.selectedsubMod=[];
			 $scope.submodulelist=[];
				 
				 submoduleService.getmodcodenamelist(itms)
					.then(
						function(data){
							$scope.modulecodelist=data;
							$scope.$digest();
							
						},
						function(){
							alert('error');
						});
				 
				 submoduleService.getsubmodulelist(itms)
					.then(
						function(data){
							$scope.submodulelist=data;
							$scope.$digest();
							
						},
						function(){
							alert('error');
						});
				 
				 
				
			 
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
	      
	     }
	     // Function to get data by selecting a single row
	     $scope.select = function(row,id) {
	  	
	       var found = $scope.selected.indexOf(id); 
	       if(found == -1){
	    	   
	    	   $scope.rowCollection[id-1].modulecodename.key = row.modulecodename.key;
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
	     
	     $scope.refresh=function(){
				$scope.reqErrMsg=null;
				$scope.data=[];
				$scope.rowCollection=[];
				$scope.init();
				$scope.selectedItms = {
						key : null,
						value : "Please Select"
					}
				$scope.selectedModcode=[];
				 $scope.modulecodelist=[];
				 $scope.selectedsubMod=[];
				 $scope.submodulelist=[];
				$scope.itmsList=[];
				$scope.addcollection=[];
				$scope.saveorUpdateFlag=false;
				$scope.asteriskFlag=false;
				$scope.message='';
				$scope.reqErrs=[];
				$scope.init();
			}
	     
	     $scope.getModule=function(appitmsacrnym,id){
	    	 $scope.list=[];
	    	 //var firstitem={key:null,value:"Please Select"};
	    	 $scope.list.push({"key":null,"value":"Please Select"});
	    	 
			 submoduleService.getAllModuleCodeName(appitmsacrnym).then(
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
	     
	     $scope.addNewSubModule = function(){
			 
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
		     	$scope.list=[];
		    	 $scope.list.push({"key":null,"value":"Please Select"});
		     	$scope.addcollection.push({
		     		'id':$scope.addcollection.length+1,
					'submodulecode':"",
					'submodulename':"",
					"appItmsNoName":{key:null,value:"Please Select"},
					"modulecodename":{key:null,value:"Please Select"},
					"modulecodenameList":$scope.list,
					'isSelected':true
		     });   
		     	angular.forEach($scope.addcollection, function(val) {
		     	     var found = $scope.selected.indexOf(val.id);
		     	      if(found == -1) $scope.selected.push(val.id);
		     	    });

		     	$scope.saveorUpdateFlag = true;
		     	$scope.data=$scope.addcollection;
		     	$scope.rowCollection=$scope.addcollection;
		     	console.log($scope.data);
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
			    		    		submoduleService.savesubmodule($scope.selectedData)
			    	  	 			.then(
			    	  	 				function(data){
			    	  	 					$scope.submodulevo=data;
			    	  	 					if($scope.submodulevo.errors.length ==0){
			    	  	 						$scope.refresh();
			    	  	 						$scope.search();
			    	  	  	 					$scope.message = 'SubModule Saved Successfully';
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
			    		    		submoduleService.updatesubmodule($scope.selectedData)
			    	  	 			.then(
			    	  	 				function(data){
			    	  	 						$scope.submodulevo=data;
			    	  	 					if($scope.submodulevo.errors.length ==0){
			    	  	 						$scope.refresh();
			    	  	 						$scope.search();
			    	  	  	 					$scope.message = 'SubModule updated Successfully';
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
		       			    $scope.reqErrMsg = 'ITMS No';
		          	            $scope.flag = true;
		          	          $scope.mandFlag = true;
		       			 }
		       		 
			    	   if(row.modulecodename.key==null || row.modulecodename.key==''||row.modulecodename.value=='Please Select')
		       			 {
			    		   if($scope.flag)
	      	            	{
			    			   $scope.reqErrMsg += ', Module';
	      	            	}else
	      	            		{
	      	            			$scope.reqErrMsg = ' Module';
	      	            			$scope.flag = true;
	      	            		}
			    		   		$scope.mandFlag = true;
		       			 }
			    	   
			    		 if(row.submodulecode==null || row.submodulecode=='')
			   			 {
			      	            if($scope.flag)
			      	            	{
			      	             $scope.reqErrMsg += ', SubModule Code';
			      	            	}else
			      	            		{
			      	            	 $scope.reqErrMsg = ' SubModule Code';
			            			  $scope.flag = true;
			      	            		}
			      	          $scope.mandFlag = true;
			   			 }
		       		 
		       		 if(row.submodulename==null || row.submodulename=='')
		   			 {
		      	            if($scope.flag)
		      	            	{
		      	             $scope.reqErrMsg += ', SubModule Name';
		      	            	}else
		      	            		{
		      	            	 $scope.reqErrMsg = 'SubModule Name';
		            			  $scope.flag = true;
		      	            		}
		      	          $scope.mandFlag = true;
		   			 }
		       		 
		       		 
		       		if($scope.mandFlag){
		   			   $scope.reqErrMsg +=  ' Cannot be blank' ;
		   		   }
		       		if(row.submodulename!=null && row.submodulename!='')
		  			 {
		       			if(alphaCharacterValidation(row.submodulename))
		       				{
		       				if($scope.flag)
		 	            	{
		 	             $scope.reqErrMsg += ', SubModule Name should contain only alphabets';
		 	            	}else
		 	            		{
		 	            	 $scope.reqErrMsg = 'SubModule Name should contain only alphabets';
		       			  $scope.flag = true;
		 	            		}
		       				}
		     	            
		  			 }
		       		if(row.submodulecode!=null && row.submodulecode!='')
		       		{
		       			
		       			if(alphaCharacterValidation(row.submodulecode))
		       			{

		       				if($scope.flag)
		       				{
		       					$scope.reqErrMsg += ',SubModule Code Should contain only alphabets';
		       				}else
		       				{
		       					$scope.reqErrMsg = ' SubModule Code  Should contain only alphabets';
		       					$scope.flag = true;
		       				}


		       			}else{
		       				if(row.submodulecode.length>5){
			       				if($scope.flag)
			       				{
			       					$scope.reqErrMsg += ',SubModule Code Should not be greater than 5';
			       				}else
			       				{
			       					$scope.reqErrMsg = ' SubModule Code  Should not be greater than 5';
			       					$scope.flag = true;
			       				}
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
		  			         'moduleCodeNameList':$scope.selectedModcode,
		  			        'submoduleCodeNameList' :$scope.selectedsubMod
		  		    			
		  		    	}
		  			
		  			
		  			submoduleService.fetchsubmodule($scope.moduleobj)
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
		       
		       $scope.downloadExcel =function(){
		    		  $http.post("http://35.154.237.162:9060/ChatBotService/configsubmodule/downloadExcel",$scope.rowCollection).then(function(response) {
		    	          if (response) {
		    	        	  var dt = new Date(); 
		    	        	     $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
		    			 		$scope.fileName = $scope.createdDate+"SubModule Configuration";
		    				
		    	             
		    	                 
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
		      		  submoduleService.downloadTemplate()
	    	 			.then(
	    	 				function(data){
	    	 					
	    	 		           	  var fileName='SubModule Maintenance Template';
	    	 		                    
	    	 		              var anchor = angular.element('<a/>');
	    	 		                 anchor.attr({
	    	 		                     href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + data,
	    	 		                     target: '_self',
	    	 		                     download: fileName});

	    	 		                 angular.element(document.body).append(anchor);
	    	 		                 anchor[0].click();
	    	 		                 $scope.succMessage = '{{"common.fileUpload.Success"  | translate}}';
	    	 		                 angular.element('#SubModuleUploadModalSingle').modal('hide');
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
		 
		 $scope.openSinglePopforSubModule = function() {
			 //alert("hi");
			 $scope.files = [];
			 angular.element("input[id='single']").val(null); 
			  angular.element('#SubModuleUploadModalSingle').modal('show');
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
		      if ($scope.fileName.startsWith('SubModule Maintenance Template'))
		      {
		    	  $http.post("http://35.154.237.162:9060/ChatBotService/configsubmodule/ExcelUpload", data, config).then(function(response) {
		              if (response) {
		            	  var dt = new Date(); 
		         	     $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
		 		 		var fileName = $scope.createdDate+"SubModule Maintenance_Log";
		            	
		               var anchor = angular.element('<a/>');
		                  anchor.attr({
		                      href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + response.data,
		                      target: '_self',
		                      download: fileName});

		                  angular.element(document.body).append(anchor);
		                  anchor[0].click();
		                  
		               $scope.succMessage = '{{"common.fileUpload.Success"  | translate}}';
		                  angular.element('#SubModuleUploadModalSingle').modal('hide');
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
		    	  angular.element('#SubModuleUploadModalSingle').modal('hide');
		    	  $scope.reqErrMsg =  '{{"DSIDMain.uploadVal.validate"  | translate}}';
		      }

			} 
		      

		  }
		
	     
		 
}]);


fordApp.service("submoduleService",['$http',function($http){
	
this.downloadTemplate = function(data) { 
		
		return new Promise(function(resolve,reject){
			$http.get('http://35.154.237.162:9060/ChatBotService/configsubmodule/downloadTemplate')
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
				
				$http.post('http://35.154.237.162:9060/ChatBotService/configsubmodule/getItmsNoAndAcrnym')
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
			
			$http.post('http://35.154.237.162:9060/ChatBotService/configsubmodule/getItmsNoAndAppName',data)
			.then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}
	
	this.getAllModuleCodeName = function(appitmsacrnym) { 
		
		return new Promise(function(resolve,reject){
			
			$http.post('http://35.154.237.162:9060/ChatBotService/configsubmodule/getModuleCodeName',appitmsacrnym)
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
			$http.post('http://35.154.237.162:9060/ChatBotService/configsubmodule/getModuleCodeNameStrList',data)
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
			$http.post('http://35.154.237.162:9060/ChatBotService/configsubmodule/getSubModuleList',data)
			.then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}
this.savesubmodule = function(data) { 
		
		return new Promise(function(resolve,reject){
			$http.post('http://35.154.237.162:9060/ChatBotService/configsubmodule/savesubmodules',data)
			.then(
				function(response){
					resolve(response.data);
				},
				function(error){
					reject([]);
				});
		});
		}
this.updatesubmodule = function(data) { 
	
	return new Promise(function(resolve,reject){
		$http.post('http://35.154.237.162:9060/ChatBotService/configsubmodule/updatesubmodules',data)
		.then(
			function(response){
				resolve(response.data);
			},
			function(error){
				reject([]);
			});
	});
	}

this.fetchsubmodule = function(data) { 
	
	return new Promise(function(resolve,reject){
		$http.post('http://35.154.237.162:9060/ChatBotService/configsubmodule/fetchsubmodules',data)
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