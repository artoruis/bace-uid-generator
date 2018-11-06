package com.bace.uid.worker;

import com.bace.uid.exception.UidGenerateException;
import org.apache.commons.lang3.ArrayUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Payne.Liu
 * @version 1.0
 * @description TODO
 * @create 2018-11-06 14:33
 * @see
 */
public class KubernetesWorkerIdAssigner implements WorkerIdAssigner{

    private int kubeClusterId; // TODO in this way, we trust the kube clusters r configured well...

    private int ipAddr;

    public KubernetesWorkerIdAssigner(int kubeClusterId) throws UnknownHostException {
        InetAddress localHost = Inet4Address.getLocalHost();
        byte[] address = localHost.getAddress();
        if (ArrayUtils.isNotEmpty(address)) {
            this.ipAddr = (address[2]<<8)|address[3];
        } else {
            throw new UidGenerateException("cannot find local ip addr");
        }
        if (kubeClusterId <0 || kubeClusterId > 63) {
            throw new UidGenerateException("kubernetes cluster id is out of limit[0-63]");
        }
        this.kubeClusterId = kubeClusterId;
    }

    @Override
    public long assignWorkerId() {
        return (kubeClusterId << 16) | ipAddr;
    }



}
