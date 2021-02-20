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

import java.util.*;

import static java.util.Comparator.comparing;

@Controller
public class CBController {
    final Comparator<Recipe>comparator = comparing(Recipe::getName);

    @Autowired
    private RecipeRepo recipeRepo;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        Iterable<Recipe> recipes = recipeRepo.findAll();
        ((List<Recipe>) recipes).sort(comparator);
        model.put("recipes", recipes);
        return "main";
    }

    @GetMapping("newrecipe")
    public String newr() {
        return "newrecipe";
    }

    @PostMapping("newrecipe")
    public String add(@RequestParam(required = true) String name,
                            @RequestParam(required = true) String text,
                            Map<String, Object> model) {
        if (name != null && !name.isEmpty() && text != null && !text.isEmpty()) {
            Recipe recipe = new Recipe(name, text, new Date(), null, null);
            recipeRepo.save(recipe);
            return "main";
        }

        Iterable<Recipe> recipes = recipeRepo.findAll();
        ((List<Recipe>) recipes).sort(comparator);
        model.put("recipes", recipes);
        /*model.put("qtty", ((List<Recipe>) recipes).size());*/
        return "newrecipe";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Recipe> recipes;
        if(filter != null || filter.isEmpty()) {
            recipes = recipeRepo.findByNameContaining(filter);
        } else {
            recipes = recipeRepo.findAll();
        }
        ((List<Recipe>) recipes).sort(comparator);
        model.put("recipes", recipes);
        //model.put("qtty", ((List<Recipe>) recipes).size());
        return "main";
    }


}
