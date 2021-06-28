package fedorov.home.MyAnimeBot.botApi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fedorov.home.MyAnimeBot.model.Anime;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class JsonToAnimeConverter {

    public Anime getAnimeObjectFromTitleJson(String rawJsonAsString) throws JsonProcessingException {
        JsonNode arrNode = new ObjectMapper().readTree(rawJsonAsString);
        JsonNode resultsNode = arrNode.get("results");
        return getAnime(resultsNode, 0);
    }

    public Anime getAnimeFromRandomJson(String rawJsonAsString) throws JsonProcessingException {
        JsonNode arrNode = new ObjectMapper().readTree(rawJsonAsString);
        JsonNode resultsNode = arrNode.get("results");
        Random random = new Random();
        int randomNumber = random.nextInt(resultsNode.size() - 1);
        return getAnime(resultsNode, randomNumber);
    }

    private Anime getAnime(JsonNode resultsNode, int i) throws JsonProcessingException {
        Anime foundAnime;

        if (resultsNode.isArray()) {
            JsonNode node = resultsNode.get(resultsNode.size() - 1);
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
        anime.setScore(node.get("score").asText());
        anime.setAnimeUrl(node.get("url").asText());
        anime.setImageUrl(node.get("image_url").asText());

        return anime;
    }
}
