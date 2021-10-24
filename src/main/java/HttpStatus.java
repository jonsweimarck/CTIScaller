public record HttpStatus(int status, String text) {
    @Override
    public String toString() {
        return status + " " +  text;
    }
}
