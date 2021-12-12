package ism.ase.ro.sqma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserWithPassword extends User {

    @NotNull
    private String passwordHash;

    public static UserWithPassword build(@NotNull @Valid User user, @NotNull String passwordHash) {
        UserWithPassword withPassword = new UserWithPassword();
        withPassword.setUsername(user.getUsername());
        withPassword.setName(user.getName());
        withPassword.setPasswordHash(passwordHash);
        return withPassword;
    }

}
