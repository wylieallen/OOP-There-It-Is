package gameview;

import gameobject.GameObject;
import guiframework.DisplayState;
import guiframework.displayable.Displayable;

import java.util.HashMap;
import java.util.Map;

public class GameDisplayState extends DisplayState
{
    private Map<GameObject, Displayable> spriteMap;

    public GameDisplayState()
    {
        spriteMap = new HashMap<>();
    }



}
