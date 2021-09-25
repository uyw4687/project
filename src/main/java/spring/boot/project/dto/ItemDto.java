package spring.boot.project.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemDto {

    private final String name;
    private final int amount;

}
