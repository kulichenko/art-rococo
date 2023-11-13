package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<T> {
    private List<T> content;
    private Paging pageable;

    private boolean last;
    private Integer totalElements;
    private Integer totalPages;
    private Integer size;
    private Integer number;
    private Sort sort;
    private boolean first;
    private Integer numberOfElements;
    private boolean empty;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Paging {


        private Integer pageSize;
        private Integer pageNumber;
        private Sort sort;
        private Integer offset;
        private Boolean paged;
        private Boolean unpaged;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Sort {
        Boolean empty;
        Boolean sorted;
        Boolean unsorted;
    }
}