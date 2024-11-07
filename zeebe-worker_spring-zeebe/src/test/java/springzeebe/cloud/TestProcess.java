package springzeebe.cloud;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivateJobsResponse;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.process.test.assertions.DeploymentAssert;
import io.camunda.zeebe.process.test.assertions.ProcessInstanceAssert;
import io.camunda.zeebe.process.test.extension.testcontainer.ZeebeProcessTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.camunda.zeebe.process.test.assertions.BpmnAssert.assertThat;


@ZeebeProcessTest//This annotation creates a new Camunda Engine completely empty
public class TestProcess {

    //This class does not have any mocks, as we drive the process manually

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ZeebeClient zeebeClient;

    @BeforeEach
    void deployProcesses() {
        // The embedded engine is completely reset before each test run.
        // Therefore, we need to deploy the process each time
        DeploymentEvent event = zeebeClient.newDeployCommand()
                .addResourceFromClasspath("PostingProcess.bpmn")
                .send()
                .join();

        //Deployment Assertions
        DeploymentAssert assertions = assertThat(event);
    }

    @Test //This method tests the happy path
    void testTweetApproved_happyPath() throws Exception {
        // start a process instance and deploy manually the file located in the folder resources
        ProcessInstanceEvent processInstance = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("Posting_Process").latestVersion()
                .variable("message", "Test Message")
                .send().join();

        //Process Instance Assertions
        ProcessInstanceAssert assertions = assertThat(processInstance);

        //We check that the process is active
        assertions.isActive();
        //and that the process is waiting at
        assertions.isWaitingAtElements("Activity_WriteTweet_Connector");

        //FIRST TASK
        //We need to manually activate a new job, because remember the engine runs and waits for a worker to complete the first task
        ActivateJobsResponse jobResponse = zeebeClient.newActivateJobsCommand()
                .jobType("processTwittter_generateMessage")
                .maxJobsToActivate(1)
                .send()
                .join();
        ActivatedJob activatedJob = jobResponse.getJobs().get(0);

        //We need to manually complete the job
        zeebeClient.newCompleteCommand(activatedJob)
                .variable("message", "Test Message")
                .send().join();

        //SECOND TASK
        //We need to manually activate a new job, because remember the engine runs and waits for a worker to complete the first task
        jobResponse = zeebeClient.newActivateJobsCommand()
                .jobType("processTwittter_approveMessage")
                .maxJobsToActivate(1)
                .send()
                .join();
        activatedJob = jobResponse.getJobs().get(0);

        //We need to manually complete the job
        zeebeClient.newCompleteCommand(activatedJob)
                .variable("approved", true)
                .send().join();

        //THIRD TASK
        //We need to manually activate a new job, because remember the engine runs and waits for a worker to complete the first task
        jobResponse = zeebeClient.newActivateJobsCommand()
                .jobType("PublishTweetTask")
                .maxJobsToActivate(1)
                .send()
                .join();
        activatedJob = jobResponse.getJobs().get(0);

        //We need to manually complete the job
        zeebeClient.newCompleteCommand(activatedJob)
                .send().join();


        // Let's assert that it passed the last element and completed
        assertThat(processInstance)
                .hasPassedElement("Activity_0d9r54k")
                .isCompleted();
    }

    @Test //This method tests the not approved path
    void testTweetApproved_notApprovedPath() throws Exception {
        // start a process instance and deploy manually the file located in the folder resources
        ProcessInstanceEvent processInstance = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("Posting_Process").latestVersion()
                .variable("message", "Test Message")
                .send().join();

        //Process Instance Assertions
        ProcessInstanceAssert assertions = assertThat(processInstance);

        //We check that the process is active
        assertions.isActive();
        //and that the process is waiting at
        assertions.isWaitingAtElements("Activity_WriteTweet_Connector");

        //FIRST TASK
        //We need to manually activate a new job, because remember the engine runs and waits for a worker to complete the first task
        ActivateJobsResponse jobResponse = zeebeClient.newActivateJobsCommand()
                .jobType("processTwittter_generateMessage")
                .maxJobsToActivate(1)
                .send()
                .join();
        ActivatedJob activatedJob = jobResponse.getJobs().get(0);

        //We need to manually complete the job
        zeebeClient.newCompleteCommand(activatedJob)
                .variable("message", "Test Message")
                .send().join();

        //SECOND TASK
        //We need to manually activate a new job, because remember the engine runs and waits for a worker to complete the first task
        jobResponse = zeebeClient.newActivateJobsCommand()
                .jobType("processTwittter_approveMessage")
                .maxJobsToActivate(1)
                .send()
                .join();
        activatedJob = jobResponse.getJobs().get(0);

        //We need to manually complete the job
        zeebeClient.newCompleteCommand(activatedJob)
                .variable("approved", false)
                .send().join();

        // Let's assert that it passed the last element and completed
        assertThat(processInstance)
                .isActive()
                .isWaitingAtElements("Activity_WriteTweet_Connector")
                .hasVariableWithValue("approved", false)
                .hasNotPassedElement("Activity_0d9r54k")
                .isNotCompleted();
    }

}
