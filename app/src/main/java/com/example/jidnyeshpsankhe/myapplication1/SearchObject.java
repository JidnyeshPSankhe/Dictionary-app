package com.example.jidnyeshpsankhe.myapplication1;

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
}
