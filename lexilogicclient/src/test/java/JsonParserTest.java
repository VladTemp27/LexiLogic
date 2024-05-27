import com.google.gson.JsonObject;
import org.amalgam.client.game.GameController;
import org.junit.Test;

public class JsonParserTest {
    @Test
    public void testParseJson() {
        GameController obj = new GameController();
        String json = "{\"state\":\"staging\",\"countdown\":5,\"room_id\":0,\"current_round\":2,\"seconds_round_duration\":30,\"round_done\":false,\"char_matrix\":[[\"u\",\"l\",\"k\",\"m\",\"y\"],[\"c\",\"d\",\"a\",\"x\",\"n\"],[\"a\",\"z\",\"m\",\"o\",\"o\"],[\"a\",\"e\",\"l\",\"l\",\"s\"]],\"game_room\":{\"Lou\":{\"username\":\"Lou\",\"points\":0,\"ready\":false,\"words\":[],\"duped_words\":[]},\"Marven\":{\"username\":\"Marven\",\"points\":0,\"ready\":false,\"words\":[],\"duped_words\":[]},\"rounds\":{\"round_1\":\"Lou\"}}}\n";
        String json1 = "{\"state\":\"staging\",\"room_id\":0,\"current_round\":3,\"seconds_round_duration\":30,\"round_done\":false,\"char_matrix\":[[\"u\",\"u\",\"q\",\"o\",\"w\"],[\"o\",\"o\",\"p\",\"a\",\"o\"],[\"x\",\"d\",\"h\",\"g\",\"r\"],[\"s\",\"w\",\"k\",\"d\",\"m\"]],\"game_room\":{\"Lou\":{\"username\":\"Lou\",\"points\":0,\"ready\":true,\"words\":[],\"duped_words\":[]},\"Marven\":{\"username\":\"Marven\",\"points\":0,\"ready\":true,\"words\":[],\"duped_words\":[]},\"rounds\":{\"round_1\":\"No Winner\",\"round_2\":\"Lou\"}}}";
        obj.update(json1);
    }
}
