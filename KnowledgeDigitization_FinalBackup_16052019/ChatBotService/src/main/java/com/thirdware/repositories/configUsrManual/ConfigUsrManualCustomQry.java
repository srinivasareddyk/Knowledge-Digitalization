package com.thirdware.repositories.configUsrManual;

import java.sql.Connection;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import com.thirdware.util.DBPropertyUtil;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.configUsrManual.ConfigUsrManualVo;


@Repository
public class ConfigUsrManualCustomQry {

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	Connection conn=null;
	
	public List<SelectedItem> getAllApplicationItmsNo() throws SQLException {
	  	
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        List<SelectedItem> getAllModuleList=new ArrayList<SelectedItem>();
        SelectedItem selectedItemObj=null;
       
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" select distinct A.KDA004_APPL_ITMS_R,A.KDA004_APPL_N,A.KDA004_APPL_ACRNM_C from MKDA005_MDUL M INNER JOIN MKDA004_APPL_DTL A ");
        	query.append(" ON M.KDA004_APPL_ITMS_R=A.KDA004_APPL_ITMS_R where KDA005_PARNT_MDUL_K IS NULL  ");
        	System.out.println("-------------------->>>>>>>  : "+query.toString());
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
		
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			selectedItemObj=new SelectedItem();
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
				
        			if (map.get("KDA004_APPL_ITMS_R") != null && !StringUtils.isEmpty(map.get("KDA004_APPL_ITMS_R").toString())) {
        				selectedItemObj.setKey(map.get("KDA004_APPL_ITMS_R").toString());
        			}
        			if (map.get("KDA004_APPL_ACRNM_C") != null && !StringUtils.isEmpty(map.get("KDA004_APPL_ACRNM_C").toString())) {
        				selectedItemObj.setValue(map.get("KDA004_APPL_ITMS_R").toString()+"-"+map.get("KDA004_APPL_ACRNM_C").toString());
        			}
        			getAllModuleList.add(selectedItemObj);
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		
		return getAllModuleList;
	}
	
	
	public List<String> getApplItmsNoAndDescListOfString() throws SQLException {
	  	
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        List<String> getAllAppItmsNoAndDescList=new ArrayList<String>();
       
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" select distinct A.KDA004_APPL_ITMS_R,A.KDA004_APPL_N,A.KDA004_APPL_ACRNM_C from MKDA005_MDUL M INNER JOIN MKDA004_APPL_DTL A ");
        	query.append(" ON M.KDA004_APPL_ITMS_R=A.KDA004_APPL_ITMS_R where KDA005_PARNT_MDUL_K IS NULL  ");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
		
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
				
        			if (map.get("KDA004_APPL_ACRNM_C") != null && !StringUtils.isEmpty(map.get("KDA004_APPL_ACRNM_C").toString())
        					&& map.get("KDA004_APPL_ITMS_R") != null && !StringUtils.isEmpty(map.get("KDA004_APPL_ITMS_R").toString())) {
        				getAllAppItmsNoAndDescList.add(map.get("KDA004_APPL_ITMS_R").toString()+"-"+map.get("KDA004_APPL_ACRNM_C").toString());
        			}
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		
		return getAllAppItmsNoAndDescList;
	}
	
	
	
	public List<SelectedItem> getSubModuleDetailsForOnChange(String moduleId) throws SQLException {
	  	
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        List<SelectedItem> getAllModuleList=new ArrayList<SelectedItem>();
        SelectedItem selectedItemObj=null;
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" SELECT KDA005_MDUL_K,KDA005_MDUL_N,KDA005_MDUL_C FROM MKDA005_MDUL WHERE KDA005_PARNT_MDUL_K="+moduleId+"");
        	
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
		
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			selectedItemObj=new SelectedItem();
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
				
        			if (map.get("KDA005_MDUL_K") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_K").toString())) {
        				selectedItemObj.setKey(map.get("KDA005_MDUL_K").toString());
        			}
        			if (map.get("KDA005_MDUL_N") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_N").toString())
        					&& map.get("KDA005_MDUL_C") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_C").toString())) {
        				selectedItemObj.setValue(map.get("KDA005_MDUL_C").toString()+"-"+map.get("KDA005_MDUL_N").toString());
        			}
        			getAllModuleList.add(selectedItemObj);
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		
		return getAllModuleList;
	}
	
	
