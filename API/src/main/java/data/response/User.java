package data.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import data.response.DataUser;
import data.response.Support;
import lombok.Data;

import java.util.List;

@Data
public class User {

    private Integer page;

    @JsonProperty("per_page")
    private Integer perPage;
    private Integer total;

    @JsonProperty("total_pages")
    private Integer totalPages;

    private List<DataUser> data;
    private Support support;


}
