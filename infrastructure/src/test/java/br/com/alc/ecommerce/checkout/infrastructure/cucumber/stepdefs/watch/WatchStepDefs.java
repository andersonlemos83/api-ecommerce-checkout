package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.watch;

import br.com.alc.ecommerce.checkout.core.service.watch.stub.VirtualWatchService;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.watch.WatchDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.E;

import java.util.List;

public class WatchStepDefs extends StepDefs {

    @E("^que o sistema seja executado na seguinte data e hora$")
    public void queOhSistemaSejaExecutadoNasSeguintesDataIhHora(List<WatchDataTable> watchDataTableList) {
        watchDataTableList.forEach(VirtualWatchService::createDateAndTime);
    }
}