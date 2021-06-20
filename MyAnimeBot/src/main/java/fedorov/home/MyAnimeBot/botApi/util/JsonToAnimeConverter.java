package fedorov.home.MyAnimeBot.botApi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fedorov.home.MyAnimeBot.model.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonToAnimeConverter {

    public Anime getAnimeObjectFromJson(String rawJsonAsString) throws JsonProcessingException {
        Anime foundAnime;

        JsonNode arrNode = new ObjectMapper().readTree(rawJsonAsString);
        JsonNode resultsNode = arrNode.get("results");
        if (resultsNode.isArray()) {
            JsonNode node = resultsNode.get(0);
            foundAnime = convertJsonNodeToObject(node);
        } else {
            foundAnime = convertJsonNodeToObject(resultsNode);
        }

        return foundAnime;
    }

    private Anime convertJsonNodeToObject(JsonNode node) {
        Anime anime = new Anime();
        anime.setMalId(node.get("mal_id").asText());
        anime.setTitle(node.get("title").asText());
        anime.setNumberOfEpisodes(node.get("episodes").asText());
        anime.setDescription(node.get("synopsis").asText());
        anime.setAnimeUrl(node.get("url").asText());
        anime.setImageUrl(node.get("image_url").asText());

        return anime;
    }

}
