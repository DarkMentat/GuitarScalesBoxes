package org.darkmentat.GuitarScalesBoxes.Model;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Box
{
    public List<Point> Points;

    public Box(GuitarModel fretBoard, Scale scale, Point startPoint) {
        Map<NoteModel, Point> points = new TreeMap<>();
        int maxWidth = 5;

        Point currPoint = new Point(startPoint);

        for(int y = startPoint.y; y >= 0; y--)
        {
            for(int x = startPoint.x-1; x < startPoint.x+maxWidth; x++)
            {
                if(y == startPoint.y && x < startPoint.x)
                    continue;

                currPoint.x = x;
                currPoint.y = y;
                NoteModel note = (NoteModel) fretBoard.Tab[currPoint.x][currPoint.y];
                if(!scale.NotesInScale.contains(note.Value))
                    continue;

                if(points.containsKey(note))
                {
                    if(points.get(note).x - currPoint.x > 0)
                        points.put(note, new Point(currPoint));
                }
                else
                    points.put(note, new Point(currPoint));
            }
        }

        Points = new ArrayList<>(points.values());
    }
}
