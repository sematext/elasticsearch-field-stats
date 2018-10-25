package com.sematext.elasticsearch.fieldstats;

import org.elasticsearch.Version;
import org.elasticsearch.action.support.broadcast.BroadcastShardResponse;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.index.shard.ShardId;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 */
public class FieldStatsShardResponse extends BroadcastShardResponse {

    private Map<String, FieldStats<?>> fieldStats;

    public FieldStatsShardResponse() {
    }

    public FieldStatsShardResponse(ShardId shardId, Map<String, FieldStats<?>> fieldStats) {
        super(shardId);
        this.fieldStats = fieldStats;
    }

    public Map<String, FieldStats<?>> getFieldStats() {
        return fieldStats;
    }

    Map<String, FieldStats<?> > filterNullMinMax() {
        return fieldStats.entrySet().stream()
            .filter((e) -> e.getValue().hasMinMax())
            .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        final int size = in.readVInt();
        fieldStats = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            FieldStats value = FieldStats.readFrom(in);
            fieldStats.put(key, value);
        }
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        final Map<String, FieldStats<?> > stats;
        stats = getFieldStats();
        out.writeVInt(stats.size());
        for (Map.Entry<String, FieldStats<?>> entry : stats.entrySet()) {
            out.writeString(entry.getKey());
            entry.getValue().writeTo(out);
        }
    }
}
