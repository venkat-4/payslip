/**
 * This class is used to get the product information.
 * 
 * @author 764432
 *
 */
package com.cts.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.cts.model.Product;

@Component
public class RWExcelProduct {

	private int rownum;
	private int cellnum;
	File file;
	String filePath = "./src/main/resources/excel/product.xlsx";

	public RWExcelProduct() {
		rownum = 0;
		cellnum = 0;
		file = new File("./src/main/resources/excel/product.xlsx");
	}

	/**
	 * This method is used for adding an item into excel.
	 * 
	 * @param pro
	 * @return
	 */
	public String addItemInExcel(Product pro) {
		File file = new File(filePath);
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		int rownum = 0;
		int cellnum = 0;
		if (file.exists() == false) {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("product");
		} else {
			try (InputStream is = new FileInputStream(file)) {
				workbook = new XSSFWorkbook(is);
				sheet = workbook.getSheetAt(0);
				rownum = sheet.getLastRowNum() + 1;
			} catch (FileNotFoundException e) {
				System.err.println("File not found");
			} catch (IOException e) {
				System.err.println("Input/Output exception happened");
			}
		}

		Row row = sheet.createRow(rownum++);
		Cell cell = row.createCell(cellnum++);
		cell.setCellValue(pro.getProdId());
		Cell cell2 = row.createCell(cellnum++);
		cell2.setCellValue(pro.getProdName());
		Cell cell3 = row.createCell(cellnum++);
		cell3.setCellValue(pro.getPrice());
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);
			out.close();
			return "Item added Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Internal Server Error";
		}
	}

	/**
	 * This method is used for reeading excel.
	 * 
	 * @return
	 */
	public List<Product> readExcel() {
		FileInputStream fileInputStream = null;
		ArrayList<Product> productList = null;
		try {
			fileInputStream = new FileInputStream(filePath);
			File file = new File(filePath);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			productList = new ArrayList<>();
			for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
				Product product = new Product();
				Row ro = sheet.getRow(i);
				for (int j = ro.getFirstCellNum(); j <= ro.getLastCellNum(); j++) {
					Cell ce = ro.getCell(j);
					if (j == 0) {
						String orderId = ce.getStringCellValue();
						product.setProdId(orderId);
					}
					if (j == 1) {
						product.setProdName(ce.getStringCellValue());
					}
					if (j == 2) {
						product.setPrice(ce.getStringCellValue());
					}
				}
				productList.add(product);
			}
			return productList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return productList;
	}

	/**
	 * This method is used for removing an item from excel.
	 * 
	 * @param inputFilePath
	 * @param id
	 */
	public void removeItemFromExcel(String inputFilePath, int id) {
		int removeRowIndex = 0;
		try {
			FileInputStream excelFile = new FileInputStream(new File(inputFilePath));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						int columnIndex = currentCell.getColumnIndex();
						int rowIndex = currentCell.getRowIndex();
						if (rowIndex >= 0) {
							if (columnIndex == 0 && currentCell.getNumericCellValue() == id) {
								removeRowIndex = rowIndex;
							}
						}
					}
				}
			}
			removeRow(datatypeSheet, removeRowIndex);
			File outWB = new File(inputFilePath);
			OutputStream out = new FileOutputStream(outWB);
			workbook.write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used for removing a row from excel.
	 * 
	 * @param sheet
	 * @param rowIndex
	 */
	public static void removeRow(Sheet sheet, int rowIndex) {
		int lastRowNum = sheet.getLastRowNum();
		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
		}
		if (rowIndex == lastRowNum) {
			Row removingRow = sheet.getRow(rowIndex);
			if (removingRow != null) {
				sheet.removeRow(removingRow);
			}
		}
	}

	/**
	 * This method is used for getting all the product.
	 * 
	 * @return
	 */
	public List<Product> getAllProducts() {
		FileInputStream fileInputStream = null;
		ArrayList<Product> productList = null;
		try {
			fileInputStream = new FileInputStream(new File(filePath));
			File file = new File(filePath);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			productList = new ArrayList<>();
			for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
				Product product = new Product();
				Row ro = sheet.getRow(i);
				for (int j = ro.getFirstCellNum(); j <= ro.getLastCellNum(); j++) {
					Cell ce = ro.getCell(j);
					if (j == 0) {
						product.setProdId(ce.getStringCellValue());
					}
					if (j == 1) {
						product.setProdName(ce.getStringCellValue());
					}
					if (j == 2) {
						product.setPrice(ce.getStringCellValue());
					}
				}
				productList.add(product);
			}
			return productList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return productList;
	}

	public Product getProductById(String prodId) {
		List<Product> products = getAllProducts();
		Product product = null;
		for (Product p : products) {
			if (p.getProdId().equalsIgnoreCase(prodId)) {
				product = p;
			}
		}
		return product;
	}
}
