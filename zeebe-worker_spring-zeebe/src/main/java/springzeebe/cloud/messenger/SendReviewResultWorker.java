package springzeebe.cloud.messenger;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendReviewResultWorker {

    @Autowired
    ZeebeClient zeebeClient;

    //Job Worker -> part of my bean that runs automatically in this app
    @JobWorker(type = "processTwittter_sendReviewResult")
    public void startTweetReview(final ActivatedJob job) {

        // This is delivering the Message to start the new Process and also contains variables necessary for said process
        String message = (String)job.getVariablesAsMap().get("message");
        zeebeClient.newPublishMessageCommand()
                .messageName("ApprovalMessage")
                .correlationKey(message)
                .variable("approved", true)
                .send().join();
    }
}
