<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ReflectWork</h1>
<h6>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;version: 0.0.2</h6>

<p>What is it?

is a Framework, development with Java using the API of Reflection

What's the point?

To manage an application using Jsons</p>


<a href="https://imgur.com/3wKj1iq"><img src="https://i.imgur.com/3wKj1iq.png" title="source: imgur.com" /></a>

<h1>How to uses this Framework</h1>

<h3>Requeriments</h3>
 * Postman </br>
 * Apache Tomcat </br>
 * Java EE </br>
 * IDE with Java EE  </br>
 * PostgreSQL </br>

<h4>1. Create Database</h4>

<a href="https://imgur.com/3T0XAsU"><img src="https://i.imgur.com/3T0XAsU.png" title="source: imgur.com" /></a>
This is a representation of the database


Please create a new database with some name in PostgreSQL, after this, please execute this commands in SQL:

<h6>Users</h6>

```sql
CREATE TABLE users
(
    id_users serial NOT NULL primary key,
    name_users varchar,
    surname_users varchar,
    email_users varchar UNIQUE,
    password_users char(64),
    creationtime_users varchar
)
```
<h6>Profile</h6>

```sql
CREATE TABLE profile
(
    id_profile serial NOT NULL primary key,
    username_profile varchar UNIQUE
)
```

<h6>Profile Users</h6>

```sql
CREATE TABLE users_profile
(
    id_users_profile serial NOT NULL primary key,
    id_profile integer NOT NULL,
    id_users integer NOT NULL,
    FOREIGN KEY (id_users) REFERENCES users(id_users)
    FOREIGN KEY (id_profile) REFERENCES profile(id_profile)
)
```
<h6>Object</h6>

```sql
CREATE TABLE object
(
    id_object serial NOT NULL primary key,
    name_object integer NOT NULL UNIQUE
)
```

<h6>Method</h6>

```sql
CREATE TABLE method
(
    id_method serial NOT NULL primary key,
    name_method integer NOT NULL,
    id_object_method integer NOT NULL,
    FOREIGN KEY (id_object_method) REFERENCES object(id_object)
)
```

<h6>Permissions</h6>

```sql
CREATE TABLE permissions
(
    id_permissions serial NOT NULL primary key,
    id_method_permissions integer NOT NULL,
    id_profile_permissions integer NOT NULL,
    FOREIGN KEY (id_method_permissions) REFERENCES method(id_method)
    FOREIGN KEY (id_profile_permissions) REFERENCES profile(id_profile)
)
```


<p>... objName, methodName, params, types ???

Bienvenido al esquema de trabajo, como es un FrameWork desarrollado en Java, gracias a la API de reflection cuando necesites un metodo en especifico, lo puedes llamar dinamicamente desde un solo endpoint... Por ejemplo !

Metodo <b>registerUser</b>: </p>

