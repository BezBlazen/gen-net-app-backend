package bzblz.gen_net_app.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Objects;

@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate() {
        // Получаем URI из JVM-параметра
        String mongoUri = System.getProperty("spring.data.mongodb.uri");

        if (mongoUri == null || mongoUri.isEmpty()) {
            throw new IllegalStateException("JVM parameter 'spring.data.mongodb.uri' is not set!");
        }

        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();

        return new MongoTemplate(
                MongoClients.create(settings),
                Objects.requireNonNull(connectionString.getDatabase())
        );
    }
}
