package sample;

import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Point3D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import java.awt.Point;

import java.awt.*;

public class SuperDrawer
{
    private Canvas canvas;

    SuperDrawer(Canvas c, Group group)
    {
        //ustawienie canvasa na odpowiedniej pozycji
        canvas = c;
        canvas.setLayoutX(10);
        canvas.setLayoutY(10);

        ResetBackground();
        DrawAxes(2);

        //dodanie canvasu do grupy, żeby mógł zostać wyświetlony
        group.getChildren().add(canvas);
    }

    //wypełnianie tła białym kolorem
    public void ResetBackground()
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,370,370);
    }

    //rysowanie osi układu współżędnych
    public void DrawAxes(int dimentions)
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);

        gc.setStroke(Color.RED);
        gc.strokeLine(0,canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2); //rysowanie osi X
        gc.setStroke(Color.GREEN);
        gc.strokeLine(canvas.getWidth() / 2,0,canvas.getWidth() / 2, canvas.getHeight()); //rysowanie osi Y
        if(dimentions == 3)
        {
            gc.setStroke(Color.BLUE);
            gc.strokeLine(0,canvas.getHeight(),canvas.getWidth(), 0); //rysowanie osi Z
        }
    }

    public void DrawMatrix(SuperMatrix pos, Color colorPos, LabelMatrix res, Color colorRes, int view, int line)
    {
        ResetBackground();
        DrawAxes(view);

        double angle = Math.sin(45 * Math.PI / 180);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //-------------------------------------rysowanie punktów przed transformacją-------------------------------------
        gc.setFill(colorPos);
        if(view == 2)
        {
            for(int x = 0; x < pos.GetWidth(); ++x)
            {
                gc.fillOval(canvas.getWidth() / 2 + 10 * pos.GetValue(x, 0) - 2,canvas.getHeight() / 2 - 10 * pos.GetValue(x, 1) - 2,4,4);
            }
        }
        if(view == 3)
        {
            for(int x = 0; x < pos.GetWidth(); ++x)
            {
                gc.fillOval(canvas.getWidth() / 2 + 10 * (pos.GetValue(x, 0) + (pos.GetValue(x, 2) * angle)) - 2,
                            canvas.getHeight() / 2 - 10 * (pos.GetValue(x, 1) + (pos.GetValue(x, 2) * angle)) - 2,
                            4,4);
            }
        }
        if(line == 1) //rysowanie połączeń pomiędzy punktami
        {
            gc.setStroke(colorPos);
            gc.setLineWidth(2);
            for(int x = 0; x < pos.GetWidth() - 1; ++x)
            {
                if(view == 2)
                {
                    gc.strokeLine(  canvas.getWidth() / 2 + 10 * pos.GetValue(x, 0),
                                    canvas.getHeight() / 2 - 10 * pos.GetValue(x, 1),
                                    canvas.getWidth() / 2 + 10 * pos.GetValue(x + 1, 0),
                                    canvas.getHeight() / 2 - 10 * pos.GetValue(x + 1, 1));
                }
                else if(view == 3)
                {
                    gc.strokeLine(  canvas.getWidth() / 2 + 10 * (pos.GetValue(x, 0) + (pos.GetValue(x, 2) * angle)),
                                    canvas.getHeight() / 2 - 10 * (pos.GetValue(x, 1) + (pos.GetValue(x, 2) * angle)),
                                    canvas.getWidth() / 2 + 10 * (pos.GetValue(x + 1, 0) + (pos.GetValue(x + 1, 2) * angle)),
                                    canvas.getHeight() / 2 - 10 * (pos.GetValue(x + 1, 1) + (pos.GetValue(x + 1, 2) * angle)));

                }
            }
            if(pos.GetWidth() > 2) //rysowanie połączenia między ostatnim, a pierwszym punktem
            {
                if(view == 2)
                {
                    gc.strokeLine(  canvas.getWidth() / 2 + 10 * pos.GetValue(0, 0),
                                    canvas.getHeight() / 2 - 10 * pos.GetValue(0, 1),
                                    canvas.getWidth() / 2 + 10 * pos.GetValue(pos.GetWidth() - 1, 0),
                                    canvas.getHeight() / 2 - 10 * pos.GetValue(pos.GetWidth() - 1, 1));
                }
                else if(view == 3)
                {
                    gc.strokeLine(  canvas.getWidth() / 2 + 10 * (pos.GetValue(0, 0) + (pos.GetValue(0, 2) * angle)),
                                    canvas.getHeight() / 2 - 10 * (pos.GetValue(0, 1) + (pos.GetValue(0, 2) * angle)),
                                    canvas.getWidth() / 2 + 10 * (pos.GetValue(pos.GetWidth() - 1, 0) + (pos.GetValue(pos.GetWidth() - 1, 2) * angle)),
                                    canvas.getHeight() / 2 - 10 * (pos.GetValue(pos.GetWidth() - 1, 1) + (pos.GetValue(pos.GetWidth() - 1, 2) * angle)));
                }

            }
        }

        //----------------------------------rysowanie punktów po transformacji------------------------------------
        gc.setFill(colorRes);
        if(view == 2)
        {
            for (int x = 0; x < res.GetWidth(); ++x)
            {
                gc.fillOval(canvas.getWidth() / 2 + 10 * res.GetValue(x, 0) - 2, canvas.getHeight() / 2 - 10 * res.GetValue(x, 1) - 2, 4, 4);
            }
        }
        if(view == 3)
        {
            for (int x = 0; x < res.GetWidth(); ++x)
            {
                gc.fillOval(canvas.getWidth() / 2 + 10 * (res.GetValue(x, 0) + (res.GetValue(x, 2) * angle)) - 2,
                            canvas.getHeight() / 2 - 10 * (res.GetValue(x, 1) + (res.GetValue(x, 2) * angle)) - 2,
                            4,4);
            }
        }
        if(line == 1) //rysowanie połączeń pomiędzy punktami
        {
            gc.setStroke(colorRes);
            gc.setLineWidth(2);
            for(int x = 0; x < res.GetWidth() - 1; ++x)
            {
                if(view == 2)
                {
                    gc.strokeLine(  canvas.getWidth() / 2 + 10 * res.GetValue(x, 0),
                                    canvas.getHeight() / 2 - 10 * res.GetValue(x, 1),
                                    canvas.getWidth() / 2 + 10 * res.GetValue(x + 1, 0),
                                    canvas.getHeight() / 2 - 10 * res.GetValue(x + 1, 1));
                }
                else if(view == 3)
                {
                    gc.strokeLine(  canvas.getWidth() / 2 + 10 * (res.GetValue(x, 0) + (res.GetValue(x, 2) * angle)),
                                    canvas.getHeight() / 2 - 10 * (res.GetValue(x, 1) + (res.GetValue(x, 2) * angle)),
                                    canvas.getWidth() / 2 + 10 * (res.GetValue(x + 1, 0) + (res.GetValue(x + 1, 2) * angle)),
                                    canvas.getHeight() / 2 - 10 * (res.GetValue(x + 1, 1) + (res.GetValue(x + 1, 2) * angle)));

                }
            }
            if(res.GetWidth() > 2) //rysowanie połączenia między ostatnim, a pierwszym punktem
            {
                if(view == 2)
                {
                    gc.strokeLine(  canvas.getWidth() / 2 + 10 * res.GetValue(0, 0),
                                    canvas.getHeight() / 2 - 10 * res.GetValue(0, 1),
                                    canvas.getWidth() / 2 + 10 * res.GetValue(res.GetWidth() - 1, 0),
                                    canvas.getHeight() / 2 - 10 * res.GetValue(res.GetWidth() - 1, 1));
                }
                else if(view == 3)
                {
                    gc.strokeLine(  canvas.getWidth() / 2 + 10 * (res.GetValue(0, 0) + (res.GetValue(0, 2) * angle)),
                                    canvas.getHeight() / 2 - 10 * (res.GetValue(0, 1) + (res.GetValue(0, 2) * angle)),
                                    canvas.getWidth() / 2 + 10 * (res.GetValue(res.GetWidth() - 1, 0) + (res.GetValue(res.GetWidth() - 1, 2) * angle)),
                                    canvas.getHeight() / 2 - 10 * (res.GetValue(res.GetWidth() - 1, 1) + (res.GetValue(res.GetWidth() - 1, 2) * angle)));
                }

            }
        }
    }

}