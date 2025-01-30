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

    private static final String SALE_REQUEST_9876543211 = ResourceFixture.getContentFromResourceJson("/fixtures/SaleRequest-987654321-1.json");

    private static final Map<MessagingDataTable, String> jsons;
    private static final Map<MessagingDataTable, String> unusedJsons;

    static {
        jsons = new HashMap<>();

        jsons.put(MessagingDataTable.builder().queueName("authorize-sale-queue").jsonKey("987654321-1").build(), mergeJsons(SALE_REQUEST_9876543211));

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