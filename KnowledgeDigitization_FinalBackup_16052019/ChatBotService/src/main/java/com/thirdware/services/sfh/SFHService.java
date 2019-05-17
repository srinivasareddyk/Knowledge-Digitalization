package com.thirdware.services.sfh;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thirdware.entity.MKDA008_MDUL_HELP;
import com.thirdware.repositories.sfh.MDAT004_MDULDAO;
import com.thirdware.repositories.sfh.MKDA003_MDUL_HELPDAO;
import com.thirdware.repositories.sfh.SfhCustomQry;
import com.thirdware.util.ExcelUtils;
import com.thirdware.vo.SfhVO;
import com.thirdware.vo.sfh.KeyValueHolder;

@Service
public class SFHService {
private static final Logger logger = LoggerFactory.getLogger(SFHService.class);

	private static final String[] DOWNLOAD_TEMPLATE_HEADERS = { "ITMS", "Module", "SubModule", "Screen Name",
			/* "Status", */"Help Document Required", "Help Document File Name",
		"Help Document Location","Help Videos Required", "Help Videos File Name", "Help Videos Location" };
private  final String SEPERATOR="#~#";
private static final String SHEET_NAME="Screen Functionality Help";
	
	@Autowired
	MKDA003_MDUL_HELPDAO maduleHelpDao;

	@Autowired
	MDAT004_MDULDAO appDetailDao;
	
	@Autowired
	SfhCustomQry customQuery;
	HashSet<String> entrydupUpldSet = new HashSet<String>();
	 public ArrayList<SfhVO> getApplicationManualDetails() {
		 
		return customQuery.getApplicationManualDetails();
		 
	 }
	 
	 public void deleteScreens(String screenId) {
		 customQuery.deleteScreens(screenId);
	 }
	
	  public byte[] getFileData(String screenId) {
	  
	  return customQuery.getFileData(screenId); }
	 
	 

	public List<String> getAllItmsNumbers() {
		logger.info("getAllItmsNumbers");
		return appDetailDao.findAllItmsNumbers();

	}
	public List<Integer> findAllItmsNumbersForGrid() {
		logger.info("getAllItmsNumbers");
		return appDetailDao.findAllItmsNumbersForGrid();

	}
	public List<String> findAllSubModules(List<String> params) {
		logger.info("findAllSubModules");
		
		return getDelimitedValue(appDetailDao.findAllSubModules());

	}

	public List<String> findAllScreens(List<String> params) {
		logger.info("findAllScreens");
	
		return getDelimitedValue(appDetailDao.findAllScreens());
	}

	public List<String> findAllModules() {
		logger.info("findAllModules");
		return getDelimitedValue(appDetailDao.findAllModules());

	}

	public List<KeyValueHolder> findAllModules1() {
		logger.info("findAllModules1");
		return getDelimitedValue1(appDetailDao.findAllModules1());
	}
	
	public List<KeyValueHolder> findAllSubModules1() {
		logger.info("findAllSubModules1");
		
		return getDelimitedValue1(appDetailDao.findAllSubModules1());

	}

	public List<KeyValueHolder> findAllScreens1() {
		logger.info("findAllScreens1");
		
		return getDelimitedValue1(appDetailDao.findAllScreens1());
	}
	
	
	
	/*
	 * public List<KeyValueHolder> findAllItmsNumbersByAcroniyum() {
	 * logger.info("findAllScreens1"); List<Object[]>
	 * appByItmsAcro=appDetailDao.findAllItmsNumbersByAcroniyum();
	 * ListIterator<Object[]> appByItmsAcroItr= appByItmsAcro.listIterator();
	 * HashMap<String,String> appDetails=new HashMap<String,String>(); Object[]
	 * rec=null; if(appByItmsAcro!=null&&appByItmsAcro.size()>0) {
	 * while(appByItmsAcroItr.hasNext()) { rec=appByItmsAcroItr.next();
	 * appDetails.put(rec[0].toString(), rec[1].toString()); }
	 * 
	 * }
	 * 
	 * 
	 * 
	 * return getDelimitedValue1(appDetailDao.findAllScreens1()); }
	 */
	
