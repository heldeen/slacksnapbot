package net.eldeen.slacksnap.bot;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SlackAttachment {

  private String fallback, color, pretext, authorName, AuthorLink, authorIcon, title, titleLink, text;
  private List<SlackFields> fields = new ArrayList<>();
  private URL imageUrl, thumbUrl;

  public String getFallback() {
    return fallback;
  }

  public void setFallback(final String fallback) {
    this.fallback = fallback;
  }

  public String getColor() {
    return color;
  }

  public void setColor(final String color) {
    this.color = color;
  }

  public String getPretext() {
    return pretext;
  }

  public void setPretext(final String pretext) {
    this.pretext = pretext;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(final String authorName) {
    this.authorName = authorName;
  }

  public String getAuthorLink() {
    return AuthorLink;
  }

  public void setAuthorLink(final String authorLink) {
    AuthorLink = authorLink;
  }

  public String getAuthorIcon() {
    return authorIcon;
  }

  public void setAuthorIcon(final String authorIcon) {
    this.authorIcon = authorIcon;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getTitleLink() {
    return titleLink;
  }

  public void setTitleLink(final String titleLink) {
    this.titleLink = titleLink;
  }

  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  public List<SlackFields> getFields() {
    return fields;
  }

  public void setFields(final List<SlackFields> fields) {
    this.fields = fields;
  }

  public URL getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(final URL imageUrl) {
    this.imageUrl = imageUrl;
  }

  public URL getThumbUrl() {
    return thumbUrl;
  }

  public void setThumbUrl(final URL thumbUrl) {
    this.thumbUrl = thumbUrl;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final SlackAttachment that = (SlackAttachment) o;
    return Objects.equals(fallback, that.fallback) &&
      Objects.equals(color, that.color) &&
      Objects.equals(pretext, that.pretext) &&
      Objects.equals(authorName, that.authorName) &&
      Objects.equals(AuthorLink, that.AuthorLink) &&
      Objects.equals(authorIcon, that.authorIcon) &&
      Objects.equals(title, that.title) &&
      Objects.equals(titleLink, that.titleLink) &&
      Objects.equals(text, that.text) &&
      Objects.equals(fields, that.fields) &&
      Objects.equals(imageUrl, that.imageUrl) &&
      Objects.equals(thumbUrl, that.thumbUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fallback,
                        color,
                        pretext,
                        authorName,
                        AuthorLink,
                        authorIcon,
                        title,
                        titleLink,
                        text,
                        fields,
                        imageUrl,
                        thumbUrl);
  }

  @Override
  public String toString() {
    return "SlackAttachment{" +
      "fallback='" + fallback + '\'' +
      ", color='" + color + '\'' +
      ", pretext='" + pretext + '\'' +
      ", authorName='" + authorName + '\'' +
      ", AuthorLink='" + AuthorLink + '\'' +
      ", authorIcon='" + authorIcon + '\'' +
      ", title='" + title + '\'' +
      ", titleLink='" + titleLink + '\'' +
      ", text='" + text + '\'' +
      ", fields=" + fields +
      ", imageUrl=" + imageUrl +
      ", thumbUrl=" + thumbUrl +
      '}';
  }
}
