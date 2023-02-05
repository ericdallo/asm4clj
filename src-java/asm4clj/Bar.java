package asm4clj;

public class Bar {
    private int some;
    private String other;

    /**
     * Run something
     *
     */
    public void run(String a, String b) {
        System.out.println("Running..." + a + b);
    }

    @Override
    public String toString() {
        return "MyFoo: " + some + " - " + other;
    }
}
