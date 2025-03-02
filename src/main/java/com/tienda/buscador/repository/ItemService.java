package com.tienda.buscador.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;  // 🔹 Cliente de Elasticsearch correctamente inyectado

    public Map<String, Object> getCategoryFacets() throws IOException {
        SearchRequest request = SearchRequest.of(s -> s
            .index("items")  // 🔹 Asegura que el índice es correcto
            .size(0)  // 🔹 No obtener documentos, solo agregaciones
            .aggregations("categorias", a -> a.terms(t -> t.field("category")))
        );

        SearchResponse<Void> response = elasticsearchClient.search(request, Void.class);

        // 🔹 Imprimir respuesta en consola para depuración
        System.out.println("Respuesta de Elasticsearch: " + response.aggregations());

        // 🔹 Extraer la agregación "categorias" correctamente
        Aggregate categoriasAgg = response.aggregations().get("categorias");

        if (categoriasAgg == null || !categoriasAgg.isSterms()) {
            return Map.of("categorias", List.of());  // 🔹 Devuelve JSON vacío si no hay datos
        }

        // 🔹 Obtener los `buckets` correctamente
        StringTermsAggregate termsAggregate = categoriasAgg.sterms();
        List<StringTermsBucket> buckets = termsAggregate.buckets().array();

        if (buckets == null || buckets.isEmpty()) {
            return Map.of("categorias", List.of());  // 🔹 Devuelve JSON vacío si no hay datos
        }

     // 🔹 Convertir los `buckets` en una lista JSON legible con conversión segura de tipos
        List<Map<String, Object>> categorias = buckets.stream()
            .map(bucket -> Map.of(
                "key", bucket.key().stringValue(),  // 🔹 Convertir la clave a String
                "doc_count", (Number) bucket.docCount()  // 🔹 Forzar `doc_count` como un `Number`
            ))
            .map(m -> (Map<String, Object>) (Map<?, ?>) m) // 🔹 Forzar la conversión a `Map<String, Object>`
            .collect(Collectors.toList());


        return Map.of("categorias", categorias);
    }
}
