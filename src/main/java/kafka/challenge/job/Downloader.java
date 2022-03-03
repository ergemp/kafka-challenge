package kafka.challenge.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import kafka.challenge.model.EventModel;
import kafka.challenge.source.KafkaStreaming;
import kafka.challenge.util.JavaSparkSessionSingleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Downloader {
    public static void main(String args[]) {

        KafkaStreaming streamJob = new KafkaStreaming();
        JavaInputDStream<ConsumerRecord<String, String>> stream = streamJob.GetStream();

        JavaDStream<String> values = stream.map(ConsumerRecord::value);
        JavaDStream<String> keys = stream.map(ConsumerRecord::key);

        //values.print();

        JavaDStream<EventModel> tt = values.map( line -> {

            ObjectMapper mapper = new ObjectMapper();

            EventModel model = new EventModel();
            JsonNode node = mapper.readTree(line);

            Date ddate = new Date(node.get("ts").asLong() * 1000);
            DateFormat format = new SimpleDateFormat("yyyyMMddHH");
            String formatted = format.format(ddate);

            model.setEvent(node.get("event").asText());
            model.setYear(formatted.substring(0, 4));
            model.setMonth(formatted.substring(4, 6));
            model.setDay(formatted.substring(6, 8));
            model.setHour(formatted.substring(8, 10));
            model.setLine(line);

            return model;
        });

        tt.print();

        tt.foreachRDD(rdd -> {
            SparkSession spark = JavaSparkSessionSingleton.getInstance(rdd.context().getConf());

            Dataset<Row> wordsDataFrame = spark.createDataFrame(rdd, EventModel.class);
            wordsDataFrame
                    .write()
                    .format("text")
                    .partitionBy("year","month","day","hour","event")
                    .mode(SaveMode.Append)
                    .save("hdfs://127.0.0.1:8020/spark/kafka/cs-events");
        });

        try {
            streamJob.streamingContext.start();
            streamJob.streamingContext.awaitTermination();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
