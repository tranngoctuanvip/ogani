package com.example.ogani_be.Config;

import com.example.ogani_be.DTO.CommentDto;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(kafkaAdmin().getConfigurationProperties());
    }

//    @Bean
//    public NewTopic testTopic(){
//        return TopicBuilder.name("test1")
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
    @Bean
    public NewTopic newTopic(){
        return TopicBuilder.name("comment")
                .partitions(2)
                .replicas(2).build();
    }
//    @Bean
//    public ProducerFactory<String,String> producerFactory(){
//        Map<String,Object> configProp = new HashMap<>();
//        configProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//        configProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
//        return new DefaultKafkaProducerFactory<>(configProp);
//    }
//    @Bean
//    public KafkaTemplate<String,String> kafkaTemplate(){
//        return new KafkaTemplate<>(producerFactory());
//    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory(){
//        ConcurrentKafkaListenerContainerFactory<String,String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
    @Bean KafkaTemplate<String,CommentDto> commentDtoKafkaTemplate(){
        return new KafkaTemplate<>(commentDtoProducerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,CommentDto> commentDtoConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,CommentDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(commentDtoDefaultKafkaConsumerFactory());
        return factory;
    }
    @Bean
    public ProducerFactory<String, CommentDto> commentDtoProducerFactory(){
        Map<String,Object> configProp = new HashMap<>();
        configProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        configProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,CommentDtoSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(configProp);
    }
    @Bean
    public DefaultKafkaConsumerFactory<String,CommentDto> commentDtoDefaultKafkaConsumerFactory(){
        Map<String,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"receive-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CommentDtoSerializer.class.getName());
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        return new DefaultKafkaConsumerFactory<>(props);
    }
//    @Bean
//    public DefaultKafkaConsumerFactory<String,String> consumerFactory(){
//        Map<String,Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG,"my-consumer-group");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
}
