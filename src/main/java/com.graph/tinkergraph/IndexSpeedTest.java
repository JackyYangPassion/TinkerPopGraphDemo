package com.graph.tinkergraph;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

public class IndexSpeedTest {
    public static void main(String[] args) throws Exception {
        //without index

        Graph graph = TinkerGraph.open();//创建图
        GraphTraversalSource g = traversal().withEmbedded(graph);//创建Process Traversal
        g.io("data/grateful-dead.xml").read().iterate();
        long start_time = System.currentTimeMillis();
        g.V().has("name","Garcia").values("name").forEachRemaining(System.out::println) ;
        long end_time = System.currentTimeMillis();
        System.out.println("Time cost without index: " + (end_time - start_time) + "ms");


        //with index
        graph = TinkerGraph.open();
        g = traversal().withEmbedded(graph);
        ((TinkerGraph) graph).createIndex("name", Vertex.class);
        System.out.println(((TinkerGraph)graph).getIndexedKeys(Vertex.class));
        /**
         * 创建索引后，如何写入索引？
         *
         */
        g.io("data/grateful-dead.xml").read().iterate();
        start_time = System.currentTimeMillis();
        g.V().has("name","Garcia").iterate().forEachRemaining(System.out::println);
        end_time = System.currentTimeMillis();
        System.out.println("Time cost with index: " + (end_time - start_time) + "ms");
    }
}
