package com.cts.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cts.controller.AbstractTest;
import com.cts.model.Product;
import com.cts.util.ProductExcelFile;

public class ProductDetailsRepoTest extends AbstractTest {

	@InjectMocks
	ProductDetailssRepo productDetailssRepo;

	@Mock
	ProductExcelFile productExcelFile;
	@Before
	@Override
	public void setUp() {
		super.setUp();
	}
	@Test
	public void testProductAdd() {
		String fileName = "./src/main/resources/excel/product.xlsx";
		Product product = new Product();
		product.setProdId("102");
		product.setProdName("nokia");
		product.setPrice("999");
		when(productExcelFile.addItemInExcel(fileName, product)).thenReturn("Added");
		String res = productDetailssRepo.addItem(product);
		assertEquals(res, "Added");
	}

	@Test
	public void testProductRemove() {
		String fileName = "./src/main/resources/excel/product.xlsx";
		Product product = new Product();
		product.setProdId("102");
		product.setProdName("nokia");
		product.setPrice("999");
		when(productExcelFile.removeItemFromExcel(fileName,product.getProdId() )).thenReturn("Removed");
		String res = productDetailssRepo.removeItem(product.getProdId());
		assertEquals(res, "Removed");
	}
}
