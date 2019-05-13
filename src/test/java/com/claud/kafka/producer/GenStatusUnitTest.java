package com.claud.kafka.producer;

import com.claud.kafka.producer.vo.gen.GenAccountInfo;
import com.claud.kafka.producer.vo.log.ActionType;
import org.junit.Test;

import static com.claud.kafka.producer.vo.log.ActionType.NORMAL;
import static org.junit.Assert.assertEquals;

public class GenStatusUnitTest {

    @Test
    public void testTransferNextStatus() {
        GenAccountInfo info = new GenAccountInfo(1, "test", "name",
                "19811009", 0, null, null);


        assertEquals(ActionType.REGISTER, info.transferNextStatus(ActionType.SESSION_NULL));

        assertEquals(ActionType.ACCOUNT_OPEN, info.transferNextStatus(ActionType.REGISTER));
        assertEquals(ActionType.SESSION_ON, info.transferNextStatus(ActionType.ACCOUNT_OPEN));
        assertEquals(NORMAL, info.transferNextStatus(ActionType.SESSION_ON));
        assertEquals(ActionType.SESSION_ON, info.transferNextStatus(NORMAL));
        assertEquals(NORMAL, info.transferNextStatus(ActionType.SESSION_ON));

    }
}
