/**
 * This class is used to get the Authenticated user information.
 * 
 * @author 764432
 *
 */
package com.cts.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.cts.model.User;

@Component
public class RWExcelFileAuth {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int rownum;
	private int cellnum;

	/**
	 * This method is used for reading the user information.
	 * 
	 * @param user
	 * @param inputFilePath
	 * @return
	 */
	@SuppressWarnings("resource")
	public String readExcel(User user, String inputFilePath) {

		String response = "";
		User userExcel = new User();
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
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						int columnIndex = currentCell.getColumnIndex();
						int rowIndex = currentCell.getRowIndex();
						if (rowIndex > 0) {
							if (columnIndex == 0) {
								userExcel.setUserId(currentCell.getStringCellValue());
							}
							if (columnIndex == 1) {
								userExcel.setFirstName(currentCell.getStringCellValue());
							}
							if (columnIndex == 2) {
								userExcel.setLastName(currentCell.getStringCellValue());
							}
							if (columnIndex == 3) {
								userExcel.setPassword(currentCell.getStringCellValue());
							}
							if (user.getUserId().equals(userExcel.getUserId())
									&& user.getPassword().equals(userExcel.getPassword())) {

								response = "User Looged in sucessfully";
								break;

							}
						}
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						return "";
					}

				}
			}
			return response;
		} catch (IOException e) {
			LOGGER.log(Level.INFO, e.getLocalizedMessage());
			return "Internal server error";
		}

	}

	/**
	 * This method is used for registering the user.
	 * 
	 * @param user
	 * @param fileName
	 * @return
	 */
	public String writeExcel(User user, String fileName) {

		try {
			FileInputStream fileInput = new FileInputStream(new File(fileName));
			Workbook workbook = new XSSFWorkbook(fileInput);
			Sheet sheet = workbook.getSheetAt(0);
			rownum = sheet.getLastRowNum();

			cellnum = 0;

			Row row = sheet.createRow(++rownum);

			Cell cell = row.createCell(cellnum++);

			cell.setCellValue(user.getUserId());

			Cell cell2 = row.createCell(cellnum++);
			cell2.setCellValue(user.getFirstName());

			Cell cell3 = row.createCell(cellnum++);
			cell3.setCellValue(user.getLastName());

			Cell cell4 = row.createCell(cellnum++);
			cell4.setCellValue(user.getPassword());

			FileOutputStream out = new FileOutputStream(new File(fileName));
			workbook.write(out);
			out.close();
			return "User Registered Successfully";

		} catch (IOException e) {
			LOGGER.log(Level.INFO, e.getLocalizedMessage());
			return "Internal Server Error";
		}

	}

}
