# Trabajo_DAD_Secondflow
Trabajo pagina web Diseño de Aplicaciones Distribuidas URJC 2023
<div align="center"><img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/logo.png"width="500" height="300"/> </div>
<h2>Miembros:</h2>
Sergio Alvarez Scharfhausen<br>
Martin Alami Mochi<br>
Eduard Vasile Stancu
<h1> Índice </h1>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#-fase-1-"> Fase 1  </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#descripcion">-&nbsp &nbsp Descripción  </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#entidades">-&nbsp &nbsp Entidades </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#funcionalidades">-&nbsp &nbsp Funcionalidades </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#funcionalidades-del-servicio-interno">-&nbsp &nbsp Funcionalidad servicio interno </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/tree/main#-fase-2-"> Fase 2  </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/tree/main#-pantallas-principales-">-&nbsp &nbsp Pantallas principales  </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/tree/main#-diagrama-de-navegaci%C3%B3n-">-&nbsp &nbsp Digrama de navegacion </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/tree/main#-diagrama-de-clases-uml-">-&nbsp &nbsp Diagrama de clases uml </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/tree/main#-diagrama-entidadrelaci%C3%B3n-">-&nbsp &nbsp Diagrama Entidad/Relacion </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#-fase-3-"> Fase 3  </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#-instrucciones-de-despliegue-de-la-aplicaci%C3%B3n-">-&nbsp &nbspInstrucciones de despliegue de aplicacion </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#-compilaci%C3%B3n-">-&nbsp &nbspCompilacion  </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#-creacion-de-instancia-openstack-">-&nbsp &nbspCreacion instacia OpenStack  </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#-subida-de-jar-">-&nbsp &nbspSubida del .jar </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#-instalaciones-en-maquina-">-&nbsp &nbspInstalaciones en maquina  </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#-ejecutar-la-aplicaci%C3%B3n-">-&nbsp &nbspEjecucion de la aplicación  </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#-fase-4-"> Fase 4  </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#-diagrama-de-conexiones-">-&nbsp &nbsp Diagrama de conexiones </a>
<br> <a href="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-#-video-">-&nbsp &nbsp Video </a>


<h1 align="center"> Fase 1 </h1>
<h2>Descripción:</h2>
Se trata de una web de compra/venta de artículos de segunda mano entre particulares.

<h2>Entidades:</h2>
<strong>-&nbspUSUARIO:&nbsp &nbsp</strong> Representara a las personas registradas en la pagina.
<br><strong>-&nbspPRODUCTO:&nbsp &nbsp</strong> Representa los artículos subidos a la plataforma por parte de los usuarios.
<br><strong>-&nbspMENSAJES:&nbsp &nbsp</strong> Representa los mensajes que se envian entre usuarios cuando quieren realizar una compra.
<br><strong>-&nbspCONVERSACIONES:&nbsp &nbsp</strong> Representa el conjunto de Chats entre usuarios con su correspondiente lista de mesajes.

<h2>Funcionalidades:</h2>
<strong>-&nbspPÚBLICAS:&nbsp &nbsp</strong> visualizar productos y perfiles de usuarios.
<br><strong>-&nbspPRIVADAS:&nbsp &nbsp</strong> Subir artículos, realizar comprar/ventas de productos y comunicación entre usuarios.

<h2>Funcionalidades del servicio Interno:</h2>
Mensajería entre usuarios y envió de correo de confirmación de creación de cuenta y confirmación transacciones.


<h1 align="center"> Fase 2 </h1>

<h2> Pantallas principales </h2>
<div align="center">
<h3> Pantalla de inicio </h3>
Sin iniciar Sesion: <br>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/inicioSin.jpg" alt="android" width="550" height="300"/> 
<br>Iniciando sesion: <br>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/inicioSesion.jpg" alt="android" width="550" height="300"/> 
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/incioCon.jpg" alt="android" width="550" height="300"/> 
</div>
<div align="justify">

Pantalla principal donde se visualizan todos los artículos en venta así como enlaces a: formulario para subir un nuevo producto, acceder al perfil del usuario y poder acceder a sus conversaciones. En todas las páginas habrá una cabecera un un pie de pagina donde, pulsando al logo desde cualquier lugar, volverá a esta pagina principal. Se podrá realizar una búsqueda de productos por nombre.
</div>

<div align="center">
<h3> Registro nuevo usuario </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/registro.jpg" alt="android" width="550" height="300"/> </div>
<div align="justify">
Ventana donde se podrá crear un nuevo usuario, unicamente rellenando los campos requeridos, donde todos los campos son de obligatorio relleno.
</div>

<div align="center">
<h3> Nuevo producto </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/formularioProducto.jpg" alt="android" width="550" height="300"/> </div>
<div align="justify">
Ventana donde se podrá poner en venta un nuevo artículo, unicamente rellenando los campos requeridos, todos los campos son de obligatorio relleno salvo la imagen, que en caso de no ponerla, se mostrará una genérica.
</div>

<div align="center">
<h3> Visualización de un producto </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/verProducto.jpg" alt="android" width="550" height="300"/> </div>
<div align="justify">
En esta página se visualiza un articulo subido previamente con todos los campos, dando la posibilidad a su creador de editarlo o eliminarlo y a su cliente potencial de comprarlo.
</div>

<div align="center">
<h3> Eliminación de un producto </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/productoEliminado.jpg" alt="android" width="550" height="300"/> </div>
<div align="justify">
Retirar articulo de tu lista de productos y de cualquier lugar de la base de datos

<div align="center">
<h3> Modificación de un producto </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/formularioEdicion.jpg" alt="android" width="550" height="300"/> </div>
<div align="justify">
Formulario para editar un articulo previamente puesto en venta para su modificación


