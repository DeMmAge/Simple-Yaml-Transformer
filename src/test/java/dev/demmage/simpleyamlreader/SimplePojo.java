package dev.demmage.simpleyamlreader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimplePojo {

    private String string;
    private int integer;
    private int[] integerArray;

}
