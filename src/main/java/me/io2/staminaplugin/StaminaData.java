package me.io2.staminaplugin;

public class StaminaData {
    public double maxStamina;
    public double currentStamina;
    public MSTimer timer = new MSTimer();
    public boolean jumpDetected = false;
    public StaminaData(double maxStamina, double currentStamina) {
        this.maxStamina = maxStamina;
        this.currentStamina = currentStamina;
    }
}
