package com.graph.tinkergraph;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = TinkerGraph.open();
        GraphTraversalSource g = traversal().withEmbedded(graph);

        //add Vertex and Edge
        Vertex marko = g.addV("person").property("name","marko").property("age",29).next();
        Vertex lop = g.addV("software").property("name","lop").property("lang","java").next();
        g.addE("created").from(marko).to(lop).property("weight",0.6d).iterate();


        graph.close();


    }
}