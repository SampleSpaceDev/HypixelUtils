package dev.samplespace.hypixelutils.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import dev.samplespace.hypixelutils.util.rotation.MapPool;
import dev.samplespace.hypixelutils.util.rotation.BedwarsMap;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class APIInteraction {

    private static final APIInteraction INSTANCE = new APIInteraction();

    public static APIInteraction get() {
        return INSTANCE;
    }

    private static final String BASE_URL = "https://mapapi.cecer1.com";

    private final OkHttpClient client;
    private final JsonParser jsonParser;
    private final Map<MapPool, List<BedwarsMap>> mapCache;

    public APIInteraction() {
        this.client = new OkHttpClient();
        this.jsonParser = new JsonParser();
        this.mapCache = new EnumMap<>(MapPool.class);

        for (MapPool pool : MapPool.VALUES) {
            this.mapCache.put(pool, Collections.emptyList());
        }
    }

    public List<BedwarsMap> getMapCache(MapPool pool) {
        return this.mapCache.get(pool);
    }

    public CompletableFuture<Void> refreshMapCache(MapPool... pools) {
        //noinspection rawtypes
        CompletableFuture[] futures = new CompletableFuture[pools.length];
        for (int i = 0; i < futures.length; i++) {
            futures[i] = this.getMapPool(pools[i]);
        }
        return CompletableFuture.allOf(futures).thenAccept(aVoid -> {
            for (int i = 0; i < futures.length; i++) {
                //noinspection unchecked
                this.mapCache.put(pools[i], (List<BedwarsMap>) futures[i].join());
            }
        });
    }

    private CompletableFuture<List<BedwarsMap>> getMapPool(MapPool pool) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/mappool/" + pool.getId())
                .build();

        return this.asyncRequest(request).thenCompose(response -> {
            ResponseBody body = response.body();
            if (body == null) {
                return CompletableFuture.completedFuture(Collections.emptyList());
            }
            JsonArray mapsArray = this.jsonParser.parse(new JsonReader(body.charStream())).getAsJsonArray();
            //noinspection rawtypes
            CompletableFuture[] futures = new CompletableFuture[mapsArray.size()];
            for (int i = 0; i < futures.length; i++) {
                String mapId = mapsArray.get(i).getAsString();
                futures[i] = this.getMap(mapId);
            }
            return CompletableFuture.allOf(futures).thenApply(aVoid -> {
                List<BedwarsMap> maps = new ArrayList<>();
                for (CompletableFuture<?> future : futures) {
                    maps.add((BedwarsMap) future.join());
                }
                return maps;
            });
        });
    }

    private CompletableFuture<BedwarsMap> getMap(String id) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/map/" + id)
                .build();
        return this.asyncRequest(request).thenApply(response -> {
            ResponseBody body = response.body();
            if (body == null) {
                return null;
            }
            JsonObject object = this.jsonParser.parse(new JsonReader(body.charStream())).getAsJsonObject();
            return new BedwarsMap(
                    object.get("id").getAsString(),
                    MapPool.getById(object.get("pool").getAsString()),
                    object.get("name").getAsString(),
                    object.get("festival").getAsString()
            );
        });
    }

    public CompletableFuture<Instant> getLastRotation() {
        Request request = new Request.Builder()
                .url(BASE_URL + "/rotation/latest")
                .build();
        return this.asyncRequest(request).thenApply(response -> {
            ResponseBody body = response.body();
            if (body == null) {
                return Instant.EPOCH;
            }
            JsonObject object = this.jsonParser.parse(new JsonReader(body.charStream())).getAsJsonObject();
            long timestamp = object.get("timestamp").getAsLong();
            return Instant.ofEpochMilli(timestamp);
        });
    }

    public CompletableFuture<Response> asyncRequest(Request request) {
        CompletableFuture<Response> future = new CompletableFuture<>();
        this.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                future.complete(response);
                response.close();
            }
        });
        return future;
    }
}