public List<SelectedItem> getModuleDetailsForOnChange(ConfigUsrManualVo configUsrManualVo) throws SQLException {
	  	
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        List<SelectedItem> getAllModuleList=new ArrayList<SelectedItem>();
        SelectedItem selectedItemObj=null;
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" SELECT KDA005_MDUL_K,KDA005_MDUL_N,KDA005_MDUL_C FROM MKDA005_MDUL WHERE KDA005_PARNT_MDUL_K IS NULL  AND KDA004_APPL_ITMS_R="+configUsrManualVo.getAppItmsNo()+"");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
		
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			selectedItemObj=new SelectedItem();
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
				
        			if (map.get("KDA005_MDUL_K") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_K").toString())) {
        				selectedItemObj.setKey(map.get("KDA005_MDUL_K").toString());
        			}
        			if (map.get("KDA005_MDUL_C") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_C").toString())
        					&& map.get("KDA005_MDUL_N") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_N").toString())) {
        				selectedItemObj.setValue(map.get("KDA005_MDUL_C").toString()+"-"+map.get("KDA005_MDUL_N").toString());
        			}
        			getAllModuleList.add(selectedItemObj);
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		
		return getAllModuleList;
	}
	
	
	public List<SelectedItem> getModuleListUsingApplItmsNo(String applItmsNo) throws SQLException {
	  	
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        List<SelectedItem> getAllApplItmsNoList=new ArrayList<SelectedItem>();
        SelectedItem selectedItemObj=null;
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" SELECT KDA005_MDUL_K,KDA005_MDUL_N,KDA005_MDUL_C FROM MKDA005_MDUL WHERE KDA005_PARNT_MDUL_K IS NULL  AND KDA004_APPL_ITMS_R="+applItmsNo+"");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
		
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			selectedItemObj=new SelectedItem();
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
				
        			if (map.get("KDA005_MDUL_K") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_K").toString())) {
        				selectedItemObj.setKey(map.get("KDA005_MDUL_K").toString());
        			}
        			if (map.get("KDA005_MDUL_C") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_C").toString())
        					&& map.get("KDA005_MDUL_N") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_N").toString())) {
        				selectedItemObj.setValue(map.get("KDA005_MDUL_C").toString()+"-"+map.get("KDA005_MDUL_N").toString());
        			}
        			getAllApplItmsNoList.add(selectedItemObj);
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		
		return getAllApplItmsNoList;
	}
	

public List<String> getSearchInputModuleSubMaduleValues(String ModuleTypeCode) throws SQLException {
	  	
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        List<String> getAllModuleList=new ArrayList<String>();
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" SELECT KDA005_MDUL_K,KDA005_MDUL_N,KDA005_MDUL_C FROM MKDA005_MDUL WHERE KDA005_MDUL_TYP_C='"+ModuleTypeCode+"'");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
		
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
				
        			if (map.get("KDA005_MDUL_C") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_C").toString())
        					&& map.get("KDA005_MDUL_N") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_N").toString())) {
        				getAllModuleList.add(map.get("KDA005_MDUL_C").toString()+"-"+map.get("KDA005_MDUL_N").toString());
        			}
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		
		return getAllModuleList;
	}
	
