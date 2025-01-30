package br.com.alc.ecommerce.checkout.infrastructure.testcontainers;

public interface ContainerManager {

    void start();

    void stop();

    void restart();

    boolean isRunning();

}