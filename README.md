Para iniciar el proyecto

levantamos el contenedor con la BD desde la raíz del proyecto con
podman run -d --name transport-db \
  -e POSTGRES_DB=transport_db \
  -e POSTGRES_USER=transport_user \
  -e POSTGRES_PASSWORD=transport_pass \
  -p 5433:5432 \
  -v transport_pg_data:/var/lib/postgresql/data \
  postgres:16-alpine

una vez ejecutado levantamos el proyecto con 
mvn spring-boot:run

para la autenticación existe un servicio post
/api/auth/login 
en el cual el usuario y contraseña es admin

una vez que tenemos el token podemos ejecutar todos los demás servicios
la url de swagger es http://localhost:8080/swagger-ui/index.html
