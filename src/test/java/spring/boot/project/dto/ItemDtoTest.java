package spring.boot.project.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemDtoTest {

    @Test
    public void set_and_get() {
        String name = "qwert";
        int amount = 123123;

        ItemDto item = new ItemDto(name, amount);
        assertThat(item.getAmount()).isEqualTo(name);
        assertThat(item.getAmount()).isEqualTo(amount);
    }

}
