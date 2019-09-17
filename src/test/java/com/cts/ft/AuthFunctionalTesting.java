package com.cts.ft;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cts.model.User;


public class AuthFunctionalTesting {

	
	@Test
	public void testLogin() {
		String inputFilePath = "./src/test/java/com/cts/ft/resources/TestNgData.xlsx";
		RestTemplate restTemplate = new RestTemplate();
		User user = new User();
		 try {

	            FileInputStream excelFile = new FileInputStream(new File(inputFilePath));
	            Workbook workbook = new XSSFWorkbook(excelFile);
	            Sheet datatypeSheet = workbook.getSheetAt(0);
	            Iterator<Row> iterator = datatypeSheet.iterator();

	            while (iterator.hasNext()) {

	                Row currentRow = iterator.next();
	                Iterator<Cell> cellIterator = currentRow.iterator();

	                TestngDto item = new TestngDto();
	                while (cellIterator.hasNext()) {

	                    Cell currentCell = cellIterator.next();
	                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
	                        System.out.print(currentCell.getStringCellValue() + "--");
	                        int columnIndex = currentCell.getColumnIndex();
	                        int rowIndex = currentCell.getRowIndex();
	                        if(rowIndex >0 ){
	                        	if( columnIndex == 1 ){
	                        item.setUrl(currentCell.getStringCellValue());
	                        	}
	                        	if( columnIndex == 2 ){
	                        		String input = currentCell.getStringCellValue();
	                        		if(input.endsWith(".json")){
	                        			user = readJsonFile(input);
	                        		}
	                        		item.setInput(currentCell.getStringCellValue());
	                        	}
	                        	if( columnIndex == 3 ){
	                        		item.setMethodType(currentCell.getStringCellValue());
	                        	}
	                        	if( columnIndex == 4 ){
	                        		if(item.getMethodType().equalsIgnoreCase("GET")){
	                        		item.setOutput(currentCell.getStringCellValue());
	                        		Map<String, String> params = new HashMap<String, String>();
	                        		params.put("id", (String)item.getInput());
	                        		}
	                        		if(item.getMethodType().equalsIgnoreCase("POST")){
	                        			String response = restTemplate.postForObject(item.getUrl(), user, String.class);
	                        			item.setOutput(currentCell.getStringCellValue());
	                        			Assert.assertEquals(response, item.getOutput());
	                        		}
	                        	}
	                        }
	                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
	                        System.out.print(currentCell.getNumericCellValue() + "--");
	                    }

	                }
	                System.out.println();

	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	  
	}
	
	/*private TechEntity techDtoToEntity(TechDTO techDtoOut) {

		TechEntity entity = new TechEntity();
		entity.setId(techDtoOut.getId());
		entity.setName(techDtoOut.getName());
		entity.setCompanysList(techDtoOut.getCompanysList());
		return entity;
	}*/



	public static User readJsonFile(String path)
	{
		User techDto = new User();
		
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader(path))
        {
            Object obj = jsonParser.parse(reader);
 
            JSONObject techDtoInput = (JSONObject) obj;
            System.out.println(techDtoInput);
            
            parseTechObject( techDtoInput, techDto);
            	            
            return techDto;
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return techDto;
    }

	private static User parseTechObject(JSONObject TechDtoIn, User user) {
		JSONObject employeeObject = (JSONObject) TechDtoIn.get("user");
		user.setUserId((String) employeeObject.get("userId"));
		user.setPassword((String) employeeObject.get("password"));
		user.setFirstName((String) employeeObject.get("firstName"));
		user.setLastName((String) employeeObject.get("lastName"));
		return user;
	}
	
	public static void main(String[] args) {
		AuthFunctionalTesting aftest = new AuthFunctionalTesting();
		
		aftest.testLogin();
	}
}
