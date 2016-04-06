public class Token
{
	String token_type, token_name, tag_type_or_attr_value, parent;
    
    public Token(String token_type , String token_name , String tag_type_or_attr_value) {
        this.token_type = token_type;
        this.token_name = token_name;
        this.tag_type_or_attr_value= tag_type_or_attr_value;
	this.parent = parent;
    }
	

    
    public String getToken_type() {
        return token_type;
    }

    public String getToken_name() {
        return token_name;
    }
    
    public String getTag_type_or_attr_value() {
        return tag_type_or_attr_value;
    }
	
    public String getParent() {
        return parent;
    }

}