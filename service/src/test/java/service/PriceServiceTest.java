package service;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import Pojo.PricePoint;

public class PriceServiceTest {
	@Test
	public void testPriceService() {
		PriceService service = PriceService.getInstance();
		double normalPrice = 500;
		int timeout = 5;
		Date currentDate = new Date();
		currentDate.setHours(3);
		List<PricePoint> pricePoints = service.getPricePoint(normalPrice, timeout, currentDate);
		System.out.println(pricePoints);
		timeout = 10;
		pricePoints = service.getPricePoint(normalPrice, timeout, currentDate);
		System.out.println(pricePoints);
	}
}
