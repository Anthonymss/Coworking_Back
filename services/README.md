Plataforma de Reservas de Espacios de Coworking
Descripción: Diseñar una plataforma de reservas de espacios de coworking que permita a los usuarios buscar y reservar espacios de trabajo, salas de reuniones y eventos. La plataforma debe incluir un sistema de facturación y gestión de membresías.



Diseño de la Arquitectura
El diseño de la arquitectura está en la carpeta resources en formato PDF.
Funcionalidades de la Aplicación
Permite logearse con los datos: nombre, apellido, correo y contraseña.
Permite registrarse con OAuth a través de Google.
Las contraseñas están encriptadas.
El servicio de autenticación (auth) devuelve información del usuario y un JWT para la sesión.
Se planifican implementaciones de roles con Spring Security.
En el header de la aplicación, una vez logeado, se puede ver el perfil, que utiliza el servicio de management-user para cargar datos.
También se utiliza el servicio coworkingspaces, que carga datos de espacios para la sección del módulo de espacios.
El API Gateway se usa para redirigir a los servicios; desde el frontend, solo se accede al gateway.
Eureka se usa para el descubrimiento dinámico de servicios, permitiendo que los servicios se registren sin necesidad de conocer sus direcciones y puertos. Para ver el dashboard, visita http://localhost:8761/. Además, ayuda con el balanceo de carga.
