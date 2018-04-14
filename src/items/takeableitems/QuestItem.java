package items.takeableitems;

import entitymodel.Equipment;

public class QuestItem extends TakeableItem {

    private int questId;

    public QuestItem(String name, int questId) {
        super(name);
        this.questId = questId;
    }

    @Override
    public void activate(Equipment e) {

    }
}
