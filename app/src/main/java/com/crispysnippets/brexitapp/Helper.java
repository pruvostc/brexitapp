package com.crispysnippets.brexitapp;

/* Created by Christian Pruvost on 21/04/2017. */

import android.text.Html;
import android.text.Spanned;

/**
 * This Class is to ensure support for older versions of android when required.
 */
public class Helper {


    /**
     * Returns a HTML String for insertion in a View (e.g. TextView)
     * @param html  the HTML to be created for the View
     * @return Spanned result a HTML object for android to display in a View
     */
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
