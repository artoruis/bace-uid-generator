package com.bace.uid;

import com.bace.uid.impl.DefaultUidGenerator;
import com.bace.uid.worker.KubernetesWorkerIdAssigner;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.junit.Test;

import java.io.IOException;
import java.io.Writer;
import java.util.stream.IntStream;

/**
 * @author Payne.Liu
 * @version 1.0
 * @description TODO
 * @create 2018-11-06 20:42
 * @see
 */
public class IPAddressTest {

    @Test
    public static void main(String[] args) throws Exception {

        KubernetesWorkerIdAssigner kubernetesWorkerIdAssigner = new KubernetesWorkerIdAssigner(11);
        long workerId = kubernetesWorkerIdAssigner.assignWorkerId();
        System.out.println(workerId);
        System.out.println(Long.toBinaryString(workerId));


        DefaultUidGenerator defaultUidGenerator = new DefaultUidGenerator(kubernetesWorkerIdAssigner, 28, 22, 13);


        IntStream.range(1, 1000).parallel().forEach(value -> {
            long uid = defaultUidGenerator.getUID();
            System.out.println(uid);
            System.out.println(Long.toBinaryString(uid));
            String hex = Long.toHexString(uid);
            System.out.println();
            try {
                Thread.sleep(RandomUtils.nextInt(0, 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
