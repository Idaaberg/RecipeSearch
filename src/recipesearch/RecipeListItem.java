package recipesearch;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.io.IOException;

public class RecipeListItem extends AnchorPane {
    private RecipeSearchController parentController;
    private Recipe recipe;

    @FXML private Label foodLabel;
    @FXML private ImageView imageFood;
    @FXML private ImageView resultMainIngredientImage;
    @FXML private  ImageView resultDifficultyImage;
    @FXML private Label resultTimeLabel;
    @FXML private Label resultPriceLabel;
    @FXML private Label resultDescriptionLabel;
    @FXML private  ImageView resultFlagImage;
    public RecipeListItem(Recipe recipe, RecipeSearchController recipeSearchController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipe_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        this.recipe = recipe;
        this.parentController = recipeSearchController;
        imageFood.setImage(recipe.getFXImage());
        foodLabel.setText(recipe.getName());
        resultTimeLabel.setText(Integer.toString(recipe.getTime()));
        resultPriceLabel.setText(Integer.toString(recipe.getPrice()));
        resultDescriptionLabel.setText((recipe.getDescription()));
        resultMainIngredientImage.setImage(recipeSearchController.getMainIngredientImage(recipe.getMainIngredient()));
        resultFlagImage.setImage(recipeSearchController.getCuisineImage(recipe.getCuisine()));
        resultDifficultyImage.setImage((recipeSearchController.getDifficultyImage(recipe.getDifficulty())));

    }

    @FXML
    protected void onClick(Event event){
        parentController.openRecipeView(recipe);
    }
}