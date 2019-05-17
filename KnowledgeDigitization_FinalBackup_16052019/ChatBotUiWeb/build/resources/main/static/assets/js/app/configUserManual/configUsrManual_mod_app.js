fordApp.controller('configUsrManualController', ['$scope','$timeout','$translate','configUsrManualService','$route','$window','$anchorScroll',
	'$rootScope','$filter','$http',
	function($scope,$timeout,$translate, configUsrManualService,$route,$window,$anchorScroll,$rootScope,$filter,$http) {

	$scope.allApplItmsNoList=[];
	$scope.allModuleList=[];
	$scope.allSubModuleList=[];
	$scope.allScreenNameNoList=[];
	$scope.allFieldNameList=[];
	$scope.selected = []; 
    $scope.dataadd=false;
    $scope.files = [];
    $scope.saveorUpdateFlag = false;
    $scope.asteriskFlag = false;
    $scope.reqErrs = [];
	
    $scope.searchApplItmsList = [];
	$scope.searchModuleList = [];
	$scope.searchSubModuleList=[];
    $scope.searchScreenNameList=[];
    $scope.searchFieldNameList=[];
    $scope.searchModule = '';
    
    
	$scope.init = function(){
		$scope.searchApplItmsList = [];
		$scope.searchModuleList = [];
		$scope.searchSubModuleList=[];
	    $scope.searchScreenNameList=[];
	    $scope.searchFieldNameList=[];
	    $scope.data=[];
		$scope.addcollection = [];
   	    $scope.rowCollection = []; 
   	    $scope.addSearchResultcollection = [];
	    $scope.saveorUpdateFlag = false;
		$scope.message ;
		$scope.reqErrs = [];
		$scope.configUsrManual;
		$scope.reqErrMsg=null;
		
		$scope.searchConfigUsrManual(false);
		configUsrManualService.srchInputAllApplItmsNo().then(
				function(data){
					//$scope.allApplItmsNoList= data;
					$scope.addRowApplItmsList= data;
					$scope.$digest();
				},function() {
					alert('error');
				});
		configUsrManualService.srchInputAllApplItmsNoAndDescListOfString().then(
				function(data){
					$scope.allApplItmsNoList= data;
					$scope.$digest();
				},function() {
					alert('error');
				});
		
		configUsrManualService.searchInputModule().then(
				function(data){
					$scope.allModuleList= data;
					$scope.$digest();
				},function() {
					alert('error');
				});
		
		configUsrManualService.searchInputSubModule().then(
				function(data){
					$scope.allSearchSubModuleList= data;
					$scope.$digest();
				},function() {
					alert('error');
				});
		
		configUsrManualService.searchInputScreenName().then(
				function(data){
					$scope.allSearchScreenNameList= data;
					$scope.$digest();
				},function() {
					alert('error');
				});
		
		}
	
	
	$scope.addNewConfigUsrManualCntrl = function(){
		
     	$scope.data = []; 
     	$scope.addSearchResultcollection = [];
     	$scope.reqErrs = [];
     	$scope.dataadd=true;
     	$scope.selectdt=true;
     	$scope.selected =[];
     	$scope.asteriskFlag = true;
     	$scope.reqErrMsg=null;
     	$scope.succMessage=null;
     	$scope.message=null;
     	$scope.configUsrManual.errors = null ;
     	
     	$scope.addApplItmsNoList = [{key:null,value : "Please Select"}];
		angular.forEach($scope.addRowApplItmsList,function(values){
			$scope.addApplItmsNoList.push(values);
		});
		
     	$scope.addcollection.push({
     		'rowId':$scope.addcollection.length+1,
     		'appItmsNo' : {key:null,value:"Please Select"},
     		'module' : {key:null,value:"Please Select"},
			'subModule' : {key:null,value:"Please Select"},
			'screenName' : {key:null,value:"Please Select"},
			'fieldName' : '',
			'fieldCode' : '',
			'isAudioHelpCheck' :true,
			'isToolTipHelpCheck' :true,
			'helpText' :"",
			'applItmsNoList' :$scope.addApplItmsNoList,
			'moduleList' : [{key:null,value : "Please Select"}],
			'subModuleList' : [{key:null,value : "Please Select"}],
			'screenNameList' : [{key:null,value : "Please Select"}],
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
	
	
	$scope.refreshConfigUsrManual=function() {
		$scope.data=[];
		$scope.addcollection = [];
   	    $scope.rowCollection = [];
   	    $scope.addSearchResultcollection = [];
   	    $scope.addSearchResultcollection = [];
     	$scope.dataadd=false;
     	$scope.selectdt=false;
     	$scope.selected=[];
     	$scope.reqErrMsg=null;
     	$scope.succMessage=null;
     	
     	$scope.searchApplItmsList = [];
     	$scope.searchModuleList = [];
		$scope.searchSubModuleList=[];
	    $scope.searchScreenNameList=[];
	    $scope.searchFieldNameList=[];
		
	    $scope.selectedData = [];	
		$scope.reqErrs = [];
		//$scope.init();
		$scope.searchConfigUsrManual(true);
		$scope.saveorUpdateFlag = false;
	}
	
	$scope.addSearchResultcollection=[];
	
	$scope.searchConfigUsrManual = function(succflag){
		 	$scope.saveorUpdateFlag = false;
		    $scope.data=[];
			$scope.addcollection = [];
	   	    $scope.rowCollection = [];
	   	    $scope.addSearchResultcollection = [];
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
			$scope.searchConfigUsrDetailsList=[];
			
			 $scope.configUsrManual ={
					 'searchApplItmsList':$scope.searchApplItmsList,
					 'searchModuleList':$scope.searchModuleList,
					 'searchSubModuleList':$scope.searchSubModuleList,
					 'searchScreenNameList': $scope.searchScreenNameList
		    	}
			 
			 configUsrManualService.srchConfigUsrManualMainServ($scope.configUsrManual)
		  			.then(
		  				function(data){
		  					$scope.data=[];
		  					$scope.rowCollection=[];
		  					$scope.addSearchResultcollection = [];
		  					$scope.data= data;
		  					
		  					angular.forEach($scope.data,function(values){
		  						
		  						$scope.addSearchResultcollection.push({
		  				     		'rowId':values.rowId,
		  				     		'configSakeyId':values.configSakeyId,
		  				     		'appItmsNoDesc':values.appItmsNoDesc,
		  				     		'moduleDesc':values.moduleDesc,
		  				     		'subModuleDesc':values.subModuleDesc,
		  				     		'screenNameDesc':values.screenNameDesc,
		  				     		'appItmsNo' : {key:values.appItmsNo,value:values.appItmsNoDesc},
		  				     		'module' : {key:values.module,value:values.moduleDesc},
		  							'subModule' : {key:values.subModule,value:values.subModuleDesc},
		  							'screenName' : {key:values.screenName,value:values.screenNameDesc},
		  							'fieldCode' : values.fieldCode,
		  							'fieldName' : values.fieldName,
		  							'audioHelp' : values.audioHelp,
		  							'toolTipHelp' : values.toolTipHelp,
		  							'audioHelpCheck' :values.audioHelpCheck,
		  							'toolTipHelpCheck' :values.toolTipHelpCheck,
		  							'helpText' :values.helpText,
		  							'applItmsNoList' : values.applItmsNoList,
		  							'moduleList' : values.moduleList,
		  							'subModuleList' : values.subModuleList,
		  							'screenNameList' : values.screenNameList,
		  							'createDateTime' : values.createDateTime,
		  							'lastUpdateDateTime' : values.lastUpdateDateTime
		  						 });   
		  						$scope.searchConfigUsrDetailsList=$scope.addSearchResultcollection;
		  					});
		  					
		  					$scope.data=[];
		  					$scope.data=$scope.searchConfigUsrDetailsList;
		  					$scope.rowCollection=$scope.searchConfigUsrDetailsList;
		  					
		  					//$scope.data= data;
		  					//$scope.rowCollection= data;
		  					if(succflag){
		  					 if($scope.data.length == 0){
		  					  $scope.reqErrMsg = '{{"configUsrManual.records.notFound" | translate}}';
		  					}
		  				}
		  					$scope.$digest();
		  				},
		  				function() {
		  					alert('error');
		  				});
		}
	
	
	$scope.addSelectedcollection=[];
	$scope.saveConfigUsrManualCntrl = function(collection){
 	   $scope.flag=false;
       $scope.reqErrMsg=null;
       $scope.message=null;
	   $scope.selectedData = [];		   
	   $scope.newDataList=[];
	   $scope.reqErrs = [];
	       //$scope.configUsrManual.errors = null ;
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
  		    	$scope.addSelectedcollection=[];
  		    	$scope.selectedRowList=[];
  		    	angular.forEach($scope.selectedData,function(values){
						$scope.addSelectedcollection.push({
				     		'rowId':values.rowId,
				     		'appItmsNoId':values.appItmsNo.key,
				     		'moduleId':values.module.key,
				     		'subModuleId':values.subModule.key,
				     		'screenNameId':values.screenName.key,
				     		'fieldCode' : values.fieldCode,
							'fieldName' : values.fieldName,
							'audioHelpCheck' : values.audioHelpCheck,
							'toolTipHelpCheck' : values.toolTipHelpCheck,
							'helpText' :values.helpText
						 });   
						$scope.selectedRowList=$scope.addSelectedcollection;
					});
  		    	$scope.selectedData=[];
  		    	$scope.selectedData=$scope.selectedRowList;
  		    	configUsrManualService.saveConfigUsrManual($scope.selectedRowList)
  	 			.then(
  	 				function(data){
  	 					$scope.configUsrManual = data;
  	 					
  	 				if($scope.configUsrManual.errors.length == 0){
  	 					$scope.refreshConfigUsrManual();
  	 					$scope.searchConfigUsrManual(true);
  	 					$scope.message = 'configUsrManual.save.succ';
  	 					
  	 				   }
  	 				
  	 				 $scope.selectedData = null;
  	 				 $window.scrollTo(0, angular.element('#messages').offsetTop); 
  	 				 $scope.$digest();
  	 				 
  	 				},function(){
  	 					alert('save eroor');
  	 				}
  	 				);
  			   
  		   }else{
  			   	$scope.addSelectedcollection=[];
		    	$scope.selectedRowList=[];
		    	angular.forEach($scope.selectedData,function(values){
		    		
						$scope.addSelectedcollection.push({
				     		'rowId':values.rowId,
				     		'configSakeyId':values.configSakeyId,
				     		'appItmsNoId':values.appItmsNo.key,
				     		'moduleId':values.module.key,
				     		'subModuleId':values.subModule.key,
				     		'screenNameId':values.screenName.key,
							'fieldName' : values.fieldName,
							'fieldCode' : values.fieldCode,
							'audioHelpCheck' : values.audioHelpCheck,
							'toolTipHelpCheck' : values.toolTipHelpCheck,
							'helpText' :values.helpText
						 });   
						$scope.selectedRowList=$scope.addSelectedcollection;
					});
		    	$scope.selectedData=[];
		    	$scope.selectedData=$scope.selectedRowList;
		    	
  			 configUsrManualService.updateConfigUsrManual($scope.selectedData)
  	 			.then(
  	 				function(data){
  	 					$scope.configUsrManual = data;
  	 					if($scope.configUsrManual.errors.length ==0){
  	 						$scope.refreshConfigUsrManual();
      	 					$scope.searchConfigUsrManual(true);
      	 					$scope.message = 'configUsrManual.update.succ';
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
  			 $scope.reqErrMsg = 'configUsrManual.select.err';
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
 	   $scope.audioToolTipHelpFlag=false;
 	   $scope.reqErrMsg="";
 	   $scope.reqErrs = [];
		 
 	   angular.forEach($selectedData, function(row) {
 		   $scope.flag = false;
 		   $scope.mandFlag=false;
 		   $scope.audioToolTipHelpFlag=false;
 		   
 		  // alert('****'+row.module+'****');
 		  if(row.appItmsNo.key == '' || row.appItmsNo.key == null){
			   $scope.reqErrMsg +=  '{{"configUsrManual.applItmsNo.Lable" | translate}}';  
		       $scope.itmsNoFlag = true;
		       $scope.mandFlag=true;
		       $scope.flag = true;
		   }
 		  
 		   if(row.module.key == '' || row.module.key == null){
 			   $scope.reqErrMsg +=  '{{"configUsrManual.module.Lable" | translate}}';  
 		       $scope.itmsNoFlag = true;
 		       $scope.mandFlag=true;
 		       $scope.flag = true;
 		   }
 		  
 		   if(row.subModule.key == '' || row.subModule.key == null){
 			   if($scope.flag){
     			   $scope.reqErrMsg +=  ', ' ;
     		   }
 			   $scope.reqErrMsg += '{{"configUsrManual.subModule.Lable" | translate}}' ;
 		       $scope.appliNameFlag = true;
 		       $scope.mandFlag=true;
 		       $scope.flag = true;
 		   }
 		  
 		   if(row.screenName.key == '' || row.screenName.key == null){
 			   if($scope.flag){
     			   $scope.reqErrMsg +=  ', ' ;
     		   }
 			   $scope.reqErrMsg += '{{"configUsrManual.screenName.Lable" | translate}}' ;
 		       $scope.appliAconymFlag = true;
 		       $scope.mandFlag=true;
 		       $scope.flag = true;
 		   }
 		  
 		 if(row.fieldCode == '' || row.fieldCode == null){
			   if($scope.flag){
   			   $scope.reqErrMsg +=  ', ' ;
   		   }
			   $scope.reqErrMsg += '{{"configUsrManual.fieldCode.Lable" | translate}}' ;
		       $scope.appliDescFlag = true;
		       $scope.mandFlag=true;
		       $scope.flag = true;
		   }
 		   if(row.fieldName == '' || row.fieldName == null){
 			   if($scope.flag){
     			   $scope.reqErrMsg +=  ', ' ;
     		   }
 			   $scope.reqErrMsg += '{{"configUsrManual.fieldName.Lable" | translate}}' ;
 		       $scope.appliDescFlag = true;
 		       $scope.mandFlag=true;
 		       $scope.flag = true;
 		   }
 		   
 		 /* if(row.audioHelpCheck== undefined) {
 			 if($scope.flag){
   			   $scope.reqErrMsg +=  ', ' ;
   		       }
			   $scope.reqErrMsg += '{{"configUsrManual.audioHelp.Lable" | translate}}' ;
		       $scope.appliDescFlag = true;
		       $scope.mandFlag=true;
		       $scope.flag = true;
		       
 		  }else if(!row.audioHelpCheck) {
 			 if($scope.flag){
   			   $scope.reqErrMsg +=  ', ' ;
   		   	   }
			   $scope.reqErrMsg += '{{"configUsrManual.audioHelp.Lable" | translate}}' ;
		       $scope.appliDescFlag = true;
		       $scope.mandFlag=true;
		       $scope.flag = true;
 		  }
 		  
 		 if(row.toolTipHelpCheck== undefined) {
 			if($scope.flag){
  			   $scope.reqErrMsg +=  ', ' ;
  		       }
			   $scope.reqErrMsg += '{{"configUsrManual.toolTipHelp.Lable" | translate}}' ;
		       $scope.appliDescFlag = true;
		       $scope.mandFlag=true;
		       $scope.flag = true;
		  }else if(!row.toolTipHelpCheck) {
			  if($scope.flag){
    			   $scope.reqErrMsg +=  ', ' ;
    		   }
			   $scope.reqErrMsg += '{{"configUsrManual.toolTipHelp.Lable" | translate}}' ;
		       $scope.appliDescFlag = true;
		       $scope.mandFlag=true;
		       $scope.flag = true;
		  }*/
 		   
 		  if(row.helpText == '' || row.helpText == null){
			   if($scope.flag){
    			   $scope.reqErrMsg +=  ', ' ;
    		   }
			   $scope.reqErrMsg += '{{"configUsrManual.helpText.Lable" | translate}}' ;
		       $scope.appliDescFlag = true;
		       $scope.mandFlag=true;
		       $scope.flag = true;
		   }
 		   
 		   if($scope.mandFlag){
 			   $scope.reqErrMsg +=  ' {{"configUsrManual.filefield.err" | translate}}' ;
 		   }
 		   
 		   if(!$scope.flag) {
 			  if(row.audioHelpCheck== undefined && row.toolTipHelpCheck== undefined) {
 			 	   $scope.reqErrMsg = '{{"configUsrManual.audioHelp.Lable" | translate}}'+' or '+'{{"configUsrManual.toolTipHelp.Lable" | translate}}'+' {{"configUsrManual.toolTipHelp.audioHelp.error.msg" | translate}}' ;
 			       $scope.audioToolTipHelpFlag=true;
 			       $scope.flag = true;			
 	 		  }else if(!row.audioHelpCheck && !row.toolTipHelpCheck) {
 			 	   $scope.reqErrMsg = '{{"configUsrManual.audioHelp.Lable" | translate}}'+' or '+'{{"configUsrManual.toolTipHelp.Lable" | translate}}'+' {{"configUsrManual.toolTipHelp.audioHelp.error.msg" | translate}}' ;
 			       $scope.audioToolTipHelpFlag=true;
 			       $scope.flag = true;
 	 		  }
 		   }
 		   
 		   
 		   if($scope.flag){
	             if($scope.reqErrMsg !==null){
	                     $scope.reqErrMsg =' {{"configUsrManual.LineNo.Lable" | translate}}' + ".: ["+row.rowId+"] " + $scope.reqErrMsg; 
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
              $scope.SingleMessage = 'configUsrManual.fileType.excel' ;
              
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
		$scope.SingleMessage='configUsrManual.file.upload.not';
	}
	else{
  $scope.fileName = $scope.files[0].name;
  if ($scope.fileName.startsWith('Application Maintenance Template'))
  {
	  $http.post("http://35.154.237.162:9060/ChatBotService/configUsrManual/ExcelUpload", data, config).then(function(response) {
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
              
           $scope.succMessage = '{{"configUsrManual.fileUpload.Success"  | translate}}';
              angular.element('#uploadModalSingle').modal('hide');
              $window.scrollTo(0, angular.element('#messages').offsetTop);
              $scope.refreshConfigUsrManual();
              $scope.searchConfigUsrManual(false);
          } else {
          $window.scrollTo(0, angular.element('#messages').offsetTop);
          $scope.reqErrMsg = '{{"configUsrManual.fileUpload.fail"  | translate}}';
          }

      });
  }
  else  
  {
	  angular.element('#uploadModalSingle').modal('hide');
	  $scope.reqErrMsg =  '{{"configUsrManual.uploadVal.validate"  | translate}}';
  }

} 
}


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



/** Onchange Application ITMS No Add Operation */
$scope.toLoadModuleDetails = function(id){
	$scope.reqErrMsg="";
	$scope.succMessage="";
	$scope.reqEditFlag=false;
	$scope.addEmptyList = [{key:null,value : "Please Select"}];
	angular.forEach($scope.data,function(values){
		if(values.rowId == id){
			$scope.configUsrManualVo={
				'appItmsNo':values.appItmsNo.key
				}
			
			values.module = {key:null,value : "Please Select"};
			values.subModule = {key:null,value : "Please Select"};
			values.screenName = {key:null,value : "Please Select"};
			values.moduleList =$scope.addEmptyList;
			values.subModuleList= $scope.addEmptyList;
			values.screenNameList= $scope.addEmptyList;
			
			configUsrManualService.getModuleDetailsForOnChange($scope.configUsrManualVo).then(
				function(data) {
					if(data==null ||data==''){
						$scope.reqErrMsg = 'Module Could not Configured...!';
 		  			}else{
 		  				$scope.addModuleList = [{key:null,value : "Please Select"}];
 		  				angular.forEach(data,function(values){
 		  					$scope.addModuleList.push(values);
 		  				});
 		  				values.moduleList = $scope.addModuleList;
 		  			}
					$scope.$digest();
					}, function() {
					alert('error');
				});
			}
		});
	};
	
	/** Onchange Module Add Operation */
	$scope.toLoadSubModuleDetails = function(id){
		$scope.reqErrMsg="";
		$scope.succMessage="";
		$scope.reqEditFlag=false;
		$scope.addEmptyList = [{key:null,value : "Please Select"}];
		angular.forEach($scope.data,function(values){
			if(values.rowId == id){
				$scope.configUsrManualVo={
					'module':values.module.key
					}
				
				values.subModule = {key:null,value : "Please Select"};
				values.screenName = {key:null,value : "Please Select"};
				values.subModuleList= $scope.addEmptyList;
				values.screenNameList= $scope.addEmptyList;
				
				configUsrManualService.getSubModuleDetailsForOnChange($scope.configUsrManualVo).then(
					function(data) {
						if(data==null ||data==''){
							$scope.reqErrMsg = 'Sub Module Could not Configured...!';
	 		  			}else{
	 		  				$scope.addSubModuleList = [{key:null,value : "Please Select"}];
	 		  				angular.forEach(data,function(values){
	 		  					$scope.addSubModuleList.push(values);
	 		  				});
	 		  				values.subModuleList = $scope.addSubModuleList;
	 		  			}
						$scope.$digest();
						}, function() {
						alert('error');
					});
				}
			});
		};
		
		/** Onchange SubModule Add Operation */
		$scope.toLoadScreenNameDetails = function(id,subModuleKey){
			$scope.reqErrMsg="";
			$scope.succMessage="";
			$scope.reqEditFlag=false;
			$scope.addEmptyList = [{key:null,value : "Please Select"}];
			angular.forEach($scope.data,function(values){
				if(values.rowId == id){
					$scope.configUsrManualVo={
						'subModule':subModuleKey
						}
					
					values.screenName = {key:null,value : "Please Select"};
					values.screenNameList= $scope.addEmptyList;
					
					configUsrManualService.getScreenNameDetailsForOnChange($scope.configUsrManualVo).then(
						function(data) {
							if(data==null ||data==''){
								$scope.reqErrMsg = 'Screen Name Could not Configured...!';
		 		  			}else{
		 		  				$scope.addScreenNameList = [{key:null,value : "Please Select"}];
		 		  				angular.forEach(data,function(values){
		 		  					$scope.addScreenNameList.push(values);
		 		  				});
		 		  				values.screenNameList = $scope.addScreenNameList;
		 		  			}
							$scope.$digest();
							}, function() {
							alert('error');
						});
					}
				});
			};
			
			/** Onchange Screen Name - If Already Exists validation */
			$scope.toValidateExistingCombination = function(id,screenNameKey){
				$scope.reqErrMsg="";
				$scope.succMessage="";
				$scope.reqEditFlag=false;
				if($scope.saveorUpdateFlag) {
					angular.forEach($scope.data,function(values){
						if(values.rowId == id){
							$scope.configUsrManualVo={
								'screenName':screenNameKey
								}
							configUsrManualService.toValidateModuleSubModuleScreenCombination($scope.configUsrManualVo).then(
								function(data) {
									if(data==null ||data==''){
				 		  			}else{
				 		  				$scope.reqErrMsg = values.screenName.value+' Following Combination Already Exists...!';
				 		  				values.screenName = {key:null,value : "Please Select"};
				 		  			}
									$scope.$digest();
									}, function() {
									alert('error');
								});
							}
						});
					}else{
						angular.forEach($scope.data,function(values){
							if(values.rowId == id){
								$scope.configUsrManualVo={
									'screenName':screenNameKey,
									'configSakeyId':values.configSakeyId
									}
								configUsrManualService.toValidateModuleSubModuleScreenCombinationForEdit($scope.configUsrManualVo).then(
									function(data) {
										if(data==null ||data==''){
					 		  			}else{
					 		  				$scope.reqErrMsg = values.screenName.value+' Following Combination Already Exists...!';
					 		  				values.screenName = {key:null,value : "Please Select"};
					 		  			}
										$scope.$digest();
										}, function() {
										alert('error');
									});
								}
							});
				}
			};
			
			$scope.DownloadTemplateForConfigUsrManual =function(){
				configUsrManualService.downloadConfigUsrManualTemplate()
		  			.then(
		  				function(data){
		  					
		  		           	  var fileName='Configure User Manual And Videos';
		  		                    
		  		              var anchor = angular.element('<a/>');
		  		                 anchor.attr({
		  		                     href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + data,
		  		                     target: '_self',
		  		                     download: fileName});

		  		                 angular.element(document.body).append(anchor);
		  		                 anchor[0].click();
		  		               $scope.searchApplication(false);
		  		              $scope.succMessage = '{{"configUsrManual.fileUpload.Success"  | translate}}';
		  		                 angular.element('#uploadModalSingle').modal('hide');
		  		                 $window.scrollTo(0, angular.element('#messages').offsetTop);
		  		                
		  		             },
		  				function()
		  				{
		  					alert('error');
		  				}
		  				);
		    	  };
			
		
	/** Download Export Excel */
			$scope.downloadExcelAppliMaintenCntrl =function(){
				
				$scope.selectedRowList=[];
  		    	angular.forEach($scope.rowCollection,function(values){
						$scope.addSelectedcollection.push({
				     		'rowId':values.rowId,
				     		'appItmsNoDesc':values.appItmsNoDesc,
  				     		'moduleDesc':values.moduleDesc,
  				     		'subModuleDesc':values.subModuleDesc,
  				     		'screenNameDesc':values.screenNameDesc,
  				     		'fieldCode' : values.fieldCode,
  				     		'fieldName' : values.fieldName,
  							'audioHelp' : values.audioHelp,
  							'toolTipHelp' : values.toolTipHelp,
							'helpText' :values.helpText
						});   
						$scope.selectedRowList=$scope.addSelectedcollection;
					});
  		    	
  			  $http.post("http://35.154.237.162:9060/ChatBotService/configUsrManual/downloadExcel",$scope.selectedRowList).then(function(response) {
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
  		              
  		           $scope.succMessage = '{{"configUsrManual.fileUpload.Success"  | translate}}';
  		              angular.element('#uploadModalSingle').modal('hide');
  		              $window.scrollTo(0, angular.element('#messages').offsetTop);
  		              $scope.searchApplication(false);
  		          } else {
  		          $window.scrollTo(0, angular.element('#messages').offsetTop);
  		          $scope.reqErrMsg = '{{"configUsrManual.fileUpload.fail"  | translate}}';
  		          }
  		      });
 		 	  };
	
  		 	  
  		 	 $scope.openSinglePop = function() {
  			        $route.reload();
  			    }
			
	
}]);
	   

	fordApp.service("configUsrManualService",['$http',function($http){
		
		/**  To Load Module List from on change Application ITMS No for Add/Edit */
		this.getModuleDetailsForOnChange = function(data) {
			return new Promise(function(resolve, reject) {
						$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/onChangeModuleOnload',data).then(
							function(response) {
								console.log("inside service"+data);
								
								resolve(response.data);
							},function(error) {
								reject([]);
							});
					});
		}
		
		/**  To Load Sub Module List from on change Module for Add/Edit */
		this.getSubModuleDetailsForOnChange = function(data) {
			return new Promise(function(resolve, reject) {
						$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/onChangeSubModuleOnload',data).then(
							function(response) {
								console.log("inside service"+data);
								
								resolve(response.data);
							},function(error) {
								reject([]);
							});
					});
		}
		
		/**  To Load Screen Name List from on change Module for Add/Edit */
		this.getScreenNameDetailsForOnChange = function(data) {
			return new Promise(function(resolve, reject) {
						$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/onChangeScreenNameOnload',data).then(
							function(response) {
								console.log("inside service"+data);
								
								resolve(response.data);
							},function(error) {
								reject([]);
							});
					});
		}
		
		/**  To Validate for Module, SubModule, Screen Name combination for Add/Edit */
		this.toValidateModuleSubModuleScreenCombination = function(data) {
			return new Promise(function(resolve, reject) {
						$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/toValidateCombination',data).then(
							function(response) {
								console.log("inside service"+data);
								
								resolve(response.data);
							},function(error) {
								reject([]);
							});
					});
		}
		
		/**  To Validate for Module, SubModule, Screen Name combination for Add/Edit */
		this.toValidateModuleSubModuleScreenCombinationForEdit = function(data) {
			return new Promise(function(resolve, reject) {
						$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/toValidateCombinationForEdit',data).then(
							function(response) {
								console.log("inside service"+data);
								
								resolve(response.data);
							},function(error) {
								reject([]);
							});
					});
		}
		
		/**  To Load Module List for Search */
		this.searchInputModule = function() { 
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/searchModuleOnload').then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
		}
		
		/**  To Load Sub Module List for Search */
		this.searchInputSubModule = function() { 
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/searchSubModuleOnload').then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
		}
		
		/**  To Load Screen Name List for Search */
		this.searchInputScreenName = function() { 
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/searchScreenNameOnload').then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
		}
		
		/**  To Load Itms No List for Search */
		this.srchInputAllApplItmsNo = function() { 
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/applItmsNoOnload').then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
		}
		
		/**  To Load Itms No List for Search */
		this.srchInputAllApplItmsNoAndDescListOfString = function() { 
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/applItmsNoAndDescOnload').then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
		}
		
		
		/** Application Maintenance Search  */
		this.srchConfigUsrManualMainServ = function(data) { 
			
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/configUsrManualSearch',data).then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
			}
		
		
		this.saveConfigUsrManual = function(data) { 
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/saveConfigUsrManual',data).then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
			
			}
		
		this.updateConfigUsrManual = function(data) { 
			return new Promise(function(resolve,reject){
				
				$http.post('http://35.154.237.162:9060/ChatBotService/configUsrManual/updateConfigUsrManual',data).then(
					function(response){
						resolve(response.data);
					},
					function(error){
						reject([]);
					});
			});
			
		}
		
		this.downloadConfigUsrManualTemplate=function()  {
			return new Promise(function(resolve,reject){
			$http.get("http://35.154.237.162:9060/ChatBotService/configUsrManual/downloadTemplate").then(
			function(response){
				
			resolve(response.data);
			},
			function(error){
				
			reject([]);
			});
			});
	}
		
		
	}]);
	    



