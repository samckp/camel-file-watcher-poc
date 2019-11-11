package com.sam.filewatch;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileWatchRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("{{fromRoute}}")
         .log("File event: ${header.CamelFileEventType} occurred on file ${header.CamelFileName}" +
                " at ${header.CamelFileLastModified}");


        from("{{watchTxtFile}}")
          .log("File event: ${header.CamelFileEventType} occurred on file ${header.CamelFileName}" +
                  " at ${header.CamelFileLastModified}");

        from("{{snapshotRoute}}")
                .setHeader(Exchange.FILE_NAME, simple("${header.CamelFileName}.${header.CamelFileLastModified}"))
                .to("{{snapshotPath}}");
    }
}
