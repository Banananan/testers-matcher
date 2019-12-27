package applause.testermatcher.model

final class Test {
    private final String testColumn1

    private final String testColumn2

    Test(String testColumn1, String testColumn2) {
        this.testColumn1 = testColumn1
        this.testColumn2 = testColumn2
    }

    String getTestColumn1() {
        return testColumn1
    }

    String getTestColumn2() {
        return testColumn2
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Test test = (Test) o

        if (testColumn1 != test.testColumn1) return false
        if (testColumn2 != test.testColumn2) return false

        return true
    }

    int hashCode() {
        int result
        result = testColumn1.hashCode()
        result = 31 * result + testColumn2.hashCode()
        return result
    }


    @Override
    String toString() {
        return "Test{" +
                "testColumn1='" + testColumn1 + '\'' +
                ", testColumn2='" + testColumn2 + '\'' +
                '}';
    }
}
