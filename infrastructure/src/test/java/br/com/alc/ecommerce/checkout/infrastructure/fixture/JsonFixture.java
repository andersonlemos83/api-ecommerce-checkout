package br.com.alc.ecommerce.checkout.infrastructure.fixture;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.messaging.MessagingDataTable;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.joining;

@Log4j2
public final class JsonFixture {

    private static final String SALE_REQUEST_987654321 = ResourceFixture.getContentFromResourceJson("/fixtures/SaleRequest-987654321.json");

    private static final String SALE_CALLBACK_REQUEST_987654322 = ResourceFixture.getContentFromResourceJson("/fixtures/SaleCallbackRequest-987654322.json");
    private static final String SALE_CALLBACK_REQUEST_987654323 = ResourceFixture.getContentFromResourceJson("/fixtures/SaleCallbackRequest-987654323.json");
    private static final String SALE_CALLBACK_REQUEST_987654324 = ResourceFixture.getContentFromResourceJson("/fixtures/SaleCallbackRequest-987654324.json");
    private static final String SALE_CALLBACK_REQUEST_987654325 = ResourceFixture.getContentFromResourceJson("/fixtures/SaleCallbackRequest-987654325.json");

    private static final Map<MessagingDataTable, String> jsons;
    private static final Map<MessagingDataTable, String> unusedJsons;

    static {
        jsons = new HashMap<>();

        jsons.put(MessagingDataTable.builder().queueName("authorize-sale-queue").jsonKey("987654321").build(), mergeJsons(SALE_REQUEST_987654321));

        jsons.put(MessagingDataTable.builder().queueName("sale-callback-queue").jsonKey("987654322").build(), mergeJsons(SALE_CALLBACK_REQUEST_987654322));
        jsons.put(MessagingDataTable.builder().queueName("sale-callback-queue").jsonKey("987654323").build(), mergeJsons(SALE_CALLBACK_REQUEST_987654323));
        jsons.put(MessagingDataTable.builder().queueName("sale-callback-queue").jsonKey("987654324").build(), mergeJsons(SALE_CALLBACK_REQUEST_987654324));
        jsons.put(MessagingDataTable.builder().queueName("sale-callback-queue").jsonKey("987654325").build(), mergeJsons(SALE_CALLBACK_REQUEST_987654325));

        unusedJsons = new HashMap<>(jsons);
    }

    private JsonFixture() {
    }

    public static String oneJson(MessagingDataTable messagingDataTable) {
        unusedJsons.remove(messagingDataTable);
        log.info("Unused JSONs: " + unusedJsons.keySet());
        return jsons.get(messagingDataTable);
    }

    private static String mergeJsons(String... jsons) {
        return Arrays.stream(jsons)
                .sorted(naturalOrder())
                .collect(joining("; ", "", ""));
    }
}