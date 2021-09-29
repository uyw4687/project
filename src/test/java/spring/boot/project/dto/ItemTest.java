package spring.boot.project.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    @Test
    public void set_and_get() {
        String name = "itemName";
        int amount = 54321;
        ItemDto item = new ItemDto(name, amount);

        assertThat(item.getName()).isEqualTo(name);
        assertThat(item.getAmount()).isEqualTo(amount);
    }

}
