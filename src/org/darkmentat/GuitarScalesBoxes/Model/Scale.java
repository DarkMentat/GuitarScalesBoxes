package org.darkmentat.GuitarScalesBoxes.Model;

import java.util.Arrays;
import java.util.List;

import static org.darkmentat.GuitarScalesBoxes.Model.NoteModel.NoteValue;

public class Scale
{
    public final NoteModel Tonic;
    public final String StepSequence;
    public final List<NoteValue> NotesInScale;

    public Scale(NoteValue tonic, String stepSequence) {
        this(new NoteModel(tonic, 0), stepSequence);
    }
    public Scale(NoteModel tonic, String stepSequence) {
        Tonic = tonic;
        StepSequence = stepSequence;
        NotesInScale = Arrays.asList(parseStepSequence(stepSequence));
    }

    private NoteValue[] parseStepSequence(String stepSequence){
        char[] steps = stepSequence.toCharArray();
        NoteValue[] notes = new NoteValue[steps.length+1];

        int k = 0;
        NoteModel curr = Tonic;
        notes[k++] = curr.Value;
        for(char step : steps)
        {
            for(int i = 0; i < Character.getNumericValue(step); i++)
                curr = curr.getNext();
            notes[k++] = curr.Value;
        }

        return notes;
    }

    public boolean isNoteOnScale(NoteModel note){
        return NotesInScale.contains(note.Value);
    }
    public boolean isNoteScaleTonic(NoteModel note){
        return Tonic.Value == note.Value;
    }
}
