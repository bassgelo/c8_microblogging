package zeebe.cloud;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientBuilder;
import io.camunda.zeebe.client.api.worker.JobWorker;
import java.time.Duration;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobWorkerCreation {
    private static final Logger logger = LoggerFactory.getLogger(JobWorkerCreation.class);

    public static void main(final String[] args) {
        final String defaultAddress = "localhost:26500";
        final String envVarAddress = System.getenv("ZEEBE_ADDRESS");
        final String jobType = System.getenv().getOrDefault("JOB_TYPE", "foo");

        try (final ZeebeClient client = createZeebeClient(defaultAddress, envVarAddress)) {
            logger.info("Opening job worker.");

            try (final JobWorker workerRegistration =
                         client
                                 .newWorker()
                                 .jobType(jobType)
                                 .handler(new ExampleJobHandler())
                                 .timeout(Duration.ofSeconds(10))
                                 .open()) {
                logger.info("Job worker opened and receiving jobs.");

                // run until System.in receives exit command
                Utility.waitUntilSystemInput("exit");
            }
        }
    }

    private static ZeebeClient createZeebeClient(String defaultAddress, String envVarAddress) {
        final ZeebeClientBuilder clientBuilder;
        if (envVarAddress != null) {
            clientBuilder = ZeebeClient.newClientBuilder().gatewayAddress(envVarAddress);
        } else {
            clientBuilder = ZeebeClient.newClientBuilder().gatewayAddress(defaultAddress).usePlaintext();
        }
        return clientBuilder.build();
    }
}
