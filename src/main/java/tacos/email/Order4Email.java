package tacos.email;

import lombok.Data;
import tacos.Taco;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order4Email {

    private final String email;
    private List<Taco> tacos = new ArrayList<>();

}
