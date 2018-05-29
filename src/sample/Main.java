package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.layout.StackPane;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;

public class Main extends Application
{
    SuperMatrix positionMatrix;
    SuperMatrix calcMatrix;
    LabelMatrix resaultMatrix;

    Group root;

    //przyciski do obliczeń w dwuch wymiarach
    Button buttonMove2D;
    Button buttonRot902D;
    Button buttonRot2702D;
    Button buttonScale2D;
    Button buttonStretchX2D;
    Button buttonStretchY2D;
    Button buttonMirrorX2D;
    Button buttonMirrorY2D;

    //przyciski do obliczeń w trzech wymiarach
    Button buttonRotZ3D;
    Button buttonStretchZ3D;
    Button buttonMirrorZ3D;

    Button button23D; //przycisk do przechodzenia między trybami 2D i 3D
    Button buttonLineView; //przycisk do trybu podglądu samych punktów lub z liniami
    int viewType; //liczba oznaczająca czy mamy widok 2D czy 3D
    int lineView; //0 widok punktowy 1 widok liniowy
    int pointsAmount; //ilość punktów do wyświetlenia
    Label labelName; //autor

    //przypisy do macierzy
    Label posMatName; //etykieta od macerzy pozycji
    Label calcMatName; //etykieta od macierzy transformacji/obliczeń
    Label resMatName; //etykieta macierzy wynikowej

    Button buttonCalculate;
    SuperDrawer superDrawer;
    Color pointsColor;
    Color resaultColor;

    Button buttonAdd; //zwiększ ilość punktów
    Button buttonDelete; //zmniejsz ilość punktów
    Button buttonRandom; //wstaw losowe wartości


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //nazwanie okienka i stworzenie grupy na kontrolki
        primaryStage.setTitle("Matrix Calc");
        root = new Group();
        viewType = 2; //tryby widoku 2D i 3D
        lineView = 0;
        pointsAmount = 1;

        //inicjalizacja macierzy pozycji, obliczeń i wyniku
        positionMatrix = new SuperMatrix(1, 3, 390, 20); //macierz z pozycją punktów 1 na 3
        calcMatrix = new SuperMatrix(3, 3, 390, 160); //macierz do obliczeń 3 na 3
        resaultMatrix = new LabelMatrix(1,3,390,330, root); //macierzy wynikowa 1 na 3

        //obiekt na którym będą rysowane punkty
        superDrawer = new SuperDrawer(new Canvas(370, 370), root);

        //tworzenie przycisków
        CreateButtons();

        //odświerzenie wdoku w textfieldach
        positionMatrix.RefreshPosioion(root);
        calcMatrix.RefreshPosioion(root);

        //wypełnianie macierzy podstawowymi wartościai
        positionMatrix.FillMatrix(0); //[0; 0; 0]
        positionMatrix.SetValue(0, 2, 1); //[0; 0; 1]
        calcMatrix.FillMatrix(0, 1); //[1, 0, 0; 0, 1, 0; 0, 0, 1]
        resaultMatrix.FillMatrix(0); //[0; 0; 0]

        pointsColor = Color.GRAY;
        resaultColor = Color.BLACK;
        superDrawer.DrawMatrix(positionMatrix, pointsColor, resaultMatrix, resaultColor, viewType, lineView); //rysowanie macierzy na canvasie

        labelName = new Label();
        CreateLabel(labelName, 10, 430, "by Maciej Nabiałczyk");
        root.getChildren().add(labelName);

        posMatName = new Label();
        CreateLabel(posMatName, 390, 1, "Position matrix");
        root.getChildren().add(posMatName);

        calcMatName = new Label();
        CreateLabel(calcMatName, 390, 141, "Transformation matrix");
        root.getChildren().add(calcMatName);

        resMatName = new Label();
        CreateLabel(resMatName, 390, 311, "Resault Matrix");
        root.getChildren().add(resMatName);

