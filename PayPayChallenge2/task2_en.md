# Requirements
```
The system needs to:

handle large write volume: Billions write events per day.

handle large read/query volume: Millions merchants want to get insight about their business. Read/Query patterns are time-series related metrics.

provide metrics to customers with at most one hour delay.

run with minimum downtime.

have the ability to reprocess historical data in case of bugs in the processing logic.
```

# Architecture
## Backend
### Fact Data(Base Data)
It handles a large amount of writing events, I adopt `Amazon DynamoDB` which is a key-value store DB to pin on a high throughput.

### Metrics Data(Processed Data)
It register consumers in the message queue corresponding to the fact data write event, and asynchronously generate and store the metrics data.
`Apache Kafka` is used for message queue processing.

The method of storing metrics data depends on the specific use, it has no direct dependency on fact data, so it can be selected according to individual purpose.
A managed RDB such as Amazon Aurora is used for complex items involving joining metrics, or Amazon Elasticsearch Service is used for full text search of texts, numbers, divisions, etc.

In order to improve the development efficiency while ensuring the consistency between the fact data and the metrics data, prepare Interceptor class as a framework for reading and writing the fact data.
In addition, by preparing regeneration logic in batch processing, it is also possible to regenerate metrics data using all fact data at an arbitrary timing.

## AP
By adopting the micro service architecture and linking between each services via Web API,
We will avoid densely dependence on service, and enhance development and operation efficiency for each service.

In addition, each service is containerized as Docker, and kubernetes is used for these operations.
While maintaining the scalability of the entire system, it also achieves version update with minimum downtime due to rolling update.

