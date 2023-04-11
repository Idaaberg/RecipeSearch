
package recipesearch;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import javafx.scene.layout.FlowPane;

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

    RecipeBackendController rbc = new RecipeBackendController();
    ToggleGroup difficultyToggleGroup = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
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
                    rbc.setMaxTime(newValue);
                    updateRecipeList();

                    if(newValue != null && !newValue.equals(oldValue) && !maxTimeSlider.isValueChanging()) { }
                }
            }
        });
    }

    private void updateRecipeList()
    {
        recipeFlowpane.getChildren().clear();
        List<Recipe> recipes = rbc.getRecipes();
        for(Recipe r: recipes)
        {
            recipeFlowpane.getChildren().add(new RecipeListItem(r, this));
        }

    }
}