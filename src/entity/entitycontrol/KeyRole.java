package entity.entitycontrol;

import java.awt.event.KeyEvent;

public enum KeyRole
{
    MOVE_N(KeyEvent.VK_NUMPAD8, KeyEvent.VK_W), MOVE_S(KeyEvent.VK_NUMPAD2, KeyEvent.VK_S),
    MOVE_SE(KeyEvent.VK_NUMPAD3, KeyEvent.VK_D), MOVE_SW(KeyEvent.VK_NUMPAD1, KeyEvent.VK_A),
    MOVE_NE(KeyEvent.VK_NUMPAD9, KeyEvent.VK_E), MOVE_NW(KeyEvent.VK_NUMPAD7, KeyEvent.VK_Q),
    BIND_WOUNDS(KeyEvent.VK_B, -1), OBSERVE(KeyEvent.VK_O, -1),
    TOGGLE_CREEP(KeyEvent.VK_CONTROL, -1), TOGGLE_SEARCH(KeyEvent.VK_SHIFT, -1),
    DISMOUNT(KeyEvent.VK_EQUALS, -1), MANAGE_INVENTORY(KeyEvent.VK_I, -1),
    MANAGE_SKILLS(KeyEvent.VK_L, -1), MANAGE_CONTROLS(KeyEvent.VK_ESCAPE, -1),
    ATTACK1(KeyEvent.VK_1, -1), ATTACK2(KeyEvent.VK_2, -1), ATTACK3(KeyEvent.VK_3, -1),
    ATTACK4(KeyEvent.VK_4, -1), ATTACK5(KeyEvent.VK_5, -1);

    private KeyRole(int primary, int secondary)
    {
        primaryKeycode = primary;
        secondaryKeycode = secondary;
    }

    private int primaryKeycode, secondaryKeycode;

    public int getPrimaryKeycode() { return primaryKeycode; }
    public int getSecondaryKeycode() { return secondaryKeycode; }
    public void setPrimaryKeycode(int keycode) { this.primaryKeycode = keycode; }
    public void setSecondaryKeycode(int keycode){ this.secondaryKeycode = keycode; }

    public boolean triggersOn(int keycode)
    {
        return keycode == primaryKeycode || keycode == secondaryKeycode;
    }
}
