package items.takeableitems;

import entity.entitymodel.Equipment;

public class QuestItem extends TakeableItem {

    private int questId;

    public QuestItem(String name, boolean onMap, int questId) {
        super(name, onMap);
        this.questId = questId;
    }

    @Override
    public void activate(Equipment e) {

    }
}
