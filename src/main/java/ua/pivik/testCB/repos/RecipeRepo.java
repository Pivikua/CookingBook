package ua.pivik.testCB.repos;

import org.springframework.data.repository.CrudRepository;
import ua.pivik.testCB.domain.Recipe;

import java.util.List;

/**
 * @autor Alexander Pivovarov
 */
public interface RecipeRepo extends CrudRepository<Recipe, Long> {
    List<Recipe> findByNameContaining(String name);
    List<Recipe> findByParentId(Long id);
    List<Recipe> findAllByParentId(Long id);
}
