/**
 * This class is used to validate the authenticated user.
 * 
 * @author 764432
 *
 */
package com.cts.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cts.model.User;
import com.cts.util.RWExcelFileAuth;

@Repository
public class AuthRepository {

	@Autowired
	private RWExcelFileAuth rWExcelFileAuth;

	/**
	 * This constructor initialize RWExcelFileAuth variable.
	 * 
	 * @param rWExcelFileAuth
	 */
	public AuthRepository(RWExcelFileAuth rWExcelFileAuth) {
		super();
		this.rWExcelFileAuth = rWExcelFileAuth;
	}

	/**
	 * This method is used to validate the user credentials.
	 * 
	 * @param user
	 * @return
	 */
	public String login(final User user) {

		final String res = rWExcelFileAuth.readExcel(user, "./src/main/resources/excel/user.xlsx");
		// return res.equals("User Looged in sucessfully") ? res : "invalid user
		// details";
		return "User Looged in sucessfully".equals(res) ? res : "invalid user details";

	}

	/**
	 * This method is used to write the user credential to excel.
	 * 
	 * @param user
	 * @return
	 */
	public String createUser(final User user) {

		return rWExcelFileAuth.writeExcel(user, "./src/main/resources/excel/user.xlsx");

	}
}
