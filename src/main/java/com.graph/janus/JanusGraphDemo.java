package com.graph.janus;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
public class JanusGraphDemo {
    public static void main(String args[]) throws Exception {
        GraphTraversalSource g = traversal().withRemote("conf/remote-graph.properties");

        Object herculesAge = g.V().has("name", "hercules").values("age").next();
        System.out.println("Hercules is " + herculesAge + " years old.");
    }
}
