package swc3.demowebshop.jdbc.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class TutorialPOJO {
    @JsonIgnore
    private long id;
    private String description;
    private Boolean published;
    private String title;
}
