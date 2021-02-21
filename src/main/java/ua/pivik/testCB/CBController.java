package ua.pivik.testCB;

/**
 * @autor Alexander Pivovarov
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.pivik.testCB.domain.Recipe;
import ua.pivik.testCB.repos.RecipeRepo;

import java.util.*;

import static java.util.Comparator.comparing;

@Controller
public class CBController {
    final Comparator<Recipe> comparator = comparing(Recipe::getName);

    @Autowired
    private RecipeRepo recipeRepo;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        Iterable<Recipe> recipes = recipeRepo.findAll();
        ((List<Recipe>) recipes).sort(comparator);
        model.put("recipes", recipes);
        return "main";
    }

    @GetMapping("newrecipe") // working
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
        return "newrecipe";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Recipe> recipes;
        if (filter != null || filter.isEmpty()) {
            recipes = recipeRepo.findByNameContaining(filter);
        } else {
            recipes = recipeRepo.findAll();
        }
        ((List<Recipe>) recipes).sort(comparator);
        model.put("recipes", recipes);
        return main(model);
    }

    @GetMapping("edit/{id}")
    public String edit(Map<String, Object> model, @PathVariable("id") Long id) {
        if (recipeRepo.findById(id).isPresent()) {
            Recipe recipe = recipeRepo.findById(id).get();
            model.put("recipes", recipe);
            return "edit";
        }
        return "main";
    }

    @PostMapping("edit/{id}")
    public String saveEdit(@RequestParam String text,
                           @RequestParam String id,
                           Map<String, Object> model) {
        Long idIn = Long.parseLong(id);
        if (recipeRepo.findById(idIn).isPresent()) {
            Recipe recipe = recipeRepo.findById(idIn).get();
            recipe.setText(text);
            recipeRepo.save(recipe);
        }
        return this.main(model);
    }

    @GetMapping("child/{id}")
    public String child (Map<String, Object> model, @PathVariable("id") Long id) {
        if (recipeRepo.findById(id).isPresent()) {
            Recipe recipe = recipeRepo.findById(id).get();
            model.put("recipes", recipe);
            return "edit";
        }
        return "main";
    }

    @PostMapping("/child/{id}")
    public String saveChild(@RequestParam String name,
                            @RequestParam String text,
                           @RequestParam String id,
                           Map<String, Object> model) {
        Long idIn = Long.parseLong(id);
        System.out.println(idIn);
        if (recipeRepo.findById(idIn).isPresent()) {
            Recipe recipe = recipeRepo.findById(idIn).get();
            recipe.setText(text);
            recipe.setName(name);
            recipeRepo.save(recipe);
        }
        return this.main(model);
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        recipeRepo.delete(recipeRepo.findById(id).get());
        return "main";
    }

    @GetMapping("/childview/{id}")  //working
    public String childView(/*@RequestParam Long parent,*/
                            Map<String, Object> model,
                            @PathVariable("id") Long id) {
        Iterable<Recipe> recipes = recipeRepo.findAllByParentId(id);
        ((List<Recipe>) recipes).sort(comparator);
        model.put("recipes", recipes);
        return "childview";
    }
}
