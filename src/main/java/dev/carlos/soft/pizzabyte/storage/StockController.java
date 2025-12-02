package dev.carlos.soft.pizzabyte.storage;

import dev.carlos.soft.pizzabyte.domain.StockItem;
import dev.carlos.soft.pizzabyte.utils.DataUtils;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.AllArgsConstructor;

import java.util.Optional;

@ExecuteOn(TaskExecutors.BLOCKING) // I love ASYNC but, I can't use in this project
@Controller("/stock")
@AllArgsConstructor
public class StockController {

    protected final StockRepository repository;

    @Get("/{id}")
    public Optional<StockItem>  getById(@PathVariable long id) {
        return repository.findById(id);
    }

    //Why?
    //This will be usefully
    @Post
    public HttpResponse<StockItem> save(@Body String jsonStock){
        StockItem item = DataUtils.deserialize(jsonStock, StockItem.class);

        if (item == null) {
            return HttpResponse.badRequest();
        }

        try {
            var savedItem = repository.saveWithException(item);
            return HttpResponse.created(savedItem);
        } catch (DataAccessException e){
            return HttpResponse.notFound();
        }
    }


}