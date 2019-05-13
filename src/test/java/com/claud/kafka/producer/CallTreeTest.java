package com.claud.kafka.producer;

import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.callback.calltree.CallTree;
import org.javasimon.callback.calltree.CallTreeNode;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class CallTreeTest {

    @BeforeClass
    public static void buildUp() {

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CallTreeTest.class);
    private static final String NAME_PREFIX = "org.javasimon.test.";

    /**
     * Call tree under test.
     */
    private CallTree callTree = new CallTree(null) {
        @Override
        public void onRootStopwatchStart(CallTreeNode rootNode, Split split) {
            CallTreeTest.this.rootTreeNode = rootNode;
        }
    };

    /**
     * Root call tree node.
     */
    private CallTreeNode rootTreeNode;

    /**
     * Start a stopwatch and append it call stack.
     */
    private Split startStopwatch(String name) {
        Split split = SimonManager.getStopwatch(NAME_PREFIX + name).start();
        callTree.onStopwatchStart(split);
        return split;
    }

    /**
     * Stop a stopwatch and remove it from call stack.
     */
    private void stopStopwatch(Split split) {
        split.stop();
        callTree.onStopwatchStop(split);
    }

    /**
     * Test call tree.
     */
    @Test
    public void testStopwatchStartStop() {
        // Initialisation
        SimonManager.clear();
        // Execute scenario
        // Special indentation represents call tree
        Split rootSplit = startStopwatch("root");
        Split child1Split = startStopwatch("child1");
        Split child11Split = startStopwatch("child1.m1");
        stopStopwatch(child11Split);
        Split child12Split = startStopwatch("child1.m2");
        stopStopwatch(child12Split);
        stopStopwatch(child1Split);
        Split child2Split = startStopwatch("child2");
        Split child21Split = startStopwatch("child2.loop");
        stopStopwatch(child21Split);
        Split child22Split = startStopwatch("child2.loop");
        stopStopwatch(child22Split);
        stopStopwatch(child2Split);
        stopStopwatch(rootSplit);
        // Check result
        assertEquals(rootTreeNode.getChildren().size(), 2);
        assertEquals(rootTreeNode.getSplitCount(), 1);
        CallTreeNode child1Node = rootTreeNode.getChild(NAME_PREFIX + "child1");
        assertEquals(child1Node.getChildren().size(), 2);
        assertEquals(child1Node.getSplitCount(), 1);
        CallTreeNode child11Node = child1Node.getChild(NAME_PREFIX + "child1.m1");
        assertEquals(child11Node.getSplitCount(), 1);
        CallTreeNode child12Node = child1Node.getChild(NAME_PREFIX + "child1.m2");
        assertEquals(child12Node.getSplitCount(), 1);
        CallTreeNode child2Node = rootTreeNode.getChild(NAME_PREFIX + "child2");
        assertEquals(child2Node.getChildren().size(), 1);
        CallTreeNode child21Node = child2Node.getChild(NAME_PREFIX + "child2.loop");
        assertEquals(child21Node.getSplitCount(), 2);
        System.out.println(rootTreeNode.toString());

        System.out.println(SimonManager.getStopwatch("org.javasimon.test.child1").sample().toString());
        System.out.println(SimonManager.getStopwatch("org.javasimon.test.child1").sample().getTotal());

        LOGGER.info(rootTreeNode.toString());
    }

}
