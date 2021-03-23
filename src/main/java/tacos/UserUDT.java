package tacos;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@UserDefinedType("user")
public class UserUDT {

    private final String username;
    private final String fullName;
    private final String phoneNumber;
}
