package net.eldeen.slacksnap.bot;

import java.util.Objects;

public class SlackFields {

  private String title, value;
  private boolean aShort;

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  public boolean isaShort() {
    return aShort;
  }

  public void setIsShort(final boolean isShort) {
    this.aShort = isShort;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final SlackFields that = (SlackFields) o;
    return Objects.equals(aShort, that.aShort) &&
      Objects.equals(title, that.title) &&
      Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, value, aShort);
  }

  @Override
  public String toString() {
    return "SlackFields{" +
      "title='" + title + '\'' +
      ", value='" + value + '\'' +
      ", aShort=" + aShort +
      '}';
  }
}
