package com.claud.kafka.producer;

import com.claud.kafka.producer.vo.log.ActionType;
import com.claud.kafka.producer.vo.gen.GenAccountInfo;
import org.junit.Test;

import static com.claud.kafka.producer.vo.log.ActionType.NORMAL;
import static org.junit.Assert.assertEquals;

public class GenStatusTest {

    @Test
    public void testTransferNextStatus() {
        GenAccountInfo info = new GenAccountInfo(1,"test","name",
                "19811009", 0, null, null);


        assertEquals(ActionType.JOIN, info.transferNextStatus(ActionType.SESSION_NULL));

        assertEquals(ActionType.OPEN, info.transferNextStatus(ActionType.JOIN));
        assertEquals(ActionType.SESSION_ON, info.transferNextStatus(ActionType.OPEN));
        assertEquals(NORMAL, info.transferNextStatus(ActionType.SESSION_ON));
        assertEquals(ActionType.SESSION_ON, info.transferNextStatus(NORMAL));
        assertEquals(NORMAL, info.transferNextStatus(ActionType.SESSION_ON));

    }
}
