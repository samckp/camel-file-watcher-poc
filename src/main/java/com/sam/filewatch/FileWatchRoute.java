package com.sam.filewatch;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileWatchRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("{{fromRoute}}")
                .routeId("fileMonitorRoute")
         .log("File event: ${header.CamelFileEventType} occurred on file ${header.CamelFileName}" +
                " at ${header.CamelFileLastModified}");


        from("{{watchTxtFile}}")
                .routeId("watchTxtFileRoute")
          .log("File event: ${header.CamelFileEventType} occurred on file ${header.CamelFileName}" +
                  " at ${header.CamelFileLastModified}");

        from("{{snapshotRoute}}")
                .routeId("snapshotRoute")
                .setHeader(Exchange.FILE_NAME, simple("${header.CamelFileName}.${header.CamelFileLastModified}"))
                .to("{{snapshotPath}}");
    }
}
