package swc3.demowebshop.DTOs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class TutorialDto {
    private long id;
    private String description;
    private Boolean published;
    private String title;
}