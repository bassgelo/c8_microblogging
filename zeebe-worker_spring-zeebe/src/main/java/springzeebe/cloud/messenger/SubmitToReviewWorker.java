package springzeebe.cloud.messenger;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubmitToReviewWorker {

    @Autowired
    ZeebeClient zeebeClient;

    @JobWorker(type = "processTwittter_submitToReview")
    public void startTweetReview(final ActivatedJob job) {
        // This is delivering the Message to start the new Process and also contains variables necessary for said process

        String message = (String)job.getVariablesAsMap().get("message");
        zeebeClient.newPublishMessageCommand()
                .messageName("Message_ReviewStart")
                .correlationKey("StartedProgramatically")
                .variable("message", message)
                .send().join();
    }
}
