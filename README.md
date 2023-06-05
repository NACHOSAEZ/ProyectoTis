 # **MODO AVIÓN**

Somos Cristina Hernando, Ignacio Sáez y Francisco Pérez.

Nuestro proyecto se basa en desarrollar una aplicación con su base de datos sobre compra-venta de billetes de vuelos.

Esta base de datos incluye la lista de todos los vuelos disponibles para que los clientes puedan elegir su vuelo junto con su asiento, reservar el billete, pagarlo y más tarde, confirmarlo; además, se registrarán los clientes que utilicen nuestra aplicación.

## **DIAGRAMA UML**

Aquí puedes encontrar nuestro Diagrama de casos de uso y su respectivo Diagrama "Entidad-Relación":

![Captura de pantalla 2023-03-10 094120](https://user-images.githubusercontent.com/72651303/224269435-0ec7b577-3add-4ccf-b2c0-1d091ce7f20a.png)

En nuestro diagrama podemos observar dos actores que desempeñan diferentes papeles:

Los clientes: Para nuestro proyecto son necesarios clientes que compren vuelos. Para ello, se registrarán en nuestra base de datos y buscarán vuelos a sus destinos favoritos; comprarán billetes y tendrán la opción de cancelarlos o confirmarlos ("Check-in").

Los vendedores: Serán los encargados de actualizar los vuelos en la lista de vuelos y vender estos billetes; para ello, previamente tendrán que registrarse como vendedores.

En cuanto a los casos de uso, podemos encontrar ocho casos que consisten en: Registro de los usuarios, compra de billetes por parte de los clientes que implica la elección del asiento y su posible cancelación o check-in ; venta de billetes por parte de los vendedores. Y por último, todo lo relacionado con los vuelos: la búsqueda de ellos y su actualización.


![DiagramaE-R](https://user-images.githubusercontent.com/72651303/224279100-6cc42d11-a8aa-4768-a6e3-999d5c735026.png)

En nuestro Diagrama Entidad-Relacion, observamos seis entidades con sus respectivos atributos y relaciones que mantienen entre ellas. Los atributos componen y definen a las entidades. Cada entidad, tiene siempre un atributo que es de tipo "primary key" y se utiliza como identificador de la propia entidad. En cuanto a las relaciones, nos sirven para ver que entidades están relacionadas y de que forma lo hacen. Hay dos tipos de relación: 1-n (uno a muchos) y n-m (muchos a muchos).

A continuación, explicaremos cada entidad, con sus atributos y relaciones correspondientes:

Compañia: Está formada por los atributos: País, PáginaWeb, Nombre, Num Telefono, Correo e Id. Siendo este último la primary key. En cuanto a su relación, tiene una relacion n-m (muchos a muchos) con la entidad Vuelo, ya que muchas compañías tienen muchos vuelos.

Vuelo: Esta entidad consta de los atributos: Fecha, Hora, Asientos y NumVuelo (su primary key). En cuanto a las relaciones, mantiene una de tipo n-m con Compañia, una de tipo 1-n (uno a muchos) con Billete, ya que un vuelo implica la venta de muchos billetes. Y, por último, mantiene dos relaciones de tipo 1-n con Aereopuerto.En este caso tiene dos relaciones con la misma entidad ya que una hace referencia al destino y la otra al origen.

Aereopuerto: Cuyos atributos son: Nombre, Ciudad, País e Id (primary key). Tiene dos relaciones 1-n con Vuelo y una relación 1-n con Empleado, ya que en un aereopuerto trabajan muchos empleados.

Empleado: Sus atributos que lo definen son: Nombre, Apellido, Puesto, Sueldo, Dni e Id (la primary key). Esta entidad solo mantiene una relación 1-n con Aereopuerto.

Billete: Sus atributos: Reservado, Pagado, Confirmado, Precio, Categoría e Id (su primary key). En cuanto a sus relaciones, mantiene una de tipo 1-n con Vuelo y otra del mismo tipo con Cliente, ya que un cliente compra muchos billetes.

Cliente: Sus atributos son: NumTelefono, Contraseña, Correo, Apellido, Nombre, Dni e Id (la primary key). ESta entidad tiene solo una relación de tipo 1-n con la entidad Billete.


## **DIAGRAMA INTERFAZ**

![DiagramaInterfaz1](https://github.com/NACHOSAEZ/ProyectoTis/blob/main/doc/DiagramaInterfaz1.png)
