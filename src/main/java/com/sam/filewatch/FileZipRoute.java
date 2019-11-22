package com.sam.filewatch;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.zipfile.ZipAggregationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileZipRoute extends RouteBuilder {

    @Autowired
    MailProcessor mailProcessor;

    @Override
    public void configure() throws Exception {

        onException(Exception.class).handled(true).log(LoggingLevel.ERROR, "${body}");

        from("{{snapshotPath}}")
                .routeId("listFiles")
                .log(LoggingLevel.INFO, "${header.CamelFileName}")
                .aggregate(new ZipAggregationStrategy())
                .constant(true)
                .completionFromBatchConsumer()
                .eagerCheckCompletion()
                .setHeader(Exchange.FILE_NAME, simple("test-$simple{date:now:yyyy-MM-dd}.zip"))
                .process(mailProcessor)     // send email processor
                .to("{{outputPath}}")
        ;
    }
}
