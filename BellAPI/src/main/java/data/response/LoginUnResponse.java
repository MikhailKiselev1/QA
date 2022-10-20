package data.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUnResponse {

    private String error;

    public LoginUnResponse(){
        super();
    }
}
