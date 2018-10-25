package com.sematext.elasticsearch.plugin;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.index.IndexModule;
import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.Arrays;
import java.util.List;

import com.sematext.elasticsearch.fieldstats.FieldStatsAction;
import com.sematext.elasticsearch.fieldstats.TransportFieldStatsAction;

public class FieldStatsPlugin extends Plugin implements ActionPlugin {

  @Override public void onIndexModule(IndexModule indexModule) {
    super.onIndexModule(indexModule);
  }

 public List<ActionHandler<? extends ActionRequest, ? extends ActionResponse>> getActions() {
   return Arrays.asList(new ActionHandler<>(FieldStatsAction.INSTANCE, TransportFieldStatsAction.class));
 }
}
