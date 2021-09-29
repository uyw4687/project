package spring.boot.project.ctrler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import spring.boot.project.config.auth.SecurityConfig;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ItemCtrler.class,
        excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class))
public class ItemCtrlerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "GUEST")
    public void item_gets_and_gives() throws Exception {

        String name = "abc";
        int amount = 123;

        mvc.perform(
                get("/item")
                        .param("name", name)
                        .param("amount", String.valueOf(amount))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

    }

}
