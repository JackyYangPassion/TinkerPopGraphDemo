package com.graph.hgraphdb;

import io.hgraphdb.ElementType;
import io.hgraphdb.HBaseGraph;
import io.hgraphdb.HBaseGraphConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.GraphFactory;

import java.time.LocalDate;
import java.util.Iterator;

/**
 * ref: https://github.com/rayokota/hgraphdb
 * 1. 基本点边写入
 * 2. 构建索引
 * 3. 查询
 */
public class Main {
    public static void main(String[] args) {
        int numRegionServers = 1;
        Configuration cfg = new HBaseGraphConfiguration()
                .setInstanceType(HBaseGraphConfiguration.InstanceType.DISTRIBUTED)
                .setGraphNamespace("mygraph")
                .setCreateTables(true)
                .setRegionCount(numRegionServers)
                .set("hbase.zookeeper.quorum", "127.0.0.1")
                .set("zookeeper.znode.parent", "/hbase");
        HBaseGraph graph = (HBaseGraph) GraphFactory.open(cfg);

        //增加点边数据
//        Vertex v7 = graph.addVertex(T.id, 7L, T.label, "person", "name", "John");
//        Vertex v8 = graph.addVertex(T.id, 8L, T.label, "person", "name", "Sally");
//        v7.addEdge("knows", v8, T.id, "edge2", "since", LocalDate.now());

        //构建二级索引
//        graph.createIndex(ElementType.VERTEX, "person", "name");
//        graph.createIndex(ElementType.EDGE, "knows", "since");


        //查询点边数据
        Iterator<Vertex> itVertices = graph.vertices();
        while (itVertices.hasNext()) {
            Vertex v = itVertices.next();
            System.out.println(v.id() + ":" + v.label());
        }

        Iterator<Edge> itEdges = graph.edges();
        while (itEdges.hasNext()) {
            Edge e = itEdges.next();
            System.out.println(e.id() + ":" + e.label() + ":" + e.inVertex().id() + ":" + e.outVertex().id());
        }


        graph.close();
        System.exit(0);
    }
}