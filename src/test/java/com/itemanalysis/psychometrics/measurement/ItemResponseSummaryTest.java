package com.itemanalysis.psychometrics.measurement;

import com.itemanalysis.psychometrics.data.VariableName;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Copyright 2016 J. Patrick Meyer
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ItemResponseSummaryTest {


    @Test
    public void incrementLettersTest(){

        ItemResponseSummary irs = new ItemResponseSummary(new VariableName("item1"));

        irs.increment("A");
        irs.increment("A");
        irs.increment("A");
        irs.increment("A");

        irs.increment("B");
        irs.increment("B");

        irs.increment("C");
        irs.increment("C");
        irs.increment("C");
        irs.increment("C");
        irs.increment("C");
        irs.increment("C");

        assertEquals("Test freq response of A", 4, irs.getFrequencyAt("A"), 1e-15);
        assertEquals("Test freq response of B", 2, irs.getFrequencyAt("B"), 1e-15);
        assertEquals("Test freq response of C", 6, irs.getFrequencyAt("C"), 1e-15);

        assertEquals("Test prop response of A", 0.333333333333333, irs.getProportionAt("A"), 1e-15);
        assertEquals("Test prop response of B", 0.166666666666666, irs.getProportionAt("B"), 1e-15);
        assertEquals("Test prop response of C", 0.500000000000000, irs.getProportionAt("C"), 1e-15);

    }

    @Test
    public void weightedIncrementLettersTest(){

        ItemResponseSummary irs = new ItemResponseSummary(new VariableName("item1"));

        irs.increment("A", 4);
        irs.increment("B", 2);
        irs.increment("C", 6);

        assertEquals("Test freq response of A", 4, irs.getFrequencyAt("A"), 1e-15);
        assertEquals("Test freq response of B", 2, irs.getFrequencyAt("B"), 1e-15);
        assertEquals("Test freq response of C", 6, irs.getFrequencyAt("C"), 1e-15);

        assertEquals("Test prop response of A", 0.333333333333333, irs.getProportionAt("A"), 1e-15);
        assertEquals("Test prop response of B", 0.166666666666666, irs.getProportionAt("B"), 1e-15);
        assertEquals("Test prop response of C", 0.500000000000000, irs.getProportionAt("C"), 1e-15);

    }

    @Test
    public void incrementNumbersTest(){

        ItemResponseSummary irs = new ItemResponseSummary(new VariableName("item1"));

        irs.increment(1);
        irs.increment(1);
        irs.increment(1);
        irs.increment(1);

        irs.increment(2);
        irs.increment(2);

        irs.increment(3);
        irs.increment(3);
        irs.increment(3);
        irs.increment(3);
        irs.increment(3);
        irs.increment(3);

        assertEquals("Test freq response of 1", 4, irs.getFrequencyAt(1.0), 1e-15);
        assertEquals("Test freq response of 2", 2, irs.getFrequencyAt(2), 1e-15);
        assertEquals("Test freq response of 3", 6, irs.getFrequencyAt(3.0), 1e-15);

        assertEquals("Test prop response of 1", 0.333333333333333, irs.getProportionAt(1), 1e-15);
        assertEquals("Test prop response of 2", 0.166666666666666, irs.getProportionAt(2.00), 1e-15);
        assertEquals("Test prop response of 3", 0.500000000000000, irs.getProportionAt(3), 1e-15);

    }

    @Test
    public void weightedIncrementNumbersTest(){

        ItemResponseSummary irs = new ItemResponseSummary(new VariableName("item1"));

        irs.increment(1, 4);
        irs.increment(2, 2);
        irs.increment(3, 6);

        assertEquals("Test freq response of 1", 4, irs.getFrequencyAt(1.0), 1e-15);
        assertEquals("Test freq response of 2", 2, irs.getFrequencyAt(2), 1e-15);
        assertEquals("Test freq response of 3", 6, irs.getFrequencyAt(3.0), 1e-15);

        assertEquals("Test prop response of 1", 0.333333333333333, irs.getProportionAt(1), 1e-15);
        assertEquals("Test prop response of 2", 0.166666666666666, irs.getProportionAt(2.00), 1e-15);
        assertEquals("Test prop response of 3", 0.500000000000000, irs.getProportionAt(3), 1e-15);

    }

    @Test
    public void itemStatisticsTest(){
        ItemResponseSummary irs = new ItemResponseSummary(new VariableName("item1"));

        irs.increment(1);
        irs.increment(1);
        irs.increment(1);
        irs.increment(1);
        irs.increment(1);
        irs.increment(1);

        irs.increment(0);
        irs.increment(0);
        irs.increment(0);
        irs.increment(0);

        assertEquals("Test of sample mean", 0.6, irs.mean(), 1e-15);
        assertEquals("Test of sample variance", 0.2666666666666667, irs.sampleVariance(), 1e-15);
        assertEquals("Test of sample mean", 0.5163977794943223, irs.sampleStandardDeviation(), 1e-15);
    }

    @Test
    public void weightedItemStatisticsTest(){
        ItemResponseSummary irs = new ItemResponseSummary(new VariableName("item1"));

        irs.increment(1, 6);
        irs.increment(0, 4);

        assertEquals("Test of sample mean", 0.6, irs.mean(), 1e-15);
        assertEquals("Test of sample variance", 0.2666666666666667, irs.sampleVariance(), 1e-15);
        assertEquals("Test of sample mean", 0.5163977794943223, irs.sampleStandardDeviation(), 1e-15);
    }

    @Test
    public void itemStatisticsTest2(){
        ItemResponseSummary irs = new ItemResponseSummary(new VariableName("item1"));

        irs.increment(1);
        irs.increment(2.5);
        irs.increment(10.79);
        irs.increment(-1.09);
        irs.increment(2.089);
        irs.increment(-0.009);

        assertEquals("Test of sample mean", 2.546666666666667, irs.mean(), 1e-15);
        assertEquals("Test of sample variance", 18.062627066666664, irs.sampleVariance(), 1e-15);
        assertEquals("Test of sample mean", 4.250014948993317, irs.sampleStandardDeviation(), 1e-15);
    }

    @Test
    public void responseScoreTest(){
        ItemResponseSummary irs = new ItemResponseSummary(new VariableName("item1"));
        irs.setScoreAt("A", 1.0);
        irs.setScoreAt("B", 0);
        irs.setScoreAt("C", 0.0);

        irs.increment("A", 4);
        irs.increment("B", 2);
        irs.increment("C", 6);

        assertEquals("Test scored mean", irs.getProportionAt("A"), irs.mean(), 1e-15);
        assertEquals("Test scored remainder mean", (irs.getProportionAt("B")+irs.getProportionAt("C")), 1.0-irs.mean(), 1e-15);

        System.out.println(irs.toString());
        System.out.println();
        System.out.println(irs.getOutputString());
        System.out.println(irs.getOutputStringAt("A"));
        System.out.println(irs.getOutputStringAt("B"));
        System.out.println(irs.getOutputStringAt("C"));
        System.out.println(irs.getTotalFrequencyOutputString());

    }

    @Test
    public void itemNameTest(){
        ItemResponseSummary irs = new ItemResponseSummary(new VariableName("item1"));
        assertEquals("Name test", "item1", irs.getName().toString());

    }

}