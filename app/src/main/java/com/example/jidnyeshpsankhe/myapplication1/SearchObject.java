package com.example.jidnyeshpsankhe.myapplication1;
import java.util.Comparator;

public class SearchObject {

    private String definitipn;
    private int upVotes;
    private int downVotes;

    public String getDefinitipn() {
        return definitipn;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public SearchObject(String definitipn, int upVotes, int downVotes){
        this.definitipn= definitipn;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

//    @Override
//    public int compareTo(SearchObject o) {
//        int comparevotes=(o).getUpVotes();
//        return this.getUpVotes() - comparevotes;
//    }
}
