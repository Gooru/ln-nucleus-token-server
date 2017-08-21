package org.gooru.nucleus.token.bootstrap.deployment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VerticleRegistry implements Iterable<String> {

    private static final String HTTP_VERTICLE = "org.gooru.nucleus.token.bootstrap.verticles.HttpVerticle";
    private static final String TOKEN_HANDLER_VERTICLE = "org.gooru.nucleus.token.bootstrap.verticles.TokenHandlerVerticle";

    private final Iterator<String> internalIterator;

    public VerticleRegistry() {
        List<String> initializers = new ArrayList<>();
        initializers.add(TOKEN_HANDLER_VERTICLE);
        initializers.add(HTTP_VERTICLE);
        internalIterator = initializers.iterator();
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {

            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public String next() {
                return internalIterator.next();
            }

        };
    }

}
