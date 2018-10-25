package com.sematext.elasticsearch.fieldstats;

import org.elasticsearch.action.support.broadcast.BroadcastShardRequest;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.index.shard.ShardId;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 */
public class FieldStatsShardRequest extends BroadcastShardRequest {

    private String[] fields;
    private boolean useCache;

    public FieldStatsShardRequest() {
    }

    public FieldStatsShardRequest(ShardId shardId, FieldStatsRequest request) {
        super(shardId, request);
        Set<String> fields = new HashSet<>(Arrays.asList(request.getFields()));
        for (IndexConstraint indexConstraint : request.getIndexConstraints()) {
            fields.add(indexConstraint.getField());
        }
        this.fields = fields.toArray(new String[fields.size()]);
        useCache = request.shouldUseCache();
    }

    public String[] getFields() {
        return fields;
    }

    public boolean shouldUseCache() {
        return useCache;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        fields = in.readStringArray();
        useCache = in.readBoolean();
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeStringArrayNullable(fields);
        out.writeBoolean(useCache);
    }

}