public List<SelectedItem> getModuleSubModuleList(String moduleSubModuleId) throws SQLException {
	  	
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        List<SelectedItem> getAllModuleList=new ArrayList<SelectedItem>();
        SelectedItem selectedItemObj=null;
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" SELECT KDA005_MDUL_K,KDA005_MDUL_N,KDA005_MDUL_C FROM MKDA005_MDUL WHERE KDA005_PARNT_MDUL_K="+moduleSubModuleId+"");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
		
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			selectedItemObj=new SelectedItem();
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
				
        			if (map.get("KDA005_MDUL_K") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_K").toString())) {
        				selectedItemObj.setKey(map.get("KDA005_MDUL_K").toString());
        			}
        			if (map.get("KDA005_MDUL_C") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_C").toString())
        					&& map.get("KDA005_MDUL_N") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_N").toString())) {
        				selectedItemObj.setValue(map.get("KDA005_MDUL_C").toString()+"-"+map.get("KDA005_MDUL_N").toString());
        			}
        			getAllModuleList.add(selectedItemObj);
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		
		return getAllModuleList;
	}


	public String getApplItmsNoSakeyOrDescUsingMuduleId(String moduleId,String selectedSakeyOrDesc) throws SQLException {
  	
		Connection conn=null;
		ArrayList<HashMap<String, Object>> resultList = null;
		String applItmsNoSakey="";
		String applItmsNoDesc="";
		String applItmsNoAcrnm="";
		try {
			conn = dataSource.getConnection();
			StringBuffer query=new StringBuffer("");
			if("FetchScreenNameForErrorMsg".equalsIgnoreCase(selectedSakeyOrDesc)) {
				query.append(" SELECT A.KDA004_APPL_ITMS_R,KDA004_APPL_N,KDA004_APPL_ACRNM_C FROM MKDA005_MDUL M INNER JOIN MKDA004_APPL_DTL A ");
				query.append(" ON M.KDA004_APPL_ITMS_R=A.KDA004_APPL_ITMS_R WHERE KDA005_MDUL_K="+moduleId+" ");
			}else {
				query.append(" SELECT A.KDA004_APPL_ITMS_R,KDA004_APPL_N,KDA004_APPL_ACRNM_C FROM MKDA005_MDUL M INNER JOIN MKDA004_APPL_DTL A ");
				query.append(" ON M.KDA004_APPL_ITMS_R=A.KDA004_APPL_ITMS_R WHERE KDA005_PARNT_MDUL_K IS NULL  AND KDA005_MDUL_K="+moduleId+" ");
			}
			
    	
			resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
	    	if(resultList!=null && !resultList.isEmpty()) {
	    		for (int i = 0; i < resultList.size(); i++) {
	    			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
	    			
	    			if (map.get("KDA004_APPL_ITMS_R") != null && !StringUtils.isEmpty(map.get("KDA004_APPL_ITMS_R").toString())) {
	    				applItmsNoSakey=map.get("KDA004_APPL_ITMS_R").toString();
        			}
	    			
	    			if (map.get("KDA004_APPL_N") != null && !StringUtils.isEmpty(map.get("KDA004_APPL_N").toString())) {
	    				applItmsNoDesc=map.get("KDA004_APPL_N").toString();
        			}
	    			
	    			if (map.get("KDA004_APPL_ACRNM_C") != null && !StringUtils.isEmpty(map.get("KDA004_APPL_ACRNM_C").toString())) {
	    				applItmsNoAcrnm=map.get("KDA004_APPL_ACRNM_C").toString();
        			}
	    			
	    			//applItmsNoSakey=map.get("KDA004_APPL_ITMS_R").toString();
	    			//applItmsNoDesc=map.get("KDA004_APPL_N").toString();
	    		}
	    	}
	
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
	return selectedSakeyOrDesc.equalsIgnoreCase("applItmsNoSakey")?applItmsNoSakey:applItmsNoSakey+"-"+applItmsNoAcrnm;
}
	
	public String getModuleSubModuleSakey(String screenNameSakey) throws SQLException {
	  	
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        String moduleSubModuleSakey="";
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" SELECT M.KDA005_MDUL_K as KDA005_MDUL_K,M.KDA005_MDUL_C as KDA005_MDUL_C");
        	query.append(" from MKDA005_MDUL M inner join MKDA005_MDUL S ON M.KDA005_MDUL_K=S.KDA005_PARNT_MDUL_K");
        	query.append(" where S.KDA005_MDUL_K="+screenNameSakey+"");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
        			moduleSubModuleSakey=map.get("KDA005_MDUL_K").toString();
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		return moduleSubModuleSakey;
	}
	
