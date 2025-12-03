package dev.carlos.soft.pizzabyte.storage;

import dev.carlos.soft.pizzabyte.domain.StockItem;
import dev.carlos.soft.pizzabyte.utils.DataUtils;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@ExecuteOn(TaskExecutors.BLOCKING) // I love ASYNC but, I can't use in this project
@Controller("/stock")
@AllArgsConstructor
public class StockController {

    protected final StockRepository repository;

    @Get("/{id}")
    public Optional<StockItem> getById(@PathVariable long id) {
        return repository.findById(id);
    }

    @Get("/list")
    public HttpResponse<List<StockItem>> getAll() {
        try {
            var list = repository.findAll();
            if (list == null || list.isEmpty()) {
                return HttpResponse.notFound();
            }

            return HttpResponse.ok(list);
        } catch (DataAccessException e) {
            return HttpResponse.serverError();
        }
    }

    //Why?
    //This will be usefully
    @Post
    public HttpResponse<StockItem> save(@Body String jsonStock) {
        StockItem item = DataUtils.deserialize(jsonStock, StockItem.class);

        if (item == null) {
            return HttpResponse.badRequest();
        }

        try {
            var savedItem = repository.saveWithException(item);
            return HttpResponse.created(savedItem);
        } catch (DataAccessException e) {
            return HttpResponse.notFound();
        }
    }

    @Put("/{id}")
    public HttpResponse<StockItem> update(@PathVariable long id, @Body String jsonStock) {
        StockItem item = DataUtils.deserialize(jsonStock, StockItem.class);

        if (item == null) {
            return HttpResponse.badRequest();
        }

        if (!repository.existsById(id)) {
            return HttpResponse.notFound();
        }

        item.setLastUpdate(System.currentTimeMillis());

        StockItem updatedItem = repository.update(item);

        return HttpResponse.created(updatedItem);
    }


}