package com.claud.kafka.producer;

import com.claud.kafka.consumer.vo.Statis;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatisUnitTest {
    @Test
    public void testStatisMinMax() {

        Statis statis = new Statis();
        assertEquals(Integer.MAX_VALUE, statis.getCalMin());
        assertEquals(Integer.MIN_VALUE, statis.getCalMax());

        statis.updateMinMax(20);
        assertEquals(20, statis.getCalMin());
        assertEquals(20, statis.getCalMax());

        statis.updateMinMax(150);
        assertEquals(20, statis.getCalMin());
        assertEquals(150, statis.getCalMax());

        statis.updateMinMax(100);
        assertEquals(20, statis.getCalMin());
        assertEquals(150, statis.getCalMax());

        statis.updateMinMax(200);
        assertEquals(20, statis.getCalMin());
        assertEquals(200, statis.getCalMax());
    }
}
