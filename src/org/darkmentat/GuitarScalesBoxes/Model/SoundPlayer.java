package org.darkmentat.GuitarScalesBoxes.Model;

public interface SoundPlayer
{
    public void init();
    public void play(NoteModel note);
    public void play(String key);
}