	public List<SfhVO> findAllDetails(List<String> itms,List<String> module,List<String> subModule,List<String> screen) {
		logger.info("findAllDetails");
		Iterable<MKDA008_MDUL_HELP> sfhResultList=null;
		List<Long> screens=null;
		
		if(itms.size()>0&&module.size()>0&&subModule.size()>0&&screen.size()>0) {
			  screens=appDetailDao.findAllApplicationDetailsByItmsModuleSubModuleScreen(
			  itms.stream() .map(s -> Integer.parseInt(s.split("-")[0]))
			  .collect(Collectors.toList()),getIds(module),getIds(subModule),getIds(screen)
			  );
			 
		}else if(itms.size()>0&&module.size()>0&&subModule.size()>0) {
			screens=appDetailDao.findAllApplicationDetailsByItmsModuleSubModule(itms.stream()
					.map(s -> Integer.parseInt(s.split("-")[0]))
					.collect(Collectors.toList()),getIds(module),getIds(subModule));
		}else if(itms.size()>0&&module.size()>0) {
			screens=appDetailDao.findAllApplicationDetailsByItmsModule(itms.stream()
					.map(s -> Integer.parseInt(s.split("-")[0]))
					.collect(Collectors.toList()),getIds(module));
		}else if(itms.size()>0) {
			screens=appDetailDao.findAllApplicationDetailsByItms(itms.stream()
					.map(s -> Integer.parseInt(s.split("-")[0]))
					.collect(Collectors.toList()));
		}else {
			sfhResultList = maduleHelpDao.findAll();
		}
		
		
		if (null!=screens&&screens.size() > 0)
			sfhResultList = maduleHelpDao.findDetailsByID(screens);
			
		List<SfhVO> sfhList = new ArrayList<SfhVO>();
		HashMap<Integer, String> appDetails = getApplicationDetails();
		String app = null;
		String details[] = null;
		String temp[] = null;
		SfhVO detailObject =null;
		
		try {
		if (sfhResultList != null) {
			for (MKDA008_MDUL_HELP sfhBo : sfhResultList) {
				 detailObject = new SfhVO();
				if (appDetails.containsKey(sfhBo.getModule().intValue())) {
					app = appDetails.get(sfhBo.getModule().intValue());
					details = app.split(SEPERATOR);
					detailObject.setItms(details[3]);
					detailObject.setModule(details[0]);
					detailObject.setSubModule(details[1]);
					detailObject.setSelectedScreen(details[2]);
				}
				detailObject.setIsReqHelpDoc(sfhBo.getHelpDocFileName().equals("Y") ? true : false);
				if (null != sfhBo.getHelpDocLoc()) {
					temp = sfhBo.getHelpDocLoc().split(SEPERATOR);
					detailObject.setHelpDocLoc(temp.length > 0 ? temp[0] : "");
					detailObject.setHelpDocFileName(temp.length > 1 ? temp[1] : "");
				}
				detailObject.setIsReqHelpVideo(sfhBo.getHelpVideoFileName().equals("Y") ? true : false);
				if (null != sfhBo.getHelpVideoLoc()) {
					temp = sfhBo.getHelpVideoLoc().split(SEPERATOR);
					detailObject.setHelpVideoLoc(temp.length > 0 ? temp[0] : "");
					detailObject.setHelpVideoFileName(temp.length > 1 ? temp[1] : "");
				}
				detailObject.setIsReqAltDoc(sfhBo.getAltDocFileName().equals("Y") ? true : false);
				detailObject.setCreatedBy(sfhBo.getCreatedUser());
				detailObject.setCreatedTime(sfhBo.getCreatedTime().toString());
				detailObject.setUpdatedBy(sfhBo.getUpdatedUser());
				detailObject.setUpdatedTime(sfhBo.getUpdatedTime().toString());
					/* detailObject.setStatusSearch(sfhBo.getStatus()); */
				sfhList.add(detailObject);
				detailObject=null;

			}
		}}finally {
			appDetails =null;
			 app = null;
			details = null;
			temp = null;
			detailObject =null;
			sfhResultList=null;
		}
		return sfhList;

	}
	
	
	public void saveSfh(SfhVO data) {
		logger.info("saveSfh");
		MKDA008_MDUL_HELP persistObject = new MKDA008_MDUL_HELP();
		try
		{
		persistObject.setModule(Long.parseLong(data.getSelectedScreen().split("-")[0]));
		persistObject.setHelpDocFileName(data.getHelpDocRequired());
		persistObject.setHelpDocLoc(data.getHelpDocLoc() + SEPERATOR + data.getHelpDocFileName());
		persistObject.setHelpVideoFileName(data.getHelpVideoRequired());
		persistObject.setHelpVideoLoc(data.getHelpVideoLoc() +SEPERATOR + data.getHelpVideoFileName());
		persistObject.setAltDocFileName(data.getAltDocRequired());
		persistObject.setAltDoc(data.getAltDocument());
		persistObject.setCreatedProcess("Save");
		persistObject.setCreatedUser(data.getCreatedBy());
		persistObject.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		persistObject.setUpdatedProcess("Save");
		persistObject.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		persistObject.setUpdatedUser(data.getUpdatedBy());
			/* persistObject.setStatus(data.getStatusSearch()); */
		maduleHelpDao.save(persistObject);
		}finally {
			persistObject=null;	
		}
	}
	
