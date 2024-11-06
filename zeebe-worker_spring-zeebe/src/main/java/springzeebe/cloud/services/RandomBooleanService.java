package springzeebe.cloud.services;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class RandomBooleanService {

    public boolean getRandomBoolean(String seed) {
        Random random = new Random(seed.hashCode());
        return random.nextBoolean();
    }
}
