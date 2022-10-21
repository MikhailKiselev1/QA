package data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataResource {

    public DataResource(){
        super();
    }

    private Integer id;
    private String name;
    private Integer year;
    private String color;

    @JsonProperty("pantone_value")
    private String pantoneValue;


}
