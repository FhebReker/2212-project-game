package ca.uwo.cs2212.group54.stayingalive.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LevelData {
    @JsonProperty("section")
    final private int section;
    @JsonProperty("level_num")
    final private int levelNum;
    
    public LevelData(@JsonProperty("section") int section,
    @JsonProperty("level_num") int levelNum) {
        this.section = section;
        this.levelNum = levelNum;
    }

    @JsonProperty("section")
    public int getSection() {
        return section;
    }

    @JsonProperty("level_num")
    public int getLevelNum() {
        return levelNum;
    }

    

}
