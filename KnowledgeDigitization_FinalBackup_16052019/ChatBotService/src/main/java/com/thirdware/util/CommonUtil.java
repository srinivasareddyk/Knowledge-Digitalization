package com.thirdware.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;



/**
 * @author rchand66
 *
 */
public class CommonUtil {
	/**
	 * 
	 */
	/** The Class Name used for Logging */
	private static final String CLASS_NAME = CommonUtil.class.getName();

	/** The Logger instance used for Logging */
	//private static final ILogger log = LogFactory.getInstance().getLogger(CLASS_NAME);

	public static String fileName;

	public static final String MMP_INITIAL_LOAD_STR="MMP_INITIAL_LOAD";
	public static final String UPLOADED_MATERIAL="Upload";
	
	public static String getCodeDescList(List<String> valueList)
	{
		StringBuilder query = new StringBuilder();
		System.out.println(valueList);
		if (valueList.size() > 1) {
			query.append(" (");
			StringBuilder strB = new StringBuilder();
			for (String val : valueList) {
				strB.append("'" + val.substring(0, val.indexOf("-")) + "',");
			}
			strB.deleteCharAt(strB.lastIndexOf(","));
			query.append(strB.toString() + ")");
		} else {
			if (valueList.size() == 0)
				query.append("");
			else {
				for (String value : valueList) {
					query.append(" ('" + value.substring(0, value.indexOf("-")) + "')");
				}

			}
		}

		System.out.println(query.toString());

		return query.toString();
	}
	public static void buildColumn(String value, PdfPTable table,Font f) {
		String METHOD_NAME = "buildColumn";
		//log.entering(CLASS_NAME, METHOD_NAME);
		Paragraph para =new Paragraph(value, f);
		PdfPCell cell = new PdfPCell(para);
		cell.setBorder(0);
		cell.setNoWrap(false);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
		cell=null;
		para =null;
		//log.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * An utiltity method which will convert integer parameter value to string
	 * if not empty, else returns default blank string.
	 * 
	 * @param charge
	 * @return String
	 */
	public static String convertIntegerToString(Integer charge) {

		if (charge != null) {

			return charge.toString();
		}
		return "";
	}


	/**
	 * 
	 * @param reportName
	 * @param contentType
	 * @return
	 * @throws IOException
	 */
	public static ServletOutputStream reportFileResponseDetails(String reportName,
			String contentType, String fileType) throws IOException {
				return null;/*

		String METHOD_NAME = "reportFileResponseDetails";
		//log.entering(CLASS_NAME, METHOD_NAME);

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		fileName = reportName+"_"+sdf.format(dt) + fileType;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		facesContext.responseComplete();
		response.resetBuffer();
		response.reset();
		response.setHeader("Content-Disposition", "attachment;filename=\""+ fileName + "\"");
		response.setContentType(contentType);

		log.exiting(CLASS_NAME, METHOD_NAME);
		return response.getOutputStream();

	*/}

	public static List uploadComment(InputStream fis, int lastColumn, boolean isXlsx)
			throws IOException {
				return null;/*
		List sheetData = new ArrayList();
		DataFormatter dataFormatter=new DataFormatter();
		HSSFWorkbook workbook=null;
		XSSFWorkbook XLSXworkbook=null;
		XSSFDataFormat fmt = null;
		HSSFSheet sheet;
		XSSFSheet XLSXsheet = null;
		Iterator rows;
		if (isXlsx) {
			XLSXworkbook = new XSSFWorkbook(fis);
			fmt=XLSXworkbook.createDataFormat();
			//DownloadLogUtil downloadLogUtil = new DownloadLogUtil();
			//String fileName="DomesticCreditAvailament.xlsx";
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			XLSXworkbook.write(byteArrayOutputStream);
			byteArrayOutputStream.flush();
			//downloadLogUtil.storeUploadExcelFile(XLSXworkbook,fileName);
			//XLSXworkbook.
			//CommonUtil.saveInterfaceLog(byteArrayOutputStream.toByteArray(),fileName,"DOM_CDT","S",0,0);
			XLSXsheet = XLSXworkbook.getSheetAt(0);
			rows = (Iterator)XLSXsheet.rowIterator();
			byteArrayOutputStream.close();
		} else {
			workbook = new HSSFWorkbook(fis);
			//	DownloadLogUtil downloadLogUtil = new DownloadLogUtil();
			String fileName="DomesticCreditAvailament.xls";
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			workbook.write(byteArrayOutputStream);
			//ByteArrayOutputStream byteArrayOutputStream = downloadLogUtil.storeUploadExcelFile(workbook,fileName);
			// CommonUtil.saveInterfaceLog(byteArrayOutputStream.toByteArray(),fileName,"DOM_CDT","S",0,0);
			sheet = workbook.getSheetAt(0);
			rows = (Iterator)sheet.rowIterator();
			byteArrayOutputStream.close();
		}			
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		while (rows.hasNext()) {
			HSSFRow row = null;
			XSSFRow xssfRow = null;
			if(isXlsx)
				xssfRow = (XSSFRow) rows.next();
			else
				row = (HSSFRow) rows.next();
			if(isXlsx)
			{
				final Iterator cells = (Iterator) xssfRow.cellIterator();
				final List<String> data = new ArrayList<String>();
				// Passing Last Column from Bean // final int lastColumn =
				// Math.max(12, 0);// row.getLastCellNum()
				for (int cn = 0; cn < lastColumn; cn++) {
					if(xssfRow.getCell(cn, XSSFRow.CREATE_NULL_AS_BLANK) != null)
					{
						if (xssfRow.getCell(cn, XSSFRow.CREATE_NULL_AS_BLANK).equals("")) {
							data.add("");
						} else {
							Cell cell = xssfRow.getCell(cn,XSSFRow.CREATE_NULL_AS_BLANK);
							CellStyle style=XLSXworkbook.createCellStyle();;
							if(cn!=6){
								style.setDataFormat(fmt.getFormat("@"));
								cell.setCellStyle(style);
							}
							else
							{
								CreationHelper createHelper = XLSXworkbook.getCreationHelper();
								style.setDataFormat(createHelper.createDataFormat().getFormat("dd-mm-yyyy"));
								cell.setCellStyle(style);
							}
							//CellStyle style = cell.getCellStyle();	
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
							{
								data.add(xssfRow.getCell(cn, XSSFRow.CREATE_NULL_AS_BLANK).toString());
								break;
							}
							case Cell.CELL_TYPE_NUMERIC:
							{
								if (DateUtils.isCellDateFormatted(cell)) {
									data.add(formatter.format(new Date(cell.getDateCellValue().getTime())));
								} else {
									//String value="";
									//value=dataFormatter.formatRawCellContents(cell.getNumericCellValue(), style.getDataFormat(),"####.####");
									data.add(NumberToTextConverter.toText(xssfRow.getCell(cn, XSSFRow.CREATE_NULL_AS_BLANK).getNumericCellValue()));
									//data.add(value);
								}
								break;
							}
							default:
								data.add(xssfRow.getCell(cn, XSSFRow.CREATE_NULL_AS_BLANK).toString()); 
										
							case Cell.CELL_TYPE_NUMERIC:
								data = dataFormatter.formatRawCellContents(cell.getNumericCellValue(), style.getDataFormat(),"#.##########");
								cvdCreditVO.setInvSeq(data);
								break;
							default:
								data.add(XSSFRow.getCell(cn, XSSFRow.CREATE_NULL_AS_BLANK).toString()); 
							}
						}  
					}
				}
				sheetData.add(data);
			}

			else {
				final Iterator cells = (Iterator) row.cellIterator();
				final List<String> data = new ArrayList<String>();
				// Passing Last Column from Bean // final int lastColumn =
				// Math.max(12, 0);// row.getLastCellNum()
				for (int cn = 0; cn < lastColumn; cn++) {
					if (row.getCell(cn, row.CREATE_NULL_AS_BLANK).toString()
							.equals("")) {
						data.add("");
					} else {
						Cell cell = row.getCell(cn);
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							data.add(row.getCell(cn, row.CREATE_NULL_AS_BLANK)
									.toString());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								data.add(formatter.format(new Date(cell
										.getDateCellValue().getTime())));
							} else {
								data.add(NumberToTextConverter.toText(row
										.getCell(cn, row.CREATE_NULL_AS_BLANK)
										.getNumericCellValue()));
							}
							break;
						default:
							data.add(row.getCell(cn, row.CREATE_NULL_AS_BLANK)
									.toString());
						}
					}
				}
				sheetData.add(data);
			}
		}
		return sheetData;
	*/}
	public static List uploadClaim(InputStream fis, int lastColumn, boolean isXlsx)
			throws IOException {
		List sheetData = new ArrayList();
		DataFormatter dataFormatter=new DataFormatter();
		HSSFWorkbook workbook=null;
		XSSFWorkbook XLSXworkbook=null;
		XSSFDataFormat fmt = null;
		HSSFSheet sheet;
		XSSFSheet XLSXsheet = null;
		Iterator rows;
		if (isXlsx) {
			XLSXworkbook = new XSSFWorkbook(fis);
			fmt=XLSXworkbook.createDataFormat();
			//DownloadLogUtil downloadLogUtil = new DownloadLogUtil();
			//String fileName="DomesticCreditAvailament.xlsx";
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			XLSXworkbook.write(byteArrayOutputStream);
			byteArrayOutputStream.flush();
			//downloadLogUtil.storeUploadExcelFile(XLSXworkbook,fileName);
			//XLSXworkbook.
			//CommonUtil.saveInterfaceLog(byteArrayOutputStream.toByteArray(),fileName,"DOM_CDT","S",0,0);
			XLSXsheet = XLSXworkbook.getSheetAt(0);
			rows = (Iterator)XLSXsheet.rowIterator();
			byteArrayOutputStream.close();
		} else {
			workbook = new HSSFWorkbook(fis);
			//	DownloadLogUtil downloadLogUtil = new DownloadLogUtil();
			String fileName="DomesticCreditAvailament.xls";
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			workbook.write(byteArrayOutputStream);
			//ByteArrayOutputStream byteArrayOutputStream = downloadLogUtil.storeUploadExcelFile(workbook,fileName);
			// CommonUtil.saveInterfaceLog(byteArrayOutputStream.toByteArray(),fileName,"DOM_CDT","S",0,0);
			sheet = workbook.getSheetAt(0);
			rows = (Iterator)sheet.rowIterator();
			byteArrayOutputStream.close();
		}			
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		while (rows.hasNext()) {
			HSSFRow row = null;
			XSSFRow xssfRow = null;
			if(isXlsx)
				xssfRow = (XSSFRow) rows.next();
			else
				row = (HSSFRow) rows.next();
			if(isXlsx)
			{
				final Iterator cells = (Iterator) xssfRow.cellIterator();
				final List<String> data = new ArrayList<String>();
				// Passing Last Column from Bean // final int lastColumn =
				// Math.max(12, 0);// row.getLastCellNum()
				for (int cn = 0; cn < lastColumn; cn++) {
					if(xssfRow.getCell(cn, XSSFRow.CREATE_NULL_AS_BLANK) != null)
					{
						if (xssfRow.getCell(cn, XSSFRow.CREATE_NULL_AS_BLANK).equals("")) {
							data.add("");
						} else {
							Cell cell = xssfRow.getCell(cn,XSSFRow.CREATE_NULL_AS_BLANK);
							CellStyle style=XLSXworkbook.createCellStyle();;
							style.setDataFormat(fmt.getFormat("@"));
							cell.setCellStyle(style);
							//CellStyle style = cell.getCellStyle();	
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
							{
								data.add(xssfRow.getCell(cn, XSSFRow.CREATE_NULL_AS_BLANK).toString());
								break;
							}
							case Cell.CELL_TYPE_NUMERIC:
							{
								/*if (DateUtils.isCellDateFormatted(cell)) {
									data.add(formatter.format(new Date(cell.getDateCellValue().getTime())));
								} else 
								{*/
									data.add(NumberToTextConverter.toText(xssfRow.getCell(cn, XSSFRow.CREATE_NULL_AS_BLANK).getNumericCellValue()));
									
								//}
								break;
							}
							default:
								data.add(xssfRow.getCell(cn, XSSFRow.CREATE_NULL_AS_BLANK).toString()); 
							}
						}  
					}
				}
				sheetData.add(data);
			}

			else {
				final Iterator cells = (Iterator) row.cellIterator();
				final List<String> data = new ArrayList<String>();
				// Passing Last Column from Bean // final int lastColumn =
				// Math.max(12, 0);// row.getLastCellNum()
				for (int cn = 0; cn < lastColumn; cn++) {
					if (row.getCell(cn, row.CREATE_NULL_AS_BLANK).toString()
							.equals("")) {
						data.add("");
					} else {
						Cell cell = row.getCell(cn);
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							data.add(row.getCell(cn, row.CREATE_NULL_AS_BLANK)
									.toString());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								data.add(formatter.format(new Date(cell
										.getDateCellValue().getTime())));
							} else {
								data.add(NumberToTextConverter.toText(row
										.getCell(cn, row.CREATE_NULL_AS_BLANK)
										.getNumericCellValue()));
							}
							break;
						default:
							data.add(row.getCell(cn, row.CREATE_NULL_AS_BLANK)
									.toString());
						}
					}
				}
				sheetData.add(data);
			}
		}
		return sheetData;
	}

	/**
	 * @param value
	 * @return
	 */
	public static boolean isValiedDate(String value) {
		final String regExp="(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
		final String regExp1="[0-9]{1,2}-[a-zA-Z]{3}-[0-9]{4}";
		final Pattern pattern = Pattern.compile(regExp);
		final Pattern pattern1 = Pattern.compile(regExp1);
		Matcher matcher = pattern.matcher(value);
		Matcher matcher1 = pattern1.matcher(value);
		if (matcher.matches() || matcher1.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * @param value
	 * @return boolean
	 */
	public static boolean isValidateNumeric(String value) {
		final String regExp = "[0-9]{0,3}+([.][0-9]{1,2})?";
		final Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * @param value
	 * @return boolean
	 */
	public static boolean isValiedEmail(String value) {
		final String regExp = "^(.+)@(.+)$";
		final Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}


	public static boolean isValidateWholeNumber(String value) {
		final String regExp = "[0-9]+";
		final Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * @param phoneNo
	 * @return boolean
	 */
	public static boolean validatePhoneNumber(String phoneNo) {
		boolean target = false;
		try {
			if (phoneNo.matches("\\d{10}")) {
				target =  true;
			} else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
				target = true;
			} else if (phoneNo
					.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
				target = true;
			} else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
				target =  true;
			} else {
				target = false;
			}
		} catch (Exception e) {
			return target = false;
		}
		return target;
	}

	public static HSSFRow generateHeaderForTemplateReport(String workSheetName,
			String[] headerName, HSSFWorkbook workbook, HSSFSheet worksheet) {
		String METHOD_NAME = "generateHeaderForTemplateReport";

		HSSFFont f = workbook.createFont();
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		f.setItalic(true);
		HSSFRow row = worksheet.createRow((short) 0);		

		for (int i = 0; i < headerName.length; i++) {
			HSSFCell cellA1 = row.createCell(i);
			cellA1.setCellValue(headerName[i]);
			HSSFCellStyle styleOfCell = workbook.createCellStyle();			
			styleOfCell.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			styleOfCell.setFont(f);
			styleOfCell.setFillPattern(HSSFCellStyle.BORDER_THIN);
			cellA1.setCellStyle(styleOfCell);
		}
		return row;
	}

	/**
	 * 
	 * @param workSheetName
	 * @param headerName
	 * @param workbook
	 * @param worksheet
	 * @return
	 */
	public static HSSFRow generateHeaderForExcelReport(String workSheetName,
			String[] headerName, HSSFWorkbook workbook, HSSFSheet worksheet) {

		String METHOD_NAME = "generateHeaderForExcelReport";
		//log.entering(CLASS_NAME, METHOD_NAME);

		HSSFFont f = workbook.createFont();
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		f.setItalic(true);
		HSSFRow row = worksheet.createRow((short) 0);		

		for (int i = 0; i < headerName.length; i++) {

			HSSFCell cellA1 = row.createCell(i);
			cellA1.setCellValue(headerName[i]);
			HSSFCellStyle styleOfCell = workbook.createCellStyle();			
			styleOfCell.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			styleOfCell.setFont(f);
			styleOfCell.setFillPattern(HSSFCellStyle.BORDER_THIN);
			cellA1.setCellStyle(styleOfCell);

		}


		//log.exiting(CLASS_NAME, METHOD_NAME);

		return row;
	}
	public static XSSFRow generateHeaderForExcelReportXLSX(String workSheetName,
			String[] headerName, XSSFWorkbook workbook, XSSFSheet worksheet) {

		String METHOD_NAME = "generateHeaderForExcelReport";
		//log.entering(CLASS_NAME, METHOD_NAME);

		XSSFFont f = workbook.createFont();
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		f.setItalic(true);
		XSSFRow row = worksheet.createRow((short) 0);		

		for (int i = 0; i < headerName.length; i++) {

			XSSFCell cellA1 = row.createCell(i);
			cellA1.setCellValue(headerName[i]);
			XSSFCellStyle styleOfCell = workbook.createCellStyle();			
			styleOfCell.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			styleOfCell.setFont(f);
			styleOfCell.setFillPattern(HSSFCellStyle.BORDER_THIN);
			cellA1.setCellStyle(styleOfCell);

		}


		//log.exiting(CLASS_NAME, METHOD_NAME);

		return row;
	}


	
	public static List<String> readFileContent(BufferedReader in){
		List<String> content = new ArrayList<String>();
		try {   
			String line;
			while ((line = in.readLine()) != null) {
				content.add(line);
			}
		}  catch (IOException e) {
			e.printStackTrace();
		} 
		return content;

	}

	public static boolean isNumericWithSpaces(String s) {
        String pattern = "^[0-9\\s]+";
        return s.matches(pattern);
  } 
	public static boolean containsSpecialCharacter(String item)
    {
           Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
           Matcher m = p.matcher(item);
           return m.find();
    }
	public static boolean isAlphaNumeric(String s) {
		String pattern = "^[a-zA-Z0-9]*$";
		return s.matches(pattern);
	}
 

	public static boolean isAlphaNumericWithSpaces(String s) {
		String pattern = "^[a-zA-Z0-9\\s]+";
		return s.matches(pattern);
	}
	public static boolean isNumeric(String s) {
		String pattern = "^[0-9]*$";
		return s.matches(pattern);
	} 
	
 
		public static String convertISTtoddmmyyyy(Date date){
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				String formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE)     ;
				return formatedDate;
			} 

		public static String convertISTtodateFormat(Date date){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			//String formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
			String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
			return formatedDate;
		}
		
		/**
		 * @param date
		 * @return
		 */
		public static String convertISTtoddmmyyyy1(Date date){
		       
	          DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	          String date1=null;
	          try
	          {
	                  date1=df.format(date);
	          }catch(Exception e)
	          {
	                 e.printStackTrace();
	          }
	              return date1;
	       }

		public static boolean isalphaNumeric(String s) {
	        String pattern = "^[a-zA-Z0-9]*$"; 
	        return s.matches(pattern);
	  }
		public static boolean isalpha(String s) {
	        String pattern = "^[a-zA-Z]*$"; 
	        return s.matches(pattern);
	  } 
		
		public static Map<String, Integer>  getLast30Days(int noOfDays) {
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Integer> dateMap = new HashMap<String, Integer>();
			//test addDays method
			int i=0;
			/*while(i<=50){
				Date newDate = addDays(date, i);
				System.out.println("Java Date after adding "+i+" days: "+newDate.toString());
				i+=10;
			}
			System.out.println("\n\n");*/
			//test subtractDays method
			
			i=0;
			while(i<noOfDays){
				Date newDate = subtractDays(date, i);
				dateMap.put(dateFormat.format(newDate),Integer.valueOf(0));
				i+=1;
			}
			return dateMap;

		}
		public static List<String>  getLast30DaysDate(int noOfDays) {
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			List<String> dateList = new ArrayList<>();
			//test addDays method
			int i=0;
			/*while(i<=50){
				Date newDate = addDays(date, i);
				System.out.println("Java Date after adding "+i+" days: "+newDate.toString());
				i+=10;
			}
			System.out.println("\n\n");*/
			//test subtractDays method
			
			i=0;
			while(i<noOfDays){
				Date newDate = subtractDays(date, i);
				dateList.add(dateFormat.format(newDate));
				i+=1;
			}
			return dateList;

		}
		/**
		 * add days to date in java
		 * @param date
		 * @param days
		 * @return
		 */
		public static Date addDays(Date date, int days) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.add(Calendar.DATE, days);
					
			return cal.getTime();
		}
		
		/**
		 * subtract days to date in java
		 * @param date
		 * @param days
		 * @return
		 */
		public static Date subtractDays(Date date, int days) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.add(Calendar.DATE, -days);
					
			return cal.getTime();
		}

		public static boolean isNumericWithUnderScore(String s) {
			String pattern = "^[0-9_]*$";
			return s.matches(pattern);
		} 
		
		
		public static String convertISTtoHHMMSS(Date date){
		       
	          DateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");
	          String date1=null;
	          try
	          {
	                  date1=df.format(date);
	          }catch(Exception e)
	          {
	                 e.printStackTrace();
	          }
	              return date1;
	       }
		public static String convertISTtohhMM(Date date){
			SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
	        String time = localDateFormat.format(date);
			
			return time;
		} 
}


