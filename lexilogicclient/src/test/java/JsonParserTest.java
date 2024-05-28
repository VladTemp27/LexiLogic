import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.amalgam.client.game.GameController;
import org.amalgam.client.login.LoginController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JsonParserTest {
    String json_staging_test_1 = "{\"state\":\"staging\",\"room_id\":0,\"current_round\":1," +
            "\"seconds_round_duration\":30," +
            "\"round_done\":false,\"char_matrix\":[[\"u\",\"u\",\"q\",\"o\",\"w\"],[\"o\",\"o\",\"p\",\"a\",\"o\"]," +
            "[\"x\",\"d\",\"h\",\"g\",\"r\"],[\"s\",\"w\",\"k\",\"d\",\"m\"]]," +
            "\"game_room\":{\"Lou\":{\"username\":\"Lou\",\"points\":0,\"ready\":true,\"words\":[],\"duped_words\":[]},\"Marven\":{\"username\":\"Marven\",\"points\":0,\"ready\":true,\"words\":[],\"duped_words\":[]},\"rounds\":{}}}";
    String json_staging_test_2 = "{\"state\":\"staging\",\"room_id\":0,\"current_round\":2," +
            "\"seconds_round_duration\":30," +
            "\"round_done\":false,\"char_matrix\":[[\"u\",\"u\",\"q\",\"o\",\"w\"],[\"o\",\"o\",\"p\",\"a\",\"o\"]," +
            "[\"x\",\"d\",\"h\",\"g\",\"r\"],[\"s\",\"w\",\"k\",\"d\",\"m\"]]," +
            "\"game_room\":{\"Lou\":{\"username\":\"Lou\",\"points\":0,\"ready\":true,\"words\":[]," +
            "\"duped_words\":[]},\"Marven\":{\"username\":\"Marven\",\"points\":0,\"ready\":true,\"words\":[]," +
            "\"duped_words\":[]},\"rounds\":{\"round_1\":\"Lou\"}}}";
    String json_staging_test_3 = "{\"state\":\"staging\",\"countdown\":3,\"room_id\":0,\"current_round\":4," +
            "\"seconds_round_duration\":30,\"round_done\":false,\"char_matrix\":[[\"a\",\"i\",\"a\",\"d\",\"r\"]," +
            "[\"p\",\"u\",\"o\",\"o\",\"k\"],[\"k\",\"m\",\"r\",\"a\",\"m\"],[\"s\",\"q\",\"g\",\"y\",\"j\"]]," +
            "\"game_room\":{\"Lou\":{\"username\":\"Lou\",\"points\":0,\"ready\":false,\"words\":[]," +
            "\"duped_words\":[]},\"Marven\":{\"username\":\"Marven\",\"points\":0,\"ready\":false,\"words\":[],\"duped_words\":[]},\"rounds\":{\"round_1\":\"Lou\",\"round_2\":\"No Winner\",\"round_3\":\"Lou\"}}}";
    String json_gameStarted_test_1 = "{\"state\":\"game_started\",\"room_id\":0,\"current_round\":2," +
            "\"seconds_round_duration\":30,\"round_done\":false,\"capacity\":2,\"char_matrix\":[[\"i\",\"p\",\"i\"," +
            "\"l\",\"u\"],[\"w\",\"e\",\"i\",\"x\",\"e\"],[\"o\",\"z\",\"t\",\"q\",\"v\"],[\"s\",\"y\",\"q\",\"s\"," +
            "\"m\"]],\"game_room\":{\"player_0\":{\"username\":\"test1\",\"points\":15,\"ready\":true,\"words\":[]," +
            "\"duped_words\":[]},\"player_1\":{\"username\":\"test2\",\"points\":69,\"ready\":true,\"words\":[]," +
            "\"duped_words\":[]},\"rounds\":{\"round_1\":\"No Winner\"}}}\n";


    @Test
    public void testParseJson() {
//        LoginController.username = "Lou";
        GameController obj = new GameController();
        obj.update(json_gameStarted_test_1);
    }

    @Test
    public void testJsons() {
        List<String> players = new ArrayList<>();
        players.add("Lou");
        players.add("Marven");
        players.add("Mark");

        JsonArray playersArray = new JsonArray();
        for (String player : players) {
            playersArray.add(player);
        }

        JsonObject response = new JsonObject();
        response.add("players", playersArray);

        System.out.println(response.toString());
    }
}
