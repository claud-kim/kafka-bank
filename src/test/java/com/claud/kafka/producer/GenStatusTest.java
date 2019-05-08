package com.claud.kafka.producer;

import com.claud.kafka.GenStatus;
import com.claud.kafka.producer.vo.ActionType;
import org.junit.Test;

import static com.claud.kafka.producer.vo.ActionType.NORMAL;
import static org.junit.Assert.assertEquals;

public class GenStatusTest {

    @Test
    public void testTranferNextStatus() {

        assertEquals(ActionType.JOIN, GenStatus.tranferNextStatus(ActionType.SESSION_NULL));

        assertEquals(ActionType.OPEN, GenStatus.tranferNextStatus(ActionType.JOIN));
        assertEquals(ActionType.SESSION_ON, GenStatus.tranferNextStatus(ActionType.OPEN));
        assertEquals(NORMAL, GenStatus.tranferNextStatus(ActionType.SESSION_ON));
        assertEquals(ActionType.SESSION_ON, GenStatus.tranferNextStatus(NORMAL));
        assertEquals(NORMAL, GenStatus.tranferNextStatus(ActionType.SESSION_ON));

    }
}
