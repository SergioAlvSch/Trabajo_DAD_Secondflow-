# Trabajo_DAD_Secondflow
Trabajo pagina web Diseño de Aplicaciones Distribuidas URJC 2023
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
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/inicioPostCreacionProducto.jpg" alt="android" width="550" height="300"/> </div>
<div align="justify">
Pantalla principal donde se visualizan todos los artículos en venta así como enlaces a: formulario para subir un nuevo producto, acceder al perfil del usuario y poder acceder a sus conversaciones. En todas las páginas habrá una cabecera un un pie de pagina donde, pulsando al logo desde cualquier lugar, volverá a esta pagina principal. Se podrá realizar una búsqueda de productos por nombre.
</div>


<div align="center">
<h3> Nuevo producto </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/formularioCreacion.jpg" alt="android" width="550" height="300"/> </div>
<div align="justify">
Ventana donde se podrá poner en venta un nuevo artículo, unicamente rellenando los campos requeridos, todos los campos son de obligatorio relleno salvo la imagen, que en caso de no ponerla, se mostrará una genérica.
</div>

<div align="center">
<h3> Visualización de un producto </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/productoCreado.jpg" alt="android" width="550" height="300"/> </div>
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
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/formularioEdicionProducto.jpg" alt="android" width="550" height="300"/> </div>
<div align="justify">
Formulario para editar un articulo previamente puesto en venta para su modificación


<div align="center">
<h3> Compra de un artículo </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/conversacionPreCompra.jpg" alt="android" width="550" height="300"/> </div>
Al seleccionar comprar un articulo desde la vista de su visualización se accede al chat donde se establece el dialogo comprador-vendedor, donde este último puede acceder a vender el articulo.



<div align="center">
<h3> Perfil de usuario </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/pantalla%20perfil.jpg" alt="android" width="550" height="300"/> </div>
Ventana donde aparece informacion principal de un usuario como su nombre, sus productos en venta y un hipervinculo al historial de compra


<div align="center">
<h3> Historial de compra </h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/historialCompras.jpg" alt="android" width="550" height="300"/> </div>
Pagina donde figurarán los articulos adquiridos por un usuario concreto

<div align="center">
<h3> Bandeja de conversaciones</h3>
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/bandejaConversaciones.jpg" alt="android" width="550" height="300"/> </div>
Pantalla donde se podrá acceder a las distintas conversaciones que tenga uno abiertas



<h2> Diagrama de navegación </h2>
<div align="center">
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/diagramaNavegacion.png" alt="android" width="550" height="300"/> </div>
  
<h2> Modelo de datos </h2>

<h3> Diagrama de clases UML </h3>
  <div align="center">
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/SecondFlowUML_Fase2.png" alt="android" width="800" height="500"/> </div>

<h3> Diagrama Entidad/Relación </h3>
<div align="center">
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/entidades%20base%20de%20datos.png" alt="android" width="450" height="300"/> </div>
  
  <div align="center">
<img src="https://github.com/SergioAlvSch/Trabajo_DAD_Secondflow-/blob/Documentacion/Ilustraciones/relacionesEntidades.png" alt="android" width="550" height="300"/> </div>
