package org.darkmentat.GuitarScalesBoxes.Model;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Box
{
    public List<Point> Points;
    public int StartFret;
    public int EndFret;
    public int Width;

    public Box(GuitarModel fretBoard, Scale scale, Point startPoint, int maxWidth) {
        Map<NoteModel, Point> points = new TreeMap<>();
        StartFret = startPoint.x;
        EndFret = StartFret;
        Point currPoint = new Point(startPoint);

        boolean started = false;

        for(int y = startPoint.y; y >= 0; y--)
        {
            for(int x = startPoint.x-1; x < startPoint.x+maxWidth; x++)
            {
                if(x<0)
                    continue;
                if(y == startPoint.y && x < startPoint.x)
                    continue;

                currPoint.x = x;
                currPoint.y = y;
                NoteModel note = (NoteModel) fretBoard.Tab[currPoint.x][currPoint.y];

                if(!scale.NotesInScale.contains(note.Value))
                    continue;
                if(!scale.isNoteScaleTonic(note) && !started)
                    continue;

                started = true;

                if(points.containsKey(note))
                {
                    if(points.get(note).x - currPoint.x > 0)
                    {
                        points.put(note, new Point(currPoint));
                        if(currPoint.x < StartFret)
                            StartFret = currPoint.x;
                        if(currPoint.x > EndFret)
                            EndFret = currPoint.x;
                    }
                }
                else
                {
                    points.put(note, new Point(currPoint));
                    if(currPoint.x < StartFret)
                        StartFret = currPoint.x;
                    if(currPoint.x > EndFret)
                        EndFret = currPoint.x;
                }
            }
        }

        Points = new ArrayList<>(points.values());

        Width = EndFret - StartFret;
    }
}
