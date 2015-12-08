package net.eldeen.slacksnap.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SlackResponse {

  private String text;
  private List<SlackAttachment> attachments = new ArrayList<>();

  public SlackResponse(final String text) {
    this.text = text;
  }

  public SlackResponse() {

  }

  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  public List<SlackAttachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(final List<SlackAttachment> attachments) {
    this.attachments = attachments;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final SlackResponse that = (SlackResponse) o;
    return Objects.equals(text, that.text) &&
      Objects.equals(attachments, that.attachments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, attachments);
  }
}
