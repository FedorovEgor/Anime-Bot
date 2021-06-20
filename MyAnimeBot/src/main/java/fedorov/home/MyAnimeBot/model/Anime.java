package fedorov.home.MyAnimeBot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Anime {
    private String malId;
    private String title;
    private String description;
    private String numberOfEpisodes;
    private String imageUrl;
    private String animeUrl;

    @Override
    public String toString() {
        return  "Id in MAL database:  " + malId + "\n\n" +
                "Title:  " + title + "\n\n" +
                "Number of episodes:  " + numberOfEpisodes + "\n\n" +
                "Synopsis:  " + description + "\n\n" +
                "MAL URL:  " + animeUrl;
    }
}
