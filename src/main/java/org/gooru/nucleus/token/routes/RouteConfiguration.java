package org.gooru.nucleus.token.routes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provide an iterable for all registered routes, so that they can be used for bulk
 * action (e.g. deploy or configure)
 * Created by ashish on 26/4/16.
 */
public class RouteConfiguration implements Iterable<RouteConfigurator> {

    private final Iterator<RouteConfigurator> internalIterator;

    public RouteConfiguration() {
        List<RouteConfigurator> configurators = new ArrayList<>(32);
        // First the global handler to enable to body reading etc
        configurators.add(new RouteGlobalConfigurator());

        // For rest of handlers, Auth should always be first one
        configurators.add(new RouteTokenConfigurator());
        configurators.add(new RouteFailureConfigurator());
        internalIterator = configurators.iterator();
    }

    @Override
    public Iterator<RouteConfigurator> iterator() {
        return new Iterator<RouteConfigurator>() {

            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public RouteConfigurator next() {
                return internalIterator.next();
            }

        };
    }

}
