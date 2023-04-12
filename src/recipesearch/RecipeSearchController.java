
package recipesearch;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import javafx.scene.layout.FlowPane;
import se.chalmers.ait.dat215.lab2.Recipe;


public class RecipeSearchController implements Initializable {

    RecipeDatabase db = RecipeDatabase.getSharedInstance();

    //_______________Components_______________-
    @FXML private FlowPane recipeFlowpane;
    @FXML private ComboBox mainIngredientBox;
    @FXML private ComboBox cuisineBox;
    @FXML private RadioButton allRadioButton;
    @FXML private RadioButton easyRadioButton;
    @FXML private RadioButton mediumRadioButton;
    @FXML private RadioButton hardRadioButton;
    @FXML private Spinner maxPriceSpinner;
    @FXML private Slider maxTimeSlider;
    @FXML private Label maxTimeLabel;
    @FXML private Label labelRecipeFood;
    @FXML private ImageView imageRecipeFood;
    @FXML private AnchorPane recipeDetailPane;
    @FXML private SplitPane searchPane;
    private Map<String, RecipeListItem> recipeListItemMap = new HashMap<String, RecipeListItem>();

    RecipeBackendController rbc = new RecipeBackendController();
    ToggleGroup difficultyToggleGroup = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        for (Recipe recipe : rbc.getRecipes()) {
            RecipeListItem recipeListItem = new RecipeListItem(recipe, this);
            recipeListItemMap.put(recipe.getName(), recipeListItem);
        }

        //_____________ComboBoxes__________________//
        updateRecipeList();
        mainIngredientBox.getItems().addAll("Visa alla", "Kött", "Fisk", "Kyckling", "Vegetariskt");
        mainIngredientBox.getSelectionModel().select("Visa alla");
        cuisineBox.getItems().addAll("Visa alla", "Sverige", "Grekland", "Indien", "Asien", "Afrika", "Frankrike");
        cuisineBox.getSelectionModel().select("Visa alla");
        mainIngredientBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()

        {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                rbc.setMainIngredient(newValue);
                updateRecipeList();
            }
        });

        cuisineBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                rbc.setCuisine(newValue);
                updateRecipeList();
            }
        });
        //_________________RadioButtons___________________
        allRadioButton.setToggleGroup(difficultyToggleGroup);
        easyRadioButton.setToggleGroup(difficultyToggleGroup);
        mediumRadioButton.setToggleGroup(difficultyToggleGroup);
        hardRadioButton.setToggleGroup(difficultyToggleGroup);
        allRadioButton.setSelected(true); // Preselected button

        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
            {

                if (difficultyToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) difficultyToggleGroup.getSelectedToggle();
                    rbc.setDifficulty(selected.getText());
                    updateRecipeList();
                }
            }
        });

        //_______________Spinner________________
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,50,10);
        maxPriceSpinner.setValueFactory(valueFactory);
        maxPriceSpinner.valueProperty().addListener(new ChangeListener<Integer>()
        {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue)
            {
                {
                    rbc.setMaxPrice(newValue);
                    updateRecipeList();
                }
            }
        });

        maxPriceSpinner.focusedProperty().addListener(new ChangeListener<Boolean>()
        {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {

                if(newValue)
                {
                    //focusgained - do nothing
                }
                else
                {
                    Integer value = Integer.valueOf(maxPriceSpinner.getEditor().getText());
                    rbc.setMaxPrice(value);
                    updateRecipeList();
                }

            }
        });

        //______________Slider_______________

        maxTimeSlider.valueProperty().addListener(new ChangeListener<Number>()
        {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                {
                    int textValue;
                    textValue = roundToNearestTen(newValue.intValue());
                    maxTimeLabel.setText(String.valueOf(textValue));
                    if(newValue != null && !newValue.equals(oldValue) && !maxTimeSlider.isValueChanging())
                    {
                        rbc.setMaxTime(newValue.intValue());
                        updateRecipeList();
                    }
                }
            }
        });

    }

    public static int roundToNearestTen(int num) {
        return (int) Math.round(num / 10.0) * 10;
    }
    private void updateRecipeList()
    {
        recipeFlowpane.getChildren().clear();
        List<Recipe> recipes = rbc.getRecipes();
        for(Recipe r: recipes)
        {
            recipeFlowpane.getChildren().add(recipeListItemMap.get(r.getName()));
        }

    }


    private void populateRecipeDetailView(Recipe recipe)
    {
        labelRecipeFood.setText(recipe.getName());
        imageRecipeFood.setImage(recipe.getFXImage());
    }


    @FXML
    public void closeRecipeView(){
        searchPane.toFront();
    }

    public void openRecipeView(Recipe recipe){
        populateRecipeDetailView(recipe);
        recipeDetailPane.toFront();
    }

}