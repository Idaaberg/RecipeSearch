
package recipesearch;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;


public class RecipeBackendController implements Initializable
{

    RecipeDatabase db = RecipeDatabase.getSharedInstance();
    SearchFilter searchFilter = new SearchFilter(null, 0, null, 0, null);

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }
    // ------ Methods -------
    public List<Recipe> getRecipes()
    {
        List<Recipe> recipes = db.search(new SearchFilter(searchFilter.getDifficulty(), searchFilter.getMaxTime(), searchFilter.getCuisine(), searchFilter.getMaxPrice(), searchFilter.getMainIngredient()));
        return recipes;
    }
    public void setCuisine(String cuisine)
    {
        List<String> cuisineList = Arrays.asList("Sverige", "Grekland", "Indien", "Asien", "Afrika", "Frankrike");
        if (cuisineList.contains(cuisine))
        {
            searchFilter.setCuisine(cuisine);
        }
        else
        {
            searchFilter.setCuisine(null);
        }
    }
    public void setMainIngredient(String mainIngredient)
    {
        List<String> mainIngredientList = Arrays.asList("Kött", "Fisk", "Kyckling", "Vegetarisk");
        if(mainIngredientList.contains(mainIngredient))
        {
            searchFilter.setMainIngredient(mainIngredient);
        }
        else
        {
            searchFilter.setMainIngredient(null);
        }
    }
    public void setDifficulty(String difficulty)
    {
        List<String> difficultyList = Arrays.asList("Lätt", "Mellan", "Svår");
        if(difficultyList.contains(difficulty))
        {
            searchFilter.setDifficulty(difficulty);
        }
        else
        {
            searchFilter.setDifficulty(null);
        }
    }
    public void setMaxPrice(int maxPrice)
    {
        if(maxPrice >= 0)
        {
            searchFilter.setMaxPrice(maxPrice);
        }
        else
        {
            searchFilter.setMaxPrice(0);
        }
    }
    public void setMaxTime(int maxTime)
    {
        if(maxTime >= 0 && maxTime<= 150 && (maxTime % 10 == 0))
        {
            searchFilter.setMaxTime(maxTime);
        }
        else
        {
            searchFilter.setMaxTime(0);
        }

    }
}