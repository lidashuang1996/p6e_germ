package com.p6e.germ.video.actuator;

import com.p6e.germ.video.RtmpActuator;
import com.p6e.germ.video.RtmpContext;
import com.p6e.germ.video.RtmpMessage;

/**
 * @author lidashuang
 * @version 1.0
 */
public class RtmpDefaultActuator implements RtmpActuator {

    @Override
    public void execute(RtmpContext context, RtmpMessage message) {
        System.out.println(message);
    }
}
