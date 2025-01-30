package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.configurator.EnvironmentConfigurator;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.support.TransitionDataTable;
import org.springframework.beans.factory.annotation.Autowired;

public class StepDefs {

    protected static TransitionDataTable transitionDataTable = TransitionDataTable.builder().build();

    @Autowired
    private EnvironmentConfigurator environmentConfigurator;

    public void initializeContext() {
        environmentConfigurator.configureEnvironment();
        transitionDataTable = TransitionDataTable.builder().build();
    }
}