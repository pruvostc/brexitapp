package com.crispysnippets.brexitapp;

/*
 * Created by Christian Pruvost on 16/04/2017.
 */

class RssFeedModel {

    /* The title of the Item in the RSS feed */
    private String title;
    /* The URL to the Item in the RSS feed */
    private String link;
    /* The short description of the Item in the RSS feed */
    private String description;
    /* The publication date of the Item in the RSS feed */
    private String date;

    /* The source of the data, when using multiple feeds */
    //private String origin; /* TODO: work on adding the source of the data For information */

    /**
     * Constructor for the RssFeedModel
     * @param title the title of the item in the RSS feed
     * @param link the link to the item in the RSS feed
     * @param description the description of the item in the RSS feed
     */
    public RssFeedModel(String title, String link, String description) {
        super();
        this.setTitle(title);
        this.setDescription(description);
        this.setLink(link);
    }
    /**
     * Constructor for the RssFeedModel
     */
    public RssFeedModel() {
        super();
    }

    /**
     * setter for the title
     * @param title the title of the RSS feed item
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * getter for the title
     * @return String the title of the RSS feed item
     */
    public String getTitle() {
        return title;
    }
    /**
     * setter for the description
     * @param description the description of the RSS feed item
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * getter for the description
     * @return String the description of the RSS feed item
     */
    public String getDescription() {
        return description;
    }
    /**
     * setter for the link
     * @param link the link to item in the RSS feed
     */
    public void setLink(String link) {
        this.link = link;
    }
    /**
     * getter for the link
     * @return String link to item in the RSS feed
     */
    public String getLink() {
        return link;
    }

    /**
     * setter for the date
     * @param date the date of the item in the RSS feed
     */
    public void setDate(String date) {
        this.date = date;
    }
    /**
     * getter for the date
     * @return String the date of the item in the RSS feed
     */
    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        try {
            return this.title + "\n" + this.description + "\n" + this.link + "\n" + this.date + "\n";
        } catch (Exception e) {
            return "";
        }
    }
}
