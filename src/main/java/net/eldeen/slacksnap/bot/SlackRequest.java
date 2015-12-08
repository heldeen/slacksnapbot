package net.eldeen.slacksnap.bot;

import java.util.Objects;

public class SlackRequest {

  private String token, teamId, teamDomain, channelId, channelName, userId, userName, command, text, responseUrl;

  public String getToken() {
    return token;
  }

  public void setToken(final String token) {
    this.token = token;
  }

  public String getTeamId() {
    return teamId;
  }

  public void setTeamId(final String teamId) {
    this.teamId = teamId;
  }

  public String getTeamDomain() {
    return teamDomain;
  }

  public void setTeamDomain(final String teamDomain) {
    this.teamDomain = teamDomain;
  }

  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(final String channelId) {
    this.channelId = channelId;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(final String channelName) {
    this.channelName = channelName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(final String userName) {
    this.userName = userName;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(final String command) {
    this.command = command;
  }

  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  public String getResponseUrl() {
    return responseUrl;
  }

  public void setResponseUrl(final String responseUrl) {
    this.responseUrl = responseUrl;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final SlackRequest that = (SlackRequest) o;
    return Objects.equals(token, that.token) &&
      Objects.equals(teamId, that.teamId) &&
      Objects.equals(teamDomain, that.teamDomain) &&
      Objects.equals(channelId, that.channelId) &&
      Objects.equals(channelName, that.channelName) &&
      Objects.equals(userId, that.userId) &&
      Objects.equals(userName, that.userName) &&
      Objects.equals(command, that.command) &&
      Objects.equals(text, that.text) &&
      Objects.equals(responseUrl, that.responseUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token,
                        teamId,
                        teamDomain,
                        channelId,
                        channelName,
                        userId,
                        userName,
                        command,
                        text,
                        responseUrl);
  }

  @Override
  public String toString() {
    return "SlackRequest{" +
      "token='" + token + '\'' +
      ", teamId='" + teamId + '\'' +
      ", teamDomain='" + teamDomain + '\'' +
      ", channelId='" + channelId + '\'' +
      ", channelName='" + channelName + '\'' +
      ", userId='" + userId + '\'' +
      ", userName='" + userName + '\'' +
      ", command='" + command + '\'' +
      ", text='" + text + '\'' +
      ", responseUrl='" + responseUrl + '\'' +
      '}';
  }
}
