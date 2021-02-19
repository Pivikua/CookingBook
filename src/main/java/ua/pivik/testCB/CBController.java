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

@Controller
public class CBController {

    @Autowired
    private RecipeRepo recipeRepo;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        Iterable<Recipe> recipes = recipeRepo.findAll();
        model.put("recipes", recipes);
        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam() String name,
                      @RequestParam String text,
                      Map<String, Object> model) {
        Recipe recipe = new Recipe(name, text, new Date(), null, null);
        recipeRepo.save(recipe);

        Iterable<Recipe> recipes = recipeRepo.findAll();
        model.put("recipes", recipes);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Recipe> recipes;
        if(filter != null || filter.isEmpty()) {
            recipes = recipeRepo.findByNameContaining(filter);
        } else {
            recipes = recipeRepo.findAll();
        }
        model.put("recipes", recipes);
        return "main";
    }
}
