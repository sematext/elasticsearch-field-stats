/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sematext.elasticsearch.plugin;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.cluster.node.DiscoveryNodes;
import org.elasticsearch.common.settings.ClusterSettings;
import org.elasticsearch.common.settings.IndexScopedSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsFilter;
import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestHandler;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.sematext.elasticsearch.fieldstats.FieldStatsAction;
import com.sematext.elasticsearch.fieldstats.RestFieldStatsAction;
import com.sematext.elasticsearch.fieldstats.TransportFieldStatsAction;

public class FieldStatsPlugin extends Plugin implements ActionPlugin {

 public List<ActionHandler<? extends ActionRequest, ? extends ActionResponse>> getActions() {
   return Arrays.asList(new ActionHandler<>(FieldStatsAction.INSTANCE, TransportFieldStatsAction.class));
 }

  @Override
  public List<RestHandler> getRestHandlers(final Settings settings,
      final RestController restController,
      final ClusterSettings clusterSettings,
      final IndexScopedSettings indexScopedSettings,
      final SettingsFilter settingsFilter,
      final IndexNameExpressionResolver indexNameExpressionResolver,
      final Supplier<DiscoveryNodes> nodesInCluster) {
    return Arrays.asList(new RestFieldStatsAction(settings, restController));
  }
}
