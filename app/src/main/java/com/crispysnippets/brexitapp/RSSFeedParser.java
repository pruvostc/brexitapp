package com.crispysnippets.brexitapp;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by Christian Pruvost on 16/04/2017.
 */
public class RSSFeedParser {

    public static final String TAG = RSSFeedParser.class.getName();

    public static final String DEFAULT_ENCODING = "utf-8";
    public static final String ITEM = "item";
    public static final String CHANNEL = "channel";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";
    //public static final String CATEGORY = "category";
    public static final String PUBDATE = "pubDate";
    //public static final String GUID = "guid";
    //public static String urlString = "";


    //private String feedTitle = "";
    //private String feedLink = "";
    //private String feedDescription = "";

    /**
     * Returns a RSSFeedModel List Object from parsing the XML in input.
     * @param inputStream The InputStream Handler to the XML data
     * @return List<RssFeedModel> object
     * @param nameSpaceAware boolean
     * @throws XmlPullParserException Parsing Error Exception
     * @throws IOException IO Error
     */
    public static List<RssFeedModel> parseFeed(InputStream inputStream, boolean nameSpaceAware) throws XmlPullParserException, IOException {
        String title = null;
        String link = null;
        String description = null;
        boolean isItem = false;
        List<RssFeedModel> items = new ArrayList<>();

        try {
            // obtain an XML Pull Parser (similar to SAX for Android)
            XmlPullParser parser = Xml.newPullParser();

            // setup for namespaces as 'false'
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, nameSpaceAware);

            // set input encoding value (default is UTF-8 here)
            parser.setInput(inputStream, DEFAULT_ENCODING);

            // get the type of the current event (START_TAG, END_TAG, TEXT, etc.)
            int eventType = parser.getEventType();

            String name = null;
            int parent = 0;
            RssFeedModel item = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ITEM)) {
                            // this is a new <item> element to handle, going down 1 level
                            parent++;
                            item = new RssFeedModel();
                        } else if (name.equalsIgnoreCase(CHANNEL)) {
                            // This is a new <channel>, going down 1 level
                            parent++;
                        } else if (name.equalsIgnoreCase(TITLE)) {
                            /* Handle the main feed / item title based on depth
                             * depth = 1 is part of the main feed title
                             * depth = 2 is the title of the item in the RSS feed
                             * <channel>
                             *     <title></title>
                             *     <link></link>
                             *     <description></description>
                             *     ...
                             *     <item>
                             *         <title></title>
                             *         <link></link>
                             *         <description></description>
                             *     </item>
                             * </channel>
                             */
                            if (parent == 1) {
                                // This is the main feed title
                                /* TODO HERE: Populate main Feed information */
                            } else if ((parent == 2) && (item != null)) {
                                item.setTitle(parser.nextText());
                            }
                        } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                            // Handle the main feed / item description based on depth
                            if (parent == 1) {
                                // This is the main feed description
                            } else if ((parent == 2) && (item != null)) {
                                item.setDescription(parser.nextText());
                            }
                        } else if (name.equalsIgnoreCase(LINK)) {
                            // Handle the main feed / item link based on depth
                            if (parent == 1) {
                                // this is the URL to the RSS Feed Source
                            } else if ((parent == 2) && (item != null)) {
                                item.setLink(parser.nextText());
                            }
                        } else if (name.equalsIgnoreCase(PUBDATE)) {
                            // Handle the main feed / item pubDate based on depth
                            if (parent == 1) {
                                // this is the pubDate to the RSS Feed Source
                            } else if ((parent == 2) && (item != null)) {
                                item.setDate(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ITEM)) {
                            // this is the end of an <item> element
                            parent--;
                            items.add(item); // add the RSS feed item to the list of items
                            Log.d(TAG, "Parsed item  ==> " + item.toString());
                            item = null; // undefined this (need to check if required)
                        } else if (name.equalsIgnoreCase(CHANNEL)) {
                            // This is the end of a <channel> element
                            parent--;
                        }
                        break;
                    default:
                        break;
                } // end switch cases...

                // move to the next event in the parsing process
                eventType = parser.next();

            } // end while loop...

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close(); // ensure this gets closed in all situations.
        }

        return items;

        // remember to handle name == null
    }
}
