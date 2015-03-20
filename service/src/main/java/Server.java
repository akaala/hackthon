import com.alibaba.fastjson.JSON;

import Pojo.User;

import static spark.Spark.get;

public class Server {

	public static void main(String[] args) {
		User user = new User(12, "my name");

		String userS =JSON.toJSONString(user);

		get("/hello", (req, res) -> userS);
	}
}
