package helpers;

import static spark.Spark.*;

public class BabelNetServer {
    public static void main(String[] args) {
        BabelNetAPI api = new BabelNetAPI();
        post("/", "application/json", (req,res) -> {
            String word = req.queryParams("word");
            System.out.println("Request in ...");
            return  api.parse(word);
        });
    }
}