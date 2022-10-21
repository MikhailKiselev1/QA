package data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Resource {
    public Resource() {
        super();
    }

    private Integer page;

    @JsonProperty("per_page")
    private Integer perPage;

    private Integer total;

    @JsonProperty("total_pages")
    private Integer totalPages;

    private List<DataResource> data;
    private Support support;
}
