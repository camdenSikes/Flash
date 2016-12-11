package hu.ait.onetwelve.flash.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Camden Sikes on 12/10/2016.
 */

public class Deck implements Serializable{

    private String uid;
    private String author;
    private String title;
    private List<String> fronts;
    private List<String> backs;

    public Deck(){}

    public Deck(String uid, String author, String title, List<String> fronts, List<String> backs) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.fronts = fronts;
        this.backs = backs;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("fronts", fronts);
        result.put("backs", backs);

        return result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public void setFronts(List<String> fronts) {
        this.fronts = fronts;
    }

    public List<String> getBacks() {
        return backs;
    }

    public void setBacks(List<String> backs) {
        this.backs = backs;
    }
}
