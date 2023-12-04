# reportesDeIncidentesUTN
Trabajo final de Java Intermedio de la UTN

>[!NOTE]
>Creado por:
>Pablo Torti y
>Cristian Calgaro

>[!IMPORTANT]
>Se debera crear una base de datos con el nombre **test**.

![final](https://github.com/Calgaro89/reportesDeIncidentesUTN/assets/140548112/2386d20a-14f9-4e53-bf8c-36b47618a0ef)

 <br>
  <br>

**Trabajo Práctico Integrador** <br>
 <br>
  <br>
**Sistema de Reporte de Incidentes** <br>
 <br>
**Contexto general**
Una importante empresa de soporte operativo solicita el diseño y desarrollo de un sistema
que le permita la generación y seguimiento de los incidentes que se presentan.<br>
La empresa en cuestión se dedica a brindar soporte operativo sobre distintas aplicaciones
(SAP, Tango, etc.) y sistemas operativos (Windows, MacOS, Linux Ubuntu).<br>
El área de RRHH se encarga de realizar las altas, bajas y modificaciones de los técnicos que se
encargan de resolver los incidentes reportados.<br>
Cada técnico tiene una o varias especialidades y solo se le pueden asignar incidentes que
coincidan con las mismas.<br>
El área comercial es responsable de incorporar nuevos clientes a la empresa. Administra las
altas, bajas y modificaciones de los datos de cada uno de ellos.<br>
Finalmente, la mesa de ayuda es responsable de atender las llamadas e ingresar al sistema los
incidentes reportados.<br>

**Ciclo de vida de un incidente**<br>
Cuando un cliente llama, la mesa de ayuda le solicita los datos para identificarlo (razón social,
CUIT) y los ingresa en el sistema para que el mismo le muestre los servicios que el cliente
tiene contratados.<br>
El operador (de la mesa de ayuda) solicita que le informen por cuál de esos servicios desea
reportar un incidente, junto con una descripción del problema y el tipo del problema.<br>
Al ingresar el incidente, el sistema devuelve un listado de técnicos disponibles para resolver el
problema. El operador selecciona uno de los técnicos disponibles y el sistema le informa el
tiempo estimado de resolución. Luego, informa al cliente que el incidente ha sido ingresado y
la fecha posible de resolución.<br>
Al confirmarse el incidente, el sistema debe enviar una notificación al técnico informándole
que tiene un nuevo incidente para resolver.<br>
<br>
<br>
Cuando el técnico atiende y resuelve el incidente, lo debe marcar como “resuelto”, indicando
las consideraciones que crea necesarias. Cuando esto ocurra, el sistema debe enviar un email
al cliente informándole que su incidente ya está solucionado.<br>
<br>
**Otros requerimientos**<br>
<br>
● El sistema debe permitir al área de RRHH emitir diariamente reportes con los
incidentes asignados a cada técnico y el estado de los mismos.<br>
● El sistema debe permitir que el operador agregue “un colchón” de horas estimadas
para la resolución del problema, si el mismo es considerado “complejo”.<br>
● El sistema debe permitir el alta de incidentes que contengan un conjunto de
problemas de un mismo servicio. Dichos problemas deben estar relacionados.<br>
● El sistema debe dar la posibilidad de informar:<br>
○ Quién fue el técnico con más incidentes resueltos en los últimos N días<br>
○ Quién fue el técnico con más incidentes resueltos de una determinada
especialidad en los últimos N días<br>
○ Quién fue el técnico que más rápido resolvió los incidentes<br>
<br>
**Consideraciones**<br>
<br>
● Cada tipo de problema particular puede ser solucionado por una o varias
especialidades.<br>
● Cada operador puede definir, optativamente, su tiempo estimado de resolución por
defecto por tipo de problema; el cual tendrá que ser menor al tiempo máximo de
resolución definido para el tipo de problema.<br>
● Cada técnico puede definir su medio preferido de notificación, los cuales pueden ser:
Email o WhatsApp. No están definidas las bibliotecas que se utilizarán para realizar
estas notificaciones.<br>
<br>
**Metodología**<br>
<br>
Se propone una metodología de trabajo iterativa e incremental. Para esto, el TP se divide en
tres entregas, las cuales se realizarán a través del envío del link al repositorio de trabajo por
medio del Campus Virtual en la fecha estipulada por el Docente del curso. <br>
En la última entrega (coincidente con la tercera) se deberá exponer el TP frente al curso,
mostrando la solución generada y justificando las decisiones tomadas. <br>
 <br>
  <br>

**Entrega 1.** <br>
En esta primera iteración nos encargaremos de modelar, a nivel datos y objetos, una solución
al dominio presentado. Además, comenzaremos con el proceso de codificación de la solución. <br>
 <br>
En particular, en esta entrega se solicita: <br>
1. Modelo de datos (DER físico) que brinde solución al dominio. <br>
2. Código con modelado de clases. El código debe estar subido a un repositorio de
GitHub. <br>
Es necesario que el proyecto Java sea creado como un proyecto “Maven” para poder añadir
algunas dependencias. Se recomienda la utilización de la dependencia de Lombok para
facilitar la tarea repetitiva de generación de Setters y Getters de las clases. <br>
 <br>
 
**Entrega 2.** <br>
En esta segunda iteración nos encargaremos de mapear, mediante anotaciones JPA, nuestras
clases implementadas para poder persistir el modelo en una Base de Datos Relacional. <br>
Además, comenzaremos con la generación de los repositorios/servicios para que brinden
solución a algunos requerimientos planteados. <br>
 <br>
En particular, en esta entrega se solicita: <br>
 <br>
1. Modelado de clases con Mapeo (anotaciones JPA) de entidades para que las mismas
sean persistidas mediante el ORM Hibernate. <br>
2. Repositorios/Servicios que den solución a los requerimientos planteados: <br>
a. Quién fue el técnico con más incidentes resueltos en los últimos N días <br>
b. Quién fue el técnico con más incidentes resueltos de una determinada
especialidad en los últimos N días <br>
c. Quién fue el técnico que más rápido resolvió los incidentes <br>
