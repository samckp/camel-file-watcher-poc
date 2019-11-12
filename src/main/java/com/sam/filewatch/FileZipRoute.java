package com.sam.filewatch;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.zipfile.ZipAggregationStrategy;
import org.springframework.stereotype.Component;

@Component
public class FileZipRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("{{snapshotPath}}")
                .routeId("listFiles")
                .log(LoggingLevel.INFO, "${header.CamelFileName}")
                .aggregate(new ZipAggregationStrategy())
                .constant(true)
                .completionFromBatchConsumer()
                .eagerCheckCompletion()
                .setHeader(Exchange.FILE_NAME, simple("test-$simple{date:now:yyyy-MM-dd}.zip"))
                .to("{{outputPath}}")
        ;
    }
}