<div align="center">
<h3> Compra de un artículo </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/chat.jpg" alt="android" width="550" height="300"/> </div>
Al seleccionar comprar un articulo desde la vista de su visualización se accede al chat donde se establece el dialogo comprador-vendedor, donde este último puede acceder a vender el articulo.


<div align="center">
<h3> Perfil de usuario </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/perfil.jpg" alt="android" width="550" height="300"/> </div>
Ventana donde aparece informacion principal de un usuario como su nombre, sus productos en venta y un hipervinculo al historial de compra


<div align="center">
<h3> Historial de compra </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/historialCompras.jpg" alt="android" width="550" height="300"/> </div>
Pagina donde figurarán los articulos adquiridos por un usuario concreto

<div align="center">
<h3> Bandeja de conversaciones</h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/conversaciones.jpg" alt="android" width="550" height="300"/> </div>
Pantalla donde se podrá acceder a las distintas conversaciones que tenga uno abiertas


<h2> Diagrama de navegación </h2>
<div align="center">
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/diagramaNavegacion.png" alt="android" width="550" height="300"/> </div>
  
<h2> Modelo de datos </h2>

<h3> Diagrama de clases UML </h3>
  <div align="center">
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/UML_Fase4.png" alt="android" width="800" height="500"/> </div>

<h3> Diagrama Entidad/Relación </h3>
<div align="center">
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/entidades%20base%20de%20datos.png" alt="android" width="450" height="300"/> </div>
  
  <div align="center">
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/relacionesEntidades.png" alt="android" width="550" height="300"/> </div>

<h1 align="center"> Fase 3 </h1>
  
<h2> Instrucciones de despliegue de la aplicación </h2>
<h3> Compilación </h3>
1.- Se añade plugin especifico de jar al pom.xml en el cual se indica cual es la clase principal del proyecto entre otras cosas<br>
2.- Desde Eclipse seleccionando el nombre del proyecto -> Run as -> 4 Maven Build y en Goals poner package spring-boot:repackage, de forma que creamos el jar<br>
3.- Repetir proceso para el Servicio Interno<br>
4.- Subir los archivos .jar generados a myR<br>
<h3> Creacion de Instancia OpenStack </h3>
1.- Acceder a la pagina de OpenStack e introducir las credenciales proporcionadas<br>
2.- Crear una nueva instancia: autogenerar Ip flotante asociada<br>
3.- Crear una nueva llave y asociarla a la instancia creada, asegurase de que tiene el menor numero de permisos(chmod 400) <br>
4.- Crear security group para el puerto de tu aplicación y asociarlo a la instancia<br>
<h3> Subida de jar </h3>
1.-Teniendo los jar en el myR los copiamos a la maquina virtual con este comando: scp -i "RUTA DE LA LLAVE" "RUTA DEL .JAR" ubuntu@"IP FLOTANTE AUTOGENERADA":/home/ubuntu<br>
2.-Entramos en la maquina virtual ssh con el siguiente comando: ssh -i  "RUTA DE LA LLAVE" ubuntu@"IP FLOTANTE AUTOGENERADA"<br>
<h3> Instalaciones en maquina </h3>
Una vez entramos en la maquina virtual ssh instalamos algunas cosas que nos haran falta para ejecutar los .jar:<br>
1.-Se actualizan los paquetes con el comando: sudo apt update<br>
2.-Instalamos java mediante el comando: sudo apt install openjdk-17-jdk openjdk-17-jre<br>
3.-Comprobamos si se ha instalado java y verificamos su version con: java --version<br>
4.-Instalamos BD mysql mediante el comando: sudo apt install mysql-server<br> 
5.-Comprobamos si se ha instalado mysql y verificamos su version con: mysql --version<br>
6.-Entramos en el inicio de sesion de mysql con: mysql -u root -p<br>
&nbsp-&nbsp6.1.- En caso de no poder entrar hacemos este comando: sudo mysql -u root<br>
7.- Una vez dentro, si fuese necesario cambiamos la contraseña del root con lo siguiente: ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'TU CONTRASEÑA DEL PROPIERTIES';<br>
8.- Nos creamos el esquema que vamos a usar en la BD con lo siguiente: CREATE SCHEMA 'NOMBRE DE TU SCHEMA DEL PROPIERTIES';<br>
9.- Salimos del mysql con exit<br>
<h3> Ejecutar la aplicación </h3>
Estando dentro de la maquina virtual ssh:<br>
1.-Ejecutamos los .jar con el siguiente comando: java -jar "NOMBRE DEL .JAR"<br>
2.-Abrimos una nueva terminal<br>
3.-Repetir paso 1 con el .jar del servicio interno<br>
4.-Acceder a la pagina con : https://"Ip Flotante":"PUERTO ASOCIADO"<br>

<h1 align="center"> Fase 4 </h1>
<h2> Diagrama de conexiones </h2>
<div align="justify">

Nuestro programa se ha realizado dentro de contenedores docker, como se ha explicado anteriormente, de manera que su esquema quedaria tal que asi:
</div>
  <div align="center">
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/DiagramaDeConexiones.png" alt="android" width="800" height="500"/> </div>
<div align="justify">

Como se puede apreciar en la imagen, los usuarios solo se podran conectarse a la aplicacion a partir de la Ip publica del balanceador de carga, de forma que este se encargara de redirigir las peticiones a traves de las Ip's privadas del programa principal correspondiente.<br>
A su vez el programa principal accedera al servicio interno a traves del balanceador a traves de otro frontend especifico para los servicios internos.<br>
Y por ultimo tenemos una instancia para la Base de datos que es comun para las dos aplicaciones, que se conectan a esta mediante su Ip privada.<br>
</div>
<h2> Video </h2>
