package com.example.jidnyeshpsankhe.myapplication1;
import java.util.Comparator;

public class SearchObject {

    private String definition;
    private int upVotes;
    private int downVotes;

    public String getDefinition() {
        return definition;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public SearchObject(String definitipn, int upVotes, int downVotes){
        this.definition= definitipn;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

}
