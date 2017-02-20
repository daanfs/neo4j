/*
 * Copyright (c) 2002-2017 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.causalclustering.load_balancing;

import java.util.List;
import java.util.Map;

import org.neo4j.causalclustering.core.consensus.LeaderLocator;
import org.neo4j.causalclustering.discovery.TopologyService;
import org.neo4j.kernel.configuration.Config;
import org.neo4j.logging.LogProvider;

/**
 * Defines the interface for an implementation of the GetServersV2
 * cluster discovery and load balancing procedure.
 */
public interface LoadBalancingPlugin
{
    void init( TopologyService topologyService, LeaderLocator leaderLocator,
            LogProvider logProvider, Config config ) throws Throwable;

    String pluginName();

    interface Result
    {
        /**
         * @return The time-to-live of the returned result.
         */
        long getTimeToLiveMillis();

        /**
         * @return List of ROUTE-capable endpoints.
         */
        List<Endpoint> routeEndpoints();

        /**
         * @return List of WRITE-capable endpoints.
         */
        List<Endpoint> writeEndpoints();

        /**
         * @return List of READ-capable endpoints.
         */
        List<Endpoint> readEndpoints();
    }

    /**
     * Runs the procedure using the supplied client context
     * and returns the result.
     *
     * @param context The client supplied context.
     * @return The result of invoking the procedure.
     */
    Result run( Map<String,String> context );
}
