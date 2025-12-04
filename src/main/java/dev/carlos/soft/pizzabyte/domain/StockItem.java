package dev.carlos.soft.pizzabyte.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Serdeable
@Introspected
@MappedEntity
public class StockItem {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Long id;

    private String namespace;

    private int amount;

    private long lastUpdate;

    @JsonCreator
    public StockItem(
            @JsonProperty("namespace") String namespace,
            @JsonProperty("amount") int amount,
            @JsonProperty("lastUpdate") long lastUpdate
    ) {
        this.namespace = namespace;
        this.amount = amount;
        this.lastUpdate = lastUpdate;
    }
}

