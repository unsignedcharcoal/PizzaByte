package dev.carlos.soft.pizzabyte.stock;

import dev.carlos.soft.pizzabyte.domain.StockItem;
import dev.carlos.soft.pizzabyte.utils.DataUtils;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.View;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExecuteOn(TaskExecutors.BLOCKING) // I love ASYNC but, I can't use in this project
@Controller("/stock")
@AllArgsConstructor
public class StockController {

    @Inject
    protected final StockService service;

    @View("stock/view-single")
    @Get("/{id}")
    public Map<String, StockItem> getById(@PathVariable long id) {
        return Collections.singletonMap("stockItem", service.findById(id).orElse(null));
    }

    @Get("/list")
    public HttpResponse<List<StockItem>> getAll() {
        try {
            var list = service.findAll();
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
    public HttpResponse<StockItem> save(@Body StockItem item) {
        try {
            var savedItem = service.save(item);
            return HttpResponse.created(savedItem);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }


    @Put("/{id}")
    public HttpResponse<StockItem> update(@PathVariable long id, @Body String jsonStock) {
        StockItem item = DataUtils.deserialize(jsonStock, StockItem.class);

        if (item == null) {
            return HttpResponse.badRequest();
        }

        if (!service.existsById(id)) {
            return HttpResponse.notFound();
        }

        StockItem updatedItem = service.update(item);

        return HttpResponse.created(updatedItem);
    }


}