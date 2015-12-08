package net.eldeen.snap;

public class SnapPipeline {

  private String name, sha;
  private int counter;
  private String link;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getSha() {
    return sha;
  }

  public void setSha(final String sha) {
    this.sha = sha;
  }

  public int getCounter() {
    return counter;
  }

  public void setCounter(final int counter) {
    this.counter = counter;
  }

  public String getLink() {
    return link;
  }

  public void setLink(final String link) {
    this.link = link;
  }
}