```java
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
float<br/>


<h2>Adaptando el proyecto a tu PC</h2>
<h3>Creando la base de datos</h3>
<a href="https://imgur.com/3T0XAsU"><img src="https://i.imgur.com/3T0XAsU.png" title="source: imgur.com" /></a>
<p>Para el manejo de los objetos y metodos, necesitamos plantear ese esquema para modo de que el sistema pueda funcionar, el funcionamiento es simple. Al momento de que el servidor arranca, automaticamente carga todos los objetos y metodos que no estan guardados en la base de datos. El objeto de seguridad relaciona un perfil con permisos, donde esos permisos dicen a fin de cuentas que metodos puede manejar y cuales no. 

Nota: Por el momento, como no existen roles, se tienen que agregar los permisos para cada perfil manualmente.
Nota 2: Si el servidor se reinicia y hubo metodos borrados, quedaran registrados en la base de datos.

Esperamos corregir eso para una proxima version. Siguiendo con el framework, estas son las tablas que necesitamos:</p>

<h3>Users</h3>

```sql
CREATE TABLE users
(
    id_users serial NOT NULL primary key,
    name_users varchar,
    surname_users varchar,
    email_users varchar UNIQUE,
    password_users char(64),
    creationtime_users varchar
)
```
<h3>Profile</h3>

```sql
CREATE TABLE profile
(
    id_profile serial NOT NULL primary key,
    username_profile varchar UNIQUE
)
```

<h3>Profile Users</h3>

```sql
CREATE TABLE users_profile
(
    id_users_profile serial NOT NULL primary key,
    id_profile integer NOT NULL,
    id_users integer NOT NULL,
    FOREIGN KEY (id_users) REFERENCES users(id_users)
    FOREIGN KEY (id_profile) REFERENCES profile(id_profile)
)
```
<h3>Object</h3>

```sql
CREATE TABLE object
(
    id_object serial NOT NULL primary key,
    name_object integer NOT NULL UNIQUE
)
```

<h3>Method</h3>

```sql
CREATE TABLE method
(
    id_method serial NOT NULL primary key,
    name_method integer NOT NULL,
    id_object_method integer NOT NULL,
    FOREIGN KEY (id_object_method) REFERENCES object(id_object)
)
```

<h3>Permissions</h3>

```sql
CREATE TABLE permissions
(
    id_permissions serial NOT NULL primary key,
    id_method_permissions integer NOT NULL,
    id_profile_permissions integer NOT NULL,
    FOREIGN KEY (id_method_permissions) REFERENCES method(id_method)
    FOREIGN KEY (id_profile_permissions) REFERENCES profile(id_profile)
)
```

<b>(!) Algo muy importante: Hay que anexar un perfil Guest, que sera el default de cualquier persona, que usualmente nada mas tendra permiso a metodos de login y register. En la tabla perfil se inserta un perfil llamado GUEST con el ID 1, y a ese perfil le añadiremos permisos mas adelante</b>

<p>Cabe destacar de antemano que se necesita tener Java EE y algun IDE de desarrollo... Despues de eso, deberas ir a tu carpeta de proyectos del IDE (en el caso de eclipse el workspace) y Clonar este repositorio, una vez abierto se verá algo asi:</p>
<a href="https://imgur.com/nMThDIJ"><img src="https://i.imgur.com/nMThDIJ.png" title="source: imgur.com" /></a>

<p><b>(!)</b> Es importante que modifiques la URI y coloques donde esta colocado tu proyecto en el workspace.. Ejemplo en windows seria C:/Users/Desktop/eclipse_workspace/backend_java Por ejemplo... de esa URI depende todos los demas archivos.

Tambien es importante que crees esos 3 archivos de configuracion, segun eso se regiran muchas cosas de la aplicacion</p>

<h2>Entendiendo y configurando archivos</h2>

<h4>config_db.properties</h4>

```properties
db.driver= (tu driver)
db.url= (url de tu base de datos)
db.username= (username de la base de datos)
db.password= (password de la base de datos)
```

<h4>config_querys.properties</h4>

```properties
insert.object= INSERT INTO object (name_object) VALUES (?);
insert.method= INSERT INTO method (name_method, id_object_method) VALUES (?, ?);
insert.users = INSERT INTO users (name_users, surname_users, email_users, password_users, creationtime_users) VALUES (?, ?, ?, ?, ?);
select.where.name_object=SELECT *FROM object WHERE name_object=?;
select.where.name_method=SELECT *FROM method WHERE name_method=?;
select.where.id_profile=SELECT *FROM profile WHERE id_profile=?;
select.profile=SELECT *FROM profile;
select.permissions=SELECT *FROM permissions;
select.where.id_profile_permissions=SELECT *FROM permissions WHERE id_profile_permissions=?;
select.where.id_method=SELECT *FROM method WHERE id_method=?;
select.where.id_object=SELECT *FROM object WHERE id_object=?;
innerjoin.object.method=SELECT method.id_method, method.name_method, object.name_object FROM method INNER JOIN object ON method.id_object_method = object.id_object;
```

<p>Usualmente esos son los querys que se usan hasta ahora, puedes anexar esos y otros si prefieres.</p>


<h4>config_pool.properties</h4>

```properties
maxconnections= (maximas conexiones al mismo tiempo para la base de datos)
hops= (instancias creadas al mismo tiempo "mordiscos")
```


<h2>Agregando Permisos</h2>

<p>Cuando inicies el servidor por primera vez, si no hay errores, te cargara automaticamente los objetos y metodos en la base de datos... ¿Te acuerdas del usuario GUEST? si no, chequea arriba, si ya lo has hecho. Tienes que agarrar las ID de los metodos que quieras darle permiso al usuario GUEST e insertarlo en la tabla Permisos</p>

<h2>Listo!</h2>

<p>Ya puedes usarlo enviando y recibiendo jsons, probando creando metodos nuevos que hagan otras cosas. Si llegaste hasta aqui, gracias por apoyarnos. Estaremos avanzando y actualizando mas cosas con el tiempo !! </p>


<br/>
<br/>
<br/>
<br/>
Collaborators of the Backend:

luisnvf7 -> Luis Villalobos <br/>
fannyzhl -> Xiao Zhao <br/>
elimora -> Eli Mora <br/>
TheSuperHack -> Heberto Urribarri <br/>
rsulbaranc -> Ricardo Sulbaran <br/>
pablocastillo123 -> Pablo Castillo <br/>
mdjfs -> Marcos Fuenmayor
