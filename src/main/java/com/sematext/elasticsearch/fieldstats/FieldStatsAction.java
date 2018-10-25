package com.sematext.elasticsearch.fieldstats;

import org.elasticsearch.action.Action;
import org.elasticsearch.client.ElasticsearchClient;

/**
 */
public class FieldStatsAction extends Action<FieldStatsRequest, FieldStatsResponse, FieldStatsRequestBuilder> {

    public static final FieldStatsAction INSTANCE = new FieldStatsAction();
    public static final String NAME = "indices:data/read/field_stats";

    private FieldStatsAction() {
        super(NAME);
    }

    @Override
    public FieldStatsResponse newResponse() {
        return new FieldStatsResponse();
    }

    @Override
    public FieldStatsRequestBuilder newRequestBuilder(ElasticsearchClient client) {
        return new FieldStatsRequestBuilder(client, this);
    }
}
