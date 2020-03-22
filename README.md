<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ReflectWork</h1>

<p>¿Que es?

ReflectWork es un Framework desarrollado en Java usando la API de Reflection ¿Descriptivo no?

Dejando el sentido del humor de lado, este trabajo se hizo con la finalidad de ahorrar trabajo repetitivo al desarrollador web, con la finalidad de que se dedique a hacer las funciones que necesita particularmente. ¿Como usarlo? el Framework trabaja estrictamente con Jsons enviados en forma de Raw. Plantea comunicaciones de esa forma</p>


<a href="https://imgur.com/3wKj1iq"><img src="https://i.imgur.com/3wKj1iq.png" title="source: imgur.com" /></a>

<p>... objName, methodName, params, types ???

Bienvenido al esquema de trabajo, como es un FrameWork desarrollado en Java, gracias a la API de reflection cuando necesites un metodo en especifico, lo puedes llamar dinamicamente desde un solo endpoint... Por ejemplo !

Metodo <b>registerUser</b>: </p>

```
  private JSONManage json_manage = new JSONManage();
	
	public JsonObject registerUser(String name, String surname, String email, String password) {
		HashPassword hashing = new HashPassword();
		try 
		{
			String hash = hashing.toHashPassword(password);
			DBComponent database = Pool.getDBInstance();
			if( database != null) {
				Calendar c = new GregorianCalendar();
				String creationtime = "Fecha: " + c.get(Calendar.DAY_OF_MONTH) + "/"
						+ c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR)+ " Hora: "
						+ Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE + ":" + Calendar.SECOND;
				if( email.contains("@")) {
					database.exeSimple(new Query("insert.users", new Object[] {name, surname, email, hash, creationtime}));
					Pool.returnDBInstance(database);
					return json_manage.ReportSuccessMessage("Welcome "+name+" "+surname+" You're registered !");
				}
				else {
					Pool.returnDBInstance(database);
					return json_manage.ReportErrorMessage("Verify your email address");
				}
			}
			else {
				return json_manage.ReportErrorMessage("Error in database consults");
			}
		} catch (NoSuchAlgorithmException | SQLException e) 
		{
			e.printStackTrace();
			return json_manage.ReportErrorMessage(e.getMessage());
		}
	}
```
<p>Vemos que es un metodo que recibe los datos de un usuario en string, se encarga de verificarlos y meterlos en una base de datos, sencillo... Lo interesante esta, que simplemente no hay que llamarlo en <b>ninguna parte del codigo</b> ¿Y entonces como se ejecuta diras? Pues...</p>

<a href="https://imgur.com/udkkW7U"><img src="https://i.imgur.com/udkkW7U.png" title="source: imgur.com" /></a>

<p>Tu despreocupate de como va a ser llamado el codigo, gracias al Framework esto es posible, simplemente tienes que poner el nombre de tu Clase, el nombre del metodo, los parametros y los tipos... 

En el ejemplo anterior tenemos el metodo registerUser que se encuentra en la clase Register, y tiene 4 parametros de tipo String<p>
	
<h3>Tipos de Parametros soportados:</h3>
string<br/>
byte<br/>
double<br/>
char<br/>
Character<br/>
boolean<br/>
ArrayList<br/>
int<br/>
Integer<br/>
float



Collaborators of the Backend:

luisnvf7 -> Luis Villalobos <br/>
fannyzhl -> Xiao Zhao <br/>
elimora -> Eli Mora <br/>
TheSuperHack -> Heberto Urribarri <br/>
rsulbaranc -> Ricardo Sulbaran <br/>
pablocastillo123 -> Pablo Castillo <br/>
mdjfs -> Marcos Fuenmayor
