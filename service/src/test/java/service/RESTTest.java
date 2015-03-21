package service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

public class RESTTest {

	private String host = "http://localhost:4567/";

	@Test
	public void testUserRequestBid() throws ClientProtocolException, IOException {
		int userId = 1;
		Content content = Request.Get(host + "order/buy?userid=" + userId + "&price=300&star=5&place=上海-徐家汇&type=商务型")
		      .execute().returnContent();
		System.out.println(content.toString());
	}

	@Test
	public void testUserGetBidList() throws ClientProtocolException, IOException {
		int userId = 1;
		Content content = Request.Get(host + "order/history?userid=" + userId).execute().returnContent();
		System.out.println(content.toString());
	}

	@Test
	public void testGetUserList() throws ClientProtocolException, IOException {
		for (int i = 1; i < 6; i++) {
			Content content = Request.Get(host + "user/" + i).execute().returnContent();
			System.out.println(content.toString());
		}
	}

	@Test
	public void testUserBidProbability() throws ClientProtocolException, IOException {
		Content content = Request.Get(host + "order/probability?star=5&place=上海-徐汇区&type=商务型&timeout=60").execute()
		      .returnContent();
		System.out.println(content);
	}

	@Test
	public void testGetHotelList() throws ClientProtocolException, IOException {
		for (int i = 1; i < 6; i++) {
			Content content = Request.Get(host + "hotel/" + i).execute().returnContent();
			System.out.println(content.toString());
		}
	}

	@Test
	public void testHotelRequestBid() {
	}
}
