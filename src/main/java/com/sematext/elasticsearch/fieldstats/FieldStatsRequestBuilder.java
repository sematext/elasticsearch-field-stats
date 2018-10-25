package com.sematext.elasticsearch.fieldstats;

import org.elasticsearch.action.support.broadcast.BroadcastOperationRequestBuilder;
import org.elasticsearch.client.ElasticsearchClient;

/**
 */
public class FieldStatsRequestBuilder extends
    BroadcastOperationRequestBuilder<FieldStatsRequest, FieldStatsResponse, FieldStatsRequestBuilder> {

    public FieldStatsRequestBuilder(ElasticsearchClient client, FieldStatsAction action) {
        super(client, action, new FieldStatsRequest());
    }

    public FieldStatsRequestBuilder setFields(String... fields) {
        request().setFields(fields);
        return this;
    }

    public FieldStatsRequestBuilder setIndexContraints(IndexConstraint... fields) {
        request().setIndexConstraints(fields);
        return this;
    }

    public FieldStatsRequestBuilder setLevel(String level) {
        request().level(level);
        return this;
    }

    public FieldStatsRequestBuilder setUseCache(boolean useCache) {
        request().setUseCache(useCache);
        return this;
    }
}
