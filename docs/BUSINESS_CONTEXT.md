## Repositorio seleccionado: [SOFKIANOS](https://github.com/ElyRiven/sofkianos-mvp)

Contexto de Negocio del Producto
1. Visión General del Producto
SofkianOS es un sistema distribuido de reconocimiento entre pares diseñado para fortalecer la cultura de equipo en organizaciones geográficamente distribuidas. El producto permite a los empleados enviar "Kudos" (reconocimientos) a sus compañeros, reconociendo logros y contribuciones específicas. El término "Kudos" proviene del griego y significa honor, reconocimiento y prestigio por un logro.
La propuesta de valor principal es crear una Cultura de Recompensas donde el reconocimiento es instantáneo, no bloqueante y fair-play mediante un sistema de gamificación con categorías y puntos, permitiendo procesamiento masivo de reconocimientos sin afectar la experiencia del usuario.
2. Actores y Roles
- Empleado Sofka (Remitente): Usuario interno que desea reconocer a un compañero por una contribución específica. Accede al formulario de envío de Kudos, selecciona al destinatario, elige una categoría y escribe un mensaje personalizado.
- Empleado Sofka (Destinatario): Usuario que recibe reconocimientos de sus compañeros. Puede visualizar los Kudos recibidos públicamente en el sistema.
- Usuario Explorador: Cualquier persona con acceso a la plataforma que desea consultar el historial de reconocimientos otorgados en la organización, pudiendo filtrar, ordenar y paginar los resultados.
3. Entidades Clave del Dominio
- Kudo (Reconocimiento): Representa un reconocimiento otorgado por un empleado a otro. Contiene información del remitente (de quién viene), destinatario (para quién va), categoría del reconocimiento, mensaje personalizado y fecha de creación. Es la entidad central del sistema.
- Usuario (Empleado): Representa a un miembro de la organización. Se identifica mediante correo electrónico y puede actuar como remitente o destinatario de Kudos.
- Categoría: Clasificación del tipo de reconocimiento. Existen cuatro categorías predefinidas: Innovation (Innovación), Teamwork (Trabajo en Equipo), Passion (Pasión) y Mastery (Dominio/Experiencia).
- Mensaje: Texto personalizado que acompaña al Kudo, con un límite de entre 10 y 500 caracteres, que detalla la razón del reconocimiento.
4. Funcionalidades Principales (Flujos de Valor)
1. Envío de Kudos: El empleado selecciona un compañero como destinatario, elige una categoría que mejor describa el reconocimiento, escribe un mensaje personalizado y confirma el envío mediante un control deslizante. El sistema procesa el reconocimiento de forma asíncrona, respondiendo inmediatamente al usuario.
2. Exploración de Kudos: El usuario puede visualizar todos los reconocimientos otorgados en la organización. El sistema permite filtrar por categoría, ordenar por fecha (ascendente/descendente), y navegar mediante paginación.
3. Verificación de Destinatario: Al seleccionar un destinatario en el formulario, el sistema muestra una previsualización de su identidad en tiempo real para confirmar que el reconocimiento se envía a la persona correcta.
4. Procesamiento Asíncrono de Reconocimientos: Los Kudos enviados se procesan en segundo plano mediante una arquitectura de mensajeria, permitiendo que el sistema escale y maneje miles de reconocimientos sin bloquear al usuario.
5. Reglas de Negocio Descubiertas
- Auto-reconocimiento prohibido: Un usuario no puede enviarse un Kudo a sí mismo. El sistema valida y rechaza este caso con el mensaje "Cannot send kudo to yourself".
- Validez de correo electrónico: Tanto el campo "from" como "to" deben ser direcciones de correo electrónico válidas. El sistema valida este formato antes de procesar el reconocimiento.
- Categorías restringidas: Solo se aceptan las cuatro categorías predefinidas (Innovation, Teamwork, Passion, Mastery). Cualquier otra categoría será rechazada.
- Longitud del mensaje: El mensaje de reconocimiento debe tener entre 10 y 500 caracteres. Mensajes más cortos o más largos no serán aceptados.
- Campos obligatorios: Todos los campos del formulario (remitente, destinatario, categoría y mensaje) son obligatorios y no pueden estar vacíos.
- Procesamiento no bloqueante: El sistema responde con confirmación inmediata (202 Accepted) al usuario, pero el procesamiento real del Kudo ocurre de forma asíncrona en segundo plano.