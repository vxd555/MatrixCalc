package sample;

import javafx.scene.Group;
import javafx.scene.control.TextField;

import java.util.Random;

public class SuperMatrix
{
    private int width;
    private int height;

    private double[][] values;
    private TextField[][] textFields;

    private int posX;
    private int posY;

    SuperMatrix(int w, int h, int x, int y) //tworzenie macierzy o określonym rozmiarze
    {
        SetSize(w, h);
        SetPosition(x, y);
    }

    public void FillRandom()
    {
        Random rn = new Random();
        int canvasLen = 100;

        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                if(y == height - 1) values[x][y] = 1;
                else
                {
                    values[x][y] = (rn.nextInt() % canvasLen - canvasLen / 2);
                    values[x][y] /= 10;
                }
                textFields[x][y].setText(Double.toString(values[x][y]));
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
                textFields[x][y].setText(Double.toString(values[x][y]));
            }
        }
    }

    public void FillMatrix(double value, double diagonal)
    {
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                if(x == y) values[x][y] = diagonal;
                else values[x][y] = value;
                textFields[x][y].setText(Double.toString(values[x][y]));
            }
        }
    }

    public void SetValue(int x, int y, double value)
    {
        values[x][y] = value;
        textFields[x][y].setText(Double.toString(value));
    }
    public void SetValue(int x, int y, String value)
    {
        textFields[x][y].setText(value);
    }

    public double GetValue(int x, int y)
    {
        return values[x][y];
    }

    //pobieranie wysokości i szerokości macierzy
    public int GetHeight() {return height;};
    public int GetWidth() {return width;};
    public int GetPosX() {return posX;};
    public int GetPosY() {return posY;};

    public void SetSize(int w, int h) //ustawianie rozmiaru macierzy i alokacja pamięci na wartości
    {
        width = w;
        height = h;
        values = new double[w][h];
        textFields = new TextField[w][h];
    }

    public void SetPosition(int x, int y)
    {
        posX = x;
        posY = y;
    }

    public void RemoveTextField(Group group)
    {
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                textFields[x][y].setVisible(false);
                group.getChildren().remove(textFields[x][y]);

            }
        }
        System.out.println("minus: " + width + " " + height);
    }

    public void NewSize(int w, int h, Group group) //zmienia rozmiar już powstałej wcześniej macierzy
    {
        double oldValues[][] = new double[width][height];
        int oldW = width;
        int oldH = height;
        if(oldW > w) oldW = w;
        else if(oldH > h) oldH = h; //zapisywanie rozmiaru starej tabeli, żeby wiedzieć ile należy przekopiować danych

        for(int x = 0; x < width; ++x) //kopiowanie wartości ze starej tabeli
        {
            for(int y = 0; y < height; ++y)
            {
                oldValues[x][y] = values[x][y];
            }
        }

        for(int x = 0; x < width; ++x) //usuwanie starych pól
        {
            for(int y = 0; y < height; ++y)
            {
                group.getChildren().remove(textFields[x][y]);
            }
        }

        //alokacja nowej tabeli i tworzenie macierzy jednostkowej
        values = new double[w][h];
        height = h;
        width = w;
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                if(y == height - 1 && x == y) values[x][y] = 1;
                else values[x][y] = 0;
            }
        }

        //przepisywanie starych wartości do tablicy macierzy
        for(int x = 0; x < oldW && x < w; ++x)
        {
            for(int y = 0; y < oldH && y < h; ++y)
            {
                values[x][y] = oldValues[x][y];
            }
        }

        textFields = new TextField[w][h]; //tworzeni pól tekstowych
        RefreshPosioion(group); //ustawianie pól w odpowiednich miejscach
        ValuesToTextField(); //przepisywanie danych z tabeli do pól tekstowych
    }

    public void RefreshPosioion(Group group)
    {
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                textFields[x][y] = new TextField();
                textFields[x][y].setLayoutX(posX + 60 * x);
                textFields[x][y].setLayoutY(posY + 30 * y);
                textFields[x][y].setPrefWidth(55);

                group.getChildren().add(textFields[x][y]);
            }
        }
    }

    public void RefreshAllValues()
    {
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                TextFieldToValues(x, y);
            }
        }
    }

    public void TextFieldToValues(int x, int y)
    {
        try
        {
            String textToParse = textFields[x][y].getText();

            if(textToParse.length() > 0 && textToParse.substring(0, 1).equals("-"))
            {
                if(textToParse.length() > 4 && textToParse.substring(1, 5).equals("sin("))
                {
                    textToParse = textToParse.substring(5, textToParse.length());
                    textToParse = textToParse.substring(0, textToParse.length() - 1);
                    values[x][y] = -1 * Math.sin(Double.parseDouble(textToParse) * Math.PI / 180);
                }
                else if(textToParse.length() > 4 && textToParse.substring(1, 5).equals("cos("))
                {
                    textToParse = textToParse.substring(5, textToParse.length());
                    textToParse = textToParse.substring(0, textToParse.length() - 1);
                    values[x][y] = -1 * Math.cos(Double.parseDouble(textToParse) * Math.PI / 180);
                }
                else if(textToParse.length() > 1)
                {
                    textToParse = textToParse.substring(1, textToParse.length());
                    values[x][y] = -1 * Double.parseDouble(textToParse);
                }
                else
                {
                    values[x][y] = 0;
                }
            }
            else
            {
                if(textToParse.length() > 3 && textToParse.substring(0, 4).equals("sin("))
                {
                    textToParse = textToParse.substring(4, textToParse.length());
                    textToParse = textToParse.substring(0, textToParse.length() - 1);
                    values[x][y] = Math.sin(Double.parseDouble(textToParse) * Math.PI / 180);
                }
                else if(textToParse.length() > 3 && textToParse.substring(0, 4).equals("cos("))
                {
                    textToParse = textToParse.substring(4, textToParse.length());
                    textToParse = textToParse.substring(0, textToParse.length() - 1);
                    values[x][y] = Math.cos(Double.parseDouble(textToParse) * Math.PI / 180);
                }
                else if(textToParse.length() > 0)
                {
                    values[x][y] = Double.parseDouble(textToParse);
                }
                else
                {
                    values[x][y] = 0;
                }
            }
        }
        catch(NumberFormatException e)
        {
            values[x][y] = 0;
        }
    }

    public void ValuesToTextField()
    {
        String temp = "0";
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                temp = Double.toString(values[x][y]);
                textFields[x][y].setText(temp);
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
                    suma += a.values[z][y] * b.values[x][z];

                values[x][y] = suma;
                suma = 0;
            }
        }
    }

    //DEBUG: wypisanie macierzy w konsoli
    public void ShowMatrix()
    {
        for(int y = 0; y < height; ++y)
        {
            for(int x = 0; x < width; ++x)
            {
                System.out.print(values[x][y] + " ");
            }
            System.out.println();
        }
    }

    //wypełnianie macierzy do obliczeń odpowiednimi wartościami
    public void FillCalculate(int type, int viewType)
    {
        if(viewType == 2)
        {
            if(type == 0) //0 translate + (4;3)
            {
                textFields[0][0].setText("1");  textFields[1][0].setText("0");  textFields[2][0].setText("4");
                textFields[0][1].setText("0");  textFields[1][1].setText("1");  textFields[2][1].setText("3");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1");
            }
            else if(type == 1) //obrót 90 stopni w lewo
            {
                textFields[0][0].setText("cos(90)");  textFields[1][0].setText("-sin(90)");  textFields[2][0].setText("0");
                textFields[0][1].setText("sin(90)");  textFields[1][1].setText("cos(90)");  textFields[2][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1");
            }
            else if(type == 2) //obrót 270 stopni w lewo czyli 90 prawo
            {
                textFields[0][0].setText("cos(270)");  textFields[1][0].setText("-sin(270)");  textFields[2][0].setText("0");
                textFields[0][1].setText("sin(270)");  textFields[1][1].setText("cos(270)");  textFields[2][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1");
            }
            else if(type == 3) //skala * 2
            {
                textFields[0][0].setText("2");  textFields[1][0].setText("0");  textFields[2][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("2");  textFields[2][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1");
            }
            else if(type == 4) //rozciąganie X 2
            {
                textFields[0][0].setText("1");  textFields[1][0].setText("2");  textFields[2][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("1");  textFields[2][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1");
            }
            else if(type == 5) //rozciąganie Y 2
            {
                textFields[0][0].setText("1");  textFields[1][0].setText("0");  textFields[2][0].setText("0");
                textFields[0][1].setText("2");  textFields[1][1].setText("1");  textFields[2][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1");
            }
            else if(type == 6) //lustro horyzontalne
            {
                textFields[0][0].setText("1");  textFields[1][0].setText("0");  textFields[2][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("-1");  textFields[2][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1");
            }
            else if(type == 7) //lustro wertykalne
            {
                textFields[0][0].setText("-1");  textFields[1][0].setText("0");  textFields[2][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("1");  textFields[2][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1");
            }
        }
        else if(viewType == 3)
        {
            if(type == 0) //translate + [3;2;5]
            {
                textFields[0][0].setText("1");  textFields[1][0].setText("0");  textFields[2][0].setText("0"); textFields[3][0].setText("3");
                textFields[0][1].setText("0");  textFields[1][1].setText("1");  textFields[2][1].setText("0"); textFields[3][1].setText("2");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1"); textFields[3][2].setText("5");
                textFields[0][3].setText("0");  textFields[1][3].setText("0");  textFields[2][3].setText("0"); textFields[3][3].setText("1");
            }
            else if(type == 1) //obrót 90 X
            {
                textFields[0][0].setText("1");  textFields[1][0].setText("0");  textFields[2][0].setText("0"); textFields[3][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("cos(90)");  textFields[2][1].setText("-sin(90)"); textFields[3][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("sin(90)");  textFields[2][2].setText("cos(90)"); textFields[3][2].setText("0");
                textFields[0][3].setText("0");  textFields[1][3].setText("0");  textFields[2][3].setText("0"); textFields[3][3].setText("1");
            }
            else if(type == 2) //obrót 90 Y
            {
                textFields[0][0].setText("cos(90)");  textFields[1][0].setText("0");  textFields[2][0].setText("sin(90)"); textFields[3][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("1");  textFields[2][1].setText("0"); textFields[3][1].setText("0");
                textFields[0][2].setText("-sin(90)");  textFields[1][2].setText("0");  textFields[2][2].setText("cos(90)"); textFields[3][2].setText("0");
                textFields[0][3].setText("0");  textFields[1][3].setText("0");  textFields[2][3].setText("0"); textFields[3][3].setText("1");
            }
            else if(type == 3) //skala * 2
            {
                textFields[0][0].setText("2");  textFields[1][0].setText("0");  textFields[2][0].setText("0"); textFields[3][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("2");  textFields[2][1].setText("0"); textFields[3][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("2"); textFields[3][2].setText("0");
                textFields[0][3].setText("0");  textFields[1][3].setText("0");  textFields[2][3].setText("0"); textFields[3][3].setText("1");
            }
            else if(type == 4) //rozciąganie X 2
            {
                textFields[0][0].setText("1");  textFields[1][0].setText("2");  textFields[2][0].setText("2"); textFields[3][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("1");  textFields[2][1].setText("0"); textFields[3][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1"); textFields[3][2].setText("0");
                textFields[0][3].setText("0");  textFields[1][3].setText("0");  textFields[2][3].setText("0"); textFields[3][3].setText("1");
            }
            else if(type == 5) //rozciąganie Y 2
            {
                textFields[0][0].setText("1");  textFields[1][0].setText("0");  textFields[2][0].setText("0"); textFields[3][0].setText("0");
                textFields[0][1].setText("2");  textFields[1][1].setText("1");  textFields[2][1].setText("2"); textFields[3][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1"); textFields[3][2].setText("0");
                textFields[0][3].setText("0");  textFields[1][3].setText("0");  textFields[2][3].setText("0"); textFields[3][3].setText("1");
            }
            else if(type == 6) //lustro horyzontalne
            {
                textFields[0][0].setText("1");  textFields[1][0].setText("0");  textFields[2][0].setText("0"); textFields[3][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("-1");  textFields[2][1].setText("0"); textFields[3][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1"); textFields[3][2].setText("0");
                textFields[0][3].setText("0");  textFields[1][3].setText("0");  textFields[2][3].setText("0"); textFields[3][3].setText("1");
            }
            else if(type == 7) //lustro wertykalne
            {
                textFields[0][0].setText("-1");  textFields[1][0].setText("0");  textFields[2][0].setText("0"); textFields[3][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("1");  textFields[2][1].setText("0"); textFields[3][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1"); textFields[3][2].setText("0");
                textFields[0][3].setText("0");  textFields[1][3].setText("0");  textFields[2][3].setText("0"); textFields[3][3].setText("1");
            }
            else if(type == 8) //obrót 90 Z
            {
                textFields[0][0].setText("cos(90)");  textFields[1][0].setText("-sin(90)");  textFields[2][0].setText("0"); textFields[3][0].setText("0");
                textFields[0][1].setText("sin(90)");  textFields[1][1].setText("cos(90)");  textFields[2][1].setText("0"); textFields[3][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("1"); textFields[3][2].setText("0");
                textFields[0][3].setText("0");  textFields[1][3].setText("0");  textFields[2][3].setText("0"); textFields[3][3].setText("1");
            }
            else if(type == 9) //11 rozciąganie Z
            {
                textFields[0][0].setText("1");  textFields[1][0].setText("0");  textFields[2][0].setText("0"); textFields[3][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("1");  textFields[2][1].setText("0"); textFields[3][1].setText("0");
                textFields[0][2].setText("2");  textFields[1][2].setText("2");  textFields[2][2].setText("1"); textFields[3][2].setText("0");
                textFields[0][3].setText("0");  textFields[1][3].setText("0");  textFields[2][3].setText("0"); textFields[3][3].setText("1");
            }
            else if(type == 10) //11 lustro Z
            {
                textFields[0][0].setText("1");  textFields[1][0].setText("0");  textFields[2][0].setText("0"); textFields[3][0].setText("0");
                textFields[0][1].setText("0");  textFields[1][1].setText("1");  textFields[2][1].setText("0"); textFields[3][1].setText("0");
                textFields[0][2].setText("0");  textFields[1][2].setText("0");  textFields[2][2].setText("-1"); textFields[3][2].setText("0");
                textFields[0][3].setText("0");  textFields[1][3].setText("0");  textFields[2][3].setText("0"); textFields[3][3].setText("1");
            }

        }
    }

}