public String getModuleDescUsingModuleId(String moduleId) throws SQLException {
	  	
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        String moduleDesc="";
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" SELECT KDA005_MDUL_K,KDA005_MDUL_N from MKDA005_MDUL where KDA005_MDUL_K="+moduleId+"");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
        			moduleDesc=map.get("KDA005_MDUL_N").toString();
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		return moduleDesc;
	}
	
	
	public List<ConfigUsrManualVo> getConfigUsrManualSearchResult(ConfigUsrManualVo configUsrManualVo) throws SQLException {
	  	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        List<ConfigUsrManualVo> configUsrManualSearchList=new ArrayList<ConfigUsrManualVo>();
        ConfigUsrManualVo configUsrManualVoObj=null;
        int rowId=1;
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" SELECT KDA006_FLD_K,KDA005_MDUL_K,  ");
        	query.append(" KDA006_FLD_N,KDA006_FLD_C,KDA006_AUDO_HELP_F,KDA006_TOOL_TIP_F,KDA006_HELP_TEXT_X, ");
        	query.append(" KDA006_CRT_USR_C,KDA006_CRT_S,KDA006_CRT_PROC_C,KDA006_LST_UPDT_USR_C,KDA006_LST_UPDT_S,KDA006_LST_UPDT_PROC_C ");
        	query.append(" FROM MKDA006_FLD_HELP ");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
		
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			configUsrManualVoObj=new ConfigUsrManualVo();
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
				
        			configUsrManualVoObj.setRowId(rowId);
        			if (map.get("KDA006_FLD_K") != null && !StringUtils.isEmpty(map.get("KDA006_FLD_K").toString())) {
        				configUsrManualVoObj.setConfigSakeyId(Integer.parseInt(map.get("KDA006_FLD_K").toString()));
        			}
        			if (map.get("KDA005_MDUL_K") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_K").toString())) {
        				configUsrManualVoObj.setScreenName(map.get("KDA005_MDUL_K").toString());
        				
        				configUsrManualVoObj.setSubModule(getModuleSubModuleSakey(configUsrManualVoObj.getScreenName()));
        				configUsrManualVoObj.setModule(getModuleSubModuleSakey(configUsrManualVoObj.getSubModule()));
        				configUsrManualVoObj.setAppItmsNo(getApplItmsNoSakeyOrDescUsingMuduleId(configUsrManualVoObj.getModule(),"applItmsNoSakey"));
        				
        			}
        			
        			if (map.get("KDA006_FLD_C") != null && !StringUtils.isEmpty(map.get("KDA006_FLD_C").toString())) {
        				configUsrManualVoObj.setFieldCode(map.get("KDA006_FLD_C").toString());
        			}
        			if (map.get("KDA006_FLD_N") != null && !StringUtils.isEmpty(map.get("KDA006_FLD_N").toString())) {
        				configUsrManualVoObj.setFieldName(map.get("KDA006_FLD_N").toString());
        			}
        			if (map.get("KDA006_AUDO_HELP_F") != null && !StringUtils.isEmpty(map.get("KDA006_AUDO_HELP_F").toString())) {
        				if(map.get("KDA006_AUDO_HELP_F").toString().equalsIgnoreCase("Y")) {
        					configUsrManualVoObj.setAudioHelp("Yes");
            				configUsrManualVoObj.setAudioHelpCheck(true);
        				}else {
        					configUsrManualVoObj.setAudioHelp("No");
            				configUsrManualVoObj.setAudioHelpCheck(false);
        				}
        			}else {
        				configUsrManualVoObj.setAudioHelp("No");
        				configUsrManualVoObj.setAudioHelpCheck(false);
        			}
        			if (map.get("KDA006_TOOL_TIP_F") != null && !StringUtils.isEmpty(map.get("KDA006_TOOL_TIP_F").toString())) {
        				if(map.get("KDA006_TOOL_TIP_F").toString().equalsIgnoreCase("Y")) {
        					configUsrManualVoObj.setToolTipHelp("Yes");
            				configUsrManualVoObj.setToolTipHelpCheck(true);
        				}else {
        					configUsrManualVoObj.setToolTipHelp("No");
            				configUsrManualVoObj.setToolTipHelpCheck(false);
        				}
        			}else {
        				configUsrManualVoObj.setToolTipHelp("No");
        				configUsrManualVoObj.setToolTipHelpCheck(false);
        			}
        			
        			if (map.get("KDA006_HELP_TEXT_X") != null && !StringUtils.isEmpty(map.get("KDA006_HELP_TEXT_X").toString())) {
        				configUsrManualVoObj.setHelpText(map.get("KDA006_HELP_TEXT_X").toString());
        			}
        			
        			if (map.get("KDA006_CRT_S") != null && !StringUtils.isEmpty(map.get("KDA006_CRT_S").toString())) {
        				configUsrManualVoObj.setCreateDateTime(dateFormat.format((Date) map.get("KDA006_CRT_S")));
        			}
        			
        			if (map.get("KDA006_LST_UPDT_S") != null && !StringUtils.isEmpty(map.get("KDA006_LST_UPDT_S").toString())) {
        				configUsrManualVoObj.setLastUpdateDateTime(dateFormat.format((Date) map.get("KDA006_LST_UPDT_S")));
        			}
        			
        			// Add Please Select
        			configUsrManualVoObj.setApplItmsNoList(toAddPleaseSelect(getAllApplicationItmsNo()));
        			configUsrManualVoObj.setModuleList(toAddPleaseSelect(getModuleListUsingApplItmsNo(configUsrManualVoObj.getAppItmsNo())));
        			configUsrManualVoObj.setSubModuleList(toAddPleaseSelect(getModuleSubModuleList(configUsrManualVoObj.getModule())));
        			configUsrManualVoObj.setScreenNameList(toAddPleaseSelect(getModuleSubModuleList(configUsrManualVoObj.getSubModule())));
        			
        			configUsrManualVoObj.setAppItmsNoDesc(getApplItmsNoSakeyOrDescUsingMuduleId(configUsrManualVoObj.getModule(),"applItmsNoDesc"));
        			configUsrManualVoObj.setModuleDesc(getModuleDescUsingModuleId(configUsrManualVoObj.getModule()));
        			configUsrManualVoObj.setSubModuleDesc(getModuleDescUsingModuleId(configUsrManualVoObj.getSubModule()));
        			configUsrManualVoObj.setScreenNameDesc(getModuleDescUsingModuleId(configUsrManualVoObj.getScreenName()));
        			
        			configUsrManualSearchList.add(configUsrManualVoObj);
        			rowId++;
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		
		return configUsrManualSearchList;
	}
	
	public List<String> toSplitModuleNameList(List<String> valueList) {
		List<String> toSplitModuleNameList=new ArrayList<String>();
		String[] splitModuleName=null;
		
		if(valueList!=null && !valueList.isEmpty()) {
			for(String strObj:valueList) {
				splitModuleName=strObj.split("-");
				toSplitModuleNameList.add(splitModuleName[0]);
			}
		}

		return toSplitModuleNameList;
	}
	
	public String convertListToString(List<String> valueList)
	{
		StringBuilder query = new StringBuilder();
		System.out.println(valueList);
		if (valueList.size() > 1) {
			query.append(" (");
			StringBuilder strB = new StringBuilder();
			for (String val : valueList) {
				strB.append("'" + val + "',");
			}
			strB.deleteCharAt(strB.lastIndexOf(","));
			query.append(strB.toString() + ")");
		} else {
			if (valueList.size() == 0)
				query.append("");
			else{
				for (String value : valueList) {
					query.append(" ('" + value + "')");
				}

			}
		}

		System.out.println(query.toString());

		return query.toString();
	}
	
	
	public List<String> getMiduleIdListUsingModuleDesc(List<String> moduleSubModuleScreenDescList) throws SQLException {
	  	
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        List<String> getAllModuleList=new ArrayList<String>();
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" SELECT KDA005_MDUL_K,KDA005_MDUL_N,KDA005_MDUL_C FROM MKDA005_MDUL ");
        	query.append("  WHERE KDA005_MDUL_N IN "+convertListToString(moduleSubModuleScreenDescList)+" ");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
		
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
				
        			if (map.get("KDA005_MDUL_K") != null && !StringUtils.isEmpty(map.get("KDA005_MDUL_K").toString())) {
        				getAllModuleList.add(map.get("KDA005_MDUL_K").toString());
        			}
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		
		return getAllModuleList;
	}
	
	
