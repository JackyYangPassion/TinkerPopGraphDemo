package com.graph.basicalgostructure;

public class LinkList {
    //你是一名程序员，请用java 实现双向链表遍历。
    public static void main(String[] args) {
        LinkList lInkList = new LinkList();
        Node head = lInkList.createLinkList();
        lInkList.traverseLinkList(head);
    }

    //创建双向链表
    public Node createLinkList() {
        Node head = new Node(-1);
        Node p = head;
        for (int i = 0; i < 10; i++) {
            Node node = new Node(i);
            p.next = node;
            node.prev = p;
            p = node;
        }
        return head;
    }

    //遍历双向链表
    public void traverseLinkList(Node head) {
        Node p = head.next;
        while (p != null) {
            System.out.println(p.data);
            p = p.next;
        }
    }
    //请创建Node 类
    class Node {
        private int data;
        private Node prev;
        private Node next;

        public Node(int data) {
            this.data = data;
        }
    }
}
