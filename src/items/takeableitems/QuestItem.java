package items.takeableitems;

import entity.entitymodel.Equipment;
import savingloading.Visitor;

public class QuestItem extends TakeableItem {

    private int questId;

    public QuestItem(String name, boolean onMap, int questId) {
        super(name, onMap);
        this.questId = questId;
    }

    @Override
    public void activate(Equipment e) {

    }

    public int getQuestId(){
        return questId;
    }

    @Override
    public void accept(Visitor v) {
        v.visitQuestItem(this);
    }
}
