package com.julian.commerceauthsecurity.api.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public abstract class PagedRequest {
    @Min(value = 0, message = "Minimum page is 0")
    private final Integer page;
    @Min(value = 10, message = "Minimum size is 10")
    private final Integer size;
    private final String sort;
    private final Sort.Direction direction;

    public PagedRequest(Integer page, Integer size, String sort, String direction) {
        this.page = page != null ? page : 0;
        this.size = size != null ? size : 10;
        this.sort = sort != null ? sort : "id";
        this.direction = direction != null ? Sort.Direction.fromString(direction) : Sort.Direction.ASC;
    }

    public Pageable getPageable() {
        return PageRequest.of(page, size, Sort.by(direction, sort));
    }
}