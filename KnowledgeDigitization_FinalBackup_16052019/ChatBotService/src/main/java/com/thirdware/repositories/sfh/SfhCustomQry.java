package com.thirdware.repositories.sfh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.thirdware.entity.MKDA008_MDUL_HELP;
import com.thirdware.vo.SfhVO;


@Repository
public class SfhCustomQry {

	@PersistenceContext
    private EntityManager em;
	@Autowired
	DataSource dataSource;
	
	Connection conn=null;
	 public ArrayList<SfhVO> getApplicationManualDetails() {
		 ArrayList<SfhVO> appHelpManualDetails=  new ArrayList<SfhVO>();
		 Connection con=null;
		 SfhVO  dbObject=null;
		 try {
			con=dataSource.getConnection();
			StringBuffer query=new StringBuffer(" SELECT T2.KDA005_MDUL_K,T2.KDA005_MDUL_N,KDA008_HELP_DOC_F,KDA008_HELP_DOC_LOC_X,KDA008_HELP_VIDEO_F,KDA008_HELP_VIDEO_LOC_X,KDA008_ALT_HELP_DOC_F FROM KnowlDigital.dbo.MKDA008_MDUL_HELP T1 join KnowlDigital.dbo.MKDA005_MDUL T2 on T1.KDA008_MDUL_K=T2.KDA005_MDUL_K ");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(query.toString());
			while(rs.next()) {
			 dbObject=new SfhVO();
             dbObject.setModule(rs.getString(1));
             dbObject.setSelectedScreen(rs.getString(2));
             dbObject.setHelpDocRequired(null!=rs.getString(3)&&rs.getString(3).equalsIgnoreCase("Y")?"true":"false");
             dbObject.setHelpDocLoc(dbObject.getHelpDocRequired().equalsIgnoreCase("true")?rs.getString(4).replaceAll("#~#",""):"");
             dbObject.setHelpVideoRequired(null!=rs.getString(5)&&rs.getString(5).equalsIgnoreCase("Y")?"true":"false");
             dbObject.setHelpVideoLoc(dbObject.getHelpVideoRequired().equalsIgnoreCase("true")?rs.getString(6).replaceAll("#~#",""):"");
             dbObject.setAltDocRequired(null!=rs.getString(7)&&rs.getString(7).equalsIgnoreCase("Y")?"true":"false");
             appHelpManualDetails.add(dbObject);
             dbObject=null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbObject=null;
			try {
				if(!con.isClosed()) {
				con.close();
				}
				con=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			return appHelpManualDetails;
			
		}
	 
	  
	  public byte[] getFileData(String screenId) {
			StringBuffer query = new StringBuffer(" FROM MKDA008_MDUL_HELP e  where e.module="+screenId);
			Query qry = em.createQuery(query.toString());
			List<MKDA008_MDUL_HELP> result = qry.getResultList();
			return result.get(0).getAltDoc();

		}
	  
	  public void deleteScreens(String screenId) {
			 Connection con=null;
			 StringBuffer query=null;
			 Statement st=null;
			 if(screenId.endsWith(",")) {
				  screenId=screenId.substring(0,screenId.length()-1);
			  }
			 try {
				con=dataSource.getConnection();
				query=new StringBuffer("DELETE FROM MKDA008_MDUL_HELP WHERE KDA008_MDUL_K in(");
				query.append(screenId);
				query.append(")");
				st=con.createStatement();
				st.execute(query.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(!con.isClosed()) {
					con.close();
					}
					con=null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				 con=null;
				 query=null;
				 st=null;
				
			}
				
			}
}
