package com.claud.kafka.producer;

import com.claud.kafka.FileUtil;
import com.claud.kafka.JsonUtil;
import com.claud.kafka.producer.vo.BaseVo;
import com.claud.kafka.producer.vo.log.SessionLog;
import com.google.gson.Gson;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileUnitTest {

    @Test
    public void testFileWriteAndRead() {
        String path = "test.txt";
        File file = new File(path);

        if ( file.exists() ) {
            file.delete();
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append("112345678080\n1skfhgfll");

        FileUtil.writeFile(path,buffer.toString());

        assertEquals(true, file.exists());

        List<String> lines = FileUtil.readFile(path);

        assertEquals(buffer.toString().split("\n").length, lines.size());


        buffer = new StringBuffer();
        buffer.append("112345678080\naaa\n");

        FileUtil.writeFile(path,buffer.toString());

        assertEquals(true, file.exists());

        lines = FileUtil.readFile(path);

        assertEquals(buffer.toString().split("\n").length, lines.size());


        System.out.println(file.getAbsolutePath());
        file.delete();
    }

}
