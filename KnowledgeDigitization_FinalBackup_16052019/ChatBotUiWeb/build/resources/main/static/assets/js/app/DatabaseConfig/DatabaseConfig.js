fordApp.controller('DbconfigController',['$scope','$rootScope','$window','$timeout',
		'$http','$route','DbconfigService',function($scope, $rootScope,$window, $timeout, $http,$route,DbconfigService){
	
	$scope.addcollection=[];
	$scope.wholedata=[];
	$scope.itmsdetails=[];
	$scope.dbvo=[];
	$scope.searchvo=null;
	$scope.selected=[];
	$scope.dbvo=[];
	$scope.searchvo=null;
	$scope.selected=[];
	$scope.dbvo.listitmsNo=[];
	$scope.itmsNoData="";
	$scope.succMessage="";
	$scope.reqErrs="";
	$scope.reqErrMsg="";
	$scope.dbvo.listapplicationName=[];
	$scope.dbvo.listdatabaseName=[];
	$scope.dbvo.listdatabaseType=[];
	$scope.databaseTypeData=[];
	$scope.applicationNameS=""
	$scope.selecteditmsNo=[];
	$scope.rowCollection=[];
	$scope.selecteddbType=[];
	$scope.selecteddbName=[];
	$scope.asteriskFlag = false;
    $scope.dataadd=false
    $scope.addStatus=false;
    $scope.addcollection=[]
    $scope.dbvo=[];
	$scope.init = function() {
		$scope.rowCollection=[];
		$scope.dbvo=[];
		$scope.searchvo=null;
		$scope.selected=[];
		$scope.dbvo.listitmsNo=[];
		$scope.itmsNoData="";
		
		$scope.reqErrs="";
		$scope.reqErrMsg="";
		$scope.dbvo.listapplicationName=[];
		$scope.dbvo.listdatabaseName=[];
		$scope.dbvo.listdatabaseType=[];
		$scope.databaseTypeData=[];
		$scope.applicationNameS=""
		$scope.selecteditmsNo=[];
		
		$scope.selecteddbType=[];
		$scope.selecteddbName=[];
		$scope.asteriskFlag = false;
        $scope.dataadd=false
        $scope.addStatus=false;
        $scope.addcollection=[]
        $scope.dbvo=[];
         DbconfigService.getRecords().then(function(data){
        	 $scope.addStatus = false;
				$scope.data = data;	
				
				$scope.rowCollection=data;
				
				$scope.wholedata=data;
				
			   	$scope.itmsNoData=data;
			   
				$scope.databaseType=data;
				
				$scope.dataflag=true;
				angular.forEach($scope.databaseType,function(row){
					if($scope.dataflag){
					if(row.listitmsNo!=null || row.listitmsNo!=undefined){
						$scope.dataflag=false;
						$scope.dbvo.listitmsNo=row.listitmsNo //dropdown in search
						$scope.itmsNoData=row.listitmsNo;//dropdown in addrow
						$scope.dbvo.listapplicationName=row.listapplicationName//label in search
						$scope.$digest();
						//$scope.dbvo.listapplicationAcronym=row.listapplicationAcronym
						$scope.dbvo.listdatabaseName=row.listdatabaseName//dropdown in search
						
					
					}
					}
					
				})
				
				
         },function(data){
        	 $scope.reqErrMsg="Fetching Datatable Data Error"; 
         })
        
         DbconfigService.getitms().then(function(data){
        	 $scope.itmsdetails=data;
        	 $scope.$digest();
         },function(data){
        	 alert('error');
         })
         DbconfigService.getDbType().then(function(data){
        	 $scope.dbvo.listdatabaseType=data;
        	$scope.databaseTypeData=data;
        	 });
		
	}
	
	
	$scope.refreshData=function(){
		$scope.applicationNameS=""
		$scope.selecteditmsNo=[];
		$scope.applicationAcronymS="";
		$scope.selecteddbType=[];
		$scope.selecteddbName=[];
		$scope.rowCollection=[];
		$scope.selected=[];
		$scope.addcollection=[];
		$scope.init();
	};
	//add new row
	$scope.addNewRowCntrl = function() {
		$scope.asteriskFlag = true;
		$scope.reqErrMsg = "";
        $scope.succMessage = "";
        $scope.rowCollection = [];
        $scope.addStatus=true;
		$scope.dataadd = true;
		$scope.addFlag = true;
		
		
		
		$scope.requireErrMsgList = [];
		
		$scope.addcollection.push({
			'id' : $scope.addcollection.length + 1,
			'itmsNo' : "",
			'applicationName' : "",
			'applicationAcronym' : "",
			'databaseType' : [],
			'hostName' : "",
			'portNumber': '',
			'driverName' : "",
			'databaseName' : "",
			'userName':"",
			'password':"",
			'isSelected':true

		});
		
		$scope.rowCollection = $scope.addcollection;
		
	};//end of add new row
	
	//ng-change itmsno
	$scope.onChangeItmsNo=function(id){
		var itmsno=id.itmsNo.split("-",1);
		
		$scope.applicationName="";
		$scope.applicationAcronym="";
		angular.forEach($scope.itmsdetails,function(values){
			if(values.itmsNo == itmsno){
				
				id.applicationName=values.applicationName;
				id.applicationAcronym=values.applicationAcronym;
					}
			
		});
				
	}
	//add or update records
	$scope.saveData=function(collection){
		
		$scope.reqErrMsg=""
		$scope.reqErrs=""
		$scope.succMessage=""
		$scope.saveDataList = [];
		 $scope.selectedData=[];
		 $scope.dbDataTable=[];
	angular.forEach(collection, function(row){ 		
		if(row.selected){
			$scope.saveDataList.push(row)
		}
	});
	//based on saveddata
	if($scope.saveDataList!=0){
        $scope.dbconfigDataTable=[];
        
        angular.forEach($scope.saveDataList, function(values){
            
             $scope.dbTableVO={
                     
                         'id':values.id,
                         'itmsNo':values.itmsNo,
                         'applicationName':$scope.applicationName,
                         'applicationAcronym':$scope.applicationAcronym,
                         'databaseType':values.databaseType,
                         'hostName':values.hostName,
                         'portNumber':values.portNumber,
                         'driverName':values.driverName,
                         'ipAddress':values.ipAddress,
                         'databaseName':values.databaseName,
                         'userName':values.userName,
                         'password': values.password
                    }
            console.log($scope.dbTableVO)
            
                    $scope.dbDataTable.push($scope.dbTableVO)
                 
        });
	
        $scope.selectedData=$scope.dbDataTable;
        $scope.flag=$scope.validate($scope.selectedData);  
        if(!$scope.flag)  {
            
            if(!$scope.selectedData.length == 0){
                
                 if($scope.addStatus){//addstatus == true , add functionality else update functionality will execute.
                	 DbconfigService.saveItms($scope.dbDataTable)
                      .then(
                          function(data){   	 					
                              $scope.msg=data
                              if($scope.msg=="Record(s) Saved Successfully"||$scope.msg=="Record Saved Successfully"){
                                  $scope.succMessage=data
                                  $scope.$digest();
                                  $scope.refreshData();
                                  $scope.addFlag = false;
                                  $scope.succMessage=data
                                  $scope.dataadd=false
                              }else{
                                  $scope.reqErrMsg=data
                                  $scope.$digest();
                              }
                              
                          
                          },
                          function(data)
                          {
                              $scope.reqErrMsg=data
                              $scope.reqErrMsg+=" Save Error"
                          }
                          );
                    
                }else{
$scope.dbTableVO=[]
$scope.dbDataTable=[]
angular.forEach($scope.saveDataList, function(values){	
 $scope.dbTableVO={
           
             'id':values.id,
             'itmsNo':values.itmsNo,
             'applicationName':$scope.applicationName,
             'applicationAcronym':$scope.applicationAcronym,
             'databaseType':values.databaseType,
             'hostName':values.hostName,
             'portNumber':values.portNumber,
             'driverName':values.driverName,
            'databaseName': values.databaseName,
            'ipAddress':values.ipAddress,
             'userName':values.userName,
            'password': values.password
        }

        $scope.dbDataTable.push($scope.dbTableVO)
               
});

					DbconfigService.updateItms($scope.dbDataTable).then(
                           function(data){
                               $scope.msg = data;
                               if($scope.msg=="Record(s) Updated Successfully" ||$scope.msg=="Record Updated Successfully"){
                                   $scope.refreshData();
                                   $scope.succMessage=data;
                               
                                 	 					
                              }else{
                                  $scope.selectedData = null;
                                  $scope.reqErrMsg=data;
                        }
                               
                               
                               $scope.$digest();
                           },
                           function()
                           {
                               $scope.reqErrMsg='update error';
                           });
                 }
                }
        }
        }else{
            $scope.reqErrMsg='No records selected!';
        }

	}
	//validate
	$scope.validate=function(selectedData){
		// alert("inside validation")
		$scope.mandFlag=false;
		$scope.uniFlag=false;
    	$scope.pcFlag=false;
    	$scope.operationFlag=false;
    	 $scope.flag = false;
	
    	$scope.reqErrMsg="";
    	$scope.missErrMsgArry=[];
    	$scope.reqErrs = [];
    	$scope.missErrMsg="";
    	
    	angular.forEach(selectedData, function(row) {

    		 if(row.itmsNo== '' || row.itmsNo==null || row.itmsNo==undefined){
                if($scope.flag)
				 $scope.reqErrMsg+="," 
                $scope.reqErrMsg="ITMS No. ";
    			
    		       $scope.mandFlag=true;
                   $scope.flag = true;

    		 }
    	
    		 if(row.databaseType== '' || row.databaseType==null || row.databaseType==undefined ||row.databaseType=="Please Select"){
    			 if($scope.flag)
    				 $scope.reqErrMsg+=", "
    			 $scope.reqErrMsg+="Database Type";
    			
    		       $scope.mandFlag=true;
    		       $scope.flag = true;
             }else if(row.databaseType.length >15){
            	 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="Database Type length should not be more than 15";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    		    
             }
    		 if(row.hostName==null||row.hostName==undefined || row.hostName==""){	
    			 if($scope.flag)
    				 $scope.reqErrMsg+=", "
			 $scope.reqErrMsg+="Host Name";
			
		       $scope.mandFlag=true;
		       $scope.flag = true;
    		 }else if(row.hostName.length>100){
            	 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="Host Name type length should not be more than 100";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    		      
             }
    		 
    		
    		
    		 
    		
    		 
    		 if(row.ipAddress=="" ||row.ipAddress==null || row.ipAddress==undefined){
    			 if($scope.flag)
    				 $scope.reqErrMsg+=", "
    			 $scope.reqErrMsg+="IP Address";
    		
    		       $scope.mandFlag=true;
    		       $scope.flag = true;
    		 }else if(specialCharacterNumValidationip(row.ipAddress)){
    			 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="IP Address should be entered numeric only";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    		       
    		 }else if(row.ipAddress.length >15){
            	 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="IP Address length should not be more than 15";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    		      
             }
    		 
    		 
    		 if(row.portNumber=="" ||row.portNumber==null || row.portNumber==undefined ){
    			 if($scope.flag)
    				 $scope.reqErrMsg+=", "
    			 $scope.reqErrMsg+="Port Number";
    			
    		       $scope.mandFlag=true;
    		       $scope.flag = true;
    		 }
    		 else if(specialCharacterNumValidation(row.portNumber)){
    			 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="Port Number should be entered numeric only";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    	
    		 }
    		 else if(row.portNumber.length >5){
            	 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="Port Number length should not be more than 5";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    		      
             }
    		
    		 
    		 if(row.driverName=="" ||row.driverName==null || row.driverName==undefined){
    			 if($scope.flag)
    				 $scope.reqErrMsg+=", "
    			 $scope.reqErrMsg+="Driver Name";
    		
    		       $scope.mandFlag=true;
    		       $scope.flag = true;
    		 }else if(row.driverName.length >250){
            	 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="Driver Name length should not be more than 250";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    		    
             }
    		 else if(specialCharacterValidationDriverName(row.driverName)){
    			 if($scope.pcFlag)
    			 $scope.missErrMsg+=""
        			 $scope.missErrMsg+="Driver Name should not contains number or special characters";
        			 $scope.pcFlag = true;
        		       $scope.mandFlag=true;
        		       
    		 }
    		 
    		 if(row.databaseName=="" ||row.databaseName==null || row.databaseName==undefined){
    			 if($scope.flag)
    				 $scope.reqErrMsg+=", "
    			 $scope.reqErrMsg+="Database Name";
    		
    		       $scope.mandFlag=true;
    		       $scope.flag = true;
    		 } 
    		 else if(specialCharacterValidation(row.databaseName)){
    			 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="Database Name should be entered alpha numeric only";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    		    
    		 }else if(row.databaseName.length >50){
            	 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="Database Name length should not be more than 50";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    		    
             }
    		 
    		 
    		 if(row.userName=="" ||row.userName==null || row.userName==undefined){
    			 if($scope.flag)
    				 $scope.reqErrMsg+=", "
    			 $scope.reqErrMsg+="UserName";
    		
    		       $scope.mandFlag=true;
    		       $scope.flag = true;
    		 }else if(specialCharacterValidation(row.userName)){
    			 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="UserName  should be entered alpha numeric only";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    		    
    		 }else if(row.userName.length >8){
            	 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="UserName length should not be more than 8";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    		      
             }
    		 
    		 
    		 
    		 if(row.password=="" ||row.password==null || row.password==undefined){
    			 if($scope.flag)
    				 $scope.reqErrMsg+=", "
    			 $scope.reqErrMsg+="Password";
    			
    		       $scope.mandFlag=true;
    		       $scope.flag = true;
    		 }
    		 else if(row.password.length >25){
            	 if($scope.pcFlag)
    				 $scope.missErrMsg+=", "
    			 $scope.missErrMsg+="Password length should not be more than 25";
    			 $scope.pcFlag = true;
    		       $scope.mandFlag=true;
    		   
             }
    		 if($scope.reqErrMsg != "" && $scope.reqErrMsg!=undefined){
                 $scope.finalMsg = "Line no["+row.id+"] : ";
                 
             }
    		 else
                 $scope.finalMsg=""
                	 
    		 if($scope.missErrMsg!= "" && $scope.missErrMsg!=undefined){
    			 $scope.FMSg="Line no["+row.id+"] :";
    		 }
    		 else{
    			 $scope.FMSg="";
    		 }
    			 
    		 $scope.missErrMsgArry.push($scope.FMSg+$scope.missErrMsg);
    		 $scope.missErrMsg="";
    		 if(!$scope.pcFlag){
    			 $scope.missErrMsgArry=[];
    		 }
    		
    		 $scope.reqErrs.push($scope.finalMsg+$scope.reqErrMsg+' should be entered mandatorily');	 
    		 $scope.reqErrMsg="";
    		 if(!$scope.flag)
	    		 $scope.reqErrs=[]
    	});
    	
    	if($scope.flag || $scope.pcFlag){
    		return true;
    	}
    	else{
    		return false;
    	}
    	   
	}
	//clear errors
	 $scope.clearErrorArray = function(index){
         $scope.reqErrs.splice(index, 1);
     }
	 $scope.missclearErrorArray = function(index){
         $scope.missErrMsgArry.splice(index, 1);
     }

	 
	 //search functionality
	 
	 $scope.searchDbConfig=function(){
		
		 $scope.searchvo=null;
		 $scope.searchVoData=null;
	      $scope.reqErrMsg="";
	      $scope.addcollection=[]
	    	  $scope.succMessage="";
		$scope.searchvo={
				 'inputItmsNo':$scope.selecteditmsNo,
				 'inputApplicationName':$scope.applicationNameS,
				 'inputDatabaseType':$scope.selecteddbType,
				 'inputDatabaseName':$scope.selecteddbName
		} 
		
	
		 $scope.searchVoData=$scope.searchvo;
		 $scope.dataadd=false
		DbconfigService.getsearch($scope.searchVoData).then(function(data){
			$scope.rowCollection=data;
			$scope.data=data;
			$scope.$digest();
			
			$scope.dataadd=false
            if(data==undefined || data==[] || data=="" || data<0){
                $scope.reqErrMsg="No records found"
                $scope.$digest();
            }
            else if(data >0){
            	
                $scope.succMessage=data.length+" Record(s) found"
            }
		},function(error){
			alert('error')
		})
		 
	 }	 
	 
	 //download template
	 $scope.downloadTemplate=function(){
		 
		 DbconfigService.getdownloadTemplate()
			.then(
				function(data){
					
		           	  var fileName='DataBase Configuration Template';
		                    
		              var anchor = angular.element('<a/>');
		                 anchor.attr({
		                     href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + data,
		                     target: '_self',
		                     download: fileName});

		                 angular.element(document.body).append(anchor);
		                 anchor[0].click();
		               
		                
		             },
				function()
				{
					$scope.reqErrMsg='Download Teamplate Error'
				}
				);
 } 
	 
	 //download excel
	 $scope.downloadExcel=function(){
		 console.log(angular.toJson($scope.rowCollection))

     
  
          if(!$scope.addStatus) {
        	
        	  console.log($scope.rowCollection);
        	  
            $http.post($rootScope.baseUrl+"/DbConfig/downloadExcel",$scope.rowCollection).then(function(response) {
            	
                if (response) {
                    var dt = new Date(); 
                       $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
                       $scope.fileName = $scope.createdDate+"Database Configuration";
                  
                   
                       
                 var anchor = angular.element('<a/>');
                    anchor.attr({
                        href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + response.data,
                        target: '_self',
                        download: $scope.fileName});
      
                    angular.element(document.body).append(anchor);
                    anchor[0].click();
                    
                 $scope.succMessage = 'Successfully Downloaded!';
                    angular.element('#uploadModal').modal('hide');
                    $window.scrollTo(0, angular.element('#messages').offsetTop);
                   
                } else {
                $window.scrollTo(0, angular.element('#messages').offsetTop);
                $scope.reqErrMsg = 'Failed to download';
                }
      
            });
        }else{
             $scope.reqErrMsg = 'No records are avaialble to export to excel';
        }
		
	  }
	 //upload template dialog box
	    $scope.uploadTemplate = function(){
            $scope.reqErrMsg="";
            
            $scope.succMessage="";
            $scope.reqErrs=[];
		
            angular.element("input[id='single']").val(null);                
             angular.element('#uploadModal').modal('show');
        }
	 
	    //file uploading
	    $scope.getExcelFileDetails=function(e){
	    	
	    	$scope.files=[];
            $scope.reqErrMsg="";
          
            $scope.succMessage="";
            $scope.errmsg="";
           
			$scope.SingleMessage = "";
			
	          $scope.$apply(function() {
	              var fileType = e.files[0].name.substring(e.files[0].name.lastIndexOf("."), e.files[0].name.length);
	              if ( fileType == '.xlsx') {
	            	  $scope.SingleMessage = 'File uploaded is xlsx, Please click upload to save data';
	                      $scope.files.push(e.files[0]);
	              } else {
	                  $scope.errmsg = 'Please select proper file with .xlsx' ;
	                 // $window.scrollTo(0, angular.element('#messages').offsetTop);
	              }
	          });
          };
          
          
           $scope.SingleMessage = "";  
          $scope.excelUploadFile = function() {
	      $scope.FileMessage = "";
           $scope.SingleMessage = "";               
            $scope.errmsg="";
	     var data = new FormData();
	      if($scope.files!=null && $scope.files!=undefined){
             data.append("file", $scope.files[0]);

             var config = {
                 transformRequest: angular.identity,
                 transformResponse: angular.identity,
                 headers: {
                     'Content-Type': undefined
                 }
             }
             if($scope.files.length==0){
                   $scope.errmsg='Please upload a file with valid file name';
               }
               else{
             $scope.fileName = $scope.files[0].name;
             if ($scope.fileName.startsWith('DataBase Configuration Template'))
             {
                 $http.post($rootScope.baseUrl+"/DbConfig/ExcelUpload", data, config).then(function(response) {
                     if (response) {
                         var dt = new Date(); 
                         $scope.createdDate= (dt.getMonth() + 1) + "/" + dt.getDate() + "/" + dt.getFullYear()+ " " +dt.getHours()+ ":" +dt.getMinutes() + ":" + dt.getSeconds() ;
                         var fileName = $scope.createdDate+"Database Configuration_Log";
                       
                      var anchor = angular.element('<a/>');
                         anchor.attr({
                             href: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,' + response.data,
                             target: '_self',
                             download: fileName});

                         angular.element(document.body).append(anchor);
                         anchor[0].click();
                         
                      $scope.succMessage = 'Successfully downloaded the Database Configuration.xlsx file';
                         angular.element('#uploadModal').modal('hide');
                         $window.scrollTo(0, angular.element('#messages').offsetTop);
                         $scope.clearFile();
                         $scope.refreshData();
                      
                     } else {
                     $window.scrollTo(0, angular.element('#messages').offsetTop);
                     $scope.errmsg = 'Failed to download DBconfiguration.xlsx file';
                     }

                 });
             }
             else  
             {
                 angular.element('#uploadModal').modal('hide');
                 $scope.errmsg =  'Please upload a file with valid file name';
             }

           } 
         }else{
             $scope.errmsg =  'Please choose file before uploading';                                       
         }
	      

	    	
	    }
          //clear in upload dialog
          
 		 $scope.clearFile=function(){
 			 
 			 $scope.errmsg=""
 		  	 $scope.SingleMessage = "";
 		  	 $scope.ErrorMessage ="";
 		  	 angular.element("input[type='file']").val(null);
 		  	 $scope.files = [];
 		 }
 		 
 		 
 		 
 		 //select and selectall
 		 

		 // Function to get data for all selected items
	       $scope.selectAll = function (rowCollection) {
	         // if there are no items in the 'selected' array,
	         // push all elements to 'selected'
	    	   
	         if ($scope.selected.length === 0) {
	        	 $scope.asteriskFlag = true;
	           angular.forEach(rowCollection, function(val) {
	            $scope.selected.push(val.id); 
	             
	           });
	         // if there are items in the 'selected' array, 
	         // add only those that ar not
	         } else if ($scope.selected.length > 0 && $scope.selected.length != rowCollection.length) {
	        	 $scope.asteriskFlag = false;
	            angular.forEach(rowCollection, function(val) {
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
	       
	       //autocomplete
	       $scope.autoComplete=function(projectName){
	    	   $scope.AppNameList=[];
				if(projectName!="" && projectName!=undefined)
					DbconfigService.autoCompletePN(projectName).then(function(data){
						console.log(data)
					$scope.AppNameList=data;
						
					$scope.$digest();
				
					},function(){
						$scope.reqErrMsg='Fetching Autocomplete Data Error'}
					)
			}
	       $scope.fillTextbox=function(string){
				$scope.applicationNameS=string;
               $scope.AppNameList=null;
               $scope.$digest();
           }
	       
	       $scope.$watch("applicationNameS", function () {
               if ($scope.applicationNameS == "") {
                   $scope.AppNameList = null;
               }
               
           });
	
}]);

fordApp.service('DbconfigService',['$http','$rootScope',
	function($http, $rootScope){
	
	this.autoCompletePN=function(projectName){
		console.log(projectName)
		return new Promise(
				function(resolve, reject) {				
					$http.post($rootScope.baseUrl+'/DbConfig/searchProjectName/'+projectName)
					
					.then(function(response) {
						resolve(response.data);
					}, function(error) {
						reject([]);
					});
				});	
	}
	
	
	
	
	this.getRecords=function(){
		return new Promise(
				function(resolve, reject) {
					$http.get($rootScope.baseUrl+'/DbConfig/getAllRecords')
					.then(function(response) {
						resolve(response.data);
					}, function(error) {
						reject([]);
					});
				});			
	}//end getall record
	
	this.getitms=function(){
		return new Promise(function(resolve,reject){
			$http.get($rootScope.baseUrl+'/DbConfig/getitms').then(function(response){
				resolve(response.data);
			},function(error){
				reject([]);
			})
		});
	}
	

	this.saveItms=function(listData){
		return new Promise(
				function(resolve, reject) {				
					$http.post($rootScope.baseUrl+'/DbConfig/addRowRecords',listData)					
					.then(function(response) {
						resolve(response.data);
					}, function(error) {
						reject([]);
					});
				});	
	}
	this.updateItms=function(listData){
		return new Promise(
				function(resolve, reject) {				
					$http.put($rootScope.baseUrl+'/DbConfig/updateRowRecords',listData)					
					.then(function(response) {
						resolve(response.data);
					}, function(error) {
						reject([]);
					});
				});	
    }
	
	this.getsearch=function(searchdata){
	
		return new Promise(function(resolve,reject){
			$http.post($rootScope.baseUrl+'/DbConfig/getsearchrecords',searchdata).then(function(response){
				resolve(response.data)
			},function(error){
				
				reject([])
			});
		});
	}
	 this.getdownloadTemplate=function(){
			return new Promise(
					function(resolve, reject) {
						$http.get($rootScope.baseUrl+'/DbConfig/downloadTemplate')
						.then(function(response) {
							resolve(response.data);
						}, function(error) {
							reject([]);
						});
					});	
		}
	
	 
	 this.getDbType=function(){
		 return new Promise(function(resolve,reject){
			 $http.get($rootScope.baseUrl+'/DbConfig/databaseTpe').then(function(response){
				 resolve(response.data);
			 },function(error){
				 reject([]);
			 });
		 });
	 }
	 
//end service	
}]);