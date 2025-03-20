package com.lsh.scheduler.common.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NotNull
@Builder
public class ListResponse<T> {
    private long totalElements;
    private int totalPages;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    @Builder.Default
    private List<T> content = new ArrayList<>();

    public static <T> ListResponse<T> fromPage(Page<T> page) {
        return ListResponse.<T>builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNextPage(page.hasNext())
                .hasPreviousPage(page.hasPrevious())
                .content(page.getContent())
                .build();
    }
}
