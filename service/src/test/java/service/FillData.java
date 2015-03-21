package service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

public class FillData {

	private String host = "http://10.16.52.103:4567/";

	@Test
	public void fillHotelBid() throws ClientProtocolException, IOException {
		int orderid = 1;
		int hotelid = 1;
		int extra = 5;
		// Suppose orderid = 1;
		Content content = Request
		      .Get(host + "order/hotelbid?hotelid=" + hotelid + "&orderid=" + orderid + "&extra=" + extra
		            + "&comment=FUCK").execute().returnContent();
		System.out.println(content);
	}
}
