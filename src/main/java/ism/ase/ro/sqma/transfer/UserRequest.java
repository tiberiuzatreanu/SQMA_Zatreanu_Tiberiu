package ism.ase.ro.sqma.transfer;

import ism.ase.ro.sqma.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends User {

    @NotNull
    private String password;

    public static UserRequest build(@NotNull String username, @NotNull String name, String password) {
        UserRequest request = new UserRequest();
        request.setUsername(username);
        request.setName(name);
        request.setPassword(password);
        return request;
    }

}
