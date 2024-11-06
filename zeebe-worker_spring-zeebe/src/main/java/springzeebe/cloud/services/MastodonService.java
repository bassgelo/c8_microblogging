package springzeebe.cloud.services;

import com.google.gson.Gson;
import com.sys1yagi.mastodon4j.MastodonClient;
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException;
import com.sys1yagi.mastodon4j.api.method.Statuses;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MastodonService {

    public void publishMastodonMessage(String message) throws Mastodon4jRequestException {

        //Prepare the client, maybe this information not in the class
        String accessToken = "ZzhUxXCccDkT8NYrkBgV43faEgagbCHPTi-CurF7isA";
        String instanceName = "mastodon.social";
        MastodonClient client =
                new MastodonClient.Builder(instanceName, new OkHttpClient.Builder(), new Gson())
                        .accessToken(accessToken).build();

        // Prepare your status content
        String status = message;
        Long inReplyToId = null;
        List<Long> mediaIds = null;
        boolean sensitive = false;
        String spoilerText = "Hello world from a Worker in Camunda";

        // Connect to the Mastodon API's statuses endpoint
        Statuses statusesEndpoint = new Statuses(client);

        // Post the status
        statusesEndpoint
                .postStatus(status, inReplyToId, mediaIds, sensitive, spoilerText)
                .execute();
    }
}
