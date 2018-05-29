package sample;

import javafx.scene.control.Label;
import javafx.scene.Group;

public class LabelMatrix
{
    private int width;
    private int height;

    private double[][] values;
    private Label[][] textLabels;

    private int posX;
    private int posY;

    private final int maxWidth = 6;
    private final int maxHeight = 4;

    LabelMatrix(int w, int h, int x, int y, Group group) //tworzenie macierzy o określonym rozmiarze
    {
        SetSize(w, h);
        SetPosition(x, y);

        textLabels = new Label[maxWidth][maxHeight];
        values = new double[maxWidth][maxHeight];

        RefreshPosioion(group);
    }

    public void SetSize(int w, int h) //ustawianie rozmiaru macierzy i alokacja pamięci na wartości
    {
        width = w;
        height = h;
    }

    public void SetPosition(int x, int y)
    {
        posX = x;
        posY = y;
    }

    public void ValuesToText()
    {
        String temp = "0";
        for(int x = 0; x < maxWidth; ++x)
        {
            for(int y = 0; y < maxHeight; ++y)
            {
                if(x < width && y < height)
                {
                    temp = Double.toString(values[x][y]);
                    textLabels[x][y].setText(temp);
                }
                else
                {
                    textLabels[x][y].setText("");
                }

            }
        }
    }

    public void FillMatrix(double value)
    {
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                values[x][y] = value;
                textLabels[x][y].setText(Double.toString(values[x][y]));
            }
        }
        ValuesToText();
    }

    public void FillMatrix(double value, double diagonal)
    {
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                if(x == y) values[x][y] = diagonal;
                else values[x][y] = value;
                textLabels[x][y].setText(Double.toString(values[x][y]));
            }
        }
        ValuesToText();
    }

    //pobieranie wysokości i szerokości macierzy
    public int GetHeight() {return height;};
    public int GetWidth() {return width;};
    public int GetPosX() {return posX;};
    public int GetPosY() {return posY;};

    public double GetValue(int x, int y)
    {
        return values[x][y];
    }

    //ustawienia w odpowiednim miejscu w okienku
    public void RefreshPosioion(Group group)
    {
        for(int x = 0; x < maxWidth; ++x)
        {
            for(int y = 0; y < maxHeight; ++y)
            {
                textLabels[x][y] = new Label();
                textLabels[x][y].setLayoutX(5 + posX + 60 * x);
                textLabels[x][y].setLayoutY(posY + 30 * y);
                textLabels[x][y].setPrefWidth(55);

                group.getChildren().add(textLabels[x][y]);
            }
        }
    }

    //mnożenie dwuch macierzy
    public void Multiplying(SuperMatrix a, SuperMatrix b)
    {
        //a x b
        SetSize(b.GetWidth(), a.GetHeight());

        double suma = 0;

        for(int y = 0; y < height; ++y)
        {
            for(int x = 0; x < width; ++x)
            {
                for(int z = 0; z < a.GetWidth() ; ++z)
                    suma += a.GetValue(z, y) * b.GetValue(x, z);

                values[x][y] = suma;
                suma = 0;
            }
        }

        ValuesToText();
    }
}