package gameview.displayable.widget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dontf on 4/21/2018.
 */
public interface DialogObservable {

    List <DialogObserver> observers = new ArrayList<>();

    public void register (DialogObserver observer);
    public void unregister (DialogObserver observer);
    public void notifyAllObservers (String message);
}
