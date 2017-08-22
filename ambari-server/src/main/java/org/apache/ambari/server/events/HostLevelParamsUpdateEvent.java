/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ambari.server.events;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.apache.ambari.server.agent.stomp.dto.Hashable;
import org.apache.ambari.server.agent.stomp.dto.HostLevelParamsCluster;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains info about host level parameters for single host. This update will be sent to single host only.
 * Host can be identified by AgentConfigsUpdateEvent#hostName.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HostLevelParamsUpdateEvent extends AmbariHostUpdateEvent implements Hashable {

  /**
   * Actual version hash.
   */
  private String hash;

  /**
   * Host identifier.
   */
  private String hostName;

  /**
   * Host level parameters by clusters.
   */
  @JsonProperty("clusters")
  private final Map<String, HostLevelParamsCluster> hostLevelParamsClusters;

  public HostLevelParamsUpdateEvent(Map<String, HostLevelParamsCluster> hostLevelParamsClusters) {
    super(Type.HOSTLEVELPARAMS);
    this.hostLevelParamsClusters = hostLevelParamsClusters;
  }

  public HostLevelParamsUpdateEvent(String clusterId, HostLevelParamsCluster hostLevelParamsCluster) {
    this(Collections.singletonMap(clusterId, hostLevelParamsCluster));
  }

  @Override
  public String getHash() {
    return hash;
  }

  @Override
  public void setHash(String hash) {
    this.hash = hash;
  }

  public static HostLevelParamsUpdateEvent emptyUpdate() {
    return new HostLevelParamsUpdateEvent(null);
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  @Override
  public String getHostName() {
    return hostName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    HostLevelParamsUpdateEvent that = (HostLevelParamsUpdateEvent) o;

    return Objects.equals(hostName, that.hostName) &&
      Objects.equals(hostLevelParamsClusters, that.hostLevelParamsClusters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hostName, hostLevelParamsClusters);
  }
}
