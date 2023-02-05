package asm.sample;

public class Foo {
    private int some;
    private String other;

    public void run(String a, String b) {
        System.out.println("Running..." + a + b);
    }

    @Override
    public String toString() {
        return "MyFoo: " + some + " - " + other;
    }
}
