package aux;

import java.util.ArrayList;

public class DetectarArrays {
	private String Values = "";
	private String TypeofValues = "";
	private String paramsInt = "[";
	private String paramsFloat = "[";
	private String paramsString = "[";
	
	public DetectarArrays(String Values, String TypeofValues) {
		//Values, type of values
		this.TypeofValues = TypeofValues;
		this.Values = Values;
	}
	
	public String getparamsInt() {
		return paramsInt;
	}
	public String getparamsFloat() {
		return paramsFloat;
	}
	public String getparamsString() {
		return paramsString;
	}
	
	public void ProcesarArrays() {
		ArrayList<String> Types=new ArrayList<String>();
		boolean open_array = false;
		String save_characters = "";
		int number_of_ints = 0;
		int number_of_floats = 0;
		int number_of_strings = 0;
		for (char c: TypeofValues.toCharArray ())
		{
			if(open_array) {
				if(c == ',' || c == ']') {
					Types.add(save_characters);
					if(save_characters.toLowerCase().equals("\"int\"")) {
						number_of_ints++;
					}
					if(save_characters.toLowerCase().equals("\"float\"")) {
						number_of_floats++;
					}
					if(save_characters.toLowerCase().equals("\"string\"")) {
						number_of_strings++;
					}
					save_characters = "";
				}
				if(c != ',')
					save_characters += c;
				if(c == ']') {
					open_array = false;
				}
			}
			if(c == '[') {
				open_array = true;
			}
		}
		save_characters = "";
		int i = 0 ;
		int cont_int = 0;
		int cont_float = 0;
		int cont_string = 0;
		for (char c: Values.toCharArray ())
		{
			if(open_array) {
				if(c == ',' || c == ']') {
					if(Types.get(i).toLowerCase().equals("\"int\"")){
						cont_int++;
						if(cont_int >= number_of_ints) {
							paramsInt += save_characters + "]";
						}
						else {
							paramsInt += save_characters + ", ";
						}
					}
					if(Types.get(i).toLowerCase().equals("\"float\"")){
						cont_float++;
						if(cont_float >= number_of_floats) {
							paramsFloat += save_characters + "]";
						}
						else {
							paramsFloat += save_characters + ", ";
						}
					}
					if(Types.get(i).toLowerCase().equals("\"string\"")){
						cont_string++;
						if(cont_string >= number_of_strings) {
							paramsString += save_characters + "]";
						}
						else {
							paramsString += save_characters + ", ";
						}
					}
					save_characters = "";
					i++;
					if(c == ']') {
						open_array = false;
					}
				}
				if(c!=',')
					save_characters += c;
			}
			if(c == '[') {
				open_array = true;
			}
		}
		
		
	}

}
