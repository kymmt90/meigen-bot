package rtb;

class Text {
    private String text;
    
    Text(String text) {
        if (text == null) throw new NullPointerException();
        this.text = text;
    }
    
    void addTo(StringBuilder builder) {
        if (builder == null) throw new NullPointerException();
        builder.append(text);
    }
    
    @Override
    public String toString() {
        return text;
    }
}
