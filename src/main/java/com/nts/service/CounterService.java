package com.nts.service;

import com.nts.entity.Counter;

import java.util.Map;

public interface CounterService {
    Map addCounter(Counter counter);

    Map removeCounter(Counter counter);

    Map updateCounter(Counter counter);

    Map show(String id, String uid);
}
