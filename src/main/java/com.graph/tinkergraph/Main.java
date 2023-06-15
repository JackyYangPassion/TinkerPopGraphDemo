package com.graph.tinkergraph;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

/**
 * TinkerGraph is a simple in-memory graph that is most useful for
 * 1. Ad-hoc analysis of large immutable graphs that fit in memory.
 * 2. Extract subgraphs, from larger graphs that don’t fit in memory, into TinkerGraph for further analysis or other purposes.
 * 3. Use TinkerGraph as a sandbox to develop and debug complex traversals by simulating data from a larger graph inside a TinkerGraph.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = TinkerGraph.open();
        GraphTraversalSource g = traversal().withEmbedded(graph);

        //add Vertex and Edge
        Vertex marko = g.addV("person").property("name","marko").property("age",29).next();
        Vertex lop = g.addV("software").property("name","lop").property("lang","java").next();
        g.addE("created").from(marko).to(lop).property("weight",0.6d).iterate();

        //query
        g.V().has("name","marko").out("created").values("name").forEachRemaining(System.out::println);

        graph.close();


    }
}