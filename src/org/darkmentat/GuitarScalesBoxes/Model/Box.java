package org.darkmentat.GuitarScalesBoxes.Model;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Box
{
    public final List<Point> Points;
    public final int StartFret;
    public final int EndFret;
    public final int Width;

    public Box(GuitarModel fretBoard, Scale scale, int startFret, int endFret) {

        Map<NoteModel, List<Point>> points = new TreeMap<>();

        NoteModel startNote = null;

        for(int y = fretBoard.StringCount-1; y >= 0; y--)
            for(int x = 0; x < fretBoard.FretCount; x++)
            {
                if(y == fretBoard.StringCount-1 && x < startFret-1)
                    continue;

                NoteModel note = (NoteModel) fretBoard.Tab[x][y];

                if(scale.isNoteOnScale(note))
                {
                    if(startNote == null)
                        startNote = note;

                    if(note.compareTo(startNote) < 0)
                        continue;

                    if(!points.containsKey(note))
                        points.put(note, new ArrayList<Point>());
                    points.get(note).add(new Point(x,y));
                }
            }

        Points = new ArrayList<>(points.size());
        ArrayList<Point> checked = new ArrayList<>();
        for (NoteModel note : points.keySet())
        {
            boolean allInInterval = true;
            checked.clear();
            checked.addAll(points.get(note));
            //region Check interval
            for (int i = 0; i < checked.size(); i++)
                if(!inInterval(checked.get(i), startFret, endFret))
                {
                    checked.remove(i);
                    i--;
                }

            if(checked.size() == 1)
            {
                Point current = checked.get(0);
                if(current.x > endFret && current.y == 0)
                    break;
                Points.add(current);
                continue;
            }

            if(checked.isEmpty())
            {
                checked.addAll(points.get(note));
                allInInterval = false;
            }
            //endregion
            //region Check width
            for (int i = 0; i < checked.size(); i++)
                if(!acceptedWidth(checked.get(i)))
                {
                    checked.remove(i);
                    i--;
                }

            if(checked.size() == 1)
            {
                Point current = checked.get(0);

                Points.add(current);
                continue;
            }

            if(checked.isEmpty())
                checked.addAll(points.get(note));
            //endregion
            //region Check distance to interval
            int minDistance = fretBoard.FretCount;
            for (Point p : checked)
            {
                int d = distanceToInterval(p, startFret, endFret);
                if (d < minDistance) minDistance = d;
            }

            for (int i = 0; i < checked.size(); i++)
                if(distanceToInterval(checked.get(i), startFret, endFret) > minDistance)
                {
                    checked.remove(i);
                    i--;
                }

            if(checked.size() == 1)
            {
                Point current = checked.get(0);
                if(current.x > endFret && current.y == 0)
                    break;
                Points.add(current);
                continue;
            }
            //endregion
            //region Check righter and lefter
            int maxX = 0;
            int righterIndex = 0;
            int minX = fretBoard.FretCount;
            int lefterIndex = 0;
            for (int i = 0; i < checked.size(); i++)
            {
                if (checked.get(i).x > maxX)
                {
                    maxX = checked.get(i).x;
                    righterIndex = i;
                }
                if (checked.get(i).x < minX)
                {
                    minX = checked.get(i).x;
                    lefterIndex = i;
                }
            }
            Point current;
            if(allInInterval)
                current = checked.get(righterIndex);
            else
                current = checked.get(lefterIndex);

            if(current.x > endFret && current.y == 0)
                break;
            Points.add(current);
            //endregion
        }

        int end = 0;
        int start = fretBoard.FretCount;

        for (Point p : Points)
        {
            if(p.x > end) end = p.x;
            if(p.x < start) start = p.x;
        }

        StartFret = start;
        EndFret = end;

        Width = EndFret - StartFret;
    }

    private boolean inInterval(Point p, int s, int e){
        return s <= p.x && p.x <= e;
    }
    private boolean acceptedWidth(Point p){
        int a =  firstBoxNoteOnString(p.y);
        return a < 0 || p.x - a < 5;
    }
    private int distanceToInterval(Point p, int s, int e){
        if(p.x < s) return s - p.x;
        if(p.x > e) return p.x - e;
        return 0;
    }
    private int firstBoxNoteOnString(int y){
        for (Point point : Points)
            if(point.y == y)
                return point.x;
        return -1;
    }
}
