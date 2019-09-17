/**
 * This class is used to get the order information.
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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.cts.model.Order;

@Component
public class RWExcelOrder {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	String filePath = "./src/main/resources/excel/order.xlsx";

	/**
	 * This method is used for reading the excel value.
	 * 
	 * 
	 * @return
	 */
	public List<Order> readExcel() {
		FileInputStream fileInputStream = null;
		ArrayList<Order> orderList = null;
		try {
			fileInputStream = new FileInputStream(new File("./src/main/resources/excel/order.xlsx"));
			File file = new File(filePath);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			orderList = new ArrayList<>();
			for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
				Order order = new Order();
				Row ro = sheet.getRow(i);
				for (int j = ro.getFirstCellNum(); j <= ro.getLastCellNum(); j++) {
					Cell ce = ro.getCell(j);
					if (j == 0) {
						String orderId = ce.getStringCellValue();
						order.setOrderId(orderId);
					}
					if (j == 1) {
						order.setProdId(ce.getStringCellValue());
					}
					if (j == 2) {
						order.setUserID(ce.getStringCellValue());
					}
					if (j == 3) {
						order.setOrderDate(ce.getStringCellValue());
					}
				}
				orderList.add(order);
			}
			return orderList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				LOGGER.log(Level.INFO, e.getMessage());
			}
		}
		return orderList;
	}

	/**
	 * This method is used for write the order item into excel.
	 * 
	 * @param order
	 * @return
	 */
	public Order writeExcel(Order order) {
		File file = new File("./src/main/resources/excel/order.xlsx");
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		int rownum = 0;
		int cellnum = 0;
		if (file.exists() == false) {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("order");
		} else {
			try (InputStream is = new FileInputStream(file)) {
				workbook = new XSSFWorkbook(is);
				sheet = workbook.getSheetAt(0);
				rownum = sheet.getLastRowNum() + 1;
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.INFO, "File not found");
			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Input/Output exception happened");
			}
		}

		Row row = sheet.createRow(rownum++);

		Cell cell = row.createCell(cellnum++);
		cell.setCellValue(order.getOrderId());

		Cell cell2 = row.createCell(cellnum++);
		cell2.setCellValue(order.getUserID());

		Cell cell3 = row.createCell(cellnum++);
		cell3.setCellValue(order.getProdId());

		Cell cell4 = row.createCell(cellnum++);
		cell4.setCellValue(order.getOrderDate());

		try {
			file = new File("./src/main/resources/excel/order.xlsx");
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			return order;
		} catch (Exception e) {
			LOGGER.log(Level.INFO, e.getMessage());
			return null;
		}
	}

	/**
	 * This method is used for canceling the order.
	 * 
	 * @param orderId
	 * @return
	 */
	public String cancelOrder(String orderId) {
		int removeRowIndex = 0;
		String cancelledOrderId = null;
		try {
			FileInputStream excelFile = new FileInputStream(new File("./src/main/resources/excel/order.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					int columnIndex = currentCell.getColumnIndex();
					int rowIndex = currentCell.getRowIndex();
					if (rowIndex >= 0) {
						if (columnIndex == 0 && currentCell.getStringCellValue().equalsIgnoreCase(orderId)) {
							removeRowIndex = rowIndex;
							cancelledOrderId = orderId;
						}
					}
				}
			}
			removeOrder(sheet, removeRowIndex);
			File outFile = new File(filePath);
			OutputStream out = new FileOutputStream(outFile);
			workbook.write(out);
			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			LOGGER.log(Level.INFO, e.getMessage());
		} catch (IOException e) {
			LOGGER.log(Level.INFO, e.getMessage());
		}
		return cancelledOrderId;
	}

	/**
	 * This method is used for removing an order.
	 * 
	 * @param sheet
	 * @param rowIndex
	 */
	private void removeOrder(Sheet sheet, int rowIndex) {
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
	 * This method is used for getting an order.
	 * 
	 * @param orderId
	 * @return
	 */
	public Order getOrderById(String orderId) {
		List<Order> orders = readExcel();
		Order order = null;
		for (Order o : orders) {
			if (o.getOrderId().equalsIgnoreCase(orderId)) {
				order = o;
			}
		}
		return order;
	}

	/**
	 * This method is used for placing an order.
	 * 
	 * @param placeOrder
	 * @return
	 */
	public String writeOrderExcel(Order placeOrder) {

		int cellnum = 0;
		File file = new File("./src/main/resources/excel/order.xlsx");
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		int rownum = 0;
		if (file.exists() == false) {
			workbook = new XSSFWorkbook();
			LOGGER.log(Level.INFO, "coming inside if");
			sheet = workbook.createSheet("User Order Details");
		} else {
			LOGGER.log(Level.INFO, "coming in else");
			try (InputStream is = new FileInputStream(file)) {
				workbook = new XSSFWorkbook(is);
				sheet = workbook.getSheetAt(0);
				rownum = sheet.getLastRowNum() + 1;
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.WARNING, "File not found");
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Input/Output exception happened");
			}
		}
		LOGGER.log(Level.INFO, "coming inside else");
		Row row = sheet.createRow(rownum++);
		Cell cell = row.createCell(cellnum++);
		cell.setCellValue(placeOrder.getOrderId());
		Cell cell2 = row.createCell(cellnum++);
		cell2.setCellValue(placeOrder.getProdId());
		Cell cell3 = row.createCell(cellnum++);
		cell3.setCellValue(placeOrder.getUserID());
		Cell cell4 = row.createCell(cellnum++);
		cell4.setCellValue(placeOrder.getOrderDate());
		try {
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			return "User Order Placed Successfully!!!The user with ID => " + placeOrder.getUserID()
					+ " has placed the Order with ID =>" + placeOrder.getOrderId() + " having the prodcut with ID => "
					+ placeOrder.getProdId();
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Internal Server Error");
			return "Internal Server Error";
		}
	}

}
