# Refinamiento de casos de prueba

| ID | Caso de Prueba generado por la gema | Ajuste realizado por el probador | ¿Por qué se ajustó? |
| :--- | :--- | :--- | :--- |
| TC-01 | Optimización de tamaño y usuario de ejecución en Docker. | Optimización de tamaño y usuario de ejecución en Docker. | La Gema realizo lo esperado con este caso de prueba. |
| TC-02 | Escaneo de vulnerabilidades en contenedor. | Escaneo de vulnerabilidades en contenedor sin nivel crítico y alto. | La Gema olvidó que el escaneo de seguridad del contenedor no debe detectar vulnerabilidades clasificadas como criticas y altas. |
| TC-03 | Despliegue exitoso en ambiente de Test. | Despliegue exitoso en ambiente de Test. | La Gema realizo lo esperado con este caso de prueba. |
| TC-04 | Persistencia de datos de Kudos tras reinicio. | Persistencia de datos de Kudos tras reinicio de la DB. | La Gema olvidó que los datos deben persistir después de un reinicio de la base de datos. |
| TC-05 | Validación de longitud mínima de mensaje en Kudo. | Rechazo por mensaje inferior al límite permitido. | La Gema olvidó que los mensajes inferiores al límite deben ser rechazados. |
| TC-06 | Validación de longitud máxima permitida. | Rechazo por mensaje superior a la longitud máxima | La Gema olvidó que los mensajes superiores al límite deben ser rechazados. |
| TC-07 | Validación de auto-reconocimiento denegado. | Rechazo por auto-reconocimiento. | La Gema, lo hizo bien, sin embargo, el mensaje es algo ambiguo. |
| TC-08 | Verificación de procesamiento asíncrono e historial. | Verificación de procesamiento asíncrono e historial para los kudos | La Gema, olvido incluir que los kudos deben ser procesados de forma asíncrona y mantener un historial. |

---

## Criterios de Aceptación (Gherkin) — Generados por la Gema

### TC-01: Optimización de tamaño y usuario de ejecución en Docker

```gherkin
Dado que el servicio de "Envío de Kudos" requiere una imagen Docker.
Cuando se ejecuta el proceso de CI/CD.
Entonces el Dockerfile debe usar una imagen base no-root (Alpine o Distroless) y el tamaño final debe ser < 200MB.
```

### TC-02: Escaneo de vulnerabilidades en contenedor

```gherkin
Dado un Dockerfile actualizado.
Cuando se ejecuta el escáner de seguridad (Trivy/Snyk).
Entonces no deben detectarse vulnerabilidades críticas (High/Critical).
```

### TC-03: Despliegue exitoso en ambiente de Test

```gherkin
Dado que se requiere desplegar el sistema para validación de QA.
Cuando se ejecuta docker-compose --env-file .env.test up.
Entonces todos los servicios (API, Workers, DB) deben iniciar con el estado "healthy" y conectarse a la red aislada de pruebas.
```

### TC-04: Persistencia de datos de Kudos tras reinicio

```gherkin
Dado un despliegue en producción.
Cuando se reinicia el contenedor de base de datos.
Entonces los Kudos registrados previamente deben persistir mediante volúmenes versionados.
```

### TC-05: Validación de longitud mínima de mensaje en Kudo

```gherkin
Dado un despliegue exitoso en Pre-Producción.
Cuando el pipeline ejecuta un POST al endpoint de /kudos con un mensaje de 5 caracteres.
Entonces el sistema debe responder con un error 400 (Bad Request) validando la regla de longitud mínima.
```

### TC-06: Validación de longitud máxima permitida

```gherkin
Dado un despliegue exitoso en Pre-Producción.
Cuando el usuario ejecuta un POST al endpoint de /kudos con un mensaje de 501 caracteres.
Entonces el sistema debe responder con un error 400 (Bad Request) validando la regla de longitud máxima.
```

### TC-07: Validación de auto-reconocimiento denegado

```gherkin
Dado un usuario autenticado en Pre-Producción.
Cuando el usuario intenta enviar un Kudo utilizando su propio correo como destinatario.
Entonces el sistema debe rechazar la solicitud con el mensaje "Cannot send kudo to yourself".
```

### TC-08: Verificación de procesamiento asíncrono e historial

```gherkin
Dado un Kudo enviado exitosamente (202 Accepted).
Cuando se consulta el historial de Kudos transcurridos 5 segundos.
Entonces el nuevo Kudo debe ser visible para el Usuario Explorador.
```

---

## Criterios de Aceptación (Gherkin) — Ajustados por el Probador

### TC-01: Optimización de tamaño y usuario de ejecución en Docker

> Sin cambios respecto al criterio original.

### TC-02: Escaneo de vulnerabilidades en contenedor sin nivel crítico y alto

```gherkin
Dado un Dockerfile actualizado.
Cuando se ejecuta el escáner de seguridad (Trivy/Snyk).
Entonces no deben detectarse vulnerabilidades clasificadas como críticas (Critical) ni altas (High).
```

> **Ajuste:** Se explicita que tanto el nivel Critical como High deben estar ausentes, no solo "críticas" de forma genérica.

### TC-03: Despliegue exitoso en ambiente de Test

> Sin cambios respecto al criterio original.

### TC-04: Persistencia de datos de Kudos tras reinicio de la DB

```gherkin
Dado un despliegue en producción.
Cuando se reinicia el contenedor de base de datos.
Entonces los Kudos registrados previamente deben persistir mediante volúmenes versionados y ser consultables inmediatamente tras el reinicio.
```

> **Ajuste:** Se precisa que la persistencia debe verificarse mediante una consulta funcional post-reinicio, no solo por la existencia de volúmenes.

### TC-05: Rechazo por mensaje inferior al límite permitido

```gherkin
Dado un despliegue exitoso en Pre-Producción.
Cuando el pipeline ejecuta un POST al endpoint de /kudos con un mensaje de 5 caracteres.
Entonces el sistema debe responder con un error 400 (Bad Request) y un mensaje indicando que el contenido no cumple la longitud mínima requerida.
```

> **Ajuste:** Se añade la expectativa de un mensaje de error descriptivo, no solo el status code.

### TC-06: Rechazo por mensaje superior a la longitud máxima

```gherkin
Dado un despliegue exitoso en Pre-Producción.
Cuando el usuario ejecuta un POST al endpoint de /kudos con un mensaje de 501 caracteres.
Entonces el sistema debe responder con un error 400 (Bad Request) y un mensaje indicando que el contenido excede la longitud máxima permitida.
```

> **Ajuste:** Se añade la expectativa de un mensaje de error descriptivo, no solo el status code.

### TC-07: Rechazo por auto-reconocimiento

```gherkin
Dado un usuario autenticado en Pre-Producción.
Cuando el usuario intenta enviar un Kudo utilizando su propio correo como destinatario.
Entonces el sistema debe rechazar la solicitud con un código 400 y el mensaje "Cannot send kudo to yourself".
```

> **Ajuste:** Se explicita el código HTTP esperado (400) junto al mensaje de rechazo, eliminando ambigüedad.

### TC-08: Verificación de procesamiento asíncrono e historial para los kudos

```gherkin
Dado un Kudo enviado exitosamente (202 Accepted).
Cuando se consulta el historial de Kudos transcurridos 5 segundos.
Entonces el nuevo Kudo debe ser visible para el Usuario Explorador en la lista de historial.
```

> **Ajuste:** Se precisa que la visibilidad es en la lista de historial, vinculando el procesamiento asíncrono con el resultado observable por el usuario.



