package item.takeableitem;

import EnitityModel.Equipment;

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
