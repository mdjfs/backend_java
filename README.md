<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ReflectWork</h1>
<h6>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;version: 0.0.2</h6>

<p>What is it?

is a Framework, development with Java using the API of Reflection. You can create classes and methods in one package, and methods and objects has been called dynamically

What's the point?

To manage an application using Jsons</p>


<a href="https://imgur.com/3wKj1iq"><img src="https://i.imgur.com/3wKj1iq.png" title="source: imgur.com" /></a>

<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;How to uses this Framework</h1>

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
<h3>2. Download Project </h3>

Download this as a zip or clone in your IDE workspace, if you download please unzip this in your IDE workspace. After, open your IDE and import project of projects existing in your workspace

<h3> 3. Configure Project </h3>

Enter in the folder Config and create the .properties files for config_db and config_pool, you need to create this files:<br/><br/>
config_pool.properties<br/>
maxconnections={number};<br/>
hops={number};<br/><br/>
config_db.properties<br/>
db.driver=org.postgresql.Driver; // to use PostgreSQL<br/>
db.url=jdbc:postgresql://localhost:5432/{YourNameDB};<br/>
db.username={YourUsernameDB};<br/>
db.password={PassofUsernameDB};<br/><br/>
<img src="https://i.imgur.com/gq6Tfjo.png" title="source: imgur.com" />
<img src="https://i.imgur.com/epRaDT5.png" title="source: imgur.com" />
<img src="https://i.imgur.com/ESydrHJ.png" title="source: imgur.com" />

<b>Very important:</b> The database needs the previous tables

<h3> 4. Runs a Server </h3>

In your IDE with Java EE, open the project and create a server in servers tab. Use a apache tomcat 8.0 or higher, and add the dynamic web project in server. after, start the server.
<img src="https://i.imgur.com/gJXfszv.png" title="source: imgur.com" />
<img src="https://i.imgur.com/JtwcJA3.png" title="source: imgur.com" />
<img src="https://i.imgur.com/SoSb0Xo.png" title="source: imgur.com" />
<img src="https://i.imgur.com/mahaCuo.png" title="source: imgur.com" />

<h3> 5. Test Server </h3>

Open postman and create new request in POST method to this endpoint: localhost:8080/TeamBackendJava/Dispatcher or localhost:8080/backend_java/Dispatcher, next go to body raw, and write Json, click in send and wait responses. <br/> <br/>

If the response is a JSON. You're using the Framework <br/>
Else if the response is a status 500 with html, servers finds a big bug  <br/>
Else the response is a status 404, server is turn off or isn't the endpoint  <br/><br/>

<img src="https://i.imgur.com/i0RMDiW.png" title="source: imgur.com" />
The default response with json empty is it <br/><br/>
<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Some Examples</h1>
<br/><br/><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;First, you needs register and login to uses dynamic objs and methods</b><br/>
<img src="https://i.imgur.com/nlIF8bj.png" title="source: imgur.com" />
<img src="https://i.imgur.com/txy7TB3.png" title="source: imgur.com" />
<img src="https://i.imgur.com/InpbAXm.png" title="source: imgur.com" />
<img src="https://i.imgur.com/qXkSJCM.png" title="source: imgur.com" />
<img src="https://i.imgur.com/9ygazdO.png" title="source: imgur.com" />
<br/><br/><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Next, you have session. The servlet needs this params:</b><br/><br/>
<img src="https://i.imgur.com/SEkYjva.png" title="source: imgur.com" />
<br/><br/><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Oh well ! Let's try, make a obj and method and restart the server</b><br/><br/>
<img src="https://i.imgur.com/d1OBeDn.png" title="source: imgur.com" />
<br/><br/><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;But... If you try to uses this method, server says this:</b><br/><br/>
<img src="https://i.imgur.com/Fz2ssSz.png" title="source: imgur.com" />
<br/><br/><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You need give permissions in SQL, like this:</b><br/><br/>
<img src="https://i.imgur.com/7Ykk6io.png" title="source: imgur.com" />
<br/><br/><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Note: The objects and methods refresh in DB with server restart</b><br/><br/>
<img src="https://i.imgur.com/ToIfCIX.png" title="source: imgur.com" />
<img src="https://i.imgur.com/Sp8y6JN.png" title="source: imgur.com" />
<br/><br/><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;And repeat process for method, next, restart the server</b><br/><br/>
<img src="https://i.imgur.com/CvlFJxu.png" title="source: imgur.com" />
<br/><br/><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ok, now you have permissions, good !</b><br/><br/>
<img src="https://i.imgur.com/icFS1ov.png" title="source: imgur.com" /> <br/><br/>

<h4>Types of paramateres supported:</h4>
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
