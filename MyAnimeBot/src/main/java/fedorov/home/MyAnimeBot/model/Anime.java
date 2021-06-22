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
        return  "ID IN MAL DATABASE:  " + malId + "\n\n" +
                "TITLE:  " + title + "\n\n" +
                "NUMBER OF EPISODES:  " + numberOfEpisodes + "\n\n" +
                "SYNOPSIS:  " + description + "\n\n" +
                "MAL URL:  " + animeUrl;
    }
}
