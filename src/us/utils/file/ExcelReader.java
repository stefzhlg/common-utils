package us.utils.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 操作Excel表格的功能类
 */
public class ExcelReader {

	public static void main(String[] args) {
		try {
			/*
			 * // 对读取Excel表格标题测试 InputStream is = new
			 * FileInputStream("E:/test/document/GM2012092.xls"); ExcelReader
			 * excelReader = new ExcelReader(); String[] title =
			 * excelReader.readExcelTitle(is);
			 * //System.out.println("获得Excel表格的标题:"); for (String s : title) {
			 * System.out.print(s + " "); } System.out.println("");
			 * System.out.println
			 * ("------------------------------------------------------------------"
			 * ); System.out.println("");
			 * 
			 * // 对读取Excel表格内容测试 InputStream is2 = new
			 * FileInputStream("E:/test/document/GM2012092.xls"); Map<Integer,
			 * String> map = excelReader.readExcelContent(is2);
			 * //System.out.println("获得Excel表格的内容:"); for (int i = 1; i <=
			 * map.size(); i++) { System.out.println(map.get(i)); }
			 */
			// kaoqin-yuanshi.xlsx
			// 对读取Excel表格内容测试
			InputStream is3 = new FileInputStream("E:/test/document/a.xls");
			ExcelReader excelReader = new ExcelReader();
			List<String> errlst = new ArrayList<String>();
			List<List<String>> lst;
			lst = excelReader.readXls(is3, errlst);
			int i = 0;
			for (List<String> tmpLst : lst) {
				System.out.println("     " + (++i));
				for (String tmpVal : tmpLst) {
					System.out.print(tmpVal);
					tmpVal = StringUtils.trim(tmpVal);
					@SuppressWarnings("unused")
					int tmpLen = StringUtils.length(tmpVal);
					/*
					 * if(tmpLen<1){ System.out.print("空"); } for(int i = 1; i
					 * <= 6-tmpLen; i++){ System.out.print("   "); }
					 */
					System.out.print("	");
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 读取xls文件
	 * 
	 * @throws IOException
	 * 
	 * @throws IOException
	 */
	public List<List<String>> readExcelContentLst(InputStream is, List<String> errorUserlst, String extension) {
		try {
			// 读取xlsx文件
			if (".xlsx".equals(StringUtils.trim(extension))) {
				return readXlsx(is, errorUserlst);

			} else {
				return readXls(is, errorUserlst);
			}
		} catch (IOException e) {
			errorUserlst.add("excel 文件读取出错了！请检查文件");
			List<List<String>> retLst = new ArrayList<List<String>>();
			retLst.add(new ArrayList<String>());
			return retLst;
		}

	}

	/**
	 * 读取xls文件
	 * 
	 * @throws IOException
	 * 
	 * @throws IOException
	 */
	public List<List<String>> readXls(InputStream is, List<String> errorUserlst) throws IOException {

		List<List<String>> retLst = new ArrayList<List<String>>();
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}

			// 循环行Row
			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				List<String> rowLst = new ArrayList<String>();
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					rowLst.add("");
					continue;
				}

				// 循环列Cell
				for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
					HSSFCell hssfCell = hssfRow.getCell(cellNum);
					if (hssfCell == null) {
						rowLst.add("");
						continue;
					}
					rowLst.add(getValue(hssfCell));
					// System.out.print("    " + getValue(hssfCell));
				}
				// System.out.println();
				retLst.add(rowLst);
			}
		}
		return retLst;
	}

	@SuppressWarnings("static-access")
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	/**
	 * 读取xlsx文件
	 * 
	 * @throws IOException
	 */
	public List<List<String>> readXlsx(InputStream is, List<String> errorUserlst) throws IOException {
		List<List<String>> retLst = new ArrayList<List<String>>();
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}

			// 循环行Row
			for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow == null) {
					continue;
				}
				List<String> rowLst = new ArrayList<String>();

				// 循环列Cell
				for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum(); cellNum++) {
					XSSFCell xssfCell = xssfRow.getCell(cellNum);
					if (xssfCell == null) {
						rowLst.add("");
						continue;
					}
					rowLst.add(getValue(xssfCell));
					// System.out.print("   " + getValue(xssfCell));
				}
				retLst.add(rowLst);
				// System.out.println();
			}
		}
		return retLst;
	}

	@SuppressWarnings("static-access")
	private String getValue(XSSFCell xssfCell) {
		if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfCell.getNumericCellValue());
		} else {
			return String.valueOf(xssfCell.getStringCellValue());
		}
	}

}