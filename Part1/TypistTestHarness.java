public class TypistTestHarness {
    private static int total = 0, passed = 0;
    private static void check(String name, boolean condition) {
        total++;
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            System.out.println("[FAIL] " + name);
        }
    }
    public static void main(String[] args) {
        Typist t = new Typist('①', "TESTER", 0.5);
        // 1) slideBack clamp to zero
        t.slideBack(3);
        check("slideBack cannot go below zero", t.getProgress() == 0);
        // 2) burnout countdown + clear at zero
        t.burnOut(3);
        check("burnOut sets state", t.isBurntOut() && t.getBurnoutTurnsRemaining() == 3);
        t.recoverFromBurnout();
        check("burnout decrements to 2", t.getBurnoutTurnsRemaining() == 2 && t.isBurntOut());
        t.recoverFromBurnout();
        t.recoverFromBurnout();
        check("burnout clears at zero", !t.isBurntOut() && t.getBurnoutTurnsRemaining() == 0);
        // 3) resetToStart clears progress + burnout
        t.typeCharacter();
        t.typeCharacter();
        t.burnOut(2);
        t.resetToStart();
        check("resetToStart clears all", t.getProgress() == 0 && !t.isBurntOut() && t.getBurnoutTurnsRemaining() == 0);
        // 4) accuracy clamping
        t.setAccuracy(-0.2);
        check("accuracy clamps low to 0.0", t.getAccuracy() == 0.0);
        t.setAccuracy(1.5);
        check("accuracy clamps high to 1.0", t.getAccuracy() == 1.0);
        // 5) normal forward movement
        int before = t.getProgress();
        t.typeCharacter();
        check("typeCharacter increments progress", t.getProgress() == before + 1);
        System.out.println("\nSummary: " + passed + "/" + total + " passed");
    }
}