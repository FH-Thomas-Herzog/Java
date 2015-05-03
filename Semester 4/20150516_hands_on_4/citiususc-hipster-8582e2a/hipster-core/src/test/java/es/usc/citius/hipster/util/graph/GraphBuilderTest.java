/*
 * Copyright 2014 CITIUS <http://citius.usc.es>, University of Santiago de Compostela.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package es.usc.citius.hipster.util.graph;


import com.google.common.collect.Sets;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GraphBuilderTest {
    private static HipsterDirectedGraph<String, Double> testGraph;

    @BeforeClass
    public static void setUp() throws Exception {

        testGraph = GraphBuilder.create()
                .connect("A").to("B").withEdge(4d)
                .connect("A").to("C").withEdge(2d)
                .connect("B").to("C").withEdge(5d)
                .connect("B").to("D").withEdge(10d)
                .connect("C").to("E").withEdge(3d)
                .connect("D").to("F").withEdge(11d)
                .connect("E").to("D").withEdge(4d)
                .buildDirectedGraph();
    }

    @Test
    public void testVertices() {
        Set<String> vertices = new HashSet<String>(Arrays.asList("A","B","C","D","E","F"));
        assertEquals(vertices, testGraph.vertices());
    }

    @Test
    public void testEdges() {
        Set<Double> expectedValues = new HashSet<Double>(Arrays.asList(4d, 2d, 5d, 10d, 3d, 11d, 4d));
        Set<Double> values = new HashSet<Double>();
        for(GraphEdge<String,Double> edge : testGraph.edges()){
            values.add(edge.getEdgeValue());
        }
        assertEquals(7, Sets.newHashSet(testGraph.edges()).size());
        assertEquals(expectedValues, values);
    }

    @Test
    public void testIncomingEdges(){
        Set<GraphEdge<String,Double>> edges = Sets.newHashSet(testGraph.incomingEdgesOf("D"));
        Set<Double> values = new HashSet<Double>();
        for(GraphEdge<String,Double> e : edges){
            values.add(e.getEdgeValue());
        }
        assertEquals(2, edges.size());
        assertEquals(values, new HashSet<Double>(Arrays.asList(4.0d, 10.0d)));
    }

    @Test
    public void testOutgoingEdges(){
        Set<GraphEdge<String,Double>> edges = Sets.newHashSet(testGraph.outgoingEdgesOf("B"));
        Set<Double> values = new HashSet<Double>();
        for(GraphEdge<String,Double> e : edges){
            values.add(e.getEdgeValue());
        }
        assertEquals(2, edges.size());
        assertEquals(values, new HashSet<Double>(Arrays.asList(5.0d, 10.0d)));
    }


}
