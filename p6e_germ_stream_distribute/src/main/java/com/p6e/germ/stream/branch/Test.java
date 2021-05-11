package com.p6e.germ.stream.branch;

import com.p6e.germ.stream.branch.client.P6eGermStreamClientSocketDefaultHandler;
import com.p6e.germ.stream.branch.server.P6eGermStreamServerSocketDefaultHandler;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) {

        P6eGermStreamBranch.setClientPort(19000);
        P6eGermStreamBranch.setClientHandler(new P6eGermStreamClientSocketDefaultHandler(""));
        P6eGermStreamBranch.setServerHandler(new P6eGermStreamServerSocketDefaultHandler(""));
        P6eGermStreamBranch.init();
        P6eGermStreamBranch.startup();

    }
}
