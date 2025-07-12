package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
public class Broker {

    public static void main(String[] args) throws IOException {

        EmbeddedKafkaBroker kafkaBroker = new EmbeddedKafkaBroker(1)
                .kafkaPorts(9092);
        kafkaBroker.afterPropertiesSet();
    }
}


class MessageProducer {
    private static KafkaProducer<String, String> producer = new KafkaProducer<>(
            Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName(),
                    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()
            )
    );

    public static void send(ProducerRecord<String, String> record) {
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                System.err.println("Error sending message: " + exception.getMessage());
            } else {
                System.out.println("Message sent successfully to topic " + metadata.topic() +
                        " partition " + metadata.partition() + " offset " + metadata.offset());
            }
        });

    }
}

class MessageConsumer {
    KafkaConsumer<String, String> consumer;

    public MessageConsumer(String topic, String login) {
        consumer = new KafkaConsumer<>(
                Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                        ConsumerConfig.GROUP_ID_CONFIG, login,
                        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName(),
                        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName(),
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest" // Start reading from the earliest message
                )
        );

        consumer.subscribe(Collections.singletonList(topic));
        consumer.poll(Duration.of(1, ChronoUnit.SECONDS)).forEach(record -> {
            System.out.println(login + ": " + record.value());
        });
    }
}

class Topic {
    public List<User> users;
}

class User {
    public User(String name) {
        this.name = name;
    }

    public String name;

}