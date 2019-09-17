package com.cts.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class InternalServerErrorTest {

	@Test(expected = InternalServerError.class)
	public void test() {
		throw new InternalServerError("InternalServerErrorException");

	}
}