	private HashMap<String,String> getAllApplicationDetailsForValidate(){
		logger.info("getAllApplicationDetailsForValidate");
		HashMap<String,String> appDetails=new HashMap<String,String>();
		Object values[]=null;
		List<Object[]> allAppDetails=appDetailDao.findAllApplicationDetailsForValidate();
		ListIterator<Object[]> it = allAppDetails.listIterator();
    try {
		while (it.hasNext()) {
			values = it.next();
			appDetails.put(values[1].toString(), values[0].toString());
			values=null;
		}
    }finally {
    	allAppDetails=null;
    	it=null;
    }
		return appDetails;
	}

	public File fileUpload(MultipartFile file,String user) throws Exception {
		logger.info("fileUpload");
		List<ArrayList<Object>> sheetData = new ArrayList<ArrayList<Object>>();
		entrydupUpldSet = new HashSet<>();
		HashMap<String,String> allAppDetails=getAllApplicationDetailsForValidate();
		List<Long> allScreens=maduleHelpDao. getAllScreenIds();
		StringBuilder keyBuilder =new StringBuilder();
		MKDA008_MDUL_HELP  persistentObject=null;
		List<String> duplicateFinder=new ArrayList<String>();
		List<SfhVO> failedRecordList = new ArrayList<SfhVO>();
		List<SfhVO> insertRecordList = new ArrayList<SfhVO>();
		List<SfhVO> updationRecordList = new ArrayList<SfhVO>();
		List<MKDA008_MDUL_HELP> insert = new ArrayList<MKDA008_MDUL_HELP>();
		List<MKDA008_MDUL_HELP> update = new ArrayList<MKDA008_MDUL_HELP>();
		InputStream fis = null;
		File finalFile = null;
		try {
			fis = file.getInputStream();
			boolean isXlsx = file.getOriginalFilename().contains(".xlsx");
			sheetData = uploadedFile(fis, 11, isXlsx, file.getOriginalFilename(), "SFH Maitenance");
			String[] headerNames = { "ITMS", "Module", "SubModule", "Screen Name",
					/* "Status", */ "Help Document Required","Help Document File Name",
					"Help Document Location","Help Videos Required","Help Videos File Name", "Help Videos Location" };
			for (int i = 1; i < sheetData.size(); i++) {
				List<Object> list = sheetData.get(i);
				SfhVO voObject=getVO(list);
				if(voObject.getErrorMsg().length()<=0) {
				keyBuilder =new StringBuilder();
				keyBuilder.append(voObject.getItms().split("-")[0]);
				keyBuilder.append(SEPERATOR);
				keyBuilder.append(voObject.getModule().split("-")[1]);
				keyBuilder.append(SEPERATOR);
				keyBuilder.append(voObject.getSubModule().split("-")[1]);
				keyBuilder.append(SEPERATOR);
				keyBuilder.append(voObject.getSelectedScreen().split("-")[1]);
				if(!duplicateFinder.contains(keyBuilder.toString()))
				{
				duplicateFinder.add(keyBuilder.toString());
				if(allAppDetails.containsKey(keyBuilder.toString()))
				{
					persistentObject=new MKDA008_MDUL_HELP();
					persistentObject.setModule(Long.parseLong(allAppDetails.get(keyBuilder.toString())));
							/* persistentObject.setStatus(voObject.getStatusSearch()); */
					persistentObject.setHelpDocFileName(null==voObject.getHelpDocRequired()||voObject.getHelpDocRequired().equals("")?"N":voObject.getHelpDocRequired());
					persistentObject.setHelpDocLoc(voObject.getHelpDocLoc()+SEPERATOR+voObject.getHelpDocFileName());
					persistentObject.setHelpVideoFileName(null==voObject.getHelpVideoRequired()||voObject.getHelpVideoRequired().equals("")?"N":voObject.getHelpVideoRequired());
					persistentObject.setHelpVideoLoc(voObject.getHelpVideoLoc()+SEPERATOR+voObject.getHelpVideoFileName());
					persistentObject.setAltDocFileName("N");
					
					persistentObject.setUpdatedProcess("TmplUpld");
					persistentObject.setUpdatedUser(user);
					persistentObject.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
					if(allScreens.contains(Long.parseLong(allAppDetails.get(keyBuilder.toString())))){
						
						updationRecordList.add(voObject);
						update.add(persistentObject);
					}else {
						persistentObject.setCreatedProcess("TmplUpld");
						persistentObject.setCreatedUser(user);
						persistentObject.setCreatedTime(new Timestamp(System.currentTimeMillis()));
						insertRecordList.add(voObject);
						insert.add(persistentObject);
					}
					persistentObject=null;
				}else {
					voObject.setErrorMsg("Invalid ITMS/Module/Sub Module/Screen");
					failedRecordList.add(voObject);
				}
				}else {
					voObject.setErrorMsg("Duplicate Record");
					failedRecordList.add(voObject);
				}
				
				}else {
					failedRecordList.add(voObject);
				}
				keyBuilder=null;

			}
			maduleHelpDao.saveAll(insert);
			ListIterator<MKDA008_MDUL_HELP> updateIt=update.listIterator();
			while(updateIt.hasNext()) {
				maduleHelpDao.update(updateIt.next());
			}
			updateIt=null;
			finalFile= logExcelGeneration(user, headerNames, sheetData.size(), failedRecordList , insertRecordList , updationRecordList );
			return finalFile;
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			
			sheetData = null;
		   allAppDetails=null;
			 allScreens=null;
			 keyBuilder =null;
			  persistentObject=null;
		     duplicateFinder=null;
			 failedRecordList = null;
			 insertRecordList = null;
			 updationRecordList = null;
			 insert = null;
			 fis = null;
			
		}
		return finalFile;

	}
	public List<ArrayList<Object>> uploadedFile(InputStream fis, int lastColumn, boolean isXlsx, String fileN,
			String param) throws IOException {
		logger.info("uploadedFile");
		List<ArrayList<Object>> rowData = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> dataCell = null;
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		Iterator<Row> rowIterator = mySheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			dataCell = new ArrayList<Object>();
			for (int cellCounter = 0; cellCounter < lastColumn; cellCounter++) {
				Cell cell = row.getCell(cellCounter);
				if (cell != null) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
						dataCell.add(null);
						break;
					case Cell.CELL_TYPE_STRING:
						dataCell.add(
								!cell.getStringCellValue().trim().equals("") ? cell.getStringCellValue().trim() : null);
						break;
					case Cell.CELL_TYPE_NUMERIC:
						dataCell.add(cell.getNumericCellValue());
						break;

					}
				} else {
					dataCell.add(null);
				}

			} 

			rowData.add(dataCell);
		} 

		return rowData;
	}

	private File logExcelGeneration(String user, String[] headerNames, int totalRecords,
			 List<SfhVO> failedRecordList,
			List<SfhVO> insertRecordList, List<SfhVO> updationRecordList) throws Exception {
		logger.info("logExcelGeneration");
		File logFile = new File("SFH.xlsx");
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			final XSSFSheet worksheet1 = workbook.createSheet("Summary");
			createRow(worksheet1,0,"Report Name","SFH Maintenance Log");
			DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
			String currentDate = dateTimeformat.format(new Date(System.currentTimeMillis()));
			createRow(worksheet1,1,"Report Generated Date",currentDate);
			createRow(worksheet1,2,"Generated By",user);
			createRow(worksheet1,3,"Total No. Of Records :",String.valueOf(totalRecords - 1));
			createRow(worksheet1,4,"Number of new records",String.valueOf(insertRecordList.size()));
			createRow(worksheet1,5,"Number of updated records",String.valueOf(updationRecordList.size()));
			createRow(worksheet1,6,"Number of  failed records",String.valueOf(failedRecordList.size()));
			XSSFSheet sheet = null;
			sheet = workbook.createSheet("Error Log");
			sheet = setDefaultTextCoulmns(sheet, workbook, 0, 1);
			CellStyle editableCellStyle = getEditableCellStyle(workbook);
			XSSFRow row0 = sheet.createRow(0);
			for (int i = 0; i < headerNames.length; i++) {
				createCellWithStyle(row0,editableCellStyle,i,headerNames[i]);
			}
			createCellWithStyle(row0,editableCellStyle,10,"Remarks");
			XSSFRow rows = null;
			int row = 1;
			  for(SfhVO vo : failedRecordList ) {
			  rows = sheet.createRow(row);
			  row++;
			  createCell(rows,0,String.valueOf(vo.getItms()));
			  createCell(rows,1,vo.getModule());
			  createCell(rows,2,vo.getSubModule());
			  createCell(rows,3,vo.getSelectedScreen());
				/* createCell(rows,4,vo.getStatusSearch()); */
			  createCell(rows,4,vo.getHelpDocRequired());
			  createCell(rows,5,vo.getHelpDocFileName());
			  createCell(rows,6,vo.getHelpDocLoc());
			  createCell(rows,7,vo.getHelpVideoRequired());
			  createCell(rows,8,vo.getHelpVideoFileName());
			  createCell(rows,9,vo.getHelpVideoLoc());
			  createCell(rows,10,vo.getErrorMsg());
			  }
			for (int columnPosition = 0; columnPosition < headerNames.length + 1; columnPosition++) {
				sheet.autoSizeColumn(columnPosition);
			}
			FileOutputStream out = new FileOutputStream(logFile);
			workbook.write(out);
			return logFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	private List<String> getDelimitedValue(List<Object[]> list) {
		logger.info("getDelimitedValue");
		List<String> obj = new ArrayList<String>();
		ListIterator<Object[]> it = list.listIterator();

		while (it.hasNext()) {
			Object values[] = it.next();
			obj.add(values[0] + "-" + values[1]);

		}

		return obj;

	}
	private List<KeyValueHolder> getDelimitedValue1(List<Object[]> list) {
		logger.info("getDelimitedValue1");
		List<KeyValueHolder> obj = new ArrayList<KeyValueHolder>();
		ListIterator<Object[]> it = list.listIterator();

		while (it.hasNext()) {
			Object values[] = it.next();
			obj.add(new KeyValueHolder(Integer.parseInt(values[0].toString()), Integer.parseInt(values[1].toString()),
					(values[1].toString() + "-" + values[2].toString())));

		}

		return obj;

	}

	private List<Long> getIds(List<String> list) {
		logger.info("getIds");
		List<Long> temp = new ArrayList<Long>();
		ListIterator<String> iterator = list.listIterator();
		while (iterator.hasNext()) {
			temp.add(Long.parseLong(iterator.next().split("-")[0].toString()));
		}
		return temp;

	}


	public HashMap<Integer, String> getApplicationDetails() {
		logger.info("getApplicationDetails");
		List<Object[]> appDetails = appDetailDao.findAllApplicationDetails();
		HashMap<Integer, String> appList = new HashMap<Integer, String>();
		ListIterator<Object[]> it = appDetails.listIterator();

		while (it.hasNext()) {
			Object[] value = it.next();
			appList.put(Integer.parseInt(value[0].toString()), value[1].toString());
		}
		return appList;

	}
	private SfhVO getVO(List<Object> list) {
		logger.info("getVO");
		SfhVO vo=new SfhVO();
		StringBuilder errorMsg=new StringBuilder();
		vo.setItms(null!=list.get(0)?list.get(0).toString():"");
		if(null==vo.getItms()||vo.getItms().equals(""))
			errorMsg.append("ITMS");		
		vo.setModule(getValue(list.get(1)));
		if(vo.getModule().equals(""))
			errorMsg.append(",Module");	
		vo.setSubModule(getValue(list.get(2)));
		if(vo.getSubModule().equals(""))
			errorMsg.append(",SubModule");
		vo.setSelectedScreen(getValue(list.get(3)));
		if(vo.getSelectedScreen().equals(""))
			errorMsg.append(",Screen");
		/*
		 * vo.setStatusSearch(getValue(list.get(4)));
		 * if(vo.getStatusSearch().equals("")) errorMsg.append(",Status");
		 */
		vo.setHelpDocRequired(getValue(list.get(4)));
		vo.setHelpDocFileName(getValue(list.get(5)));
		vo.setHelpDocLoc(getValue(list.get(6)));
		if(vo.getHelpDocRequired().equalsIgnoreCase("Y")&&vo.getHelpDocFileName().equals(""))
			errorMsg.append(",Help Doc File Name");
		if(vo.getHelpDocRequired().equalsIgnoreCase("Y")&&vo.getHelpDocLoc().equals(""))
			errorMsg.append(",Help Doc location");
		vo.setHelpVideoRequired(getValue(list.get(7)));
		vo.setHelpVideoFileName(getValue(list.get(8)));
		vo.setHelpVideoLoc(getValue(list.get(9)));
		if(vo.getHelpVideoRequired().equalsIgnoreCase("Y")&&vo.getHelpVideoFileName().equals(""))
			errorMsg.append(",Help Video File Name");
		if(vo.getHelpVideoRequired().equalsIgnoreCase("Y")&&vo.getHelpVideoLoc().equals(""))
			errorMsg.append(",Help Video Location");
		if(errorMsg.length()>2)
			errorMsg.append(" Required");
		vo.setErrorMsg(errorMsg.toString());
		return vo;
		
	}
	
	private String getValue(Object value) {
		
		return value!=null?value.toString():"";
	}
	
    private String getIntValue(Object value) {
		return value!=null?value.toString():"0";
	}

	private void createRow(XSSFSheet workSheet,int row,String col1,String col2) {
		final XSSFRow rowReference = workSheet.createRow((short) row);
		createCell(rowReference,0,col1);
		createCell(rowReference,1,col2);
		workSheet.autoSizeColumn(0);
		
	}
	
	private void createCell(XSSFRow row,int pos,String value) {
		
		  XSSFCell  cell = row.createCell(pos);
		  cell.setCellValue(value!=null?value  :"");
		
	}
	private void createCellWithStyle(XSSFRow row,CellStyle cellStyle,int pos,String value) {
		  XSSFCell cell = row.createCell(pos);
		  cell.setCellStyle(cellStyle);
		  cell.setCellValue(value!=null?value  :"");
		
	}
	private CellStyle getEditableCellStyle (XSSFWorkbook workbook) {
		CellStyle editableCellStyle = workbook.createCellStyle();
		editableCellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		editableCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		editableCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		editableCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		editableCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		editableCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		editableCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		return editableCellStyle;
	}
	private XSSFSheet setDefaultTextCoulmns(XSSFSheet sheet, XSSFWorkbook workbook, int startCo, int endCol) {
		DataFormat fmt = workbook.createDataFormat();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(fmt.getFormat("@"));
		for (int i = startCo; i <= endCol; i++) {
			sheet.setDefaultColumnStyle(i, cellStyle);
		}
		return sheet;
	}
	
	/*
	 * private boolean isInteger(String input) { try { Integer.parseInt(input);
	 * return true; } catch (Exception e) { return false; } }
	 */

	public File dowloadTemplate() {
		logger.info("dowloadTemplate");
		return ExcelUtils.downloadExcelTemplate(SHEET_NAME, DOWNLOAD_TEMPLATE_HEADERS, 10);
	}
	public File dowloadExcel(List<SfhVO> data) {
		logger.info("dowloadExcel");
		return ExcelUtils.downloadSFHExportExcell(SHEET_NAME, DOWNLOAD_TEMPLATE_HEADERS, 9, data);
	}
	
	

}
