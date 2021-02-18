package ua.pivik.testCB;

/**
 * @autor Alexander Pivovarov
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.pivik.testCB.domain.Recipe;
import ua.pivik.testCB.repos.RecipeRepo;

import java.util.Date;
import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private RecipeRepo recipeRepo;

/*    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }*/

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Recipe> recipes = recipeRepo.findAll();

        model.put("recipe", recipes);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String text, Map<String, Object> model) {
        Recipe recipe = new Recipe(text, new Date(), null, null);
        recipeRepo.save(recipe);

        Iterable<Recipe> recipes = recipeRepo.findAll();
        model.put("recipe", recipes);

        return "main";
    }
}
