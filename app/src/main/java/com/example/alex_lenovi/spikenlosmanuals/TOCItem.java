package com.example.alex_lenovi.spikenlosmanuals;

import org.jsoup.nodes.Element;

/**
 * Created by alex-lenovi on 7/22/2016.
 */
public class TOCItem {
    String title = "";
    String style = "";
    int level = 0;
    String details = "";
    String link = "";
    String html_str = "";
    Element element = null;

    public TOCItem(Element element) {
        this.element = element;
        //html_str = element.html();
        title = element.text().replaceAll("\\s+", " ").trim();
        style = element.tagName();
        level = Integer.parseInt(style.replaceAll("[\\D]", ""));
        link = element.select("a[name]").attr("name");

    }

    public String getHtmlStr() {
        return html_str;
    }

    public void setHtmlStr(String xml_str) {
        this.html_str = xml_str;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
