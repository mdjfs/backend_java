package communication;

public class User {
	private int id;
	private String method;
	public User(Integer id, String method){
	      this.id = id;
	      this.method = method;
	}
	public String Getmethod() {
		return method;
	}
	@Override
	public String toString()
	{
	   return "Employee [id=" + id + ", method=" + method +"]";
	}

}
