package net.eldeen.slacksnap.bot;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.eldeen.snap.SnapPipeline;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

public class SlackSnapBot implements RequestHandler<SlackRequest,SlackResponse> {

  private static final String SNAP_USERNAME = "snapUsername";
  private static final String SNAP_API_KEY = "snapApiKey";
  private static final String SLACK_USER_ID = "slackUserId";
  private static final String SLACK_USER_NAME = "slackUserName";
  private static final String SLACK_TEAM_ID = "slackTeamId";
  private static final String SLACK_TEAM_DOMAIN = "slackTeamDomain";
  private static final String DOMAIN_SNAP_CREDENTIALS = "snap_credentials";
  private final AmazonSimpleDB simpleDB = new AmazonSimpleDBClient();

  private final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
  private final HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final TypeReference<List<SnapPipeline>> listSnapPipeline = new TypeReference<List<SnapPipeline>>() {};

  public SlackResponse handleRequest(final SlackRequest input, final Context context) {

    String[] commands = input.getText().split(" ");

    //look at 'help' and 'login' first
    switch (commands[0]) {
      case "login":
        final List<ReplaceableAttribute> createUserAttributes = new ArrayList<>();
        createUserAttributes.add(new ReplaceableAttribute(SLACK_TEAM_DOMAIN, input.getTeamDomain(), true));
        createUserAttributes.add(new ReplaceableAttribute(SLACK_TEAM_ID, input.getTeamId(), true));
        createUserAttributes.add(new ReplaceableAttribute(SLACK_USER_NAME, input.getUserName(), true));
        createUserAttributes.add(new ReplaceableAttribute(SLACK_USER_ID, input.getUserId(), true));
        String[] snapCredentials = commands[1].split(":");
        createUserAttributes.add(new ReplaceableAttribute(SNAP_USERNAME, snapCredentials[0], true));
        createUserAttributes.add(new ReplaceableAttribute(SNAP_API_KEY, snapCredentials[1], true));
        final PutAttributesRequest putUser = new PutAttributesRequest(DOMAIN_SNAP_CREDENTIALS, input.getTeamId() +"-"+input.getUserId(), createUserAttributes);
        simpleDB.putAttributes(putUser);
        return new SlackResponse("You have been logged in");
      case "help":
        return helpResponse(String.format(
          "Usage: %s [[login <snapUsername>:<snapApiKey>] [list] [trigger <repository> [<branch>]]]",
          input.getCommand()));
    }

    //we'll need credentials for anything below
    final UsernamePasswordCredentials usernamePasswordCredentials = lookupCredentials(input);
    credentialsProvider.setCredentials(new AuthScope("api.snap-ci-com", 443),
                                       usernamePasswordCredentials);
    //look for list and trigger
    switch (commands[0]) {
      case "list":
        try {
          final HttpGet httpGet = new HttpGet("https://api.snap-ci.com/");
          httpGet.setHeader("Accept", "application/vnd.snap-ci.com.v1+json");
          final ResponseHandler<List<SnapPipeline>> responseHandler = response -> objectMapper.readValue(response.getEntity().getContent(), listSnapPipeline);
          final List<SnapPipeline> snapPipelines = client.execute(httpGet, responseHandler);

          final StringBuilder stringBuilder = new StringBuilder();
          snapPipelines.forEach(snapPipeline -> stringBuilder.append(snapPipeline.getName()));

          final SlackResponse slackResponse = new SlackResponse();
          slackResponse.setText(stringBuilder.toString());
          return slackResponse;
        }
        catch (Exception e) {
          return new SlackResponse("unable to 'list'");
        }
      case "trigger":
        if (commands.length >= 2) {
          final String repository = commands[1];
          final String branchName = commands.length >= 3? commands[2] : "master";
          try {
            final HttpPost trigger = new HttpPost(String.format("https://api.snap-ci.com/project/%s/%s/branch/%s/trigger",
                                                                usernamePasswordCredentials.getUserName(),
                                                                repository,
                                                                branchName));
            trigger.setHeader("Accept", "application/vnd.snap-ci.com.v1+json");
            final ResponseHandler<SnapPipeline> responseHandler = response -> objectMapper.readValue(response.getEntity().getContent(), SnapPipeline.class);
            final SnapPipeline triggeredPipeline = client.execute(trigger, responseHandler);
            return mapSlackResponse(triggeredPipeline);
          }
          catch (Exception e) {
            return new SlackResponse("unable to 'trigger'");
          }
        }
    }

    final SlackResponse notSupportedYet = new SlackResponse();
    notSupportedYet.setText(String.format("Action '%s' is not yet supported by this bot", input.getText()));
    return notSupportedYet;
  }

  private SlackResponse helpResponse(final String format) {
    SlackResponse helpResponse = new SlackResponse();
    helpResponse.setText(format);
    return helpResponse;
  }

  private SlackResponse mapSlackResponse(final SnapPipeline triggeredPipeline) {
    SlackResponse triggeredPipelineResponse = new SlackResponse();
    triggeredPipelineResponse.setText("A new pipeline was triggered");
    final List<SlackAttachment> attachments = new ArrayList<>();
    final SlackAttachment pipelineDetailsAttachment = new SlackAttachment();
    pipelineDetailsAttachment.setTitle(String.valueOf(triggeredPipeline.getCounter()));
    pipelineDetailsAttachment.setTitleLink(triggeredPipeline.getLink());
    attachments.add(pipelineDetailsAttachment);
    triggeredPipelineResponse.setAttachments(attachments);
    return triggeredPipelineResponse;
  }

  private UsernamePasswordCredentials lookupCredentials(final SlackRequest input) {
    final SelectResult selectResult = simpleDB.select(new SelectRequest(String.format(
      "Select * from %s where %s = '%s' AND %s = '%s' limit 1",
      DOMAIN_SNAP_CREDENTIALS,
      SLACK_TEAM_ID,
      input.getTeamId(),
      SLACK_USER_ID,
      input.getUserId())));

    final Item item = selectResult.getItems().get(0);
    final Map<String, Attribute> attributes = item.getAttributes().stream().collect(toMap(attribute -> attribute.getName(),
                                                                                          Function.<Attribute>identity()));
    final String snapUsername = attributes.get(SNAP_USERNAME).getValue();
    final String snapApiKey = attributes.get(SNAP_API_KEY).getValue();
    return new UsernamePasswordCredentials(snapUsername, snapApiKey);
  }


}
