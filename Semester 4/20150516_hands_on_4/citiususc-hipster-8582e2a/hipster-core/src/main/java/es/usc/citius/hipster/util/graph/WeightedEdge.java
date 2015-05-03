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


/**
 * Dumb class that can be used to generate unique weighted edges for a graph.
 * Do not use this for production code!
 * 
 * @author Pablo Rodríguez Mier
 */
public class WeightedEdge extends UniqueEdge<Double> {

    public WeightedEdge(Double value) {
        super(value);
    }

    public static WeightedEdge create(Double value){
        return new WeightedEdge(value);
    }

    @Override
    public String toString() {
        return "WeightedEdge{" +
                "value=" + this.getValue() +
                ", edgeId='" + this.getEdgeId() + '\'' +
                '}';
    }

}