public List<ConfigUsrManualVo> getConfigUsrManualSearchResultBySelectedInput(ConfigUsrManualVo configUsrManualVo) throws SQLException {
	  	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        List<ConfigUsrManualVo> configUsrManualSearchList=new ArrayList<ConfigUsrManualVo>();
        ConfigUsrManualVo configUsrManualVoObj=null;
        List<String> applItmsNoSearchList=new ArrayList<String>();
        List<String> moduleCodeSearchList=new ArrayList<String>();
        List<String> subModuleCodeSearchList=new ArrayList<String>();
        List<String> screenNameCodeSearchList=new ArrayList<String>();
        int rowId=1;
        boolean flag=false;
        try {
        	
        	if(configUsrManualVo.getSearchApplItmsList()!=null && !configUsrManualVo.getSearchApplItmsList().isEmpty())  {
        		applItmsNoSearchList.addAll(toSplitModuleNameList(configUsrManualVo.getSearchApplItmsList()));
        	}
        	
        	if(configUsrManualVo.getSearchModuleList()!=null && !configUsrManualVo.getSearchModuleList().isEmpty())  {
        		moduleCodeSearchList.addAll(toSplitModuleNameList(configUsrManualVo.getSearchModuleList()));
        	}
        	if(configUsrManualVo.getSearchSubModuleList()!=null && !configUsrManualVo.getSearchSubModuleList().isEmpty())  {
        		subModuleCodeSearchList.addAll(toSplitModuleNameList(configUsrManualVo.getSearchSubModuleList()));
        	}
        	if(configUsrManualVo.getSearchScreenNameList()!=null && !configUsrManualVo.getSearchScreenNameList().isEmpty())  {
        		screenNameCodeSearchList.addAll(toSplitModuleNameList(configUsrManualVo.getSearchScreenNameList()));
        	}
        	
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	
        	
        	/*query.append(" select MD.KDA005_MDUL_K as ModuleId, MD.[KDA005_MDUL_C]+'-'+MD.KDA005_MDUL_N as ModuleCode, ");
        	query.append(" SM.KDA005_MDUL_K as SubModuleId,SM.[KDA005_MDUL_C]+'-'+SM.KDA005_MDUL_N as SubModuleCode, ");
        	query.append(" SR.KDA005_MDUL_K as screenNameId,SR.[KDA005_MDUL_C]+'-'+SR.KDA005_MDUL_N as ScreenName, ");
        	query.append(" [KDA006_FLD_C],[KDA006_FLD_N],[KDA006_AUDO_HELP_F],[KDA006_TOOL_TIP_F], ");
        	query.append(" [KDA006_HELP_TEXT_X],[KDA006_CRT_S],[KDA006_LST_UPDT_S],KDA006_FLD_K from MKDA006_FLD_HELP FH inner join [dbo].[MKDA005_MDUL] SR "); 
        	query.append(" on SR.KDA005_MDUL_K=FH.KDA005_MDUL_K and SR.[KDA005_MDUL_TYP_C]='S' inner join [dbo].[MKDA005_MDUL] SM ");
        	query.append(" on SM.KDA005_MDUL_K=SR.KDA005_PARNT_MDUL_K and SM.[KDA005_MDUL_TYP_C]='SM' inner join [dbo].[MKDA005_MDUL] MD ");
        	query.append(" on MD.KDA005_MDUL_K=SM.KDA005_PARNT_MDUL_K and MD.[KDA005_MDUL_TYP_C]='M'" );*/
        	
        	query.append(" select MD.KDA005_MDUL_K as ModuleId, MD.[KDA005_MDUL_C]+'-'+MD.KDA005_MDUL_N as ModuleCode, ");
        	query.append(" SM.KDA005_MDUL_K as SubModuleId,SM.[KDA005_MDUL_C]+'-'+SM.KDA005_MDUL_N as SubModuleCode, ");
        	query.append(" SR.KDA005_MDUL_K as screenNameId,SR.[KDA005_MDUL_C]+'-'+SR.KDA005_MDUL_N as ScreenName,  ");
        	query.append(" [KDA006_FLD_C],[KDA006_FLD_N],[KDA006_AUDO_HELP_F],[KDA006_TOOL_TIP_F], ");
        	query.append(" [KDA006_HELP_TEXT_X],[KDA006_CRT_S],[KDA006_LST_UPDT_S],KDA006_FLD_K  ");
        	query.append(" from MKDA006_FLD_HELP FH  ");
        	query.append(" inner join [dbo].[MKDA005_MDUL] SR  ");
        	query.append(" on SR.KDA005_MDUL_K=FH.KDA005_MDUL_K and SR.[KDA005_MDUL_TYP_C]='SC'  ");
        	query.append(" inner join [dbo].[MKDA005_MDUL] SM  ");
        	query.append(" on SM.KDA005_MDUL_K=SR.KDA005_PARNT_MDUL_K and SM.[KDA005_MDUL_TYP_C]='SM'  ");
        	query.append(" inner join [dbo].[MKDA005_MDUL] MD  ");
        	query.append(" on MD.KDA005_MDUL_K=SM.KDA005_PARNT_MDUL_K and MD.[KDA005_MDUL_TYP_C]='MD'  ");
        	query.append(" inner join [dbo].[MKDA005_MDUL] AP  ");
        	query.append(" on AP.KDA005_MDUL_K=FH.KDA005_MDUL_K AND AP.KDA004_APPL_ITMS_R=MD.KDA004_APPL_ITMS_R  ");
        	
        	if(moduleCodeSearchList!=null && !moduleCodeSearchList.isEmpty())  {
        		query.append("  WHERE MD.[KDA005_MDUL_C] IN "+convertListToString(moduleCodeSearchList)+" ");
        		flag=true;
        	}
        	
        	if(subModuleCodeSearchList!=null && !subModuleCodeSearchList.isEmpty())  {
        		if(flag) {
        			query.append("  AND SM.[KDA005_MDUL_C] IN "+convertListToString(subModuleCodeSearchList)+" ");
        		}else {
        			query.append("  WHERE SM.[KDA005_MDUL_C] IN "+convertListToString(subModuleCodeSearchList)+" ");
        			flag=true;
        		}
        		
        	}
        	
        	if(screenNameCodeSearchList!=null && !screenNameCodeSearchList.isEmpty())  {
        		if(flag) {
        			query.append("  AND SR.[KDA005_MDUL_C] IN "+convertListToString(screenNameCodeSearchList)+" ");
        		}else {
        			query.append("  WHERE SR.[KDA005_MDUL_C] IN "+convertListToString(screenNameCodeSearchList)+" ");
        			flag=true;
        		}
        	}
        	
        	if(applItmsNoSearchList!=null && !applItmsNoSearchList.isEmpty())  {
        		if(flag) {
        			query.append("  AND AP.KDA004_APPL_ITMS_R IN "+convertListToString(applItmsNoSearchList)+" ");
        		}else {
        			query.append("  WHERE AP.KDA004_APPL_ITMS_R IN "+convertListToString(applItmsNoSearchList)+" ");
        			flag=true;
        		}
        	}
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
		
        	if(resultList!=null && !resultList.isEmpty()) {
        		for (int i = 0; i < resultList.size(); i++) {
        			configUsrManualVoObj=new ConfigUsrManualVo();
        			final HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(i);
				
        			configUsrManualVoObj.setRowId(rowId);
        			if (map.get("KDA006_FLD_K") != null && !StringUtils.isEmpty(map.get("KDA006_FLD_K").toString())) {
        				configUsrManualVoObj.setConfigSakeyId(Integer.parseInt(map.get("KDA006_FLD_K").toString()));
        			}
        			
        			if (map.get("ModuleId") != null && !StringUtils.isEmpty(map.get("ModuleId").toString())) {
        				configUsrManualVoObj.setModule(map.get("ModuleId").toString());
        				configUsrManualVoObj.setAppItmsNo(getApplItmsNoSakeyOrDescUsingMuduleId(configUsrManualVoObj.getModule(),"applItmsNoSakey"));
        				configUsrManualVoObj.setAppItmsNoDesc(getApplItmsNoSakeyOrDescUsingMuduleId(configUsrManualVoObj.getModule(),"applItmsNoDesc"));
        			}
        			
        			if (map.get("ModuleCode") != null && !StringUtils.isEmpty(map.get("ModuleCode").toString())) {
        				configUsrManualVoObj.setModuleDesc(map.get("ModuleCode").toString());
        				
        			}
        			
        			if (map.get("SubModuleId") != null && !StringUtils.isEmpty(map.get("SubModuleId").toString())) {
        				configUsrManualVoObj.setSubModule(map.get("SubModuleId").toString());
        			}
        			
        			if (map.get("SubModuleCode") != null && !StringUtils.isEmpty(map.get("SubModuleCode").toString())) {
        				configUsrManualVoObj.setSubModuleDesc(map.get("SubModuleCode").toString());
        			}
        			
        			if (map.get("screenNameId") != null && !StringUtils.isEmpty(map.get("screenNameId").toString())) {
        				configUsrManualVoObj.setScreenName(map.get("screenNameId").toString());
        			}
        			
        			if (map.get("ScreenName") != null && !StringUtils.isEmpty(map.get("ScreenName").toString())) {
        				configUsrManualVoObj.setScreenNameDesc(map.get("ScreenName").toString());
        			}
        			
        			if (map.get("KDA006_FLD_C") != null && !StringUtils.isEmpty(map.get("KDA006_FLD_C").toString())) {
        				configUsrManualVoObj.setFieldCode(map.get("KDA006_FLD_C").toString());
        			}
        			
        			if (map.get("KDA006_FLD_N") != null && !StringUtils.isEmpty(map.get("KDA006_FLD_N").toString())) {
        				configUsrManualVoObj.setFieldName(map.get("KDA006_FLD_N").toString());
        			}
        			
        			if (map.get("KDA006_AUDO_HELP_F") != null && !StringUtils.isEmpty(map.get("KDA006_AUDO_HELP_F").toString())) {
        				if(map.get("KDA006_AUDO_HELP_F").toString().equalsIgnoreCase("Y")) {
        					configUsrManualVoObj.setAudioHelp("Yes");
            				configUsrManualVoObj.setAudioHelpCheck(true);
        				}else {
        					configUsrManualVoObj.setAudioHelp("No");
            				configUsrManualVoObj.setAudioHelpCheck(false);
        				}
        			}else {
        				configUsrManualVoObj.setAudioHelp("No");
        				configUsrManualVoObj.setAudioHelpCheck(false);
        			}
        			if (map.get("KDA006_TOOL_TIP_F") != null && !StringUtils.isEmpty(map.get("KDA006_TOOL_TIP_F").toString())) {
        				if(map.get("KDA006_TOOL_TIP_F").toString().equalsIgnoreCase("Y")) {
        					configUsrManualVoObj.setToolTipHelp("Yes");
            				configUsrManualVoObj.setToolTipHelpCheck(true);
        				}else {
        					configUsrManualVoObj.setToolTipHelp("No");
            				configUsrManualVoObj.setToolTipHelpCheck(false);
        				}
        			}else {
        				configUsrManualVoObj.setToolTipHelp("No");
        				configUsrManualVoObj.setToolTipHelpCheck(false);
        			}
        			
        			if (map.get("KDA006_HELP_TEXT_X") != null && !StringUtils.isEmpty(map.get("KDA006_HELP_TEXT_X").toString())) {
        				configUsrManualVoObj.setHelpText(map.get("KDA006_HELP_TEXT_X").toString());
        			}
        			
        			if (map.get("KDA006_CRT_S") != null && !StringUtils.isEmpty(map.get("KDA006_CRT_S").toString())) {
        				configUsrManualVoObj.setCreateDateTime(dateFormat.format((Date) map.get("KDA006_CRT_S")));
        			}
        			
        			if (map.get("KDA006_LST_UPDT_S") != null && !StringUtils.isEmpty(map.get("KDA006_LST_UPDT_S").toString())) {
        				configUsrManualVoObj.setLastUpdateDateTime(dateFormat.format((Date) map.get("KDA006_LST_UPDT_S")));
        			}
        			
        			// Add Please Select
        			configUsrManualVoObj.setApplItmsNoList(toAddPleaseSelect(getAllApplicationItmsNo()));
        			configUsrManualVoObj.setModuleList(toAddPleaseSelect(getModuleListUsingApplItmsNo(configUsrManualVoObj.getAppItmsNo())));
        			configUsrManualVoObj.setSubModuleList(toAddPleaseSelect(getModuleSubModuleList(configUsrManualVoObj.getModule())));
        			configUsrManualVoObj.setScreenNameList(toAddPleaseSelect(getModuleSubModuleList(configUsrManualVoObj.getSubModule())));
        			
        			/*configUsrManualVoObj.setAppItmsNoDesc(getApplItmsNoSakeyOrDescUsingMuduleId(configUsrManualVoObj.getModule(),"applItmsNoDesc"));
        			configUsrManualVoObj.setModuleDesc(getModuleDescUsingModuleId(configUsrManualVoObj.getModule()));
        			configUsrManualVoObj.setSubModuleDesc(getModuleDescUsingModuleId(configUsrManualVoObj.getSubModule()));
        			configUsrManualVoObj.setScreenNameDesc(getModuleDescUsingModuleId(configUsrManualVoObj.getScreenName()));*/
        			
        			configUsrManualSearchList.add(configUsrManualVoObj);
        			rowId++;
        		}
        	}
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		
		return configUsrManualSearchList;
	}
	
	public List<SelectedItem> toAddPleaseSelect(List<SelectedItem> listOfValues) {
		boolean flag=false;
		List<SelectedItem> addPleaseSelectList=new ArrayList<SelectedItem>();
		if(listOfValues!=null && !listOfValues.isEmpty()) {
			for(SelectedItem si:listOfValues) {
				if(si.getValue().equalsIgnoreCase("Please Select")) {
					flag=true;
				}
			}
			if(!flag) {
				SelectedItem siObj=new SelectedItem();
				siObj.setKey(null);
				siObj.setValue("Please Select");
				addPleaseSelectList.add(siObj);
				addPleaseSelectList.addAll(listOfValues);
			}
		}
		
		return addPleaseSelectList;
	}
	
	
	public boolean validateModuleId(String moduleId,String fieldCode) throws SQLException {
		
		boolean flag=false;
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" SELECT KDA006_FLD_K,KDA005_MDUL_K FROM MKDA006_FLD_HELP WHERE KDA005_MDUL_K="+moduleId+" AND KDA006_FLD_C='"+fieldCode+"' ");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
        	flag=resultList!=null && !resultList.isEmpty()?true:false;
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		return flag;
	}
	
	public boolean validateFieldCodeUnikey(String fieldCode) throws SQLException {
		
		boolean flag=false;
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" select KDA006_FLD_K,KDA006_FLD_C from MKDA006_FLD_HELP where KDA006_FLD_C='"+fieldCode+"' ");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
        	flag=resultList!=null && !resultList.isEmpty()?true:false;
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		return flag;
	}
	
