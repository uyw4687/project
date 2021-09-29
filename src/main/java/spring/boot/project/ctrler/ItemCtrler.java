package spring.boot.project.ctrler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.project.dto.ItemDto;

@RestController
public class ItemCtrler {

    @GetMapping("/item")
    public ItemDto giveItem(@RequestParam("name") String name,
                            @RequestParam("amount") String amount) {
        return new ItemDto(name, Integer.parseInt(amount));
    }

}
