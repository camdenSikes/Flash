package hu.ait.onetwelve.flash.model;

import java.io.Serializable;
import java.util.List;

public class Deck implements Serializable{

    private String uid;
    private String author;
    private String title;
    private List<String> fronts;
    private List<String> backs;
    private boolean priv;

    public Deck(){}

    public Deck(String uid, String author, String title, List<String> fronts, List<String> backs, boolean priv) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.fronts = fronts;
        this.backs = backs;
        this.priv = priv;
    }

    public String getUid() {
        return uid;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getFronts() {
        return fronts;
    }

    public List<String> getBacks() {
        return backs;
    }

    public boolean isPrivate() {
        return priv;
    }

    public void setPrivate(boolean priv) {
        this.priv = priv;
    }
}
