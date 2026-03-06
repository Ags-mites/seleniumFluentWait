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