public boolean validateFieldCodeUnikeyForUpdate(String fieldCode,int fieldHelpSakey) throws SQLException {
		
		boolean flag=false;
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" select KDA006_FLD_K,KDA006_FLD_C from MKDA006_FLD_HELP where KDA006_FLD_C='"+fieldCode+"' AND KDA006_FLD_K!="+fieldHelpSakey+"");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
        	flag=resultList!=null && !resultList.isEmpty()?true:false;
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		return flag;
	}
	
	
	public boolean validateModuleIdForUpdate(String moduleId,int fieldHelpSakey,String fieldCode) throws SQLException {
		boolean flag=false;
		Connection conn=null;
        ArrayList<HashMap<String, Object>> resultList = null;
        try {
        	conn = dataSource.getConnection();
        	StringBuffer query=new StringBuffer("");
        	query.append(" SELECT KDA006_FLD_K,KDA005_MDUL_K FROM MKDA006_FLD_HELP WHERE KDA006_FLD_C='"+fieldCode+"' AND KDA005_MDUL_K="+moduleId+" AND KDA006_FLD_K!="+fieldHelpSakey+" ");
        	
        	resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
        	flag=resultList!=null && !resultList.isEmpty()?true:false;
		
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	conn.close();
        }
		return flag;
	}
	
}