        //tworzenie okna
        primaryStage.setScene(new Scene(root, 810, 450));
        primaryStage.getScene().setFill(Color.rgb(230, 230, 230));
        primaryStage.show();

    }

    public void CreateButtons()
    {
        //-------------------------------------------------
        //              przyciski od obliczeń
        //-------------------------------------------------
        //***********************2D************************
        buttonMove2D = new Button();
        CreateButton(buttonMove2D, 630, 160, 50, "Move");
        buttonMove2D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) { calcMatrix.FillCalculate(0, viewType); CalcFunction();}
        });
        root.getChildren().add(buttonMove2D);

        buttonRot902D = new Button();
        CreateButton(buttonRot902D, 630, 190, 50, "RotZ");
        buttonRot902D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) { calcMatrix.FillCalculate(1, viewType); CalcFunction(); }
        });
        root.getChildren().add(buttonRot902D);

        buttonRot2702D = new Button();
        CreateButton(buttonRot2702D, 690, 190, 50, "Rot-Z");
        buttonRot2702D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) { calcMatrix.FillCalculate(2, viewType); CalcFunction();}
        });
        root.getChildren().add(buttonRot2702D);

        buttonScale2D = new Button();
        CreateButton(buttonScale2D, 690, 160, 50, "Scal");
        buttonScale2D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) { calcMatrix.FillCalculate(3, viewType); CalcFunction();}
        });
        root.getChildren().add(buttonScale2D);

        buttonStretchX2D = new Button();
        CreateButton(buttonStretchX2D, 630, 220, 50, "StretX");
        buttonStretchX2D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) { calcMatrix.FillCalculate(4, viewType); CalcFunction();}
        });
        root.getChildren().add(buttonStretchX2D);

        buttonStretchY2D = new Button();
        CreateButton(buttonStretchY2D, 690, 220, 50, "StretY");
        buttonStretchY2D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) { calcMatrix.FillCalculate(5, viewType); CalcFunction();}
        });
        root.getChildren().add(buttonStretchY2D);

        buttonMirrorX2D = new Button();
        CreateButton(buttonMirrorX2D, 630, 250, 50, "MirX");
        buttonMirrorX2D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) { calcMatrix.FillCalculate(6, viewType); CalcFunction();}
        });
        root.getChildren().add(buttonMirrorX2D);

        buttonMirrorY2D = new Button();
        CreateButton(buttonMirrorY2D, 690, 250, 50, "MirY");
        buttonMirrorY2D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) { calcMatrix.FillCalculate(7, viewType); CalcFunction();}
        });
        root.getChildren().add(buttonMirrorY2D);

        //***********************3D************************
        buttonRotZ3D = new Button();
        CreateButton(buttonRotZ3D, 750, 190, 50, "RotZ");
        buttonRotZ3D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) { calcMatrix.FillCalculate(8, 3); CalcFunction();}
        });
        root.getChildren().add(buttonRotZ3D);
        buttonRotZ3D.setVisible(false);

        buttonStretchZ3D = new Button();
        CreateButton(buttonStretchZ3D, 750, 220, 50, "StretZ");
        buttonStretchZ3D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) { calcMatrix.FillCalculate(9, 3); CalcFunction();}
        });
        root.getChildren().add(buttonStretchZ3D);
        buttonStretchZ3D.setVisible(false);

        buttonMirrorZ3D = new Button();
        CreateButton(buttonMirrorZ3D, 750, 250, 50, "MirZ");
        buttonMirrorZ3D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) { calcMatrix.FillCalculate(10, 3); CalcFunction();}
        });
        root.getChildren().add(buttonMirrorZ3D);
        buttonMirrorZ3D.setVisible(false);

        //******************inne przyciski*****************
        button23D = new Button();
        CreateButton(button23D, 10, 390, 30, "2D");
        button23D.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(viewType == 2) //zmiana trybu okna na 3D
                {
                    viewType = 3;
                    button23D.setText("3D");
                    calcMatrix.NewSize(4, 4, root);
                    positionMatrix.NewSize(pointsAmount, viewType + 1, root);
                    CalcFunction();

                    buttonRotZ3D.setVisible(true);
                    buttonStretchZ3D.setVisible(true);
                    buttonMirrorZ3D.setVisible(true);

                    buttonRot902D.setText("RotX");
                    buttonRot2702D.setText("RotY");
                }
                else if(viewType == 3) //zmiana trybu okna na 2D
                {
                    viewType = 2;
                    button23D.setText("2D");
                    calcMatrix.NewSize(3, 3, root);
                    positionMatrix.NewSize(pointsAmount, viewType + 1, root);
                    CalcFunction();

                    buttonRotZ3D.setVisible(false);
                    buttonStretchZ3D.setVisible(false);
                    buttonMirrorZ3D.setVisible(false);

                    buttonRot902D.setText("RotZ");
                    buttonRot2702D.setText("Rot-Z");
                }

            }
        });
        root.getChildren().add(button23D);

        buttonLineView = new Button();
        CreateButton(buttonLineView, 50, 390, 73, "Point view");
        buttonLineView.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(lineView == 1)
                {
                    lineView = 0;
                    buttonLineView.setText("Point view");
                    superDrawer.DrawMatrix(positionMatrix, pointsColor, resaultMatrix, resaultColor, viewType, lineView);
                }
                else if(lineView == 0)
                {
                    lineView = 1;
                    buttonLineView.setText("Line view");
                    superDrawer.DrawMatrix(positionMatrix, pointsColor, resaultMatrix, resaultColor, viewType, lineView);
                }

            }
        });
        root.getChildren().add(buttonLineView);

        buttonCalculate = new Button();
        CreateButton(buttonCalculate, 133, 390, 50, "Calculate");
        buttonCalculate.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                CalcFunction();
            }
        });
        root.getChildren().add(buttonCalculate);

        buttonAdd = new Button();
        CreateButton(buttonAdd, 760, 20, 25, "+");
        buttonAdd.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(pointsAmount < 6) ++pointsAmount;
                positionMatrix.NewSize(pointsAmount, viewType + 1, root);
                if(viewType == 2) positionMatrix.SetValue(pointsAmount - 1, 2, 1);
                else if(viewType == 3) positionMatrix.SetValue(pointsAmount - 1, 3, 1);
                CalcFunction();
            }
        });
        root.getChildren().add(buttonAdd);

        buttonDelete = new Button();
        CreateButton(buttonDelete, 760, 55, 25, "-");
        buttonDelete.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(pointsAmount > 1) --pointsAmount;
                positionMatrix.NewSize(pointsAmount, viewType + 1, root);
                CalcFunction();
            }
        });
        root.getChildren().add(buttonDelete);

        buttonRandom = new Button();
        CreateButton(buttonRandom, 760, 90, 25, "*");
        buttonRandom.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                positionMatrix.FillRandom();
                CalcFunction();
            }
        });
        root.getChildren().add(buttonRandom);

    }

    public void CalcFunction() //obliczanie macierzy wynikowej na podstawie punktów i macierzy transformacji
    {
        positionMatrix.RefreshAllValues();
        calcMatrix.RefreshAllValues();
        resaultMatrix.Multiplying(calcMatrix, positionMatrix);

        superDrawer.DrawMatrix(positionMatrix, pointsColor, resaultMatrix, resaultColor, viewType, lineView);
    }

    public void CreateButton(Button butt, int posX, int posY, int minW, String label) //funcja do skróconego zapisu tworzenia przycisku
    {
        butt.setLayoutX(posX);
        butt.setLayoutY(posY);
        butt.setMinWidth(minW);
        butt.setText(label);
    }

    public void CreateLabel(Label lab, int posX, int posY, String label) //funkcja do skróconego zapisu tworzenia etykiety
    {
        lab.setLayoutX(posX);
        lab.setLayoutY(posY);
        lab.setText(label);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}