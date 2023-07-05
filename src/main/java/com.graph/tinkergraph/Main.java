package com.graph.tinkergraph;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalExplanation;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalMetrics;
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
        //根据框架解释 TinkerPop 执行逻辑
        Graph graph = TinkerGraph.open();//创建图
        //GraphTraversalSource g = traversal().withEmbedded(graph);
        GraphTraversalSource g  = graph.traversal();//创建遍历器

        //add Vertex/Edge by Graph API use github compilot
        // 通过 API 直接操作图 便于查询调试
        Vertex a = graph.addVertex("name","alice");
        Vertex b = graph.addVertex("name","bobby");
        Vertex c = graph.addVertex("name","cindy");
        Vertex d = graph.addVertex("name","david");
        Vertex e = graph.addVertex("name","eliza");
        a.addEdge("rates",b,"tag","ruby","value",9);
        b.addEdge("rates",c,"tag","ruby","value",8);
        c.addEdge("rates",d,"tag","ruby","value",7);
        d.addEdge("rates",e,"tag","ruby","value",6);
        e.addEdge("rates",a,"tag","java","value",10);

        //add Vertex/Edge by traversal
//        g.addV().property("name","alice").as("a").
//                addV().property("name","bobby").as("b").
//                addV().property("name","cindy").as("c").
//                addV().property("name","david").as("d").
//                addV().property("name","eliza").as("e").
//                addE("rates").from("a").to("b").property("tag","ruby").property("value",9).
//                addE("rates").from("b").to("c").property("tag","ruby").property("value",8).
//                addE("rates").from("c").to("d").property("tag","ruby").property("value",7).
//                addE("rates").from("d").to("e").property("tag","ruby").property("value",6).
//                addE("rates").from("e").to("a").property("tag","java").property("value",10).
//                iterate();

        //query：类Gremlin 写法 便于在console 调试
//        g.V().has("name","alice")
//                .out("rates")
//                .values("name")
//                .limit(10)
//                .forEachRemaining(System.out::println);
        //拆后便于进行Debug ：获取 Alice 一度好友姓名
        //explain() :get step info
        //          [TinkerGraphStep(vertex,[name.eq(alice)]),
        //           VertexStep(OUT,[rates],vertex),
        //           PropertiesStep([name],value),
        //           RangeGlobalStep(0,10)]
//        GraphTraversal allV1 = g.V();
//        GraphTraversal hasName1 = allV1.has("name", "alice");//1. 此处在什么地方过滤
//        GraphTraversal outCreated1 = hasName1.out("rates");//2. 同样下推到什么地方
//        GraphTraversal valueName1 = outCreated1.values("name");
//        GraphTraversal outLimit1 = valueName1.limit(10);
//
//        //gremlin explain()
////        TraversalExplanation explain = outLimit.explain();
////        System.out.println(explain.toString());
////
////        //gremlin profile()
////        System.out.println(outLimit.profile());
//
//        //gremlin get result
//        System.out.println(outLimit1.next());



        //Debug repeat out time(s):获取Alice 两度好友姓名
        //explain() :get step info
        //          [TinkerGraphStep(vertex,[name.eq(alice)]),
        //           VertexStep(OUT,[rates],vertex), NoOpBarrierStep(2500),
        //           VertexStep(OUT,[rates],vertex), NoOpBarrierStep(2500),
        //           PropertiesStep([name],value),
        //           RangeGlobalStep(0,10)]
        GraphTraversal allV = g.V();
        GraphTraversal hasName = allV.has("name", "alice");//1. 此处在什么地方过滤
        GraphTraversal outCreated = hasName.repeat(__.out("rates")).times(2);//2. 同样下推到什么地方
        GraphTraversal valueName = outCreated.values("name");
        GraphTraversal outLimit = valueName.limit(10);
        //gremlin explain()
//        TraversalExplanation explain = outLimit.explain();
//        System.out.println(explain.toString());
        System.out.println(outLimit.next());

        graph.close();
    }
}