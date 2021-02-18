package ua.pivik.testCB.repos;

import org.springframework.data.repository.CrudRepository;
import ua.pivik.testCB.domain.Recipe;

/**
 * @autor Alexander Pivovarov
 */
public interface RecipeRepo extends CrudRepository<Recipe, Long> {

}