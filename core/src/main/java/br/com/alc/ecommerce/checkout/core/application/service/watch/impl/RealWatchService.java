package br.com.alc.ecommerce.checkout.core.application.service.watch.impl;

import br.com.alc.ecommerce.checkout.core.application.service.watch.WatchService;

import java.time.LocalDateTime;

public class RealWatchService implements WatchService {

    @Override
    public LocalDateTime nowLocalDateTime() {
        return LocalDateTime.now();
    }
}