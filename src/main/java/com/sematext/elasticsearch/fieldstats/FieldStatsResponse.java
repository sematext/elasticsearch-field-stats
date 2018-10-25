package com.sematext.elasticsearch.fieldstats;

import org.elasticsearch.Version;
import org.elasticsearch.action.ShardOperationFailedException;
import org.elasticsearch.action.support.DefaultShardOperationFailedException;
import org.elasticsearch.action.support.broadcast.BroadcastResponse;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class FieldStatsResponse extends BroadcastResponse {
    private Map<String, Map<String, FieldStats>> indicesMergedFieldStats;
    private Map<String, String> conflicts;

    public FieldStatsResponse() {
    }

    public FieldStatsResponse(int totalShards, int successfulShards, int failedShards,
                              List<DefaultShardOperationFailedException> shardFailures,
                              Map<String, Map<String, FieldStats>> indicesMergedFieldStats,
                              Map<String, String> conflicts) {
        super(totalShards, successfulShards, failedShards, shardFailures);
        this.indicesMergedFieldStats = indicesMergedFieldStats;
        this.conflicts = conflicts;
    }

    @Nullable
    public Map<String, FieldStats> getAllFieldStats() {
        return indicesMergedFieldStats.get("_all");
    }

    public Map<String, String> getConflicts() {
        return conflicts;
    }

    public Map<String, Map<String, FieldStats>> getIndicesMergedFieldStats() {
        return indicesMergedFieldStats;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        int size = in.readVInt();
        indicesMergedFieldStats = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            int indexSize = in.readVInt();
            Map<String, FieldStats> indexFieldStats = new HashMap<>(indexSize);
            indicesMergedFieldStats.put(key, indexFieldStats);
            for (int j = 0; j < indexSize; j++) {
                key = in.readString();
                FieldStats value = FieldStats.readFrom(in);
                indexFieldStats.put(key, value);
            }
        }
        size = in.readVInt();
        conflicts = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            String value = in.readString();
            conflicts.put(key, value);
        }

    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeVInt(indicesMergedFieldStats.size());
        for (Map.Entry<String, Map<String, FieldStats>> entry1 : indicesMergedFieldStats.entrySet()) {
            out.writeString(entry1.getKey());
            int size = entry1.getValue().size();
            out.writeVInt(size);
            for (Map.Entry<String, FieldStats> entry2 : entry1.getValue().entrySet()) {
                out.writeString(entry2.getKey());
                entry2.getValue().writeTo(out);
            }
        }
        out.writeVInt(conflicts.size());
        for (Map.Entry<String, String> entry : conflicts.entrySet()) {
            out.writeString(entry.getKey());
            out.writeString(entry.getValue());
        }
    }
}
