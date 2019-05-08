package com.claud.kafka.producer;

import com.claud.kafka.cosumer.Statis;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatisTest {
    @Test
    public void testStatisMinMax() {

        Statis statis = new Statis();
        assertEquals(Long.MAX_VALUE, statis.getMin());
        assertEquals(Long.MIN_VALUE, statis.getMax());

        statis.updateMinMax(20);
        assertEquals(20, statis.getMin());
        assertEquals(20, statis.getMax());

        statis.updateMinMax(150);
        assertEquals(20, statis.getMin());
        assertEquals(150, statis.getMax());

        statis.updateMinMax(100);
        assertEquals(20, statis.getMin());
        assertEquals(150, statis.getMax());

        statis.updateMinMax(200);
        assertEquals(20, statis.getMin());
        assertEquals(200, statis.getMax());
    }
}
