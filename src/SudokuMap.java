public class SudokuMap {
    private Integer number;
    private Boolean isFixedNumber;

    public SudokuMap() {
    }

    public SudokuMap (int number) {
        this.number = number;
    }

    public Boolean getFixedNumber() {
        return isFixedNumber;
    }

    public void setFixedNumber(Boolean fixedNumber) {
        isFixedNumber = fixedNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
