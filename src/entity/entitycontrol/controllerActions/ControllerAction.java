package entity.entitycontrol.controllerActions;

//This represents an action that can be performed by a Controller. It is used to dynamically attach functionality to an
//Entity on an individual basis.

public abstract class ControllerAction {

    boolean waitingToExecute=false;

    //This should be called when something wants the action to happen next time this action is updated
    public final void activate(){
        waitingToExecute = true;
    }

    //This will execute the action if activate() has been called and it has not executed since then
    public final void update(){
        if(waitingToExecute){
            execute();
            waitingToExecute = false;
        }
    }

    protected abstract void execute();

}
